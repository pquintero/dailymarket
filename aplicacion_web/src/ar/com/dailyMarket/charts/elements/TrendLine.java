package ar.com.dailyMarket.charts.elements;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class TrendLine {
	private List<TrendlineElement> lines = new LinkedList<TrendlineElement>();
	
	public TrendLine(){
		super();
	}
	public TrendLine(TrendlineElement element){
		super();
		lines.add(element);
	}
	public TrendLine(List<TrendlineElement> lista){
		super();
		this.setLines(lista);
	}
	
	
	public List<TrendlineElement> getLines() {
		return lines;
	}
	public void setLines(List<TrendlineElement> lines) {
		this.lines = lines;
	}
	
	public void serializeMe(Element element) {
        for (Iterator<TrendlineElement> iter = getLines().iterator(); iter.hasNext();) {
        	TrendlineElement trendline = (TrendlineElement) iter.next();
            Element trendlineElement = DocumentHelper.createElement("line");
            SerializeChart.serializeThat(trendlineElement,trendline);
            element.add(trendlineElement);
        }        
    }
}