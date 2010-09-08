package ar.com.dailyMarket.charts;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import ar.com.dailyMarket.charts.elements.DatasetElement;
import ar.com.dailyMarket.charts.elements.Lines;
import ar.com.dailyMarket.charts.elements.SerializeChart;



public class MSLine extends AxisChart {

    private String xAxisName;
    private String yAxisName;

    private Double yAxisMinValue;
    private Double yAxisMaxValue;

    private Integer rotateLabels;
    private Integer showLegend;

	private String lineColor;
	private Integer lineThickness;
	private Integer lineAlpha;
//NO
	private Integer showShadow;
	private String shadowColor;
	private Integer shadowThickness;
	private Integer shadowAlpha;
	private Integer shadowXShift;
	private Integer shadowYShift;
//
	private Integer drawAnchors;
	private Integer anchorSides;
	private Integer anchorRadius;
	private String anchorBorderColor;
	private Integer anchorBorderThickness;
	private String anchorBgColor;
	private Integer anchorBgAlpha;
	private Integer anchorAlpha;

	private String outCnvBaseFont;
	private Integer outCnvBaseFontSize;
    private String outCnvBaseFontColor;
//NO
    private Integer divLineDecimalPrecision;
    private Integer limitsDecimalPrecision;
//
    private Integer zeroPlaneThickness;
	private String zeroPlaneColor;
	private Double zeroPlaneAlpha;

	private Integer numdivlines;
	private String divlinecolor;
	private Integer divLineThickness;
	private Double divLineAlpha;
	private Integer showDivLineValues;
	private String showAlternateHGridColor;
	private String alternateHGridColor;
	private Double alternateHGridAlpha;

	private Integer numVDivLines;
	private String vDivlinecolor;
	private Integer vDivLineThickness;
	private Double vDivLineAlpha;
	private String showAlternateVGridColor;
	private String alternateVGridColor;
	private Integer alternateVGridAlpha;

	private Integer chartLeftMargin;
	private Integer chartRightMargin;
	private Integer chartTopMargin;
	private Integer chartBottomMargin;

