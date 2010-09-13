package ar.com.tsoluciones.util;

import ar.com.tsoluciones.arcom.security.User;

/**
 * <p>
 * Permite mantener el perfil del usuario asociado al contexto de un thread. De esta manera
 * es posible acceder al usuario desde los aspectos y cualquier otro contexto en donde el
 * mismo no llegue a través de la cadena de invocación normal.
 * El seteo y desvinculación del usuario en el contexto actual se hace en la clase base de los
 * managers <code>TelefrontServiceFactory</code>
 * </p>
 *
 * @see ar.com.tsoluciones.telefront.servicefactory.TelefrontServiceFactory#beforeExecution()
 * @see ar.com.tsoluciones.telefront.servicefactory.TelefrontServiceFactory#afterExecution()
 */
public class UserManager {
    private static ThreadLocal<User> usuarioActual = new ThreadLocal<User>();


    public static void setCurrentUser(User user) {
        usuarioActual.set(user);

    }

    public static void removeCurrentUser() {
        usuarioActual.remove();
    }

    public static User getCurrentUser() {
        return usuarioActual.get();
    }
}
