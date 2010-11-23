package ar.com.dailyMarket.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.apache.struts.action.DynaActionForm;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import ar.com.dailyMarket.charts.LineChart;
import ar.com.dailyMarket.charts.MSLine;
import ar.com.dailyMarket.charts.elements.CategoryElement;
import ar.com.dailyMarket.charts.elements.DatasetElement;
import ar.com.dailyMarket.charts.elements.Lines;
import ar.com.dailyMarket.charts.elements.SetElement;
import ar.com.dailyMarket.model.HourlyBand;
import ar.com.dailyMarket.model.SesionVenta;
import ar.com.dailyMarket.model.User;

public class IndicadoresService {
	
	/**
	 * TODO ventas por cajero yo lo tome como ventas en dinero, no en catidad de operaciones de venta ver cual es la correcta
	 */
	
/******		VentasPorCajeroMensual		******/
	public LineChart getVPCMChart(DynaActionForm form) {
		UserService us = new UserService();
		User cajero = us.getUserByPK((Long)form.get("cajeroId"));
		
		GregorianCalendar fechaPedida = new GregorianCalendar();
		fechaPedida.set(Integer.valueOf((String)form.get("year")), Integer.valueOf((String)form.get("month")), 1);
		
		GregorianCalendar cal = new GregorianCalendar();
		cal.set(fechaPedida.get(GregorianCalendar.YEAR), fechaPedida.get(GregorianCalendar.MONTH), 1);
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		
		List<SetElement> values = new ArrayList<SetElement>();
		while(fechaPedida.get(GregorianCalendar.MONTH) == cal.get(GregorianCalendar.MONTH)){
	      	SetElement el = new SetElement();
	       	
	      	el.setLabel(sdf.format(cal.getTime()));//DIA
	       	el.setValue(getVentasPorCajeroPorFechaPorDia(cajero, cal, (Long) form.get("bandaHorariaId")));
	      	
	       	values.add(el);
	      	cal.add(GregorianCalendar.DATE, 1);
	    }
		
		LineChart line = new LineChart(values);
		line.setCaption("Ventas De " + cajero.getCompleteName() + 
			". En el mes de " + fechaPedida.getDisplayName(GregorianCalendar.MONTH,GregorianCalendar.LONG, new Locale("es")).toUpperCase() + 
			" de " + fechaPedida.get(GregorianCalendar.YEAR));
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
		
		List<SetElement> values = new ArrayList<SetElement>();
		while(fechaPedida.get(GregorianCalendar.YEAR) == cal.get(GregorianCalendar.YEAR)){
	      	SetElement el = new SetElement();
	       	
	      	el.setLabel(cal.getDisplayName(GregorianCalendar.MONTH,GregorianCalendar.LONG, new Locale("es")));
	       	el.setValue(getVentasPorCajeroPorFechaPorMes(cajero, cal, (Long) form.get("bandaHorariaId")));
	      	
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public MSLine getCVPCMChart(DynaActionForm form, String[] cajeros) {
		UserService us = new UserService();
		
	    List values = new ArrayList();
	    List<CategoryElement> subValues = new ArrayList<CategoryElement>();
	    
		GregorianCalendar fechaPedida = new GregorianCalendar();
		fechaPedida.set(Integer.valueOf((String)form.get("year")), Integer.valueOf((String)form.get("month")), 1);
		
		GregorianCalendar cal = new GregorianCalendar();
		cal.set(fechaPedida.get(GregorianCalendar.YEAR), fechaPedida.get(GregorianCalendar.MONTH), 1);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		Integer dias = 0;
		
	   	//Se ponen los distintos puntos del eje X
	   	
	    while(fechaPedida.get(GregorianCalendar.MONTH) == cal.get(GregorianCalendar.MONTH)) {
			CategoryElement ce = new CategoryElement(sdf.format(cal.getTime()));
			subValues.add(ce);
			cal.add(GregorianCalendar.DATE, 1);
			dias++;
	    }
		values.add(subValues);
		
		for(Integer i = 0; i < cajeros.length; i++) {
			User cajero = us.getUserByPK(Long.valueOf(cajeros[i]));
			
			DatasetElement curva = new DatasetElement();
			curva.setSeriesname(cajero.getCompleteName());
			curva.setShowValues(0);
			
			LinkedList<Lines> puntos = new LinkedList<Lines>();
			for (Integer aux = 1; aux <= dias; aux++) {
		      	SetElement el = new SetElement();
	       		
		      	cal.set(fechaPedida.get(GregorianCalendar.YEAR), fechaPedida.get(GregorianCalendar.MONTH), aux);
		      	el.setValue(getVentasPorCajeroPorFechaPorDia(cajero, cal, (Long) form.get("bandaHorariaId")));
		       	
		       	puntos.add(el);
		    }
			curva.setSets(puntos);
			values.add(curva);
		}
		
		
	    MSLine mSLine = new MSLine(values);
	    mSLine.setPalette(3);
	    
	    mSLine.setCaption("Comparativa de Ventas Por Cajero en el mes de " + 
	    		(fechaPedida.getDisplayName(GregorianCalendar.MONTH,GregorianCalendar.LONG, new Locale("es"))).toUpperCase() +
	    		" de " + fechaPedida.get(GregorianCalendar.YEAR));
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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public MSLine getCVPCAChart(DynaActionForm form, String[] cajeros) {
		UserService us = new UserService();
		
		List values = new ArrayList();
	    List<CategoryElement> subValues = new ArrayList<CategoryElement>();
	    
	    GregorianCalendar fechaPedida = new GregorianCalendar();
		fechaPedida.set(Integer.valueOf((String)form.get("year")), 0, 1);
		
		GregorianCalendar cal = new GregorianCalendar();
		cal.set(fechaPedida.get(GregorianCalendar.YEAR), 0 , 1);
		
		Integer meses = 0;
		
	   	//Se ponen los distintos puntos del eje X
	    while(fechaPedida.get(GregorianCalendar.YEAR) == cal.get(GregorianCalendar.YEAR)) {
			CategoryElement ce = new CategoryElement(cal.getDisplayName(GregorianCalendar.MONTH,GregorianCalendar.LONG, new Locale("es")));
			subValues.add(ce);
			cal.add(GregorianCalendar.MONTH, 1);
			meses++;
	    }
		values.add(subValues);
		
		for(Integer i = 0; i < cajeros.length; i++) {
			User cajero = us.getUserByPK(Long.valueOf(cajeros[i]));
			
			DatasetElement curva = new DatasetElement();
			curva.setSeriesname(cajero.getCompleteName());
			curva.setShowValues(0);
			
			/** Obtener los puntos de un cajero, debe tener puntos sin valor si no tiene ventas un mes, la curva debe tener siempre 12 puntos **/
			LinkedList<Lines> puntos = new LinkedList<Lines>();
			for (Integer aux = 0; aux < meses; aux++) {
		      	SetElement el = new SetElement();
		      	
		      	cal.set(fechaPedida.get(GregorianCalendar.YEAR), aux , 1);
	       		el.setValue(getVentasPorCajeroPorFechaPorMes(cajero, cal, (Long) form.get("bandaHorariaId")));
		       	
		       	puntos.add(el);
		    }
			curva.setSets(puntos);
			values.add(curva);
		}
		
	    MSLine mSLine = new MSLine(values);
	    mSLine.setPalette(3);
	    
	    mSLine.setCaption("Comparativa de Ventas Por Cajero del año " + fechaPedida.get(GregorianCalendar.YEAR));
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
	
	/**
	 * TODO ver banda horaria minutos y segundos
	 * Metodos para obtener values 
	 */
	
	@SuppressWarnings("unchecked")
	private String getVentasPorCajeroPorFechaPorDia(User cajero, GregorianCalendar dia, Long bandaId) {
		HourlyBandService bandServ = new HourlyBandService();
		
		Criteria ventas = HibernateHelper.currentSession().createCriteria(SesionVenta.class);
		ventas.createCriteria("cajero").add(Restrictions.eq("id", cajero.getId()));
		
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
	
	@SuppressWarnings("unchecked")
	private String getVentasPorCajeroPorFechaPorMes(User cajero, GregorianCalendar mes, Long bandaId) {
		HourlyBandService bandServ = new HourlyBandService();
		
		Criteria ventas = HibernateHelper.currentSession().createCriteria(SesionVenta.class);
		ventas.createCriteria("cajero").add(Restrictions.eq("id", cajero.getId()));
		
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