package ar.com.dailyMarket.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.DynaActionForm;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import ar.com.dailyMarket.model.HourlyBand;

public class HourlyBandService {
	
	public void copyProperties(Object obj, DynaActionForm form) {			
		HourlyBand hourlyBand = (HourlyBand)obj;
		hourlyBand.setName((String)form.get("name"));
		hourlyBand.setDescription((String)form.get("description"));
		hourlyBand.setInitBand((Integer)form.get("initBand"));
		hourlyBand.setEndBand((Integer)form.get("endBand"));
	}
	
	public void save (ActionForm form) {	
		HourlyBand hourlyBand = new HourlyBand();
		copyProperties(hourlyBand, (DynaActionForm)form);
		save(hourlyBand);		
	}	
	
	public void update (ActionForm form, HourlyBand hourlyBand) {
		copyProperties(hourlyBand, (DynaActionForm)form);
		save(hourlyBand);
	}
	
	public void save (HourlyBand hourlyBand) {
		HibernateHelper.currentSession().saveOrUpdate(hourlyBand);
		HibernateHelper.currentSession().flush();		
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
		List hourlyBands = (List)c.list();		
		return hourlyBands.isEmpty() ? new ArrayList() : hourlyBands;
	}
	
	@SuppressWarnings("unchecked")
	public List<HourlyBand> getAllHourlyBands() {
		return (List<HourlyBand>)HibernateHelper.currentSession().createCriteria(HourlyBand.class).list();
	}
	
	public void delete (Long id) {
		HourlyBand hourlyBand = getHourlyBandByPK(id);
		HibernateHelper.currentSession().delete(hourlyBand);
		HibernateHelper.currentSession().flush();
	}
}
