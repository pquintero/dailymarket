package ar.com.dailyMarket.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import ar.com.dailyMarket.model.Product;
import ar.com.dailyMarket.services.GroupProductService;
import ar.com.dailyMarket.services.ProductService;
import ar.com.dailyMarket.ui.validator.Validator;

public class ProductAction extends BaseAction {
	
	public ActionForward showFilter (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {    	    
    	initForm((DynaActionForm)form);
    	setGroupUserRequest(request);
    	return mapping.findForward("filter");
    }             
    
    public ActionForward saveAndBackToCheckPoint (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ProductService productService = new ProductService();
    	ActionErrors errors = validateForm((DynaActionForm)form, request);
    	if (!errors.isEmpty()) {
    		saveErrors(request, errors);
    		setGroupUserRequest(request);
    		return super.initAction(mapping, form, request, response);
    	}
    	productService.save(form);
    	return super.stepBack(mapping, form, request, response);
    } 
    
    public ActionForward executeFilter (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ProductService productService = new ProductService();
    	request.setAttribute("items", productService.executeFilter((DynaActionForm)form));    	
    	setGroupUserRequest(request);
    	return mapping.findForward("filter");
    }
    
    public ActionForward update (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ProductService productService = new ProductService();
    	ActionErrors errors = validateForm((DynaActionForm)form, request);
    	if (!errors.isEmpty()) {
    		saveErrors(request, errors);
    		setGroupUserRequest(request);
        	return mapping.findForward("showDetail");
    	}
    	productService.update(form,productService.getProductByPK((Long)((DynaActionForm)form).get("id")));
    	return super.stepBack(mapping, form, request, response);
    }
    
    public ActionForward findByPK (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ProductService productService = new ProductService();
    	setFormProperties((DynaActionForm)form,productService.getProductByPK((Long)((DynaActionForm)form).get("id")));
    	setGroupUserRequest(request);
    	return mapping.findForward("showDetail");
    }
    
    private void initForm(DynaActionForm form) {
    	form.set("name", "");
    	form.set("description", "");
    	form.set("code", "");
    	form.set("groupProductId", null);
    }
    
    private void setFormProperties(DynaActionForm form, Product product) {
    	form.set("id", product.getId());
    	form.set("name", product.getName());
    	form.set("actualStock", product.getActualStock());
    	form.set("price", product.getPrice());
    	form.set("sizeOfPurchase", product.getSizeOfPurchase());
    	form.set("code", product.getCode());
    	form.set("description", product.getDescription());
    	form.set("groupProductId", product.getGroupProduct() != null ? product.getGroupProduct().getId() : null);
    	form.set("repositionStock", product.getRepositionStock() != null ? product.getRepositionStock() : null);
    }
    
    private void setGroupUserRequest (HttpServletRequest request) {
    	GroupProductService groupProductService = new GroupProductService();
    	request.setAttribute("groupsProduct", groupProductService.getAllGroupProduct());
    }
    
    @Override
    public ActionForward redirectCreate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	setGroupUserRequest(request);
    	return super.redirectCreate(mapping, form, request, response);
    }   
    
    public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ProductService productService = new ProductService();
    	productService.delete((Long)((DynaActionForm)form).get("id"));
    	return executeFilter(mapping, form, request, response);    	
    }
    
    private ActionErrors validateForm(DynaActionForm form, HttpServletRequest request) {    	
    	ActionErrors errors = new ActionErrors();
    	Validator.isEmpty(form.get("code"), errors, request, getResources(request).getMessage("ProductForm.code"));
    	Validator.isEmpty(form.get("name"), errors, request, getResources(request).getMessage("ProductForm.name"));
    	Validator.isEmpty(form.get("description"), errors, request, getResources(request).getMessage("ProductForm.description"));
    	Validator.isDouble(form.get("price"), errors, request, getResources(request).getMessage("ProductForm.price"), true);
    	Validator.isInteger(form.get("sizeOfPurchase"), errors, request, getResources(request).getMessage("ProductForm.sizeOfPurchase"), true);
    	Validator.isInteger(form.get("actualStock"), errors, request, getResources(request).getMessage("ProductForm.actualStock"), true);
    	Validator.isInteger(form.get("repositionStock"), errors, request, getResources(request).getMessage("ProductForm.repositionStock"), true);
    			
    	return errors;
    }
}
