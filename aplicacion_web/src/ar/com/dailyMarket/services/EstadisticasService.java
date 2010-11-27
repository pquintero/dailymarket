package ar.com.dailyMarket.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.apache.struts.action.DynaActionForm;
import org.hibernate.Criteria;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import ar.com.dailyMarket.charts.ColumnChart2D;
import ar.com.dailyMarket.charts.elements.SetElement;
import ar.com.dailyMarket.model.GroupProduct;
import ar.com.dailyMarket.model.HourlyBand;
import ar.com.dailyMarket.model.Product;
import ar.com.dailyMarket.model.ProductoVenta;
import ar.com.dailyMarket.model.SesionVenta;

public class EstadisticasService {

/******		VENTAS MENSUALES		******/
	public ColumnChart2D getVMChart(DynaActionForm form) {
		GregorianCalendar fechaPedida = new GregorianCalendar();
		fechaPedida.set(Integer.valueOf((String)form.get("year")), Integer.valueOf((String)form.get("month")), 1);
		
		GregorianCalendar cal = new GregorianCalendar();
		cal.set(fechaPedida.get(GregorianCalendar.YEAR), fechaPedida.get(GregorianCalendar.MONTH), 1);
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		
		LinkedList<SetElement> values = new LinkedList<SetElement>();
		while(fechaPedida.get(GregorianCalendar.MONTH) == cal.get(GregorianCalendar.MONTH)){
	      	SetElement el = new SetElement();
	      	
	      	el.setLabel(sdf.format(cal.getTime()));//DIA
	       	el.setValue(getVentasPorDia(cal, (Long) form.get("bandaHorariaId"), -1L, -1L));//getValue (banda horaria y fecha pedida)
	       	el.setShowLabel(1);
	      	
	       	values.add(el);
	      	cal.add(GregorianCalendar.DATE, 1);
	    }
        
		ColumnChart2D colChart2D = new ColumnChart2D(values);
		colChart2D.setCaption("Ventas en el mes de " + 
				fechaPedida.getDisplayName(GregorianCalendar.MONTH,GregorianCalendar.LONG, new Locale("es")).toUpperCase() + 
				" de " + (String)form.get("year"));
		colChart2D.setXAxisName("Días");
		colChart2D.setYAxisName("Ventas");
		colChart2D.setShowValues(0);
        return colChart2D;
	}
	
/******		VENTAS ANUALES		******/
	public ColumnChart2D getVAChart(DynaActionForm form) {
		GregorianCalendar fechaPedida = new GregorianCalendar();
		fechaPedida.set(Integer.valueOf((String)form.get("year")), 0, 1);
		
		GregorianCalendar cal = new GregorianCalendar();
		cal.set(fechaPedida.get(GregorianCalendar.YEAR), 0 , 1);
		
		LinkedList<SetElement> values = new LinkedList<SetElement>();
		while(fechaPedida.get(GregorianCalendar.YEAR) == cal.get(GregorianCalendar.YEAR)){
	      	SetElement el = new SetElement();
	      	
	       	el.setLabel(cal.getDisplayName(GregorianCalendar.MONTH,GregorianCalendar.LONG, new Locale("es")));
	       	el.setValue(getVentasPorMes(cal, (Long) form.get("bandaHorariaId"), -1L, -1L));//getValue(banda Horaria y fecha pedida)
	       	el.setShowLabel(1);
	       	
	      	values.add(el);
	      	cal.add(GregorianCalendar.MONTH, 1);
	    }
		
		ColumnChart2D colChart2D = new ColumnChart2D(values);
		colChart2D.setCaption("Ventas del año " + fechaPedida.get(GregorianCalendar.YEAR));
		colChart2D.setXAxisName("Meses");
		colChart2D.setYAxisName("Ventas");
		colChart2D.setShowValues(0);
		return colChart2D;
	}

	
/******		VENTAS MENSUALES POR PRODUCTO		******/
	public ColumnChart2D getVMPPChart(DynaActionForm form) {
		ProductService ps = new ProductService();
		Product producto = ps.getProductByPK((Long)form.get("productId"));

		GregorianCalendar fechaPedida = new GregorianCalendar();
		fechaPedida.set(Integer.valueOf((String)form.get("year")), Integer.valueOf((String)form.get("month")), 1);
		
		GregorianCalendar cal = new GregorianCalendar();
		cal.set(fechaPedida.get(GregorianCalendar.YEAR), fechaPedida.get(GregorianCalendar.MONTH), 1);
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		
		LinkedList<SetElement> values = new LinkedList<SetElement>();
		while(fechaPedida.get(GregorianCalendar.MONTH) == cal.get(GregorianCalendar.MONTH)){
	      	SetElement el = new SetElement();
	      	
	      	el.setLabel(sdf.format(cal.getTime()));//DIAS
	       	el.setValue(getVentasPorDia(cal, (Long) form.get("bandaHorariaId"), producto.getId(), -1L));//getValue (banda horaria y fecha pedida y producto)
	       	el.setShowLabel(1);
	      	
	       	values.add(el);
	      	cal.add(GregorianCalendar.DATE, 1);
	    }
        
		ColumnChart2D colChart2D = new ColumnChart2D(values);
		
		String title = "Ventas en el mes de " + 
			fechaPedida.getDisplayName(GregorianCalendar.MONTH,GregorianCalendar.LONG, new Locale("es")).toUpperCase() + 
			" de " + (String)form.get("year") + "\n" + "PRODUCTO: " + producto.getName();
		colChart2D.setCaption(title);
		
		colChart2D.setXAxisName("Días");
		colChart2D.setYAxisName("Ventas");
		colChart2D.setShowValues(0);
        return colChart2D;
	}
	
/******		VENTAS ANUALES POR PRODUCTO			******/
	public ColumnChart2D getVAPPChart(DynaActionForm form) {
		ProductService ps = new ProductService();
		Product producto = ps.getProductByPK((Long)form.get("productId"));
		
		GregorianCalendar fechaPedida = new GregorianCalendar();
		fechaPedida.set(Integer.valueOf((String)form.get("year")), 0, 1);
		
		GregorianCalendar cal = new GregorianCalendar();
		cal.set(fechaPedida.get(GregorianCalendar.YEAR), 0 , 1);
		
		LinkedList<SetElement> values = new LinkedList<SetElement>();
		while(fechaPedida.get(GregorianCalendar.YEAR) == cal.get(GregorianCalendar.YEAR)){
	      	SetElement el = new SetElement();
	      	
	       	el.setLabel(cal.getDisplayName(GregorianCalendar.MONTH,GregorianCalendar.LONG, new Locale("es")));
	       	el.setValue(getVentasPorMes(cal, (Long) form.get("bandaHorariaId"), producto.getId(), -1L));//getValue(banda Horaria y fecha pedida y producto)
	       	el.setShowLabel(1);
	       	
	      	values.add(el);
	      	cal.add(GregorianCalendar.MONTH, 1);
	    }
		
		ColumnChart2D colChart2D = new ColumnChart2D(values);
		colChart2D.setCaption("Ventas del año " + fechaPedida.get(GregorianCalendar.YEAR) + "\nPRODUCTO: " + producto.getName());
		colChart2D.setXAxisName("Meses");
		colChart2D.setYAxisName("Ventas");
		colChart2D.setShowValues(0);
		return colChart2D;
	}
	
/******		VENTAS MENSUALES POR GRUPO DE PRODUCTOS		******/
	public ColumnChart2D getVMPGPChart(DynaActionForm form) {
		GroupProductService gps = new GroupProductService();
		GroupProduct grupoProducto = gps.getGroupProductByPK((Long)form.get("groupProductId"));
		
		GregorianCalendar fechaPedida = new GregorianCalendar();
		fechaPedida.set(Integer.valueOf((String)form.get("year")), Integer.valueOf((String)form.get("month")), 1);
		
		GregorianCalendar cal = new GregorianCalendar();
		cal.set(fechaPedida.get(GregorianCalendar.YEAR), fechaPedida.get(GregorianCalendar.MONTH), 1);
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		
		LinkedList<SetElement> values = new LinkedList<SetElement>();
		while(fechaPedida.get(GregorianCalendar.MONTH) == cal.get(GregorianCalendar.MONTH)){
	      	SetElement el = new SetElement();
	      	
	      	el.setLabel(sdf.format(cal.getTime()));//DIAS
	       	el.setValue(getVentasPorDia(cal, (Long) form.get("bandaHorariaId"), -1L, grupoProducto.getId()));//getValue (banda horaria y fecha pedida y grupo de producto)
	       	el.setShowLabel(1);
	      	
	       	values.add(el);
	      	cal.add(GregorianCalendar.DATE, 1);
	    }
        
		ColumnChart2D colChart2D = new ColumnChart2D(values);
		
		String title = "Ventas en el mes de " + 
		fechaPedida.getDisplayName(GregorianCalendar.MONTH,GregorianCalendar.LONG, new Locale("es")).toUpperCase() + 
			" de " + (String)form.get("year") + "\n" + "GRUPO DE PRODUCTO: " + grupoProducto.getName();
		colChart2D.setCaption(title);

		colChart2D.setXAxisName("Días");
		colChart2D.setYAxisName("Ventas");
		colChart2D.setShowValues(0);
        return colChart2D;
	}
	
/******		VENTAS ANUALES POR GRUPO DE PRODUCTOS			******/
	public ColumnChart2D getVAPGPChart(DynaActionForm form) {
		GroupProductService gps = new GroupProductService();
		GroupProduct grupoProducto = gps.getGroupProductByPK((Long)form.get("groupProductId"));
		
		GregorianCalendar fechaPedida = new GregorianCalendar();
		fechaPedida.set(Integer.valueOf((String)form.get("year")), 0, 1);
		
		GregorianCalendar cal = new GregorianCalendar();
		cal.set(fechaPedida.get(GregorianCalendar.YEAR), 0 , 1);
		
		LinkedList<SetElement> values = new LinkedList<SetElement>();
		while(fechaPedida.get(GregorianCalendar.YEAR) == cal.get(GregorianCalendar.YEAR)){
	      	SetElement el = new SetElement();
	      	
	       	el.setLabel(cal.getDisplayName(GregorianCalendar.MONTH,GregorianCalendar.LONG, new Locale("es")));
	       	el.setValue(getVentasPorMes(cal, (Long) form.get("bandaHorariaId"), -1L, grupoProducto.getId()));//getValue(banda Horaria y fecha pedida y grupo de producto)
	       	el.setShowLabel(1);
	       	
	      	values.add(el);
	      	cal.add(GregorianCalendar.MONTH, 1);
	    }
		
		ColumnChart2D colChart2D = new ColumnChart2D(values);
		colChart2D.setCaption("Ventas del año " + fechaPedida.get(GregorianCalendar.YEAR)+ 
				"\n" + "GRUPO DE PRODUCTO: " + grupoProducto.getName());
		colChart2D.setXAxisName("Meses");
		colChart2D.setYAxisName("Ventas");
		colChart2D.setShowValues(0);
		return colChart2D;
	}
	
