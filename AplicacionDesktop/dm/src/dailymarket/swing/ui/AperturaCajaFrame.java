package dailymarket.swing.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import nl.jj.swingx.gui.modal.JModalDialog;


import dailymarket.lectorDeHuellas.UtilLectorHuellasSingleton;
import dailymarket.lectorDeHuellas.LectorDeHuellasFirstLogin;


@SuppressWarnings("serial")
public class AperturaCajaFrame extends JModalDialog implements HuellaDigitalInterface{

	protected JFrame parentFrame;
	protected JLabel imgHuella = new JLabel();
    protected boolean FIRMA_APERTURA = false;
    protected boolean DO_FIRST_LOGIN = false;
	JLabel mensaje = new JLabel();
	JLabel mensajeLector = new JLabel();
	JTextField cajero = new JTextField("");
	AperturaCajaFrame frame ;
	JPanel imageHuellaPanel = new JPanel();

    protected JPasswordField passwordTextField = new JPasswordField();
	JLabel passwordLabel = new JLabel("Password :");
	JButton firmar = new JButton("Firmar");
	JCheckBox checkFirstLogon = new JCheckBox("Primer Logeo");
	JLabel cajeroLabel = new JLabel("Nombre de Usuario :");
	JLabel lectorImgLabel = new JLabel();
	JButton volverButton = new JButton("Volver");
	private Double MONTO_CONSTANTE_APERTURA = new Double(575);
	
	UtilLectorHuellasSingleton utilHuellas = null;
	LectorDeHuellasFirstLogin utilHuellasFirstLogin = null;
	
	public AperturaCajaFrame(JFrame f){
		parentFrame = f;
		frame = this;

		JFrame.setDefaultLookAndFeelDecorated(true);
     	this.setDefaultCloseOperation(0);
	    
		setResizable(false);
		
		java.net.URL img = InitDailyMarketFrame.class.getResource("dm.ico");
		ImageIcon logoImg = new ImageIcon(img);
		this.setIconImage(logoImg.getImage());
		
		setTitle("Apertura  Caja ");
		setLocationRelativeTo(parentFrame);
		JPanel mainPanel = new JPanel();
		mainPanel.setPreferredSize(new Dimension(500,400));
		mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		JPanel aperturaPanel = new JPanel();
		aperturaPanel.setPreferredSize(new Dimension(500,130));
		
		JPanel formPanel = new JPanel();
		formPanel.setPreferredSize(new Dimension(300,130));
		formPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		imageHuellaPanel.setPreferredSize(new Dimension(180,125));
		imageHuellaPanel.setLayout( new GridBagLayout());
		imageHuellaPanel.setBorder(new TitledBorder(new LineBorder(Color.GRAY), "Huella",
				TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, null, Color.BLACK));
		imageHuellaPanel.remove(imgHuella);

		aperturaPanel.add(formPanel);
		aperturaPanel.add(imageHuellaPanel);
				
		java.net.URL imgURL = InitDailyMarketFrame.class.getResource("lector.gif");
		ImageIcon lectorImg = new ImageIcon(imgURL);
		lectorImgLabel = new JLabel(new ImageIcon(lectorImg.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH)));
 
		formPanel.add(lectorImgLabel);
	
		JLabel altaHuellaLabel = new JLabel("----- A L T A   D E   H U E L L A   D I G I T A L --- ");
		altaHuellaLabel.setForeground(Color.black);
		altaHuellaLabel.setFont(new Font("Serif", Font.BOLD, 14));

		formPanel.add(altaHuellaLabel);

		cajero = new JTextField();
		cajeroLabel.setVisible(false);
		cajero.setVisible(false);
		cajero.setPreferredSize(new Dimension(130,20));
		formPanel.add(cajeroLabel);
		formPanel.add(cajero);

		passwordTextField.setPreferredSize(new Dimension(180,20));
		passwordTextField.setVisible(false);
		
		passwordLabel.setVisible(false);
		formPanel.add(passwordLabel);
		formPanel.add(passwordTextField);
		
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
		mensaje.setText("Presione en Firmar y luego apoye su dedo en lector");
		messagePanel.add(mensaje);
		
		mainPanel.add(aperturaPanel);
		mainPanel.add(buttonsMainPanel);
		mainPanel.add(messagePanel);
		
		getContentPane().add(mainPanel);
		
		firmar.setPreferredSize(new Dimension(80,30));
		firmar.setMnemonic(KeyEvent.VK_F);
		firmar.addActionListener(new firmarsButtonListener());
		buttonsPanel.add(firmar);
	
