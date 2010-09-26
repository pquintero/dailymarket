package ar.com.dailyMarket.charts;

import java.util.List;

public class ColumnChart2D extends ColumnChart {
	private Integer canvasBorderThickness;
	private Integer zeroPlaneThickness;
	private Integer showAlternateHGridColor;
	private String alternateHGridColor;
	private Integer alternateHGridAlpha;
	
	public ColumnChart2D(List values){
	    super(values);
	}
	
    public Integer getAlternateHGridAlpha() {
        return alternateHGridAlpha;
    }
    public String getAlternateHGridColor() {
        return alternateHGridColor;
    }
    public Integer getCanvasBorderThickness() {
        return canvasBorderThickness;
    }
    public Integer getShowAlternateHGridColor() {
        return showAlternateHGridColor;
    }
    public Integer getZeroPlaneThickness() {
        return zeroPlaneThickness;
    }
    public void setAlternateHGridAlpha(Integer alternateHGridAlpha) {
        this.alternateHGridAlpha = alternateHGridAlpha;
    }
    public void setAlternateHGridColor(String alternateHGridColor) {
        this.alternateHGridColor = alternateHGridColor;
    }
    public void setCanvasBorderThickness(Integer canvasBorderThickness) {
        this.canvasBorderThickness = canvasBorderThickness;
    }
    public void setShowAlternateHGridColor(Integer showAlternateHGridColor) {
        this.showAlternateHGridColor = showAlternateHGridColor;
    }
    public void setZeroPlaneThickness(Integer zeroPlaneThickness) {
        this.zeroPlaneThickness = zeroPlaneThickness;
    }
}
