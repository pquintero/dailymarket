package ar.com.tsoluciones.emergencies.server.businesslogic.core.test;

import ar.com.tsoluciones.arcom.security.User;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.util.Enumeration;

/**
 * Implementacion dummy de HttpSession
 */
@SuppressWarnings("deprecation")
public class DummyHttpSession implements HttpSession {
	private User user = null;

	public DummyHttpSession(User user) {
		this.user = user;
	}

	/**
	 * Implementacion dummy
	 * @param string Implementacion dummy
	 * @return Usuario
	 */
	public Object getAttribute(String string) {
		if (string.equalsIgnoreCase("CURRENTUSER"))
			return this.user;

		return null;
	}

	public long getCreationTime() {
		return 0; //To change body of implemented methods use File | Settings | File Templates.
	}

	public String getId() {
		return null; //To change body of implemented methods use File | Settings | File Templates.
	}

	public long getLastAccessedTime() {
		return 0; //To change body of implemented methods use File | Settings | File Templates.
	}

	public ServletContext getServletContext() {
		return null; //To change body of implemented methods use File | Settings | File Templates.
	}

	public void setMaxInactiveInterval(int i) {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	public int getMaxInactiveInterval() {
		return 0; //To change body of implemented methods use File | Settings | File Templates.
	}

	public Enumeration<String> getAttributeNames() {
		return null; //To change body of implemented methods use File | Settings | File Templates.
	}

	public void setAttribute(String string, Object object) {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	public void putValue(String string, Object object) {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	public void removeAttribute(String string) {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	public void removeValue(String string) {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	public void invalidate() {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	public boolean isNew() {
		return false; //To change body of implemented methods use File | Settings | File Templates.
	}

	public HttpSessionContext getSessionContext() {
		return null;
	}

	public Object getValue(String arg0) {
		return null;
	}

	public String[] getValueNames() {
		return null;
	}
}
