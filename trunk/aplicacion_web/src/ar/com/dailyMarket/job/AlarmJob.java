package ar.com.dailyMarket.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import ar.com.dailyMarket.services.AlarmService;

public class AlarmJob implements Job{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		AlarmService alarmService = new AlarmService();
		alarmService.sendMail();		
	}
}
