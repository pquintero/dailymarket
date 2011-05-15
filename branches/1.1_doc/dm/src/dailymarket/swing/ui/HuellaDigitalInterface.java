package dailymarket.swing.ui;

import javax.swing.JLabel;
import javax.swing.JPanel;

//Interface para ventanas que quieran utilizar la huella digital

public interface HuellaDigitalInterface {

	
	public String getUserName();
	public JLabel getMensajeLector();
	public JLabel getFrameMensaje();
	public JPanel getImageHuellaPanel();
	public JLabel getFingerPrintPicture();
	public String getUserPassword();
	public String getMonto();
	public void loguear();
	public void altaDeHuella();

}
