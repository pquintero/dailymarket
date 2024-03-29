package dailymarket.swing.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.dom4j.Document;
import org.dom4j.Element;

import telefront.TelefrontGUI;
import dailymarket.model.Context;
import dailymarket.model.ProductModel;


public class FinVentaFrame extends JDialog {
	public JFrame parentFrame;
	public Double montoVenta;
	public JTextField montoPagoVenta ;
	public JLabel mensaje = new JLabel();
	public boolean finVenta = false;
	
	private static final String CONTROLLER_CLASS = "ar.com.tsoluciones.emergencies.server.gui.core.telefront.action.CajeroVentaManagerService";

	
	public FinVentaFrame(JFrame frame, Double monto) {
		
		super(frame, false);
		montoVenta = monto;
		parentFrame = frame;
		setTitle("Fin de Sesion de Venta");
		setBounds(200, 200, 350, 250);
		setAlwaysOnTop(true);
	
		getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER));
		
		JLabel finVenta = new JLabel("          Ingrese el monto del pago en efectivo ");
		finVenta.setPreferredSize(new Dimension(300,20));
     	JDialog.setDefaultLookAndFeelDecorated(true);
		
		JLabel montoLabel = new JLabel("Monto: ");
		montoLabel.setFont(new Font("Serif", Font.CENTER_BASELINE, 15));
		montoPagoVenta = new JTextField();
		montoPagoVenta.setPreferredSize(new Dimension(100, 25));
		
		JButton aceptarButton = new JButton("Aceptar");
		aceptarButton.addActionListener(new AceptarButtonAction());
		JButton cancelarButton = new JButton("Cancelar");
		cancelarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		
		JPanel panelButtons = new JPanel();
		panelButtons.setPreferredSize(new Dimension(300, 40));
		panelButtons.add(aceptarButton);
		panelButtons.add(cancelarButton);
		
		 JPanel mensajesPanel = new JPanel();
		 mensajesPanel.setPreferredSize(new Dimension(300,70));
		 mensajesPanel.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "Mensajes",
					TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, null, null));
		    					
		mensaje.setForeground(Color.GRAY);
	    mensajesPanel.add(mensaje);
		
		getContentPane().add(finVenta);
		getContentPane().add(montoLabel);
		getContentPane().add(montoPagoVenta);
		getContentPane().add(panelButtons);
		getContentPane().add(mensajesPanel);
		setVisible(true);
		setResizable(false);
		
	}
	class AceptarButtonAction implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			
			if(montoPagoVenta.getText().equals("")){
				mensaje.setText("Debe ingresar el pago para terminar la venta");
				mensaje.setForeground(Color.red);
			}else{
				try {
					Double montoPago = Double.parseDouble(montoPagoVenta.getText());
					if(montoVenta.doubleValue() > montoPago.doubleValue() ){
						mensaje.setText("El Pago no supera el monto de la venta");
						mensaje.setForeground(Color.red);
					}
						else{
							List<String> productCode = new ArrayList<String>();
							for( ProductModel  pm :((CajeroVentaFrame)parentFrame).getProductos()){
								for(int i = 0 ; i< pm.getCantidad(); i++)
								  productCode.add(pm.getCode());
							}
							
							String codigos = concatenarCodigos(productCode);
							Object params[] = new Object[] {Configuration.getInstance().getCaja(),Context.getInstance().getCurrentUser().getUser(),codigos,((CajeroVentaFrame)parentFrame).totalVenta.toString()};
				            Document doc = TelefrontGUI.getInstance().executeMethod(CONTROLLER_CLASS, "guardarSesionVenta", params);
							if(doc != null){
								((CajeroVentaFrame)parentFrame).pagoVenta =  montoPago;
								((CajeroVentaFrame)parentFrame).habilitarImprimirVenta();
								
								//Se actualiza el monto de cierre de la caja sumando al acumulado cada venta
								double montoAcumulado = Context.getInstance().getMontoCierrAcumulado().doubleValue() + montoVenta.doubleValue();
								Context.getInstance().setMontoCierrAcumulado(montoAcumulado);
								
								Element root = doc.getRootElement();
								Long id = Long.valueOf(root.selectSingleNode("sesion").getStringValue());
								((CajeroVentaFrame)parentFrame).idSesionVenta =  id;
								
								dispose();	
							}else{
								mensaje.setText("No se pudo registrar la venta");
								mensaje.setForeground(Color.red);
							}
									
						}
				} catch (NumberFormatException e) {
					mensaje.setText("El valor debe ser num�rico");
					mensaje.setForeground(Color.red);

				}
				
			}
		}

		private String concatenarCodigos(List<String> productsCode) {
			String codigos = "";
			String separador = "-";

			for (Iterator iterator = productsCode.iterator(); iterator
					.hasNext();) {
				String codigo = (String) iterator.next();
				codigos += codigo;
				codigos += separador;
			}
			return codigos;
		}
	}

}
