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

import dailymarket.model.Context;

public class TicketFrame extends JFrame {
	
	public JFrame parentFrame;
	
	String[] s;
	
	public TicketFrame(HuellaDigitalInterface frame){
		
//		super((JFrame)frame, true);
		setAlwaysOnTop(true);
		parentFrame = (JFrame)frame;
		setTitle("Ticket de Cierre");
		setLocationRelativeTo((JFrame)frame);
		
		s = new String[10];
		s[0] = "T i c k e t  d e  C i e r r e";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		JLabel fechaLabel = new JLabel("Fecha:         " + sdf.format(new Date()));
		s[1] = fechaLabel.getText();
		
		JLabel fechaAperturaLabel = new JLabel("Hora de Apertura: " );
		JTextField fechaAperturaTextField = new JTextField();
		fechaAperturaTextField.setPreferredSize(new Dimension(100,20));
		fechaAperturaTextField.setEditable(false);
		fechaAperturaTextField.setText("08:34");
		
		s[2] = fechaAperturaLabel.getText() + fechaAperturaTextField.getText();
		
		
		JLabel fechaCierreLabel = new JLabel("Hora de Cierre: " );
		JTextField fechaCierreTextField = new JTextField();
		fechaCierreTextField.setPreferredSize(new Dimension(100,20));
		fechaCierreTextField.setEditable(false);
		fechaCierreTextField.setText("12:34");
		
		s[3] = fechaCierreLabel.getText() + fechaCierreTextField.getText();
		
		
		JLabel cajeroLabel = new JLabel("Cajero: ");
		JTextField cajeroTextField = new JTextField();
		cajeroTextField.setPreferredSize(new Dimension(120,20));
		cajeroTextField.setEditable(false);
		cajeroTextField.setText(Context.getInstance().getCurrentUser().getName() + " " + Context.getInstance().getCurrentUser().getLastName());
		
		s[4] = cajeroLabel.getText()+ cajeroTextField.getText();
		
		
		JLabel montoFacturadoLabel = new JLabel("Monto Facturado: ");
		JTextField montoFacturadoTextField = new JTextField();
		montoFacturadoTextField.setPreferredSize(new Dimension(100,20));
		montoFacturadoTextField.setEditable(false);
		montoFacturadoTextField.setText("1500");
		
		s[5] = montoFacturadoLabel.getText() + montoFacturadoTextField.getText();		
		
		JLabel montoInicioLabel = new JLabel("Monto Inicio: " );
		JTextField montoInicioTextField = new JTextField();
		montoInicioTextField.setPreferredSize(new Dimension(100,20));
		montoInicioTextField.setEditable(false);
		montoInicioTextField.setText("500");
		
		s[6] = montoInicioLabel.getText() + montoFacturadoTextField.getText();
		
		
		JLabel montoAEntregar = new JLabel("Monto a Entregar: " );
		JTextField montoAEntregarTextField = new JTextField();
		montoAEntregarTextField.setPreferredSize(new Dimension(100,20));
		montoAEntregarTextField.setEditable(false);
		montoAEntregarTextField.setText("2000");		

		s[7] = montoAEntregar.getText() + montoAEntregarTextField.getText();

		
		s[8] = "-----------------";
		s[9] = fechaLabel.getText();
		
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
		add(fechaAperturaLabel);
		add(fechaAperturaTextField);
		add(fechaCierreLabel);
		add(fechaCierreTextField);
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
//     	this.setDefaultCloseOperation(0);
//	    this.addWindowListener(new WindowAdapter() {});
	    
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
	


}
