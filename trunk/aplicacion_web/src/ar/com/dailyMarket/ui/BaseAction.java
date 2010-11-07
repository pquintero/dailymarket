package ar.com.dailyMarket.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import ar.com.dailyMarket.charts.Chart;
import ar.com.dailyMarket.dailyMapping.BaseActionMapping;

public class BaseAction extends DispatchAction {	
	
	public ActionForward redirectCreate (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {			
		request.getSession().setAttribute("initMethod", true);	
		return mapping.findForward("redirectCreate");
	}	
	
	public ActionForward initAction (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		return mapping.findForward("showNew");
	}
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	BaseActionMapping myMapping = (BaseActionMapping)mapping;
    	Boolean initMethod = (Boolean)request.getSession().getAttribute("initMethod");
    	if (initMethod != null && initMethod) {    		
    		request.getParameterMap().put("VirtualDispatchName", myMapping.getInitMethod());    		
    		request.setAttribute("VirtualDispatchName", myMapping.getInitMethod());   
    		request.getSession().setAttribute("initMethod",false);
    	}    	
    	String action = myMapping.getPath();  
    	assignTabInSession(action, request);
    	return super.execute(mapping, form, request, response);
    }
	
	public ActionForward stepBack (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.getSession().setAttribute("initMethod", true);
		return mapping.findForward("redirectFilter");
    }
	
    public ActionForward getXML(Chart chart, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/xml"); 
        chart.writeXML(response.getOutputStream());
        return null;
    }
    
    public void assignTabInSession(String action, HttpServletRequest request) {
    	action = action.substring(1); //le saco la barra de adelante
    	if (action.equals("groupUser") || action.equals("filterGroupUser")) {
    		request.getSession().setAttribute("tab", "groupUser");
    	} else if (action.equals("groupProduct") || action.equals("filterGroupProduct")) {
    		request.getSession().setAttribute("tab", "groupProduct");    	
    	} else if (action.equals("user") || action.equals("filterUser")) {
    		request.getSession().setAttribute("tab", "user");
    	} else if (action.equals("product") || action.equals("filterProduct")) {
    		request.getSession().setAttribute("tab", "product");
    	} else if (action.equals("configuration")){
    		request.getSession().setAttribute("tab", "configuration");
    	} else if (action.equals("hourlyBand") || action.equals("filterHourlyBand")) {
    		request.getSession().setAttribute("tab", "hourlyBand");
    	} else if (action.equals("home") || action.equals("manager")) {
    		request.getSession().setAttribute("tab", "emision");
    	} else if (action.equals("simulator")) {
    		request.getSession().setAttribute("tab", "simulador");
    	} else if (action.equals("reportes")) {
    		request.getSession().setAttribute("tab", "reportes");
    	} else if (action.equals("indicadores")) {
    		request.getSession().setAttribute("tab", "indicadores");
    	} else if (action.equals("estadisticas")) {
    		request.getSession().setAttribute("tab", "estadisticas");
    	}
    }
}
