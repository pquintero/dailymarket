package ar.com.dailyMarket.services;

import org.apache.struts.action.DynaActionForm;
import org.hibernate.Transaction;

import ar.com.dailyMarket.model.Configuration;

public class ConfigurationService {
	
	public void copyProperties(Object obj, DynaActionForm form) {
		Configuration configuration = (Configuration)obj;
		configuration.setTimer(Integer.parseInt((String)form.get("timer")));	
		configuration.setEmailDeposito((String)form.get("emailDeposito"));
	}
	
	public void save(DynaActionForm form) {
		Transaction tx = null;
		try {
			HibernateHelper.closeSession();
			tx = HibernateHelper.currentSession().beginTransaction();
		
			Configuration conf = (Configuration) HibernateHelper.currentSession().createCriteria(Configuration.class).uniqueResult();
			if (conf == null) {
				conf  = new Configuration();
			}
			copyProperties(conf, form);
			HibernateHelper.currentSession().saveOrUpdate(conf);
			
			tx.commit();
		}
		catch (RuntimeException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		}
		finally {
			tx = null;
			HibernateHelper.closeSession();
		}
	}
	
	public Configuration getConfiguration() {
		Transaction tx = null;
		Configuration conf = null;
		try {
			HibernateHelper.closeSession();
			tx = HibernateHelper.currentSession().beginTransaction();
			
			conf = (Configuration) HibernateHelper.currentSession().createCriteria(Configuration.class).uniqueResult();
			
			tx.commit();
		}
		catch (RuntimeException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		}
		finally {
			tx = null;
			HibernateHelper.closeSession();
		}
		return conf;
	}	
}
