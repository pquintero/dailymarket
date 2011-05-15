package ar.com.tsoluciones.arcom.pagination;




/**
 * Copyright (c) Telefónica Soluciones
 * Todos los derechos reservados.
 */

/**
 * <p>Permite paginar una consulta al DBMS</p>
 *
 * @author despada
 * @version 1.0, Apr 26, 2005, 9:28:37 AM
 */
public class Paginate
{
	private boolean paginate = false;
	private int page = 1;
	private int pageSize = 30;

	/**
	 * Construye el paginador como un mock, no paginará los resultados
	 */
	public Paginate()
	{
		paginate = false;
		page = 1;
		pageSize = Integer.MAX_VALUE;
	}

	/**
	 * Construye un paginador que servirá para paginar los resultados
	 * @param page Numero de la pagina pedida, comienza desde 1
	 * @param pageSize Tamaño de la página
	 */
	public Paginate(int page, int pageSize)
	{
    paginate = true;
		this.page = page;
		this.pageSize = pageSize;
	}

	public boolean isPaginate()
	{
		return paginate;
	}

	public int getPage()
	{
		return page;
	}

	public int getPageSize()
	{
		return pageSize;
	}
}