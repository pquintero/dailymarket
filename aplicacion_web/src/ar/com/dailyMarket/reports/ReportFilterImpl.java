package ar.com.dailyMarket.reports;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.beanutils.DynaBean;



public class ReportFilterImpl implements ReportFilter {
	protected Collection criterios;
	
	public ReportFilterImpl() {		
		criterios = new ArrayList();
	}
	public void addReportCriteria(ReportCriteria reportCriteria){
		this.criterios.add(reportCriteria);
	}
	public String createSQLQuery(Object filterData){
		StringBuffer buffer = new StringBuffer("");
		for(Iterator iter = criterios.iterator(); iter.hasNext() ;) {
			buffer.append( ( ((ReportCriteria)iter.next()).getCriteria((DynaBean)filterData) ) );			
		}
		return buffer.toString();
	}

}
