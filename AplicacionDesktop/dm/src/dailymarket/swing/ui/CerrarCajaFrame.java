package dailymarket.swing.ui;

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

public class CerrarCajaFrame extends DailyMarketFrame {

	JLabel mensaje = new JLabel();
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    protected JTextField montoCierre = new JTextField();
    protected boolean FIRMA_CIERRE = false;
	JFrame parentFrame ;
    
	public CerrarCajaFrame(JFrame frame){
		
		parentFrame = frame;
		setBounds(200, 100, 500, 400);
		setLayout(new FlowLayout(FlowLayout.CENTER));
		
		JPanel mainPanel = new JPanel();
		mainPanel.setPreferredSize(new Dimension(400, 200));
		mainPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JLabel cajeroLabel = new JLabel("Cajero :");
		JTextField cajero = new JTextField();
		cajero.setEditable(false);
		cajero.setPreferredSize(new Dimension(200,20));
		cajero.setText("Ottaviano, Gabriel Ignacio");
	
		mainPanel.add(cajeroLabel);
		mainPanel.add(cajero);
		
		JLabel dateAperturaCajaLabel = new JLabel("Fecha y hora de apertura :");
		JTextField dateAperturaCaja = new JTextField();
		dateAperturaCaja.setEditable(false);
		dateAperturaCaja.setPreferredSize(new Dimension(200,20));
		dateAperturaCaja.setText(sdf.format(new Date()));
		mainPanel.add(dateAperturaCajaLabel);
		mainPanel.add(dateAperturaCaja);
		
		JLabel dateCierreCajaLabel = new JLabel("Fecha y hora de cierre :");
		JTextField dateCierreCaja = new JTextField();
		dateCierreCaja.setEditable(false);
		dateCierreCaja.setPreferredSize(new Dimension(200,20));
		dateCierreCaja.setText(sdf.format(new Date()));
		mainPanel.add(dateCierreCajaLabel);
		mainPanel.add(dateCierreCaja);
		
		JLabel montoAperturaLabel = new JLabel("Monto de Apertura :");
		JTextField montoApertura = new JTextField();
		montoApertura.setEditable(false);
		montoApertura.setPreferredSize(new Dimension(200,20));
		montoApertura.setText("456,89");
		mainPanel.add(montoAperturaLabel);
		mainPanel.add(montoApertura);
		
		JLabel montoCierreLabel = new JLabel("Monto de Cierre :");
		montoCierre = new JTextField();
		montoCierre.setPreferredSize(new Dimension(200,20));
		mainPanel.add(montoCierreLabel);
		mainPanel.add(montoCierre);
		
		JButton firmar = new JButton("Firmar");
		firmar.setPreferredSize(new Dimension(100,30));
		firmar.setMnemonic(KeyEvent.VK_F);
		firmar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				if( montoCierre.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Debe Ingresar el monto de cierre ");
				}else{
					//VALIDAR Q SEA NUMERICO
					mensaje.setText("Esperando su huella digital");
					mensaje.setForeground(Color.red);
				
					FIRMA_CIERRE = true;
					
				}
			}
		});
		mainPanel.add(firmar);

		JButton cerrar = new JButton("Cancelar");
		cerrar.setPreferredSize(new Dimension(100,30));
		cerrar.setMnemonic(KeyEvent.VK_C);
		cerrar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				
				//CANCELAR LA FIRMA
				
				String [] disabledButtons = new String[2];
				disabledButtons[0] = DailyMarketFrame.APERTURA_CAJA;
				disabledButtons[1] = DailyMarketFrame.CERRAR_APLICACION;
				
				((CajaFrame)parentFrame).deshabilitarBotones(disabledButtons);
				parentFrame.setVisible(true);
				
			}
		});
		
		cerrar.setEnabled(false);
		mainPanel.add(cerrar);
		
		JButton volver = new JButton("Volver");
		volver.setMnemonic(KeyEvent.VK_V);
		volver.setPreferredSize(new Dimension(100,30));
		volver.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				
				String [] disabledButtons = new String[2];
				if(FIRMA_CIERRE){
					disabledButtons[0] = DailyMarketFrame.CERRAR_CAJA;
					disabledButtons[1] = DailyMarketFrame.NUEVA_SESION;
				}else{
					disabledButtons[0] = DailyMarketFrame.APERTURA_CAJA;
					disabledButtons[1] = DailyMarketFrame.CERRAR_APLICACION;
				}
				
				((CajaFrame)parentFrame).deshabilitarBotones(disabledButtons);
				parentFrame.setVisible(true);
				dispose();
				
			}
		});
		
		mainPanel.add(volver);
		
		JPanel mensajesPanel = new JPanel();
		mensajesPanel.setPreferredSize(new Dimension(390,100));
		mensajesPanel.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "Mensajes",
				TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, null, null));
		
		mensaje = new JLabel();
		mensaje.setForeground(Color.red);
		mensaje.setText("Ingrese su huella digital para cerrar la caja");
		
		getContentPane().add(mainPanel);
		mensajesPanel.add(mensaje);
		getContentPane().add(mensajesPanel);		
		setTitle("Cerrar  Caja ");
		setVisible(true);

	}
}
