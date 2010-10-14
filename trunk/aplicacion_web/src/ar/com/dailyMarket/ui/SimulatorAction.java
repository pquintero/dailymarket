package ar.com.dailyMarket.ui;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		setProductAndGroupProduct(request);
		request.setAttribute("products", new ArrayList<Product>());
		return mapping.findForward("showSimulator");
	}
	
	public ActionForward executeSimulator (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SimulatorService simulatorService = new SimulatorService();
    	request.setAttribute("productsList", simulatorService.executeFilter((DynaActionForm)form));    	
    	setProductAndGroupProduct(request);
    	return mapping.findForward("showSimulator");
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
