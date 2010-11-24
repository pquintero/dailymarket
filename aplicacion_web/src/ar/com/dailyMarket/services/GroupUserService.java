package ar.com.dailyMarket.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.DynaActionForm;
import org.hibernate.Criteria;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import ar.com.dailyMarket.model.GroupUser;

public class GroupUserService {
	
	public void copyProperties(Object obj, DynaActionForm form) {			
		GroupUser groupUser = (GroupUser)obj;
		groupUser.setName((String)form.get("name"));
		groupUser.setDescription((String)form.get("description"));
		groupUser.setActive(new Boolean(true));
	}
	
	public void save (ActionForm form) {	
		Transaction tx = null;
		try {
			tx = HibernateHelper.currentSession().beginTransaction();
			
			GroupUser groupUser = new GroupUser();
			copyProperties(groupUser, (DynaActionForm)form);
			save(groupUser);
			
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
	
	public void update (ActionForm form, GroupUser groupUser) {
		Transaction tx = null;
		try {
			tx = HibernateHelper.currentSession().beginTransaction();
			
			copyProperties(groupUser, (DynaActionForm)form);
			save(groupUser);
			
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
			
			GroupUser groupUser = getGroupUserByPK(id);
			groupUser.setActive(false);
			save(groupUser);
			
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
	
	public void save (GroupUser groupUser) {
		HibernateHelper.currentSession().saveOrUpdate(groupUser);
	}
	
	public GroupUser getGroupUserByPK (Long id) {
		return (GroupUser)HibernateHelper.currentSession().load(GroupUser.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<GroupUser> executeFilter(DynaActionForm form) {
		String name = !((String)form.get("name")).equals("") ? (String)form.get("name") : null;
		String description = !((String)form.get("description")).equals("") ? (String)form.get("description") : null;
		
		Criteria c = HibernateHelper.currentSession().createCriteria(GroupUser.class);
		if (name != null) {
			c.add(Restrictions.ilike("name", name,MatchMode.ANYWHERE));
		}
		if (description != null) {
			c.add(Restrictions.ilike("description", description,MatchMode.ANYWHERE));
		}		
		c.add(Restrictions.eq("active", new Boolean(true)));
		List<GroupUser> groups = (List<GroupUser>)c.list();		
		return groups.isEmpty() ? new ArrayList<GroupUser>() : groups;
	}
	
	@SuppressWarnings("unchecked")
	public List<GroupUser> getAllGroupsUsers () {
		return (List<GroupUser>)HibernateHelper.currentSession().createCriteria(GroupUser.class)
		.add(Restrictions.eq("active", new Boolean(true))).list();
	}
}
