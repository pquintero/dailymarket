package ar.com.tsoluciones.arcom.util;

import java.util.Date;

/**
 * Comentarios genericos para cualquier entidad que quiera linkear a ellos. No emplee esta clase con un one-to-many,
 * esta clase debe ser la punta one, no guarde FK en esta clase.
 *
 * @author jrizzo
 */
public class Comment {

	private Long id;

	private String userName;

	private String description;

	private Date creationDate;

	private boolean deleted;

	public Comment() {
		//
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

}
