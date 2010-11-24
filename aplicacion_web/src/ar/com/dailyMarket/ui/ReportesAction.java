package ar.com.dailyMarket.ui;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import ar.com.dailyMarket.model.GroupProduct;
import ar.com.dailyMarket.model.Product;
import ar.com.dailyMarket.services.BillingReportService;
import ar.com.dailyMarket.services.GroupProductService;
import ar.com.dailyMarket.services.HourlyBandService;
import ar.com.dailyMarket.services.ListCodesReportService;
import ar.com.dailyMarket.services.ListPricesReportService;
import ar.com.dailyMarket.services.ProductService;
import ar.com.dailyMarket.services.SalesReportService;
import ar.com.dailyMarket.util.CombSelect;

public class ReportesAction extends BaseAction {

    public ActionForward initAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	return mapping.findForward("showReportesHome");
    }
    
    public ActionForward doReporteVentasAnuales(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	setHourlyBandInRequest(request);
    	setGroupInRequestAndComboProductsInForm(request, (DynaActionForm) form);    
    	return mapping.findForward("showReporteVentasAnualesFilter");
    }
    
    public ActionForward doReporteVentasMensuales(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	setHourlyBandInRequest(request);
    	setGroupInRequestAndComboProductsInForm(request, (DynaActionForm) form); 
    	return mapping.findForward("showReporteVentasMensualesFilter");
    }
    
    public ActionForward doReporteFacturacionAnual(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	setHourlyBandInRequest(request);
    	setGroupInRequestAndComboProductsInForm(request, (DynaActionForm) form); 
    	return mapping.findForward("showReporteFacturacionAnualFilter");
    }
    
    public ActionForward doReporteFacturacionMensual(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	    	
    	setHourlyBandInRequest(request);
    	setGroupInRequestAndComboProductsInForm(request, (DynaActionForm) form); 
    	return mapping.findForward("showReporteFacturacionMensualFilter");
    }

    public ActionForward doReporteListadoPrecios(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	setGroupInRequestAndComboProductsInForm(request, (DynaActionForm) form); 
    	return mapping.findForward("showReporteListadoPreciosFilter");
    }
    
    public ActionForward doReporteListadoCodigos(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	setGroupInRequestAndComboProductsInForm(request, (DynaActionForm) form); 
    	return mapping.findForward("showReporteListadoCodigosFilter");
    }
    
    //Ventas Mensuales
    @SuppressWarnings("unchecked")
	public ActionForward executeMonthlySales(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	SalesReportService rrs = new SalesReportService();
    	String reportName = "ventas";
        try {
      		response.setHeader("Content-Disposition","attachment; filename=" + reportName + ".pdf" + "\"");
      		List col = new ArrayList();
      		Map<String, String> filters = rrs.getFilters((DynaActionForm)form);      		
      		byte[] bytes = rrs.runReport((DynaBean)form, col,reportName, filters, "Mensual");      		
      		executeReport(bytes, response);    	    
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
    @SuppressWarnings("unchecked")
	public ActionForward executeAnnualSales(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	SalesReportService ars = new SalesReportService();
    	String reportName = "ventas";
        try {
      		response.setHeader("Content-Disposition","attachment; filename=" + reportName + ".pdf" + "\"");
      		List col = new ArrayList();
      		Map<String, String> filters = ars.getFilters((DynaActionForm)form);
      		byte[] bytes = ars.runReport((DynaBean)form, col,reportName, filters, "Anual");      		
      		executeReport(bytes, response);    	    
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
    @SuppressWarnings("unchecked")
	public ActionForward executeMonthlyBilling(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	BillingReportService mrs = new BillingReportService();
    	String reportName = "facturacion";
        try {
      		response.setHeader("Content-Disposition","attachment; filename=" + reportName + ".pdf" + "\"");
      		List col = new ArrayList();
      		Map<String, String> filters = mrs.getFilters((DynaActionForm)form);
      		byte[] bytes = mrs.runReport((DynaBean)form, col,reportName, filters, "Mensual");
      		executeReport(bytes, response);  	    
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
    @SuppressWarnings("unchecked")
	public ActionForward executeAnnualBilling(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	BillingReportService ars = new BillingReportService();
    	String reportName = "facturacion";
        try {
      		response.setHeader("Content-Disposition","attachment; filename=" + reportName + ".pdf" + "\"");
      		List col = new ArrayList();
      		Map<String, String> filters = ars.getFilters((DynaActionForm)form);
      		byte[] bytes = ars.runReport((DynaBean)form, col,reportName, filters, "Anual");
      		executeReport(bytes, response);    	    
      	    return null;
      	} catch (Exception fe){
      	    log.error("Error en la generacion del reporte", fe);
            ActionErrors errors = new ActionErrors();
            errors.add("", new ActionError("errors.report"));
            saveErrors(request, errors);
            return doReporteFacturacionAnual(mapping, form, request, response);
      	}
    }
    
    public ActionForward executeListCodes(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	ListCodesReportService lcrs = new ListCodesReportService();
    	String reportName = "codigos";
        try {
      		response.setHeader("Content-Disposition","attachment; filename=" + reportName + ".pdf" + "\"");
      		Map<String, Object> filters = lcrs.getFiltersObject((DynaActionForm)form);
      		byte[] bytes = lcrs.runReport((DynaBean)form, reportName, filters);
      		executeReport(bytes, response);    	    
      	    return null;
      	} catch (Exception fe){
      	    log.error("Error en la generacion del reporte", fe);
            ActionErrors errors = new ActionErrors();
            errors.add("", new ActionError("errors.report"));
            saveErrors(request, errors);
            return doReporteFacturacionAnual(mapping, form, request, response);
      	}
    }    
    
    public ActionForward executeListPrices(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	ListPricesReportService lprs = new ListPricesReportService();
    	String reportName = "precios";
        try {
      		response.setHeader("Content-Disposition","attachment; filename=" + reportName + ".pdf" + "\"");
      		Map<String, Object> filters = lprs.getFiltersObject((DynaActionForm)form);
      		byte[] bytes = lprs.runReport((DynaBean)form, reportName, filters);
      		executeReport(bytes, response);    	    
      	    return null;
      	} catch (Exception fe){
      	    log.error("Error en la generacion del reporte", fe);
            ActionErrors errors = new ActionErrors();
            errors.add("", new ActionError("errors.report"));
            saveErrors(request, errors);
            return doReporteFacturacionAnual(mapping, form, request, response);
      	}
    }
    
    public void setGroupInRequestAndComboProductsInForm (HttpServletRequest request, DynaActionForm form) {
    	GroupProductService groupProductService = new GroupProductService();
    	ProductService productService = new ProductService()
    	;
    	request.setAttribute("groupsProduct", groupProductService.getAllGroupProduct());

    	List<GroupProduct> gp = new GroupProductService().getAllGroupProduct();
    	JSONArray json = new JSONArray();
    	for (GroupProduct groupProduct : gp) {
			for (Product product : productService.getProductsByGroup(groupProduct.getId())) {
    			json.add(new CombSelect(groupProduct.getName(), 
    					groupProduct.getId(), 
    					product.getName(), 
    					product.getId())
    			);
    		}
    	}
    	((DynaActionForm)form).set("comboProductos", json.toString());
    }  
    
    public void setHourlyBandInRequest (HttpServletRequest request) {
    	HourlyBandService hourlyBandService = new HourlyBandService();
    	request.setAttribute("hourlyBands", hourlyBandService.getAllHourlyBands());
    }
    
    private void executeReport(byte[] bytes, HttpServletResponse response) throws IOException {
    	OutputStream stream = response.getOutputStream();
  		stream.write(bytes);
		stream.flush();
		stream.close();
  		response.setContentType("aplication/pdf");
  	    response.setContentLength(bytes.length);    	    
    }
}
