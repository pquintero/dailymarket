package ar.com.dailyMarket.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.DynaActionForm;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import ar.com.dailyMarket.model.Product;

public class SimulatorService extends MailService{
			
	@SuppressWarnings("unchecked")
	public List<Product> executeFilter(DynaActionForm form) {		
		Long productId = form.get("productId") != null && ((Long)form.get("productId")).longValue() != new Long(-1) ? (Long)form.get("productId") : null;				
		Long groupProductId = form.get("groupProductId") != null && ((Long)form.get("groupProductId")).longValue() != new Long(-1).longValue() ? (Long)form.get("groupProductId") : null;
		
		Criteria c = HibernateHelper.currentSession().createCriteria(Product.class);
		if (productId != null) {
			c.add(Restrictions.eq("id", productId));
		} 
		if (groupProductId != null) {
			c.createCriteria("groupProduct").add(Restrictions.eq("id", groupProductId));
		}
		c.add(Restrictions.eq("active", new Boolean(true)));
		List products = (List)c.list();		
		return products.isEmpty() ? new ArrayList() : products;
	}		
}
