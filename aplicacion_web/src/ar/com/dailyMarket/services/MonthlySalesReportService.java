package ar.com.dailyMarket.services;

import java.io.OutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.commons.beanutils.DynaBean;

import ar.com.dailyMarket.reports.ReportServiceImpl;

public class MonthlySalesReportService extends ReportServiceImpl {
	private static Map jaspers = new HashMap<String, JasperReport>();
	private static JasperReport jasperReport = null;
	
	public int runReport(DynaBean reportData, OutputStream stream, String path, ServletContext ctx, Collection col, String report)throws Exception {
        Map parameters = new HashMap();		
        String imgs = "";
        
//        Context initCtx;
//		try {			
//			initCtx = new InitialContext();
//			Context envCtx = (Context)initCtx.lookup("java:comp/env");
//	        imgs = (String) envCtx.lookup("reportsFolder");
//		} catch (NamingException e1) {
//			e1.printStackTrace();
//		}
        //parameters.put("reportsFolder", imgs); 

//		try {
//			reportName="ventasMensuales";
//			if (jasperReport == null) {
//				jasperReport = JasperCompileManager.compileReport(this.getClass().getResourceAsStream(path + reportName + ".jrxml"));
//			}
//		
//			byte[] bytes = null;			
//			
//			bytes = JasperRunManager.runReportToPdf(jasperReport, parameters, getDataSource(col));			
//			
//			stream.write(bytes, 0, bytes.length);
//			stream.flush();
//			stream.close();
//			return bytes.length; //Para el setContentLength del response
			
        try {
        JasperCompileManager.compileReportToFile(path + "\\WEB-INF\\classes\\reports\\reportePrueba" + ".jrxml");
//	    JasperReport jasperReport = (JasperReport) JRLoader.loadObject(ctx.getResourceAsStream(path + "\\WEB-INF\\classes\\reports\\reportePrueba" + ".jasper"));
		
	    parameters.put(JRParameter.REPORT_LOCALE, new Locale("es"));
		parameters.put(JRParameter.REPORT_RESOURCE_BUNDLE, ResourceBundle.getBundle("ApplicationResources", new Locale("es")));
	    
		byte[] bytes = null;			
//		bytes = JasperRunManager.runReportToPdf(jasperReport, parameters, getDataSource(col));			
	
		//CAMBIE LO ANTERIOR POR ESTO Y ANDUVO!!!!!!!! IGUAL LO MIRO AUNQUE ESTO NO ESTA MAL
		bytes = JasperRunManager.runReportToPdf(path + "\\WEB-INF\\classes\\reports\\reportePrueba" + ".jasper", parameters, getDataSource(col));			
		
		stream.write(bytes);
		stream.flush();
		stream.close();
		return bytes.length; //Para el setContentLength del response
        } catch (Throwable e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}
        
/*			
			if (!(jaspers.containsKey(report))) {      	
        		//jaspers.put(report, JRLoader.loadObject(this.getClass().getResourceAsStream(path + report + ".jasper")));
        		report = "prueba";        		
        		jaspers.put(report, "C:\\workspace\\aplicacion_web\\config\\reports\\prueba.jrxml");
        		JasperCompileManager.compileReport("C:\\workspace\\aplicacion_web\\webapp\\WEB-INF\\classes\\reports\\ventasMensuales.jrxml");
        	}			
        	reportName="prueba";
        	jasperReport = JasperCompileManager.compileReport(this.getClass().getResourceAsStream(path + reportName + ".jrxml"));
        	byte[] bytes = JasperRunManager.runReportToPdf(jasperReport, parameters, getDataSource(col));						
			stream.write(bytes, 0, bytes.length);
			stream.flush();
			stream.close();
			return bytes.length; //Para el setContentLength del response
        	
        	byte[]  bytes = JasperRunManager.runReportToPdf((String) jaspers.get(report), parameters, getDataSource(col));
			InputStream s = this.getClass().getClassLoader().getResourceAsStream("/reports/ventasMensuales.pdf");
			byte[] buffer = new byte[1000];
			int len;
			int tot = 0;
			
			while((len = s.read(buffer)) >= 0) {
				stream.write(buffer, 0, len);
				tot += len;
			}
				
			s.close();
			stream.flush();
			stream.close();
			//return bytes; //Para el setContentLength del response
			
		} catch (JRException e) {
			e.printStackTrace();
			throw new Exception(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception(e);
		} 			*/
    
    private JRDataSource getDataSource(Collection results) {
        return new CustomDS(results);
    }
    
    protected class CustomDS implements JRDataSource  {		
        protected Iterator<Object> iterator; 
        protected Object currentValue; 
        
        public CustomDS(Collection c) {
        	this.iterator = c.iterator();
        }
        
        public Object getFieldValue(JRField field) {
            return null;
        }
        
        public boolean next() throws JRException { 
            currentValue = iterator.hasNext() ? iterator.next() : null; 
            return (currentValue != null);
        }
    }  
}
