package ar.com.dailyMarket.charts;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import ar.com.dailyMarket.charts.elements.SerializeChart;


public class CylinderChart extends InstrumentalChart {

	private Double value = 0d;

	private Double lowerLimit;
	private Double upperLimit;
	private String lowerLimitDisplay;
	private String upperLimitDisplay;
	private Integer showLimits;

	private Double cylOriginX;
	private Double cylOriginY;

	private Double cylRadius;
	private Double cylHeight;
	private Double cylArcYRadius;

	private String defaultNumberScale;
	private String numberScaleValue;
	private String numberScaleUnit;

	private Double showTickMarks;
	private Double tickMarkGap;
	private Double majorTMNumber;
	private String majorTMColor;
	private Double majorTMWidth;
	private Double majorTMThickness;
	private Double minorTMNumber;
	private String minorTMColor;
	private Double minorTMWidth;
	private Double minorTMThickness;
	private Integer showTickValues;
	private Integer tickMarkDecimalPrecision;

	private String clickURL;

	private String dataStreamURL;
	private Integer refreshInterval;
	private Integer showRealTimeValue;
	private String realTimeValueFont;
	private String realTimeValueFontColor;
	private Integer realTimeValueFontSize;

	private String cylColor;
	
	
	public CylinderChart(){
	    super();
	}
	
	public CylinderChart(Double value){
	    super();
	    this.value = value;
	}	

	

