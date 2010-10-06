package ar.com.dailyMarket.ui;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.com.dailyMarket.services.GroupProductService;
import ar.com.dailyMarket.services.MonthlySalesReportService;
import ar.com.dailyMarket.services.ProductService;

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
    	GroupProductService groupProductService = new GroupProductService();
    	ProductService productService = new ProductService();
    	
    	request.setAttribute("groupsProduct", groupProductService.getAllGroupProduct());
    	request.setAttribute("products", productService.getAllProducts());
    	
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
    
    public ActionForward executeMonthlySales(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	ServletContext context = request.getSession().getServletContext();
    	MonthlySalesReportService rrs = new MonthlySalesReportService();            
//        String path = "/reports/";
//        String path = "WEB-INF" + File.separator + "classes" + File.separator + "reports" + File.separator;
    	String path = context.getRealPath("");
        
        try {
      		response.setHeader("Content-Disposition","attachment; filename=" + "ventasMensuales.pdf" + "\"");
      		List l = new ArrayList();
      		int cant = rrs.runReport((DynaBean)form, response.getOutputStream(), path, context, l,"ventasMensuales");            
      	    response.setContentType("aplication/pdf");
      	    response.setContentLength(cant);    	    
      	    return null;
      	} catch (Exception fe){
      	    log.error("Error en la generacion del reporte", fe);
            ActionErrors errors = new ActionErrors();
            errors.add("", new ActionError("errors.report"));
            saveErrors(request, errors);
      	}
      	return null;
    }
    
    
}
