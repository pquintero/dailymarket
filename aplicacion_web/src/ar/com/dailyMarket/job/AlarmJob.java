package ar.com.dailyMarket.job;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.Transaction;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import ar.com.dailyMarket.model.Configuration;
import ar.com.dailyMarket.model.Product;
import ar.com.dailyMarket.services.AlarmService;
import ar.com.dailyMarket.services.ConfigurationService;
import ar.com.dailyMarket.services.HibernateHelper;
import ar.com.dailyMarket.services.ProductService;

public class AlarmJob implements Job{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		AlarmService alarmService = new AlarmService();
		ProductService prodServ = new ProductService();
		List<Product> productos = prodServ.getProductWithoutStock();
		List<Product> enviar = new ArrayList<Product>();
		
		Configuration conf = new ConfigurationService().getConfiguration();
		Integer time = conf != null ? conf.getTimer() : 1;
		
		Date hoy = new Date();
		
		Transaction tx = null;
		try {
			tx = HibernateHelper.currentSession().beginTransaction();
			
			for (Product product : productos) {
				if (Product.PRODUCT_STATE_PENDING.equals(product.getState())) {
					if (product.getDateWithoutStock() == null) {
						product.setDateWithoutStock(hoy);
					} else {
						long dias = (hoy.getTime() - product.getDateWithoutStock().getTime()) / ( 24*60*60*1000 );
						
						if (dias >= time.longValue()) {
							enviar.add(product);
						}
					}
				}
			}
			
			tx.commit();
		}catch (RuntimeException e) {
		    if (tx != null) tx.rollback();
		    e.printStackTrace();
		}
		finally {
		    tx = null;
		}
		
		try{
			if (!enviar.isEmpty()) {
				alarmService.sendMail(enviar, time.toString());
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
