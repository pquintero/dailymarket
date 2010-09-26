package ar.com.dailyMarket.charts;

import java.util.List;

public class Combi2DSingleY extends MSColumn2DLine {
	
	private Integer drawAnchors;
	private Integer anchorSides;
	private Integer anchorRadius;
	private String anchorBorderColor;
	private Integer anchorBorderThickness;
	private String anchorBgColor;
	private Integer anchorBgAlpha;
	private Integer anchorAlpha;
	
	
	public Combi2DSingleY(List value) {
		super(value);
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
	public String getAnchorBgColor() {
		return anchorBgColor;
	}
	public void setAnchorBgColor(String anchorBgColor) {
		this.anchorBgColor = anchorBgColor;
	}
	public Integer getAnchorBgAlpha() {
		return anchorBgAlpha;
	}
	public void setAnchorBgAlpha(Integer anchorBgAlpha) {
		this.anchorBgAlpha = anchorBgAlpha;
	}
	public Integer getAnchorAlpha() {
		return anchorAlpha;
	}
	public void setAnchorAlpha(Integer anchorAlpha) {
		this.anchorAlpha = anchorAlpha;
	}
}
