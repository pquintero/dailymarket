package ar.com.tsoluciones.arcom.cor;

import java.io.*;

/**
 * Copyright (c) Telefonica Soluciones
 * Todos los derechos reservados
 * <p/>
 * 
 * Interface que permite la extension del framework COR (Chain of Resposability)
 * Cada nuevo comando debe implementar esta Interface
 *
 * @author Javier Rizzo
 * @version 1.1
 * @see
 * @since 1.1
 */
public interface Command extends Serializable {
    
    /**
     * Resultado de la ejecucion del comando
     * @return
     */
    public Object getResult();
    
    /**
     * Determina si el comando debe ejecutarse en una transacccion
     * @return
     */
    public boolean isTransactional();
   
}

