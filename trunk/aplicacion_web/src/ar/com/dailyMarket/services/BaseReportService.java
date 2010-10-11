package ar.com.dailyMarket.services;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.DynaActionForm;

import ar.com.dailyMarket.model.GroupProduct;
import ar.com.dailyMarket.model.HourlyBand;
import ar.com.dailyMarket.model.Product;

public class BaseReportService {
	
	public Map<String,String> getFilters (DynaActionForm form) {
    	ProductService productService = new ProductService();
    	GroupProductService groupProductService = new GroupProductService();
    	HourlyBandService hourlyBandService = new HourlyBandService();
    	Product product = null;
    	GroupProduct groupProduct = null;
    	HourlyBand hourlyBand = null;
    	
    	if (((Long)(form.get("productId"))).longValue() != new Long(-1).longValue()) {
    		product = productService.getProductByPK((Long)form.get("productId"));
    	}
    	if (((Long)(form.get("groupProductId"))).longValue() != new Long(-1).longValue()) {
    		groupProduct = groupProductService.getGroupProductByPK((Long)form.get("groupProductId"));
    	}
    	if (((Long)(form.get("hourlyBandId"))).longValue() != new Long(-1).longValue()) {
    		hourlyBand = hourlyBandService.getHourlyBandByPK((Long)form.get("hourlyBandId"));
    	}
    	Map<String, String> filters = new HashMap<String, String>();
    	filters.put("periodo",form.get("yearFrom") + " - " + form.get("yearTo"));
    	filters.put("productFilter", product != null ? product.getName() : "Todos");
    	filters.put("groupProduct", groupProduct != null ? groupProduct.getName() : "Todos");
    	filters.put("hourlyBand", hourlyBand != null ? hourlyBand.getDetail() : "Todas");
    	return filters;
    }
}
