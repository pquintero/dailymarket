package ar.com.dailyMarket.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.DynaActionForm;
import org.hibernate.Criteria;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import ar.com.dailyMarket.model.GroupProduct;

public class GroupProductService {
	
	public void copyProperties(Object obj, DynaActionForm form) {			
		GroupProduct groupProduct = (GroupProduct)obj;
		groupProduct.setName((String)form.get("name"));
		groupProduct.setDescription((String)form.get("description"));
		groupProduct.setActive(new Boolean(true));
	}
	
	public void save (ActionForm form) {
		Transaction tx = null;
		try {
			tx = HibernateHelper.currentSession().beginTransaction();
		
			GroupProduct groupProduct = new GroupProduct();
			copyProperties(groupProduct, (DynaActionForm)form);
			save(groupProduct);
			
			tx.commit();
		}
		catch (RuntimeException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		}
		finally {
			tx = null;
		}
	}	
	
	public void update (ActionForm form, GroupProduct groupProduct) {
		Transaction tx = null;
		try {
			tx = HibernateHelper.currentSession().beginTransaction();
			
			copyProperties(groupProduct, (DynaActionForm)form);
			save(groupProduct);
			
			tx.commit();
		}
		catch (RuntimeException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		}
		finally {
			tx = null;
		}
	}
	
	public void delete (Long id) {
		Transaction tx = null;
		try {
			tx = HibernateHelper.currentSession().beginTransaction();
		
			GroupProduct groupProduct = getGroupProductByPK(id);
			groupProduct.setActive(false);
			save(groupProduct);
			
			tx.commit();
		}
		catch (RuntimeException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		}
		finally {
			tx = null;
		}
	}
	
	public void save (GroupProduct groupProduct) {
		HibernateHelper.currentSession().saveOrUpdate(groupProduct);
	}
	
	public GroupProduct getGroupProductByPK (Long id) {
		return (GroupProduct)HibernateHelper.currentSession().load(GroupProduct.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<GroupProduct> executeFilter(DynaActionForm form) {
		String name = !((String)form.get("name")).equals("") ? (String)form.get("name") : null;
		String description = !((String)form.get("description")).equals("") ? (String)form.get("description") : null;		
		
		Criteria c = HibernateHelper.currentSession().createCriteria(GroupProduct.class);
		if (name != null) {
			c.add(Restrictions.ilike("name", name,MatchMode.ANYWHERE));
		}
		if (description != null) {
			c.add(Restrictions.ilike("description", description,MatchMode.ANYWHERE));
		}		
		c.add(Restrictions.eq("active", new Boolean(true)));
		List<GroupProduct> groups = (List<GroupProduct>)c.list();		
		return groups.isEmpty() ? new ArrayList<GroupProduct>() : groups;
	}
	
	@SuppressWarnings("unchecked")
	public List<GroupProduct> getAllGroupProduct() {
		return (List<GroupProduct>)HibernateHelper.currentSession().createCriteria(GroupProduct.class)
		.add(Restrictions.eq("active", new Boolean(true))).list();
	}
}