    public String getClickURL() {
        return clickURL;
    }
    public Double getCylArcYRadius() {
        return cylArcYRadius;
    }
    public Double getCylHeight() {
        return cylHeight;
    }
    public Double getCylOriginX() {
        return cylOriginX;
    }
    public Double getCylOriginY() {
        return cylOriginY;
    }
    public Double getCylRadius() {
        return cylRadius;
    }
    public String getDataStreamURL() {
        return dataStreamURL;
    }
    public String getDefaultNumberScale() {
        return defaultNumberScale;
    }
    public Double getLowerLimit() {
        return lowerLimit;
    }
    public String getLowerLimitDisplay() {
        return lowerLimitDisplay;
    }
    public String getMajorTMColor() {
        return majorTMColor;
    }
    public Double getMajorTMNumber() {
        return majorTMNumber;
    }
    public Double getMajorTMThickness() {
        return majorTMThickness;
    }
    public Double getMajorTMWidth() {
        return majorTMWidth;
    }
    public String getMinorTMColor() {
        return minorTMColor;
    }
    public Double getMinorTMNumber() {
        return minorTMNumber;
    }
    public Double getMinorTMThickness() {
        return minorTMThickness;
    }
    public Double getMinorTMWidth() {
        return minorTMWidth;
    }
    public String getNumberScaleUnit() {
        return numberScaleUnit;
    }
    public String getNumberScaleValue() {
        return numberScaleValue;
    }
    public String getRealTimeValueFont() {
        return realTimeValueFont;
    }
    public String getRealTimeValueFontColor() {
        return realTimeValueFontColor;
    }
    public Integer getRealTimeValueFontSize() {
        return realTimeValueFontSize;
    }
    public Integer getRefreshInterval() {
        return refreshInterval;
    }
    public Integer getShowLimits() {
        return showLimits;
    }
    public Integer getShowRealTimeValue() {
        return showRealTimeValue;
    }
    public Integer getShowTickValues() {
        return showTickValues;
    }
    public Integer getTickMarkDecimalPrecision() {
        return tickMarkDecimalPrecision;
    }
    public Double getTickMarkGap() {
        return tickMarkGap;
    }
    public Double getUpperLimit() {
        return upperLimit;
    }
    public String getUpperLimitDisplay() {
        return upperLimitDisplay;
    }
    public Double getValue() {
        return value;
    }
    public void setClickURL(String clickURL) {
        this.clickURL = clickURL;
    }
    public void setCylArcYRadius(Double cylArcYRadius) {
        this.cylArcYRadius = cylArcYRadius;
    }
    public void setCylHeight(Double cylHeight) {
        this.cylHeight = cylHeight;
    }
    public void setCylOriginX(Double cylOriginX) {
        this.cylOriginX = cylOriginX;
    }
    public void setCylOriginY(Double cylOriginY) {
        this.cylOriginY = cylOriginY;
    }
    public void setCylRadius(Double cylRadius) {
        this.cylRadius = cylRadius;
    }
    public void setDataStreamURL(String dataStreamURL) {
        this.dataStreamURL = dataStreamURL;
    }
    public void setDefaultNumberScale(String defaultNumberScale) {
        this.defaultNumberScale = defaultNumberScale;
    }
    public void setLowerLimit(Double lowerLimit) {
        this.lowerLimit = lowerLimit;
    }
    public void setLowerLimitDisplay(String lowerLimitDisplay) {
        this.lowerLimitDisplay = lowerLimitDisplay;
    }
    public void setMajorTMColor(String majorTMColor) {
        this.majorTMColor = majorTMColor;
    }
    public void setMajorTMNumber(Double majorTMNumber) {
        this.majorTMNumber = majorTMNumber;
    }
    public void setMajorTMThickness(Double majorTMThickness) {
        this.majorTMThickness = majorTMThickness;
    }
    public void setMajorTMWidth(Double majorTMWidth) {
        this.majorTMWidth = majorTMWidth;
    }
    public void setMinorTMColor(String minorTMColor) {
        this.minorTMColor = minorTMColor;
    }
    public void setMinorTMNumber(Double minorTMNumber) {
        this.minorTMNumber = minorTMNumber;
    }
    public void setMinorTMThickness(Double minorTMThickness) {
        this.minorTMThickness = minorTMThickness;
    }
    public void setMinorTMWidth(Double minorTMWidth) {
        this.minorTMWidth = minorTMWidth;
    }
    public void setNumberScaleUnit(String numberScaleUnit) {
        this.numberScaleUnit = numberScaleUnit;
    }
    public void setNumberScaleValue(String numberScaleValue) {
        this.numberScaleValue = numberScaleValue;
    }
    public void setRealTimeValueFont(String realTimeValueFont) {
        this.realTimeValueFont = realTimeValueFont;
    }
    public void setRealTimeValueFontColor(String realTimeValueFontColor) {
        this.realTimeValueFontColor = realTimeValueFontColor;
    }
    public void setRealTimeValueFontSize(Integer realTimeValueFontSize) {
        this.realTimeValueFontSize = realTimeValueFontSize;
    }
    public void setRefreshInterval(Integer refreshInterval) {
        this.refreshInterval = refreshInterval;
    }
    public void setShowLimits(Integer showLimits) {
        this.showLimits = showLimits;
    }
    public void setShowRealTimeValue(Integer showRealTimeValue) {
        this.showRealTimeValue = showRealTimeValue;
    }
    public void setShowTickValues(Integer showTickValues) {
        this.showTickValues = showTickValues;
    }
    public void setTickMarkDecimalPrecision(Integer tickMarkDecimalPrecision) {
        this.tickMarkDecimalPrecision = tickMarkDecimalPrecision;
    }
    public void setTickMarkGap(Double tickMarkGap) {
        this.tickMarkGap = tickMarkGap;
    }
    public void setUpperLimit(Double upperLimit) {
        this.upperLimit = upperLimit;
    }
    public void setUpperLimitDisplay(String upperLimitDisplay) {
        this.upperLimitDisplay = upperLimitDisplay;
    }
    public void setValue(Double value) {
        this.value = value;
    }    
    public String getCylColor() {
        return cylColor;
    }
    public Double getShowTickMarks() {
        return showTickMarks;
    }
    public void setCylColor(String cylColor) {
        this.cylColor = cylColor;
    }
    public void setShowTickMarks(Double showTickMarks) {
        this.showTickMarks = showTickMarks;
    }
    public Element setChartAttributes(Element root) {        
    	SerializeChart.serializeThat(root, this);
    	
        Element value = DocumentHelper.createElement("value");
        value.addText(this.value.toString());
        
        root.add(value);
        return root;
    }

}
