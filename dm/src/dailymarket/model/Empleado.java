package dailymarket.model;

import java.util.HashMap;
import java.util.Map;
import java.util.prefs.AbstractPreferences;
import java.util.prefs.Preferences;


public class Empleado {

	private Long id;
	private String user;
	private String name;
	private String lastName;
	private String password;
	private String passwordOld;
	private String dni;
	private String dateCreated;
	private GroupEmpleado groupEmpleado;
	private String email;
	private boolean receiveNotifications;
	private String huelladigital;
	private byte[] foto;
	
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
	public String getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}
	public GroupEmpleado getGroupEmpleado() {
		return groupEmpleado;
	}
	public void setGroupEmpleado(GroupEmpleado groupEmpleado) {
		this.groupEmpleado = groupEmpleado;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isReceiveNotifications() {
		return receiveNotifications;
	}
	public void setReceiveNotifications(boolean receiveNotifications) {
		this.receiveNotifications = receiveNotifications;
	}
	public String getHuelladigital() {
		return huelladigital;
	}
	public void setHuelladigital(String huelladigital) {
		this.huelladigital = huelladigital;
	}
	public byte[] getFoto() {
		return foto;
	}
	public void setFoto(byte[] foto) {
		this.foto = foto;
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
