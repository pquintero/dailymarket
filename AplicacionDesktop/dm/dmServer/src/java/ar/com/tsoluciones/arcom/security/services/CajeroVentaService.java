package ar.com.tsoluciones.arcom.security.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.LockMode;
import org.hibernate.type.Type;

import ar.com.tsoluciones.arcom.hibernate.HibernateService;
import ar.com.tsoluciones.arcom.hibernate.Transactional;
import ar.com.tsoluciones.arcom.security.Product;
import ar.com.tsoluciones.arcom.security.ProductoVenta;
import ar.com.tsoluciones.arcom.security.SesionVenta;
import ar.com.tsoluciones.arcom.security.Sucursal;
import ar.com.tsoluciones.arcom.security.User;
import ar.com.tsoluciones.arcom.security.services.factory.UserServiceFactory;
import ar.com.tsoluciones.arcom.security.services.proxyinterface.CajeroVentaServiceInterface;
import ar.com.tsoluciones.arcom.security.services.proxyinterface.UserServiceInterface;
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

	@Transactional
	public Long guardarSesionVenta(String idCaja, String cajero,
			String productos, String totalVenta) {
		
		UserServiceInterface userInterface = (UserServiceInterface) new UserServiceFactory().newInstance();
		User user = userInterface.getUserByUserName(cajero);
		String [] codigos = productos.split("-");
		
		SesionVenta sesionVenta = new SesionVenta();
		sesionVenta.setCajero(user);
		sesionVenta.setTotalVenta(Double.valueOf(totalVenta));
		Set<ProductoVenta> productosVenta = new HashSet<ProductoVenta>();
		sesionVenta.setIdCaja(Long.valueOf(idCaja));
		
//		sesionVenta.setProductos(productosVenta);
		
		HibernateService.updateObject(sesionVenta);
		
		for (int i = 0; i < codigos.length; i++) {
			String codeProd = (String) codigos[i];
			Product producto = getProductByCode(codeProd);
			ProductoVenta productoVenta = new ProductoVenta();
			productoVenta.setProducto(producto);
			productoVenta.setSesionVenta(sesionVenta);
			productosVenta.add(productoVenta);
			HibernateService.updateObject(productoVenta);
		}
		
		return sesionVenta.getId();
		
	}
	
	@Transactional
	public void actualizarProducto(Product product){
		HibernateService.updateObject(product);
	}
	
	
	public SesionVenta obtenerSesionVenta(Long id) {
		

		SesionVenta sesionVenta = HibernateService.getObject(SesionVenta.class, id, LockMode.NONE);
		return sesionVenta;
		
	}
	
	public Sucursal obtenerSucursal(Long id) {

		Sucursal sucursal = HibernateService.getObject(Sucursal.class, id,
				LockMode.NONE);
		return sucursal;

	}

}
