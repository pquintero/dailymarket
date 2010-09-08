package ar.com.dailyMarket.charts.elements;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class CategoriesXYPlotElement {
	
	private String font;
	private Integer fontSize;
	private String fontColor;
	private String verticalLineColor;
	private Integer verticalLineThickness;
	private Integer verticalLineAlpha;
		
	private List<CategoryXYPlotElement> xYPlotCategories = new LinkedList<CategoryXYPlotElement>();
	
	
	public CategoriesXYPlotElement(List<CategoryXYPlotElement> list) {
		super();
		this.xYPlotCategories = list;
	}


	public String getFont() {
		return font;
	}
	public void setFont(String font) {
		this.font = font;
	}
	public Integer getFontSize() {
		return fontSize;
	}
	public void setFontSize(Integer fontSize) {
		this.fontSize = fontSize;
	}
	public String getFontColor() {
		return fontColor;
	}
	public void setFontColor(String fontColor) {
		this.fontColor = fontColor;
	}
	public String getVerticalLineColor() {
		return verticalLineColor;
	}
	public void setVerticalLineColor(String verticalLineColor) {
		this.verticalLineColor = verticalLineColor;
	}
	public Integer getVerticalLineThickness() {
		return verticalLineThickness;
	}
	public void setVerticalLineThickness(Integer verticalLineThickness) {
		this.verticalLineThickness = verticalLineThickness;
	}
	public Integer getVerticalLineAlpha() {
		return verticalLineAlpha;
	}
	public void setVerticalLineAlpha(Integer verticalLineAlpha) {
		this.verticalLineAlpha = verticalLineAlpha;
	}
	public List<CategoryXYPlotElement> getXYPlotCategories() {
		return xYPlotCategories;
	}
	public void setXYPlotCategories(List<CategoryXYPlotElement> categories) {
		this.xYPlotCategories = categories;
	}
	
	public void serializeMe(Element element) {        
		SerializeChart.serializeThat(element, this);
        
        for (Iterator<CategoryXYPlotElement> iter = getXYPlotCategories().iterator(); iter.hasNext();) {
            CategoryXYPlotElement catElement = iter.next();
            Element set = DocumentHelper.createElement("category");
            SerializeChart.serializeThat(set,catElement);
            element.add(set);
        }        
    }
}