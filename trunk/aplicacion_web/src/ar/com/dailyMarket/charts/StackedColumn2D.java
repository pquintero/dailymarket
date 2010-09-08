package ar.com.dailyMarket.charts;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import ar.com.dailyMarket.charts.elements.DatasetElement;
import ar.com.dailyMarket.charts.elements.Lines;
import ar.com.dailyMarket.charts.elements.SerializeChart;

public class StackedColumn2D extends ColumnChart {
	private String plotGradientColor;
	private Double plotFillRatio;
	private Double plotFillAlpha; 
	private Double plotFillAngle;
	
	private List<DatasetElement> datasets = new LinkedList<DatasetElement>();
	private List<Lines> categories = new LinkedList<Lines>();
	
	public StackedColumn2D(List list){
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

	public String getPlotGradientColor() {
		return plotGradientColor;
	}
	public void setPlotGradientColor(String plotGradientColor) {
		this.plotGradientColor = plotGradientColor;
	}
	public Double getPlotFillRatio() {
		return plotFillRatio;
	}
	public void setPlotFillRatio(Double plotFillRatio) {
		this.plotFillRatio = plotFillRatio;
	}
	public Double getPlotFillAlpha() {
		return plotFillAlpha;
	}
	public void setPlotFillAlpha(Double plotFillAlpha) {
		this.plotFillAlpha = plotFillAlpha;
	}
	public Double getPlotFillAngle() {
		return plotFillAngle;
	}
	public void setPlotFillAngle(Double plotFillAngle) {
		this.plotFillAngle = plotFillAngle;
	}
	public List<DatasetElement> getDatasets() {
		return datasets;
	}
	public void setDatasets(List<DatasetElement> datasets) {
		this.datasets = datasets;
	}
	public List<Lines> getCategories() {
		return categories;
	}
	public void setCategories(List<Lines> categories) {
		this.categories = categories;
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
