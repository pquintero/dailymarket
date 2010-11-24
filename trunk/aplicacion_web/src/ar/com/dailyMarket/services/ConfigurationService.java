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
			tx = HibernateHelper.currentSession().beginTransaction();
		
			Configuration conf = getConfiguration();
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
		}
	}
	
	public Configuration getConfiguration() {
		return (Configuration)HibernateHelper.currentSession().createCriteria(Configuration.class).uniqueResult();		
	}	
}
