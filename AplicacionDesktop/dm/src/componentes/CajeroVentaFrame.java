package componentes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import javax.swing.table.DefaultTableModel;

import dailymarket.model.Caja;
import dailymarket.model.Cajero;
import dailymarket.model.LineaTicket;
import dailymarket.model.Sucursal;
import dailymarket.model.Ticket;
public class CajeroVentaFrame extends DailyMarketFrame {
	
	protected static final int COLUMNA_CHECK_BOX = 0;
	protected static final int COLUMNA_PRECIO_TOTAL = 5;
    protected Vector<Vector<String>> rowsProducts = new Vector<Vector<String>>();
    protected DefaultTableModel tableModelProducts;
    protected JTable tableRelations = new JTable();

    protected String cajaNumber = "1";//EL VALOR es PARAMETRO DEL CONSTRUCTOR
    
    protected JTextField scanCodProducto;
    protected Double totalVenta = new Double(0);
    protected Double subTotalVenta = new Double(0);
    public Double pagoVenta = new Double(0);

    
    protected JTextField totalVentaTextField = new JTextField();
    protected JTextField subtotalVentaTextfield = new JTextField();
    protected JTextField descProducto = new JTextField();

    protected JTextField cantProd = new JTextField();
    protected SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    protected JFrame frame;
	protected JButton imprimirTicket ;
    JButton finVentaButton ;
    JButton nuevaVentaButton;
    JButton modificarSesionVenta ;
    JButton salirButton ;
    JFrame parentFrame;
	final JScrollPane scrollRelationsPane;
    JLabel message = new JLabel();


	
	public CajeroVentaFrame(JFrame p) throws IOException {

		parentFrame = p;
		JPanel mainPanel = new JPanel();
		JPanel footerPanel = new JPanel();
		JPanel listPanel = new JPanel();
		JPanel ventaProductoPanel = new JPanel();
		JPanel productoPanel = new JPanel();
		
		JPanel headerPanel = new JPanel();
		JPanel dailyMarkeyPanel = new JPanel();
		JPanel cajaPanel = new JPanel();
		JPanel vendedorPanel = new JPanel();
		
		frame = this;
		
		productoPanel.setBackground(new Color(0xCCCCFF));
		mainPanel.setBackground(Color.gray);
		
		listPanel.setOpaque(true);
		
		final FlowLayout flowLayout_6 = new FlowLayout();
		flowLayout_6.setVgap(0);
		flowLayout_6.setHgap(0);
		flowLayout_6.setAlignment(FlowLayout.LEADING);
		
		mainPanel.setLayout(flowLayout_6);
		mainPanel.setBounds(0, 0, 950, 730);
		
		listPanel.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "Lista de Productos",
				TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, null, Color.BLACK));
		listPanel.setPreferredSize(new Dimension(600, 500));
		
	    ventaProductoPanel.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "Venta",
				TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, null, null));
		ventaProductoPanel.setPreferredSize(new Dimension(330, 500));
		
		//Image PAnel
		JPanel imagePanel = new JPanel();
		imagePanel.setPreferredSize(new Dimension(200,155));
		ventaProductoPanel.add(imagePanel);
		
		//ProductoPanel
		productoPanel.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "",
				TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, null, null));
		
		productoPanel.setPreferredSize(new Dimension(300, 120));
		ventaProductoPanel.add(productoPanel);
		final FlowLayout fl2 = new FlowLayout();
		fl2.setAlignment(FlowLayout.LEFT);
		productoPanel.setLayout(fl2);
		
		JLabel codigoProducto = new JLabel("Codigo Producto ");
		productoPanel.add(codigoProducto);
		
		scanCodProducto = new JTextField();
		scanCodProducto.setPreferredSize(new Dimension(120, 20));
	    scanCodProducto.addActionListener(new ActionListener() {

			
			public void actionPerformed(ActionEvent e) {
			
			
				//TRAER PRODUCTO DE LA BASE DE DATOS Y SETEARLO AL TICKET..   
				tableModelProducts.addRow(new Object[]{ new Boolean(false),"1", "Pepsi", new Integer(2), new Double(7.8), new Double(8.5)});
				
				cantProd.setText("1");
				descProducto.setText("PepsiCola 2,15 Lt");
				//
				
				tableRelations.getColumn("Cancel").setCellRenderer(new MultiRenderer());
			    tableRelations.getColumn("Cancel").setCellEditor(new MultiEditor());
				
				
				subTotalVenta += new Double(8.5);
				subtotalVentaTextfield.setText(subTotalVenta.toString());
			}
		});
	    			    
		productoPanel.add(scanCodProducto);
		
		JLabel descProductoLabel = new JLabel("Descripcion ");
		productoPanel.add(descProductoLabel);
		
		descProducto = new JTextField();
		descProducto.setPreferredSize(new Dimension(180,20));
		descProducto.setEditable(false);
		productoPanel.add(descProducto);
		
		JLabel cantProdLabel = new JLabel("Cantidad       ");
		productoPanel.add(cantProdLabel);
		
		cantProd = new JTextField();
		cantProd.setPreferredSize(new Dimension(45, 20));
		productoPanel.add(cantProd);
		
		JButton ingresarProdButton = new JButton("Ingresar Producto Manualmente");
		productoPanel.add(ingresarProdButton);
		ingresarProdButton.setMnemonic(KeyEvent.VK_I);
		ingresarProdButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				scanCodProducto.setText("");
				descProducto.setText("");
				cantProd.setText("");
			}
		});
		
		//TICKET PANEL
		JPanel ticketPanel = new JPanel();
		ticketPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		ticketPanel.setPreferredSize(new Dimension(300,180));
		ticketPanel.setBackground(new Color(0xCCCCFF));
		
		ventaProductoPanel.add(ticketPanel);
		ticketPanel.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "",
				TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
		
		JLabel nroTicketLabel = new JLabel("Nro Ticket ");
		ticketPanel.add(nroTicketLabel);
		
		JTextField nroTicket = new JTextField();
		nroTicket.setPreferredSize(new Dimension(180, 20));
		nroTicket.setEditable(false);
		ticketPanel.add(nroTicket);
		
		JLabel subtotalVentaLabel = new JLabel("SubTotal       ");
		subtotalVentaLabel.setFont(new Font("Serif", Font.BOLD, 20));
		subtotalVentaLabel.setForeground(Color.BLACK);
		ticketPanel.add(subtotalVentaLabel);
		
		subtotalVentaTextfield = new JTextField();
		subtotalVentaTextfield.setFont(new Font("Serif", Font.BOLD, 25));
		subtotalVentaTextfield.setForeground(Color.BLACK);
		subtotalVentaTextfield.setBackground(new Color(0xFFFF99));
		subtotalVentaTextfield.setEditable(false);
		subtotalVentaTextfield.setPreferredSize(new Dimension(140,40));
		subtotalVentaTextfield.setText(  subTotalVenta.toString());
		ticketPanel.add(subtotalVentaTextfield);
		
		JLabel totalVentaLabel = new JLabel("TOTAL ");
		totalVentaLabel.setFont(new Font("Serif", Font.BOLD, 30));
		totalVentaLabel.setForeground(Color.BLACK);
		ticketPanel.add(totalVentaLabel);
		
		totalVentaTextField = new JTextField();
		totalVentaTextField.setFont(new Font("Serif", Font.BOLD, 30));
		totalVentaTextField.setBackground(new Color(0xFFFF99));
		totalVentaTextField.setForeground(Color.BLACK);
		totalVentaTextField.setEditable(false);
		totalVentaTextField.setPreferredSize(new Dimension(140,40));
		totalVentaTextField.setText(totalVenta.toString());
		
		ticketPanel.add(totalVentaTextField);
	
		imprimirTicket = new JButton("IMPRIMIR TICKET");
		imprimirTicket.setEnabled(false);
		ticketPanel.add(imprimirTicket);
		imprimirTicket.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				//armar el ticket, lineas de 28 car
				String[] s;
				s = makeTicket(null);
				 
				imprimirTicket(s);
				
			}

			private String[] makeTicket(Ticket t) {

				Sucursal suc = new Sucursal();
				suc.setCuit("23-94237098-9");
				suc.setDireccion("Suipacha 234");
				suc.setNombre("Lider_Oriental");
				suc.setTelefono("4982-7021");
				
				Cajero cajero = new Cajero();
				cajero.setApellido("Gimenez");
				cajero.setLegajo("15-489892");
				cajero.setNombre("Patricia");
				
				Caja caja = new Caja();
				caja.setCajero(cajero);
				caja.setNroCaja("1");
				caja.setSucursal(suc);
				
				LineaTicket linea1 = new LineaTicket();
				linea1.setDescripcion("Coca_Cola_x_2Lts");
				linea1.setCantidad("6");
				linea1.setPrecioUnitario("8");
				linea1.setPrecioTotal("32");
		
				LineaTicket linea2 = new LineaTicket();
				linea2.setDescripcion("Galletitas_Costa_Chocolate_x_400_gramos");
				linea2.setCantidad("1");
				linea2.setPrecioUnitario("3");
				linea2.setPrecioTotal("3");
		
				List<LineaTicket> lista = new ArrayList<LineaTicket>();
				
				lista.add(linea1);
				lista.add(linea2);
				
				Ticket ticket = new Ticket();
				ticket.setCaja(caja);
				ticket.setNroTicket("000001");
				ticket.setLineas(lista);
				ticket.setTotal("35");
				ticket.setSubtotal("35");
				
				int cantFija = 15;//HEADER y FOOTER DEL TICKETñ
				int cantDinamica = ticket.getLineas().size();
				
				String lineaTicket[] = new String[/*cantFija + cantDinamica*/8];
				
				lineaTicket[0]= ticket.getCaja().getSucursal().getNombre().toUpperCase();
				lineaTicket[1]= ticket.getCaja().getSucursal().getDireccion().toUpperCase() ;
				lineaTicket[2]= ".";
				lineaTicket[3]= "EMPLEADO:" + ticket.getCaja().getCajero().getLegajo() + " - " + ticket.getCaja().getCajero().getNombre();
				lineaTicket[4]= "CAJA:" + ticket.getCaja().getNroCaja();
				lineaTicket[5]= "CUIT-NRO: " + ticket.getCaja().getSucursal().getCuit().toUpperCase();
				lineaTicket[6]= "NRO-TICKET: " + ticket.getNroTicket().toUpperCase();
				lineaTicket[7]= "FECHA: " + (new Date()).toString().toUpperCase()  ;
				
				
				return lineaTicket;
			}
		
			private void imprimirTicket(String[] s) {
				PrinterJob job = PrinterJob.getPrinterJob();
				
				job.setPrintable(new PrinterTicket(s));
				
						try {
							job.print();
						} catch (PrinterException e) {
							message.setText(e.getMessage());
							e.printStackTrace();
						}
						}
					
			
			
		});
		JButton calcularTotal = new JButton("Calcular Total");
		calcularTotal.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
			
				totalVentaTextField.setText("345");

			}
		});
		ticketPanel.add(calcularTotal);
		
		footerPanel.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "",
				TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
		footerPanel.setPreferredSize(new Dimension(930, 90));
	
		headerPanel.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "",
				TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, Color.BLACK));
		headerPanel.setPreferredSize(new Dimension(930, 80));
		flowLayout_6.setVgap(5);
		headerPanel.setLayout(flowLayout_6);
		
		dailyMarkeyPanel.setPreferredSize(new Dimension(450, 60));
		headerPanel.add(dailyMarkeyPanel);
		
		cajaPanel.setPreferredSize(new Dimension(260,60));
		cajaPanel.setLayout(new BoxLayout(cajaPanel, WIDTH));
		headerPanel.add(cajaPanel);
		
		//======VENDEDORPANEL
		
		java.net.URL imgURL = InitDailyMarketFrame.class.getResource("images/vendedor.jpg");
		ImageIcon vendedorImg = new ImageIcon(imgURL);
		JLabel vendedorPicLabel = new JLabel(new ImageIcon(vendedorImg.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
 
		
		vendedorPanel.add(vendedorPicLabel);
		vendedorPanel.setPreferredSize(new Dimension(200,60));
		final FlowLayout flowLayout_1 = new FlowLayout();
		flowLayout_1.setVgap(2);
		flowLayout_1.setHgap(2);
		flowLayout_1.setAlignment(FlowLayout.LEADING);
		
		vendedorPanel.setLayout(flowLayout_1);
		headerPanel.add(vendedorPanel);
		
		JLabel nombreVendedorLabel = new JLabel();
		nombreVendedorLabel.setText(" Claudia Gimenez");
		vendedorPanel.add(nombreVendedorLabel);
		
		//DAILYMARKET PANEL
		dailyMarkeyPanel.setLayout(new FlowLayout());
		
		JLabel supermercadoLabel = new JLabel();
		supermercadoLabel.setText("------SUPER LIDER ORIENTAL---------");
		supermercadoLabel.setFont(new Font("Serif", Font.BOLD, 20));
		supermercadoLabel.setForeground(Color.BLUE);
		dailyMarkeyPanel.add(supermercadoLabel);
		
		JLabel datosSuperLabel = new JLabel();
		datosSuperLabel.setFont(new Font("Serif", Font.ROMAN_BASELINE, 11));
		datosSuperLabel.setForeground(Color.BLACK);
		datosSuperLabel.setText("Av. Rivadavia 4567 - 011-4982-2340");
		dailyMarkeyPanel.add(datosSuperLabel);
		
		//CAJA PANEL
		JLabel cajaLabel = new JLabel();
		cajaLabel.setText("CAJA: " + cajaNumber);
		cajaLabel.setFont(new Font("Serif", Font.BOLD, 18));
		cajaLabel.setForeground(Color.BLACK);
		cajaPanel.add(cajaLabel);
		
		JLabel vendedorLabel = new JLabel();
		vendedorLabel.setText("Cajero Leg: " + "1045");
		cajaPanel.add(vendedorLabel);
		
		JLabel fechaLabel = new JLabel();
		
		fechaLabel.setText(sdf.format(new Date()));
		cajaPanel.add(fechaLabel);
		
		mainPanel.add(headerPanel);
		mainPanel.add(listPanel);
		mainPanel.add(ventaProductoPanel);
		mainPanel.add(footerPanel);

		java.net.URL imgURLprod = InitDailyMarketFrame.class.getResource("images/pepsi.jpg");
		ImageIcon productoImg = new ImageIcon(imgURLprod);
				
		JLabel picLabel = new JLabel(new ImageIcon(productoImg.getImage().getScaledInstance(180, 140, Image.SCALE_SMOOTH)));
		imagePanel.add(picLabel);
		
		getContentPane().add(mainPanel);
		
		//LISTA
		 //Lista de Relaciones
		scrollRelationsPane = new JScrollPane();
		scrollRelationsPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
	    scrollRelationsPane.setPreferredSize(new Dimension(580, 465));
    	tableRelations = new JTable();
	    scrollRelationsPane.setViewportView(tableRelations);
	    
	    tableModelProducts = new DefaultTableModel(){
            public boolean isCellEditable(int row, int column) {
                if (column == 0) {
                	scanCodProducto.requestFocusInWindow();
                    return true;
                }
                return false;
            }
        };
	    tableRelations.setModel(tableModelProducts);
        tableModelProducts.setDataVector(
                new Object[][] {},
                new Object[] { "Cancel", "Item", "Descripcion", "Cantidad", "Precio", "Total" });
	    
        tableRelations.getColumnModel().getColumn(0).setPreferredWidth(3);
        tableRelations.getColumnModel().getColumn(1).setPreferredWidth(3);
        tableRelations.getColumnModel().getColumn(2).setPreferredWidth(185);
        tableRelations.getColumnModel().getColumn(3).setPreferredWidth(3);
        tableRelations.getColumnModel().getColumn(4).setPreferredWidth(5);
        tableRelations.getColumnModel().getColumn(5).setPreferredWidth(5);
        tableRelations.setFont(new Font("Serif", Font.CENTER_BASELINE, 15));

	    listPanel.add(scrollRelationsPane);
	    
	    //FOOTER PANEL
	    footerPanel.setLayout(new FlowLayout(1, 8, 8));
	    JPanel mensajesPanel = new JPanel();
	    JPanel accionesPanel = new JPanel();
	    
	    footerPanel.add(mensajesPanel);
	    footerPanel.add(accionesPanel);
	    
	    message = new JLabel();
	    message.setText("No existe el producto de codigo 12324");
	    message.setForeground(Color.red);
		

	    mensajesPanel.add(message);
	    mensajesPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
	    mensajesPanel.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "Mensajes",
				TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, null, null));
	    
	    mensajesPanel.setPreferredSize(new Dimension(600,70));
	    accionesPanel.setPreferredSize(new Dimension(300,70));
	    
	    modificarSesionVenta = new JButton("Modificar Venta");
	    modificarSesionVenta.setPreferredSize(new Dimension(130,25));
	    modificarSesionVenta.setMnemonic(KeyEvent.VK_M);
	    modificarSesionVenta.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
