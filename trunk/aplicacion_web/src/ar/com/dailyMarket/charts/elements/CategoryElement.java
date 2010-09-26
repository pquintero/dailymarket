package ar.com.dailyMarket.charts.elements;



public class CategoryElement implements Lines{

    private String label;
    private Integer showLabel;
    private String toolText;
    
    public String elementName() {
		return "category";
	}
    
    public CategoryElement(){
    	super();
    }
    
    public CategoryElement(String label) {
        super();
        this.setLabel(label);        
    }
    
    public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public Integer getShowLabel() {
		return showLabel;
	}
	public void setShowLabel(Integer showLabel) {
		this.showLabel = showLabel;
	}
	public String getToolText() {
		return toolText;
	}
	public void setToolText(String toolText) {
		this.toolText = toolText;
	}
}