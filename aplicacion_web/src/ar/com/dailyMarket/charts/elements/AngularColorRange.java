package ar.com.dailyMarket.charts.elements;



public class AngularColorRange{

    private String name;
    private Double minValue;
    private Double maxValue;    
    private String code;
    private String borderColor;
    private Integer alpha;
    
    
    
    
    public Integer getAlpha() {
        return alpha;
    }
    public String getBorderColor() {
        return borderColor;
    }
    public String getCode() {
        return code;
    }
    public Double getMaxValue() {
        return maxValue;
    }
    public Double getMinValue() {
        return minValue;
    }
    public String getName() {
        return name;
    }
    public void setAlpha(Integer alpha) {
        this.alpha = alpha;
    }
    public void setBorderColor(String borderColor) {
        this.borderColor = borderColor;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
    }
    public void setMinValue(Double minValue) {
        this.minValue = minValue;
    }
    public void setName(String name) {
        this.name = name;
    }
}
