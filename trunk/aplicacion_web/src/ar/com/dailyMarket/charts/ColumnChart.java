package ar.com.dailyMarket.charts;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import ar.com.dailyMarket.charts.elements.Lines;
import ar.com.dailyMarket.charts.elements.SerializeChart;

public class ColumnChart extends AxisChart {
	
	private List<Lines> values = new LinkedList<Lines>();
	
	private String canvasBgColor = new String("ffffff");
	
	private String xAxisName;
	private String yAxisName;
	
	private String yAxisMinValue;
	private String yAxisMaxValue;
	
	private Integer showLimits;
	private Integer yAxisValuesStep;
	private Integer slantLabels;
	private Integer rotateLabels;

	private String outCnvBaseFont;
	private Integer outCnvBaseFontSize;
	private String outCnvBaseFontColor;
	
	private Integer divLineDecimalPrecision;
	private Integer limitsDecimalPrecision;
	
	private String zeroPlaneColor;
	private Integer zeroPlaneAlpha;
	
	private Integer numdivlines;
	private String divlinecolor;
	private Integer divLineThickness;
	private Integer divLineAlpha;
	private Integer showDivLineValues;
	
	private Integer chartLeftMargin;
	private Integer chartRightMargin;
	private Integer chartTopMargin;
	private Integer chartBottomMargin;
	
	
	public ColumnChart(List values){
	    super();
	    this.setValues(values);
	}
    public ColumnChart() {
        super();
    }


    public Integer getYAxisValuesStep() {
		return yAxisValuesStep;
	}
	public void setYAxisValuesStep(Integer axisValuesStep) {
		yAxisValuesStep = axisValuesStep;
	}
	public Integer getSlantLabels() {
		return slantLabels;
	}
	public void setSlantLabels(Integer slantLabels) {
		this.slantLabels = slantLabels;
	}
	public String getYAxisMaxValue() {
		return yAxisMaxValue;
	}
	public void setYAxisMaxValue(String axisMaxValue) {
		yAxisMaxValue = axisMaxValue;
	}
	public String getCanvasBgColor() {
        return canvasBgColor;
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
    public Integer getDivLineAlpha() {
        return divLineAlpha;
    }
    public String getDivlinecolor() {
        return divlinecolor;
    }
    public Integer getDivLineDecimalPrecision() {
        return divLineDecimalPrecision;
    }
    public Integer getDivLineThickness() {
        return divLineThickness;
    }
    public Integer getLimitsDecimalPrecision() {
        return limitsDecimalPrecision;
    }
    public Integer getNumdivlines() {
        return numdivlines;
    }
    public String getOutCnvBaseFont() {
        return outCnvBaseFont;
    }
    public String getOutCnvBaseFontColor() {
        return outCnvBaseFontColor;
    }
    public Integer getOutCnvBaseFontSize() {
        return outCnvBaseFontSize;
    }
    public Integer getRotateLabels() {
        return rotateLabels;
    }
    public Integer getShowDivLineValues() {
        return showDivLineValues;
    }
    public Integer getShowLimits() {
        return showLimits;
    }
    public String getXAxisName() {
        return xAxisName;
    }
    public String getYAxisMinValue() {
        return yAxisMinValue;
    }
    public String getYAxisName() {
        return yAxisName;
    }
    public Integer getZeroPlaneAlpha() {
        return zeroPlaneAlpha;
    }
    public String getZeroPlaneColor() {
        return zeroPlaneColor;
    }
    public void setCanvasBgColor(String canvasBgColor) {
        this.canvasBgColor = canvasBgColor;
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
    public void setDivLineAlpha(Integer divLineAlpha) {
        this.divLineAlpha = divLineAlpha;
    }
    public void setDivlinecolor(String divlinecolor) {
        this.divlinecolor = divlinecolor;
    }
    public void setDivLineDecimalPrecision(Integer divLineDecimalPrecision) {
        this.divLineDecimalPrecision = divLineDecimalPrecision;
    }
    public void setDivLineThickness(Integer divLineThickness) {
        this.divLineThickness = divLineThickness;
    }
    public void setLimitsDecimalPrecision(Integer limitsDecimalPrecision) {
        this.limitsDecimalPrecision = limitsDecimalPrecision;
    }
    public void setNumdivlines(Integer numdivlines) {
        this.numdivlines = numdivlines;
    }
    public void setOutCnvBaseFont(String outCnvBaseFont) {
        this.outCnvBaseFont = outCnvBaseFont;
    }
    public void setOutCnvBaseFontColor(String outCnvBaseFontColor) {
        this.outCnvBaseFontColor = outCnvBaseFontColor;
    }
    public void setOutCnvBaseFontSize(Integer outCnvBaseFontSze) {
        this.outCnvBaseFontSize = outCnvBaseFontSze;
    }
    public void setRotateLabels(Integer rotateNames) {
        this.rotateLabels = rotateNames;
    }
    public void setShowDivLineValues(Integer showDivLineValue) {
        this.showDivLineValues = showDivLineValue;
    }
    public void setShowLimits(Integer showLimits) {
        this.showLimits = showLimits;
    }
    public void setXAxisName(String axisName) {
        xAxisName = axisName;
    }
    public void setYAxisMinValue(String axisMinValue) {
        yAxisMinValue = axisMinValue;
    }
    public void setYAxisName(String axisName) {
        yAxisName = axisName;
    }
    public void setZeroPlaneAlpha(Integer zeroPlaneAlpha) {
        this.zeroPlaneAlpha = zeroPlaneAlpha;
    }
    public void setZeroPlaneColor(String zeroPlaneColor) {
        this.zeroPlaneColor = zeroPlaneColor;
    }
    public List<Lines> getValues() {
		return values;
	}
	public void setValues(List<Lines> values) {
		this.values = values;
	}
	@Override
    public Element setChartAttributes(Element root) {
    	super.setChartAttributes(root);
    	
    	if(getValues() != null) {//Si hay algo en la lista
    		for (Iterator iter = getValues().iterator(); iter.hasNext();) {
    			Lines line = (Lines) iter.next();
    			Element lineElement = DocumentHelper.createElement(line.elementName());
    			
   				SerializeChart.serializeThat(lineElement, line);
   				root.add(lineElement);
    		}
        }
        return root;
    }
}