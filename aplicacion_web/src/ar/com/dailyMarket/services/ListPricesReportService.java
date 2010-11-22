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

import ar.com.dailyMarket.model.GroupProduct;
import ar.com.dailyMarket.model.Product;

public class ListPricesReportService extends BaseReportService{
	
	public byte[] runReport(DynaBean reportData, String report, Map<String, Object> filters)throws Exception {
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
		
        try {
		    JasperReport jasperReport = (JasperReport) JRLoader.loadObject(ListPricesReportService.class.getResourceAsStream("/reports/" + report + ".jasper"));					    
		    return JasperRunManager.runReportToPdf(jasperReport, parameters, getDataSource(getListProduct(filters), filters, imgs));			
        } catch (Throwable e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}			
	
    private JRDataSource getDataSource(Collection results, Map<String, Object> filters, String imgs) {
        return new CustomDS(results, filters, imgs);
    }
    
    protected class CustomDS implements JRDataSource  {		
        protected Iterator<Object> iterator; 
        protected Object currentValue; 
        protected Map<String, Object> filters;
        protected String imgs;
        
        public CustomDS(Collection c, Map<String, Object> filters, String imgs) {
        	this.iterator = c.iterator();
        	this.filters = filters;
        	this.imgs = imgs;
        }
        
        public Object getFieldValue(JRField field) {
        	Product product = (Product) currentValue;
        	
        	if("groupProduct".equals(field.getName())) {
        		return filters.get("groupProduct") != null ? ((GroupProduct)filters.get("groupProduct")).getName() : "Todos";
        	} else if("productFilter".equals(field.getName())) {
        		return filters.get("productFilter") != null ? ((Product)filters.get("productFilter")).getName() : "Todos";
        	} else if("code".equals(field.getName())) {
        		return product.getCode();
        	} else if("price".equals(field.getName())) {
        		return product.getPrice().toString();
        	} else if("name".equals(field.getName())) {
        		return product.getName();
        	} else if("description".equals(field.getName())) {
        		return product.getDescription();
        	} else if("actualStock".equals(field.getName())) {
        		return product.getActualStock().toString();
        	} else if("repositionStock".equals(field.getName())) {
        		return product.getRepositionStock().toString();
        	} else if("urlImg".equals(field.getName())) {
        		return imgs;
        	}
        	return null;
        }
        
        public boolean next() throws JRException { 
            currentValue = iterator.hasNext() ? iterator.next() : null; 
            return (currentValue != null);
        }               
    }         
}
