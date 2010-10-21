package ar.com.tsoluciones.arcom.security.services.proxyinterface;

import ar.com.tsoluciones.arcom.security.Product;
import ar.com.tsoluciones.arcom.security.SesionVenta;
import ar.com.tsoluciones.arcom.security.Sucursal;

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
	  public Long guardarSesionVenta(String idCaja, String idCajero, String productos, String totalVenta);
	  
	  public SesionVenta obtenerSesionVenta(Long id) ;
	public Sucursal obtenerSucursal(Long id);

}
