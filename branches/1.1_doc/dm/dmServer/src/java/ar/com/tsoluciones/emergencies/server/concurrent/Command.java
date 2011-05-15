package ar.com.tsoluciones.emergencies.server.concurrent;

/**
 * Interface que representa un comando a ser ejecutado en forma asincrónica por parte del <code>CommandExecutor</code>
 * @see ar.com.tsoluciones.emergencies.server.concurrent.CommandExecutor.
 */
public abstract class Command implements Runnable {

	public void run() {
		exec();
	}

	public abstract void exec();
	
	/**
	 * Handler para el caso en el que el Commando no se pueda ejecutar (particularmente en el 
	 * caso de que el buffer esté completo)
	 * @param t
	 */
	public abstract void onCommandRejection(Throwable t);
}