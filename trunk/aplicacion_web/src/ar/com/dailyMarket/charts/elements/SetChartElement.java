package ar.com.dailyMarket.charts.elements;

public abstract class SetChartElement implements Lines {
    private Integer showValue;
    private String link;
    private String displayValue;
    
    public Integer getShowValue() {
		return showValue;
	}
	public void setShowValue(Integer showValue) {
		this.showValue = showValue;
	}
	public String getDisplayValue() {
		return displayValue;
	}
	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}
    public String getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }
}
