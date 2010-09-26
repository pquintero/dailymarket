package ar.com.dailyMarket.charts;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import ar.com.dailyMarket.charts.elements.TrendLine;

public abstract class AxisChart extends ChartImpl {
	private List<TrendLine> trendLines = new LinkedList<TrendLine>();

	public List<TrendLine> getTrendlines() {
		return trendLines;
	}
	public void setTrendlines(List<TrendLine> trendlines) {
		this.trendLines = trendlines;
	}
	
	
	@Override
    public Element setChartAttributes(Element root) {
		super.setChartAttributes(root);
		
		if(getTrendlines() != null) {
    		for (Iterator iter = getTrendlines().iterator(); iter.hasNext();) {
    			TrendLine trendLine= (TrendLine)iter.next();
    			Element trendLineElement = DocumentHelper.createElement("trendLines");
    			trendLine.serializeMe(trendLineElement);
   				root.add(trendLineElement);
    		}
        }
        return root;
    }
}