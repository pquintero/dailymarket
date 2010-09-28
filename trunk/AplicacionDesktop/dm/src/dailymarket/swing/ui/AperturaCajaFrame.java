package dailymarket.swing.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.dom4j.Document;

import telefront.TelefrontGUI;

import dailymarket.lectorDeHuellas.UtilLectorHuellasSingleton;


@SuppressWarnings("serial")
public class AperturaCajaFrame extends DailyMarketFrame implements HuellaDigitalInterface{

	protected JFrame parentFrame;
	protected JLabel imgHuella = new JLabel();
    protected boolean FIRMA_APERTURA = false;
	JLabel mensaje = new JLabel();
	JLabel mensajeLector = new JLabel();

    protected JTextField montoApertura = new JTextField();
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	JTextField monto = new JTextField();
	JTextField cajero = new JTextField("");
	AperturaCajaFrame frame ;
	
	JPanel imageHuellaPanel = new JPanel();
	JButton cerrarButton;
	UtilLectorHuellasSingleton  utilHuellas = UtilLectorHuellasSingleton.getInstance();
	
	
	public AperturaCajaFrame(JFrame f){
		
		parentFrame = f;
		frame = this;
		setTitle("Apertura  Caja ");
		
		JPanel mainPanel = new JPanel();
		mainPanel.setPreferredSize(new Dimension(500,400));
		mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		JPanel aperturaPanel = new JPanel();
		aperturaPanel.setPreferredSize(new Dimension(500,130));
		
		JPanel formPanel = new JPanel();
		formPanel.setPreferredSize(new Dimension(300,130));
		formPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		imageHuellaPanel.setPreferredSize(new Dimension(180,125));
		imageHuellaPanel.setLayout( new GridBagLayout());
		imageHuellaPanel.setBorder(new TitledBorder(new LineBorder(Color.GRAY), "Huella",
				TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, null, Color.BLACK));
		imageHuellaPanel.remove(imgHuella);

		aperturaPanel.add(formPanel);
		aperturaPanel.add(imageHuellaPanel);
		
		JLabel dateAperturaCajaLabel = new JLabel("Fecha y hora de Apertura :");
		JTextField dateAperturaCaja = new JTextField();
		dateAperturaCaja.setEditable(false);
		dateAperturaCaja.setPreferredSize(new Dimension(130,20));
		dateAperturaCaja.setText(sdf.format(new Date()));
		formPanel.add(dateAperturaCajaLabel);
		formPanel.add(dateAperturaCaja);
		
		JLabel montoAperturaLabel = new JLabel("Monto de Apertura :");
		montoApertura = new JTextField();
		montoApertura.setPreferredSize(new Dimension(100,20));
		montoApertura.setText("456,89");
		montoApertura.setEditable(false);
		formPanel.add(montoAperturaLabel);
		formPanel.add(montoApertura);
		
		JLabel cajeroLabel = new JLabel("Nombre de Usuario :");
		cajero = new JTextField();
		cajero.setPreferredSize(new Dimension(130,20));
		formPanel.add(cajeroLabel);
		formPanel.add(cajero);
		
		JLabel montoLabel = new JLabel("Monto :");
		monto = new JTextField();
		monto.setPreferredSize(new Dimension(130,20));
		formPanel.add(montoLabel);
		formPanel.add(monto);
		
		JPanel buttonsMainPanel = new JPanel();
		buttonsMainPanel.setPreferredSize(new Dimension(500,50));
		buttonsMainPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JPanel messageFingerPrintPanel = new JPanel();
		messageFingerPrintPanel.setPreferredSize(new Dimension(180,45));
		messageFingerPrintPanel.setBorder(new TitledBorder(new LineBorder(Color.GRAY), "Eventos del Lector",
				TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, null, null));
		messageFingerPrintPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		messageFingerPrintPanel.add(mensajeLector);
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setPreferredSize(new Dimension(300,50));
	
		buttonsMainPanel.add(buttonsPanel);
		buttonsMainPanel.add(messageFingerPrintPanel);
		
		JPanel messagePanel = new JPanel();
		messagePanel.setPreferredSize(new Dimension(480, 60));
		messagePanel.setBorder(new TitledBorder(new LineBorder(Color.GRAY), "Mensajes",
				TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, null, null));
		messagePanel.add(mensaje);
		
		mainPanel.add(aperturaPanel);
		mainPanel.add(buttonsMainPanel);
		mainPanel.add(messagePanel);
		
		getContentPane().add(mainPanel);
		
		JButton firmar = new JButton("Firmar");
		firmar.setPreferredSize(new Dimension(80,30));
		firmar.setMnemonic(KeyEvent.VK_F);
		firmar.addActionListener(new firmarsButtonListener());
		buttonsPanel.add(firmar);
	
		cerrarButton = new JButton("Cancelar");
		cerrarButton.setPreferredSize(new Dimension(90,30));
		cerrarButton.setMnemonic(KeyEvent.VK_C);
		cerrarButton.setEnabled(false);
		cerrarButton.addActionListener(new CerrarButtonListener());
		
		buttonsPanel.add(cerrarButton);
		JButton volverButton = new JButton("Volver");
		volverButton.setMnemonic(KeyEvent.VK_V);
		volverButton.setPreferredSize(new Dimension(80,30));
		buttonsPanel.add(volverButton);
		volverButton.addActionListener(new volverButtonListener());
		
		getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER));
		setBounds(200, 100, 600, 350);
		setVisible(true);

	}

	class CerrarButtonListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			
			if(FIRMA_APERTURA){
				//CANCELAR LA FIRMA
				FIRMA_APERTURA  = false;
				mensaje.setText("Apertura cancelada");
				mensaje.setForeground(Color.red);
				utilHuellas.stop(mensajeLector);
				cerrarButton.setEnabled(false);
			}
		}
	}
	
	class volverButtonListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			utilHuellas.stop(mensajeLector);

			if(FIRMA_APERTURA){
				String [] disabledButtons = new String[2];
				disabledButtons[0] = DailyMarketFrame.APERTURA_CAJA;
				disabledButtons[1] = DailyMarketFrame.CERRAR_APLICACION;
				
				((CajaFrame)parentFrame).deshabilitarBotones(disabledButtons);
				parentFrame.setVisible(true);
				dispose();
				
			}else{
				
				String [] disabledButtons = new String[2];
				disabledButtons[0] = DailyMarketFrame.CERRAR_CAJA;
				disabledButtons[1] = DailyMarketFrame.NUEVA_SESION;
				
				((CajaFrame)parentFrame).deshabilitarBotones(disabledButtons);
				parentFrame.setVisible(true);
				dispose();
				
			}
		}
		
	}
	
	class firmarsButtonListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			if( monto.getText().equals("")  || cajero.equals("")){
				JOptionPane.showMessageDialog(frame, "Debe Ingresar el monto de apertura y usuario (cajero) ");
			}else{
				//VALIDAR Q SEA NUMERICO
				try{
//					//Aca se debe leer la huella digital y una vez leido  hacer el login
//					String huellaDigital = "";
//					Object params[] = new String[] { cajero.getText(), monto.getText(),"", huellaDigital};
//					Document doc = TelefrontGUI.getInstance().executeMethod(CONTROLLER_CLASS, "abrirCaja", params);
//					if (doc != null) {
//						System.out.println(doc.asXML());
//					//	setCurrentUser(doc);
//					}
//					
					
					Double.parseDouble(monto.getText());
		
					mensaje.setText("Esperando su huella digital");
					mensaje.setForeground(Color.red);
				
					utilHuellas.init( frame);
					utilHuellas.start(mensajeLector);
					
				}
				catch (NumberFormatException e) {
					mensaje.setText("El valor debe ser numérico");
					mensaje.setForeground(Color.red);
				}	
			}
		}
		
  }

	public String getUserName() {
		return cajero.getText();
	}

	public JLabel getFingerPrintPicture() {
		return imgHuella;
	}

	public JLabel getFrameMensaje() {
		return mensaje;
	}

	public JPanel getImageHuellaPanel() {
		return imageHuellaPanel;
	}

	public JLabel getMensajeLector() {
		return mensajeLector;
	}

	public  void loguear(/*User*/){
   	 cajero.setEditable(false);
   	 monto.setEditable(false);
   	
	 FIRMA_APERTURA = true;
  	 cerrarButton.setEnabled(true);
		 
  	 
  	 //	setCurrentUser(user);
  	 
	 }
}
