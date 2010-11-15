package ar.com.dailyMarket.services;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import ar.com.dailyMarket.model.Product;

public class AlarmService extends MailService {
	
	public void sendMail() throws ClassNotFoundException, SQLException {
		ProductService productService = new ProductService();
		UserService userService = new UserService();
		ar.com.dailyMarket.model.Configuration conf = new ConfigurationService().getConfiguration();
		String time = conf != null ? conf.getTimer().toString() : null;
		if (time != null) {
			String[] emails = userService.getEmailsToNotifications();    	     	    	    
			super.sendMail(emails, new StringBuffer(createMessage(productService.getProductWithoutStock(), time)));
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
