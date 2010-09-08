
package ar.com.dailyMarket.charts;

import java.io.IOException;
import java.io.OutputStream;

import org.dom4j.Element;


public interface Chart {

    public Element setChartAttributes(Element root);
    public void writeXML(OutputStream os) throws IOException; 
}
