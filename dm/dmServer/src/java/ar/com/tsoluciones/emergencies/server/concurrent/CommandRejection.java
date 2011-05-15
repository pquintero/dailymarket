package ar.com.tsoluciones.emergencies.server.concurrent;

import java.util.Date;

/**
 * <p>
 * Representa un commando rechazado por el pool de thrads. 
 * Solo es utilizada como bean para persistir los comandos que no se pudieron
 * ejecutar, dejándolos registrados en la base de datos.
 * </p>
 */
public class CommandRejection {

    private Long id;
    private String message;
    private Date date;
    
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
}