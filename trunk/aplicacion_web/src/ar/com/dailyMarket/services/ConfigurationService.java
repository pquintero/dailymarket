package ar.com.dailyMarket.services;

import org.apache.struts.action.DynaActionForm;

import ar.com.dailyMarket.model.Configuration;

public class ConfigurationService {
	
	public void copyProperties(Object obj, DynaActionForm form) {
		Configuration configuration = (Configuration)obj;
		configuration.setTimer((Integer)form.get("timer"));	
		configuration.setEmailDeposito((String)form.get("emailDeposito"));
	}
	
	public void save(DynaActionForm form) {
		Configuration conf = getConfiguration();
		if (conf == null) {
			conf  = new Configuration();
		}
		copyProperties(conf, form);
		HibernateHelper.currentSession().saveOrUpdate(conf);
		HibernateHelper.currentSession().flush();
	}
	
	public Configuration getConfiguration() {
		return (Configuration)HibernateHelper.currentSession().createCriteria(Configuration.class).uniqueResult();		
	}	
}
