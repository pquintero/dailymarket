package ar.com.dailyMarket.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;

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
	private Boolean active;
	private Image image;
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
	public String getCompleteName() {
		return getLastName() + ", " + getName();
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
	public byte[] getFoto() {
		return foto;
	}
	
	/** Don't invoke this.  Used by Hibernate only. */
	public Blob getFotoBlob() {
		if(foto != null)
	 return Hibernate.createBlob(this.foto);
		else
			return null;
	}
	
	public void setFoto(byte[] foto) {
		this.foto = foto;
	}
	
	public void setFotoBlob(Blob fotoBlob) {
		if(fotoBlob!= null)
		 this.foto = this.toByteArray(fotoBlob);
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
}
