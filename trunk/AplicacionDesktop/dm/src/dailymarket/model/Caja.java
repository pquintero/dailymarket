package dailymarket.model;

public class Caja {
	
	private String nroCaja;
	private Sucursal sucursal;
	private Cajero cajero;
	
	public String getNroCaja() {
		return nroCaja;
	}
	public void setNroCaja(String nroCaja) {
		this.nroCaja = nroCaja;
	}
	public Sucursal getSucursal() {
		return sucursal;
	}
	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}
	public Cajero getCajero() {
		return cajero;
	}
	public void setCajero(Cajero cajero) {
		this.cajero = cajero;
	}
	
	
}
