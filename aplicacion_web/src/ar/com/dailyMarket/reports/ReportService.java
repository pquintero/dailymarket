package ar.com.dailyMarket.reports;

import java.io.OutputStream;

import javax.servlet.ServletContext;

import org.apache.commons.beanutils.DynaBean;

public interface ReportService {
		
	 public int runReport(DynaBean reportData, OutputStream stream, String path, ServletContext ctx) throws Exception;

}
