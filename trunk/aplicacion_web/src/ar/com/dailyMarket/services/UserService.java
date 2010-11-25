package ar.com.dailyMarket.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;
import org.hibernate.Criteria;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import ar.com.dailyMarket.model.GroupUser;
import ar.com.dailyMarket.model.Image;
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
		user.setGroupUser((GroupUser)HibernateHelper.currentSession().load(GroupUser.class, ((Long)form.get("groupUserId"))));
		user.setReceiveNotifications(new Boolean(false));
		user.setActive(new Boolean(true));
	}
	
	public void save (ActionForm form) {	
		Transaction tx = null;
		try {
			HibernateHelper.closeSession();
			tx = HibernateHelper.currentSession().beginTransaction();
			
			User user = new User();
			copyProperties(user, (DynaActionForm)form);
			HibernateHelper.currentSession().save(user);
			
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
	
	public void update (ActionForm form, User user) {
		Transaction tx = null;
		try {
			HibernateHelper.closeSession();
			tx = HibernateHelper.currentSession().beginTransaction();
			
			copyProperties(user, (DynaActionForm)form);
			HibernateHelper.currentSession().update(user);
			
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
	
	public void delete(String userStr) {
		Transaction tx = null;
		try {
			HibernateHelper.closeSession();
			tx = HibernateHelper.currentSession().beginTransaction();
			
			User user = (User) HibernateHelper.currentSession().createCriteria(User.class).add(Restrictions.eq("user", userStr)).uniqueResult();
			user.setActive(false);
			HibernateHelper.currentSession().saveOrUpdate(user);
			
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
	
	public User getUserByPK (Long id) {
		Transaction tx = null;
		User user = null;
		try {
			HibernateHelper.closeSession();
			tx = HibernateHelper.currentSession().beginTransaction();
			
			user = (User)HibernateHelper.currentSession().load(User.class, id);
			
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
		return user;
	}
	
	public User getUser (String userStr) {
		Transaction tx = null;
		User user = null;
		try {
			HibernateHelper.closeSession();
			tx = HibernateHelper.currentSession().beginTransaction();
			
			Criteria c = HibernateHelper.currentSession().createCriteria(User.class);
			c.add(Restrictions.eq("user", userStr));
			user = (User)c.uniqueResult();
			
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
		return user;
	}
	
	@SuppressWarnings("unchecked")
	public List<User> executeFilter(DynaActionForm form) {
		Transaction tx = null;
		List<User>  users = new ArrayList<User>();
		try {
			HibernateHelper.closeSession();
			tx = HibernateHelper.currentSession().beginTransaction();
			
			String user = !((String)form.get("user")).equals("") ? (String)form.get("user") : null;
			String name = !((String)form.get("name")).equals("") ? (String)form.get("name") : null;
			String lastName = !((String)form.get("lastName")).equals("") ? (String)form.get("lastName") : null;
			String dni = !((String)form.get("dni")).equals("") ? (String)form.get("dni") : null;
			String id = !((String)form.get("idStr")).equals("") ? (String)form.get("idStr") : null;		
			Long groupUser = form.get("groupUserId") != null && ((Long)form.get("groupUserId")).longValue() != new Long(-1).longValue() ? (Long)form.get("groupUserId") : null;
			
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
				c.add(Restrictions.eq("dni", dni));
			}
			if (groupUser != null) {
				c.createCriteria("groupUser").add(Restrictions.eq("id", groupUser));
			}
			c.add(Restrictions.eq("active", new Boolean(true)));
			users = c.list();		
			
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
		
		return users;
	}			
	
	public String getRoleInUser(String userStr) {
		Transaction tx = null;
		User user = null;
		try {
			HibernateHelper.closeSession();
			tx = HibernateHelper.currentSession().beginTransaction();
			
			Criteria c = HibernateHelper.currentSession().createCriteria(User.class);
			c.add(Restrictions.eq("user", userStr));
			user = (User)c.uniqueResult();
			
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
		return user != null ? user.getGroupUser().getName() : "";
	}	
	
	@SuppressWarnings("unchecked")
	public List<User> getUserToNotifications(Boolean value) {
		Transaction tx = null;
		List<User> users = new ArrayList<User>();
		try {
			HibernateHelper.closeSession();
			tx = HibernateHelper.currentSession().beginTransaction();
			
			String[] listRestrictions = new String[3];
			listRestrictions[0] = GroupUser.ROLE_ADMIN;
			listRestrictions[1] = GroupUser.ROLE_MANAGER;
			listRestrictions[2] = GroupUser.ROLE_SUPERVISOR;
			
			Criteria c = HibernateHelper.currentSession().createCriteria(User.class);
			c.add(Restrictions.eq("receiveNotifications", value));
			c.createCriteria("groupUser").add(Restrictions.in("name", listRestrictions));
			users = c.list();
			
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
		return users;
		
	}
	
	public void addNotificationInUser(Long idUser) {
		Transaction tx = null;
		try {
			HibernateHelper.closeSession();
			tx = HibernateHelper.currentSession().beginTransaction();
			
			User user = (User)HibernateHelper.currentSession().load(User.class, idUser);
			user.setReceiveNotifications(new Boolean(true));
			HibernateHelper.currentSession().saveOrUpdate(user);
			
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
	
	public String[] getEmailsToNotifications() {
		List<User> users = getUserToNotifications(true);
		String[] emails = new String[users.size()];
		int i = 0;
		for (Iterator<User> it = users.iterator(); it.hasNext(); i++) {
			emails[i] = it.next().getEmail();
		}
		return emails;
	}
	
	public void deleteNotificationInUser(Long idUser) {
		Transaction tx = null;
		try {
			HibernateHelper.closeSession();
			tx = HibernateHelper.currentSession().beginTransaction();
			
			User user = (User)HibernateHelper.currentSession().load(User.class, idUser);
			user.setReceiveNotifications(new Boolean(false));
			HibernateHelper.currentSession().saveOrUpdate(user);
			
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
	
	@SuppressWarnings("unchecked")
	public List<User> getCajeros() {
		Transaction tx = null;
		List<User> cajeros = new ArrayList<User>();
		try {
			HibernateHelper.closeSession();
			tx = HibernateHelper.currentSession().beginTransaction();
			
			Criteria c = HibernateHelper.currentSession().createCriteria(User.class)
			.createCriteria("groupUser").add(Restrictions.eq("name", GroupUser.ROLE_CAJERO))
			.add(Restrictions.eq("active", new Boolean(true)));
			cajeros = c.list();
			
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
		
		return cajeros;
	}
	
	public void saveImage(User user, Image img, FormFile file) {
		Transaction tx = null;
		try {
			HibernateHelper.closeSession();
			tx = HibernateHelper.currentSession().beginTransaction();
			
			user.setFoto(file.getFileData());
	        user.setImage(img);
	        HibernateHelper.currentSession().saveOrUpdate(user);
			
			tx.commit();
		}
		catch (Exception e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		}
		finally {
			tx = null;
			HibernateHelper.closeSession();
		}
	}
}
