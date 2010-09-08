package ar.com.dailyMarket.charts.elements;


public class CategoryXYPlotElement extends CategoryElement{

    private Integer x;
    private Integer showVerticalLine;
    private Integer lineDashed;
    
    
    public CategoryXYPlotElement(String label) {
        super(label);
    }
    
    public CategoryXYPlotElement(){
    	super();
    }
    
    public Integer getShowVerticalLine() {
        return showVerticalLine;
    }
    public void setShowVerticalLine(Integer showVerticalLine) {
        this.showVerticalLine = showVerticalLine;
    }
    public Integer getX() {
        return x;
    }
    public void setX(Integer x) {
        this.x = x;
    }
	public Integer getLineDashed() {
		return lineDashed;
	}
	public void setLineDashed(Integer lineDashed) {
		this.lineDashed = lineDashed;
	}
}