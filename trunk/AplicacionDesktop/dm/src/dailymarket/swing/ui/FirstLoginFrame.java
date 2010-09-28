package dailymarket.swing.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import dailymarket.lectorDeHuellas.UtilLectorHuellasSingleton;

@SuppressWarnings("serial")
public class FirstLoginFrame extends DailyMarketFrame  {

	protected JFrame parentFrame;
	protected JLabel imgHuella = new JLabel();
	JLabel mensaje = new JLabel();
	JLabel mensajeLector = new JLabel();
    protected JPasswordField passwordTextField = new JPasswordField();

	JTextField usuario = new JTextField("");
	FirstLoginFrame frame ;
	
	JPanel imageHuellaPanel = new JPanel();
	
	UtilLectorHuellasSingleton utilHuellas = UtilLectorHuellasSingleton.getInstance();
	JButton firmar = new JButton("Registrar Huella");
	
	public FirstLoginFrame(JFrame f){
		
		parentFrame = f;
		frame = this;
		setTitle("Primer Logueo  ");
		
		JPanel mainPanel = new JPanel();
		mainPanel.setPreferredSize(new Dimension(500,400));
		mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		JPanel firstLoginPanel = new JPanel();
		firstLoginPanel.setPreferredSize(new Dimension(500,130));
		
		JPanel formPanel = new JPanel();
		formPanel.setPreferredSize(new Dimension(300,130));
		formPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		imageHuellaPanel.setPreferredSize(new Dimension(180,125));
		imageHuellaPanel.setBorder(new TitledBorder(new LineBorder(Color.GRAY), "Huella",
				TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, null, Color.BLACK));
		imgHuella.setName("img");
		imageHuellaPanel.add(imgHuella);
		
		firstLoginPanel.add(formPanel);
		firstLoginPanel.add(imageHuellaPanel);
		
		
		JLabel cajeroLabel = new JLabel("Nombre de Usuario :");
		usuario = new JTextField();
		usuario.setPreferredSize(new Dimension(130,20));
		formPanel.add(cajeroLabel);
		formPanel.add(usuario);
		
		JLabel passwordLabel = new JLabel("Password :");
		passwordTextField.setPreferredSize(new Dimension(180,20));

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
		messagePanel.add(mensaje);
		
		mainPanel.add(firstLoginPanel);
		mainPanel.add(buttonsMainPanel);
		mainPanel.add(messagePanel);
		
		getContentPane().add(mainPanel);
		
		firmar.setPreferredSize(new Dimension(150,30));
		firmar.setMnemonic(KeyEvent.VK_F);
		firmar.addActionListener(new firmarsButtonListener());
		buttonsPanel.add(firmar);
		
		JButton volverButton = new JButton("Volver");
		volverButton.setMnemonic(KeyEvent.VK_V);
		volverButton.setPreferredSize(new Dimension(80,30));
		buttonsPanel.add(volverButton);
		volverButton.addActionListener(new volverButtonListener());
		
		getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER));
		setBounds(200, 100, 600, 350);
		setVisible(true);

	}
	
	class volverButtonListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			utilHuellas.stop(mensajeLector);
			dispose();
			}
		}
				
	class firmarsButtonListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			
			if( passwordTextField.getText().equals("")  || usuario.equals("")){
				JOptionPane.showMessageDialog(frame, "Debe Ingresar el usuario y clave ");
			}else{
					mensaje.setText("Esperando su huella digital");
					mensaje.setForeground(Color.red);
					utilHuellas.init(mensaje,imgHuella , usuario.getText(), imageHuellaPanel, mensajeLector);
					utilHuellas.start(mensajeLector);
					firmar.setEnabled(false);
			}
		
		}
	}
  
	public static void main(String arg[]){
	
		new FirstLoginFrame(null);
	}
	
}
