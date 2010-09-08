package ar.com.dailyMarket.charts;

import java.util.List;

public class Combi2DDualY extends Combi2DSingleY {
	private String PYAxisName;
	private String SYAxisName;
	private Double PYAxisMaxValue;
	private Double PYAxisMinValue;
	private Double SYAxisMaxValue;
	private Double SYAxisMinValue;
	private Integer showDivLineSecondaryValue;
	private Integer setAdaptiveSYMin;
	private Integer PYAxisNameWidth;
	private Integer SYAxisNameWidth ;
	
	public Combi2DDualY(List value){
		super(value);
	}

	
	public String getPYAxisName() {
		return PYAxisName;
	}
	public void setPYAxisName(String axisName) {
		PYAxisName = axisName;
	}
	public String getSYAxisName() {
		return SYAxisName;
	}
	public void setSYAxisName(String axisName) {
		SYAxisName = axisName;
	}
	public Double getPYAxisMaxValue() {
		return PYAxisMaxValue;
	}
	public void setPYAxisMaxValue(Double axisMaxValue) {
		PYAxisMaxValue = axisMaxValue;
	}
	public Double getPYAxisMinValue() {
		return PYAxisMinValue;
	}
	public void setPYAxisMinValue(Double axisMinValue) {
		PYAxisMinValue = axisMinValue;
	}
	public Double getSYAxisMaxValue() {
		return SYAxisMaxValue;
	}
	public void setSYAxisMaxValue(Double axisMaxValue) {
		SYAxisMaxValue = axisMaxValue;
	}
	public Double getSYAxisMinValue() {
		return SYAxisMinValue;
	}
	public void setSYAxisMinValue(Double axisMinValue) {
		SYAxisMinValue = axisMinValue;
	}
	public Integer getShowDivLineSecondaryValue() {
		return showDivLineSecondaryValue;
	}
	public void setShowDivLineSecondaryValue(Integer showDivLineSecondaryValue) {
		this.showDivLineSecondaryValue = showDivLineSecondaryValue;
	}
	public Integer getSetAdaptiveSYMin() {
		return setAdaptiveSYMin;
	}
	public void setSetAdaptiveSYMin(Integer setAdaptiveSYMin) {
		this.setAdaptiveSYMin = setAdaptiveSYMin;
	}
	public Integer getPYAxisNameWidth() {
		return PYAxisNameWidth;
	}
	public void setPYAxisNameWidth(Integer axisNameWidth) {
		PYAxisNameWidth = axisNameWidth;
	}
	public Integer getSYAxisNameWidth() {
		return SYAxisNameWidth;
	}
	public void setSYAxisNameWidth(Integer axisNameWidth) {
		SYAxisNameWidth = axisNameWidth;
	}
}