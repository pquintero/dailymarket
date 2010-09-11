package ar.com.dailyMarket.ui;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.com.dailyMarket.model.GroupUser;
import ar.com.dailyMarket.services.UserService;

public class HomeAction extends BaseAction {             
	
	public ActionForward changeLocale(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	request.getPathInfo();
        request.getServletPath();
        request.getRequestURI();
        request.getRemoteHost();
        request.getRemoteAddr();  
        setLocale(request, new Locale(request.getParameter("lang")));           	    	
        return mapping.findForward("showAdminHome");       
    }                             
    
    public ActionForward doError (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	return mapping.findForward("showError");    	
    }    
    
    public ActionForward initAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	String user = request.getRemoteUser();
    	UserService userService = new UserService();
    	String role = userService.getRoleInUser(user);
    	
    	if(GroupUser.ROLE_ADMIN.equals(role)) {
    		return mapping.findForward("showAdminHome");
    	}
    	return mapping.findForward("showManagerHome");
    }        
}