package ar.com.tsoluciones.arcom.cor;

/**
 * Copyright (c) Telefonica Soluciones
 * Todos los derechos reservados
 * <p/>
 * <p/>
 * Exception que determina algun error en el procesamiento del framework COR (Chain of Resposability)
 *
 * @author Javier Rizzo
 * @version 1.1
 * @see
 * @since 1.1
 */
public class ServiceException extends Exception
{

    private boolean logError = true;
    /**
	 * Constructor
	 */
	public ServiceException()
	{
		super();
	}

	/**
	 * Constructor
	 *
	 * @param message contiene mas informacion sobre la Exception
	 */
	public ServiceException(String message)
	{
		super(message);
	}

	/**
	 * Constructor
	 *
	 * @param message contiene mas informacion sobre la Exception
	 * @param cause	 contiene la Exception que origino la Exception actual
	 */
	public ServiceException(String message, Throwable cause)
	{
		super(message, cause);
	}

	/**
	 * Constructor
	 *
	 * @param cause contiene la Exception que origino la Exception actual
	 */
	public ServiceException(Throwable cause)
	{
		super(cause);
	}


	public ServiceException(String message, boolean logError)
	{
        super(message);
        this.logError = logError;
    }

	/**
	 * Constructor
	 *
	 * @param message contiene mas informacion sobre la Exception
	 * @param cause	 contiene la Exception que origino la Exception actual
	 */
	public ServiceException(String message, Throwable cause, boolean logError)
	{
        super(message, cause);
        this.logError = logError;
    }


    public boolean isLogError() {
        return logError;
    }

    public void setLogError(boolean logError) {
        this.logError = logError;
    }
}
