package ar.com.dailyMarket.charts;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import ar.com.dailyMarket.charts.elements.DatasetElement;
import ar.com.dailyMarket.charts.elements.Lines;
import ar.com.dailyMarket.charts.elements.SerializeChart;



public class MSColumn3DLine extends ColumnChart3D {

	private String PYAxisName;
	private String SYAxisName;
	
	private Integer PYAxisMaxValue;
	private Integer PYAxisMinValue;
	private Integer SYAxisMaxValue;
	private Integer SYAxisMinValue;

	private Integer showSecondaryLimits;
	private Integer showLegend;
	
	private String lineColor;
	private Integer lineThickness;
	private Integer lineAlpha;

	private Integer showShadow;
	private String shadowColor;
	private Integer shadowThickness;
	private Integer shadowAlpha;
	private Integer shadowXShift;
	private Integer shadowYShift;

	private Integer drawAnchors;
	private Integer anchorSides;
	private Integer anchorRadius;
	private String anchorBorderColor;
	private Integer anchorBorderThickness;
	private String anchorBgColor;
	private Integer anchorBgAlpha;
	private Integer anchorAlpha;
	
	private Integer showDivLineSecondaryValue;	
	
	private List<DatasetElement> datasets = new LinkedList<DatasetElement>();
	private List<Lines> categories = new LinkedList<Lines>();
	
	
	/**
	 * Recibe una lista donde el primer elemento es de tipo <code>CategoryElement</code>, que corresponde al tag "categories",
	 * los demas elementos son de tipo <code>DatasetElement</code> y corresponden al tag "dataset",
	 * el ultimo elemento es el correspondiente a la linea
	 * @param list
	 */
	public MSColumn3DLine(List list){
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
    public Integer getLineAlpha() {
        return lineAlpha;
    }
    public String getLineColor() {
        return lineColor;
    }
    public Integer getLineThickness() {
        return lineThickness;
    }
    public Integer getPYAxisMaxValue() {
        return PYAxisMaxValue;
    }
    public Integer getPYAxisMinValue() {
        return PYAxisMinValue;
    }
    public String getPYAxisName() {
        return PYAxisName;
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
    public Integer getDrawAnchors() {
        return drawAnchors;
    }
    public Integer getShowDivLineSecondaryValue() {
        return showDivLineSecondaryValue;
    }
    public Integer getShowLegend() {
        return showLegend;
    }
    public Integer getShowSecondaryLimits() {
        return showSecondaryLimits;
    }
    public Integer getShowShadow() {
        return showShadow;
    }
    public Integer getSYAxisMaxValue() {
        return SYAxisMaxValue;
    }
    public Integer getSYAxisMinValue() {
        return SYAxisMinValue;
    }
    public String getSYAxisName() {
        return SYAxisName;
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
    public void setLineAlpha(Integer lineAlpha) {
        this.lineAlpha = lineAlpha;
    }
    public void setLineColor(String lineColor) {
        this.lineColor = lineColor;
    }
    public void setLineThickness(Integer lineThickness) {
        this.lineThickness = lineThickness;
    }
    public void setPYAxisMaxValue(Integer axisMaxValue) {
        PYAxisMaxValue = axisMaxValue;
    }
    public void setPYAxisMinValue(Integer axisMinValue) {
        PYAxisMinValue = axisMinValue;
    }
    public void setPYAxisName(String axisName) {
        PYAxisName = axisName;
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
    public void setDrawAnchors(Integer drawAnchors) {
        this.drawAnchors = drawAnchors;
    }
    public void setShowDivLineSecondaryValue(Integer showDivLineSecondaryValue) {
        this.showDivLineSecondaryValue = showDivLineSecondaryValue;
    }
    public void setShowLegend(Integer showLegend) {
        this.showLegend = showLegend;
    }
    public void setShowSecondaryLimits(Integer showSecondaryLimits) {
        this.showSecondaryLimits = showSecondaryLimits;
    }
    public void setShowShadow(Integer showShadow) {
        this.showShadow = showShadow;
    }
    public void setSYAxisMaxValue(Integer axisMaxValue) {
        SYAxisMaxValue = axisMaxValue;
    }
    public void setSYAxisMinValue(Integer axisMinValue) {
        SYAxisMinValue = axisMinValue;
    }
    public void setSYAxisName(String axisName) {
        SYAxisName = axisName;
    }
    
    public List<Lines> getCategories() {
        return categories;
    }
    public List<DatasetElement> getDatasets() {
        return datasets;
    }
    public void setCategories(List<Lines> categories) {
        this.categories = categories;
    }
    public void setDatasets(List<DatasetElement> datasets) {
        this.datasets = datasets;
    }    
    
    public Element setChartAttributes(Element chart) {                
        super.setChartAttributes(chart);
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
