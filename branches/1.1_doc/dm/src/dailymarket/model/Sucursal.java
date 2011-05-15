package dailymarket.model;

import org.dom4j.Document;
import org.dom4j.Element;

public class Sucursal {

	private Long id;
	private String nombre;
	private String direccion;
	private String telefono;
	private String cuit;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getCuit() {
		return cuit;
	}
	public void setCuit(String cuit) {
		this.cuit = cuit;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public void toSucursalModel(Document doc) {

		Element root = doc.getRootElement();

		Long id = Long.valueOf(root.selectSingleNode("id").getStringValue());
		String direccion = root.selectSingleNode("direccion").getStringValue();
		String telefono = root.selectSingleNode("telefono").getStringValue();
		String nombre = root.selectSingleNode("nombre").getStringValue();
		String cuit = root.selectSingleNode("cuit").getStringValue();
		this.setId(id);
		this.setDireccion(direccion);
		this.setTelefono(telefono);
		this.setNombre(nombre);
		this.setCuit(cuit);
	}
	
}
