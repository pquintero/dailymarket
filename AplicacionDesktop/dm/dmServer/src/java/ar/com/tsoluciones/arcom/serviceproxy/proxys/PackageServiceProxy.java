package ar.com.tsoluciones.arcom.serviceproxy.proxys;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import ar.com.tsoluciones.arcom.cor.InternalErrorException;
import ar.com.tsoluciones.arcom.logging.Log;
import ar.com.tsoluciones.arcom.serviceproxy.aspects.Aspect;

/**
 * Modificación sobre la clase BasicServiceProxy pero evalua también el paquete,
 * la clase y el metodo.
 *
 * @author Ipoletti
 * @version 1.0, 15/06/2005
 * @see
 */
public class PackageServiceProxy extends ServiceProxy {

	/**
	 * Invoca el método ejecutando los aspectos como "capas de una cebolla".
	 * @param proxy Proxy recibido
	 * @param method Metodo a invocar
	 * @param args Argumentos al metodo
	 * @return El retorno del objeto
	 * @throws Throwable Cuando algo falla
	 */
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object methodResult;

        String packageClassMethod = method.getDeclaringClass().getName() + "." + method.getName();
        Method methodImpl = getMethodImpl(method);
        Aspect[] aspectArray = this.getAspectArray(packageClassMethod, methodImpl.getAnnotations());

        // Invocar todos los aspectos (primero los layers exteriores)
        for (int i = aspectArray.length - 1; i >= 0; i--) {
            Log.getLogger(this.getClass()).debug("Aplicando aspecto " + aspectArray[i].getClass().getName());
            aspectArray[i].onBeforeMethod(method, args, methodImpl.getAnnotations());
        }

        try {
            methodResult = method.invoke(serviceObject, args);

            // Invocar todos los aspectos (primero los layers interiores)
            for (int i = 0; i < aspectArray.length; i++) {
                aspectArray[i].onAfterMethod(method, args, methodImpl.getAnnotations());
                Log.getLogger(this.getClass()).debug("Aspecto " + aspectArray[i].getClass().getName() + " finalizado con éxito");
            }

        } catch (Exception e) {
            // Invocar todos los aspectos (primero los layers interiores)
            for (int i = 0; i < aspectArray.length; i++)
                aspectArray[i].onException(method, e, args);

            Log.getLogger(this.getClass()).debug("Hubo un error en el proxy al invocar el método", e);
            // Si es una excepcion interna lanzada por el método, no
            // encapsularla, lanzarla tal como viene
            if (e instanceof InvocationTargetException)
                throw e.getCause();

            throw new InternalErrorException("Hubo un error en el proxy al invocar el método", e);
        } finally {
            // Invocar todos los aspectos (primero los layers interiores)
            for (int i = 0; i < aspectArray.length; i++)
                aspectArray[i].onFinally(method, args);
        }

        // Devolver retorno
        return methodResult;
    }

    /**
     * Factory method que crea un basicServiceProxy vacio
     * @return un objeto BasicServiceProxy
     */
    @Override
	public ServiceProxy newInstance() {
        return new PackageServiceProxy();
    }
}