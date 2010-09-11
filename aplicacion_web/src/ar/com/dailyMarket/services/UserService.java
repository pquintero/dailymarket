package ar.com.dailyMarket.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.DynaActionForm;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import ar.com.dailyMarket.model.GroupUser;
import ar.com.dailyMarket.model.User;
import ar.com.dccsoft.skeleton.services.HibernateHelper;

public class UserService {
	
	public void copyProperties(Object obj, DynaActionForm form) {			
		User user = (User)obj;
		user.setName((String)form.get("name"));
		user.setLastName((String)form.get("lastName"));
		user.setPassword((String)form.get("password"));
		user.setUser((String)form.get("user"));
		user.setDni((String)form.get("dni"));
		user.setDateCreated(new Date());
		GroupUserService groupUserService = new GroupUserService();
		user.setGroupUser(groupUserService.getUserByPK((Long)form.get("groupUserId")));
		user.setReceiveNotifications(new Boolean(false));
	}
	
	public void save (ActionForm form) {	
		User user = new User();
		copyProperties(user, (DynaActionForm)form);
		save(user);		
	}	
	
	public void update (ActionForm form, User user) {
		copyProperties(user, (DynaActionForm)form);
		save(user);
	}
	
	public void save (User user) {
		HibernateHelper.currentSession().saveOrUpdate(user);
		HibernateHelper.currentSession().flush();		
	}
	
	public User getUserByPK (Long id) {
		return (User)HibernateHelper.currentSession().load(User.class, id);
	}
	
	public User getUser (String user) {
		Criteria c = HibernateHelper.currentSession().createCriteria(User.class);
		c.add(Restrictions.eq("user", user));
		return (User)c.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<User> executeFilter(DynaActionForm form) {
		String user = !((String)form.get("user")).equals("") ? (String)form.get("user") : null;
		String name = !((String)form.get("name")).equals("") ? (String)form.get("name") : null;
		String lastName = !((String)form.get("lastName")).equals("") ? (String)form.get("lastName") : null;
		String dni = !((String)form.get("dni")).equals("") ? (String)form.get("dni") : null;
		String id = !((String)form.get("idStr")).equals("") ? (String)form.get("idStr") : null;		
		Long groupUser = ((Long)form.get("groupUserId")).longValue() != new Long(-1).longValue() ? (Long)form.get("groupUserId") : null;
		
		Criteria c = HibernateHelper.currentSession().createCriteria(User.class);
		if (user != null) {
			c.add(Restrictions.ilike("user", user,MatchMode.ANYWHERE));
		}
		if (name != null) {
			c.add(Restrictions.ilike("name", name,MatchMode.ANYWHERE));
		}
		if (lastName != null) {
			c.add(Restrictions.ilike("lastName", lastName,MatchMode.ANYWHERE));
		}
		if (id != null) {
			c.add(Restrictions.eq("id", Long.parseLong(id)));
		}
		if (dni != null) {
			c.add(Restrictions.ilike("dni", dni,MatchMode.ANYWHERE));
		}
		if (groupUser != null) {
			c.createCriteria("groupUser").add(Restrictions.eq("id", groupUser));
		}
		List users = (List)c.list();		
		return users.isEmpty() ? new ArrayList() : users;
	}			
	
	public String getRoleInUser(String user) {
		Criteria c = HibernateHelper.currentSession().createCriteria(User.class);
		c.add(Restrictions.eq("user", user));
		return ((User)c.uniqueResult()).getGroupUser().getName();
	}	
	
	@SuppressWarnings("unchecked")
	public List<User> getUserToNotifications(Boolean value) {
		String[] listRestrictions = new String[3];
		listRestrictions[0] = GroupUser.ROLE_ADMIN;
		listRestrictions[1] = GroupUser.ROLE_MANAGER;
		listRestrictions[2] = GroupUser.ROLE_SUPERVISOR;
		
		Criteria c = HibernateHelper.currentSession().createCriteria(User.class);
		c.add(Restrictions.eq("receiveNotifications", value));
		c.createCriteria("groupUser").add(Restrictions.in("name", listRestrictions));
		return c.list();
	}
	
	public void addNotificationInUser(Long idUser) {
		User user = getUserByPK(idUser);
		user.setReceiveNotifications(new Boolean(true));
		save(user);
	}
	
	public String[] getEmailsToNotifications() {
		List<User> users = getUserToNotifications(true);
		String[] emails = new String[users.size()];
		int i = 0;
		for (Iterator<User> it = users.iterator(); it.hasNext(); i++) {
			emails[i] = it.next().getEmail();
		}
		return emails;
	}
}
