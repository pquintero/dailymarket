package ar.com.dailyMarket.ui.decorators;

import org.displaytag.decorator.TableDecorator;

import ar.com.dailyMarket.model.Product;

public class ProductDecorator extends TableDecorator {
	
	public String getState() {
		String state = ((Product)this.getCurrentRowObject()).getState(); 
		if (state.equals(Product.PRODUCT_STATE_SEND)) {
			state = "SOLICITADO";
		} else if (state.equals(Product.PRODUCT_STATE_PENDING)) {
			state = "ENVÍO PENDIENTE";
		} else if (state.equals(Product.PRODUCT_STATE_STOCK)) {
			state = "CON STOCK";
		} 	
	 	
		return state;
	}
}
