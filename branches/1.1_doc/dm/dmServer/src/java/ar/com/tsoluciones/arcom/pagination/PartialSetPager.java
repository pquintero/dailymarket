package ar.com.tsoluciones.arcom.pagination;

import java.util.List;

/**
 * Copyright (c) Telefónica Soluciones
 * Todos los derechos reservados.
 */

/**
 * <p>Representa una pagina del pager de resultados parciales</p>
 *
 * @author despada
 * @version 1.0, Apr 26, 2005, 9:19:44 AM
 */
public class PartialSetPager {
	private List<?> objectList;
	private Paginate paginate;

	/**
	 * Construye un pager de resultados parciales
	 *
	 * @param objectList Lista de la pagina
	 * @param paginate   Datos de la paginación
	 */
	public PartialSetPager(List<?> objectList, Paginate paginate) {
		this.objectList = objectList;
		this.paginate = paginate;
	}

	/**
	 * Obtiene el array de objetos de la pagina
	 *
	 * @return Array de objetos de la pagina
	 */
	public List<?> getObjectList() {
		// Tomar un objeto menos del tamaño de la pagina (luego el sublist excluye el maximo)
		int toIndex = paginate.getPageSize();
		// ...o el total, si es menos del tamaño de pagina
		if (paginate.getPageSize() > objectList.size())
			toIndex = objectList.size();

		// el sublist es inclusive en origen y exclusivo en fin
		return objectList.subList(0, toIndex);
	}

	/**
	 * Obtiene el objeto paginate que se envio para ejecutar la consulta
	 *
	 * @return el objeto paginate que se envio para ejecutar la consulta
	 */
	public Paginate getPaginate() {
		return paginate;
	}

	/**
	 * Si hay proxima pagina o no
	 *
	 * @return Si hay proxima pagina o no
	 */
	public boolean isHasNext() {
		if (objectList.size() > paginate.getPageSize())
			return true;
		return false;
	}

	/**
	 * Si hay pagina previa o no
	 *
	 * @return Si hay pagina previa o no
	 */
	public boolean isHasPrevious() {
		if (paginate.getPage() <= 1)
			return false;
		return true;
	}
}
