package ar.com.dailyMarket.charts.elements;



public class AngularDial{

    private String name;
    
    private Double value;
    private String link;    
    private String bgColor;
    
    private Integer radius;
    private Integer baseWidth;
    private Integer topWidth;
	private String borderColor;
	private Integer borderThickness;
	private Integer borderAlpha;

    
    public Integer getBaseWidth() {
        return baseWidth;
    }
    public String getBgColor() {
        return bgColor;
    }
    public Integer getBorderAlpha() {
        return borderAlpha;
    }
    public String getBorderColor() {
        return borderColor;
    }
    public Integer getBorderThickness() {
        return borderThickness;
    }
    public String getLink() {
        return link;
    }
    public String getName() {
        return name;
    }
    public Integer getRadius() {
        return radius;
    }
    public Integer getTopWidth() {
        return topWidth;
    }
    public void setBaseWidth(Integer baseWidth) {
        this.baseWidth = baseWidth;
    }
    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }
    public void setBorderAlpha(Integer borderAlpha) {
        this.borderAlpha = borderAlpha;
    }
    public void setBorderColor(String borderColor) {
        this.borderColor = borderColor;
    }
    public void setBorderThickness(Integer borderThickness) {
        this.borderThickness = borderThickness;
    }
    public void setLink(String link) {
        this.link = link;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setRadius(Integer radius) {
        this.radius = radius;
    }
    public void setTopWidth(Integer topWidth) {
        this.topWidth = topWidth;
    }
    public Double getValue() {
        return value;
    }
    public void setValue(Double value) {
        this.value = value;
    }
}