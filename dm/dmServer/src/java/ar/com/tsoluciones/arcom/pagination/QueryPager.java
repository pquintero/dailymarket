package ar.com.tsoluciones.arcom.pagination;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;

/**
 * Copyright (c) Telefónica Soluciones
 * Todos los derechos reservados.
 */

/**
 * <p>Realiza la paginacion de una query</p>
 *
 * @author despada
 * @version 1.0, Apr 26, 2005, 10:06:37 AM
 */
public class QueryPager
{
	Paginate paginate;

	/**
	 * Construye un pager de queries
	 * @param paginate Objeto paginate con los datos de la paginacion
	 */
	public QueryPager(Paginate paginate)
	{
		this.paginate = paginate;
	}

	/**
	 * Pagina un objeto Criteria y lo ejecuta
	 * @param criteria Objeto criteria
	 * @return Resultado de la ejecucion
	 */
	public PartialSetPager paginate(Criteria criteria)
	{
		try
		{
			if (paginate.isPaginate())
				criteria = criteria.setFirstResult((paginate.getPage() - 1) * paginate.getPageSize()).setMaxResults(paginate.getPageSize() + 1);

			return new PartialSetPager(criteria.list(), paginate);
		}
		catch(HibernateException e)
		{
			throw new RuntimeException("Error al ejecutar Criteria", e);
		}
	}
	
	public PartialSetPager paginate(Query q) {
		try
		{
			if (paginate.isPaginate())
				q.setFirstResult((paginate.getPage() - 1) * paginate.getPageSize()).setMaxResults(paginate.getPageSize() + 1);
			return new PartialSetPager(q.list(), paginate);
		}
		catch(HibernateException e)
		{
			throw new RuntimeException("Error al ejecutar la consulta", e);
		}		
	}
}
