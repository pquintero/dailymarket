package ar.com.dailyMarket.charts.elements;



public class SetElement extends SetChartElement {

    private String label;
    private String value;
    private String color;
    private String toolText;
    private Integer alpha;
    
    private Integer showLabel;
    
    public String elementName() {
		return "set";
	}
    
    public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public Integer getAlpha() {
        return alpha;
    }
    public String getColor() {
        return color;
    }
    public String getToolText() {
        return toolText;
    }
    public Integer getShowLabel() {
        return showLabel;
    }
    public String getValue() {
        return value;
    }
    public void setAlpha(Integer alpha) {
        this.alpha = alpha;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public void setToolText(String hoverText) {
        this.toolText = hoverText;
    }
    public void setShowLabel(Integer showName) {        
        this.showLabel = showName;
    }
    public void setValue(String value) {        
        this.value = value;
    }
}