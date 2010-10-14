package dailymarket.swing.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;


@SuppressWarnings("serial")
public class CajaFrame  extends DailyMarketFrame{
	
	public JButton aperturaCajaButton = new JButton("Apertura de Caja");
	public JButton cierreCajaButton = new JButton("Cierre de Caja");
	public JButton nuevaSesionVentaButton = new JButton("Nueva Sesion de Venta");
	public JButton cerrarApp = new JButton("Cerrar Aplicación");
    JFrame frame ;
	
	public CajaFrame(){
	
		frame = this;
		setBounds(300, 100, 400, 400);
		setLayout(new FlowLayout(FlowLayout.CENTER));
		
		JPanel mainPanel = new JPanel();
		JPanel imagePanel = new JPanel();

		imagePanel.setPreferredSize(new Dimension(300,80));
		
		aperturaCajaButton = new JButton("Apertura de Caja");
		aperturaCajaButton.setPreferredSize(new Dimension(170,30));
		aperturaCajaButton.setMnemonic(KeyEvent.VK_A);
		aperturaCajaButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
			new AperturaCajaFrame(frame);
			frame.setVisible(false);
			}
		});
		
		cierreCajaButton = new JButton("Cierre de Caja");
		cierreCajaButton.setMnemonic(KeyEvent.VK_C);
		cierreCajaButton.setPreferredSize(new Dimension(170,30));
		cierreCajaButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
			new CerrarCajaFrame(frame);
			frame.setVisible(false);
			}
		});
		
		nuevaSesionVentaButton = new JButton("Nueva Sesion de Venta");
		nuevaSesionVentaButton.setPreferredSize(new Dimension(170,30));
		nuevaSesionVentaButton.setMnemonic(KeyEvent.VK_N);
		nuevaSesionVentaButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
			
				try {
					new CajeroVentaFrame(frame);
					dispose();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		cerrarApp = new JButton("Cerrar Aplicación");
		cerrarApp.setPreferredSize(new Dimension(170,30));
		cerrarApp.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
				dispose();
	
			}
		});

		mainPanel.setPreferredSize(new Dimension(200, 250));
		
		mainPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10,10));
		
		mainPanel.add(aperturaCajaButton);
		mainPanel.add(nuevaSesionVentaButton);
		mainPanel.add(cierreCajaButton);
		mainPanel.add(cerrarApp);
		
		JSeparator separador = new JSeparator();
		separador.setPreferredSize(new Dimension(150, 10));
		separador.setForeground(Color.BLACK);
		
		JLabel menuLabel = new JLabel("D A I L Y M A R K E T");
		menuLabel.setFont(new Font("Serif", Font.BOLD, 25));
		imagePanel.add(menuLabel);
		imagePanel.add(separador);

		
		getContentPane().add(imagePanel);
		getContentPane().add(mainPanel);
		
		setTitle(" Menu Principal ");
		setVisible(true);

	}

	public static void main(String[] args) {
		
		new CajaFrame();
	}

	protected void deshabilitarBotones(String[] disableComponents) {

		cierreCajaButton.setEnabled(true);
		aperturaCajaButton.setEnabled(true);
		cerrarApp.setEnabled(true);
		nuevaSesionVentaButton.setEnabled(true);

		if( disableComponents != null ){
			for(String s: disableComponents ){
				if( s.equals(DailyMarketFrame.CERRAR_CAJA))
					cierreCajaButton.setEnabled(false);
				
				if( s.equals(DailyMarketFrame.APERTURA_CAJA))
					aperturaCajaButton.setEnabled(false);
				
				if( s.equals(DailyMarketFrame.CERRAR_APLICACION))
					cerrarApp.setEnabled(false);
				
				if( s.equals(DailyMarketFrame.NUEVA_SESION))
					nuevaSesionVentaButton.setEnabled(false);
		   }
		}
		//SACAAAAAAAAAAAAAAAAR
		nuevaSesionVentaButton.setEnabled(true);
	}
		
	
}
