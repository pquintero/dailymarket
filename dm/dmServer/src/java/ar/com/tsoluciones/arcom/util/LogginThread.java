package ar.com.tsoluciones.arcom.util;

import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.PrintStream;
import java.io.PrintWriter;

import ar.com.tsoluciones.emergencies.server.hibernate.C3P0ConnectionProvider;

/**
 * <p>Title: </p>
 *
 * <p>Description: Clase que logea las conexiones.</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author Ariel Clocchiatti
 * @version 1.0
 */

public class LogginThread extends Thread {

	private String fileLog = "";
	private static boolean paro = false;
	static int segundos = 30;
	static int dias = 3;
	static private int contador;
	static {
		contador = -1;
	}

	public LogginThread(String archivo, int segundos, int dias) throws Exception {

		if (contador > -1) {
			if (!paro)
				throw new Exception("YA ESTA EJECUTANDOSE, SOLO SE PUEDE VOLVER A EJECUTAR, CUANDO FINALICE.");
		}
		contador++;
		fileLog = archivo;
		LogginThread.segundos = segundos;
		LogginThread.dias = dias;
		paro = false;
	}

	@Override
	public void run() {

		try {
			synchronized (this) {

				while (true) {
					//2veces*60min*24Hs*3Dias = 9000 aprox. ejecuciones en 3 Dias.
					if (contador > ((60 / segundos) * 60 * 24 * dias)) {
						contador = -1; //reseteo.
						return;
					}
					contador++;

					this.wait(segundos * 1000); //Cada 30 Segundos se ejecuta.

					ByteArrayOutputStream bout = new ByteArrayOutputStream();
					PrintStream o = new PrintStream(bout);

					FileWriter f = new FileWriter(fileLog, true);
					PrintWriter p = new PrintWriter(f);

					p.println("*********************" + new java.util.Date() + "**************************");

					C3P0ConnectionProvider.printReport(o);
					//C3P0ConnectionProvider cp = (C3P0ConnectionProvider) ((SessionFactoryImpl) SystemManager.getSessionFactory()).getSettings().getConnectionProvider();

					p.println(new String(bout.toByteArray()));
					p.flush();
					p.close();

				}
			}

		} catch (Exception f) {
			f.printStackTrace();
			return;
		}
	}

	public static synchronized void pararLog() {

		contador = ((60 / segundos) * 60 * 24 * dias) + 1;
		paro = true;

	}
}
