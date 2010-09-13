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
 * Representa un error de negocios en el servidor
 *
 * @author Ipoletti
 * @version 1.0, 17/06/2005
 * @see
 */
public class TelefrontBusinessException extends TelefrontClientException {
    public TelefrontBusinessException() {
    	//
    }

    public TelefrontBusinessException(Throwable cause) {
        super(cause);
    }

    public TelefrontBusinessException(String message) {
        super(message);
    }

    public TelefrontBusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
