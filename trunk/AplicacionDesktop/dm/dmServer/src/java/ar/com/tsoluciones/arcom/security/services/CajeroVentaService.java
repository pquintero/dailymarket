package ar.com.tsoluciones.arcom.security.services;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.type.Type;

import ar.com.tsoluciones.arcom.hibernate.HibernateService;
import ar.com.tsoluciones.arcom.security.Product;
import ar.com.tsoluciones.arcom.security.services.proxyinterface.CajeroVentaServiceInterface;
import ar.com.tsoluciones.util.Cast;

public class CajeroVentaService implements CajeroVentaServiceInterface {

	public Product getProductByCode(String code) {
		List<Product> l = Cast.castList(Product.class, HibernateService.findByFilter(Product.class, new String[] { "code"}, new Object[] { code}, new Type[] {
			Hibernate.STRING}));

		if (l == null || l.size() == 0)
		return null;

		Product listedProduct = l.get(0);
		return listedProduct;
	}

}
