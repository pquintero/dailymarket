package ar.com.dailyMarket.ui;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class LoginAction extends BaseAction {
    
    public ActionForward doHome(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        setLocale(request, new Locale("es"));    	
        mapping.setForward("home");
        return execute(mapping, form, request, response);
    }

    public ActionForward doError(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        mapping.setForward("error");
        return execute(mapping, form, request, response);
    }

    public ActionForward doLogout(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return mapping.findForward("logout");
    }
}
