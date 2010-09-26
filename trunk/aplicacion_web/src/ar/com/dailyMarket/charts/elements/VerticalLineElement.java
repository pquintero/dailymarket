package ar.com.dailyMarket.charts.elements;

public class VerticalLineElement implements Lines {
	//vLines
	private String color;
	private Integer thickness;
	private Integer dashed;
	private String label;
	private Integer showLabelBorder;
	private Integer linePosition;
	private Integer labelPosition;
	
	public String elementName() {
		return "vLine";
	}

	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public Integer getThickness() {
		return thickness;
	}
	public void setThickness(Integer thickness) {
		this.thickness = thickness;
	}
	public Integer getDashed() {
		return dashed;
	}
	public void setDashed(Integer dashed) {
		this.dashed = dashed;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public Integer getShowLabelBorder() {
		return showLabelBorder;
	}
	public void setShowLabelBorder(Integer showLabelBorder) {
		this.showLabelBorder = showLabelBorder;
	}
	public Integer getLinePosition() {
		return linePosition;
	}
	public void setLinePosition(Integer linePosition) {
		this.linePosition = linePosition;
	}
	public Integer getLabelPosition() {
		return labelPosition;
	}
	public void setLabelPosition(Integer labelPosition) {
		this.labelPosition = labelPosition;
	}
}