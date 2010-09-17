package ar.com.dailyMarket.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.apache.struts.action.DynaActionForm;

import ar.com.dailyMarket.charts.LineChart;
import ar.com.dailyMarket.charts.elements.SetElement;

public class IndicadoresService {
	/*		VentasPorCajeroMensual		*/
	public LineChart getVPCMChart(DynaActionForm form) {
		GregorianCalendar hoy = new GregorianCalendar();
		hoy.setTime(new Date());//ESTO VIENE DEL FORM mes
		
		GregorianCalendar cal = new GregorianCalendar();
		cal.set(hoy.get(GregorianCalendar.YEAR), hoy.get(GregorianCalendar.MONTH), 1);
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		
		List values = new ArrayList();
		while(hoy.get(GregorianCalendar.MONTH) == cal.get(GregorianCalendar.MONTH)){
	      	SetElement el = new SetElement();
	       	el.setLabel(sdf.format(cal.getTime()));//DIAS
	      	//getValueByEmployeeByParameters
	       	el.setValue(new Double(new Random().nextDouble() * 50).toString());
	      	values.add(el);
	      	cal.add(GregorianCalendar.DATE, 1);
	    }
		
		LineChart line = new LineChart(values);
		line.setCaption("Ventas De XXX EMPLEADO, En el mes " + hoy.getDisplayName(GregorianCalendar.MONTH,GregorianCalendar.LONG, new Locale("es")));
		line.setXAxisName("Días");
		line.setYAxisName("Ventas");
		line.setNumberPrefix("");
		line.setNumberSuffix("");
		line.setShowValues(0);
		
		return line;
	}
	
	/*		VentasPorCajeroAnual		*/
	public LineChart getVPCAChart(DynaActionForm form) {
		GregorianCalendar hoy = new GregorianCalendar();
		hoy.setTime(new Date());//ESTO VIENE DEL FORM año
		
		GregorianCalendar cal = new GregorianCalendar();
		cal.set(hoy.get(GregorianCalendar.YEAR), 0 , 1);
		SimpleDateFormat sdf = new SimpleDateFormat("MM");
		
		List values = new ArrayList();
		while(hoy.get(GregorianCalendar.YEAR) == cal.get(GregorianCalendar.YEAR)){
	      	SetElement el = new SetElement();
	       	el.setLabel(cal.getDisplayName(GregorianCalendar.MONTH,GregorianCalendar.LONG, new Locale("es")));
	      	//getValueByEmployeeByParameters
	       	el.setValue(new Double(new Random().nextDouble() * 50).toString());
	      	values.add(el);
	      	cal.add(GregorianCalendar.MONTH, 1);
	    }
		
		LineChart line = new LineChart(values);
		line.setCaption("Ventas De XXX EMPLEADO, En el año " + hoy.get(GregorianCalendar.YEAR));
		line.setXAxisName("Meses");
		line.setYAxisName("Ventas");
		line.setNumberPrefix("");
		line.setNumberSuffix("");
		line.setShowValues(0);
		
		return line;
	}
	
	
}