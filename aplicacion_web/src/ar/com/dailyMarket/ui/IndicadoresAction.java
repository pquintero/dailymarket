package ar.com.dailyMarket.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class IndicadoresAction extends BaseAction {

    public ActionForward initAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	return mapping.findForward("showIndicadoresHome");
    }
    
    public ActionForward doIndicadoresVentasPorCajeroMensual(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	return mapping.findForward("showIndicadoresVentasPorCajeroMensualFilter");
    }
    
    public ActionForward doIndicadoresVentasPorCajeroAnual(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	return mapping.findForward("showIndicadoresVentasPorCajeroAnualFilter");
    }
    
    public ActionForward doIndicadoresComparativaDeVentasPorCajeroMensual(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	return mapping.findForward("showIndicadoresComparativaDeVentasPorCajeroMensualFilter");
    }
    
    public ActionForward doIndicadoresComparativaDeVentasPorCajeroAnual(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	return mapping.findForward("showIndicadoresComparativaDeVentasPorCajeroAnualFilter");
    }
}