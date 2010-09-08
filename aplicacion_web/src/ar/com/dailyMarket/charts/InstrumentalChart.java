package ar.com.dailyMarket.charts;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import ar.com.dailyMarket.charts.elements.DatasetElement;
import ar.com.dailyMarket.charts.elements.SerializeChart;


public abstract class InstrumentalChart implements Chart {

	private Map values;	
	private DatasetElement dataset = new DatasetElement();
	
	private String bgColor = new String("ffffff");
	private Integer bgAlpha;
	private String bgSWF;
	
	private String baseFont;
	private Integer baseFontSize;
	private String baseFontColor;
	    
	private String numberPrefix = "$";
	private String numberSuffix;
	private Integer formatNumber;
	private Integer formatNumberScale;
	private String decimalSeparator = ".";
	private String thousandSeparator = ",";
	private Integer decimalPrecision;
	
	private Integer showBorder;
	private String borderColor;
	private Integer borderThickness;
	private Integer borderAlpha;

	private Integer chartLeftMargin;
	private Integer chartRightMargin;
	private Integer chartTopMargin;
	private Integer chartBottomMargin;

	
	public InstrumentalChart(){
	    super();
	}
	
	public InstrumentalChart(Map values){
	    super();
	    this.values = values;
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
    public Integer getChartBottomMargin() {
        return chartBottomMargin;
    }
    public Integer getChartLeftMargin() {
        return chartLeftMargin;
    }
    public Integer getChartRightMargin() {
        return chartRightMargin;
    }
    public Integer getChartTopMargin() {
        return chartTopMargin;
    }
    public Integer getShowBorder() {
        return showBorder;
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
    public void setChartBottomMargin(Integer chartBottomMargin) {
        this.chartBottomMargin = chartBottomMargin;
    }
    public void setChartLeftMargin(Integer chartLeftMargin) {
        this.chartLeftMargin = chartLeftMargin;
    }
    public void setChartRightMargin(Integer chartRightMargin) {
        this.chartRightMargin = chartRightMargin;
    }
    public void setChartTopMargin(Integer chartTopMargin) {
        this.chartTopMargin = chartTopMargin;
    }
    public void setShowBorder(Integer showBorder) {
        this.showBorder = showBorder;
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
    public String getNumberPrefix() {
        return numberPrefix;
    }
    public String getNumberSuffix() {
        return numberSuffix;
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
    public void setNumberPrefix(String numberPrefix) {
        this.numberPrefix = numberPrefix;
    }
    public void setNumberSuffix(String numberSuffix) {
        this.numberSuffix = numberSuffix;
    }
    public void setThousandSeparator(String thousandSeparator) {
        this.thousandSeparator = thousandSeparator;
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
    public Map getValues() {
        return values;
    }
    public void setValues(Map values) {
        this.values = values;
    }
    public DatasetElement getDataset() {
        return dataset;
    }
    public void setDataset(DatasetElement dataset) {
        this.dataset = dataset;
    }
    
    
    public void writeXML(OutputStream os) throws IOException{  
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("Chart");
        
        setChartAttributes(root);
        
        String xml = root.asXML();
       	os.write(xml.getBytes());
    }

    public Element setChartAttributes(Element root) {        
    	SerializeChart.serializeThat(root, this);
    	return root;
    }
}
