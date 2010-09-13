package ar.com.tsoluciones.arcom.serviceproxy.aspects.profiling;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import ar.com.tsoluciones.arcom.serviceproxy.aspects.Aspect;

/**
 * Copyright (c) Telefónica Soluciones
 * Todos los derechos reservados.
 */

/**
 * <p>
 * Este aspecto implementa un profiling avanzado de metodos. Toma estadisticas de los tiempos de ejecucion de cada
 * metodo y puede presentar una lista
 * </p>
 * <p>
 * Para utilizar este aspecto, debe configurar dos parámetros:
 * </p>
 * <p>
 * a) file: ruta completa al archivo donde se escriben las estadisticas
 * b) refresh: tiempo en milisegundos de intervalo para actualizar las estadísticas
 * </p>
 *
 * @author despada
 * @version 1.0, Apr 5, 2005, 4:24:16 PM
 * @see ar.com.tsoluciones.arcom.serviceproxy.aspects.Aspect
 */
public class StatsProfilingAspect extends Aspect {
	// Estado global de estadisticas
	private static Map<String, Stat> statMap = Collections.synchronizedMap(new HashMap<String, Stat>());

	private static Timer timer = null;

	// Configuración
	private static final String FILE_CONFIGURATION = "file";

	private static final String REFRESH_FILE_CONFIGURATION = "refresh";

	// Toma los tiempos del metodo particular
	private Calendar calendar;

	/**
	 * Escribe las estadisticas a un archivo
	 */
	protected class StatsTimerTask extends TimerTask {
		@Override
		public void run() {
			// Escribir estadisticas
			writeStatisticsToFile();
		}
	}

	/**
	 * Estadisticas para un metodo
	 */
	private static class Stat {
		private String method;

		private long milliseconds = 0;

		private long times = 0;

		public Stat(String method) {
			this.method = method;
		}

		public void addMilliseconds(long milliseconds) {
			this.milliseconds += milliseconds;
		}

		public void sumExecution() {
			times++;
		}

		public Double getAverage() {
			return new Double(milliseconds / (double)times);
		}

		public long getExecutions() {
			return times;
		}

		public String getMethod() {
			return method;
		}
	}

	/**
	 * Comparator para ordenar un array de Stats
	 */
	protected static class StatComparator implements Comparator<Stat> {
		public int compare(Stat one, Stat two) {
			Stat statOne = one;
			Stat statTwo = two;

			if (statOne.getAverage().doubleValue() < statTwo.getAverage().doubleValue())
				return -1;

			return 1;
		}
	}

	/**
	 * Lanza el timer si no arranco ya
	 */
	public void launchTimer() {

		// Arrancar timer si no arranco
		if (timer == null) {
			// Configuración
			String refreshString = getConfiguration(REFRESH_FILE_CONFIGURATION);
			if (refreshString == null)
				throw new IllegalArgumentException("No ha proporcionado la configuración " + REFRESH_FILE_CONFIGURATION);

			long refresh = Long.valueOf(refreshString).longValue();

			// Lanzar timer
			timer = new Timer();
			timer.scheduleAtFixedRate(new StatsTimerTask(), refresh, refresh);
		}
	}

	@Override
	public void onBeforeMethod(Method method, Object[] args, Annotation[] annotations) {
		calendar = Calendar.getInstance();
	}

	@Override
	public void onFinally(Method method, Object[] args) {
		long executionTime = Calendar.getInstance().getTimeInMillis() - calendar.getTimeInMillis();

		String methodKey = method.getDeclaringClass() + "." + method.getName();

		// Sumar segundos al metodo
		// Tomar los tiempos preexistentes, si hay
		Stat stat = statMap.get(methodKey);

		if (stat == null)
			stat = new Stat(methodKey);

		stat.addMilliseconds(executionTime);
		stat.sumExecution();

		statMap.put(methodKey, stat);
	}

	@Override
	public Object clone() {
		// Al momento de clonarlo, arrancar el timer si no arranco
		launchTimer();

		Aspect aspect = new StatsProfilingAspect();
		aspect.addConfiguration(FILE_CONFIGURATION, this.getConfiguration(FILE_CONFIGURATION));
		aspect.setLayerNumber(getLayerNumber());

		return aspect;
	}

	/**
	 * Escribe las estadisticas al archivo de estadisticas
	 */
	protected synchronized void writeStatisticsToFile() {
		FileOutputStream fileOutputStream = null;
		PrintStream printStream = null;

		try {
			String file = getConfiguration(FILE_CONFIGURATION);
			if (file == null)
				throw new IllegalArgumentException("No se proporciono un archivo, debe proporcionar uno para escribir las estadisticas");

			fileOutputStream = new FileOutputStream(file, true);
			printStream = new PrintStream(fileOutputStream);

			Stat[] statArray = statMap.values().toArray(new Stat[0]);
			Arrays.sort(statArray, new StatComparator());

			printStream.println("--------------------------------- " + new Date());
			for (int i = 0; i < statArray.length; i++) {
				Stat stat = statArray[i];

				printStream.println(stat.getAverage() + "\t(" + stat.getExecutions() + ")\t" + stat.getMethod());
			}
			printStream.println("---------------------------------");
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Imposible escribir al archivo de estadisticas", e);
		} finally {
			try {
				if (printStream != null)
					printStream.close();
				if (fileOutputStream != null)
					fileOutputStream.close();
			} catch (IOException e) {
				throw new RuntimeException("Imposible cerrar el archivo de estadisticas", e);
			}
		}
	}

}