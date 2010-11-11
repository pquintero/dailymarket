package ar.com.dailyMarket.ui;

import java.sql.SQLException;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
    	request.setAttribute("products", productService.getProductWithoutStock());    	
    	((DynaActionForm)form).set("productsIds", productService.getProductsIdsArray());
    	//FIXME IMPORTANTE ver porque cada ves que se refresca viene distinto el estado de los Productos
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
    
    public ActionForward redirectToConfirmSendOrder(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException {
    	//Armar mail sin enviarlo 
    	//validar si hay ids seleccionados?
    	ProductService productService = new ProductService();
    	List<Product> products = productService.getProductsFromArray((String[])((DynaActionForm)form).get("productsIds"));
    	
    	String body = productService.createMessage(products).toString();
    	((DynaActionForm)form).set("mailBody", body);
    	
    	//Preguntar juani por n destinatarios por configuracion
    	String to = productService.getMaildestinataries() + ";"; //Concatenados con ';'
    	((DynaActionForm)form).set("mailTo", to);
    	
    	//TODO ver from y subject
//    	BaseAction.class.getResourceAsStream("/mail/mail-properties.xml");
    	
    	User u = new UserService().getUser(request.getRemoteUser());
    	((DynaActionForm)form).set("mailFrom", u.getEmail());
    	
    	((DynaActionForm)form).set("mailSubject", "Productos Pendientes de Pedido");
    	
    	return mapping.findForward("showConfirmMail");
    }
    
    public ActionForward cancelMail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException {
    	return initAction(mapping, form, request, response);
    }
    
    public ActionForward sendOrder(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException {
    	//Mandar mail con los datos del form, previamente validar datos
    	ProductService productService = new ProductService();
    	String body = (String)((DynaActionForm)form).get("mailBody");
    	String to = (String) ((DynaActionForm)form).get("mailTo");
    	String from = (String) ((DynaActionForm)form).get("mailFrom");
    	String subject = (String) ((DynaActionForm)form).get("mailSubject");
    	
    	if(!validarSendOrder(body, to, from, subject)) {
    		//TODO save errors
    		return redirectToConfirmSendOrder(mapping, form, request, response);
    	}
    	
    	productService.sendOrder((String[])((DynaActionForm)form).get("productsIds"), to, from, subject, body); //desde aca enviar email al deposito con el pedido    	
    	return initAction(mapping, form, request, response);
    }
    
    public boolean validarSendOrder(String body, String to, String from, String subject) {
    	//TODO errors
    	if(!StringUtils.isNotEmpty(body)) {
    		//body vacio
    		return false;
    	}
    	if(!StringUtils.isNotEmpty(subject)) {
    		//subject vacio
    		return false;
    	}
    	if(!StringUtils.isNotEmpty(to)) {
    		//to vacio
    		return false;
    	}
    	if(!StringUtils.isNotEmpty(from)) {
    		//from vacio
    		return false;
    	}
    	if (!isEmail(from)) {
    		//mail de emisor invalido
			return false;
		}
    	
    	StringTokenizer st = new StringTokenizer(to, ";");
    	while (st.hasMoreTokens()) {
			String next = st.nextToken();
			if (!isEmail(next)) {
	    		//mail de emisor invalido
				return false;
			}
		}
    	
    	return true;
    }
    
    //metodo para validar correo electronio
    public boolean isEmail(String correo) {
		Pattern pat = Pattern.compile("^([0-9a-zA-Z]([_.w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-w]*[0-9a-zA-Z].)+([a-zA-Z]{2,9}.)+[a-zA-Z]{2,3})$");
		Matcher mat = pat.matcher(correo);
		return mat.find();   
	}
}
