package ar.com.dailyMarket.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.com.dailyMarket.services.GroupProductService;

public class SimulatorAction extends BaseAction {
	
	public ActionForward initAction (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		setGroupProductRequest(request);
		return mapping.findForward("showSimulator");
	}
	
	public ActionForward executeSimulator (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//ejecutar simulador
		//redireccionar a home con msj q el simulador ejecuto correctamente
		return doHome(mapping, form, request, response);
	}
	
	public ActionForward doHome (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.getSession().setAttribute("initMethod", true);
		return mapping.findForward("doHome");
	}
	
	public ActionForward stepBack (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {		
		return mapping.findForward("redirectFilter");
    }
	
	public ActionForward saveAndBackToCheckPoint (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	//guardar el usuario
    	return doHome(mapping, form, request, response);
    }
	
	private void setGroupProductRequest (HttpServletRequest request) {
    	GroupProductService groupProductService = new GroupProductService();
    	request.setAttribute("groupsProduct", groupProductService.getAllGroupProduct());
    }     
}
