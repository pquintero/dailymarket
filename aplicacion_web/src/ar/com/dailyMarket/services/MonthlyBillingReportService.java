package ar.com.dailyMarket.services;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.commons.beanutils.DynaBean;

public class MonthlyBillingReportService extends BaseReportService{
	
	public byte[] runReport(DynaBean reportData, Collection col, String report, Map<String, String> filters)throws Exception {
        Map<String, String> parameters = new HashMap<String, String>();		
        String imgs = "";
        
        Context initCtx;
		try {			
			initCtx = new InitialContext();
			Context envCtx = (Context)initCtx.lookup("java:comp/env");
	        imgs = (String) envCtx.lookup("reportsFolder");
		} catch (NamingException e1) {
			e1.printStackTrace();
		}
        parameters.put("reportsFolder", imgs); 
		
        try {
		    JasperReport jasperReport = (JasperReport) JRLoader.loadObject(MonthlyBillingReportService.class.getResourceAsStream("/reports/" + report + ".jasper"));
			
		  //Para probar
		    col.add(new MonthlyBilling(new Integer(58), new Double(764.3), "08/09", new Integer(1345)));
		    col.add(new MonthlyBilling(new Integer(58), new Double(678.6), "09/09", new Integer(2145)));
		    col.add(new MonthlyBilling(new Integer(58), new Double(813.2), "10/09", new Integer(2456)));
		    col.add(new MonthlyBilling(new Integer(58), new Double(1351.5), "11/09",new Integer(2897)));
		    
		    return JasperRunManager.runReportToPdf(jasperReport, parameters, getDataSource(col, filters));			
        } catch (Throwable e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	protected class MonthlyBilling {
		private Integer prodVendidos;
		private Double billing;
		private Double promFact;
		private String month;
		private Double promVentas;
		private Integer ventas;
		
		public MonthlyBilling(Integer prodVendidos, Double billing, String month, Integer ventas) {
			this.prodVendidos = prodVendidos;
			this.billing = billing;
			this.month = month; 
			promVentas =  Double.parseDouble(prodVendidos.toString()) / Double.parseDouble(billing.toString());
			promFact =  Double.parseDouble(billing.toString()) / Double.parseDouble(ventas.toString());
			this.ventas = ventas; 
		}
				
		public Integer getProdVendidos() {
			return prodVendidos;
		}
		public void setProdVendidos(Integer prodVendidos) {
			this.prodVendidos = prodVendidos;
		}		
		public String getMonth() {
			return month;
		}
		public void setMonth(String month) {
			this.month = month;
		}
		public Double getBilling() {
			return billing;
		}
		public void setBilling(Double billing) {
			this.billing = billing;
		}
		public Double getPromFact() {
			return promFact;
		}
		public void setPromFact(Double promFact) {
			this.promFact = promFact;
		}
		public Double getPromVentas() {
			return promVentas;
		}
		public void setPromVentas(Double promVentas) {
			this.promVentas = promVentas;
		}
		public Integer getVentas() {
			return ventas;
		}
		public void setVentas(Integer ventas) {
			this.ventas = ventas;
		}			
	}
	
    private JRDataSource getDataSource(Collection results, Map<String, String> filters) {
        return new CustomDS(results, filters);
    }
    
    protected class CustomDS implements JRDataSource  {		
        protected Iterator<Object> iterator; 
        protected Object currentValue; 
        protected Map<String, String> filters;
        
        public CustomDS(Collection c, Map<String, String> filters) {
        	this.iterator = c.iterator();
        	this.filters = filters;
        }
        
        public Object getFieldValue(JRField field) {
        	MonthlyBilling mb = (MonthlyBilling) currentValue;
        	if ("prodVendidos".equals(field.getName())) {
        		return mb.getProdVendidos();
        	} else if("billing".equals(field.getName())) {
        		return mb.getBilling();
        	} else if("groupProduct".equals(field.getName())) {
        		return filters.get("groupProduct");
        	} else if("productFilter".equals(field.getName())) {
        		return filters.get("productFilter");
        	} else if("periodo".equals(field.getName())) {
        		return filters.get("periodo");
        	} else if("hourlyBand".equals(field.getName())) {
        		return filters.get("hourlyBand");
        	} else if("month".equals(field.getName())) {
        		return mb.getMonth();
        	} else if("ventas".equals(field.getName())) {
        		return mb.getVentas().toString();
        	} else if("promVentas".equals(field.getName())) {
        		return mb.getPromVentas().toString().substring(0, mb.getPromVentas().toString().indexOf(".") + 3);
        	} else if("promFact".equals(field.getName())) {
        		return mb.getPromFact().toString().substring(0, mb.getPromFact().toString().indexOf(".") + 3);
        	}
        	return null;
        }
        
        public boolean next() throws JRException { 
            currentValue = iterator.hasNext() ? iterator.next() : null; 
            return (currentValue != null);
        }
    }          
}
