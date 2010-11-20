package ar.com.tsoluciones.arcom.security;

import java.util.Date;

public class LoginHistory {

	private Long id;
	private User cajero;
	private Date fechaApertura;
	private Double montoApertura;
	private Date fechaCierre;
	private Double montoCierre;
	private Long idCaja;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getCajero() {
		return cajero;
	}

	public void setCajero(User cajero) {
		this.cajero = cajero;
	}

	public Date getFechaApertura() {
		return fechaApertura;
	}

	public void setFechaApertura(Date fechaApertura) {
		this.fechaApertura = fechaApertura;
	}

	public Double getMontoApertura() {
		return montoApertura;
	}

	public void setMontoApertura(Double montoApertura) {
		this.montoApertura = montoApertura;
	}

	public Date getFechaCierre() {
		return fechaCierre;
	}

	public void setFechaCierre(Date fechaCierre) {
		this.fechaCierre = fechaCierre;
	}

	public Double getMontoCierre() {
		return montoCierre;
	}

	public void setMontoCierre(Double montoCierre) {
		this.montoCierre = montoCierre;
	}

	public Long getIdCaja() {
		return idCaja;
	}

	public void setIdCaja(Long idCaja) {
		this.idCaja = idCaja;
	}

}
