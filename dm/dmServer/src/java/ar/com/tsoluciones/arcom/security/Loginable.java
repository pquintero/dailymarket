package ar.com.tsoluciones.arcom.security;

/**
 * Copyright (c) Telefonica Soluciones
 * Todos los derechos reservados
 */

/*
 * Aquellas clases de dominio que puedan logearse al sistema (como usuarios, por ejemplo)
 * deben implementar esta interfaz. Solo utilice estos métodos desde la capa de negocios, ya
 * que emplean recursos pertenecientes a la EIL.
 *
 * @author Andres Herrera, despada
 * @version 1.0 - 17/05/2004 13:36:06
 * @see
 * @since 0.2
 */
public interface Loginable {

    /**
     * <p>
		 * Verifica la autenticidad de un string
		 * </p>
		 *
     * @param password
     * @return
     */
    public boolean authenticate (String password);

    /**
     * <p>
		 * Termina el login
		 * </p>
		 *
     * @return boolean, el status de locked, deberia ser false. Locked fue deprecado.
     */
    public boolean logout ();

    /**
     * <p> cambia a la password </p>
     * @return int
     * @param oldPassword Viejo password
     * @param newPassword Nuevo password
     */
    public int changePassword (String oldPassword, String newPassword);

    /**
     * <p> Reinicia la password </p>
     * @return devuelve la nueva password
     */
    public String resetPassword();


}
