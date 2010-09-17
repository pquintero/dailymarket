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
}
