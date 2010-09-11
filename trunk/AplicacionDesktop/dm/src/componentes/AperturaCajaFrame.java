package componentes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import org.dom4j.Element;

import telefront.TelefrontGUI;
import dailymarket.model.Cajero;
import dailymarket.model.Context;

public class AperturaCajaFrame extends DailyMarketFrame{

	protected JFrame parentFrame;
	private static final String CONTROLLER_CLASS = "ar.com.tsoluciones.emergencies.server.gui.core.telefront.action.AperturaCajaManagerService";
	
    protected boolean FIRMA_APERTURA = false;
	JLabel mensaje = new JLabel();
    protected JTextField montoApertura = new JTextField();
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	JTextField monto = new JTextField();
	JTextField cajero = new JTextField();
	JTextField dateAperturaCaja = new JTextField();
	JFrame frame ;
	
	public AperturaCajaFrame(JFrame f){
		
		parentFrame = f;
		frame = this;
		setTitle("Apertura  Caja ");
		
		JPanel mainPanel = new JPanel();
		mainPanel.setPreferredSize(new Dimension(500,400));
		mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		JPanel aperturaPanel = new JPanel();
		aperturaPanel.setPreferredSize(new Dimension(300,130));
		aperturaPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JLabel dateAperturaCajaLabel = new JLabel("Fecha y hora de Apertura :");
		
		dateAperturaCaja.setEditable(false);
		dateAperturaCaja.setPreferredSize(new Dimension(130,20));
		dateAperturaCaja.setText(sdf.format(new Date()));
		aperturaPanel.add(dateAperturaCajaLabel);
		aperturaPanel.add(dateAperturaCaja);
		
		JLabel montoAperturaLabel = new JLabel("Monto de Apertura :");
		montoApertura = new JTextField();
		montoApertura.setPreferredSize(new Dimension(100,20));
		montoApertura.setText("456,89");
		montoApertura.setEditable(false);
		aperturaPanel.add(montoAperturaLabel);
		aperturaPanel.add(montoApertura);
		
		JLabel cajeroLabel = new JLabel("Nombre de Usuario :");
		cajero = new JTextField();
		cajero.setPreferredSize(new Dimension(130,20));
		aperturaPanel.add(cajeroLabel);
		aperturaPanel.add(cajero);
		
		JLabel montoLabel = new JLabel("Monto :");
		monto = new JTextField();
		monto.setPreferredSize(new Dimension(130,20));
		aperturaPanel.add(montoLabel);
		aperturaPanel.add(monto);
		
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setPreferredSize(new Dimension(370,50));
		
		JPanel messagePanel = new JPanel();
		messagePanel.setPreferredSize(new Dimension(380, 100));
		messagePanel.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "Mensajes",
				TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, null, null));
		messagePanel.add(mensaje);
		
		
		mainPanel.add(aperturaPanel);
		mainPanel.add(buttonsPanel);
		mainPanel.add(messagePanel);
		
		getContentPane().add(mainPanel);
		
		JButton firmar = new JButton("Firmar");
		firmar.setPreferredSize(new Dimension(100,30));
		firmar.setMnemonic(KeyEvent.VK_F);
		firmar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				if( monto.getText().equals("")  || cajero.equals("")){
					JOptionPane.showMessageDialog(frame, "Debe Ingresar el monto de apertura y usuario (cajero) ");
				}else{
					//VALIDAR Q SEA NUMERICO
					try{
						Double.parseDouble(monto.getText());
						
						mensaje.setText("Esperando su huella digital");
						mensaje.setForeground(Color.red);
						
						//Aca se debe leer la huella digital y una vez leido  hacer el login
						String huellaDigital = "";
						Object params[] = new String[] { cajero.getText(), monto.getText(),dateAperturaCaja.getText(), huellaDigital};
						Document doc = TelefrontGUI.getInstance().executeMethod(CONTROLLER_CLASS, "login", params);
						if (doc != null) {
							setCurrentUser(doc);
						}
					
						FIRMA_APERTURA = true;
					
					}
					catch (NumberFormatException e) {
						mensaje.setText("El valor debe ser numérico");
						mensaje.setForeground(Color.red);
					}	
				}
			}
		});
		buttonsPanel.add(firmar);
	
		JButton cerrar = new JButton("Cancelar");
		cerrar.setPreferredSize(new Dimension(100,30));
		cerrar.setMnemonic(KeyEvent.VK_C);
		cerrar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				
				if(FIRMA_APERTURA){
					//CANCELAR LA FIRMA
					FIRMA_APERTURA  = false;
					mensaje.setText("Apertura cancelada");
					mensaje.setForeground(Color.red);
				}
			}
		});
		buttonsPanel.add(cerrar);
		JButton volverButton = new JButton("Volver");
		volverButton.setMnemonic(KeyEvent.VK_V);
		volverButton.setPreferredSize(new Dimension(100,30));
		buttonsPanel.add(volverButton);
		volverButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
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
		});
		getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER));
		setBounds(200, 100, 500, 400);
		
		setVisible(true);

	}
	
	/**
	 * Setea el usuario actual en el contexto de sesión del cliente.
	 * @param doc Representación XML del usuario.
	 */
	@SuppressWarnings("unchecked")
	public static void setCurrentUser(Document doc) {
		Element root = doc.getRootElement();
		
		//Setear todos los valores necesarios para el cajero
		
		Cajero user = new Cajero();

		Context.getInstance().setCurrentUser(user);
	}


}
