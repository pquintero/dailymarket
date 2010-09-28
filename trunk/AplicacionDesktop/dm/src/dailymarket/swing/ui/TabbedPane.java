package dailymarket.swing.ui;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Vector;

public class TabbedPane extends JPanel {
	SupervisorFrame parentFrame;
	JScrollPane scrollProductsPane;
    protected DefaultTableModel tableModelSelecteds;
    protected JTable tableSelecteds = new JTable();
    protected Vector<Vector<String>> rowsProducts = new Vector<Vector<String>>();
    public JComponent panel1; 
	public JComponent panel2 ;
	public  JComponent panel3;
	
    public TabbedPane(DefaultTableModel tableModelProducts, SupervisorFrame supervisorFrame) {
       
    	parentFrame = supervisorFrame;
    	
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setPreferredSize(new Dimension(600, 200));
        
        panel1 = makeCancelarProductosPanel("", tableModelProducts);
        tabbedPane.addTab("Cancelar Productos", null, panel1,
                "Cancelacion de los pedidos seleccionados por el cajero");
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
        
        panel2 = makeDescuentosPanel("");
        tabbedPane.addTab("Otorgar Descuentos", null, panel2,
                "Otorga Descuentos a clientes empleados");
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
        
        panel3 = makeCancelarVentaPanel("panel para canelar");
        tabbedPane.addTab("Cancelar Venta", null, panel3,
                "Cancela sesion de Venta Actual");
        tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
        
        add(tabbedPane);
        
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
  
    }
    
    protected JComponent makeCancelarVentaPanel(String text) {
    	
		 JPanel cancelarVentaPanel = new JPanel(false);
		 cancelarVentaPanel.setPreferredSize(new Dimension(580,465));
		 cancelarVentaPanel.setPreferredSize(new Dimension(200, 530));
		 FlowLayout fl = new FlowLayout(FlowLayout.CENTER);
		 fl.setHgap(10);
		 fl.setVgap(20);
		 cancelarVentaPanel.setLayout(fl);
		 
		 JLabel motivoLabel = new JLabel("Motivo ");
		 JTextArea textArea = new JTextArea();
		 textArea.setPreferredSize(new Dimension(500,100));
		 textArea.setBorder(new LineBorder(Color.BLACK));
		 
		 cancelarVentaPanel.add(motivoLabel);
		 cancelarVentaPanel.add(textArea);
		 
		 JButton aceptarButton = new JButton("Firmar");
		 aceptarButton.setMnemonic(KeyEvent.VK_F);
		 aceptarButton.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
			}
		});
        cancelarVentaPanel.add(aceptarButton);
        
        
        JButton terminarButton = new JButton("Finalizar Y Volver");
        terminarButton.setMnemonic(KeyEvent.VK_V);
        terminarButton.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			parentFrame.dispose();
			}
		});
       cancelarVentaPanel.add(terminarButton);
    	
		
        return cancelarVentaPanel;
    }
    
    protected JComponent makeDescuentosPanel(String text) {
        
		 JPanel descuentosPanel = new JPanel(false);
		 descuentosPanel.setPreferredSize(new Dimension(200, 530));
		 
		 FlowLayout fl = new FlowLayout(FlowLayout.CENTER);
		 fl.setHgap(20);
		 fl.setVgap(30);
		 descuentosPanel.setLayout( fl);
         
		 JLabel empleadoLabel = new JLabel("Empleado");
		 JTextField empleado = new JTextField();
		 empleado.setPreferredSize(new Dimension(100,20));
		 
		 JButton firmaEmpleado = new JButton("Firma Empleado");
		 firmaEmpleado.setMnemonic(KeyEvent.VK_E);
		 JButton firmaSupervisor = new JButton("Autoriza supervisor");
		 firmaSupervisor.setMnemonic(KeyEvent.VK_S);
		 JButton terminar = new JButton("Finalizar y volver");
		 terminar.setMnemonic(KeyEvent.VK_V);
		 JPanel buttonPanel = new JPanel();
		 
		 buttonPanel.setPreferredSize(new Dimension(580, 50));
		 buttonPanel.add(firmaEmpleado);
		 buttonPanel.add(firmaSupervisor);
		 buttonPanel.add(terminar);
		 
		 
		 descuentosPanel.add(empleadoLabel);
		 descuentosPanel.add(empleado);
		 descuentosPanel.add(buttonPanel);
		 
        return descuentosPanel;
    }
    
    
    protected JComponent makeCancelarProductosPanel(String text, DefaultTableModel tableModelProducts) {
    	
        JPanel panel = new JPanel(false);
        
    	JButton aceptarButton = new JButton("Aceptar Seleccionados");
    	aceptarButton.setMnemonic(KeyEvent.VK_A);
    	aceptarButton.setPreferredSize(new Dimension(170,30));
    	
		//LISTA
		 //Lista de Relaciones
    	final JScrollPane scrollRelationsPane;

		scrollRelationsPane = new JScrollPane();
		scrollRelationsPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
	    scrollRelationsPane.setPreferredSize(new Dimension(580, 130));
	    tableSelecteds = new JTable();
	    scrollRelationsPane.setViewportView(tableSelecteds);
	    
	    tableModelSelecteds = new DefaultTableModel(){
           public boolean isCellEditable(int row, int column) {
               if (column == 0) {
                   return true;
               }
               return false;
           }
       };
       tableSelecteds.setModel(tableModelSelecteds);
       tableModelSelecteds.setDataVector(
               new Object[][] {},
               new Object[] { "Cancel","Descripcion", "Cantidad", "Precio", "Total" });
	    
       tableSelecteds.getColumnModel().getColumn(0).setPreferredWidth(3);
       tableSelecteds.getColumnModel().getColumn(1).setPreferredWidth(185);
       tableSelecteds.getColumnModel().getColumn(2).setPreferredWidth(3);
       tableSelecteds.getColumnModel().getColumn(3).setPreferredWidth(5);
       tableSelecteds.getColumnModel().getColumn(4).setPreferredWidth(5);
       tableSelecteds.setFont(new Font("Serif", Font.CENTER_BASELINE, 15));

	    panel.add(scrollRelationsPane);

	    //Agrego rows en true, las seleccionadas por el cajero
    	for(int i = 0 ; i < tableModelProducts.getRowCount(); i++ ){
    		if( (Boolean) tableModelProducts.getValueAt(i, 0)){
				tableModelSelecteds.addRow(new Object[]{ new Boolean(false),tableModelProducts.getValueAt(i, 1), tableModelProducts.getValueAt(i, 2),
						tableModelProducts.getValueAt(i, 3), tableModelProducts.getValueAt(i, 4)});
				
				tableSelecteds.getColumn("Cancel").setCellRenderer(new MultiRenderer());
				tableSelecteds.getColumn("Cancel").setCellEditor(new MultiEditor());
    		}
    	}
    	
        	
        aceptarButton.addActionListener(new ActionListener() {

        	public void actionPerformed(ActionEvent arg0) {
		
			parentFrame.dispose();
			
		}
	});
    	panel.add(aceptarButton);
       
        panel.setPreferredSize(new Dimension(600, 200));
        panel.setLayout( new FlowLayout(FlowLayout.CENTER));
       
        return panel;
    }
    

}