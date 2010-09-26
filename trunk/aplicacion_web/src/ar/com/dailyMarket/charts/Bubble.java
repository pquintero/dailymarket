package ar.com.dailyMarket.charts;

import java.util.List;

import ar.com.dailyMarket.charts.elements.CategoriesXYPlotElement;

public class Bubble extends XYPlot {

	private Double bubbleScale;
	private Integer is3D;
    private Integer clipBubbles;
    private Integer showPlotBorder;
		
	
	public Bubble(CategoriesXYPlotElement categories, List list){
	    super(categories, list);	    
	}
		
    public Double getBubbleScale() {
        return bubbleScale;
    }
    public void setBubbleScale(Double bubbleScale) {
        this.bubbleScale = bubbleScale;
    }
    public void setIs3D(Integer is3D) {
        this.is3D = is3D;
    }
    public Integer getIs3D() {
		return is3D;
	}    
	public Integer getClipBubbles() {
		return clipBubbles;
	}
	public void setClipBubbles(Integer clipBubbles) {
		this.clipBubbles = clipBubbles;
	}
	public Integer getShowPlotBorder() {
		return showPlotBorder;
	}
	public void setShowPlotBorder(Integer showPlotBorder) {
		this.showPlotBorder = showPlotBorder;
	}
}
