package dailymarket.swing.ui;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import dailymarket.lectorDeHuellas.UtilLectorHuellasSingleton;
import dailymarket.model.ProductModel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class TabbedPane extends JPanel {
	JScrollPane scrollProductsPane;
    protected DefaultTableModel tableModelSelecteds;
    protected JTable tableSelecteds = new JTable();
    protected Vector<Vector<String>> rowsProducts = new Vector<Vector<String>>();
    public JComponent panel1; 
	public JComponent panel2 ;
	public  JComponent panel3;
	public static boolean EMPLEADO_VALIDADO = false; 
	public static boolean SUPERVISOR_VALIDADO = false; 
	
	protected JLabel imgHuella = new JLabel();
	JLabel mensajeLector = new JLabel();
	
	HuellaDigitalInterface frameParent;
	JButton aceptarButton = new JButton("Firmar");
	
	JButton firmaEmpleado = new JButton("Firma Empleado");
	JButton firmaSupervisor = new JButton("Firma Supervisor");

	JTextArea textArea = new JTextArea();
	JButton aceptarSeleccionadosButton = new JButton("Aceptar Seleccionados");

	DefaultTableModel tableModelProducts;
    private List <ProductModel> productos = new ArrayList<ProductModel>();
    private Double subTotal;
	
    public TabbedPane(DefaultTableModel productsList, HuellaDigitalInterface supervisorFrame, JLabel mensajeLector, JLabel imgHuella, List<ProductModel> products, Double subTotalVenta) {
         frameParent = supervisorFrame;
         tableModelProducts = productsList;
        
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setPreferredSize(new Dimension(600, 200));
        
        panel1 = makeCancelarProductosPanel("");
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
        productos = products;
        subTotal = subTotalVenta;
        
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
		 textArea.setPreferredSize(new Dimension(500,100));
		 textArea.setBorder(new LineBorder(Color.BLACK));
		 
		 cancelarVentaPanel.add(motivoLabel);
		 cancelarVentaPanel.add(textArea);
		 
		 aceptarButton.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					((SupervisorFrame)frameParent).getFrameMensaje().setText("Ingrese su huella digital para realizar la operación");
					((SupervisorFrame)frameParent).setActualAction(SupervisorFrame.CANCELAR_VENTA);
					UtilLectorHuellasSingleton utilHuellas = new UtilLectorHuellasSingleton();
					utilHuellas.start(mensajeLector);
					utilHuellas.initLogin(frameParent);
					aceptarButton.setEnabled(false);
					((SupervisorFrame)frameParent).primerLogueoCheck.setEnabled(false);
			}
		});
        cancelarVentaPanel.add(aceptarButton);
        
        JButton terminarButton = new JButton("Finalizar Y Volver");
        terminarButton.setMnemonic(KeyEvent.VK_V);
        terminarButton.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			((SupervisorFrame)frameParent).dispose();	
			}
		});
        
        cancelarVentaPanel.add(terminarButton);    	
		
        return cancelarVentaPanel;
    }
    
    protected JComponent makeDescuentosPanel(String text) {
		 JPanel descuentosPanel = new JPanel(false);
		 descuentosPanel.setPreferredSize(new Dimension(200, 530));
		 
		 FlowLayout fl = new FlowLayout(FlowLayout.CENTER);

		 descuentosPanel.setLayout( fl);
		 
		java.net.URL imgURL = InitDailyMarketFrame.class.getResource("lector.gif");
		ImageIcon lectorImg = new ImageIcon(imgURL);
		JLabel lectorImgLabel = new JLabel(new ImageIcon(lectorImg.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH)));
	 
		descuentosPanel.add(lectorImgLabel);
		descuentosPanel.add(new JLabel(" D E S C U E N T O S     A     E M P L E A D O S"));
    	 firmaEmpleado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((SupervisorFrame)frameParent).getFrameMensaje().setText("Sr Empleado apoye su dedo en el lector de huellas digitales");
				UtilLectorHuellasSingleton lector = new UtilLectorHuellasSingleton();  
				((SupervisorFrame)frameParent).setActualAction(SupervisorFrame.OTORGAR_DESCUENTOS_EMP);
				lector.start(mensajeLector);
				lector.initLogin(frameParent);
				firmaEmpleado.setEnabled(false);
				((SupervisorFrame)frameParent).primerLogueoCheck.setEnabled(false);
			}
		});
		 
		 firmaEmpleado.setMnemonic(KeyEvent.VK_E);
		 
		 firmaSupervisor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				((SupervisorFrame)frameParent).getFrameMensaje().setText("Sr Supervisor apoye su dedo en el lector de huellas digitales");
				UtilLectorHuellasSingleton lector = new UtilLectorHuellasSingleton();
        		((SupervisorFrame)frameParent).setActualAction(SupervisorFrame.OTORGAR_DESCUENTOS_SUP);
        		lector.start(mensajeLector);
				lector.initLogin(frameParent);
				firmaSupervisor.setEnabled(false);
				((SupervisorFrame)frameParent).primerLogueoCheck.setEnabled(false);
			}
		});
		 
		  JButton terminarButton = new JButton("Finalizar Y Volver");
	        terminarButton.setMnemonic(KeyEvent.VK_V);
	        terminarButton.addActionListener( new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
				((SupervisorFrame)frameParent).dispose();	
				}
			});
		 
		 JPanel buttonPanel = new JPanel();
		 
		 buttonPanel.setPreferredSize(new Dimension(580, 50));
		 buttonPanel.add(firmaSupervisor);
		 buttonPanel.add(firmaEmpleado);
		 buttonPanel.add(terminarButton);
		 
		 descuentosPanel.add(buttonPanel);
		 
        return descuentosPanel;
    }
    
    
    protected JComponent makeCancelarProductosPanel(String text) {
    	
        JPanel panel = new JPanel(false);
        
        aceptarSeleccionadosButton.setMnemonic(KeyEvent.VK_A);
        aceptarSeleccionadosButton.setPreferredSize(new Dimension(170,30));
    	
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
               new Object[] { "Cancel","Descripcion", "Cantidad", "Precio", "Total","nroRow" });
	    
       tableSelecteds.getColumnModel().getColumn(1).setPreferredWidth(185);
       tableSelecteds.getColumnModel().getColumn(2).setPreferredWidth(3);
       tableSelecteds.getColumnModel().getColumn(3).setPreferredWidth(5);
       tableSelecteds.getColumnModel().getColumn(4).setPreferredWidth(5);
       tableSelecteds.getColumnModel().getColumn(5).setPreferredWidth(0);

       tableSelecteds.setFont(new Font("Serif", Font.CENTER_BASELINE, 15));

	    panel.add(scrollRelationsPane);

	    //Agrego rows en true, las seleccionadas por el cajero
    	for(int i = 0 ; i < tableModelProducts.getRowCount(); i++ ){
    		if( (Boolean) tableModelProducts.getValueAt(i, 0)){
    			tableModelSelecteds.addRow(new Object[]{ new Boolean(false),tableModelProducts.getValueAt(i, 1), tableModelProducts.getValueAt(i, 2),
				tableModelProducts.getValueAt(i, 3), tableModelProducts.getValueAt(i, 4), i});
				
				tableSelecteds.getColumn("Cancel").setCellRenderer(new MultiRenderer());
				tableSelecteds.getColumn("Cancel").setCellEditor(new MultiEditor());
    		}
    	}
    	
        	
    	aceptarSeleccionadosButton.addActionListener(new ActionListener() {

        	public void actionPerformed(ActionEvent arg0) {
        		((SupervisorFrame)frameParent).getFrameMensaje().setText("Ingrese su huella digital para realizar la operación");
        		((SupervisorFrame)frameParent).setActualAction(SupervisorFrame.CANCELAR_PRODUCTOS);
        		((SupervisorFrame)frameParent).primerLogueoCheck.setEnabled(false);
				UtilLectorHuellasSingleton utilHuellas = new UtilLectorHuellasSingleton();
				
				utilHuellas.start(mensajeLector);
				utilHuellas.initLogin(frameParent);
				aceptarSeleccionadosButton.setEnabled(false);
			}
		});
	  	  JButton terminarButton = new JButton("Finalizar Y Volver");
	      terminarButton.setMnemonic(KeyEvent.VK_V);
	      terminarButton.addActionListener( new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
				((SupervisorFrame)frameParent).dispose();	
				}
			});
	  
    	panel.add(aceptarSeleccionadosButton);
    	panel.add(terminarButton);
       
        panel.setPreferredSize(new Dimension(600, 200));
        panel.setLayout( new FlowLayout(FlowLayout.CENTER));
       
        return panel;
    }

	public void habilitarFirma() {
		aceptarButton.setEnabled(true);
		aceptarSeleccionadosButton.setEnabled(true);
		firmaEmpleado.setEnabled(true);
		firmaSupervisor.setEnabled(true);
	}

	public String getMotivoDeCancelacion() {
		return textArea.getText();
	}

	public void doCancelProducts() {

		int cantCancel = 0;
		for(int i = 0 ; i < tableModelSelecteds.getRowCount(); i++ ){
    		if( (Boolean) tableModelSelecteds.getValueAt(i, 0) ){

    		tableModelProducts.removeRow( new Integer(((String)tableModelSelecteds.getValueAt(i, 5).toString())) - cantCancel);
    		ProductModel pm = productos.get(new Integer(((String)tableModelSelecteds.getValueAt(i, 5).toString())) - cantCancel);
    		subTotal -= pm.getPrice();
    		
			productos.remove(new Integer(((String)tableModelSelecteds.getValueAt(i, 5).toString())) - cantCancel);
			
    		tableSelecteds.getColumn("Cancel").setCellRenderer(new MultiRenderer());
			tableSelecteds.getColumn("Cancel").setCellEditor(new MultiEditor());
			cantCancel++;	
    		}
    	}
		
		
	}

	public void deshabilitarFirmas() {
		aceptarButton.setEnabled(false);
		aceptarSeleccionadosButton.setEnabled(false);
		firmaEmpleado.setEnabled(false);
		firmaSupervisor.setEnabled(false);
		
	}

	public void empleadoValidado() {
		EMPLEADO_VALIDADO = true;
		if(SUPERVISOR_VALIDADO){
			((SupervisorFrame)frameParent).otorgarDescuento();	
			((SupervisorFrame)frameParent).getFrameMensaje().setText("Descuento Otorgado");
		}
	}

	public void supervisorValidado() {
		SUPERVISOR_VALIDADO = true;
		if(EMPLEADO_VALIDADO){
			((SupervisorFrame)frameParent).otorgarDescuento();	
			((SupervisorFrame)frameParent).getFrameMensaje().setText("Descuento Otorgado");
		}
	}
}