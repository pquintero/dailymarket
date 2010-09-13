/*
 *******************************************************************************************
 *   Requerimiento:
 *******************************************************************************************
 *  Company:         Telefonica Soluciones
 *  Author:          Ipoletti
 *  Creation Date:   29/11/2004
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
 * 1.0.0  Ipoletti          29/11/2004       Creacion
 *
 */
package ar.com.tsoluciones.emergencies.server.businesslogic.core.util;

import ar.com.tsoluciones.arcom.security.Role;
import ar.com.tsoluciones.arcom.security.User;

/**
 * <p>Determina si el usuario pertenece a alguno de los roles importantes para el sistema. Es una clase cliente, no emplea proxy</p>
 *
 * @author Ipoletti, despada
 * @version 1.0, 29/11/2004
 * @see
 */
public class UserRolesHelper {
	public static final String OPERATOR_ROLE = "OPERADOR";
	public static final String SUPERVISOR_ROLE = "SUPERVISOR";
	public static final String SPECIALIST_ROLE = "ESPECIALISTA";
	public static final String DERIVATION_UNIT_ROLE = "DESPACHANTE";
	public static final String CLOSER_ROLE = "CLOSER";
	public static final String OPERATIONS_ROLE = "OPERACIONES";
	public static final String DEPENDENCE_ROLE = "DEPENDENCIA";
	public static final String COORDINATOR_ROLE = "COORDINADOR";
	public static final String DAOC_ROLE = "DAOC";
	public static final String JEFE_DE_SALA_ROLE = "CHIEF";
	public static final String DERIVATOR_ROLE = "DERIVATOR";

	/**
	 * Verifica si el usuario tiene un rol
	 * @param roleName Nombre del rol
	 * @param user Usuario verificado
	 * @return true si lo tiene, false si no
	 */
	public static boolean hasRole(User user, String roleName) {

		// Para los otros roles es lo mismo
		Object array[] = user.getRoles().toArray();

		for (int i = 0; i < array.length; i++) {
			Role role = (Role) array[i];
			if (role.getName().toLowerCase().startsWith(roleName.toLowerCase()))
				return true;
		}
		return false;
	}
}
