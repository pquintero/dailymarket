package ar.com.dailyMarket.reports;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.dbcp.BasicDataSource;

import ar.com.dailyMarket.services.BaseReportService;

public class ReportServiceImpl extends BaseReportService implements ReportService {
	protected String reportName;
	protected String baseQuery;
	protected String orderBy;
	protected String groupBy;
	protected ReportFilterImpl reportFilter;
	
	
	public int runReport(DynaBean reportData, OutputStream stream, String path, ServletContext ctx) throws Exception {
		
		Connection conn = null;
		Map parameters = generateParametersMap(reportData);
		try {
			conn = getConnection();	
	    	
	    	JasperCompileManager.compileReportToFile(path + reportName + ".jrxml");	    	
	    	
	    	JasperReport jasperReport = (JasperReport) JRLoader.loadObject(ctx.getResourceAsStream(path + reportName + ".jasper"));
				
			String reportFileName = null;					
			byte[] bytes = null;			
			
			bytes = JasperRunManager.runReportToPdf(jasperReport, parameters, conn);			
			
			stream.write(bytes, 0, bytes.length);
			stream.flush();
			stream.close();
			if (conn != null) {				
				conn.close();				
			}
			return bytes.length; //Para el setContentLength del response
			
		} catch (JRException e) {
			e.printStackTrace();
			throw new Exception();
		} catch (NamingException e) {
			e.printStackTrace();
			throw new Exception();
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception();
		}
		
	}
	
	protected Map generateParametersMap(DynaBean reportData) {
		Map params = new HashMap();
		String filters = "";
		if (reportFilter != null) {
			filters = ((ReportFilterImpl)reportFilter).createSQLQuery(reportData);
		}
		if (baseQuery != null && !baseQuery.equals("")) {
			StringBuffer query = new StringBuffer("");
			query.append(baseQuery);
			if (filters != null && !filters.equals("")) {
				query.append(" where 1=1 " + filters);				
			}
			if (groupBy != null && !groupBy.equals("")) {
				query.append(" group by " + groupBy);
			}				
			if (orderBy != null && !orderBy.equals("")) {
				query.append(" order by " + orderBy);
			}		
			params.put("sql_query",query.toString());
		} else {
			return null;
		}
		return params;
	}
	
	private BasicDataSource getDataSource() throws NamingException {
		BasicDataSource ds = null;
    	if (ds == null) {
			Context ctx = new InitialContext();
			ds = (BasicDataSource) ctx.lookup("java:comp/env/jdbc/bumeran");
		}
		return ds;
	}

	private Connection getConnection() throws SQLException, NamingException {
		return getDataSource().getConnection();
	}

	public String getBaseQuery() {
		return baseQuery;
	}
	public void setBaseQuery(String baseQuery) {
		this.baseQuery = baseQuery;
	}
	public ReportFilterImpl getReportFilter() {
		return reportFilter;
	}
	public void setReportFilter(ReportFilterImpl reportFilter) {
		this.reportFilter = reportFilter;
	}
	public void addReportFilter(ReportFilterImpl filter) {
        this.reportFilter = filter;
    }
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}	
    public String getGroupBy() {
        return groupBy;
    }
    public void setGroupBy(String groupBy) {
        this.groupBy = groupBy;
    }
}
