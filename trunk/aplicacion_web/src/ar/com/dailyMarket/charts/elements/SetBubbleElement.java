package ar.com.dailyMarket.charts.elements;



public class SetBubbleElement extends SetXYPlotElement {
    
	private Double z;
	private Integer alpha;
	private String color;
	private String name;
		
	public Integer getAlpha() {
		return alpha;
	}
	public void setAlpha(Integer alpha) {
		this.alpha = alpha;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setZ(Double z) {
        this.z = z;
    }
	public Double getZ() {
        return z;
    }
}
