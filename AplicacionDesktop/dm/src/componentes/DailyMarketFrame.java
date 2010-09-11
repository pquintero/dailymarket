package componentes;

import java.awt.event.WindowAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class DailyMarketFrame extends JFrame {

	protected static final String  CERRAR_APLICACION  = "cerrar_aplicacion";
	protected static final String  APERTURA_CAJA  = "apertura_caja";
	protected static final String  CERRAR_CAJA  = "cerrar_caja";
	protected static final String  NUEVA_SESION  = "nueva_sesion";
	
	
 public DailyMarketFrame(){

     	JFrame.setDefaultLookAndFeelDecorated(true);
     	this.setDefaultCloseOperation(0);
	    this.addWindowListener(new WindowAdapter() {});
	    
		setResizable(false);
		setVisible(true);
		
		java.net.URL imgURL = InitDailyMarketFrame.class.getResource("images/dm.ico");
		ImageIcon logoImg = new ImageIcon(imgURL);
		this.setIconImage(logoImg.getImage());
	 
 }
 

 
 	
}
