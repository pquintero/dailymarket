package ar.com.dailyMarket.charts;

import java.util.List;


public class PieChart3D extends PieChart2D {

    protected Integer pieSliceDepth;
    protected Integer pieYScale;
	
	
	public PieChart3D(List values){
	    super(values);
	}
	
    
    public Integer getPieSliceDepth() {
        return pieSliceDepth;
    }
    public Integer getPieYScale() {
        return pieYScale;
    }
    public void setPieSliceDepth(Integer pieSliceDepth) {
        this.pieSliceDepth = pieSliceDepth;
    }
    public void setPieYScale(Integer pieYScale) {
        this.pieYScale = pieYScale;
    }
}