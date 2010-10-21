package ar.com.tsoluciones.emergencies.server.gui.core.telefront.action;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import ar.com.tsoluciones.arcom.cor.ServiceException;
import ar.com.tsoluciones.arcom.security.Product;
import ar.com.tsoluciones.arcom.security.Sucursal;
import ar.com.tsoluciones.arcom.security.services.factory.CajeroVentaFactory;
import ar.com.tsoluciones.arcom.security.services.proxyinterface.CajeroVentaServiceInterface;
import ar.com.tsoluciones.telefront.dispatcher.XmlSerializable;
import ar.com.tsoluciones.telefront.dispatcher.XmlSerializableImpl;
import ar.com.tsoluciones.telefront.servicefactory.TelefrontServiceFactory;

public class CajeroVentaManagerService extends TelefrontServiceFactory {
	/**
	 * Instancia un manager de Cjaero Venta
	 *
	 * @return Manager de Cajero Venta
	 */
	@Override
	public Object newInstance() {
		return new CajeroVentaManagerService();
	}

  /**
   * Obtiene un producto a partir del codigo
   *
   * @param code Codigo del producto
   * @throws ar.com.tsoluciones.arcom.cor.ServiceException
   *          Cuando hay un error de negocios, por ejemplo, si el usuario no se puede logear
   */
  public XmlSerializable obtenerProducto(String code) throws ServiceException {
		CajeroVentaServiceInterface cajeroService = (CajeroVentaServiceInterface) new CajeroVentaFactory().newInstance();
		Product producto = cajeroService.getProductByCode(code);
		return new XmlSerializableImpl(producto.toXml().asXML());
	}
  
  /**
   * Persiste una sesion de venta una vez que esta se cierra
   *
   * @throws ar.com.tsoluciones.arcom.cor.ServiceException
   *          Cuando hay un error de negocios, por ejemplo, si el usuario no se puede logear
   */
  public XmlSerializable guardarSesionVenta(String idCaja, String idCajero, String productos, String totalVenta) throws ServiceException {
		CajeroVentaServiceInterface cajeroService = (CajeroVentaServiceInterface) new CajeroVentaFactory().newInstance();
		
		Long id = cajeroService.guardarSesionVenta(idCaja, idCajero, productos, totalVenta);
		Document document = DocumentHelper.createDocument();
		Element rootElement = document.addElement("sesionVenta");
		rootElement.addElement("sesion").setText(id.toString());		
		return new XmlSerializableImpl(document.asXML());
	}
  
  /**
   * Obtiene una sucursal
   *
   * @param code Codigo del producto
   * @throws ar.com.tsoluciones.arcom.cor.ServiceException
   *          Cuando hay un error de negocios, por ejemplo, si el usuario no se puede logear
   */
  public XmlSerializable obtenerSucursal(String idSucursal) throws ServiceException {
		CajeroVentaServiceInterface cajeroService = (CajeroVentaServiceInterface) new CajeroVentaFactory().newInstance();
		Sucursal sucursal = cajeroService.obtenerSucursal(new Long(idSucursal));
		return new XmlSerializableImpl(sucursal.toXml().asXML());
	}

}