		checkFirstLogon.setSelected(false);
		checkFirstLogon.addActionListener( new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(checkFirstLogon.isSelected()){
					passwordLabel.setVisible(true);
					passwordTextField.setVisible(true);
					cajeroLabel.setVisible(true);
					cajero.setVisible(true);
					lectorImgLabel.setVisible(false);
					mensaje.setText("Ingrese usuario y password y presione en Firmar");
				}
				else{
					cajeroLabel.setVisible(false);
					cajero.setVisible(false);
					passwordLabel.setVisible(false);
					passwordTextField.setVisible(false);
					lectorImgLabel.setVisible(true);
					mensaje.setText("Presione en Firmar y luego apoye su dedo en lector");

				}
				
			}
		});

		volverButton.setMnemonic(KeyEvent.VK_V);
		volverButton.setPreferredSize(new Dimension(80,30));
		buttonsPanel.add(volverButton);
		buttonsPanel.add(checkFirstLogon);

		volverButton.addActionListener(new volverButtonListener());
		
		getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER));
		setBounds(100, 100, 600, 350);
		setVisible(true);

	}
	class volverButtonListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			String [] disabledButtons = new String[2];
			
			if(FIRMA_APERTURA){
				disabledButtons[0] = DailyMarketFrame.APERTURA_CAJA;
				disabledButtons[1] = DailyMarketFrame.CERRAR_APLICACION;
			}else{
				disabledButtons[0] = DailyMarketFrame.CERRAR_CAJA;
				disabledButtons[1] = DailyMarketFrame.NUEVA_SESION;
			}
			((CajaFrame)parentFrame).deshabilitarBotones(disabledButtons);
			parentFrame.setVisible(true);
			
			if(utilHuellas != null){
				utilHuellas.stop(mensajeLector);
				utilHuellas = null;
			}
			if(utilHuellasFirstLogin!= null){
				utilHuellasFirstLogin.stop(mensajeLector);
				utilHuellasFirstLogin = null;
			}
			
			dispose();
		}
	}
	
	class firmarsButtonListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			if( checkFirstLogon.isSelected() ){
				if(cajero.getText().equals("") || passwordTextField.getText().equals("")){
					JOptionPane.showMessageDialog(frame, "Debe Ingresar el usuario y password ");		
				}else{
					//Primer Logieo
					passwordTextField.setEnabled(false);
					cajero.setEnabled(false);
					utilHuellasFirstLogin = new LectorDeHuellasFirstLogin();
					utilHuellasFirstLogin.start(mensaje);
					utilHuellasFirstLogin.init(frame);
					firmar.setEnabled(false);
					checkFirstLogon.setEnabled(false);
//					volverButton.setEnabled(false);
					
				}
			}else{
					//logueo normal
					mensaje.setText("Esperando su huella digital");
					mensaje.setForeground(Color.red);
					firmar.setEnabled(false);
					utilHuellas = new UtilLectorHuellasSingleton();
					utilHuellas.start(mensajeLector);
					utilHuellas.initLogin(frame);
					checkFirstLogon.setEnabled(false);
//					volverButton.setEnabled(false);

				}
		}
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

	public  void loguear(){
   		 FIRMA_APERTURA = true;
   		 volverButton.setEnabled(true);
	 }
	public  void doFirstLogin(){ 
		passwordLabel.setVisible(true);
		passwordTextField.setVisible(true);
		mensajeLector.setText("");
		firmar.setEnabled(true);
		
	 }

	public String getUserPassword() {
		return passwordTextField.getText();
	}

	public String getMonto() {
		return MONTO_CONSTANTE_APERTURA.toString();
	}

	public void backToInitSession() {
		String[] disabledButtons = new String[2];
		disabledButtons[0] = DailyMarketFrame.APERTURA_CAJA;
		disabledButtons[1] = DailyMarketFrame.CERRAR_APLICACION;

		((CajaFrame) parentFrame).deshabilitarBotones(disabledButtons);
		parentFrame.setVisible(true);
		dispose();

	}

	public void altaDeHuella() {
		firmar.setEnabled(true);
		passwordTextField.setEnabled(true);
		cajero.setEnabled(true);
	}
	
	public void habilitarBotonFirmar(){
		firmar.setEnabled(true);
		passwordTextField.setEnabled(true);
		cajero.setEnabled(true);
		volverButton.setEnabled(true);
	}

	public String getUserName() {
		return cajero.getText();
	}
}
