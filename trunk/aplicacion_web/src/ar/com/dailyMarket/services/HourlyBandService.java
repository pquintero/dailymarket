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
			save(hourlyBand);
			
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
			save(hourlyBand);
			
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
			
			HourlyBand hourlyBand = getHourlyBandByPK(id);
			hourlyBand.setActive(false);
			save(hourlyBand);
			
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
	
	public void save (HourlyBand hourlyBand) {
		HibernateHelper.currentSession().saveOrUpdate(hourlyBand);
	}
	
	public HourlyBand getHourlyBandByPK (Long id) {
		return (HourlyBand)HibernateHelper.currentSession().load(HourlyBand.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<HourlyBand> executeFilter(DynaActionForm form) {		
		Long id = ((Long)form.get("id")).longValue() != new Long(-1).longValue() ? (Long)form.get("id") : null;			
		
		Criteria c = HibernateHelper.currentSession().createCriteria(HourlyBand.class);
		if (id != null) {
			c.add(Restrictions.eq("id", id));
		}		
		c.add(Restrictions.eq("active", new Boolean(true)));
		List<HourlyBand> hourlyBands = (List<HourlyBand>)c.list();		
		return hourlyBands.isEmpty() ? new ArrayList<HourlyBand>() : hourlyBands;
	}
	
	@SuppressWarnings("unchecked")
	public List<HourlyBand> getAllHourlyBands() {
		return (List<HourlyBand>)HibernateHelper.currentSession().createCriteria(HourlyBand.class)
		.add(Restrictions.eq("active", new Boolean(true))).list();
	}
}
