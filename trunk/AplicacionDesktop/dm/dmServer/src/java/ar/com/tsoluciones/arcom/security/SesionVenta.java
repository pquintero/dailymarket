package ar.com.tsoluciones.arcom.security;

import java.util.Date;
import java.util.List;

public class SesionVenta {

	private Long id;
	private User cajero;
	private Double totalVenta;
	private List<ProductoVenta> productos;
	private Long idCaja;
	private Date fechaInicio = new Date();

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

	public Double getTotalVenta() {
		return totalVenta;
	}

	public void setTotalVenta(Double totalVenta) {
		this.totalVenta = totalVenta;
	}

	public List<ProductoVenta> getProductos() {
		return productos;
	}

	public void setProductos(List<ProductoVenta> productos) {
		this.productos = productos;
	}

	public Long getIdCaja() {
		return idCaja;
	}

	public void setIdCaja(Long idCaja) {
		this.idCaja = idCaja;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

}
