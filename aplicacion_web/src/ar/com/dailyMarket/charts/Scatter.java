package ar.com.dailyMarket.charts;

import java.util.List;

import ar.com.dailyMarket.charts.elements.CategoriesXYPlotElement;

public class Scatter extends XYPlot{
	
	private Integer rotateValues;
	
	public Scatter(CategoriesXYPlotElement categories, List list) {
		super(categories, list);
	}
	
	public Integer getRotateValues() {
		return rotateValues;
	}
	public void setRotateValues(Integer rotateValues) {
		this.rotateValues = rotateValues;
	}
}
