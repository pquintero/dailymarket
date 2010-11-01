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
	
	public String getCheck(){
    	Product product = (Product) this.getCurrentRowObject();
    	Long[] productsIds = (Long[])this.getPageContext().getAttribute("productsIds");
    	int i = 0;
    	if (product.getState().equals(Product.PRODUCT_STATE_PENDING)) {
	    	while (i < productsIds.length) {
	    		if(productsIds[i].equals(product.getId())) 
	    			return "<input type=\"checkbox\" onclick=\"isAllUnselected();\" name=\"productsIds\" value=\""+product.getId()+"\" checked>";
	    		i++;
			}
	    	return "<input type=\"checkbox\" onclick=\"isAllUnselected();\" name=\"productsIds\" value=\""+product.getId()+"\">";
    	} else {
    		return "&nbsp;";
    	}
    }  	
}
