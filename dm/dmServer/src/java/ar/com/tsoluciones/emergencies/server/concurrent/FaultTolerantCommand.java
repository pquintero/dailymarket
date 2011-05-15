package ar.com.tsoluciones.emergencies.server.concurrent;

import org.apache.log4j.Logger;

/**
 * <p>
 * Comando que permite realizar reintentos de una operación y en caso de que falle brinda
 * un punto de extensión a través del método <code>onFailure</code> para informar dicha
 * condición.
 * </p>
 */
public abstract class FaultTolerantCommand extends Command {
	private static final int ONE_SECOND_IN_MILLIS = 1000;

	protected long delaySeconds;
	protected int retries;

	/**
	 * Construye un comando que soporta reintentos tras la falla en la ejecución.
	 * @param retries Cantidad de reintentos.
	 * @param delay Demora especificada en segundos entre cada reintento.
	 */
	public FaultTolerantCommand(int retries, long delay) {
		if (retries < 0)
			throw new RuntimeException("La cantidad de reintentos debe ser un número natural");
		this.retries = retries;
		this.delaySeconds = delay * ONE_SECOND_IN_MILLIS;
	}

	@Override
	public final void exec() {
		Logger log = Logger.getLogger(FaultTolerantCommand.class);
		for(int i=0; i <= retries; i++) {
			try {
				log.debug("Intento nro: " + i);
				tryIt(i);
				break;
			} catch (Throwable t) {
				if (i < retries) {
					log.warn("Intento fallido. " + t);
					long startSleep = System.currentTimeMillis();
					try {
						Thread.sleep(delaySeconds);
					} catch (InterruptedException e) {
						log.error("Se interrumió el executor", e);
						throw new RuntimeException(e);
					}
					log.info("Salí del sueño, vuelvo a intentar. Dormí " + (System.currentTimeMillis() - startSleep) + " ms");
				} else {
					log.warn("El comando " + this + " no pudo procesarse luego de " + retries + " reintentos", t);
					onFailure(t);
				}

			}
		}
	}

	public abstract void tryIt(int order);
	public abstract void onFailure(Throwable t);
}