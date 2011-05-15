package ar.com.tsoluciones.arcom.cor;


/**
 * Copyright (c) Telefonica Soluciones
 * Todos los derechos reservados
 * <p/>
 * 
 * Interface que permite la extension del framework COR (Chain of Resposability)
 * Cada nuevo servicio asociado a un comando debe implementar esta Interface
 *
 * @author Javier Rizzo
 * @version 1.1
 * @see
 * @since 1.1
 */
public interface Service {

    /**
     * Ejecuta el servicio asociado al comando
     * @param command contiene el comando a ejecutar
     * @return el mismo comando o una nueva instancia puede o no tener asociado un resultado
     * @throws ServiceException en caso de que no haya podido ejecutarse el comando correctamente
     */
    public Command process(Command command) throws  ServiceException;

}
