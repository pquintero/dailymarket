package ar.com.dailyMarket.charts.elements;

public class TrendlineElement extends LineElement {
	
	private Integer showOnTop;
	private Integer valueOnRight;
	private Integer dashed;
	private Integer dashGap;
	
	public Integer getShowOnTop() {
		return showOnTop;
	}
	public void setShowOnTop(Integer showOnTop) {
		this.showOnTop = showOnTop;
	}
	public Integer getValueOnRight() {
		return valueOnRight;
	}
	public void setValueOnRight(Integer valueOnRight) {
		this.valueOnRight = valueOnRight;
	}
	public Integer getDashed() {
		return dashed;
	}
	public void setDashed(Integer dashed) {
		this.dashed = dashed;
	}
	public Integer getDashGap() {
		return dashGap;
	}
	public void setDashGap(Integer dashGap) {
		this.dashGap = dashGap;
	}
}