package ar.com.dailyMarket.ui;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class HomeAction extends BaseAction {             
	
	public ActionForward changeLocale(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	request.getPathInfo();
        request.getServletPath();
        request.getRequestURI();
        request.getRemoteHost();
        request.getRemoteAddr();  
        setLocale(request, new Locale(request.getParameter("lang")));           	    	
        return mapping.findForward("showHome");       
    }                             
    
    public ActionForward doError (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	return mapping.findForward("showError");    	
    }    
    
    public ActionForward initAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	//preguntar por el tipo de usuario y dependiendo, por ahora mandarlo a una pagina u a otra    	
    	//return executeNext("doHome", "showHome", mapping, form, request, response);
    	return mapping.findForward("showHome");
    }        
}