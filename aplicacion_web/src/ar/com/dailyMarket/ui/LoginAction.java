package ar.com.dailyMarket.ui;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
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
        ActionErrors error = new ActionErrors();
        error.add("", new ActionError("Usuario o Password Inválido"));
    	return mapping.findForward("error");
    }

    public ActionForward doLogout(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return mapping.findForward("logout");
    }
}
