package ar.com.dailyMarket.services;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import ar.com.dailyMarket.model.Product;

public class AlarmService extends MailService {
	
	public void sendMail(List<Product> toSend, String time) throws ClassNotFoundException, SQLException {
		if (!toSend.isEmpty()) {
			UserService userService = new UserService();
			String[] emails = userService.getEmailsToNotifications();    	     	    	    
			super.sendMail(emails, new StringBuffer(createMessage(toSend, time)), "");
		}
	}

	public StringBuffer createMessage (List<Product> products, String timer) {
		StringBuffer message = new StringBuffer();		
		
		if (!products.isEmpty()) {
			message.append("Productos sin emitir pedido desde hace mas de" + timer.toString() +" días\n");
			for (Iterator<Product> it = products.iterator(); it.hasNext();) {
				Product product = it.next();
				message.append("\tProducto: " + product.getName() + " - Código " + product.getCode() + " - Desde " + product.getDateWithoutStock() + "\n");
			}
			message.append(" \n");
		}								
		return message;
	}
}
