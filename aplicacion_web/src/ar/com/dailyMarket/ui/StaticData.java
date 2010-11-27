package ar.com.dailyMarket.ui;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

import ar.com.dailyMarket.model.SesionVenta;
import ar.com.dailyMarket.services.HibernateHelper;

public class StaticData {
	
	public static ArrayList<String> meses = new ArrayList<String>();
	static {
		meses.add("Enero");
		meses.add("Febrero");
		meses.add("Marzo");
		meses.add("Abril");
		meses.add("Mayo");
		meses.add("Junio");
		meses.add("Julio");
		meses.add("Agosto");
		meses.add("Septiembre");
		meses.add("Octubre");
		meses.add("Noviembre");
		meses.add("Diciembre");
	}
	
	public static ArrayList<String> anios = new ArrayList<String>();
	static {
		Transaction tx = null;
		List<SesionVenta> sesiones = new ArrayList<SesionVenta>();
		try {
			tx = HibernateHelper.currentSession().beginTransaction();
			
			Criteria c = HibernateHelper.currentSession().createCriteria(SesionVenta.class).addOrder(Order.asc("fechaInicio"));
			sesiones = c.list();
			
			SesionVenta first = sesiones.get(0);
			SesionVenta last = sesiones.get(sesiones.size() - 1);
			
			GregorianCalendar aux = new GregorianCalendar();
			aux.setTime(last.getFechaInicio());
			
			GregorianCalendar firstCal = new GregorianCalendar();
			firstCal.setTime(first.getFechaInicio());
			
			while (aux.get(GregorianCalendar.YEAR) >= firstCal.get(GregorianCalendar.YEAR)) {
				anios.add(new Integer(aux.get(GregorianCalendar.YEAR)).toString());
				aux.add(GregorianCalendar.YEAR, -1);
			}
			
			tx.commit();
		} catch (Exception e) {
			anios = new ArrayList<String>();
			anios.add("2010");
			anios.add("2009");
			anios.add("2008");
		}
	}
}
