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

}
