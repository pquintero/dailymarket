package ar.com.tsoluciones.emergencies.server.businesslogic.core.service.proxyinterface;


public interface AperturaCajaServiceInterface
{

    /**
     * Realiza la apertura del Usuario a la aplicación
     *
     * @param username
     * @param montoApertura
     * @param fecha
     * @param huellaDigital
     * @return
     */
    public boolean abrirCaja(String username, String montoApertura, String fecha, String huellaDigital);
    
    
	public boolean altaHuellaDigital(String username, String password, String huella );


}
