package ar.com.dailyMarket.reports;



public interface ReportFilter {
	 void addReportCriteria(ReportCriteria reportCriteria);
	 String createSQLQuery(Object filterData);


}
