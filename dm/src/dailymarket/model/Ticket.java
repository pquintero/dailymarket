package dailymarket.model;

import java.util.ArrayList;
import java.util.List;

public class Ticket {

	private String nroTicket;
	private Caja caja;
	private List<LineaTicket> lineas = new ArrayList<LineaTicket>();
	private String total;
	private String subtotal;


	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(String subtotal) {
		this.subtotal = subtotal;
	}

	public Caja getCaja() {
		return caja;
	}

	public void setCaja(Caja caja) {
		this.caja = caja;
	}

	public List<LineaTicket> getLineas() {
		return lineas;
	}

	public void setLineas(List<LineaTicket> lineas) {
		this.lineas = lineas;
	}

	public String getNroTicket() {
		return nroTicket;
	}

	public void setNroTicket(String nroTicket) {
		this.nroTicket = nroTicket;
	}
	
}
