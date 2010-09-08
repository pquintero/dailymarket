package ar.com.dailyMarket.charts;

import java.util.List;




public class ColumnChart3D extends ColumnChart {

	
	private Integer canvasBaseColor;
	private Integer canvasBaseDepth;
	private Integer canvasBgDepth;
	private Integer showCanvasBase;
	private Integer showCanvasBg;

	private Integer zeroPlaneShowBorder;
	private String zeroPlaneBorderColor;
	

	
	public ColumnChart3D(List values){
	    super(values);
	}
    public ColumnChart3D() {
        super();
    }

    public Integer getCanvasBaseColor() {
        return canvasBaseColor;
    }
    public Integer getCanvasBaseDepth() {
        return canvasBaseDepth;
    }
    public Integer getCanvasBgDepth() {
        return canvasBgDepth;
    }
    public Integer getShowCanvasBase() {
        return showCanvasBase;
    }
    public Integer getShowCanvasBg() {
        return showCanvasBg;
    }
    public String getZeroPlaneBorderColor() {
        return zeroPlaneBorderColor;
    }
    public Integer getZeroPlaneShowBorder() {
        return zeroPlaneShowBorder;
    }
    public void setCanvasBaseColor(Integer canvasBaseColor) {
        this.canvasBaseColor = canvasBaseColor;
    }
    public void setCanvasBaseDepth(Integer canvasBaseDepth) {
        this.canvasBaseDepth = canvasBaseDepth;
    }
    public void setCanvasBgDepth(Integer canvasBgDepth) {
        this.canvasBgDepth = canvasBgDepth;
    }
    public void setShowCanvasBase(Integer showCanvasBase) {
        this.showCanvasBase = showCanvasBase;
    }
    public void setShowCanvasBg(Integer showCanvasBg) {
        this.showCanvasBg = showCanvasBg;
    }
    public void setZeroPlaneBorderColor(String zeroPlaneBorderColor) {
        this.zeroPlaneBorderColor = zeroPlaneBorderColor;
    }
    public void setZeroPlaneShowBorder(Integer zeroPlaneShowBorder) {
        this.zeroPlaneShowBorder = zeroPlaneShowBorder;
    }
}