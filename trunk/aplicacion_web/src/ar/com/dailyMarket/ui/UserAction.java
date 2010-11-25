package ar.com.dailyMarket.ui;

import java.io.IOException;
import java.util.Locale;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;

import ar.com.dailyMarket.model.Image;
import ar.com.dailyMarket.model.User;
import ar.com.dailyMarket.services.GroupUserService;
import ar.com.dailyMarket.services.ImageService;
import ar.com.dailyMarket.services.UserService;
import ar.com.dailyMarket.ui.validator.Validator;

public class UserAction extends BaseAction {         
    
    @Override
    public ActionForward initAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	((DynaActionForm)form).set("attachId",new Long(-1));
    	((DynaActionForm)form).set("id",new Long(-1));
    	request.getSession().setAttribute("image", null);
    	return super.initAction(mapping, form, request, response);
    }
	
	public ActionForward showFilter (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {    	    
    	initForm((DynaActionForm)form);
    	setGroupUserRequest(request);
    	return mapping.findForward("filter");
    }             
    
    public ActionForward saveAndBackToCheckPoint (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	UserService userService = new UserService();
    	ActionErrors errors = validateForm((DynaActionForm)form, request);
    	if (!errors.isEmpty()) {	
    		saveErrors(request, errors);
    		setGroupUserRequest(request);
    		((DynaActionForm)form).set("id",new Long(-1));
    		((DynaActionForm)form).set("attachId",new Long(-1));
    		request.getSession().setAttribute("image", null);
    		return super.initAction(mapping, form, request, response);
    	}
    	userService.save(form);
    	return super.stepBack(mapping, form, request, response);
    } 
    
    public ActionForward executeFilter (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	UserService userService = new UserService();
    	ActionErrors errors = validateFilter((DynaActionForm)form, request);
    	if (!errors.isEmpty()) {	
    		saveErrors(request, errors);
    		setGroupUserRequest(request);
    		((DynaActionForm)form).set("id",new Long(-1));
    		((DynaActionForm)form).set("attachId",new Long(-1));
    		request.getSession().setAttribute("image", null);
    		return mapping.findForward("filter");
    	}
    	request.setAttribute("items", userService.executeFilter((DynaActionForm)form));
    	setGroupUserRequest(request);    	
    	    	
    	return mapping.findForward("filter");
    }
    
    public ActionForward update (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	UserService userService = new UserService();
    	userService.update(form, userService.getUserByPK((Long)((DynaActionForm)form).get("id")));
    	
    	ActionErrors errors = validateForm((DynaActionForm)form, request);
    	if (!errors.isEmpty()) {	
    		saveErrors(request, errors);
    		setGroupUserRequest(request);
    		User user = userService.getUserByPK((Long)((DynaActionForm)form).get("id"));
    		if (user.getImage() != null) {
        		request.getSession().setAttribute("image", user.getImage());
        		((DynaActionForm)form).set("attachId",user.getImage().getId());
        	} else {
        		((DynaActionForm)form).set("attachId",new Long(-1));
        		request.getSession().setAttribute("image", null);
        	}
    		return mapping.findForward("showDetail");
    	}
    	return super.stepBack(mapping, form, request, response);
    }
    
    public ActionForward findByPK (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	UserService userService = new UserService();
    	User user = userService.getUserByPK((Long)((DynaActionForm)form).get("id"));
    	setFormProperties((DynaActionForm)form,user);
    	setGroupUserRequest(request);
    	
    	if (user.getImage() != null) {
    		request.getSession().setAttribute("image", user.getImage());
    		((DynaActionForm)form).set("attachId",user.getImage().getId());
    	} else {
    		((DynaActionForm)form).set("attachId",new Long(-1));
    		request.getSession().setAttribute("image", null);
    	}
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
    
    public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	UserService userService = new UserService();
    	userService.delete((String)((DynaActionForm)form).get("user"));
    	((DynaActionForm)form).set("user", ""); //limpio el dato para el executeFilter
    	return executeFilter(mapping, form, request, response);
    }
    
    private ActionErrors validateForm(DynaActionForm form, HttpServletRequest request) {    	
    	ActionErrors errors = new ActionErrors();
    	Locale locale = new Locale("es");
    	Validator.isEmpty(form.get("name"), errors, request, getResources(request).getMessage(locale, "UserForm.name"));
    	Validator.isEmpty(form.get("lastName"), errors, request, getResources(request).getMessage(locale, "UserForm.lastName"));
    	Validator.isInteger(form.get("dni"), errors, request, getResources(request).getMessage(locale, "UserForm.dni"), true);
    	Validator.isEmpty(form.get("user"), errors, request, getResources(request).getMessage(locale, "UserForm.user"));
    	Validator.isEmpty(form.get("password"), errors, request, getResources(request).getMessage(locale, "UserForm.password"));
    	
    	return errors;
    }
    
    private ActionErrors validateFilter(DynaActionForm form, HttpServletRequest request) {
    	ActionErrors errors = new ActionErrors();
    	Validator.isInteger(form.get("dni"), errors, request, getResources(request).getMessage("UserForm.dni"), false);
    	Validator.isInteger(form.get("idStr"), errors, request, getResources(request).getMessage("UserForm.id"), false);

    	return errors;
    }
    
    public ActionForward initImage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		((DynaActionForm) form).set("description", "");
		return mapping.findForward("showImage");
	}
    
    public ActionForward confirmImage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
        ImageService imageService = new ImageService();	
        UserService userService = new UserService();
        
        Context initCtx;
        Context envCtx;
        String uploadPath = "";
		try {
			initCtx = new InitialContext();
			envCtx = (Context)initCtx.lookup("java:comp/env");
			uploadPath = (String) envCtx.lookup("uploadPath");
		} catch (NamingException e) {			
			e.printStackTrace();			
		}        
		
        ActionErrors errors = new ActionErrors();
        FormFile file = (FormFile) ((DynaActionForm) form).get("file"); 
        if (!file.getContentType().startsWith("image")) {
   			errors.add("file", new ActionError("errors.invalid", "Archivo debe ser una imagen.")); 
            saveErrors(request, errors);
            return findByPK(mapping, form, request, response);
        }
        if (file.getFileSize() == 0 ) {
   			errors.add("file", new ActionError("errors.required", "Archivo")); 
            saveErrors(request, errors);
            return findByPK(mapping, form, request, response);
        }
        
		((DynaActionForm) form).set("uploadPath", uploadPath);
        
		User user = userService.getUserByPK((Long)((DynaActionForm)form).get("id"));
        Image img = imageService.saveImage((DynaBean) form);
        if (user.getImage() != null) {
        	imageService.deleteImg(user);
        }
        userService.saveImage(user, img, file);
		return findByPK(mapping, form, request, response);
	}	
    
    public ActionForward deleteImage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
    	UserService userService = new UserService();
    	User user = userService.getUserByPK((Long)((DynaActionForm)form).get("id"));
    	
    	ImageService imageService = new ImageService();
    	imageService.deleteImg(user);
    	return findByPK(mapping, form, request, response);
    } 
}