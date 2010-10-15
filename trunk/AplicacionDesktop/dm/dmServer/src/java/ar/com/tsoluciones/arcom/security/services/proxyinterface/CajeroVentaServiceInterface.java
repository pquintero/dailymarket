package ar.com.tsoluciones.arcom.security.services.proxyinterface;

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
	  public void guardarSesionVenta(String idCaja, String idCajero, String productos, String totalVenta);

}
