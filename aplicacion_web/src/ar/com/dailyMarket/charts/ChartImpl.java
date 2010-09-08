package ar.com.dailyMarket.charts;

import java.io.IOException;
import java.io.OutputStream;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import ar.com.dailyMarket.charts.elements.SerializeChart;


public abstract class ChartImpl implements Chart {

//	private DatasetElement dataset = new DatasetElement();
	
	private String bgColor = new String("ffffff");
	private Integer bgAlpha;
	private String bgSWF;
	
	private String canvasBgColor = new String("ffffff");
	private Integer canvasBgAlpha;
	private String canvasBorderColor = new String("ffffff");
	private Integer canvasBorderThickness;
	
	private Integer palette;
	private Integer animation;
	private String caption;
	private String subcaption;

	private Integer showLabels;
	private Integer showValues;
	
	private String baseFont;
	private Integer baseFontSize;
	private String baseFontColor;
	    
	private String numberPrefix;
	private String numberSuffix;
	private Integer formatNumber;
	private Integer formatNumberScale;
	private String decimalSeparator = ".";
	private String thousandSeparator = ",";
	private Integer decimalPrecision;
	private Integer decimals;
	
	private Integer showhovercap;
	private String hoverCapBgColor;
	private String hoverCapBorderColor;
	private String hoverCapSepChar;
	
	private Integer showLimits;
	

	public ChartImpl(){
	    super();
	}
	
	public Integer getDecimals() {
		return decimals;
	}
	public void setDecimals(Integer decimals) {
		this.decimals = decimals;
	}
	public Integer getPalette() {
		return palette;
	}
	public void setPalette(Integer palette) {
		this.palette = palette;
	}
	public Integer getAnimation() {
		return animation;
	}
	public void setAnimation(Integer animation) {
		this.animation = animation;
	}
	public String getCanvasBgColor() {
        return canvasBgColor;
    }
    public void setCanvasBgColor(String canvasBgColor) {
        this.canvasBgColor = canvasBgColor;
    }
    public String getBaseFont() {
        return baseFont;
    }
    public String getBaseFontColor() {
        return baseFontColor;
    }
    public Integer getBaseFontSize() {
        return baseFontSize;
    }
    public Integer getDecimalPrecision() {
        return decimalPrecision;
    }
    public String getDecimalSeparator() {
        return decimalSeparator;
    }
    public Integer getFormatNumber() {
        return formatNumber;
    }
    public Integer getFormatNumberScale() {
        return formatNumberScale;
    }
    public String getHoverCapBgColor() {
        return hoverCapBgColor;
    }
    public String getHoverCapBorderColor() {
        return hoverCapBorderColor;
    }
    public String getHoverCapSepChar() {
        return hoverCapSepChar;
    }
    public String getNumberPrefix() {
        return numberPrefix;
    }
    public String getNumberSuffix() {
        return numberSuffix;
    }
    public Integer getShowhovercap() {
        return showhovercap;
    }
    public String getThousandSeparator() {
        return thousandSeparator;
    }
    public void setBaseFont(String baseFont) {
        this.baseFont = baseFont;
    }
    public void setBaseFontColor(String baseFontColor) {
        this.baseFontColor = baseFontColor;
    }
    public void setBaseFontSize(Integer baseFontSize) {
        this.baseFontSize = baseFontSize;
    }
    public void setDecimalPrecision(Integer decimalPrecision) {
        this.decimalPrecision = decimalPrecision;
    }
    public void setDecimalSeparator(String decimalSeparator) {
        this.decimalSeparator = decimalSeparator;
    }
    public void setFormatNumber(Integer formatNumber) {
        this.formatNumber = formatNumber;
    }
    public void setFormatNumberScale(Integer formatNumberScale) {
        this.formatNumberScale = formatNumberScale;
    }
    public void setHoverCapBgColor(String hoverCapBgColor) {
        this.hoverCapBgColor = hoverCapBgColor;
    }
    public void setHoverCapBorderColor(String hoverCapBorderColor) {
        this.hoverCapBorderColor = hoverCapBorderColor;
    }
    public void setHoverCapSepChar(String hoverCapSepChar) {
        this.hoverCapSepChar = hoverCapSepChar;
    }
    public void setNumberPrefix(String numberPrefix) {
        this.numberPrefix = numberPrefix;
    }
    public void setNumberSuffix(String numberSuffix) {
        this.numberSuffix = numberSuffix;
    }
    public void setShowhovercap(Integer showhovercap) {
        this.showhovercap = showhovercap;
    }
    public void setThousandSeparator(String thousandSeparator) {
        this.thousandSeparator = thousandSeparator;
    }
    public Integer getShowLimits() {
        return showLimits;
    }
    public Integer getShowLabels() {
        return showLabels;
    }
    public Integer getShowValues() {
        return showValues;
    }
    public void setShowLimits(Integer showLimits) {
        this.showLimits = showLimits;
    }
    public void setShowLabels(Integer showLabels) {
        this.showLabels = showLabels;
    }
    public void setShowValues(Integer showValues) {
        this.showValues = showValues;
    }
    public Integer getBgAlpha() {
        return bgAlpha;
    }
    public void setBgAlpha(Integer bgAlpha) {
        this.bgAlpha = bgAlpha;
    }
    public String getBgColor() {
        return bgColor;
    }
    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }
    public String getBgSWF() {
        return bgSWF;
    }
    public void setBgSWF(String bgSWF) {
        this.bgSWF = bgSWF;
    }
    public Integer getCanvasBgAlpha() {
        return canvasBgAlpha;
    }
    public void setCanvasBgAlpha(Integer canvasBgAlpha) {
        this.canvasBgAlpha = canvasBgAlpha;
    }
    public String getCanvasBorderColor() {
        return canvasBorderColor;
    }
    public void setCanvasBorderColor(String canvasBorderColor) {
        this.canvasBorderColor = canvasBorderColor;
    }
    public Integer getCanvasBorderThickness() {
        return canvasBorderThickness;
    }
    public void setCanvasBorderThickness(Integer canvasBorderThickness) {
        this.canvasBorderThickness = canvasBorderThickness;
    }
    public String getCaption() {
        return caption;
    }
    public void setCaption(String caption) {
        this.caption = caption;
    }
    public String getSubcaption() {
        return subcaption;
    }
    public void setSubcaption(String subcaption) {
        this.subcaption = subcaption;
    }
   
//    public DatasetElement getDataset() {
//        return dataset;
//    }
//    public void setDataset(DatasetElement dataset) {
//        this.dataset = dataset;
//    }
//    
    
    public void writeXML(OutputStream os) throws IOException {  
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("chart");
        
        setChartAttributes(root);          
        String xml = root.asXML();
       	os.write(xml.getBytes());
    }

    public Element setChartAttributes(Element root) {        
    	SerializeChart.serializeThat(root, this);
    	return root;
    }
}