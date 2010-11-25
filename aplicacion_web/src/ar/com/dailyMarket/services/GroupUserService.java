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
			HibernateHelper.closeSession();
			tx = HibernateHelper.currentSession().beginTransaction();
			
			GroupUser groupUser = new GroupUser();
			copyProperties(groupUser, (DynaActionForm)form);
			HibernateHelper.currentSession().save(groupUser);
			
			tx.commit();
		}
		catch (RuntimeException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		}
		finally {
			tx = null;
			HibernateHelper.closeSession();
		}
	}	
	
	public void update (ActionForm form, GroupUser groupUser) {
		Transaction tx = null;
		try {
			HibernateHelper.closeSession();
			tx = HibernateHelper.currentSession().beginTransaction();
			
			copyProperties(groupUser, (DynaActionForm)form);
			HibernateHelper.currentSession().update(groupUser);
			
			tx.commit();
		}
		catch (RuntimeException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		}
		finally {
			tx = null;
			HibernateHelper.closeSession();
		}
	}
	
	public void delete (Long id) {
		Transaction tx = null;
		try {
			HibernateHelper.closeSession();
			tx = HibernateHelper.currentSession().beginTransaction();
			
			GroupUser groupUser = (GroupUser)HibernateHelper.currentSession().load(GroupUser.class, id);
			groupUser.setActive(false);
			HibernateHelper.currentSession().update(groupUser);
			
			tx.commit();
		}
		catch (RuntimeException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		}
		finally {
			tx = null;
			HibernateHelper.closeSession();
		}
	}
	
	public GroupUser getGroupUserByPK (Long id) {
		Transaction tx = null;
		GroupUser groups = null;
		try {
			HibernateHelper.closeSession();
			tx = HibernateHelper.currentSession().beginTransaction();
			
			groups = (GroupUser)HibernateHelper.currentSession().load(GroupUser.class, id);
			
			tx.commit();
		}
		catch (RuntimeException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		}
		finally {
			tx = null;
			HibernateHelper.closeSession();
		}
		return groups; 
	}
	
	@SuppressWarnings("unchecked")
	public List<GroupUser> executeFilter(DynaActionForm form) {
		Transaction tx = null;
		List<GroupUser> groups = new ArrayList<GroupUser>();
		try {
			HibernateHelper.closeSession();
			tx = HibernateHelper.currentSession().beginTransaction();
			
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
			groups = (List<GroupUser>)c.list();
			
			tx.commit();
		}
		catch (RuntimeException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		}
		finally {
			tx = null;
			HibernateHelper.closeSession();
		}
		
		return groups;
	}
	
	@SuppressWarnings("unchecked")
	public List<GroupUser> getAllGroupsUsers () {
		Transaction tx = null;
		List<GroupUser> groups = new ArrayList<GroupUser>();
		try {
			HibernateHelper.closeSession();
			tx = HibernateHelper.currentSession().beginTransaction();
			
			Criteria c = HibernateHelper.currentSession().createCriteria(GroupUser.class)
						.add(Restrictions.eq("active", new Boolean(true)));
			groups = c.list();
			
			tx.commit();
		}
		catch (RuntimeException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		}
		finally {
			tx = null;
			HibernateHelper.closeSession();
		}
		return groups;
	}
}
