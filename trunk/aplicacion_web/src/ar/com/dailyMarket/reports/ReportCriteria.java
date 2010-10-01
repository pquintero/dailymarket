package ar.com.dailyMarket.reports;

import org.apache.commons.beanutils.DynaBean;


public class ReportCriteria {
	protected String propertyData;
	protected String propertyValue;
	protected String propertyOperator;
	protected String nullValue = "";
	
	
	public ReportCriteria() {		
	}
	public String getNullValue() {
		return nullValue;
	}
	public void setNullValue(String nullValue) {
		this.nullValue = nullValue;
	}
	public String getPropertyData() {
		return propertyData;
	}
	public void setPropertyData(String propertyData) {
		this.propertyData = propertyData;
	}
	public String getPropertyValue() {
		return propertyValue;
	}
	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}
	public String getCriteria(DynaBean reportData){
		if (reportData.get(propertyValue) != null && !(reportData.get(propertyValue)).toString().equals(nullValue)) {			
			return (" and " + propertyData + " " + propertyOperator + " " + getFormatedData(reportData.get(propertyValue)));
		} else {
			return ("");
		}		 
	}
	public String getPropertyOperator() {
		return propertyOperator;
	}
	public void setPropertyOperator(String propertyOperator) {
	    if(propertyOperator.equals("less") || propertyOperator.equals("below")){
	        this.propertyOperator = new String("<=");
	    } else {
	        this.propertyOperator = propertyOperator;
	    }
	}
	
	protected String getFormatedData(Object data) {
		String type = data.getClass().toString();
		if (type.equals("class java.lang.String")){
			return "'" + data.toString() + "'";
		} else {
			return data.toString();
		}		
	}
}
