package ar.com.dailyMarket.charts.elements;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;


public class DatasetElement {

    protected String seriesname;
    protected Integer showValues;
    protected String color;
    
    protected String renderAs;//Combi
    protected String parentYAxis;//Combi 
    protected Integer lineThickness;
    protected Integer includeInLegend;
    
    protected Integer alpha;
	
    protected Integer drawAnchors;
    protected Integer anchorSides;
    protected Integer anchorRadius;
    protected String anchorBorderColor;
    protected Integer anchorBorderThickness;
    protected String anchorBgColor;
    protected Integer anchorBgAlpha;
    protected Integer anchorAlpha;
    
    protected String ratio;//Stacked 	

    protected LinkedList<Lines> sets = new LinkedList<Lines>();
    
    
    public DatasetElement(Map values) {
        super();
        for (Iterator iter = values.values().iterator(); iter.hasNext();) {
            SetElement set = (SetElement) iter.next();
            this.sets.add(set);
        }
    }
    
    public String getRatio() {
		return ratio;
	}
	public void setRatio(String ratio) {
		this.ratio = ratio;
	}
	public Integer getIncludeInLegend() {
		return includeInLegend;
	}
	public void setIncludeInLegend(Integer includeInLegend) {
		this.includeInLegend = includeInLegend;
	}
	public DatasetElement() {
        super();
    }
    public String getRenderAs() {
		return renderAs;
	}
	public void setRenderAs(String renderAs) {
		this.renderAs = renderAs;
	}
	public Integer getAlpha() {
        return alpha;
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
    public Integer getAnchorBorderThickness() {
        return anchorBorderThickness;
    }
    public Integer getDrawAnchors() {
        return drawAnchors;
    }
    public void setAlpha(Integer alpha) {
        this.alpha = alpha;
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
    public void setAnchorBorderThickness(Integer anchorBorderThickness) {
        this.anchorBorderThickness = anchorBorderThickness;
    }
    public void setDrawAnchors(Integer drawAnchors) {
        this.drawAnchors = drawAnchors;
    }
    public LinkedList<Lines> getSets() {
        return sets;
    }
    public void setSets(LinkedList<Lines> sets) {
        this.sets = sets;
    }
    public void addSet(Lines set){
        if(this.sets == null){
            this.sets = new LinkedList<Lines>();
        }
        this.sets.add(set);
    }
    public String getAnchorBorderColor() {
        return anchorBorderColor;
    }
    public void setAnchorBorderColor(String anchorBorderColor) {
        this.anchorBorderColor = anchorBorderColor;
    }
    public Integer getAnchorRadius() {
        return anchorRadius;
    }
    public void setAnchorRadius(Integer anchorRadius) {
        this.anchorRadius = anchorRadius;
    }
    public Integer getAnchorSides() {
        return anchorSides;
    }
    public void setAnchorSides(Integer anchorSides) {
        this.anchorSides = anchorSides;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public Integer getLineThickness() {
        return lineThickness;
    }
    public void setLineThickness(Integer lineThickness) {
        this.lineThickness = lineThickness;
    }
    public String getParentYAxis() {
        return parentYAxis;
    }
    public void setParentYAxis(String parentYAxis) {
        this.parentYAxis = parentYAxis;
    }
    public String getSeriesname() {
        return seriesname;
    }
    public void setSeriesname(String seriesname) {
        this.seriesname = seriesname;
    }
    public Integer getShowValues() {
        return showValues;
    }
    public void setShowValues(Integer showValues) {
        this.showValues = showValues;
    }
    
    public void serializeMe(Element element) {        
    	SerializeChart.serializeThat(element, this);
    	
        for (Iterator<Lines> iter = getSets().iterator(); iter.hasNext();) {
        	Lines line = (Lines) iter.next();
            Element lineElement = DocumentHelper.createElement(line.elementName());
            SerializeChart.serializeThat(lineElement, line);
            element.add(lineElement);
        }
    }
}
