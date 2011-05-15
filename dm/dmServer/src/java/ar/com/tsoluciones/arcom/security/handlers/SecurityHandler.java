
/*
 * SecurityHandler.java
 *
 * Created on 28 de enero de 2004, 15:06
 */

package ar.com.tsoluciones.arcom.security.handlers;

/**
 *
 * @author  Aherrera
 */
public interface SecurityHandler {
    boolean authenticate (String id, String password);
    int setPassword (String id, String password);
    int delete (String id);
}
