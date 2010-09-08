package ar.com.dailyMarket.charts.elements;

public class LineElement {
	private Integer startValue;
	private Integer endValue;
	private String displayValue;
	private String color;
	private Integer isTrendZone;
	private Integer thickness;
	
	public Integer getStartValue() {
		return startValue;
	}
	public void setStartValue(Integer startValue) {
		this.startValue = startValue;
	}
	public Integer getEndValue() {
		return endValue;
	}
	public void setEndValue(Integer endValue) {
		this.endValue = endValue;
	}
	public String getDisplayValue() {
		return displayValue;
	}
	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public Integer getIsTrendZone() {
		return isTrendZone;
	}
	public void setIsTrendZone(Integer isTrendZone) {
		this.isTrendZone = isTrendZone;
	}
	public Integer getThickness() {
		return thickness;
	}
	public void setThickness(Integer thickness) {
		this.thickness = thickness;
	}
}