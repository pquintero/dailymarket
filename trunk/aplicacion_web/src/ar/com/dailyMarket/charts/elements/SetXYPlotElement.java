package ar.com.dailyMarket.charts.elements;


public class SetXYPlotElement extends SetChartElement {
	private Double x;
    private Double y;
    private boolean newWindowLink = false;

    public String elementName() {
		return "set";
	}
    
	public Double getX() {
        return x;
    }
    public Double getY() {
        return y;
    }
    public void setX(Double x) {
        this.x = x;
    }
    public void setY(Double y) {
        this.y = y;
    }
    public boolean isNewWindowLink() {
        return newWindowLink;
    }
    public void setNewWindowLink(boolean newWindowLink) {
        this.newWindowLink = newWindowLink;
    }
}