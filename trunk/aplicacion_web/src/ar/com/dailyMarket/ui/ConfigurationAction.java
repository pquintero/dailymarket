package ar.com.dailyMarket.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import ar.com.dailyMarket.model.Configuration;
import ar.com.dailyMarket.services.ConfigurationService;
import ar.com.dailyMarket.services.UserService;
import ar.com.dailyMarket.ui.validator.Validator;

public class ConfigurationAction extends BaseAction {
	
	public ActionForward initAction (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		setAtributesInRequest(request);		
		setFormProperties((DynaActionForm)form);
		return mapping.findForward("showConfiguration");
	}	
	
	public ActionForward addSendNotification (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		UserService userService = new UserService();
		userService.addNotificationInUser((Long)((DynaActionForm)form).get("userId"));		
		return initAction(mapping, form, request, response);
	}
	
	private void setFormProperties(DynaActionForm form) {
		form.set("userId", null);		
		ConfigurationService configurationService = new ConfigurationService();
		Configuration conf = configurationService.getConfiguration();
		if (conf != null) {
			form.set("timer", conf.getTimer());
			form.set("emailDeposito", conf.getEmailDeposito());
		}
	}
	
	public ActionForward saveAndBack (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		ConfigurationService configurationService = new ConfigurationService();
		ActionErrors errors = validateForm((DynaActionForm)form, request);
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			setAtributesInRequest(request);
			return mapping.findForward("showConfiguration");
		}
		configurationService.save((DynaActionForm)form);
		return initAction(mapping, form, request, response);
	}
	
	private void setAtributesInRequest (HttpServletRequest request) {
		UserService userService = new UserService();
		request.setAttribute("users", userService.getUserToNotifications(false)); //para q no me traiga los q ya estan
		request.setAttribute("items", userService.getUserToNotifications(true));
	}
	
	public ActionForward deleteSendNotiication (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		UserService userService = new UserService();
		userService.deleteNotificationInUser((Long)((DynaActionForm)form).get("userId"));		
		return initAction(mapping, form, request, response);
	}
	
	private ActionErrors validateForm(DynaActionForm form, HttpServletRequest request) {    	
    	ActionErrors errors = new ActionErrors();
    	Validator.isInteger(form.get("timer"), errors, request, getResources(request).getMessage("ConfigrationForm.timerAlarm"), true);    	
    	return errors;
    }
}
