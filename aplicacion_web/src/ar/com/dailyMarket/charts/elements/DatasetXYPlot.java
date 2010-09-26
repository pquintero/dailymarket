package ar.com.dailyMarket.charts.elements;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class DatasetXYPlot {
	
	protected String seriesname;
	protected String color;
    protected String alpha;
	protected Integer showValues;
    protected Integer includeInLegend;
    
    protected LinkedList<SetXYPlotElement> sets = new LinkedList<SetXYPlotElement>();
    
    
    public DatasetXYPlot(Map values) {
        super();
        for (Iterator iter = values.values().iterator(); iter.hasNext();) {
            SetXYPlotElement set = (SetXYPlotElement) iter.next();
            this.sets.add(set);
        }
        
    }
    public DatasetXYPlot() {
        super();
    }
    
    
    public LinkedList<SetXYPlotElement> getSets() {
        return sets;
    }
    public void setSets(LinkedList<SetXYPlotElement> sets) {
        this.sets = sets;
    }
    public void addSet(SetXYPlotElement set){
        if(this.sets == null){
            this.sets = new LinkedList<SetXYPlotElement>();
        }
        this.sets.add(set);
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
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
    public String getAlpha() {
		return alpha;
	}
	public void setAlpha(String alpha) {
		this.alpha = alpha;
	}
	public Integer getIncludeInLegend() {
		return includeInLegend;
	}
	public void setIncludeInLegend(Integer includeInLegend) {
		this.includeInLegend = includeInLegend;
	}

	
	public void serializeMe(Element element) {        
		SerializeChart.serializeThat(element, this);
        
        for (Iterator<SetXYPlotElement> iter = getSets().iterator(); iter.hasNext();) {
            SetXYPlotElement setElement = (SetXYPlotElement) iter.next();
            Element set = DocumentHelper.createElement("set");
            SerializeChart.serializeThat(set,setElement);
            element.add(set);
        }        
    }
}
