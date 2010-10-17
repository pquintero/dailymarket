package dailymarket.swing.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
		
		setLayout(new FlowLayout(FlowLayout.CENTER,0,60));
//		getContentPane().setBackground(Color.white);
		
		JPanel mainPanel = new JPanel();
		JPanel imagePanel = new JPanel();

//		mainPanel.setBackground(Color.white);
//		imagePanel.setBackground(Color.white);

		java.net.URL imgURL = InitDailyMarketFrame.class.getResource("logo.png");
		ImageIcon logoImg = new ImageIcon(imgURL);
					
		JLabel logoPicLabel = new JLabel(logoImg);
		imagePanel.add(logoPicLabel);
		
		setBounds(20,0, 843, 640);
		mainPanel.setPreferredSize(new Dimension(300, 500));
		imagePanel.setPreferredSize(new Dimension(400,500));
		
		aperturaCajaButton = new JButton("Apertura de Caja");
		aperturaCajaButton.setPreferredSize(new Dimension(230,64));
		aperturaCajaButton.setFont(new Font("SansSerif", Font.BOLD, 14));

		aperturaCajaButton.setMnemonic(KeyEvent.VK_A);
		Icon aperturaIcon = new ImageIcon("C:\\apertura.png");
		aperturaCajaButton.setIcon(aperturaIcon);
		aperturaCajaButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
			new AperturaCajaFrame(frame);
//			frame.setvisible(false);
			}
		});
		
		cierreCajaButton = new JButton("Cierre de Caja");
		cierreCajaButton.setMnemonic(KeyEvent.VK_C);
		cierreCajaButton.setFont(new Font("SansSerif", Font.BOLD, 14));
		cierreCajaButton.setIcon(aperturaIcon);
		cierreCajaButton.setPreferredSize(new Dimension(230,64));
		cierreCajaButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
			new CerrarCajaFrame(frame);
//			frame.setVisible(false);
			}
		});
		
		nuevaSesionVentaButton = new JButton("  Nueva Venta");
		nuevaSesionVentaButton.setFont(new Font("SansSerif", Font.BOLD, 14));

		nuevaSesionVentaButton.setPreferredSize(new Dimension(230,64));
		Icon carritoIcon = new ImageIcon("C:\\carrito.png");
		nuevaSesionVentaButton.setIcon(carritoIcon);
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
		Icon cerrarIcon = new ImageIcon("C:\\exit.png");
		cerrarApp.setIcon(cerrarIcon);
		cerrarApp.setFont(new Font("SansSerif", Font.BOLD, 14));
		cerrarApp.setPreferredSize(new Dimension(230,64));
		cerrarApp.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				Object[] options = {"Si","No"};
				int n = JOptionPane.showOptionDialog(frame,"¿Realmente quiere salir de DailyMarket?"," DailyMarket",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,     
				options,  
				options[0]);
				if(n == 0){
					System.exit(0);
					dispose();
				}
	
			}
		});

		
		mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 40,40));
		
		mainPanel.add(aperturaCajaButton);
		mainPanel.add(nuevaSesionVentaButton);
		mainPanel.add(cierreCajaButton);
		mainPanel.add(cerrarApp);
		
		JSeparator separador = new JSeparator();
		separador.setPreferredSize(new Dimension(150, 10));
		separador.setForeground(Color.BLACK);
		
		JLabel menuLabel = new JLabel("D A I L Y M A R K E T");
		menuLabel.setFont(new Font("Serif", Font.BOLD, 25));
		
		JLabel gestionLabel = new JLabel("G e s t i ó n   E f e c t i v a   d e   S u p e r m e r c a d o s ");
		gestionLabel.setFont(new Font("Serif", Font.BOLD, 15));
		
		imagePanel.setLayout( new FlowLayout(FlowLayout.CENTER, 0, 25));
		imagePanel.add(menuLabel);
		imagePanel.add(separador);
		imagePanel.add(gestionLabel);
		
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
