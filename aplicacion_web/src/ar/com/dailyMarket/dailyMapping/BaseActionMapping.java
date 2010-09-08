package ar.com.dailyMarket.dailyMapping;

import org.apache.struts.action.ActionMapping;


public class BaseActionMapping extends ActionMapping{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String initMethod;

	public String getInitMethod() {
		return initMethod;
	}

	public void setInitMethod(String initMethod) {
		this.initMethod = initMethod;
	}	
}
