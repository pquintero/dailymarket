package ar.com.dailyMarket.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import ar.com.dailyMarket.model.GroupProduct;
import ar.com.dailyMarket.model.HourlyBand;
import ar.com.dailyMarket.model.Product;
import ar.com.dailyMarket.model.ProductoVenta;
import ar.com.dailyMarket.model.SesionVenta;

public class SalesReportService extends BaseReportService{
	
	public byte[] runReport(DynaBean reportData, Collection col, String report, Map<String, String> filters,String tipo)throws Exception {
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
		    JasperReport jasperReport = (JasperReport) JRLoader.loadObject(SalesReportService.class.getResourceAsStream("/reports/" + report + ".jasper"));
			
		    if (tipo.equals("Anual")) {
			    setDataReportAnual(col, filters);		    	
		    } else {
		    	col.add(new Ventas(new Integer(58), new Integer(764), "09/09"));
			    col.add(new Ventas(new Integer(58), new Integer(678), "10/09"));
			    col.add(new Ventas(new Integer(58), new Integer(813), "11/09"));
			    col.add(new Ventas(new Integer(58), new Integer(1351), "12/09"));
		    }
		    
		    return JasperRunManager.runReportToPdf(jasperReport, parameters, getDataSource(col, filters, tipo));			
        } catch (Throwable e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	protected class Ventas {
		protected Integer producto;
		protected Integer ventas;
		protected Double prom;
		protected String anio;
		
		public Ventas(Integer pr, Integer ve, String anio) {
			producto = pr;
			ventas = ve;
			this.anio = anio; 
			prom =  Double.parseDouble(pr.toString()) / Double.parseDouble(ve.toString());
		}
		
		public Integer getProducto() {
			return producto;
		}
		public void setProducto(Integer producto) {
			this.producto = producto;
		}
		public Integer getVentas() {
			return ventas;
		}
		public void setVentas(Integer ventas) {
			this.ventas = ventas;
		}
		public Double getProm() {
			return prom;
		}
		public void setProm(Double prom) {
			this.prom = prom;
		}
		public String getAnio() {
			return anio;
		}
		public void setAnio(String anio) {
			this.anio = anio;
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
        	Ventas pr = (Ventas) currentValue;
        	if ("producto".equals(field.getName())) {
        		return pr.getProducto();
        	} else if("ventas".equals(field.getName())) {
        		return pr.getVentas();
        	} else if("groupProduct".equals(field.getName())) {
        		return filters.get("groupProduct");
        	} else if("productFilter".equals(field.getName())) {
        		return filters.get("productFilter");
        	} else if("periodo".equals(field.getName())) {
        		return filters.get("periodo");
        	} else if("hourlyBand".equals(field.getName())) {
        		return filters.get("hourlyBand");
        	} else if("date".equals(field.getName())) {
        		return pr.getAnio();
        	} else if("prom".equals(field.getName())) {
        		return pr.getProm().toString().substring(0, pr.getProm().toString().indexOf(".") + 2);
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
    
    private void setDataReportAnual(Collection col, Map<String, String> filters) {
    	Calendar calDesde = new GregorianCalendar();
    	Calendar calHasta = new GregorianCalendar();
    	
    	calDesde.setTime(new Date());
    	calDesde.set(Calendar.MONTH, 0);
    	calDesde.set(Calendar.DAY_OF_MONTH, 1);    	
    	calHasta.setTime(calDesde.getTime());
    	calHasta.set(Calendar.YEAR, Integer.parseInt(filters.get("anioHasta")));
    	calHasta.set(Calendar.MONTH, 11);
    	calHasta.set(Calendar.DAY_OF_MONTH, 31);
    	calDesde.set(Calendar.YEAR, Integer.parseInt(filters.get("anioDesde")));
    	
    	List<SesionVenta> ventas = getVentasAnuales(calDesde, calHasta, filters);    	    	
    	
    	Integer prodPorAnio = 0;
    	Integer ventasPorAnio = 0;
    	Integer anio = 0;
    	for (Iterator<SesionVenta> it = ventas.iterator(); it.hasNext(); ) {    		
    		SesionVenta sVenta = it.next();
    		Calendar calVenta = new GregorianCalendar();
    		calVenta.setTime(sVenta.getFechaInicio());
    		if (anio < calVenta.get(Calendar.YEAR)) {
    			if (anio != 0) {
    				col.add(new Ventas(prodPorAnio, ventasPorAnio, anio.toString()));    				    			
    			}  
    			anio = calVenta.get(Calendar.YEAR);
    			prodPorAnio = sVenta.getProductos().size();
    	    	ventasPorAnio = 1;    	    	
    		} else {
    			prodPorAnio =+ sVenta.getProductos().size();
    			ventasPorAnio++;
    		}    		
    	}
    	if (!ventas.isEmpty()) {
    		//guardo el último
        	col.add(new Ventas(prodPorAnio, ventasPorAnio, anio.toString()));
    	}    	
    }
    
    @SuppressWarnings("unchecked")
	private List<SesionVenta> getVentasAnuales (Calendar calDesde, Calendar calHasta, Map<String, String> filters) {
    	String productId = filters.get("productId");
    	String groupProductId = filters.get("groupProductId");
    	String hourlyBandId = filters.get("hourlyBandId");
    	
    	Criteria c = HibernateHelper.currentSession().createCriteria(SesionVenta.class);
    	c.add(Restrictions.between("fechaInicio", calDesde.getTime(), calHasta.getTime()));    	
    	c.addOrder(Order.asc("fechaInicio"));
    	    	
    	List<SesionVenta> ventas = c.list();
    	
    	if (!hourlyBandId.equals("-1")){ //si filtre por banda horaria
    		HourlyBand hb = new HourlyBandService().getHourlyBandByPK(Long.parseLong(hourlyBandId));
    		List<SesionVenta> ventasFilter = new ArrayList<SesionVenta>();
    		for (Iterator<SesionVenta> it = ventas.iterator(); it.hasNext(); ){
    			SesionVenta venta = it.next();
    			Calendar calVenta = new GregorianCalendar();
    			calVenta.setTime(venta.getFechaInicio());
    			//me fijo si la venta esta en el rango de la banda horaria ingresada en el filtro
    			if (calVenta.get(Calendar.HOUR_OF_DAY) >= hb.getInitBand() && calVenta.get(Calendar.HOUR_OF_DAY) <= hb.getEndBand()){
    				ventasFilter.add(venta);
    			}
    		}
    		ventas = ventasFilter;
    	}
    	if (!productId.equals("-1")) { //si filtre por producto
    		List<SesionVenta> ventasFilter = new ArrayList<SesionVenta>();
    		Product product = new ProductService().getProductByPK(Long.parseLong(productId)); 
    		for (Iterator<SesionVenta> it = ventas.iterator(); it.hasNext(); ) {
    			SesionVenta venta = it.next();
    			for (Iterator<ProductoVenta> pv = venta.getProductos().iterator(); it.hasNext(); ) {
    				ProductoVenta pVenta = pv.next();
    				if (pVenta.getProducto().getId().longValue() == product.getId().longValue()) {
    					ventasFilter.add(venta);
    					break;
    				}   
    			}
    		}
    		return ventasFilter;
    	} else if (!groupProductId.equals("-1")) { //si filtre por grupo de producto
    		List<SesionVenta> ventasFilter = new ArrayList<SesionVenta>();
    		for (Iterator<SesionVenta> it = ventas.iterator(); it.hasNext(); ) {
    			SesionVenta venta = it.next();
    			List<Product> products = new ProductService().getProductsByGroup(Long.parseLong(groupProductId));    			
    			for (Iterator<ProductoVenta> it2 = venta.getProductos().iterator(); it2.hasNext(); ) {
    				if (products.contains(((ProductoVenta)it2.next()).getProducto())) {
    					ventasFilter.add(venta);
    					break;
    				}    			    			    				
    			}
    		}
    		return ventasFilter;
    	} else {
    		return ventas;
    	}    	
    }
}
