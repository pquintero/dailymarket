package dailymarket.swing.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;

public class InitDailyMarketFrame extends DailyMarketFrame{
	
 private int segundos = 0;
 private JProgressBar progressBar = new JProgressBar();
 private Timer tiempo;
 private JFrame frame;	
 JLabel  iniciandoLabel = new JLabel();


 public InitDailyMarketFrame(){
	 
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	 JPanel dmLogoPanel = new JPanel();
	 JPanel dailyMarketPanel = new JPanel();
	 JPanel dmProgressBarPanel = new JPanel();
	 
     dmLogoPanel.setPreferredSize(new Dimension(600, 200));
	 dmLogoPanel.setBackground(Color.white);
	
	 java.net.URL imgURL = InitDailyMarketFrame.class.getResource("dm.ico");
	 ImageIcon logoImg = new ImageIcon(imgURL);
				
	 JLabel logoPicLabel = new JLabel(logoImg);
     dmLogoPanel.add(logoPicLabel);
	 
	 //DAILIMARKETPANEL
	 dailyMarketPanel.setPreferredSize(new Dimension(300, 100));
	 JLabel dailyMarket = new JLabel();
	 dailyMarket.setText("D A I L Y M A R K E T");
	 dailyMarketPanel.add(dailyMarket);
	 dailyMarket.setFont(new Font("Serif", Font.BOLD, 25));
	 dailyMarketPanel.setBackground(Color.WHITE);
	 
	 iniciandoLabel.setText("Iniciando ...");
	 
	 
	 dailyMarketPanel.add(iniciandoLabel);

	 //PROGRESS BAR
	 
	 progressBar.setStringPainted(true);
	 
	 dmProgressBarPanel.setPreferredSize(new Dimension(600, 100));
	 dmProgressBarPanel.add(progressBar);
	 dmProgressBarPanel.setBackground(Color.white);
		
	 setLayout(new FlowLayout(FlowLayout.CENTER));
	 setBounds(200,100, 600, 400);
	 
	 JSeparator separador = new JSeparator();
	 separador.setPreferredSize(new Dimension(400, 10));
	 separador.setForeground(Color.BLACK);
	 setBounds(200, 100, 500, 400);
	 dmLogoPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
	 dmLogoPanel.add(separador);
	 dmLogoPanel.setPreferredSize(new Dimension(300,200));
	 getContentPane().add(dmLogoPanel);
	 getContentPane().add(dailyMarketPanel);
	 getContentPane().add(dmProgressBarPanel);
	 getContentPane().setBackground(Color.white);
	 
	 frame = this;
	 setVisible(true);
	
  
	 
	 
//	 addKeyListener(this);
	 tiempo = new Timer();
	 
	 tiempo.schedule(new TimerProgressBar(), 0, 30);
 }

 
public  static void main(String arg[]){
	 
	 new InitDailyMarketFrame();
 }
 
 class TimerProgressBar extends TimerTask{

	@Override
	public void run() {
		segundos++;
		progressBar.setValue(segundos);
	
		if(segundos == 100){
			tiempo.cancel();
			crearNuevaSesion();
		}
		
	}

	private void crearNuevaSesion() {
		String [] disabledButtons = new String[2];
		disabledButtons[0] = DailyMarketFrame.CERRAR_CAJA;
		disabledButtons[1] = DailyMarketFrame.NUEVA_SESION;
		
		(new CajaFrame()).deshabilitarBotones(disabledButtons);
		dispose();
		
	}
	 
 }
 public BufferedImage cargarImagen(String path, Class  c) {

	    BufferedImage  logoPic = null;
		java.net.URL imgURL = c.getResource(path);
	
				try {
					logoPic = ImageIO.read(new File(imgURL.toURI()));
				} catch (IOException e) {
					iniciandoLabel.setText("eeeeeerrror1");
					e.printStackTrace();
				} catch (URISyntaxException e) {
					iniciandoLabel.setText("eeeeeerrror1w2");
				}
	
		return logoPic;
		
	}
	
}
