package telefront;

/**
 * Representa un error de negocios en el servidor
 * 
 * @author Ipoletti
 * @version 1.0, 17/06/2005
 * @see
 */
public class BusinessException extends TelefrontException {
	public BusinessException() {
		super();
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}
}
