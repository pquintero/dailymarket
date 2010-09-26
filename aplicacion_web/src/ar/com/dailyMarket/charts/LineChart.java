package ar.com.dailyMarket.charts;

import java.util.List;


public class LineChart extends ColumnChart {

	private String lineColor;
	private Integer lineThickness;
	private Integer lineAlpha;
	private Integer showShadow;
	
	private Integer drawAnchors;
	private Integer anchorSides;
	private Integer anchorRadius;	
	private String anchorBorderColor;
	private Integer anchorBorderThickness;
	private String anchorBgColor;
	private Integer anchorBgAlpha;
	private Integer anchorAlpha;
		
	private Integer zeroPlaneThickness;
	private Integer showAlternateHGridColor;
	private String alternateHGridColor;
	private Integer alternateHGridAlpha;
	
	public LineChart(List values){
	    super(values);
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
    public Integer getDrawAnchors() {
        return drawAnchors;
    }
    public Integer getShowShadow() {
        return showShadow;
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
    public void setDrawAnchors(Integer drawAnchors) {
        this.drawAnchors = drawAnchors;
    }
    public void setShowShadow(Integer showShadow) {
        this.showShadow = showShadow;
    }
    public Integer getAlternateHGridAlpha() {
        return alternateHGridAlpha;
    }
    public String getAlternateHGridColor() {
        return alternateHGridColor;
    }
    public Integer getShowAlternateHGridColor() {
        return showAlternateHGridColor;
    }
    public Integer getZeroPlaneThickness() {
        return zeroPlaneThickness;
    }
    public void setAlternateHGridAlpha(Integer alternateHGridAlpha) {
        this.alternateHGridAlpha = alternateHGridAlpha;
    }
    public void setAlternateHGridColor(String alternateHGridColor) {
        this.alternateHGridColor = alternateHGridColor;
    }
    public void setShowAlternateHGridColor(Integer showAlternateHGridColor) {
        this.showAlternateHGridColor = showAlternateHGridColor;
    }
    public void setZeroPlaneThickness(Integer zeroPlaneThickness) {
        this.zeroPlaneThickness = zeroPlaneThickness;
    }
}