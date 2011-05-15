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
 * Representa un error no esperado de la conexión, contiene un codigo de error.
 *
 * @author Ipoletti
 * @version 1.0, 17/06/2005
 * @see
 */
public class TelefrontConnectionException extends TelefrontClientException
{
  private int errorCode;

  public TelefrontConnectionException()
  {
    super();
  }

  public TelefrontConnectionException(Throwable cause)
  {
    super(cause);
  }

  public TelefrontConnectionException(String message, int errorCode)
  {
    super(message);
    this.errorCode = errorCode;
  }

  public TelefrontConnectionException(String message, Throwable cause)
  {
    super(message, cause);
  }

  public int getErrorCode()
  {
    return errorCode;
  }

  public void setErrorCode(int errorCode)
  {
    this.errorCode = errorCode;
  }

}
