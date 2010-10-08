package ar.com.dailyMarket.services;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.commons.beanutils.DynaBean;

public class MonthlySalesReportService {
	private static Map jaspers = new HashMap<String, JasperReport>();
	private static JasperReport jasperReport = null;
	
	public byte[] runReport(DynaBean reportData, Collection col, String report)throws Exception {
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
		
        try {
		    JasperReport jasperReport = (JasperReport) JRLoader.loadObject(MonthlySalesReportService.class.getResourceAsStream("/reports/" + report + ".jasper"));
	
	//	    parameters.put(JRParameter.REPORT_LOCALE, new Locale("es"));
	//		parameters.put(JRParameter.REPORT_RESOURCE_BUNDLE, ResourceBundle.getBundle("ApplicationResources", new Locale("es")));
			
		  //Para probar
		    col.add(new Prueba("Carnes", new Double(1275.3)));
		    col.add(new Prueba("Lacteos", new Double(275.8)));
		    col.add(new Prueba("Verduras", new Double(75.4)));
		    col.add(new Prueba("Bebidas", new Double(1351.9)));
		    
		    return JasperRunManager.runReportToPdf(jasperReport, parameters, getDataSource(col));			
        } catch (Throwable e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	protected class Prueba {
		protected String producto;
		protected Double ventas;
		
		public Prueba(String pr, Double ve) {
			producto = pr;
			ventas = ve;
		}
		
		public String getProducto() {
			return producto;
		}
		public void setProducto(String producto) {
			this.producto = producto;
		}
		public Double getVentas() {
			return ventas;
		}
		public void setVentas(Double ventas) {
			this.ventas = ventas;
		}
	}
	
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
            Prueba pr = (Prueba) currentValue;
        	if ("producto".equals(field.getName())) {
        		return pr.getProducto();
        	} else if("ventas".equals(field.getName())) {
        		return pr.getVentas().toString();
        	}
        	return null;
        }
        
        public boolean next() throws JRException { 
            currentValue = iterator.hasNext() ? iterator.next() : null; 
            return (currentValue != null);
        }
    }  
}
