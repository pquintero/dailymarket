package ar.com.dailyMarket.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class EstadisticasAction extends BaseAction {

    public ActionForward initAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	return mapping.findForward("showEstadisticasHome");
    }
    
    public ActionForward doEstadisticasVentasMensuales(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	return mapping.findForward("showEstadisticasVentasMensualesFilter");
    }
    
    public ActionForward doEstadisticasVentasAnuales(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	return mapping.findForward("showEstadisticasVentasAnualesFilter");
    }
    
    public ActionForward doEstadisticasVentasMensualesPorProducto(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	return mapping.findForward("showEstadisticasVentasMensualesPorProductoFilter");
    }
    
    public ActionForward doEstadisticasVentasAnualesPorProducto(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	return mapping.findForward("showEstadisticasVentasAnualesPorProductoFilter");
    }
    
    public ActionForward doEstadisticasVentasMensualesPorGrupoDeProducto(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	return mapping.findForward("showEstadisticasVentasMensualesPorGrupoDeProductoFilter");
    }
    
    public ActionForward doEstadisticasVentasAnualesPorGrupoDeProducto(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	return mapping.findForward("showEstadisticasVentasAnualesPorGrupoDeProductoFilter");
    }
    
}
