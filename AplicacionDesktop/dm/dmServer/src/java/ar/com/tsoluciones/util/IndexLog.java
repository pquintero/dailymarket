package ar.com.tsoluciones.util;

import java.util.Date;

/**
 * <p>
 * Representa un log de una entrada indexada que se modificó. Se utiliza para resincronizar los índices ante un failover.
 * Por ahora se utiliza solamente para las calles. Quizás esta política no sirva si se necesita luego indexar las cartas.
 * </p> 
 */
public class IndexLog {
	private Long id;
	private Date indexDate;
	private Long referenceId;
	private String hostname;
	private Boolean updated;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getIndexDate() {
		return indexDate;
	}
	public void setIndexDate(Date indexDate) {
		this.indexDate = indexDate;
	}
	public Long getReferenceId() {
		return referenceId;
	}
	public void setReferenceId(Long referenceId) {
		this.referenceId = referenceId;
	}
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public Boolean getUpdated() {
		return updated;
	}
	public void setUpdated(Boolean updated) {
		this.updated = updated;
	}
	
}
