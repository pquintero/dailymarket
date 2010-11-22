package ar.com.dailyMarket.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts.action.DynaActionForm;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

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
    	filters.put("productId", product != null ? product.getId().toString() : "-1");
    	filters.put("groupProductId", groupProduct != null ? groupProduct.getId().toString() : "-1");
    	filters.put("groupProduct", groupProduct != null ? groupProduct.getName() : "Todos");
    	filters.put("hourlyBand", hourlyBand != null ? hourlyBand.getDetail() : "Todas");
    	filters.put("hourlyBandId", hourlyBand != null ? hourlyBand.getId().toString() : "-1");
    	filters.put("anioDesde", (String)form.get("yearFrom"));
    	filters.put("anioHasta", (String)form.get("yearTo"));
    	//en el anual vienen en "" en el mensual con el respectivo valor
    	filters.put("mesDesde", (String)((DynaActionForm)form).get("monthFrom"));
  		filters.put("mesHasta", (String)((DynaActionForm)form).get("monthTo"));
    	return filters;
    }
	
	public Map<String,Object> getFiltersObject (DynaActionForm form) {
    	ProductService productService = new ProductService();
    	GroupProductService groupProductService = new GroupProductService();
    	Product product = null;
    	GroupProduct groupProduct = null;

    	Map<String, Object> filters = new HashMap<String, Object>();
    	if (((Long)(form.get("productId"))).longValue() != new Long(-1).longValue()) {
    		product = productService.getProductByPK((Long)form.get("productId"));
    	}
    	if (((Long)(form.get("groupProductId"))).longValue() != new Long(-1).longValue()) {
    		groupProduct = groupProductService.getGroupProductByPK((Long)form.get("groupProductId"));
    	}    	
    	filters.put("productFilter", product != null ? product : null);
    	filters.put("groupProduct", groupProduct != null ? groupProduct : null);       	    	
    	return filters;
    }
	
	//PRE: los productos filtrados y/o grupo de producto estan activos en la base
	@SuppressWarnings("unchecked")
	public List<Product> getListProduct(Map<String,Object> filters) {		
		Criteria c = HibernateHelper.currentSession().createCriteria(Product.class); 
		if (filters.get("productFilter") != null) {
			c.add(Restrictions.eq("id", ((Product)filters.get("productFilter")).getId()));
		} else if (filters.get("groupProduct") != null) {
			c.add(Restrictions.eq("groupProduct.id", ((GroupProduct)filters.get("groupProduct")).getId()));
		}
		return c.list();
	}
}
