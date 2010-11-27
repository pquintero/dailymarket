package ar.com.dailyMarket.ui;

import java.io.IOException;
import java.sql.SQLException;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import ar.com.dailyMarket.model.Product;
import ar.com.dailyMarket.model.User;
import ar.com.dailyMarket.services.ProductService;
import ar.com.dailyMarket.services.UserService;

public class ManagerAction extends BaseAction {

    public ActionForward initAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	ProductService productService = new ProductService();
    	
    	List<Product> productos = productService.getProductWithoutStock();
    	request.setAttribute("products", productos);    	
    	((DynaActionForm)form).set("productsIds", productService.getProductsIdsArray(productos));
    	return mapping.findForward("showManagerHome");
    }
    
    public ActionForward doHomeEstadisticas(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	request.getSession().setAttribute("initMethod", true);
    	return mapping.findForward("showEstadisticasHome");
    }
    
    public ActionForward doHomeIndicadores(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	request.getSession().setAttribute("initMethod", true);
    	return mapping.findForward("showIndicadoresHome");
    }
    
    public ActionForward doHomeReportes(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	request.getSession().setAttribute("initMethod", true);
    	return mapping.findForward("showReportesHome");
    }        
    
    public ActionForward redirectToConfirmSendOrder(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, InvalidPropertiesFormatException, IOException {
    	//Armar mail sin enviarlo 
    	//validar si hay ids seleccionados?
    	ProductService productService = new ProductService();
    	List<Product> products = productService.getProductsFromArray((String[])((DynaActionForm)form).get("productsIds"));
    	User u = new UserService().getUser(request.getRemoteUser());
    	
    	String body = productService.createMessage(products).toString();
    	((DynaActionForm)form).set("mailBody", body);
    	
    	String to = productService.getMaildestinataries() + ";" + u.getEmail() + ";"; //ENVÍO AL DEPOSITO Y AL USUARIO Q HIZO EL PEDIDO
    	((DynaActionForm)form).set("mailTo", to);
    	
    	Properties props = new Properties();
		
    	props.loadFromXML(BaseAction.class.getResourceAsStream("/mail/mail-properties.xml"));
    	//el from lo agrego para que se vea readonly
    	((DynaActionForm)form).set("mailFrom", (String) props.get("from"));    	
    	((DynaActionForm)form).set("mailSubject", (String) props.get("subject"));
    	
    	return mapping.findForward("showConfirmMail");
    }
    
    public ActionForward cancelMail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException {
    	return initAction(mapping, form, request, response);
    }
    
    public ActionForward sendOrder(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, InvalidPropertiesFormatException, IOException {
    	//Mandar mail con los datos del form, previamente validar datos
    	//FIXME que solo se mande una vez, solucionar problema con f5
    	
    	ProductService productService = new ProductService();
    	String body = (String)((DynaActionForm)form).get("mailBody");
    	String to = (String) ((DynaActionForm)form).get("mailTo");
    	String subject = (String) ((DynaActionForm)form).get("mailSubject");
    	ActionErrors errors = validarSendOrder(body, to, subject);
    	if(!errors.isEmpty()) {
    		saveErrors(request, errors);
    		return redirectToConfirmSendOrder(mapping, form, request, response);
    	}
    	
    	productService.sendOrder((String[])((DynaActionForm)form).get("productsIds"), to, subject, body); //desde aca enviar email al deposito con el pedido    	
    	return initAction(mapping, form, request, response);
    }
    
    public ActionErrors validarSendOrder(String body, String to, String subject) {
    	ActionErrors errors = new ActionErrors();
		
    	if(StringUtils.isEmpty(body)) {
    		//body vacio
    		errors.add("", new ActionError("errors.bodyEmpty"));
    	}    	
    	if(StringUtils.isEmpty(to)) {
    		//to vacio
    		errors.add("", new ActionError("errors.toEmpty"));
    	}
    	
    	StringTokenizer st = new StringTokenizer(to, ";");
    	while (st.hasMoreTokens()) {
			String next = st.nextToken();
			if (!isEmail(next)) {
	    		//mail de emisor invalido
				errors.add("", new ActionError("errors.invalidMail", next));
			}
		}
    	return errors;
    }
    
    //metodo para validar correo electronio
    public boolean isEmail(String correo) {
		Pattern pat = Pattern.compile("^([0-9a-zA-Z]([_.w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-w]*[0-9a-zA-Z].)+([a-zA-Z]{2,9}.)+[a-zA-Z]{2,3})$");
		Matcher mat = pat.matcher(correo);
		return mat.find();   
	}
}
