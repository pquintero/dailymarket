package ar.com.dailyMarket.charts.elements;

import java.util.Map;



public class DatasetBubbleElement extends DatasetXYPlot {
	
	private Integer showPlotBorder;
	private String plotBorderColor;
	private Integer plotBorderThickness;
	private Integer plotBorderAlpha;
	
	
	public DatasetBubbleElement(Map values) {
        super(values);
    }
	
	public DatasetBubbleElement() {
        super();
    }
	
	public Integer getShowPlotBorder() {
		return showPlotBorder;
	}
	public void setShowPlotBorder(Integer showPlotBorder) {
		this.showPlotBorder = showPlotBorder;
	}
	public String getPlotBorderColor() {
		return plotBorderColor;
	}
	public void setPlotBorderColor(String plotBorderColor) {
		this.plotBorderColor = plotBorderColor;
	}
	public Integer getPlotBorderThickness() {
		return plotBorderThickness;
	}
	public void setPlotBorderThickness(Integer plotBorderThickness) {
		this.plotBorderThickness = plotBorderThickness;
	}
	public Integer getPlotBorderAlpha() {
		return plotBorderAlpha;
	}
	public void setPlotBorderAlpha(Integer plotBorderAlpha) {
		this.plotBorderAlpha = plotBorderAlpha;
	}
}
