/*
*******************************************************************************************
*   Requerimiento: 
*******************************************************************************************
*  Company:         Telefonica Soluciones
*  Author:          POLETTII
*  Creation Date:   27/04/2006
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
* 1.0.0  POLETTII          27/04/2006       Creacion
*
*/
package ar.com.tsoluciones.emergencies.server.tomcat;

import org.apache.catalina.Manager;
import org.apache.catalina.SessionEvent;
import org.apache.catalina.SessionListener;
import org.apache.catalina.session.StandardSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.File;

/**
 * Extiende la sesión de tomcat para permitir persistirlas convenientemente para la aplicación.
 * Se sobrecarga el metodo setAttribute para persistir la session cuando se setee el parametro CURRENTWORKSTATION
 * Se crea un listener de sesiones para borrar el archivo persistido cuando la misma se destruya.
 *
 * @author POLETTII
 * @version 1.0, 27/04/2006
 * @see
 */
public class PersistentSession extends StandardSession implements SessionListener {

    protected Log log = LogFactory.getLog(PersistentSession.class);

    /**
     * Construct a new Session associated with the specified Manager.
     *
     * @param manager The manager with which this Session is associated
     */
    public PersistentSession(Manager manager) {
        super(manager);
        super.addSessionListener(this);

    }

    /**
     *
     * @param name
     * @param value
     */
    @Override
	public void setAttribute(String name, Object value) {
        try {
            log.debug("Setting attribute " + name);
            super.setAttribute(name, value);

            if (name.equals("CURRENTWORKSTATION")) {
                try {
                    String fileName = getSessionFileName();
                    log.debug("Saving file " + fileName);
                    FileOutputStream fout = new FileOutputStream(new File(PersistentManager.getPersistedSessionsDir(), fileName));
                    ObjectOutputStream objectOut = new ObjectOutputStream(fout);
                    writeObject(objectOut);
                    fout.close();
                } catch (IOException e) {
                	log.error("Se produjo un error persistiendo sesion " + getSessionFileName(),e);
                }
            }
        } catch (Exception e) {
            log.error("Se produjo un error persistiendo sesion " + getSessionFileName(),e);
        }

    }

    /**
     * Evento que borra el archivo de sesion al destruirse la sesion
     * @param event
     */
    public void sessionEvent(SessionEvent event) {
        try {
            if ("destroySession".equals(event.getType())) {
                log.debug("Sesión destruida" + getSessionFileName());
                File file = new File(PersistentManager.getPersistedSessionsDir(), getSessionFileName());
                file.delete();
            }
        } catch (Exception e) {
            log.error("No se pudo borrar la sesión persistida de " + getSessionFileName(),e);
        }
    }

    public void doSessionValid() {
        this.lastAccessedTime = System.currentTimeMillis();
        this.access();
    }

    private String getSessionFileName() {
        Object user = this.getAttribute("CURRENTUSER");
        if (user == null)
            log.error("Se esta intentando persistir una sesion con usuario nulo");
        return  (user!=null?user.toString():"user_null") + '-' +  this.getId();
    }

}
