package ar.com.dailyMarket.ui;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import ar.com.dailyMarket.model.GroupProduct;
import ar.com.dailyMarket.model.Product;
import ar.com.dailyMarket.services.GroupProductService;
import ar.com.dailyMarket.services.ProductService;
import ar.com.dailyMarket.services.SimulatorService;
import ar.com.dailyMarket.ui.validator.Validator;
import ar.com.dailyMarket.util.CombSelect;

public class SimulatorAction extends BaseAction {
	
	public ActionForward initAction (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		this.initialize(mapping, form, request);
		return mapping.findForward("showSimulator");
	}
	
	public void initialize(ActionMapping mapping, ActionForm form, HttpServletRequest request) {
		ProductService productService = new ProductService();
		((DynaActionForm)form).initialize(mapping);
		((DynaActionForm)form).set("margen","");
		((DynaActionForm)form).set("all","");
		((DynaActionForm)form).set("yearFrom","2009");
		((DynaActionForm)form).set("days","");
		((DynaActionForm)form).set("simuladorArray", new String[0]);
		
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
		
		setGroupProduct(request);
		request.setAttribute("productsList", new ArrayList<Product>());
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
    	
    	((DynaActionForm)form).set("all","");
    	setGroupProduct(request);
    	return mapping.findForward("showSimulator");
    }
	
	public ActionForward executeSimulator (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionErrors errors = validateForm((DynaActionForm)form, request);
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return executeFilter(mapping, form, request, response);
		}
		SimulatorService simulatorService = new SimulatorService();
		request.setAttribute("productsList", simulatorService.executeFilter((DynaActionForm)form));
		
		simulatorService.executeSimulation((DynaActionForm)form);
		
    	setGroupProduct(request);
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
	
	private void setGroupProduct (HttpServletRequest request) {
    	GroupProductService groupProductService = new GroupProductService();
    	request.setAttribute("groupsProduct", groupProductService.getAllGroupProduct());
    }     
	
	private ActionErrors validateForm(DynaActionForm form, HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
    	Validator.isInteger(form.get("margen"), errors, request, "Margen de Días", true);
    	Validator.isInteger(form.get("days"), errors, request, "Días a Simular", true);
    	if(errors.isEmpty()) {
    		Integer dias = Integer.valueOf((String) form.get("days"));
    		Integer margen = Integer.valueOf((String)form.get("margen"));
    		
    		if(dias < 1) {
    			errors.add("", new ActionError("errors.integerChico","Días a Simular"));
    		}
    		if(margen < 1){
    			errors.add("", new ActionError("errors.integerChico", "Margen de Días"));
    		}
    		if(margen > dias) {
    			errors.add("", new ActionError("errors.margenGrande"));
    		}
    	}
    	
    	return errors;
	}
}
