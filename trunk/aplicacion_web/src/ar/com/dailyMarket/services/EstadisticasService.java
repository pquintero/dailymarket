package ar.com.dailyMarket.services;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Random;

import org.apache.struts.action.DynaActionForm;

import ar.com.dailyMarket.charts.ColumnChart2D;
import ar.com.dailyMarket.charts.elements.SetElement;
import ar.com.dailyMarket.model.GroupProduct;
import ar.com.dailyMarket.model.Product;

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
	      	
	      	el.setLabel(sdf.format(cal.getTime()));//DIAS
	      	//getValue (banda horaria y fecha pedida)
	       	el.setValue(new Double(new Random().nextDouble() * 50).toString());
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
	       	el.setValue(new Double(new Random().nextDouble() * 50).toString());
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
	       	el.setValue(new Double(new Random().nextDouble() * 50).toString());
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
	
}
