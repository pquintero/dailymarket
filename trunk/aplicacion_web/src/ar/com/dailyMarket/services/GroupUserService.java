package ar.com.dailyMarket.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.DynaActionForm;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import ar.com.dailyMarket.model.GroupUser;

public class GroupUserService {
	
	public void copyProperties(Object obj, DynaActionForm form) {			
		GroupUser groupUser = (GroupUser)obj;
		groupUser.setName((String)form.get("name"));
		groupUser.setDescription((String)form.get("description"));
	}
	
	public void save (ActionForm form) {	
		GroupUser groupUser = new GroupUser();
		copyProperties(groupUser, (DynaActionForm)form);
		save(groupUser);		
	}	
	
	public void update (ActionForm form, GroupUser groupUser) {
		copyProperties(groupUser, (DynaActionForm)form);
		save(groupUser);
	}
	
	public void save (GroupUser groupUser) {
		HibernateHelper.currentSession().saveOrUpdate(groupUser);
		HibernateHelper.currentSession().flush();		
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
		List groups = (List)c.list();		
		return groups.isEmpty() ? new ArrayList() : groups;
	}
	
	@SuppressWarnings("unchecked")
	public List<GroupUser> getAllGroupsUsers () {
		return (List)HibernateHelper.currentSession().createCriteria(GroupUser.class).list();
	}
	
	public void delete (Long id) {
		GroupUser groupUser = getGroupUserByPK(id);
		HibernateHelper.currentSession().delete(groupUser);
		HibernateHelper.currentSession().flush();
	}
}