	/**
	 * TODO ver banda horaria minutos y segundos, 
	 * Metodos para obtener values 
	 */
	
	@SuppressWarnings("unchecked")
	private String getVentasPorDia(GregorianCalendar dia, Long bandaId, Long productoId, Long grupoProductoId) {
		Transaction tx = null;
		List<SesionVenta> ventas = new ArrayList<SesionVenta>();
		try {
			tx = HibernateHelper.currentSession().beginTransaction();
			
			Criteria productoVentaCriteria = HibernateHelper.currentSession().createCriteria(ProductoVenta.class);
			GregorianCalendar from;
			GregorianCalendar to;
			
			if (bandaId > 0) {
				HourlyBand banda = (HourlyBand)HibernateHelper.currentSession().load(HourlyBand.class, bandaId);
				from = new GregorianCalendar(dia.get(GregorianCalendar.YEAR), dia.get(GregorianCalendar.MONTH),	
											dia.get(GregorianCalendar.DAY_OF_MONTH), Integer.valueOf(banda.getInitBand()), 0, 0);
				to = new GregorianCalendar(dia.get(GregorianCalendar.YEAR), dia.get(GregorianCalendar.MONTH), 
											dia.get(GregorianCalendar.DAY_OF_MONTH), Integer.valueOf(banda.getEndBand()), 0, 0);
			} else {
				from = new GregorianCalendar(dia.get(GregorianCalendar.YEAR), dia.get(GregorianCalendar.MONTH), 
											dia.get(GregorianCalendar.DAY_OF_MONTH), 0,	0, 0);
				to = new GregorianCalendar(dia.get(GregorianCalendar.YEAR), dia.get(GregorianCalendar.MONTH),
											dia.get(GregorianCalendar.DAY_OF_MONTH), 23, 59, 59);
			}
			
			Criteria sesionCriteria = productoVentaCriteria.createCriteria("sesionVenta");
			sesionCriteria.add(Restrictions.between("fechaInicio", from.getTime(), to.getTime()));
			
			if (productoId > 0) {
				productoVentaCriteria.createCriteria("producto").add(Restrictions.eq("id", productoId));
			} else if (grupoProductoId > 0) {
				productoVentaCriteria.createCriteria("producto").createCriteria("groupProduct").add(Restrictions.eq("id", grupoProductoId));
			}
			
			productoVentaCriteria.setProjection(Projections.distinct(Projections.property("sesionVenta")));
			
			ventas = productoVentaCriteria.list();
			
			tx.commit();
		}
		catch (RuntimeException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
			return "0";
		}
		finally {
			tx = null;
		}
		return new Integer(ventas.size()).toString();
	}
	
