package ar.com.dailyMarket.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import ar.com.dailyMarket.model.GroupProduct;
import ar.com.dailyMarket.services.GroupProductService;

public class GroupProductAction extends BaseAction {
	
	public ActionForward showFilter (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	initForm((DynaActionForm)form);    	
    	return mapping.findForward("filter");
    }
	
	public ActionForward saveAndBackToCheckPoint (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	GroupProductService groupProductService = new GroupProductService();
    	groupProductService.save(form);
    	return super.stepBack(mapping, form, request, response);
    }
	
	public ActionForward executeFilter (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	GroupProductService groupProductService = new GroupProductService();
    	request.setAttribute("items", groupProductService.executeFilter((DynaActionForm)form));    	
    	return mapping.findForward("filter");
    }
    
    public ActionForward update (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	GroupProductService groupProductService = new GroupProductService();
    	groupProductService.update(form,groupProductService.getProductByPK((Long)((DynaActionForm)form).get("id")));
    	return super.stepBack(mapping, form, request, response);
    }
    
    public ActionForward findByPK (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	GroupProductService groupProductService = new GroupProductService();
    	setFormProperties((DynaActionForm)form,groupProductService.getProductByPK((Long)((DynaActionForm)form).get("id")));
    	return mapping.findForward("showDetail");
    }
    
    private void initForm(DynaActionForm form) {
    	form.set("name", "");
    	form.set("description", "");
    }
    
    private void setFormProperties(DynaActionForm form, GroupProduct groupProduct) {
    	form.set("id", groupProduct.getId());
    	form.set("name", groupProduct.getName());
    	form.set("description", groupProduct.getDescription());
    }
}
