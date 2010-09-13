package ar.com.tsoluciones.emergencies.server.concurrent;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import ar.com.tsoluciones.emergencies.server.gui.core.configuration.Configuration;

/**
 * <p>
 * Es un singleton que maneja un pool de threads que ejecutan tareas que son pasadas como instancias de la clase
 * <code>Command</code>.
 * </p>
 * @see ar.com.tsoluciones.emergencies.server.concurrent.Command
 */
public class CommandExecutor {

	private static CommandExecutor instance;
	private static Map<PoolName, ThreadPoolExecutor> threadPoolMap;
	private static RejectedExecutionHandler defaultRejectionHandler = new ThreadPoolExecutor.AbortPolicy();
	
	private CommandExecutor() {
		super();
		threadPoolMap = new HashMap<PoolName, ThreadPoolExecutor>();
	}

	public void addCommandPool(Configuration.Pool params) {
		Integer bufferSize = params.getBufferSize();
		Integer corePoolSize = params.getCorePoolSize();
		Integer keepAliveSeconds = params.getKeepAliveSeconds();
		Integer maximumPoolSize = params.getMaximumPoolSize();
		ArrayBlockingQueue<Runnable> buffer = new ArrayBlockingQueue<Runnable>(bufferSize);
		
		ThreadPoolExecutor pool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveSeconds, TimeUnit.SECONDS, buffer);
		pool.setRejectedExecutionHandler(defaultRejectionHandler);		
		threadPoolMap.put(params.getName(), pool);
	}
	
	/**
	 * Devuelve la instancia del singleton.
	 * @return CommandExecutor
	 */
	public synchronized static CommandExecutor getInstance() {
		if (instance == null) {
			instance = new CommandExecutor();
			Configuration conf = Configuration.getInstance();
			instance.addCommandPool(conf.getCommandExecutorParams());
			instance.addCommandPool(conf.getAuditPool());
			instance.addCommandPool(conf.getMessagingPool());
		}
		
		return instance;
	}


	/**
	 * Ejecuta los comandos en forma asincrónica en un pool determinado.
	 * @param name el nombre del pool
	 * @param command Implementación del comando
	 */
	public void execute(PoolName name, Command command) {
		try {
			threadPoolMap.get(name).execute(command);
		} catch (RejectedExecutionException e) {
			command.onCommandRejection(e);
		}
	}

	/**
	 * Detiene la ejecución del pool. Se espera que terminen de ejecutarse las tareas que hayan sido encoladas.
	 */
	public void stop() {
		for(ThreadPoolExecutor pool : threadPoolMap.values()) {
			pool.shutdown();
		}
	}

	/**
	 * Aborta la ejecución del pool.
	 */
	public void abort() {
		for(ThreadPoolExecutor pool : threadPoolMap.values()) {
			pool.shutdownNow();
		}
	}

	public static void main(String args[]) {
		CommandExecutor executor = CommandExecutor.getInstance();
		System.out.println("Voy a ejecutar la tarea");
		executor.execute(PoolName.DEFAULT, new FaultTolerantCommand(3, 5) {
			@Override
			public void tryIt(int order) {
				System.out.println("Intentando y fallando");
				throw new RuntimeException("Fallo forzado");
			}

			@Override
			public void onFailure(Throwable t) {
				System.out.println("Después de " + retries + " no pude ejecutar la tarea");
			}

			@Override
			public void onCommandRejection(Throwable t) {
				System.out.println("Comando rechazado por el pool");
			}});
		executor.stop();
	}
}
