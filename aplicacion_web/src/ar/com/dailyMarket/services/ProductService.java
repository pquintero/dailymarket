package ar.com.dailyMarket.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.DynaActionForm;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import ar.com.dailyMarket.model.Product;

public class ProductService {
	
	public void copyProperties(Object obj, DynaActionForm form) {			
		Product product = (Product)obj;
		product.setName((String)form.get("name"));
		product.setDescription((String)form.get("description"));
		product.setActualStock((Integer)form.get("actualStock"));
		product.setPrice((Double)form.get("price"));
		product.setSizeOfPurchase((Integer)form.get("sizeOfPurchase"));
		GroupProductService groupProductService = new GroupProductService();
		product.setGroupProduct(groupProductService.getProductByPK((Long)form.get("groupProductId")));
	}
	
	public void save (ActionForm form) {	
		Product product = new Product();
		copyProperties(product, (DynaActionForm)form);
		save(product);
		//aca armo el codigo del producto, ver de armar algun patron para q por ejemplo diga 0000000id
		product.setCode(product.getId().toString());
		save(product);
	}	
	
	public void update (ActionForm form, Product product) {
		copyProperties(product, (DynaActionForm)form);
		save(product);
	}
	
	public void save (Product product) {
		HibernateHelper.currentSession().saveOrUpdate(product);
		HibernateHelper.currentSession().flush();		
	}
	
	public Product getProductByPK (Long id) {
		return (Product)HibernateHelper.currentSession().load(Product.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<Product> executeFilter(DynaActionForm form) {
		String code = !((String)form.get("code")).equals("") ? (String)form.get("code") : null;
		String name = !((String)form.get("name")).equals("") ? (String)form.get("name") : null;
		String description = !((String)form.get("description")).equals("") ? (String)form.get("description") : null;			
		Long groupProduct = ((Long)form.get("groupProductId")).longValue() != new Long(-1).longValue() ? (Long)form.get("groupProductId") : null;
		
		Criteria c = HibernateHelper.currentSession().createCriteria(Product.class);
		if (code != null) {
			c.add(Restrictions.eq("code", code));
		}
		if (name != null) {
			c.add(Restrictions.ilike("name", name,MatchMode.ANYWHERE));
		}
		if (description != null) {
			c.add(Restrictions.ilike("description", description,MatchMode.ANYWHERE));
		}
		if (groupProduct != null) {
			c.createCriteria("groupProduct").add(Restrictions.eq("id", groupProduct));
		}
		List products = (List)c.list();		
		return products.isEmpty() ? new ArrayList() : products;
	}
}
