package ar.com.tsoluciones.arcom.hibernate;

import java.util.List;

import org.hibernate.transform.ResultTransformer;

/**
 * ResultTransformer para convertir los resultados de una query en una lista de
 * {@code ar.com.tsoluciones.arcom.hibernate.ResultRow}
 *
 * @see org.hibernate.transform.ResultTransformer.
 */
public class AliasToResultRowTransformer implements ResultTransformer {

	public Object transformTuple(Object[] tuple, String[] aliases) {
		return new ResultRow(tuple, aliases);
	}

	@SuppressWarnings("unchecked")
	public List transformList(List collection) {
		return collection;
	}

}