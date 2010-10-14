package ar.com.tsoluciones.arcom.security.services.proxyinterface;

import java.util.Collection;

import ar.com.tsoluciones.arcom.security.Product;

public interface CajeroVentaServiceInterface {
	
	  /**
	   * Obtiene un Product
	   *
	   * @param code code del Product
	   * @return objeto Product
	   */
	  public Product getProductByCode(String code);
	  
	  /**
	   * Guardar Sesion de venta
	   *
	   * @return void
	   */
	  public void guardarSesionVenta(String idCaja, String idCajero, Collection<String> productos, String totalVenta);

}
