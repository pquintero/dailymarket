package ar.com.dailyMarket.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ReportesAction extends BaseAction {

    public ActionForward initAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	return mapping.findForward("showReportesHome");
    }
    
    public ActionForward doReporteVentasAnuales(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	return mapping.findForward("showReporteVentasAnualesFilter");
    }
    
    public ActionForward executeReporteVentasAnuales(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	//agarrar datos del formulario y ejecutar reporte
    	return null;
    }
    
    public ActionForward doReporteVentasMensuales(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	return mapping.findForward("showReporteVentasMensualesFilter");
    }
    
    public ActionForward doReporteFacturacionAnual(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	return mapping.findForward("showReporteFacturacionAnualFilter");
    }
    
    public ActionForward doReporteFacturacionMensual(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	return mapping.findForward("showReporteFacturacionMensualFilter");
    }
    
    public ActionForward doReporteListadoPrecios(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	return mapping.findForward("showReporteListadoPreciosFilter");
    }
    
    public ActionForward doReporteListadoCodigos(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	return mapping.findForward("showReporteListadoCodigosFilter");
    }
    
}
