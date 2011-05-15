package ar.com.tsoluciones.arcom.cor;


/**
 * Hace un wrapper de un error interno y elimina la parte de la pila
 * correspondiente a sí misma.
 */
public class WrappedInternalErrorException extends InternalErrorException {
    public WrappedInternalErrorException(Throwable t) {
        super(t.getMessage(), t);
        setStackTrace(new StackTraceElement[]{});
    }
}