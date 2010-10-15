package ar.com.dailyMarket.ui;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import ar.com.dailyMarket.services.ProductService;

public class ManagerAction extends BaseAction {

    public ActionForward initAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
    	ProductService productService = new ProductService();
    	request.setAttribute("products", productService.getProductWithoutStock());    	
    	((DynaActionForm)form).set("productsIds", productService.getProductsIdsArray());
    	
    	if (!(Boolean)((DynaActionForm)form).get("envioMail")) {
    		request.getSession().setAttribute("mail",null);
		}
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
    
    public ActionForward sendOrder(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException {
    	ProductService productService = new ProductService();
    	StringBuffer sb = productService.sendOrder((Long[])((DynaActionForm)form).get("productsIds")); //desde aca enviar email al deposito con el pedido    	
    	
    	//HABRIA QUE PREGUNTAR SI NO ENVIO NADA QUE NO GUARDE NADA
    	request.getSession().setAttribute("mail",sb.toString().replaceAll("\\n", "<br>\n"));
    	((DynaActionForm)form).set("envioMail", true);
    	
    	return initAction(mapping, form, request, response);
    }
}
