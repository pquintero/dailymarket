package ar.com.dailyMarket.charts;

import java.util.ArrayList;
import java.util.Iterator;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import ar.com.dailyMarket.charts.elements.AngularColorRange;
import ar.com.dailyMarket.charts.elements.AngularDial;
import ar.com.dailyMarket.charts.elements.SerializeChart;


public class AngularGauge extends InstrumentalChart {

	private Double lowerLimit;
	private Double upperLimit;
	private String lowerLimitDisplay;
	private String upperLimitDisplay;
	private Integer showLimits;

	private Integer gaugeScaleAngle;
	private Integer gaugeStartAngle;
	private Double gaugeOriginX;
	private Double gaugeOriginY;

	private String defaultNumberScale;
	private String numberScaleValue;
	private String numberScaleUnit;

	private Integer gaugeOuterRadius;
	private Integer gaugeInnerRadius;
	private Integer gaugeAlpha;
	private Integer animation;

	private Double showTickMarks;
	private Double majorTMNumber;
	private String majorTMColor;
	private Double majorTMHeight;
	private Double majorTMThickness;
	private Double minorTMNumber;
	private String minorTMColor;
	private Double minorTMHeight;
	private Double minorTMThickness;
	private Integer showTickValues;
	private Integer displayValueDistance;
	private Integer tickMarkDecimalPrecision;

	private String clickURL;

	private String pivotBgColor;
	private String pivotBorderColor;
	private Integer pivotBorderThickness;
	private Integer pivotRadius;

	private String dataStreamURL;
	private Integer refreshInterval;
	private Integer showRealTimeValue;
	private String realTimeValueFont;
	private String realTimeValueFontColor;
	private Integer realTimeValueFontSize;

	private Integer showhovercap;
	private String hoverCapBgColor;
	private String hoverCapBorderColor;
	private String hoverCapSepChar;
	
	private Integer showGaugeBorder;
	private Integer placeValuesInside;
	
	private ArrayList<AngularColorRange> colorRanges = new ArrayList<AngularColorRange>();
	private ArrayList<AngularDial> dials = new ArrayList<AngularDial>();
	
	public AngularGauge(){
	    super();
	}
	
	

