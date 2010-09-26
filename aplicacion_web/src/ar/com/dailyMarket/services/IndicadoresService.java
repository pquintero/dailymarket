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

public class IndicadoresService {
	
/******		VentasPorCajeroMensual		******/
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
	
/******		VentasPorCajeroAnual		******/
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
	
/******		ComparativoDeVentasPorCajeroMensual		******/
public MSLine getCVPCMChart(DynaActionForm form) {
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
	for(Integer i = new Integer(1); i<=2;i++) {
		DatasetElement curva = new DatasetElement();
		curva.setSeriesname("Cajero_"+i.toString());
		curva.setShowValues(0);
		
		/** Obtener los puntos de un cajero, debe tener puntos sin valor si no tiene ventas un dia, la curva debe tener siempre tantos puntos como 
		dias el mes **/
		LinkedList<Lines> puntos = new LinkedList<Lines>();
		for (Integer aux = 1; aux <= dias; aux++) {
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
public MSLine getCVPCAChart(DynaActionForm form) {
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
	for(Integer i = new Integer(1); i<=2;i++) {
		DatasetElement curva = new DatasetElement();
		curva.setSeriesname("Cajero_"+i.toString());
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