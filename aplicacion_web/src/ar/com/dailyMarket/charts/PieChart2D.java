package ar.com.dailyMarket.charts;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import ar.com.dailyMarket.charts.elements.SerializeChart;
import ar.com.dailyMarket.charts.elements.SetElement;


public class PieChart2D extends ChartImpl {

    protected Integer showPercentageValues;
    protected Integer pieRadius;
    protected Integer slicingDistance;
    protected Integer showZeroPies;
    protected Integer showShadow;
    protected List<SetElement> sets = new LinkedList<SetElement>();
    protected String[] pieDefaultColors = { "FF0000", //Bright Red
					            			"006F00", //Dark Green
					            			"0099FF", //Blue (Light)
					            			"FF66CC", //Dark Pink
					            			"996600", //Variant of brown
					            			"669966", //Dirty green
										    "7C7CB4", //Violet shade of blue
										    "0099CC", //Blue Shade
										    "FF9933", //Orange
										    "CCCC00", //Chrome Yellow+Green
										    "9900FF", //Violet
										    "999999", //Grey
										    "99FFCC", //Blue+Green Light
										    "CCCCFF", //Light violet
										    "669900", //Shade of green
										    "1941A5", //Dark Blue
										    "BB33BB", //??
										    "ABCDEF", //??
										    "949494" //??
    };

    public Integer getShowShadow() {
        return showShadow;
    }
    public void setShowShadow(Integer showShadow) {
        this.showShadow = showShadow;
    }
    public Integer getSlicingDistance() {
        return slicingDistance;
    }
    public void setSlicingDistance(Integer slicingDistance) {
        this.slicingDistance = slicingDistance;
    }
	
    
    public PieChart2D(List values){
	    super();
	    Integer i = 0;
	    for (Iterator iter = values.iterator(); iter.hasNext(); i++) {
	        if(i == pieDefaultColors.length)
	            i = 0;
            SetElement set = (SetElement) iter.next();
            set.setColor(pieDefaultColors[i]);            
        }
	    setSets(values);
	}
	
    
    public Integer getShowZeroPies() {
		return showZeroPies;
	}
	public void setShowZeroPies(Integer showZeroPies) {
		this.showZeroPies = showZeroPies;
	}
	public String[] getPieDefaultColors() {
        return pieDefaultColors;
    }
    public List<SetElement> getSets() {
        return sets;
    }
    public void setPieDefaultColors(String[] pieDefaultColors) {
        this.pieDefaultColors = pieDefaultColors;
    }
    public void setSets(List<SetElement> sets) {
        this.sets = sets;
    }
    public Integer getPieRadius() {
        return pieRadius;
    }
    public Integer getShowPercentageValues() {
        return showPercentageValues;
    }
    public void setPieRadius(Integer pieRadius) {
        this.pieRadius = pieRadius;
    }
    public void setShowPercentageValues(Integer showPercentageValues) {
        this.showPercentageValues = showPercentageValues;
    }
    
    public Element setChartAttributes(Element chart) {        
        chart = super.setChartAttributes(chart);                
        
        for (Iterator<SetElement> iter = getSets().iterator(); iter.hasNext();) {
            SetElement setElement = (SetElement) iter.next();
            Element set = DocumentHelper.createElement("set");
            SerializeChart.serializeThat(set, setElement);
            chart.add(set);
        }
        return chart;
    }
}
