package ar.com.dailyMarket.ui;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import ar.com.dailyMarket.model.Product;
import ar.com.dailyMarket.services.GroupProductService;
import ar.com.dailyMarket.services.ProductService;
import ar.com.dailyMarket.services.SimulatorService;

public class SimulatorAction extends BaseAction {
	
	public ActionForward initAction (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		//TODO Inicializar todo el formulario
		((DynaActionForm)form).set("margen","");
		((DynaActionForm)form).set("yearFrom","2009");
		((DynaActionForm)form).set("days",0);
		((DynaActionForm)form).set("simuladorArray", new String[0]);
		setProductAndGroupProduct(request);
		request.setAttribute("productsList", new ArrayList<Product>());
		return mapping.findForward("showSimulator");
	}
	
    public ActionForward executeFilter (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	SimulatorService simulatorService = new SimulatorService();
    	List<Product> productList = simulatorService.executeFilter((DynaActionForm)form);
    	request.setAttribute("productsList", productList);
    	
    	String [] array = new String[productList.size()];
    	for (int i = 0; i < array.length; i++) {
			array[i]="";
		}
    	
    	((DynaActionForm)form).set("simulatedSizeOfPurchaseArray", array);
    	((DynaActionForm)form).set("simulatedRepositionStockArray", array);
    	
    	setProductAndGroupProduct(request);
    	return mapping.findForward("showSimulator");
    }
	
	public ActionForward executeSimulator (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (!StringUtils.isNotEmpty((String) ((DynaActionForm)form).get("margen"))) {
			return executeFilter(mapping, form, request, response);
		}
		SimulatorService simulatorService = new SimulatorService();
		request.setAttribute("productsList", simulatorService.executeFilter((DynaActionForm)form));
		
		simulatorService.executeSimulation((DynaActionForm)form);
		
    	setProductAndGroupProduct(request);
    	return mapping.findForward("showSimulator");
	}
	
	public ActionForward aplicarCambios (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SimulatorService simulatorService = new SimulatorService();
		simulatorService.aplicarCambios(form);
		return initAction(mapping, form, request, response);
	}
	
	public ActionForward stepBack (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {		
		return mapping.findForward("redirectFilter");
    }
	
	private void setProductAndGroupProduct (HttpServletRequest request) {
    	GroupProductService groupProductService = new GroupProductService();
    	ProductService productService = new ProductService();
    	
    	request.setAttribute("products", productService.getAllProducts());
    	request.setAttribute("groupsProduct", groupProductService.getAllGroupProduct());
    }     
}
