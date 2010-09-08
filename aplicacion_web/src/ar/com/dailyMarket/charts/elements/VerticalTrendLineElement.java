package ar.com.dailyMarket.charts.elements;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class VerticalTrendLineElement {
	
	private List<LineElement> vTrendLines = new LinkedList<LineElement>();
		
	public List<LineElement> getVTrendLines() {
		return vTrendLines;
	}
	public void setVTrendLines(List<LineElement> trendLines) {
		vTrendLines = trendLines;
	}

	public void serializeMe(Element element) {        
		for (Iterator<LineElement> iter = getVTrendLines().iterator(); iter.hasNext();) {
            LineElement verticalTrendLine = (LineElement) iter.next();
            Element verticalTrendLineElement = DocumentHelper.createElement("line");
            SerializeChart.serializeThat(verticalTrendLineElement,verticalTrendLine);
            element.add(verticalTrendLineElement);
        }
    }
}
	