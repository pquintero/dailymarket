package ar.com.dailyMarket.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.apache.struts.action.DynaActionForm;
import org.hibernate.Criteria;
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
	      	//getValue (banda horaria y fecha pedida)
	       	el.setValue(getVentasPorDia(cal, (Long) form.get("bandaHorariaId"), -1L, -1L));
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
	      	//getValue(banda Horaria y fecha pedida)
	       	el.setValue(new Double(new Random().nextDouble() * 50).toString());
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
	      	//getValue (banda horaria y fecha pedida y producto)
	       	el.setValue(getVentasPorDia(cal, (Long) form.get("bandaHorariaId"), producto.getId(), -1L));
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
	      	//getValue(banda Horaria y fecha pedida y producto)
	       	el.setValue(new Double(new Random().nextDouble() * 50).toString());
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
	      	//getValue (banda horaria y fecha pedida y grupo de producto)
	       	el.setValue(getVentasPorDia(cal, (Long) form.get("bandaHorariaId"), -1L, grupoProducto.getId()));
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
	      	//getValue(banda Horaria y fecha pedida y grupo de producto)
	       	el.setValue(new Double(new Random().nextDouble() * 50).toString());
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
		HourlyBandService bandServ = new HourlyBandService();
		
		Criteria productoVentaCriteria = HibernateHelper.currentSession().createCriteria(ProductoVenta.class);
		
		GregorianCalendar from;
		GregorianCalendar to;
		
		if (bandaId > 0) {
			HourlyBand banda = bandServ.getHourlyBandByPK(bandaId);
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
		
		List<SesionVenta> ventas = productoVentaCriteria.list();
		
		return new Integer(ventas.size()).toString();
	}
	
	private String getVentasPorMes(GregorianCalendar mes, Long bandaId, Long productoId, Long grupoProductoId) {
		HourlyBandService bandServ = new HourlyBandService();
		
		Criteria ventas = HibernateHelper.currentSession().createCriteria(SesionVenta.class);
		
		GregorianCalendar from;
		GregorianCalendar to;
		
		if (bandaId > 0) {
			HourlyBand banda = bandServ.getHourlyBandByPK(bandaId);
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
		
		ventas.add(Restrictions.between("fechaInicio", from.getTime(), to.getTime()));
		List<SesionVenta> sesiones = ventas.list();
		
		if (sesiones.isEmpty())
			return "0.00";
		
		Double total = 0D;
		for (SesionVenta sesionVenta : sesiones) {
			total += sesionVenta.getTotalVenta() != null ? sesionVenta.getTotalVenta() : 0D;
		}
		
		return new BigDecimal(total).setScale(2, RoundingMode.HALF_EVEN).toString();
	}
}
