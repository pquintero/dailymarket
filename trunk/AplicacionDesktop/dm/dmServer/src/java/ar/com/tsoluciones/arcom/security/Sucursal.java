package ar.com.tsoluciones.arcom.security;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class Sucursal {

	private Long id;
	private String nombre;
	private String direccion;
	private String telefono;
	private String cuit;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	/**
	 * <p>
	 * Retorna una representación mínima del producto.
	 * </p>
	 * 
	 * @return Document
	 */
	public Document toXml() {

		Document doc = DocumentHelper.createDocument();
		doc.setRootElement(DocumentHelper.createElement("sucursal"));

		Element root = doc.getRootElement();
		root.addElement("id").setText(id.toString());
		root.addElement("nombre").setText(nombre != null ? nombre : "");
		root.addElement("direccion")
				.setText(direccion != null ? direccion : "");
		root.addElement("telefono").setText(telefono != null ? telefono : "");
		root.addElement("cuit").setText(cuit != null ? cuit : "");

		return doc;
	}

}
