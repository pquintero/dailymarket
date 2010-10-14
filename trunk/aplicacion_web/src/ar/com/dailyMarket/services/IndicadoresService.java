package ar.com.dailyMarket.services;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.apache.struts.action.DynaActionForm;

import ar.com.dailyMarket.charts.LineChart;
import ar.com.dailyMarket.charts.MSLine;
import ar.com.dailyMarket.charts.elements.CategoryElement;
import ar.com.dailyMarket.charts.elements.DatasetElement;
import ar.com.dailyMarket.charts.elements.Lines;
import ar.com.dailyMarket.charts.elements.SetElement;
import ar.com.dailyMarket.model.User;

public class IndicadoresService {
	
/******		VentasPorCajeroMensual		******/
	public LineChart getVPCMChart(DynaActionForm form) {
		UserService us = new UserService();
		User cajero = us.getUserByPK((Long)form.get("cajeroId"));
		
		GregorianCalendar fechaPedida = new GregorianCalendar();
		fechaPedida.set(Integer.valueOf((String)form.get("year")), Integer.valueOf((String)form.get("month")), 1);
		
		GregorianCalendar cal = new GregorianCalendar();
		cal.set(fechaPedida.get(GregorianCalendar.YEAR), fechaPedida.get(GregorianCalendar.MONTH), 1);
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		
		List values = new ArrayList();
		while(fechaPedida.get(GregorianCalendar.MONTH) == cal.get(GregorianCalendar.MONTH)){
	      	SetElement el = new SetElement();
	       	el.setLabel(sdf.format(cal.getTime()));//DIAS
	      	//getValueByEmployeeByParameters (cajero y banda horaria y fecha pedida)
	       	el.setValue(new Double(new Random().nextDouble() * 50).toString());
	      	values.add(el);
	      	cal.add(GregorianCalendar.DATE, 1);
	    }
		
		LineChart line = new LineChart(values);
		line.setCaption("Ventas De " + cajero.getCompleteName() + ". En el mes de" + fechaPedida.getDisplayName(GregorianCalendar.MONTH,GregorianCalendar.LONG, new Locale("es")).toUpperCase());
		line.setXAxisName("Días");
		line.setYAxisName("Ventas");
		line.setNumberPrefix("");
		line.setNumberSuffix("");
		line.setShowValues(0);
		
		return line;
	}
	
/******		VentasPorCajeroAnual		******/
	public LineChart getVPCAChart(DynaActionForm form) {
		UserService us = new UserService();
		User cajero = us.getUserByPK((Long)form.get("cajeroId"));
		
		GregorianCalendar fechaPedida = new GregorianCalendar();
		fechaPedida.set(Integer.valueOf((String)form.get("year")), 0, 1);
		
		GregorianCalendar cal = new GregorianCalendar();
		cal.set(fechaPedida.get(GregorianCalendar.YEAR), 0 , 1);
		SimpleDateFormat sdf = new SimpleDateFormat("MM");
		
		List values = new ArrayList();
		while(fechaPedida.get(GregorianCalendar.YEAR) == cal.get(GregorianCalendar.YEAR)){
	      	SetElement el = new SetElement();
	       	el.setLabel(cal.getDisplayName(GregorianCalendar.MONTH,GregorianCalendar.LONG, new Locale("es")));
	      	//getValueByEmployeeByParameters(con el cajero y si tiene banda Horaria y fecha pedida)
	       	el.setValue(new Double(new Random().nextDouble() * 50).toString());
	      	values.add(el);
	      	cal.add(GregorianCalendar.MONTH, 1);
	    }
		
		LineChart line = new LineChart(values);
		line.setCaption("Ventas De " + cajero.getCompleteName() + ". En el año " + fechaPedida.get(GregorianCalendar.YEAR));
		line.setXAxisName("Meses");
		line.setYAxisName("Ventas");
		line.setNumberPrefix("");
		line.setNumberSuffix("");
		line.setShowValues(0);
		
		return line;
	}
	
/******		ComparativoDeVentasPorCajeroMensual		******/
	public MSLine getCVPCMChart(DynaActionForm form, String[] cajeros) {
		UserService us = new UserService();
		
	    List values = new ArrayList();
	    List subValues = new ArrayList();
	    
	    GregorianCalendar hoy = new GregorianCalendar();
		hoy.setTime(new Date());//ESTO VIENE DEL FORM mes, año
		
		GregorianCalendar cal = new GregorianCalendar();
		cal.set(hoy.get(GregorianCalendar.YEAR), hoy.get(GregorianCalendar.MONTH), 1);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		Integer dias = 0;
		
	   	//Se ponen los distintos puntos del eje X
	   	
	    while(hoy.get(GregorianCalendar.MONTH) == cal.get(GregorianCalendar.MONTH)) {
			CategoryElement ce = new CategoryElement(sdf.format(cal.getTime()));
			subValues.add(ce);
			cal.add(GregorianCalendar.DATE, 1);
			dias++;
	    }
		values.add(subValues);
		
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(0);
		for(Integer i = 0; i < cajeros.length; i++) {
			User cajero = us.getUserByPK(Long.valueOf(cajeros[i]));
			
			DatasetElement curva = new DatasetElement();
			curva.setSeriesname(cajero.getCompleteName());
			curva.setShowValues(0);
			
			/** Obtener los puntos de un cajero, debe tener puntos sin valor si no tiene ventas un dia, la curva debe tener siempre tantos puntos como 
			dias el mes **/
			LinkedList<Lines> puntos = new LinkedList<Lines>();
			for (Integer aux = 1; aux <= dias; aux++) {
		      	SetElement el = new SetElement();
		      	//getValueByEmployeeByParameters ()
	       		el.setValue(nf.format(new Double(new Random().nextDouble() * 50)));
		       	
		       	puntos.add(el);
		    }
			curva.setSets(puntos);
			values.add(curva);
		}
		
		
	    MSLine mSLine = new MSLine(values);
	    mSLine.setPalette(3);
	    
	    mSLine.setCaption("Comparativa de Ventas Por Cajero en el mes de " + (hoy.getDisplayName(GregorianCalendar.MONTH,GregorianCalendar.LONG, new Locale("es"))).toUpperCase());
	    mSLine.setShowLabels(1);
	    mSLine.setYAxisMinValue(0d);
	    mSLine.setShowValues(0);
	    mSLine.setDecimals(0);
	    mSLine.setRotateLabels(0);
	    mSLine.setXAxisName("Dias");
	    mSLine.setYAxisName("Ventas");
	    mSLine.setNumberPrefix("");
	    mSLine.setNumberSuffix("");
	    return mSLine;
	}
		
/******		ComparativoDeVentasPorCajeroAnual		******/
	public MSLine getCVPCAChart(DynaActionForm form, String[] cajeros) {
		UserService us = new UserService();
		
		List values = new ArrayList();
	    List subValues = new ArrayList();
	    
	    GregorianCalendar hoy = new GregorianCalendar();
		hoy.setTime(new Date());//ESTO VIENE DEL FORM año
		
		GregorianCalendar cal = new GregorianCalendar();
		cal.set(hoy.get(GregorianCalendar.YEAR), 0 , 1);
		SimpleDateFormat sdf = new SimpleDateFormat("MM");
		Integer meses = 0;
		
	   	//Se ponen los distintos puntos del eje X
	   	
	    while(hoy.get(GregorianCalendar.YEAR) == cal.get(GregorianCalendar.YEAR)) {
			CategoryElement ce = new CategoryElement(cal.getDisplayName(GregorianCalendar.MONTH,GregorianCalendar.LONG, new Locale("es")));
			subValues.add(ce);
			cal.add(GregorianCalendar.MONTH, 1);
			meses++;
	    }
		values.add(subValues);
		
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(0);
		for(Integer i = 0; i< cajeros.length; i++) {
			User cajero = us.getUserByPK(Long.valueOf(cajeros[i]));
			
			DatasetElement curva = new DatasetElement();
			curva.setSeriesname(cajero.getCompleteName());
			curva.setShowValues(0);
			
			/** Obtener los puntos de un cajero, debe tener puntos sin valor si no tiene ventas un mes, la curva debe tener siempre 12 puntos **/
			LinkedList<Lines> puntos = new LinkedList<Lines>();
			for (Integer aux = 1; aux <= meses; aux++) {
		      	SetElement el = new SetElement();
		      	//getValueByEmployeeByParameters
	       		el.setValue(nf.format(new Double(new Random().nextDouble() * 50)));
		       	
		       	puntos.add(el);
		    }
			curva.setSets(puntos);
			values.add(curva);
		}
		
	    MSLine mSLine = new MSLine(values);
	    mSLine.setPalette(3);
	    
	    mSLine.setCaption("Comparativa de Ventas Por Cajero del año " + hoy.get(GregorianCalendar.YEAR));
	    mSLine.setShowLabels(1);
	    mSLine.setYAxisMinValue(0d);
	    mSLine.setShowValues(0);
	    mSLine.setDecimals(0);
	    mSLine.setRotateLabels(0);
	    mSLine.setXAxisName("Meses");
	    mSLine.setYAxisName("Ventas");
	    mSLine.setNumberPrefix("");
	    mSLine.setNumberSuffix("");
	    return mSLine;
	}
	
	
}