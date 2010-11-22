package ar.com.dailyMarket.services;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;
import net.sourceforge.barbecue.output.OutputException;

import org.apache.commons.beanutils.DynaBean;

import ar.com.dailyMarket.model.GroupProduct;
import ar.com.dailyMarket.model.Product;

public class ListCodesReportService extends BaseReportService{
	
	public byte[] runReport(DynaBean reportData, String report, Map<String, Object> filters)throws Exception {
        Map<String, String> parameters = new HashMap<String, String>();		
        String imgs = "";
        String barcodeUrl = "";
        
        Context initCtx;
		try {			
			initCtx = new InitialContext();
			Context envCtx = (Context)initCtx.lookup("java:comp/env");
	        imgs = (String) envCtx.lookup("reportsFolder");
	        barcodeUrl = (String) envCtx.lookup("barcode");
		} catch (NamingException e1) {
			e1.printStackTrace();
		}
		
        try {
		    JasperReport jasperReport = (JasperReport) JRLoader.loadObject(ListCodesReportService.class.getResourceAsStream("/reports/" + report + ".jasper"));			
		    
		    return JasperRunManager.runReportToPdf(jasperReport, parameters, getDataSource(getListProduct(filters), filters, barcodeUrl, imgs));			
        } catch (Throwable e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}
		
    @SuppressWarnings("unchecked")
	private JRDataSource getDataSource(Collection results, Map<String, Object> filters, String barcodeUrl, String img) {
        return new CustomDS(results, filters,barcodeUrl, img);
    }
    
    protected class CustomDS implements JRDataSource  {		
        protected Iterator<Object> iterator; 
        protected Object currentValue; 
        protected Map<String, Object> filters;
        protected String barcodeUrl;
        protected String imgs;
        
        @SuppressWarnings("unchecked")
		public CustomDS(Collection c, Map<String, Object> filters, String barcodeUrl, String imgs) {
        	this.iterator = c.iterator();
        	this.filters = filters;
        	this.barcodeUrl = barcodeUrl;
        	this.imgs = imgs;
        }
        
        public Object getFieldValue(JRField field) {
        	Product product = (Product) currentValue;
        	
        	if("groupProduct".equals(field.getName())) {
        		return filters.get("groupProduct") != null ? ((GroupProduct)filters.get("groupProduct")).getName() : "Todos";
        	} else if("productFilter".equals(field.getName())) {
        		return filters.get("productFilter") != null ? ((Product)filters.get("productFilter")).getName() : "Todos";
        	} else if("code".equals(field.getName())) {        		
        		CodigoBarra codigoBarra = new CodigoBarra();
        		codigoBarra.CodigoBarraJpg(product.getCode(), barcodeUrl);
        		return product.getCode();
        	} else if("price".equals(field.getName())) {
        		return product.getPrice().toString();
        	} else if("name".equals(field.getName())) {
        		return product.getName() + " - " + product.getCode();
        	} else if("urlBarcode".equals(field.getName())) {
        		return  barcodeUrl;
        	} else if("urlImg".equals(field.getName())) {
        		return  imgs;
        	}  
        	return null;
        }
        
        public boolean next() throws JRException { 
            currentValue = iterator.hasNext() ? iterator.next() : null; 
            return (currentValue != null);
        }               
    } 
    
    public class CodigoBarra{ 
    	//parametro de codigo, es el codigo a convertir en barras. 
    	//parametro de ruta, es la carpeta donde se almacenara el archivo de imagen jpg. 
    	public void CodigoBarraJpg(String codigo, String ruta){ 
    		Barcode barcode=null; 
    		try { 
    			barcode = BarcodeFactory.createCode128(codigo); 
    			FileOutputStream fos=null;     			    			    		    			
    			fos = new FileOutputStream(ruta+codigo+".jpg"); 
    			BarcodeImageHandler.outputBarcodeAsJPEGImage(barcode, fos); 
    		} catch (FileNotFoundException e) { 
    			e.printStackTrace(); 
    		} catch (BarcodeException e) { 
    			e.printStackTrace(); 
    		} catch (IOException e) { 
    			e.printStackTrace(); 
    		} catch (OutputException e) {
				e.printStackTrace();
			} 
    		System.out.println("CodigoBarraJpg: "+ruta); 
    	} 
    } 
}
