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

public class BillingReportService extends BaseReportService{
	
	public byte[] runReport(DynaBean reportData, Collection col, String report, Map<String, String> filters, String tipo)throws Exception {
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
		    JasperReport jasperReport = (JasperReport) JRLoader.loadObject(BillingReportService.class.getResourceAsStream("/reports/" + report + ".jasper"));
			
		  //Para probar
		    col.add(new AnnualBilling(new Integer(58), new Double(764.3), "2007", new Integer(1345)));
		    col.add(new AnnualBilling(new Integer(58), new Double(678.6), "2008", new Integer(2145)));
		    col.add(new AnnualBilling(new Integer(58), new Double(813.2), "2009", new Integer(2456)));
		    col.add(new AnnualBilling(new Integer(58), new Double(1351.5), "2010",new Integer(2897)));
		    
		    return JasperRunManager.runReportToPdf(jasperReport, parameters, getDataSource(col, filters, tipo));			
        } catch (Throwable e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	protected class AnnualBilling {
		private Integer prodVendidos;
		private Double billing;
		private Double promFact;
		private String anio;
		private Double promVentas;
		private Integer ventas;
		
		public AnnualBilling(Integer prodVendidos, Double billing, String anio, Integer ventas) {
			this.prodVendidos = prodVendidos;
			this.billing = billing;
			this.anio = anio; 
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
		public String getAnio() {
			return anio;
		}
		public void setAnio(String anio) {
			this.anio = anio;
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
	
    private JRDataSource getDataSource(Collection results, Map<String, String> filters, String tipo) {
        return new CustomDS(results, filters, tipo);
    }
    
    protected class CustomDS implements JRDataSource  {		
        protected Iterator<Object> iterator; 
        protected Object currentValue; 
        protected Map<String, String> filters;
        protected String tipo;
        
        public CustomDS(Collection c, Map<String, String> filters, String tipo) {
        	this.iterator = c.iterator();
        	this.filters = filters;
        	this.tipo = tipo;
        }
        
        public Object getFieldValue(JRField field) {
        	AnnualBilling ab = (AnnualBilling) currentValue;
        	if ("prodVendidos".equals(field.getName())) {
        		return ab.getProdVendidos();
        	} else if("billing".equals(field.getName())) {
        		return ab.getBilling();
        	} else if("groupProduct".equals(field.getName())) {
        		return filters.get("groupProduct");
        	} else if("productFilter".equals(field.getName())) {
        		return filters.get("productFilter");
        	} else if("periodo".equals(field.getName())) {
        		return filters.get("periodo");
        	} else if("hourlyBand".equals(field.getName())) {
        		return filters.get("hourlyBand");
        	} else if("date".equals(field.getName())) {
        		return ab.getAnio();
        	} else if("ventas".equals(field.getName())) {
        		return ab.getVentas().toString();
        	} else if("promVentas".equals(field.getName())) {
        		return ab.getPromVentas().toString().substring(0, ab.getPromVentas().toString().indexOf(".") + 3);
        	} else if("promFact".equals(field.getName())) {
        		return ab.getPromFact().toString().substring(0, ab.getPromFact().toString().indexOf(".") + 3);
        	} else if("tipo".equals(field.getName())) {
        		return tipo;
        	} else if("tipoPeriodo".equals(field.getName())) {
        		return tipo.equals("Anual") ? "Año" : "Mes";
        	}
        	return null;
        }
        
        public boolean next() throws JRException { 
            currentValue = iterator.hasNext() ? iterator.next() : null; 
            return (currentValue != null);
        }
    }          
}