	private List<DatasetElement> datasets = new LinkedList<DatasetElement>();
	private List<Lines> categories = new LinkedList<Lines>();
	
	
	/**
	 * Recibe una lista donde el primer elemento es de tipo <code>CategoryElement</code>, que corresponde al tag "categories",
	 * los demas elementos son de tipo <code>DatasetElement</code> y corresponden al tag "dataset",
	 * el ultimo elemento es el correspondiente a la linea
	 * @param list
	 */
	public MSLine(List list){
	    super();	    
	    for(Iterator it = ((List)list.get(0)).iterator(); it.hasNext();){
	    	Lines category = (Lines) it.next();
	        this.categories.add(category);
	    }
	    for(Iterator it = list.subList(1, list.size()).iterator(); it.hasNext();){ //para cada dataset	        
	        DatasetElement dataset = (DatasetElement) it.next();
	        this.datasets.add(dataset);
	    }	    
	}
    public void addDataset(DatasetElement dataset){
        if(this.datasets == null){
            this.datasets = new LinkedList<DatasetElement>();
        }
        this.datasets.add(dataset);
    }
    public void addCategory(Lines category){
        if(this.categories == null){
            this.categories = new LinkedList<Lines>();
        }
        this.categories.add(category);
    }
    public Double getAlternateHGridAlpha() {
        return alternateHGridAlpha;
    }
    public String getAlternateHGridColor() {
        return alternateHGridColor;
    }
    public Integer getAlternateVGridAlpha() {
        return alternateVGridAlpha;
    }
    public String getAlternateVGridColor() {
        return alternateVGridColor;
    }
    public Integer getAnchorAlpha() {
        return anchorAlpha;
    }
    public Integer getAnchorBgAlpha() {
        return anchorBgAlpha;
    }
    public String getAnchorBgColor() {
        return anchorBgColor;
    }
    public String getAnchorBorderColor() {
        return anchorBorderColor;
    }
    public Integer getAnchorBorderThickness() {
        return anchorBorderThickness;
    }
    public Integer getAnchorRadius() {
        return anchorRadius;
    }
    public Integer getAnchorSides() {
        return anchorSides;
    }
    public List<Lines> getCategories() {
        return categories;
    }
    public Integer getChartBottomMargin() {
        return chartBottomMargin;
    }
    public Integer getChartLeftMargin() {
        return chartLeftMargin;
    }
    public Integer getChartRightMargin() {
        return chartRightMargin;
    }
    public Integer getChartTopMargin() {
        return chartTopMargin;
    }
    public List<DatasetElement> getDatasets() {
        return datasets;
    }
    public Double getDivLineAlpha() {
        return divLineAlpha;
    }
    public String getDivlinecolor() {
        return divlinecolor;
    }
    public Integer getDivLineDecimalPrecision() {
        return divLineDecimalPrecision;
    }
    public Integer getDivLineThickness() {
        return divLineThickness;
    }
    public Integer getLimitsDecimalPrecision() {
        return limitsDecimalPrecision;
    }
    public Integer getLineAlpha() {
        return lineAlpha;
    }
    public String getLineColor() {
        return lineColor;
    }
    public Integer getLineThickness() {
        return lineThickness;
    }
    public Integer getNumdivlines() {
        return numdivlines;
    }
    public Integer getNumVDivLines() {
        return numVDivLines;
    }
    public String getOutCnvBaseFont() {
        return outCnvBaseFont;
    }
    public String getOutCnvBaseFontColor() {
        return outCnvBaseFontColor;
    }
    public Integer getOutCnvBaseFontSize() {
        return outCnvBaseFontSize;
    }
    public Integer getRotateLabels() {
        return rotateLabels;
    }
    public Integer getShadowAlpha() {
        return shadowAlpha;
    }
    public String getShadowColor() {
        return shadowColor;
    }
    public Integer getShadowThickness() {
        return shadowThickness;
    }
    public Integer getShadowXShift() {
        return shadowXShift;
    }
    public Integer getShadowYShift() {
        return shadowYShift;
    }
    public String getShowAlternateHGridColor() {
        return showAlternateHGridColor;
    }
    public String getShowAlternateVGridColor() {
        return showAlternateVGridColor;
    }
    public Integer getDrawAnchors() {
        return drawAnchors;
    }
    public Integer getShowDivLineValues() {
        return showDivLineValues;
    }
    public Integer getShowLegend() {
        return showLegend;
    }
    public Integer getShowShadow() {
        return showShadow;
    }
    public Double getVDivLineAlpha() {
        return vDivLineAlpha;
    }
    public String getVDivlinecolor() {
        return vDivlinecolor;
    }
    public Integer getVDivLineThickness() {
        return vDivLineThickness;
    }
    public String getXAxisName() {
        return xAxisName;
    }
    public Double getYAxisMaxValue() {
        return yAxisMaxValue;
    }
    public Double getYAxisMinValue() {
        return yAxisMinValue;
    }
    public String getYAxisName() {
        return yAxisName;
    }
    public Double getZeroPlaneAlpha() {
        return zeroPlaneAlpha;
    }
    public String getZeroPlaneColor() {
        return zeroPlaneColor;
    }
    public Integer getZeroPlaneThickness() {
        return zeroPlaneThickness;
    }
    public void setAlternateHGridAlpha(Double alternateHGridAlpha) {
        this.alternateHGridAlpha = alternateHGridAlpha;
    }
    public void setAlternateHGridColor(String alternateHGridColor) {
        this.alternateHGridColor = alternateHGridColor;
    }
    public void setAlternateVGridAlpha(Integer alternateVGridAlpha) {
        this.alternateVGridAlpha = alternateVGridAlpha;
    }
    public void setAlternateVGridColor(String alternateVGridColor) {
        this.alternateVGridColor = alternateVGridColor;
    }
    public void setAnchorAlpha(Integer anchorAlpha) {
        this.anchorAlpha = anchorAlpha;
    }
    public void setAnchorBgAlpha(Integer anchorBgAlpha) {
        this.anchorBgAlpha = anchorBgAlpha;
    }
    public void setAnchorBgColor(String anchorBgColor) {
        this.anchorBgColor = anchorBgColor;
    }
    public void setAnchorBorderColor(String anchorBorderColor) {
        this.anchorBorderColor = anchorBorderColor;
    }
    public void setAnchorBorderThickness(Integer anchorBorderThickness) {
        this.anchorBorderThickness = anchorBorderThickness;
    }
    public void setAnchorRadius(Integer anchorRadius) {
        this.anchorRadius = anchorRadius;
    }
    public void setAnchorSides(Integer anchorSides) {
        this.anchorSides = anchorSides;
    }
    public void setCategories(List<Lines> categories) {
        this.categories = categories;
    }
    public void setChartBottomMargin(Integer chartBottomMargin) {
        this.chartBottomMargin = chartBottomMargin;
    }
    public void setChartLeftMargin(Integer chartLeftMargin) {
        this.chartLeftMargin = chartLeftMargin;
    }
    public void setChartRightMargin(Integer chartRightMargin) {
        this.chartRightMargin = chartRightMargin;
    }
    public void setChartTopMargin(Integer chartTopMargin) {
        this.chartTopMargin = chartTopMargin;
    }
    public void setDatasets(List<DatasetElement> datasets) {
        this.datasets = datasets;
    }
    public void setDivLineAlpha(Double divLineAlpha) {
        this.divLineAlpha = divLineAlpha;
    }
    public void setDivlinecolor(String divlinecolor) {
        this.divlinecolor = divlinecolor;
    }
    public void setDivLineDecimalPrecision(Integer divLineDecimalPrecision) {
        this.divLineDecimalPrecision = divLineDecimalPrecision;
    }
    public void setDivLineThickness(Integer divLineThickness) {
        this.divLineThickness = divLineThickness;
    }
    public void setLimitsDecimalPrecision(Integer limitsDecimalPrecision) {
        this.limitsDecimalPrecision = limitsDecimalPrecision;
    }
    public void setLineAlpha(Integer lineAlpha) {
        this.lineAlpha = lineAlpha;
    }
    public void setLineColor(String lineColor) {
        this.lineColor = lineColor;
    }
    public void setLineThickness(Integer lineThickness) {
        this.lineThickness = lineThickness;
    }
    public void setNumdivlines(Integer numdivlines) {
        this.numdivlines = numdivlines;
    }
    public void setNumVDivLines(Integer numVDivLines) {
        this.numVDivLines = numVDivLines;
    }
    public void setOutCnvBaseFont(String outCnvBaseFont) {
        this.outCnvBaseFont = outCnvBaseFont;
    }
    public void setOutCnvBaseFontColor(String outCnvBaseFontColor) {
        this.outCnvBaseFontColor = outCnvBaseFontColor;
    }
    public void setOutCnvBaseFontSize(Integer outCnvBaseFontSze) {
        this.outCnvBaseFontSize = outCnvBaseFontSze;
    }
    public void setRotateLabels(Integer rotateNames) {
        this.rotateLabels = rotateNames;
    }
    public void setShadowAlpha(Integer shadowAlpha) {
        this.shadowAlpha = shadowAlpha;
    }
    public void setShadowColor(String shadowColor) {
        this.shadowColor = shadowColor;
    }
    public void setShadowThickness(Integer shadowThickness) {
        this.shadowThickness = shadowThickness;
    }
    public void setShadowXShift(Integer shadowXShift) {
        this.shadowXShift = shadowXShift;
    }
    public void setShadowYShift(Integer shadowYShift) {
        this.shadowYShift = shadowYShift;
    }
    public void setShowAlternateHGridColor(String showAlternateHGridColor) {
        this.showAlternateHGridColor = showAlternateHGridColor;
    }
    public void setShowAlternateVGridColor(String showAlternateVGridColor) {
        this.showAlternateVGridColor = showAlternateVGridColor;
    }
    public void setDrawAnchors(Integer drawAnchors) {
        this.drawAnchors = drawAnchors;
    }
    public void setShowDivLineValues(Integer showDivLineValue) {
        this.showDivLineValues = showDivLineValue;
    }
    public void setShowLegend(Integer showLegend) {
        this.showLegend = showLegend;
    }
    public void setShowShadow(Integer showShadow) {
        this.showShadow = showShadow;
    }
    public void setVDivLineAlpha(Double divLineAlpha) {
        vDivLineAlpha = divLineAlpha;
    }
    public void setVDivlinecolor(String divlinecolor) {
        vDivlinecolor = divlinecolor;
    }
    public void setVDivLineThickness(Integer divLineThickness) {
        vDivLineThickness = divLineThickness;
    }
    public void setXAxisName(String axisName) {
        xAxisName = axisName;
    }
    public void setYAxisMaxValue(Double axisMaxValue) {
        yAxisMaxValue = axisMaxValue;
    }
    public void setYAxisMinValue(Double axisMinValue) {
        yAxisMinValue = axisMinValue;
    }
    public void setYAxisName(String axisName) {
        yAxisName = axisName;
    }
    public void setZeroPlaneAlpha(Double zeroPlaneAlpha) {
        this.zeroPlaneAlpha = zeroPlaneAlpha;
    }
    public void setZeroPlaneColor(String zeroPlaneColor) {
        this.zeroPlaneColor = zeroPlaneColor;
    }
    public void setZeroPlaneThickness(Integer zeroPlaneThickness) {
        this.zeroPlaneThickness = zeroPlaneThickness;
    }
    
    public Element setChartAttributes(Element chart) {        
        chart = super.setChartAttributes(chart);
        
        Element categories = DocumentHelper.createElement("categories");        
        for (Iterator<Lines> iter = getCategories().iterator(); iter.hasNext();) {
        	Lines line = (Lines) iter.next();
            Element lineElement = DocumentHelper.createElement(line.elementName());
            SerializeChart.serializeThat(lineElement, line);
            categories.add(lineElement);
        }        
        chart.add(categories);
        for (Iterator<DatasetElement> iter = getDatasets().iterator(); iter.hasNext();) {
            DatasetElement dataSetElement = (DatasetElement) iter.next();
            Element dataset = DocumentHelper.createElement("dataset");
            dataSetElement.serializeMe(dataset);
            chart.add(dataset);
        }
        return chart;
    }
}
