package ar.com.dailyMarket.charts.elements;

import java.util.Map;

public class DatasetScatter extends DatasetXYPlot {
	
	private Integer drawAnchors;
	private Integer anchorSides; 
	private Integer anchorRadius;
	private String anchorBorderColor; 
	private Integer anchorBorderThickness;
	private Integer drawLine;
	private String lineColor;
	private Integer lineThickness;
	
	
	public DatasetScatter(Map values) {
		super(values);
	}
	
	public DatasetScatter() {
        super();
    }
	
	public Integer getDrawAnchors() {
		return drawAnchors;
	}
	public void setDrawAnchors(Integer drawAnchors) {
		this.drawAnchors = drawAnchors;
	}
	public Integer getAnchorSides() {
		return anchorSides;
	}
	public void setAnchorSides(Integer anchorSides) {
		this.anchorSides = anchorSides;
	}
	public Integer getAnchorRadius() {
		return anchorRadius;
	}
	public void setAnchorRadius(Integer anchorRadius) {
		this.anchorRadius = anchorRadius;
	}
	public String getAnchorBorderColor() {
		return anchorBorderColor;
	}
	public void setAnchorBorderColor(String anchorBorderColor) {
		this.anchorBorderColor = anchorBorderColor;
	}
	public Integer getAnchorBorderThickness() {
		return anchorBorderThickness;
	}
	public void setAnchorBorderThickness(Integer anchorBorderThickness) {
		this.anchorBorderThickness = anchorBorderThickness;
	}
	public Integer getDrawLine() {
		return drawLine;
	}
	public void setDrawLine(Integer drawLine) {
		this.drawLine = drawLine;
	}
	public String getLineColor() {
		return lineColor;
	}
	public void setLineColor(String lineColor) {
		this.lineColor = lineColor;
	}
	public Integer getLineThickness() {
		return lineThickness;
	}
	public void setLineThickness(Integer lineThickness) {
		this.lineThickness = lineThickness;
	}
}
