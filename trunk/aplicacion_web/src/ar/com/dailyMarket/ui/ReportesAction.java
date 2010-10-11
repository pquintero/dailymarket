package ar.com.dailyMarket.ui;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import ar.com.dailyMarket.services.AnnualBillingReportService;
import ar.com.dailyMarket.services.AnnualSalesReportService;
import ar.com.dailyMarket.services.GroupProductService;
import ar.com.dailyMarket.services.HourlyBandService;
import ar.com.dailyMarket.services.MonthlyBillingReportService;
import ar.com.dailyMarket.services.MonthlySalesReportService;
import ar.com.dailyMarket.services.ProductService;

public class ReportesAction extends BaseAction {

    public ActionForward initAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	return mapping.findForward("showReportesHome");
    }
    
    public ActionForward doReporteVentasAnuales(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	setInRequest(request);    
    	return mapping.findForward("showReporteVentasAnualesFilter");
    }
    
    public ActionForward executeReporteVentasAnuales(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	//agarrar datos del formulario y ejecutar reporte
    	return null;
    }
    
    public ActionForward doReporteVentasMensuales(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	setInRequest(request);    	
    	return mapping.findForward("showReporteVentasMensualesFilter");
    }
    
    public ActionForward doReporteFacturacionAnual(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	setInRequest(request);
    	return mapping.findForward("showReporteFacturacionAnualFilter");
    }
    
    public ActionForward doReporteFacturacionMensual(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	    	
    	setInRequest(request);
    	return mapping.findForward("showReporteFacturacionMensualFilter");
    }

    public ActionForward doReporteListadoPrecios(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	return mapping.findForward("showReporteListadoPreciosFilter");
    }
    
    public ActionForward doReporteListadoCodigos(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	return mapping.findForward("showReporteListadoCodigosFilter");
    }
    
  //Ventas Mensuales
    public ActionForward executeMonthlySales(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	MonthlySalesReportService rrs = new MonthlySalesReportService();
    	String reportName = "ventasMensuales";
        try {
      		response.setHeader("Content-Disposition","attachment; filename=" + reportName + ".pdf" + "\"");
      		List col = new ArrayList();
      		Map<String, String> filters = rrs.getFilters((DynaActionForm)form);
      		byte[] bytes = rrs.runReport((DynaBean)form, col,reportName, filters);
      		OutputStream stream = response.getOutputStream();
      		stream.write(bytes);
			stream.flush();
			stream.close();
      		response.setContentType("aplication/pdf");
      	    response.setContentLength(bytes.length);    	    
      	    return null;
      	} catch (Exception fe){
      	    log.error("Error en la generacion del reporte", fe);
            ActionErrors errors = new ActionErrors();
            errors.add("", new ActionError("errors.report"));
            saveErrors(request, errors);
            return doReporteVentasMensuales(mapping, form, request, response);
      	}
    }
    
    //Ventas Anuales
    public ActionForward executeAnnualSales(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	AnnualSalesReportService ars = new AnnualSalesReportService();
    	String reportName = "ventasAnuales";
        try {
      		response.setHeader("Content-Disposition","attachment; filename=" + reportName + ".pdf" + "\"");
      		List col = new ArrayList();
      		Map<String, String> filters = ars.getFilters((DynaActionForm)form);
      		byte[] bytes = ars.runReport((DynaBean)form, col,reportName, filters);
      		OutputStream stream = response.getOutputStream();
      		stream.write(bytes);
			stream.flush();
			stream.close();
      		response.setContentType("aplication/pdf");
      	    response.setContentLength(bytes.length);    	    
      	    return null;
      	} catch (Exception fe){
      	    log.error("Error en la generacion del reporte", fe);
            ActionErrors errors = new ActionErrors();
            errors.add("", new ActionError("errors.report"));
            saveErrors(request, errors);
            return doReporteVentasAnuales(mapping, form, request, response);
      	}
    }   
    
  //facturacion Mensual
    public ActionForward executeMonthlyBilling(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	MonthlyBillingReportService mrs = new MonthlyBillingReportService();
    	String reportName = "facturacionMensual";
        try {
      		response.setHeader("Content-Disposition","attachment; filename=" + reportName + ".pdf" + "\"");
      		List col = new ArrayList();
      		Map<String, String> filters = mrs.getFilters((DynaActionForm)form);
      		byte[] bytes = mrs.runReport((DynaBean)form, col,reportName, filters);
      		OutputStream stream = response.getOutputStream();
      		stream.write(bytes);
			stream.flush();
			stream.close();
      		response.setContentType("aplication/pdf");
      	    response.setContentLength(bytes.length);    	    
      	    return null;
      	} catch (Exception fe){
      	    log.error("Error en la generacion del reporte", fe);
            ActionErrors errors = new ActionErrors();
            errors.add("", new ActionError("errors.report"));
            saveErrors(request, errors);
            return doReporteFacturacionAnual(mapping, form, request, response);
      	}
    }
    
    //facturacion Anual
    public ActionForward executeAnnualBilling(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	AnnualBillingReportService ars = new AnnualBillingReportService();
    	String reportName = "facturacionAnual";
        try {
      		response.setHeader("Content-Disposition","attachment; filename=" + reportName + ".pdf" + "\"");
      		List col = new ArrayList();
      		Map<String, String> filters = ars.getFilters((DynaActionForm)form);
      		byte[] bytes = ars.runReport((DynaBean)form, col,reportName, filters);
      		OutputStream stream = response.getOutputStream();
      		stream.write(bytes);
			stream.flush();
			stream.close();
      		response.setContentType("aplication/pdf");
      	    response.setContentLength(bytes.length);    	    
      	    return null;
      	} catch (Exception fe){
      	    log.error("Error en la generacion del reporte", fe);
            ActionErrors errors = new ActionErrors();
            errors.add("", new ActionError("errors.report"));
            saveErrors(request, errors);
            return doReporteFacturacionAnual(mapping, form, request, response);
      	}
    }
    
    public void setInRequest (HttpServletRequest request) {
    	GroupProductService groupProductService = new GroupProductService();
    	ProductService productService = new ProductService();
    	HourlyBandService hourlyBandService = new HourlyBandService();
    	
    	request.setAttribute("groupsProduct", groupProductService.getAllGroupProduct());
    	request.setAttribute("products", productService.getAllProducts());
    	request.setAttribute("hourlyBands", hourlyBandService.getAllHourlyBands());
    }
    
}
