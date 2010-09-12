package telefront;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import logging.Logger;

import dailymarket.swing.ui.LargeDetailDialog;

/**
 * Tratamiento estandar de errores de telefront
 * Si no se especifica uno se utilizara esta implementacion
 *
 * @author ipoletti, despada
 * @version 1.0, 13/07/2006
 * @see
 */
public class ErrorMessageListener implements TelefrontExceptionListener {
	static LargeDetailDialog largeDetailDialog = null;

	public void catchException(TelefrontExceptionEvent event) {
		logError(event.getException(), event.getTelefront());
	}

	/**
	 * Logea un error del sistema
	 * @param e Error del sistema
	 */
	public static void logError(Exception e) {
		logError(e, null);

	}

	/**
	 * Logea un error del sistema y lo notifica al usuario por una ventana de dialogo
	 * @param e Error del sistema
	 * @param telefront Objeto telefront que reporta el error
	 */
	private static synchronized void logError(Exception e, TelefrontGUI telefront) {
		Logger.getLogger911().warn("Excepcion reportada desde business logic", e);

		if (largeDetailDialog != null)
			return;

		Logger.getLogger911().info("Creando y mostrando nuevo cartel de error");

		largeDetailDialog = new LargeDetailDialog(telefront.getParent(), "Mensaje", "Información de F911", e.getMessage());

		largeDetailDialog.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				largeDetailDialog = null;
			}
		});
		largeDetailDialog.setVisible(true);
	}

}
