package ar.com.dailyMarket.charts;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import ar.com.dailyMarket.charts.elements.CategoryElement;
import ar.com.dailyMarket.charts.elements.DatasetElement;
import ar.com.dailyMarket.charts.elements.Lines;
import ar.com.dailyMarket.charts.elements.SerializeChart;



public class MSColumn2DLine extends ColumnChart3D {
	
	private Integer decimals;
	private Integer showSecondaryLimits;
	private Integer showLegend;
	
	private String lineColor;
	private Integer lineThickness;
	private Integer lineAlpha;

	private Integer showColumnShadow;
	private Integer showShadow;
	private String shadowColor;
	private Integer shadowThickness;
	private Integer shadowAlpha;
	
	private Integer shadowXShift;
	private Integer shadowYShift;
	
	private List<DatasetElement> datasets = new LinkedList<DatasetElement>();
	private List<Lines> categories = new LinkedList<Lines>();
	
	
	/**
	 * Recibe una lista donde el primer elemento es de tipo <code>CategoryElement</code>, que corresponde al tag "categories",
	 * los demas elementos son de tipo <code>DatasetElement</code> y corresponden al tag "dataset",
	 * el ultimo elemento es el correspondiente a la linea
	 * @param list
	 */
	public MSColumn2DLine(List list){
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
	
    public Integer getDecimals() {
		return decimals;
	}
	public void setDecimals(Integer decimals) {
		this.decimals = decimals;
	}
	public Integer getShowColumnShadow() {
        return showColumnShadow;
    }
    public void setShowColumnShadow(Integer showColumnShadow) {
        this.showColumnShadow = showColumnShadow;
    }
    public void addDataset(DatasetElement dataset){
    	if(this.datasets == null){
            this.datasets = new LinkedList<DatasetElement>();
        }
        this.datasets.add(dataset);
    }
    public void addCategory(CategoryElement category){
        if(this.categories == null){
            this.categories = new LinkedList<Lines>();
        }
        this.categories.add(category);
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
    public Integer getShowLegend() {
        return showLegend;
    }
    public Integer getShowSecondaryLimits() {
        return showSecondaryLimits;
    }
    public Integer getShowShadow() {
        return showShadow;
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
    public void setShowLegend(Integer showLegend) {
        this.showLegend = showLegend;
    }
    public void setShowSecondaryLimits(Integer showSecondaryLimits) {
        this.showSecondaryLimits = showSecondaryLimits;
    }
    public void setShowShadow(Integer showShadow) {
        this.showShadow = showShadow;
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