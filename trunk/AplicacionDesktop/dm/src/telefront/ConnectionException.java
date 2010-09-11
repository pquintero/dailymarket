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
package telefront;

/**
 * Representa un error no esperado de la conexión, contiene un codigo de error.
 *
 * @author Ipoletti
 * @version 1.0, 17/06/2005
 * @see
 */
public class ConnectionException extends TelefrontException {
    private String header;

    public ConnectionException() {
        super();
    }

    public ConnectionException(Throwable cause) {
        super(cause);
    }

    public ConnectionException(String message) {
        super(message);
    }

    public ConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionException(String message, String header, Throwable cause) {
        super(message, cause);
        this.header = header;
    }

    public String getHeader() {
        return header;
    }
}