    public Integer getPlaceValuesInside() {
        return placeValuesInside;
    }
    public Integer getShowGaugeBorder() {
        return showGaugeBorder;
    }
    public void setPlaceValuesInside(Integer placeValuesInside) {
        this.placeValuesInside = placeValuesInside;
    }
    public void setShowGaugeBorder(Integer showGaugeBorder) {
        this.showGaugeBorder = showGaugeBorder;
    }
    public ArrayList<AngularColorRange> getColorRanges() {
        return colorRanges;
    }
    public ArrayList<AngularDial> getDials() {
        return dials;
    }
    public void setColorRanges(ArrayList<AngularColorRange> colorRanges) {
        this.colorRanges = colorRanges;
    }
    public void setDials(ArrayList<AngularDial> dials) {
        this.dials = dials;
    }
    public Integer getAnimation() {
        return animation;
    }
    public String getClickURL() {
        return clickURL;
    }
    public String getDataStreamURL() {
        return dataStreamURL;
    }
    public String getDefaultNumberScale() {
        return defaultNumberScale;
    }
    public Integer getDisplayValueDistance() {
        return displayValueDistance;
    }
    public Integer getGaugeAlpha() {
        return gaugeAlpha;
    }
    public Integer getGaugeInnerRadius() {
        return gaugeInnerRadius;
    }
    public Double getGaugeOriginX() {
        return gaugeOriginX;
    }
    public Double getGaugeOriginY() {
        return gaugeOriginY;
    }
    public Integer getGaugeOuterRadius() {
        return gaugeOuterRadius;
    }
    public Integer getGaugeScaleAngle() {
        return gaugeScaleAngle;
    }
    public Integer getGaugeStartAngle() {
        return gaugeStartAngle;
    }
    public String getHoverCapBgColor() {
        return hoverCapBgColor;
    }
    public String getHoverCapBorderColor() {
        return hoverCapBorderColor;
    }
    public String getHoverCapSepChar() {
        return hoverCapSepChar;
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
    public Double getMajorTMHeight() {
        return majorTMHeight;
    }
    public Double getMajorTMNumber() {
        return majorTMNumber;
    }
    public Double getMajorTMThickness() {
        return majorTMThickness;
    }
    public String getMinorTMColor() {
        return minorTMColor;
    }
    public Double getMinorTMHeight() {
        return minorTMHeight;
    }
    public Double getMinorTMNumber() {
        return minorTMNumber;
    }
    public Double getMinorTMThickness() {
        return minorTMThickness;
    }
    public String getNumberScaleUnit() {
        return numberScaleUnit;
    }
    public String getNumberScaleValue() {
        return numberScaleValue;
    }
    public String getPivotBgColor() {
        return pivotBgColor;
    }
    public String getPivotBorderColor() {
        return pivotBorderColor;
    }
    public Integer getPivotBorderThickness() {
        return pivotBorderThickness;
    }
    public Integer getPivotRadius() {
        return pivotRadius;
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
    public Integer getShowhovercap() {
        return showhovercap;
    }
    public Integer getShowLimits() {
        return showLimits;
    }
    public Integer getShowRealTimeValue() {
        return showRealTimeValue;
    }
    public Double getShowTickMarks() {
        return showTickMarks;
    }
    public Integer getShowTickValues() {
        return showTickValues;
    }
    public Integer getTickMarkDecimalPrecision() {
        return tickMarkDecimalPrecision;
    }
    public Double getUpperLimit() {
        return upperLimit;
    }
    public String getUpperLimitDisplay() {
        return upperLimitDisplay;
    }
    public void setAnimation(Integer animation) {
        this.animation = animation;
    }
    public void setClickURL(String clickURL) {
        this.clickURL = clickURL;
    }
    public void setDataStreamURL(String dataStreamURL) {
        this.dataStreamURL = dataStreamURL;
    }
    public void setDefaultNumberScale(String defaultNumberScale) {
        this.defaultNumberScale = defaultNumberScale;
    }
    public void setDisplayValueDistance(Integer displayValueDistance) {
        this.displayValueDistance = displayValueDistance;
    }
    public void setGaugeAlpha(Integer gaugeAlpha) {
        this.gaugeAlpha = gaugeAlpha;
    }
    public void setGaugeInnerRadius(Integer gaugeInnerRadius) {
        this.gaugeInnerRadius = gaugeInnerRadius;
    }
    public void setGaugeOriginX(Double gaugeOriginX) {
        this.gaugeOriginX = gaugeOriginX;
    }
    public void setGaugeOriginY(Double gaugeOriginY) {
        this.gaugeOriginY = gaugeOriginY;
    }
    public void setGaugeOuterRadius(Integer gaugeOuterRadius) {
        this.gaugeOuterRadius = gaugeOuterRadius;
    }
    public void setGaugeScaleAngle(Integer gaugeScaleAngle) {
        this.gaugeScaleAngle = gaugeScaleAngle;
    }
    public void setGaugeStartAngle(Integer gaugeStartAngle) {
        this.gaugeStartAngle = gaugeStartAngle;
    }
    public void setHoverCapBgColor(String hoverCapBgColor) {
        this.hoverCapBgColor = hoverCapBgColor;
    }
    public void setHoverCapBorderColor(String hoverCapBorderColor) {
        this.hoverCapBorderColor = hoverCapBorderColor;
    }
    public void setHoverCapSepChar(String hoverCapSepChar) {
        this.hoverCapSepChar = hoverCapSepChar;
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
    public void setMajorTMHeight(Double majorTMHeight) {
        this.majorTMHeight = majorTMHeight;
    }
    public void setMajorTMNumber(Double majorTMNumber) {
        this.majorTMNumber = majorTMNumber;
    }
    public void setMajorTMThickness(Double majorTMThickness) {
        this.majorTMThickness = majorTMThickness;
    }
    public void setMinorTMColor(String minorTMColor) {
        this.minorTMColor = minorTMColor;
    }
    public void setMinorTMHeight(Double minorTMHeight) {
        this.minorTMHeight = minorTMHeight;
    }
    public void setMinorTMNumber(Double minorTMNumber) {
        this.minorTMNumber = minorTMNumber;
    }
    public void setMinorTMThickness(Double minorTMThickness) {
        this.minorTMThickness = minorTMThickness;
    }
    public void setNumberScaleUnit(String numberScaleUnit) {
        this.numberScaleUnit = numberScaleUnit;
    }
    public void setNumberScaleValue(String numberScaleValue) {
        this.numberScaleValue = numberScaleValue;
    }
    public void setPivotBgColor(String pivotBgColor) {
        this.pivotBgColor = pivotBgColor;
    }
    public void setPivotBorderColor(String pivotBorderColor) {
        this.pivotBorderColor = pivotBorderColor;
    }
    public void setPivotBorderThickness(Integer pivotBorderThickness) {
        this.pivotBorderThickness = pivotBorderThickness;
    }
    public void setPivotRadius(Integer pivotRadius) {
        this.pivotRadius = pivotRadius;
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
    public void setShowhovercap(Integer showhovercap) {
        this.showhovercap = showhovercap;
    }
    public void setShowLimits(Integer showLimits) {
        this.showLimits = showLimits;
    }
    public void setShowRealTimeValue(Integer showRealTimeValue) {
        this.showRealTimeValue = showRealTimeValue;
    }
    public void setShowTickMarks(Double showTickMarks) {
        this.showTickMarks = showTickMarks;
    }
    public void setShowTickValues(Integer showTickValues) {
        this.showTickValues = showTickValues;
    }
    public void setTickMarkDecimalPrecision(Integer tickMarkDecimalPrecision) {
        this.tickMarkDecimalPrecision = tickMarkDecimalPrecision;
    }
    public void setUpperLimit(Double upperLimit) {
        this.upperLimit = upperLimit;
    }
    public void setUpperLimitDisplay(String upperLimitDisplay) {
        this.upperLimitDisplay = upperLimitDisplay;
    }
    public Element setChartAttributes(Element root) {
    	SerializeChart.serializeThat(root, this);
    	
        Element colorRange = DocumentHelper.createElement("colorRange");
        for (Iterator<AngularColorRange> iter = getColorRanges().iterator(); iter.hasNext();) {
            AngularColorRange angColRangElement = (AngularColorRange) iter.next();
            Element color = DocumentHelper.createElement("color");
            SerializeChart.serializeThat(color ,angColRangElement);
            colorRange.add(color);
        }        
        root.add(colorRange);
        Element dials = DocumentHelper.createElement("dials");
        for (Iterator<AngularDial> iter = getDials().iterator(); iter.hasNext();) {
            AngularDial angDialElement = (AngularDial) iter.next();
            Element dial = DocumentHelper.createElement("dial");
            SerializeChart.serializeThat(dial, angDialElement);
            dials.add(dial);
        }
        root.add(dials);
        return root;
    }
}