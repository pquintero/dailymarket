package telefront;

/**
 * Clase base para las excepciones Telefront
 *
 * @author Ipoletti
 * @version 1.0, 17/06/2005
 * @see
 */
public class TelefrontException extends Exception
{
	private int errorCode;

	public TelefrontException()
	{
		super();
	}

	public TelefrontException(Throwable cause)
	{
		super(cause);
	}

	public TelefrontException(String message)
	{
		super(message);
	}

	public TelefrontException(String message, int errorCode)
	{
		super(message);
		this.errorCode = errorCode;
	}

	public TelefrontException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public int getErrorCode()
	{
		return errorCode;
	}

	public void setErrorCode(int errorCode)
	{
		this.errorCode = errorCode;
	}

}
