package ar.com.dailyMarket.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import ar.com.dailyMarket.charts.ColumnChart2D;
import ar.com.dailyMarket.services.EstadisticasService;
import ar.com.dailyMarket.services.GroupProductService;
import ar.com.dailyMarket.services.HourlyBandService;
import ar.com.dailyMarket.services.ProductService;

public class EstadisticasAction extends BaseAction {

    public ActionForward initAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	return mapping.findForward("showEstadisticasHome");
    }
    
/******		 VentasMensuales		******/
    
    public ActionForward doEstadisticasVentasMensuales(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	HourlyBandService hbs = new HourlyBandService();
    	request.setAttribute("aniosList", StaticData.anios);
    	request.setAttribute("mesesList", StaticData.meses);
    	request.setAttribute("bandaList", hbs.getAllHourlyBands());
    	return mapping.findForward("showEstadisticasVentasMensualesFilter");
    }
    
    public ActionForward executeEstadisticasVentasMensuales(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	return mapping.findForward("showEstadisticasVentasMensualesChart");
    }
    
    public ActionForward getVMChart(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		EstadisticasService es = new EstadisticasService();
        ColumnChart2D col = es.getVMChart((DynaActionForm)form);
        return getXML(col, mapping, form, request, response);
    }
    
/******		VentasAnuales		******/
    
    public ActionForward doEstadisticasVentasAnuales(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	HourlyBandService hbs = new HourlyBandService();
    	request.setAttribute("aniosList", StaticData.anios);
    	request.setAttribute("bandaList", hbs.getAllHourlyBands());
    	return mapping.findForward("showEstadisticasVentasAnualesFilter");
    }
    
    public ActionForward executeEstadisticasVentasAnuales(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	return mapping.findForward("showEstadisticasVentasAnualesChart");
    }
    
    public ActionForward getVAChart(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
    	EstadisticasService es = new EstadisticasService();
        ColumnChart2D col = es.getVAChart((DynaActionForm)form);
        return getXML(col, mapping, form, request, response);
    }
    
/******		VentasMensualesPorProducto		******/
    
    public ActionForward doEstadisticasVentasMensualesPorProducto(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	HourlyBandService hbs = new HourlyBandService();
    	ProductService productService = new ProductService();
    	
    	request.setAttribute("aniosList", StaticData.anios);
    	request.setAttribute("mesesList", StaticData.meses);
    	request.setAttribute("products", productService.getAllProducts());
    	request.setAttribute("bandaList", hbs.getAllHourlyBands());
    	return mapping.findForward("showEstadisticasVentasMensualesPorProductoFilter");
    }
    
    public ActionForward executeEstadisticasVentasMensualesPorProducto(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	return mapping.findForward("showEstadisticasVentasMensualesPorProductoChart");
    }
    
    public ActionForward getVMPPChart(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
    	EstadisticasService es = new EstadisticasService();
        ColumnChart2D col = es.getVMPPChart((DynaActionForm)form);
        return getXML(col, mapping, form, request, response);
    }
    
/******		VentasAnualesPorProducto		******/    
    
    public ActionForward doEstadisticasVentasAnualesPorProducto(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	HourlyBandService hbs = new HourlyBandService();
    	ProductService productService = new ProductService();
    	
    	request.setAttribute("aniosList", StaticData.anios);
    	request.setAttribute("products", productService.getAllProducts());
    	request.setAttribute("bandaList", hbs.getAllHourlyBands());
    	return mapping.findForward("showEstadisticasVentasAnualesPorProductoFilter");
    }
    
    public ActionForward executeEstadisticasVentasAnualesPorProducto(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	return mapping.findForward("showEstadisticasVentasAnualesPorProductoChart");
    }
    
    public ActionForward getVAPPChart(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
    	EstadisticasService es = new EstadisticasService();
        ColumnChart2D col = es.getVAPPChart((DynaActionForm)form);
        return getXML(col, mapping, form, request, response);
    }
    
/******		VentasMensualesPorGrupoDeProducto		******/
   
    public ActionForward doEstadisticasVentasMensualesPorGrupoDeProducto(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	HourlyBandService hbs = new HourlyBandService();
    	GroupProductService groupProductService = new GroupProductService();
    	
    	request.setAttribute("aniosList", StaticData.anios);
    	request.setAttribute("mesesList", StaticData.meses);
    	request.setAttribute("groupsProduct", groupProductService.getAllGroupProduct());
    	request.setAttribute("bandaList", hbs.getAllHourlyBands());
    	return mapping.findForward("showEstadisticasVentasMensualesPorGrupoDeProductoFilter");
    }
    
    public ActionForward executeEstadisticasVentasMensualesPorGrupoDeProducto(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	return mapping.findForward("showEstadisticasVentasMensualesPorGrupoDeProductoChart");
    }
    
    public ActionForward getVMPGPChart(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
    	EstadisticasService es = new EstadisticasService();
        ColumnChart2D col = es.getVMPGPChart((DynaActionForm)form);
        return getXML(col, mapping, form, request, response);
    }
    
/******		VentasAnualesPorGrupoDeProducto		******/    
    
    public ActionForward doEstadisticasVentasAnualesPorGrupoDeProducto(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	HourlyBandService hbs = new HourlyBandService();
    	GroupProductService groupProductService = new GroupProductService();
    	
    	request.setAttribute("aniosList", StaticData.anios);
    	request.setAttribute("groupsProduct", groupProductService.getAllGroupProduct());
    	request.setAttribute("bandaList", hbs.getAllHourlyBands());
    	return mapping.findForward("showEstadisticasVentasAnualesPorGrupoDeProductoFilter");
    }
    
    public ActionForward executeEstadisticasVentasAnualesPorGrupoDeProducto(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	return mapping.findForward("showEstadisticasVentasAnualesPorGrupoDeProductoChart");
    }
    
    public ActionForward getVAPGPChart(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
    	EstadisticasService es = new EstadisticasService();
        ColumnChart2D col = es.getVAPGPChart((DynaActionForm)form);
        return getXML(col, mapping, form, request, response);
    }
}
