/*
*******************************************************************************************
*   Requerimiento: 
*******************************************************************************************
*  Company:         Telefonica Soluciones
*  Author:          Ipoletti
*  Creation Date:   17/06/2005
*  Release:	   1.0.0
*  Description:     
*
*
*  Dependencies:    
*
*****************************************************************************************
*  History:
*
*  Release    Author      Date        Description
*****************************************************************************************
*
* 1.0.0  Ipoletti          17/06/2005       Creacion
*
*/
package ar.com.tsoluciones.telefront.client;

/**
 * Excepción del client telefront
 *
 * @author Ipoletti
 * @version 1.0, 17/06/2005
 * @see
 */
public class TelefrontClientException extends Exception {
    public TelefrontClientException() {
        super();
    }

    public TelefrontClientException(Throwable cause) {
        super(cause);
    }

    public TelefrontClientException(String message) {
        super(message);
    }

    public TelefrontClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
