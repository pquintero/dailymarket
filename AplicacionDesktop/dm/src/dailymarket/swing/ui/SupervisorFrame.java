package dailymarket.swing.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import dailymarket.lectorDeHuellas.LectorDeHuellasFirstLogin;
import dailymarket.model.ProductModel;



public class SupervisorFrame extends  DailyMarketFrame implements HuellaDigitalInterface {
	CajeroVentaFrame parentFrame ;
	JPanel imageHuellaPanel = new JPanel();
	protected JLabel imgHuella = new JLabel();
	JLabel mensaje = new JLabel();
	public static final String  CANCELAR_VENTA  = "cancelar_venta";
	public static final String  CANCELAR_PRODUCTOS  = "cancelar_productos";
	public static final String OTORGAR_DESCUENTOS_EMP = "otorgar_descuentos_emp";
	public static final String OTORGAR_DESCUENTOS_SUP = "otorgar_descuentos_sup";

	private String actualAction = "";
	JLabel passwordLabel = new JLabel("Password :");
    protected JPasswordField passwordTextField = new JPasswordField();
	TabbedPane solicitudesTabbedPane ;
    JButton altaHuellaButton = new JButton("Alta de Huella");
    HuellaDigitalInterface thisFrame ;
	
	JLabel mensajeLector = new JLabel();
	JPanel altaHuellaPanel = new JPanel();
	JCheckBox primerLogueoCheck = new JCheckBox("Primer Logueo");
	JTextField supervisorTextField = new JTextField();
	JLabel supervisorLabel = new JLabel("Usuario");

	public SupervisorFrame(DefaultTableModel tableModelProducts, CajeroVentaFrame frame, List<ProductModel> products){
		
		parentFrame = frame;
		solicitudesTabbedPane = new TabbedPane(tableModelProducts, this, mensajeLector, imgHuella, products, frame);
        thisFrame = this;
        setLocationRelativeTo(parentFrame);
        add(solicitudesTabbedPane, BorderLayout.CENTER);
        setBounds(50,100,830,360);
        setLayout(new FlowLayout(FlowLayout.LEFT ));
        setAlwaysOnTop(true);
        
        JPanel fingerPrintPanel = new JPanel();
        fingerPrintPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        fingerPrintPanel.setPreferredSize(new Dimension(185,200));
        
        imageHuellaPanel.setPreferredSize(new Dimension(180,125));
		imageHuellaPanel.setLayout( new GridBagLayout());
		imageHuellaPanel.setBorder(new TitledBorder(new LineBorder(Color.GRAY), "Huella",
				TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, null, Color.BLACK));
		imageHuellaPanel.remove(imgHuella);

		fingerPrintPanel.add(imageHuellaPanel);
		
		add(fingerPrintPanel);
		
        JPanel mensajesPanel = new JPanel();
		mensajesPanel.setPreferredSize(new Dimension(690,60));
		mensajesPanel.setBorder(new TitledBorder(null, "Mensajes",
				TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, Color.BLACK));
		
		mensajesPanel.add(mensaje);
		
		JPanel messageFingerPrintPanel = new JPanel();
		messageFingerPrintPanel.setPreferredSize(new Dimension(180,60));
		messageFingerPrintPanel.setBorder(new TitledBorder(new LineBorder(Color.GRAY), "Eventos del Lector",
				TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, null, null));
		messageFingerPrintPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		messageFingerPrintPanel.add(mensajeLector);
		
		
		altaHuellaPanel.setPreferredSize(new Dimension(790, 40));
		altaHuellaPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
		altaHuellaPanel.setBackground(new Color(0xCCCCFF));
		altaHuellaPanel.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "",
				TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, null, null));
		
	
		supervisorTextField.setPreferredSize(new Dimension(100,20));
		altaHuellaPanel.add(supervisorLabel);
		altaHuellaPanel.add(supervisorTextField);
		altaHuellaPanel.add(passwordLabel);
		altaHuellaPanel.add(passwordTextField);
		altaHuellaPanel.add(altaHuellaButton);
		altaHuellaPanel.setVisible(false);
		
		add(altaHuellaPanel);
		add(mensajesPanel);
		
		primerLogueoCheck.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
			
				if(primerLogueoCheck.isSelected()){
					solicitudesTabbedPane.deshabilitarFirmas();
					altaHuellaPanel.setVisible(true);
				}else{
					solicitudesTabbedPane.habilitarFirma();
					altaHuellaPanel.setVisible(false);
				}
			}
		});
		
		add(primerLogueoCheck);
		passwordTextField.setPreferredSize(new Dimension(100,20));
	
        altaHuellaButton.setMnemonic(KeyEvent.VK_H);
        altaHuellaButton.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if( passwordTextField.getText().equals("") || supervisorTextField.getText().equals("")  ){
					JOptionPane.showMessageDialog((Component) thisFrame, "Ingrese su usuario y password");
				}else{
					LectorDeHuellasFirstLogin utilHuellasFirstLogin = new LectorDeHuellasFirstLogin();
					utilHuellasFirstLogin.start(mensaje);
					utilHuellasFirstLogin.init(thisFrame);
					altaHuellaButton.setEnabled(false);
					passwordTextField.setEnabled(false);
				}
			}
		});
		
		fingerPrintPanel.add(messageFingerPrintPanel);

		setVisible(true);
		setResizable(false);
		setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		setTitle("Solicitudes a Supervisor");
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
	public String getMonto() {
		return null;
	}

	public String getUserName() {
		return supervisorTextField.getText();
	}

	public String getUserPassword() {
		return passwordTextField.getText();
	}

	public void loguear() {
		solicitudesTabbedPane.aceptarButton.setEnabled(true);
		altaHuellaPanel.setVisible(false);
	}
	
	public void setActualAction(String s){
		actualAction = s;
	}
	public String getActualAction(){
		return actualAction;
	}
	public void doFirstLogin() {
		altaHuellaPanel.setVisible(true);
	}

	public void altaDeHuella() {
		JOptionPane.showMessageDialog(this, "La password ingresada es incorrecta, re intente nuevamente");
		passwordTextField.setEnabled(true);
		altaHuellaButton.setEnabled(true);
	}

	public void habilitarFirma() {
		solicitudesTabbedPane.habilitarFirma();
		primerLogueoCheck.setEnabled(true);
		mensaje.setText("Reintente nuevamente");
	}

	public String getMotivoDeCancelacion() {
		return solicitudesTabbedPane.getMotivoDeCancelacion();
	}

	public void doCancelProducts() {
		solicitudesTabbedPane.doCancelProducts();
		dispose();		
	}

	public void otorgarDescuento() {
		((CajeroVentaFrame)parentFrame).otorgarDescuento();

	}

	public void empleadoValidated() {
		solicitudesTabbedPane.empleadoValidado();		
	}

	public void supervisorValidated() {
		solicitudesTabbedPane.supervisorValidado();		
	}

	public void habilitarFirmaEmpleado() {
		solicitudesTabbedPane.firmaEmpleado.setEnabled(true);
		
	}

	public void habilitarFirmaSupervisor() {
		solicitudesTabbedPane.firmaSupervisor.setEnabled(true);
	}

	public void cancelarVenta() {
		((CajeroVentaFrame)parentFrame).cancelarVenta();
		
	}
}