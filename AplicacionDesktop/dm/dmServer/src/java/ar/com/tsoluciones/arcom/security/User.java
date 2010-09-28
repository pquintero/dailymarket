package ar.com.tsoluciones.arcom.security;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.prefs.AbstractPreferences;
import java.util.prefs.Preferences;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.hibernate.Hibernate;


public class User {
	
	private Long id;
	private String user;
	private String name;
	private String lastName;
	private String password;
	private String passwordOld;
	private String dni;
	private Date dateCreated;
	private GroupUser groupUser;
	private String email;
	private boolean receiveNotifications;
	private byte[] huelladigital;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public GroupUser getGroupUser() {
		return groupUser;
	}
	public void setGroupUser(GroupUser groupUser) {
		this.groupUser = groupUser;
	}
	public String getPasswordOld() {
		return passwordOld;
	}
	public void setPasswordOld(String passwordOld) {
		this.passwordOld = passwordOld;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDescUser() {
		return getLastName() + ", " + getName() + " - " + getEmail();
	}
	public boolean isReceiveNotifications() {
		return receiveNotifications;
	}
	public void setReceiveNotifications(boolean receiveNotifications) {
		this.receiveNotifications = receiveNotifications;
	}	
	

	/**
	 * <p>
	 * Retorna una representación mínima del usuario.
	 * </p>
	 * @return Document
	 */
	public Document toXml() {

		Document doc = DocumentHelper.createDocument();
		doc.setRootElement(DocumentHelper.createElement("user"));

		Element root = doc.getRootElement();
		root.addElement("id").setText(id.toString());
		root.addElement("user").setText(String.valueOf(user));
		root.addElement("name").setText(String.valueOf(name));
		root.addElement("lastName").setText(String.valueOf(lastName));
		root.addElement("password").setText(String.valueOf(password));
		root.addElement("passwordOld").setText(String.valueOf(passwordOld));
		root.addElement("dni").setText(String.valueOf(dni));
		root.addElement("dateCreated").setText(String.valueOf(dateCreated));
		root.addElement("huelladigital").setText( MyBase64.encode(huelladigital));
		
		//Serializar de otra forma
//		root.addElement("groupUser").setText(String.valueOf(groupUser));
		
		root.addElement("email").setText(String.valueOf(email));
		root.addElement("receiveNotifications").setText(String.valueOf(receiveNotifications));

	    return doc;
	}
	
	   /**
	 * <p>
	 * Valida el usuario con su huella digital
	 * </p>
	 * @param huellaDigital - La huellaDigital a autentificar
	 * @return boolean true = La password es valida false= La password no es valida
	 */
    public boolean authenticate(String huellaDigital) {

    	boolean auth = true;
 
        return auth;
    }

    /**
     * Validates user password against the corresponding SecurityHandler
     * implementation
     */
    public boolean logout() {
        return false;
    }
	public void setHuelladigital(byte[] huelladigital) {
		this.huelladigital = huelladigital;
	}
	public byte[] getHuelladigital() {
		return huelladigital;
	}
	
	public void setHuellaBlob(Blob huellaBlob) {
		  this.huelladigital = this.toByteArray(huellaBlob);
		 }

	 private byte[] toByteArray(Blob fromBlob) {
		  ByteArrayOutputStream baos = new ByteArrayOutputStream();
		  try {
		   return toByteArrayImpl(fromBlob, baos);
		  } catch (SQLException e) {
		   throw new RuntimeException(e);
		  } catch (IOException e) {
		   throw new RuntimeException(e);
		  } finally {
		   if (baos != null) {
		    try {
		     baos.close();
		    } catch (IOException ex) {
		    }
		   }
		  }
		 }

	 private byte[] toByteArrayImpl(Blob fromBlob, ByteArrayOutputStream baos)
	  throws SQLException, IOException {
	  byte[] buf = new byte[4000];
	  InputStream is = fromBlob.getBinaryStream();
	  try {
	   for (;;) {
	    int dataSize = is.read(buf);

	    if (dataSize == -1)
	     break;
	    baos.write(buf, 0, dataSize);
	   }
	  } finally {
	   if (is != null) {
	    try {
	     is.close();
	    } catch (IOException ex) {
	    }
	   }
	  }
	  return baos.toByteArray();
	 }
	 /** Don't invoke this.  Used by Hibernate only. */
	 public Blob getHuellaBlob() {
	  return Hibernate.createBlob(this.huelladigital);
	 }
	 

	 public static class MyBase64 {
	     private static class MyPreferences extends AbstractPreferences {
	         private Map<String,String> map = new HashMap<String,String>();
	         MyPreferences() { super(null,""); }
	         protected void putSpi(String key,String value) { map.put(key,value); }
	         protected String getSpi(String key) { return map.get(key); }
	         protected void removeSpi(String key) { map.remove(key); }
	         protected void removeNodeSpi() { }
	         protected String[] keysSpi() { return null; }
	         protected String[] childrenNamesSpi() { return null; }
	         protected AbstractPreferences childSpi(String key) { return null; }
	         protected void syncSpi() {}
	         protected void flushSpi() {}
	     }
	     static String encode(byte[] ba) {
	         Preferences p = new MyPreferences();
	         p.putByteArray("",ba);
	         return p.get("",null);
	     }
	     static byte[] decode(String s) {
	         Preferences p = new MyPreferences();
	         p.put("",s);
	         return p.getByteArray("",null);
	     }
	     public static void main(String[] arg) {
	         byte[] ba = arg[0].getBytes();
	         String s = MyBase64.encode(ba);
	         System.out.println(s);
	         ba = MyBase64.decode(s);
	         s = new String(ba);
	         System.out.println(s);
	     }
	 }

	
}
