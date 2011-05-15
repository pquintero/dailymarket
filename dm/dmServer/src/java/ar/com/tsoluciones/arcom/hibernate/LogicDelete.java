/*
*******************************************************************************************
*   Requerimiento:
*******************************************************************************************
*  Company:         Telefonica Soluciones
*  Author:          Ipoletti
*  Creation Date:   17/05/2004
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
* 1.0.0  Ipoletti          17/05/2004       Creacion
*
*/
package ar.com.tsoluciones.arcom.hibernate;


/**
 * Interfaz que deben implementar los beans que soporten borrado logico.
 * Es usada por la clase HibernateService en los metodos que retornar solo los objetos
 * que se encuentra no borrados.
 */
public interface LogicDelete {

    /**
     * Metodo que retorna si el objeto se encuentra borrado logicamente o no
     * @return si esta borrado o no
     */
    public boolean isDeleted();
}