	@SuppressWarnings("unchecked")
	private String getVentasPorMes(GregorianCalendar mes, Long bandaId, Long productoId, Long grupoProductoId) {
		Transaction tx = null;
		List<SesionVenta> ventas = new ArrayList<SesionVenta>();
		try {
			tx = HibernateHelper.currentSession().beginTransaction();
			
			Criteria productoVentaCriteria = HibernateHelper.currentSession().createCriteria(ProductoVenta.class);
			GregorianCalendar from;
			GregorianCalendar to;
			
			if (bandaId > 0) {
				HourlyBand banda = (HourlyBand) HibernateHelper.currentSession().load(HourlyBand.class, bandaId);
				from = new GregorianCalendar(mes.get(GregorianCalendar.YEAR), mes.get(GregorianCalendar.MONTH),	
											1, Integer.valueOf(banda.getInitBand()), 0, 0);
				to = new GregorianCalendar(mes.get(GregorianCalendar.YEAR), mes.get(GregorianCalendar.MONTH), 
											mes.getActualMaximum(GregorianCalendar.DAY_OF_MONTH), Integer.valueOf(banda.getEndBand()), 0, 0);
			} else {
				from = new GregorianCalendar(mes.get(GregorianCalendar.YEAR), mes.get(GregorianCalendar.MONTH), 
											1, 0,	0, 0);
				to = new GregorianCalendar(mes.get(GregorianCalendar.YEAR), mes.get(GregorianCalendar.MONTH),
											mes.getActualMaximum(GregorianCalendar.DAY_OF_MONTH), 23, 59, 59);
			}
			
			Criteria sesionCriteria = productoVentaCriteria.createCriteria("sesionVenta");
			sesionCriteria.add(Restrictions.between("fechaInicio", from.getTime(), to.getTime()));
			
			if (productoId > 0) {
				productoVentaCriteria.createCriteria("producto").add(Restrictions.eq("id", productoId));
			} else if (grupoProductoId > 0) {
				productoVentaCriteria.createCriteria("producto").createCriteria("groupProduct").add(Restrictions.eq("id", grupoProductoId));
			}
			
			productoVentaCriteria.setProjection(Projections.distinct(Projections.property("sesionVenta")));
			
			ventas = productoVentaCriteria.list();
			
			tx.commit();
		}
		catch (RuntimeException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
			return "0";
		}
		finally {
			tx = null;
		}
		return new Integer(ventas.size()).toString();
	}
}