//				new ModificacionSesionVentaFrame(frame);	
//				scanCodProducto.requestFocus();
//				
				new SupervisorFrame(tableModelProducts , frame);
			}
		});
	    accionesPanel.add(modificarSesionVenta);
	    
	    nuevaVentaButton = new JButton("Nueva Venta");
	    nuevaVentaButton.setPreferredSize(new Dimension(130,25));
	    nuevaVentaButton.setMnemonic(KeyEvent.VK_N);
	    nuevaVentaButton.setEnabled(false);
	    
	    nuevaVentaButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				
				//LO hago asi? o limpio todo lo anterior?
				try {
					new CajeroVentaFrame(parentFrame);
				} catch (IOException e) {
					e.printStackTrace();
				}
				dispose();
				
			
			}
		});
	    accionesPanel.add(nuevaVentaButton);
	    
	    finVentaButton = new JButton("Fin Venta");
	    finVentaButton.setPreferredSize(new Dimension(130,25));
	    finVentaButton.setMnemonic(KeyEvent.VK_F);
	    finVentaButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				totalVentaTextField.setText("345");

				
		      new FinVentaFrame(frame, Double.parseDouble(totalVentaTextField.getText()));
			
				
			}
		});
	    accionesPanel.add(finVentaButton);
	    
	    salirButton = new JButton("Salir");
	    salirButton.setEnabled(false);
	    salirButton.setPreferredSize(new Dimension(130,25));
	    salirButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				String [] disabledButtons = new String[2];
				disabledButtons[0] = DailyMarketFrame.APERTURA_CAJA;
				disabledButtons[1] = DailyMarketFrame.CERRAR_APLICACION;
				
				((CajaFrame)parentFrame).deshabilitarBotones(disabledButtons);
				parentFrame.setVisible(true);
				
				dispose();
				
			}
		});
	    accionesPanel.add(salirButton);
	    
		//-------------------MENUBAR
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Menu");
		menu.setMnemonic(KeyEvent.VK_M);
		menu.getAccessibleContext().setAccessibleDescription(
		        "The only menu in this program that has menu items");
		menuBar.add(menu);
		
		//a group of JMenuItems
		JMenuItem menuItem = new JMenuItem("A text-only menu item",KeyEvent.VK_T);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");
		menu.add(menuItem);
		
		//a submenu
		menu.addSeparator();
		JMenu submenu = new JMenu("subMenu");
		submenu.setMnemonic(KeyEvent.VK_S);

		menuItem = new JMenuItem("item en el subMenu");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.ALT_MASK));
		submenu.add(menuItem);

		menuItem = new JMenuItem("otro item");
		submenu.add(menuItem);
		menu.add(submenu);
		
		setBounds(20,0, 943, 740);
		setTitle("DailyMarket - VENTA ");

		setJMenuBar(menuBar);
		scanCodProducto.requestFocusInWindow();
		setVisible(true);

	}	
	public void habilitarImprimirVenta(){
		
		imprimirTicket.setEnabled(true);
		finVentaButton.setEnabled(false);
		nuevaVentaButton.setEnabled(true);
		modificarSesionVenta.setEnabled(false);
		salirButton.setEnabled(true);
		
	}
	public static void main(String[] args) throws IOException, SQLException{
		new CajeroVentaFrame(null);
	   }
}


