package ar.com.tsoluciones.arcom.security;

public class ProductoVenta {

	private Long id;
	private Product producto;
	private SesionVenta sesionVenta;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Product getProducto() {
		return producto;
	}

	public void setProducto(Product producto) {
		this.producto = producto;
	}

	public SesionVenta getSesionVenta() {
		return sesionVenta;
	}

	public void setSesionVenta(SesionVenta sesionVenta) {
		this.sesionVenta = sesionVenta;
	}

}
