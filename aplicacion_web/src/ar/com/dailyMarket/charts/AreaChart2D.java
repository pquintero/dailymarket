package ar.com.dailyMarket.charts;

import java.util.List;

public class AreaChart2D extends ColumnChart {
	
	private Integer drawAnchors; 
	private Integer labelStep;
	private Integer yAxisValuesStep;
	private Integer numVDivLines;
	
	public AreaChart2D(List values){
		super(values);
	}
	
	public Integer getDrawAnchors() {
		return drawAnchors;
	}
	public void setDrawAnchors(Integer drawAnchors) {
		this.drawAnchors = drawAnchors;
	}
	public Integer getLabelStep() {
		return labelStep;
	}
	public void setLabelStep(Integer labelStep) {
		this.labelStep = labelStep;
	}
	public Integer getYAxisValuesStep() {
		return yAxisValuesStep;
	}
	public void setYAxisValuesStep(Integer axisValuesStep) {
		yAxisValuesStep = axisValuesStep;
	}
	public Integer getNumVDivLines() {
		return numVDivLines;
	}
	public void setNumVDivLines(Integer numVDivLines) {
		this.numVDivLines = numVDivLines;
	}
}
