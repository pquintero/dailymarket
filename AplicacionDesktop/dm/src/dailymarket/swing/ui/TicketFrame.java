package dailymarket.swing.ui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.dom4j.Document;

import telefront.TelefrontGUI;

import dailymarket.model.Context;
import dailymarket.model.Sucursal;

public class TicketFrame extends JDialog {
	
	public JFrame parentFrame;
	
	String[] s;
	private static final String CONTROLLER_CLASS = "ar.com.tsoluciones.emergencies.server.gui.core.telefront.action.CajeroVentaManagerService";

	
	public TicketFrame(HuellaDigitalInterface frame){
		
		super((JFrame)frame, true);
		setAlwaysOnTop(true);
		parentFrame = (JFrame)frame;
		setTitle("Ticket de Cierre");
		setLocationRelativeTo((JFrame)frame);
		
		s = new String[11];
		s[0] = "T i c k e t  d e  C i e r r e";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		s[1] = "      ";
		
		Object params[] = new String[] { Configuration.getInstance().getSucursal() };
		Document doc = TelefrontGUI.getInstance().executeMethod(CONTROLLER_CLASS, "obtenerSucursal", params);

		Sucursal suc = new Sucursal();
		suc.toSucursalModel(doc);
		
		s[2] = suc.getNombre().toUpperCase();
		s[3] = suc.getDireccion().toUpperCase();
		
		JLabel fechaLabel = new JLabel("Fecha:                          " + sdf.format(new Date()));
		s[4] = fechaLabel.getText();
		
		JLabel cajaLabel = new JLabel("Caja: " );
		JTextField cajaTextField = new JTextField();
		cajaTextField.setPreferredSize(new Dimension(150,20));
		cajaTextField.setEditable(false);
		cajaTextField.setText(Configuration.getInstance().getCaja().toString());
		
		s[5] = cajaLabel.getText() + cajaTextField.getText();
		
		
		JLabel sucursalLabel = new JLabel("Sucursal: " );
		JTextField sucursalTextField = new JTextField();
		sucursalTextField.setPreferredSize(new Dimension(100,20));
		sucursalTextField.setEditable(false);
		sucursalTextField.setText(suc.getNombre());
		
		
		JLabel cajeroLabel = new JLabel("Cajero:       ");
		JTextField cajeroTextField = new JTextField();
		cajeroTextField.setPreferredSize(new Dimension(120,20));
		cajeroTextField.setEditable(false);
		cajeroTextField.setText(Context.getInstance().getCurrentUser().getName() + " " + Context.getInstance().getCurrentUser().getLastName());
		
		s[6] = cajeroLabel.getText()+ cajeroTextField.getText();
		
		
		JLabel montoFacturadoLabel = new JLabel("Monto Facturado: ");
		JTextField montoFacturadoTextField = new JTextField();
		montoFacturadoTextField.setPreferredSize(new Dimension(100,20));
		montoFacturadoTextField.setEditable(false);
		Double montoFac = Context.getInstance().getMontoCierrAcumulado() - Double.parseDouble(Configuration.getInstance().getMontoApertura());
		montoFac = Truncar(montoFac, 2);
		montoFacturadoTextField.setText(montoFac.toString());
		
		s[7] = montoFacturadoLabel.getText() + montoFacturadoTextField.getText();		
		
		JLabel montoInicioLabel = new JLabel("Monto Inicio: " );
		JTextField montoInicioTextField = new JTextField();
		montoInicioTextField.setPreferredSize(new Dimension(100,20));
		montoInicioTextField.setEditable(false);
		montoInicioTextField.setText( Configuration.getInstance().getMontoApertura());
		
		s[8] = montoInicioLabel.getText() + montoFacturadoTextField.getText();
		
		
		JLabel montoAEntregar = new JLabel("Monto a Entregar: " );
		JTextField montoAEntregarTextField = new JTextField();
		montoAEntregarTextField.setPreferredSize(new Dimension(100,20));
		montoAEntregarTextField.setEditable(false);
		montoAEntregarTextField.setText( Context.getInstance().getMontoCierrAcumulado().toString());		

		s[9] = montoAEntregar.getText() + montoAEntregarTextField.getText();

		
		s[10] = "----------------------------";
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setPreferredSize(new Dimension(270, 40));
		
		JButton imprimirButton = new JButton("Imprimir");
		imprimirButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imprimirTicket(s);
				dispose();	
			}
		});
		buttonPanel.add(imprimirButton);
		
		getContentPane().setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
		
		add(fechaLabel);
		add(cajaLabel);
		add(cajaTextField);
		add(sucursalLabel);
		add(sucursalTextField);
		add(cajeroLabel);
		add(cajeroTextField);
		add(montoFacturadoLabel);
		add(montoFacturadoTextField);
		add(montoInicioLabel);
		add(montoInicioTextField);
		add(montoAEntregar);
		add(montoAEntregarTextField);
		add(buttonPanel);
		
		JFrame.setDefaultLookAndFeelDecorated(true);
		setResizable(false);
		
		setBounds(300,150,250,320);
		setVisible(true);
	}
	private void imprimirTicket(String[] s) {
		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPrintable(new PrinterTicket(s));
		try {
			job.print();
		} catch (PrinterException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		new TicketFrame(null);
	   }
	private double Truncar(double nD, int nDec) {
		if (nD > 0)
			nD = Math.floor(nD * Math.pow(10, nDec)) / Math.pow(10, nDec);
		else
			nD = Math.ceil(nD * Math.pow(10, nDec)) / Math.pow(10, nDec);

		return nD;
	}

}
