package telefront;

/**
 * <p>
 * Esta excepción tiene como propósito reflejar el escenario en donde por algún problema no se puede 
 * llegar hasta el servidor.
 * </p> 
 */
public class ServerUnreachableException extends ConnectionException {
	
	public ServerUnreachableException(String msg, Throwable t) {
		super(msg, t);
	}
}
