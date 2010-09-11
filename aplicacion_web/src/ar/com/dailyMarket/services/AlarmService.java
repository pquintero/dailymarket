package ar.com.dailyMarket.services;

import java.util.Iterator;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ar.com.dailyMarket.model.Product;

public class AlarmService extends MailService {
	
	public void sendMail() {
		ProductService productService = new ProductService();
		UserService userService = new UserService();
		String time = getTime();
		if (time != null) {
			String[] emails = userService.getEmailsToNotifications();    	     	    	    
			sendMail(emails, new StringBuffer(createMessage(productService.getProductWithoutStock(), getTime())));
		}		
	}
	
	private String getTime() {
		Context initCtx;
		try {			
			initCtx = new InitialContext();
			Context envCtx = (Context)initCtx.lookup("java:comp/env");
		    return (String) envCtx.lookup("timer");
		} catch (NamingException e1) {
			e1.printStackTrace();
		}
		return null;
	}				

	public StringBuffer createMessage (List<Product> products, String timer) {
		StringBuffer message = new StringBuffer();		
		
		if (!products.isEmpty()) {
			message.append("Productos sin emitir pedido desde hace mas " + timer.toString() +" días\n");
			for (Iterator<Product> it = products.iterator(); it.hasNext();) {
				Product product = it.next();
				message.append("\tProducto: " + product.getName() + " - Código " + product.getCode() + " - Desde " + product.getDateWithoutStock() + "\n");
			}
			message.append(" \n");
		}								
		return message;
	}

	public void sendMail(String emails[], StringBuffer message) {					
	    if ((message == null) || (message.toString().equals(""))) {
	    	return;
	    }
		super.sendMail(emails, message.toString());				    		    
	}		
}
