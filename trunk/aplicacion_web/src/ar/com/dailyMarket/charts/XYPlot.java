package ar.com.dailyMarket.charts;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import ar.com.dailyMarket.charts.elements.CategoriesXYPlotElement;
import ar.com.dailyMarket.charts.elements.DatasetXYPlot;
import ar.com.dailyMarket.charts.elements.VerticalTrendLineElement;

public class XYPlot extends AxisChart {
	
	
	private String xAxisName;
	private String yAxisName;

	private Integer yAxisMinValue;
	private Integer yAxisMaxValue;
	private Integer xAxisMinValue;
	private Integer xAxisMaxValue;

	private Integer showLimits;
	private Integer rotateLabels;
	private Integer animation;
	private Integer showLegend;
	
	private String outCnvBaseFont;
	private Integer outCnvBaseFontSize;
	private String outCnvBaseFontColor;
	private Integer zeroPlaneThickness;
	private String zeroPlaneColor;
	private Integer zeroPlaneAlpha;
	private Integer numdivlines;
	private String divlinecolor;
	private Integer divLineThickness;
	private Integer divLineAlpha;
	private Integer showDivLineValues;
	private Integer showAlternateHGridColor;

	private String alternateHGridColor;
	private Integer alternateHGridAlpha;
	
	private Integer chartLeftMargin;
	private Integer chartRightMargin;
	private Integer chartTopMargin;
	private Integer chartBottomMargin;
	
	private Integer palette;
	
	private List<VerticalTrendLineElement> vTrendLines = new LinkedList<VerticalTrendLineElement>();
	private List<DatasetXYPlot> datasets = new LinkedList<DatasetXYPlot>();
	private CategoriesXYPlotElement categories;

	/**
	 * Recibe una lista donde el primer elemento es de tipo <code>CategoryBubbleElement</code>, que corresponde al tag "categories",
	 * los demas elementos son de tipo <code>DatasetBubbleElement</code> y corresponden al tag "dataset",
	 * el ultimo elemento es el correspondiente a la linea
	 * @param list
	 */

	public XYPlot(CategoriesXYPlotElement categories,List<DatasetXYPlot> list){
	    super();
	    this.categories = categories;
	    
	    for(Iterator<DatasetXYPlot> it = list.iterator(); it.hasNext();){ //para cada dataset	        
	        DatasetXYPlot dataset = (DatasetXYPlot) it.next();
	        this.datasets.add(dataset);
	    }	    
	}
	
		
    public CategoriesXYPlotElement getCategories() {
		return categories;
	}
	public void setCategories(CategoriesXYPlotElement categories) {
		this.categories = categories;
	}
	public Integer getPalette() {
		return palette;
	}
	public void setPalette(Integer palette) {
		this.palette = palette;
	}
    public List<DatasetXYPlot> getDatasets() {
		return datasets;
	}
	public void setDatasets(List<DatasetXYPlot> datasets) {
		this.datasets = datasets;
	}
	public void addDataset(DatasetXYPlot dataset){
        if(this.datasets == null){
            this.datasets = new LinkedList<DatasetXYPlot>();
        }
        this.datasets.add(dataset);
    }
    public Integer getAlternateHGridAlpha() {
        return alternateHGridAlpha;
    }
    public String getAlternateHGridColor() {
        return alternateHGridColor;
    }
    public Integer getAnimation() {
        return animation;
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
    public Integer getDivLineThickness() {
        return divLineThickness;
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
    public Integer getShowAlternateHGridColor() {
        return showAlternateHGridColor;
    }
    public Integer getShowDivLineValues() {
        return showDivLineValues;
    }
    public Integer getShowLegend() {
        return showLegend;
    }
    public Integer getShowLimits() {
        return showLimits;
    }
    public Integer getXAxisMaxValue() {
        return xAxisMaxValue;
    }
    public Integer getXAxisMinValue() {
        return xAxisMinValue;
    }
    public String getXAxisName() {
        return xAxisName;
    }
    public Integer getYAxisMaxValue() {
        return yAxisMaxValue;
    }
    public Integer getYAxisMinValue() {
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
    public Integer getZeroPlaneThickness() {
        return zeroPlaneThickness;
    }
    public void setAlternateHGridAlpha(Integer alternateHGridAlpha) {
        this.alternateHGridAlpha = alternateHGridAlpha;
    }
    public void setAlternateHGridColor(String alternateHGridColor) {
        this.alternateHGridColor = alternateHGridColor;
    }
    public void setAnimation(Integer animation) {
        this.animation = animation;
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
    public void setDivLineThickness(Integer divLineThickness) {
        this.divLineThickness = divLineThickness;
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
    public void setOutCnvBaseFontSize(Integer outCnvBaseFontSize) {
        this.outCnvBaseFontSize = outCnvBaseFontSize;
    }
    public void setRotateLabels(Integer rotateLabels) {
        this.rotateLabels = rotateLabels;
    }
    public void setShowAlternateHGridColor(Integer showAlternateHGridColor) {
        this.showAlternateHGridColor = showAlternateHGridColor;
    }
    public void setShowDivLineValues(Integer showDivLineValues) {
        this.showDivLineValues = showDivLineValues;
    }
    public void setShowLegend(Integer showLegend) {
        this.showLegend = showLegend;
    }
    public void setShowLimits(Integer showLimits) {
        this.showLimits = showLimits;
    }
    public void setXAxisMaxValue(Integer axisMaxValue) {
        xAxisMaxValue = axisMaxValue;
    }
    public void setXAxisMinValue(Integer axisMinValue) {
        xAxisMinValue = axisMinValue;
    }
    public void setXAxisName(String axisName) {
        xAxisName = axisName;
    }
    public void setYAxisMaxValue(Integer axisMaxValue) {
        yAxisMaxValue = axisMaxValue;
    }
    public void setYAxisMinValue(Integer axisMinValue) {
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
    public void setZeroPlaneThickness(Integer zeroPlaneThickness) {
        this.zeroPlaneThickness = zeroPlaneThickness;
    }
    public List<VerticalTrendLineElement> getVTrendLines() {
		return vTrendLines;
	}
	public void setVTrendLines(List<VerticalTrendLineElement> trendLines) {
		vTrendLines = trendLines;
	}


	public Element setChartAttributes(Element chart) {
        chart = super.setChartAttributes(chart);//Agrega atributos del chart
        
        Element categories = DocumentHelper.createElement("categories");
        getCategories().serializeMe(categories);//Agrega el categories con atributos y los category
        chart.add(categories);
        
        for (Iterator<DatasetXYPlot> iter = getDatasets().iterator(); iter.hasNext();) {
            DatasetXYPlot datasetXYPlot = (DatasetXYPlot) iter.next();
            Element datasetElement = DocumentHelper.createElement("dataset");
            datasetXYPlot.serializeMe(datasetElement);//Agrega los datasets con sus atributos y conj. de Sets 
            chart.add(datasetElement);
        }
        if(!getVTrendLines().isEmpty()){
        	for (Iterator<VerticalTrendLineElement> iter = getVTrendLines().iterator(); iter.hasNext();) {
        		VerticalTrendLineElement vTrend = (VerticalTrendLineElement) iter.next();
        		Element vTrendElement = DocumentHelper.createElement("vTrendLines");
        		vTrend.serializeMe(vTrendElement);//Agrega los datasets con sus atributos y conj. de Sets 
        		chart.add(vTrendElement);
        	}
        }
        return chart;
    }
}
