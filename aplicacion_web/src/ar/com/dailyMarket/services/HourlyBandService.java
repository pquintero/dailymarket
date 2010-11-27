package ar.com.dailyMarket.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.DynaActionForm;
import org.hibernate.Criteria;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import ar.com.dailyMarket.model.HourlyBand;

public class HourlyBandService {
	
	public void copyProperties(Object obj, DynaActionForm form) {			
		HourlyBand hourlyBand = (HourlyBand)obj;
		hourlyBand.setName((String)form.get("name"));
		hourlyBand.setDescription((String)form.get("description"));
		hourlyBand.setInitBand((Integer)form.get("initBand"));
		hourlyBand.setEndBand((Integer)form.get("endBand"));
		hourlyBand.setActive(new Boolean(true));
	}
	
	public void save (ActionForm form) {
		Transaction tx = null;
		try {
			tx = HibernateHelper.currentSession().beginTransaction();
			
			HourlyBand hourlyBand = new HourlyBand();
			copyProperties(hourlyBand, (DynaActionForm)form);
			HibernateHelper.currentSession().save(hourlyBand);
			
			tx.commit();
		}
		catch (RuntimeException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		}
		finally {
			tx = null;
		}
	}	
	
	public void update (ActionForm form, HourlyBand hourlyBand) {
		Transaction tx = null;
		try {
			tx = HibernateHelper.currentSession().beginTransaction();
			
			copyProperties(hourlyBand, (DynaActionForm)form);
			HibernateHelper.currentSession().update(hourlyBand);
			
			tx.commit();
		}
		catch (RuntimeException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		}
		finally {
			tx = null;
		}
	}
	
	public void delete (Long id) {
		Transaction tx = null;
		try {
			tx = HibernateHelper.currentSession().beginTransaction();
			
			HourlyBand hourlyBand = (HourlyBand)HibernateHelper.currentSession().load(HourlyBand.class, id);
			hourlyBand.setActive(false);
			HibernateHelper.currentSession().update(hourlyBand);
			
			tx.commit();
		}
		catch (RuntimeException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		}
		finally {
			tx = null;
		}
	}
	
	public HourlyBand getHourlyBandByPK (Long id) {
		Transaction tx = null;
		HourlyBand banda = null;
		try {
			tx = HibernateHelper.currentSession().beginTransaction();
			
			banda = (HourlyBand)HibernateHelper.currentSession().load(HourlyBand.class, id);
			
			tx.commit();
		}
		catch (RuntimeException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		}
		finally {
			tx = null;
		}
		return banda;
	}
	
	@SuppressWarnings("unchecked")
	public List<HourlyBand> executeFilter(DynaActionForm form) {		
		Transaction tx = null;
		List<HourlyBand> hourlyBands = new ArrayList<HourlyBand>();
		try {
			tx = HibernateHelper.currentSession().beginTransaction();
			
			Long id = ((Long)form.get("id")).longValue() != new Long(-1).longValue() ? (Long)form.get("id") : null;			
			Criteria c = HibernateHelper.currentSession().createCriteria(HourlyBand.class);
			if (id != null) {
				c.add(Restrictions.eq("id", id));
			}
			c.add(Restrictions.eq("active", new Boolean(true)));
			hourlyBands = c.list();
			
			tx.commit();
		}
		catch (RuntimeException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		}
		finally {
			tx = null;
		}
				
		return hourlyBands;
	}
	
	@SuppressWarnings("unchecked")
	public List<HourlyBand> getAllHourlyBands() {
		Transaction tx = null;
		List<HourlyBand> bandas = new ArrayList<HourlyBand>();
		try {
			tx = HibernateHelper.currentSession().beginTransaction();
			
			Criteria c = HibernateHelper.currentSession().createCriteria(HourlyBand.class)
						.add(Restrictions.eq("active", new Boolean(true)));
			bandas = c.list();
			
			tx.commit();
		}
		catch (RuntimeException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		}
		finally {
			tx = null;
		}
		return bandas;
	}
}
