package ar.com.tsoluciones.emergencies.server.businesslogic.core.util;

/**
 * Copyright (c) Telefónica Soluciones
 * Todos los derechos reservados.
 */

/**
 * <p>Clase que puede contener un id y una descripcion</p>
 *
 * @author despada
 * @version 1.0, Dec 3, 2004, 6:50:23 PM
 * @since 1.0
 */
public class ItemHolder {
	private String id;

	private String description;

	/**
	 * Constructor sin parametros para el bean
	 */
	public ItemHolder() {
		//
	}

	/**
	 * Construye un ItemHolder asignandole id a id y description
	 * @param id Valor a asignar
	 */
	public ItemHolder(String id) {
		this.id = id;
		this.description = id;
	}

	/**
	 * Constructor con id y description
	 * @param id Id
	 * @param description Descripcion
	 */
	public ItemHolder(String id, String description) {
		this.id = id;
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
