package ar.com.dailyMarket.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import ar.com.dailyMarket.model.User;
import ar.com.dailyMarket.services.GroupUserService;
import ar.com.dailyMarket.services.UserService;

public class UserAction extends BaseAction {         
    
    public ActionForward showFilter (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {    	    
    	initForm((DynaActionForm)form);
    	setGroupUserRequest(request);
    	return mapping.findForward("filter");
    }             
    
    public ActionForward saveAndBackToCheckPoint (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	UserService userService = new UserService();
    	userService.save(form);
    	return super.stepBack(mapping, form, request, response);
    } 
    
    public ActionForward executeFilter (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	UserService userService = new UserService();
    	request.setAttribute("items", userService.executeFilter((DynaActionForm)form));
    	setGroupUserRequest(request);
    	return mapping.findForward("filter");
    }
    
    public ActionForward update (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	UserService userService = new UserService();
    	userService.update(form,userService.getUserByPK((Long)((DynaActionForm)form).get("id")));
    	return super.stepBack(mapping, form, request, response);
    }
    
    public ActionForward findByPK (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	UserService userService = new UserService();
    	setFormProperties((DynaActionForm)form,userService.getUserByPK((Long)((DynaActionForm)form).get("id")));
    	setGroupUserRequest(request);
    	return mapping.findForward("showDetail");
    }
    
    private void initForm(DynaActionForm form) {
    	form.set("name", "");
    	form.set("lastName", "");
    	form.set("dni", "");
    	form.set("user", "");
    	form.set("idStr", "");
    	form.set("groupUserId", null);
    }
    
    private void setFormProperties(DynaActionForm form, User user) {
    	form.set("id", user.getId());
    	form.set("name", user.getName());
    	form.set("lastName", user.getLastName());
    	form.set("dni", user.getDni());
    	form.set("password", user.getPassword());
    	form.set("user", user.getUser());  
    	form.set("groupUserId", user.getGroupUser().getId());
    }
    
    private void setGroupUserRequest (HttpServletRequest request) {
    	GroupUserService groupUserService = new GroupUserService();
    	request.setAttribute("groupsUsers", groupUserService.getAllGroupsUsers());
    }
    
    @Override
    public ActionForward redirectCreate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	setGroupUserRequest(request);
    	return super.redirectCreate(mapping, form, request, response);
    }
}