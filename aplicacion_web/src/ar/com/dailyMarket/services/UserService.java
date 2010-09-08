package ar.com.dailyMarket.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.DynaActionForm;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import ar.com.dailyMarket.model.GroupUser;
import ar.com.dailyMarket.model.User;

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
}
