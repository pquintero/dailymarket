package dailymarket.swing.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;


import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import dailymarket.lectorDeHuellas.UtilLectorHuellasSingleton;
import dailymarket.model.Context;
import dailymarket.model.Empleado;


@SuppressWarnings("serial")
public class CerrarCajaFrame extends DailyMarketFrame implements HuellaDigitalInterface{

	protected JFrame parentFrame;
	protected JLabel imgHuella = new JLabel();
    protected boolean FIRMA_CIERRE = false;
	JLabel mensaje = new JLabel();
	JLabel mensajeLector = new JLabel();

	JTextField cajero = new JTextField("");
	CerrarCajaFrame frame ;
	Empleado user = Context.getInstance().getCurrentUser();
	
	JPanel imageHuellaPanel = new JPanel();
	JButton firmar = new JButton("Firmar");
	UtilLectorHuellasSingleton utilHuellas = null;
	
	public CerrarCajaFrame(JFrame f){
		
		parentFrame = f;
		frame = this;
		setTitle("Cerrar  Caja ");
		setLocationRelativeTo(parentFrame);
		JPanel mainPanel = new JPanel();
		mainPanel.setPreferredSize(new Dimension(500,400));
		mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		JPanel cierrePanel = new JPanel();
		cierrePanel.setPreferredSize(new Dimension(500,130));
		
		JPanel formPanel = new JPanel();
		formPanel.setPreferredSize(new Dimension(300,130));
		formPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		imageHuellaPanel.setPreferredSize(new Dimension(180,125));
		imageHuellaPanel.setLayout( new GridBagLayout());
		imageHuellaPanel.setBorder(new TitledBorder(new LineBorder(Color.GRAY), "Huella",
				TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, null, Color.BLACK));
		imageHuellaPanel.remove(imgHuella);

		cierrePanel.add(formPanel);
		cierrePanel.add(imageHuellaPanel);
		
		cajero.setEditable(false);
		cajero.setPreferredSize(new Dimension(180,20));
		
		
		java.net.URL imgURL = InitDailyMarketFrame.class.getResource("lector.gif");
		ImageIcon lectorImg = new ImageIcon(imgURL);
		JLabel lectorImgLabel = new JLabel(new ImageIcon(lectorImg.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH)));
		
		formPanel.add(lectorImgLabel);

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
		
		mainPanel.add(cierrePanel);
		mainPanel.add(buttonsMainPanel);
		mainPanel.add(messagePanel);
		
		getContentPane().add(mainPanel);
		
		firmar.setPreferredSize(new Dimension(80,30));
		firmar.setMnemonic(KeyEvent.VK_F);
		firmar.addActionListener(new firmarsButtonListener());
		buttonsPanel.add(firmar);

		JButton volverButton = new JButton("Volver");
		volverButton.setMnemonic(KeyEvent.VK_V);
		volverButton.setPreferredSize(new Dimension(80,30));
		buttonsPanel.add(volverButton);
		volverButton.addActionListener(new volverButtonListener());
		
		getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER));
		setBounds(100, 100, 600, 350);
		setVisible(true);

	}


	class volverButtonListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			String [] disabledButtons = new String[2];
			if(FIRMA_CIERRE){
				disabledButtons[0] = DailyMarketFrame.CERRAR_CAJA;
				disabledButtons[1] = DailyMarketFrame.NUEVA_SESION;
				
			}else{
				disabledButtons[0] = DailyMarketFrame.APERTURA_CAJA;
				disabledButtons[1] = DailyMarketFrame.CERRAR_APLICACION;
			}
			
			if(utilHuellas != null){
				utilHuellas.stop(mensajeLector);
				utilHuellas = null;
			}
			
			((CajaFrame)parentFrame).deshabilitarBotones(disabledButtons);
			parentFrame.setVisible(true);
			dispose();
		}
		
	}
	
	class firmarsButtonListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			
					mensaje.setText("Esperando su huella digital");
					mensaje.setForeground(Color.red);
					utilHuellas = new UtilLectorHuellasSingleton();
					utilHuellas.start(mensajeLector);				
					utilHuellas.initLogin(frame);

					firmar.setEnabled(false);
					
		}
		
  }

	public String getUserName() {
		return user.getUser();
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
   	 cajero.setEditable(false);
	 FIRMA_CIERRE = true;
	 }


	public String getUserPassword() {
		// TODO Auto-generated method stub
		return null;
	}


	public String getMonto() {
		return "20";
	}

	public void backToInitLogin() {
		String[] disabledButtons = new String[2];
		disabledButtons[0] = DailyMarketFrame.CERRAR_CAJA;
		disabledButtons[1] = DailyMarketFrame.NUEVA_SESION;

		((CajaFrame) parentFrame).deshabilitarBotones(disabledButtons);
		parentFrame.setVisible(true);
		dispose();
		
	}
	public void habilitarBotonFirmar(){
		firmar.setEnabled(true);	
	}
	

	public void altaDeHuella() {
		
	}
}
