package ar.com.dailyMarket.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ManagerAction extends BaseAction {

    public ActionForward initAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	return mapping.findForward("showManagerHome");
    }
    
    public ActionForward doHomeEstadisticas(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	request.getSession().setAttribute("initMethod", true);
    	return mapping.findForward("showEstadisticasHome");
    }
    
    public ActionForward doHomeIndicadores(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	request.getSession().setAttribute("initMethod", true);
    	return mapping.findForward("showIndicadoresHome");
    }
    
    public ActionForward doHomeReportes(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	request.getSession().setAttribute("initMethod", true);
    	return mapping.findForward("showReportesHome");
    }
    
    public ActionForward doHomeSimulador(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	request.getSession().setAttribute("initMethod", true);
    	return mapping.findForward("showSimuladorHome");
    }
}
