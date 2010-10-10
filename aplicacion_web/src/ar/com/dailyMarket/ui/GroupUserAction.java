package ar.com.dailyMarket.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import ar.com.dailyMarket.model.GroupUser;
import ar.com.dailyMarket.services.GroupUserService;

public class GroupUserAction extends BaseAction {
	
	public ActionForward showFilter (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		initForm((DynaActionForm)form);    	
    	return mapping.findForward("filter");
    }
	
	public ActionForward saveAndBackToCheckPoint (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	GroupUserService groupUserService = new GroupUserService();
    	groupUserService.save(form);
    	return super.stepBack(mapping, form, request, response);
    }
	
	public ActionForward executeFilter (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	GroupUserService groupUserService = new GroupUserService();
    	request.setAttribute("items", groupUserService.executeFilter((DynaActionForm)form));    	
    	return mapping.findForward("filter");
    }
    
    public ActionForward update (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	GroupUserService groupUserService = new GroupUserService();
    	groupUserService.update(form,groupUserService.getGroupUserByPK((Long)((DynaActionForm)form).get("id")));
    	return super.stepBack(mapping, form, request, response);
    }
    
    public ActionForward findByPK (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	GroupUserService groupUserService = new GroupUserService();
    	setFormProperties((DynaActionForm)form,groupUserService.getGroupUserByPK((Long)((DynaActionForm)form).get("id")));
    	return mapping.findForward("showDetail");
    }
    
    private void initForm(DynaActionForm form) {
    	form.set("name", "");
    	form.set("description", "");
    }
    
    private void setFormProperties(DynaActionForm form, GroupUser groupUser) {
    	form.set("id", groupUser.getId());
    	form.set("name", groupUser.getName());
    	form.set("description", groupUser.getDescription());
    }
    
    public ActionForward delete (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	GroupUserService groupUserService = new GroupUserService();
    	groupUserService.delete((Long)((DynaActionForm)form).get("id"));
    	return executeFilter(mapping, form, request, response);
    }
}
