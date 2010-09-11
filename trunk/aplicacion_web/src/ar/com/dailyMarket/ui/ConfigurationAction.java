package ar.com.dailyMarket.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import ar.com.dailyMarket.services.UserService;

public class ConfigurationAction extends BaseAction {
	
	public ActionForward initAction (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		UserService userService = new UserService();
		request.setAttribute("users", userService.getUserToNotifications(false)); //para q no me traiga los q ya estan
		request.setAttribute("items", userService.getUserToNotifications(true));
		initForm((DynaActionForm)form);
		return mapping.findForward("showConfiguration");
	}	
	
	public ActionForward addSendNotification (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		UserService userService = new UserService();
		userService.addNotificationInUser((Long)((DynaActionForm)form).get("userId"));		
		return initAction(mapping, form, request, response);
	}
	
	private void initForm(DynaActionForm form) {
		form.set("userId", null);
	}
}
