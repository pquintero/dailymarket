package dailymarket.swing.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;


public class SupervisorFrame extends JDialog {
	JFrame parentFrame ;

	public SupervisorFrame(DefaultTableModel tableModelProducts, JFrame frame){
		super(frame,true);
		parentFrame = frame;
		TabbedPane solicitudesTabbedPane =/* new TabbedPane(tableModelProducts, this)*/ new TabbedPane(tableModelProducts, this);
        
        add(solicitudesTabbedPane, BorderLayout.CENTER);
        setBounds(100,100,650,370);
        setLayout(new FlowLayout(FlowLayout.CENTER));
        
        
        JPanel mensajesPanel = new JPanel();
		mensajesPanel.setPreferredSize(new Dimension(600,70));
		mensajesPanel.setBorder(new TitledBorder(null, "Mensajes",
				TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, Color.BLACK));
		
		JLabel mensaje = new JLabel();
		mensaje.setForeground(Color.RED);
		mensaje.setText("Ingrese su huella digital para cerrar la caja");
        
		mensajesPanel.add(mensaje);
		
		add(mensajesPanel);
		setVisible(true);
		setAlwaysOnTop(true);
		setTitle("Solicitudes a Supervisor");
		
	}
	

	public static void  main(String[] arg){
	}
	
	
	
}