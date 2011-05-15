/*
*******************************************************************************************
*   Requerimiento: 
*******************************************************************************************
*  Company:         Telefonica Soluciones
*  Author:          POLETTII
*  Creation Date:   09/04/2006
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
* 1.0.0  POLETTII          09/04/2006       Creacion
*
*/
package ar.com.tsoluciones.telefront.dispatcher;

/**
 * <p>  </p>
 * <p/>
 * <p> Ejemplo:
 * <pre>
 *  </pre>
 * </p>
 *
 * @author POLETTII
 * @version 1.0, 09/04/2006
 * @see
 */
public class ByteArraySerializable implements XmlSerializable {

    private byte bytes[];

    public ByteArraySerializable(byte[] bytes) {
        this.bytes = bytes;
    }

    /**
     * @return
     */
    public byte[]  getBytes() {
        return this.bytes;
    }
}
