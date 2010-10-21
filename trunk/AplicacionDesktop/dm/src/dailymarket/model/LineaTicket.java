package dailymarket.model;

public class LineaTicket {

	private String descripcion;
	private String cantidad;
	private String precioUnitario;
	private String precioTotal;
	
	
	public String getCantidad() {
		return cantidad;
	}

	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}

	public String getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(String precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public String getPrecioTotal() {
		return precioTotal;
	}

	public void setPrecioTotal(String precioTotal) {
		this.precioTotal = precioTotal;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return descripcion + "  " +  cantidad + " " + precioUnitario + " " + precioTotal;
	}
	
}
