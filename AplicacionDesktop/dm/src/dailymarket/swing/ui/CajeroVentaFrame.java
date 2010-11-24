package dailymarket.swing.ui;

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
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import javax.swing.table.DefaultTableModel;

import org.dom4j.Document;

import telefront.TelefrontGUI;

import dailymarket.model.Caja;
import dailymarket.model.Context;
import dailymarket.model.Empleado;
import dailymarket.model.LineaTicket;
import dailymarket.model.ProductModel;
import dailymarket.model.Sucursal;
import dailymarket.model.Ticket;


public class CajeroVentaFrame extends DailyMarketFrame {

	private static final String CONTROLLER_CLASS = "ar.com.tsoluciones.emergencies.server.gui.core.telefront.action.CajeroVentaManagerService";
	private static final Double DESCUENTO_EMPLEADO = new Double(0.85); // DEscuento
	protected static final int COLUMNA_CHECK_BOX = 0;
	protected static final int COLUMNA_PRECIO_TOTAL = 5;
	protected Vector<Vector<String>> rowsProducts = new Vector<Vector<String>>();
	protected DefaultTableModel tableModelProducts;
	protected JTable tableRelations = new JTable();

	private List<String> productsCode = new ArrayList<String>();
	private List<ProductModel> productos = new ArrayList<ProductModel>();

	protected String cajaNumber = Configuration.getInstance().getCaja();
	protected JTextField scanCodProducto;
	protected Double totalVenta = new Double(0);
	protected Double subTotalVenta = new Double(0);
	public Double pagoVenta = new Double(0);
	public Long idSesionVenta;

	protected JTextField totalVentaTextField = new JTextField();
	protected JTextField subtotalVentaTextfield = new JTextField();
	protected JTextField descProducto = new JTextField();

	protected JTextField cantProd = new JTextField();
	protected SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	protected JFrame frame;
	protected JButton imprimirTicket;
	JButton finVentaButton;
	JButton nuevaVentaButton;
	JButton modificarSesionVenta;
	JButton salirButton;
	JFrame parentFrame;
	final JScrollPane scrollRelationsPane;
	JLabel message = new JLabel();
	Empleado user = Context.getInstance().getCurrentUser();
	private boolean OTORGAR_DESCUENTO = false;

	JPanel imagePanel = new JPanel();
	JLabel picLabel = new JLabel();
	JPanel ventaProductoPanel = new JPanel();

	JPanel listPanel = new JPanel();
	JButton ingresarProdButton = new JButton("Ingresar Producto Manualmente") ;
	JTextField vueltoTextField = new JTextField();

	
	public CajeroVentaFrame(JFrame p) throws IOException {

		parentFrame = p;
		JPanel mainPanel = new JPanel();
		JPanel footerPanel = new JPanel();
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
		setAlwaysOnTop(true);

		listPanel.setBorder(new TitledBorder(new LineBorder(Color.BLACK),
				"Lista de Productos", TitledBorder.CENTER,
				TitledBorder.DEFAULT_POSITION, null, Color.BLACK));
		listPanel.setPreferredSize(new Dimension(600, 500));

		ventaProductoPanel.setBorder(new TitledBorder(new LineBorder(
				Color.BLACK), "Venta", TitledBorder.CENTER,
				TitledBorder.DEFAULT_POSITION, null, null));
		ventaProductoPanel.setPreferredSize(new Dimension(330, 500));

		// Image PAnel
		imagePanel.setPreferredSize(new Dimension(200, 155));
		ventaProductoPanel.add(imagePanel);

		// ProductoPanel
		productoPanel.setBorder(new TitledBorder(new LineBorder(Color.BLACK),
				"", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, null,
				null));

		productoPanel.setPreferredSize(new Dimension(300, 120));
		ventaProductoPanel.add(productoPanel);
		final FlowLayout fl2 = new FlowLayout();
		fl2.setAlignment(FlowLayout.LEFT);
		productoPanel.setLayout(fl2);

		JLabel codigoProducto = new JLabel("Código Producto ");
		productoPanel.add(codigoProducto);

		scanCodProducto = new JTextField();
		scanCodProducto.setPreferredSize(new Dimension(120, 20));
		scanCodProducto.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				agregarProductoSesion();
			}
		});

		productoPanel.add(scanCodProducto);

		JLabel descProductoLabel = new JLabel("Descripción ");
		productoPanel.add(descProductoLabel);

		descProducto = new JTextField();
		descProducto.setPreferredSize(new Dimension(180, 20));
		descProducto.setEditable(false);
		productoPanel.add(descProducto);

		JLabel cantProdLabel = new JLabel("Cantidad       ");
		productoPanel.add(cantProdLabel);

		cantProd = new JTextField();
		cantProd.setPreferredSize(new Dimension(45, 20));
		cantProd.setText("1");
		productoPanel.add(cantProd);

				
		productoPanel.add(ingresarProdButton);
		ingresarProdButton.setMnemonic(KeyEvent.VK_I);
		ingresarProdButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				agregarProductoSesion();
			}
		});

		// TICKET PANEL
		JPanel ticketPanel = new JPanel();
		ticketPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		ticketPanel.setPreferredSize(new Dimension(300, 180));
		ticketPanel.setBackground(new Color(0xCCCCFF));

		ventaProductoPanel.add(ticketPanel);
		ticketPanel.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));



		JLabel subtotalVentaLabel = new JLabel("SubTotal       ");
		subtotalVentaLabel.setFont(new Font("Serif", Font.BOLD, 20));
		subtotalVentaLabel.setForeground(Color.BLACK);
		ticketPanel.add(subtotalVentaLabel);

		subtotalVentaTextfield = new JTextField();
		subtotalVentaTextfield.setFont(new Font("Serif", Font.BOLD, 25));
		subtotalVentaTextfield.setForeground(Color.BLACK);
		subtotalVentaTextfield.setBackground(new Color(0xFFFF99));
		subtotalVentaTextfield.setEditable(false);
		subtotalVentaTextfield.setPreferredSize(new Dimension(140, 40));
		subtotalVentaTextfield.setText(subTotalVenta.toString());
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
		totalVentaTextField.setPreferredSize(new Dimension(140, 40));
		totalVentaTextField.setText(totalVenta.toString());

		ticketPanel.add(totalVentaTextField);
		
		JLabel vueltoLabel = new JLabel("Su Vuelto      ");
		vueltoLabel.setFont(new Font("Serif", Font.BOLD, 20));
		vueltoLabel.setForeground(Color.BLACK);
		ticketPanel.add(vueltoLabel);

		vueltoTextField.setFont(new Font("Serif", Font.BOLD, 25));
		vueltoTextField.setForeground(Color.BLACK);
		vueltoTextField.setBackground(new Color(0xFFFF99));
		vueltoTextField.setEditable(false);
		vueltoTextField.setPreferredSize(new Dimension(140, 40));
		vueltoTextField.setText(subTotalVenta.toString());
		 
		vueltoTextField.setText(" ");
		ticketPanel.add(vueltoTextField);
		
		imprimirTicket = new JButton("Imprimir Ticket");
		imprimirTicket.setEnabled(false);
		ticketPanel.add(imprimirTicket);
		imprimirTicket.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				// armar el ticket, lineas de 28 car
				String[] s;
				s = makeTicket(null);

				imprimirTicket(s);

			}

			private String[] makeTicket(Ticket t) {

				Object params[] = new String[] { Configuration.getInstance().getSucursal() };
				Document doc = TelefrontGUI.getInstance().executeMethod(CONTROLLER_CLASS, "obtenerSucursal", params);

				Sucursal suc = new Sucursal();
				suc.toSucursalModel(doc);

				Empleado cajero = Context.getInstance().getCurrentUser();

				Caja caja = new Caja();
				caja.setCajero(cajero);
				caja.setNroCaja(Configuration.getInstance().getCaja());
				caja.setSucursal(suc);

				Vector productos = tableModelProducts.getDataVector();
				List<LineaTicket> lista = new ArrayList<LineaTicket>();

				for (int i = 0; i < productos.size(); i++) {
					LineaTicket linea = new LineaTicket();
					Vector producto = (Vector) productos.get(i);
					linea.setDescripcion(producto.get(1).toString());
					linea.setCantidad(producto.get(2).toString());
					linea.setPrecioUnitario(producto.get(3).toString());
					linea.setPrecioTotal(producto.get(4).toString());
					lista.add(linea);
				}

				Ticket ticket = new Ticket();
				ticket.setCaja(caja);
				ticket.setNroTicket(idSesionVenta.toString());
				ticket.setLineas(lista);
				ticket.setTotal(totalVenta.toString());
				ticket.setSubtotal(subTotalVenta.toString());

				int cantFija =  OTORGAR_DESCUENTO ? 15 + 1:  15 + 0 ;// HEADER y FOOTER DEL TICKET
				int cantDinamica = ticket.getLineas().size();

				String lineaTicket[] = new String[cantFija + cantDinamica];

				lineaTicket[0] = ticket.getCaja().getSucursal().getNombre().toUpperCase();
				lineaTicket[1] = ticket.getCaja().getSucursal().getDireccion().toUpperCase();
				lineaTicket[2] = "    ";
				lineaTicket[3] = "Empleado: "+ ticket.getCaja().getCajero().getLastName() + ", " + ticket.getCaja().getCajero().getName();
				lineaTicket[4] = "Caja:" + ticket.getCaja().getNroCaja();
				lineaTicket[5] = "CUIT-NRO: "+ ticket.getCaja().getSucursal().getCuit().toUpperCase();
				lineaTicket[6] = "Nro-Ticket: "+ ticket.getNroTicket().toUpperCase();
				SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				lineaTicket[7] = "Fecha: " + sdf.format(new Date());
				lineaTicket[8] = "					";

				int i = 9;
				for (Iterator iterator = ticket.getLineas().iterator(); iterator
						.hasNext();) {
					LineaTicket linea = (LineaTicket) iterator.next();
					lineaTicket[i] = linea.toString();
					i++;
				}

				lineaTicket[i++] = "				";
				lineaTicket[i++] = "SubTotal $" + ticket.getSubtotal();
				if( OTORGAR_DESCUENTO ){
					Double desc = Truncar(((new Double(1) - DESCUENTO_EMPLEADO )*subTotalVenta),2);
					lineaTicket[i++] = "Descuento -$" + desc.toString() ;
				}
				lineaTicket[i++] = "-------------------------------";
				lineaTicket[i++] = "TOTAL $" + ticket.getTotal() ;
				lineaTicket[i++] = "   ";
				lineaTicket[i++] = "Su Vuelto $" + Truncar((pagoVenta - totalVenta),2) ;

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


		footerPanel.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "",TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		footerPanel.setPreferredSize(new Dimension(930, 90));

		headerPanel.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "",TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, Color.BLACK));
		headerPanel.setPreferredSize(new Dimension(930, 80));
		flowLayout_6.setVgap(5);
		headerPanel.setLayout(flowLayout_6);

		dailyMarkeyPanel.setPreferredSize(new Dimension(450, 60));
		headerPanel.add(dailyMarkeyPanel);

		cajaPanel.setPreferredSize(new Dimension(260, 60));
		cajaPanel.setLayout(new BoxLayout(cajaPanel, WIDTH));
		headerPanel.add(cajaPanel);

		// ======VENDEDORPANEL

		ImageIcon vendedorImg = null;
		if(Context.getInstance().getCurrentUser().getFoto()!=null){
			vendedorImg = new ImageIcon(Context.getInstance().getCurrentUser().getFoto());
		}else{
			java.net.URL imgURL = InitDailyMarketFrame.class
				.getResource("sinImg.jpg");
			vendedorImg = new ImageIcon(imgURL);
		}
		JLabel vendedorPicLabel = new JLabel(new ImageIcon(vendedorImg
				.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));

		vendedorPanel.add(vendedorPicLabel);
		vendedorPanel.setPreferredSize(new Dimension(200, 60));
		final FlowLayout flowLayout_1 = new FlowLayout();
		flowLayout_1.setVgap(2);
		flowLayout_1.setHgap(2);
		flowLayout_1.setAlignment(FlowLayout.LEADING);

		vendedorPanel.setLayout(flowLayout_1);
		headerPanel.add(vendedorPanel);

		JLabel nombreVendedorLabel = new JLabel();

		 nombreVendedorLabel.setText(Context.getInstance().getCurrentUser().getName()
		 + " " + Context.getInstance().getCurrentUser().getLastName());
		vendedorPanel.add(nombreVendedorLabel);

		// DAILYMARKET PANEL
		dailyMarkeyPanel.setLayout(new FlowLayout());

		JLabel supermercadoLabel = new JLabel();
		supermercadoLabel.setText("------------DAILYMARKET----------------");
		supermercadoLabel.setFont(new Font("Serif", Font.BOLD, 20));
		supermercadoLabel.setForeground(Color.BLUE);
		dailyMarkeyPanel.add(supermercadoLabel);

		JLabel datosSuperLabel = new JLabel();
		datosSuperLabel.setFont(new Font("Serif", Font.ROMAN_BASELINE, 11));
		datosSuperLabel.setForeground(Color.BLACK);
		datosSuperLabel.setText("Av. Rivadavia 4567 - 011-4982-2340");
		dailyMarkeyPanel.add(datosSuperLabel);

		// CAJA PANEL
		JLabel cajaLabel = new JLabel();
		cajaLabel.setText("CAJA: " + cajaNumber);
		cajaLabel.setFont(new Font("Serif", Font.BOLD, 18));
		cajaLabel.setForeground(Color.BLACK);
		cajaPanel.add(cajaLabel);

		JLabel vendedorLabel = new JLabel();

		vendedorLabel.setText("Cajero DNI: " +
		Context.getInstance().getCurrentUser().getDni());
		cajaPanel.add(vendedorLabel);

		JLabel fechaLabel = new JLabel();

		fechaLabel.setText(sdf.format(new Date()));
		cajaPanel.add(fechaLabel);

		mainPanel.add(headerPanel);
		mainPanel.add(listPanel);
		mainPanel.add(ventaProductoPanel);
		mainPanel.add(footerPanel);

	
		imagePanel.add(picLabel);

		getContentPane().add(mainPanel);

		// LISTA
		scrollRelationsPane = new JScrollPane();
		scrollRelationsPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		scrollRelationsPane.setPreferredSize(new Dimension(580, 465));
		tableRelations = new JTable();
		scrollRelationsPane.setViewportView(tableRelations);

		tableModelProducts = new DefaultTableModel() {
			public boolean isCellEditable(int row, int column) {
				if (column == 0) {
					scanCodProducto.requestFocusInWindow();
					return true;
				}
				return false;
			}
		};
		tableRelations.setModel(tableModelProducts);
		tableModelProducts.setDataVector(new Object[][] {}, 
				new Object[] {"Cancel", "Descripcion", "Cantidad", "Precio", "Total" });
		tableRelations.getColumnModel().getColumn(0).setPreferredWidth(3);
		tableRelations.getColumnModel().getColumn(1).setPreferredWidth(190);
		tableRelations.getColumnModel().getColumn(2).setPreferredWidth(5);
		tableRelations.getColumnModel().getColumn(3).setPreferredWidth(5);
		tableRelations.getColumnModel().getColumn(4).setPreferredWidth(5);
		tableRelations.setFont(new Font("Serif", Font.CENTER_BASELINE, 15));

		listPanel.add(scrollRelationsPane);

		// FOOTER PANEL
		footerPanel.setLayout(new FlowLayout(1, 8, 8));
		JPanel mensajesPanel = new JPanel();
		JPanel accionesPanel = new JPanel();

		footerPanel.add(mensajesPanel);
		footerPanel.add(accionesPanel);

		message = new JLabel();
		message.setText("");
		message.setForeground(Color.red);

		mensajesPanel.add(message);
		mensajesPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		mensajesPanel.setBorder(new TitledBorder(new LineBorder(Color.BLACK),
				"Mensajes", TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION,null, null));

		mensajesPanel.setPreferredSize(new Dimension(600, 70));
		accionesPanel.setPreferredSize(new Dimension(300, 70));

		modificarSesionVenta = new JButton("Modificar Venta");
		modificarSesionVenta.setPreferredSize(new Dimension(130, 25));
		modificarSesionVenta.setMnemonic(KeyEvent.VK_M);
		modificarSesionVenta.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				new SupervisorFrame(tableModelProducts,(CajeroVentaFrame) frame, productos);
				scanCodProducto.requestFocus();
			}
		});
		accionesPanel.add(modificarSesionVenta);

		nuevaVentaButton = new JButton("Nueva Venta");
		nuevaVentaButton.setPreferredSize(new Dimension(130, 25));
		nuevaVentaButton.setMnemonic(KeyEvent.VK_N);
		nuevaVentaButton.setEnabled(false);
		nuevaVentaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
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
		finVentaButton.setPreferredSize(new Dimension(130, 25));
		finVentaButton.setMnemonic(KeyEvent.VK_F);
		finVentaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(		tableRelations.getRowCount()> 0 )
					new FinVentaFrame(frame, Double.parseDouble(totalVentaTextField.getText()));
				else
					message.setText("No se puede finalizar la Venta por que no hay productos facturados ");
			}
		});
		accionesPanel.add(finVentaButton);

		salirButton = new JButton("Salir");
		salirButton.setEnabled(true);
		salirButton.setPreferredSize(new Dimension(130, 25));
		salirButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String[] disabledButtons = new String[2];
				disabledButtons[0] = DailyMarketFrame.APERTURA_CAJA;
				disabledButtons[1] = DailyMarketFrame.CERRAR_APLICACION;
				((CajaFrame) parentFrame).deshabilitarBotones(disabledButtons);
				parentFrame.setVisible(true);
				dispose();
			}
		});
		accionesPanel.add(salirButton);
	
		setBounds(20, 0, 943, 740);
		setTitle("DailyMarket - VENTA ");

		scanCodProducto.requestFocusInWindow();
		setVisible(true);

	}

	public void habilitarImprimirVenta() {

		imprimirTicket.setEnabled(true);
		finVentaButton.setEnabled(false);
		nuevaVentaButton.setEnabled(true);
		modificarSesionVenta.setEnabled(false);
		salirButton.setEnabled(true);
		
		listPanel.setEnabled(false);
		tableRelations.setEnabled(false);
		scrollRelationsPane.setEnabled(false);
		listPanel.setOpaque(true);
		tableRelations.setOpaque(true);
		scrollRelationsPane.setOpaque(true);
		
		scanCodProducto.setEditable(false);
		scanCodProducto.setEnabled(false);
		
		cantProd.setEditable(false);
		cantProd.setEnabled(false);
		ingresarProdButton.setEnabled(false);
		descProducto.setEditable(false);
		descProducto.setEnabled(false);
		
		vueltoTextField.setText(( new Double ((Truncar((pagoVenta - totalVenta),2 )))).toString());

	}

	public static void main(String[] args) throws IOException, SQLException {
		new CajeroVentaFrame(null);
	}

	public List<ProductModel> getProductos() {
		return productos;
	}

	public List<String> getProductsCode() {
		return productsCode;
	}

	public void setProductsCode(List<String> productsCode) {
		this.productsCode = productsCode;
	}

	private void agregarAProductsCode(String code) {
		productsCode.add(code);
	}

	private void eliminarDeProductsCode(String code) {
		productsCode.remove(code);
	}

	private void agregarProducto(ProductModel prod) {
		productos.add(prod);
	}

	private void agregarProductoSesion() {

		if (cantProd.getText() == null || "".equals(cantProd.getText()) || scanCodProducto.getText().equals(""))
			return;

		Object params[] = new String[] { scanCodProducto.getText() };
		Document doc = TelefrontGUI.getInstance().executeMethod(CONTROLLER_CLASS, "obtenerProducto", params);
		ProductModel productModel = new ProductModel();
		
		if (doc != null) {
			message.setText("");
			productModel.toProductModel(doc);
			if(productModel.getId()!=null){
				salirButton.setEnabled(false);
	
				subTotalVenta += Double.valueOf(productModel.getPrice())* Double.parseDouble(cantProd.getText());
				tableModelProducts.addRow(new Object[] {new Boolean(false),productModel.getDescription(),cantProd.getText(),
						Double.valueOf(productModel.getPrice()),Double.valueOf(productModel.getPrice())* Double.parseDouble(cantProd.getText()) });
				productModel.setCantidad(Integer.parseInt(cantProd.getText()));
				agregarAProductsCode(productModel.getCode());
	
				agregarProducto(productModel);
	
				descProducto.setText(productModel.getDescription());
				tableRelations.getColumn("Cancel").setCellRenderer(new MultiRenderer());
				tableRelations.getColumn("Cancel").setCellEditor(new MultiEditor());
	
				subTotalVenta = Truncar(subTotalVenta, 2);
				subtotalVentaTextfield.setText(subTotalVenta.toString());
	
				totalVenta = OTORGAR_DESCUENTO ? subTotalVenta * DESCUENTO_EMPLEADO: subTotalVenta;
				totalVenta = Truncar(totalVenta, 2);
				totalVentaTextField.setText(totalVenta.toString());
	
				imagePanel.remove(picLabel);
				ImageIcon productoImg = null;
	
				if (productModel.getFoto() != null) {
					productoImg = new ImageIcon(productModel.getFoto());
				} else {
					java.net.URL imgURL = InitDailyMarketFrame.class
							.getResource("sinImg.jpg");
					productoImg = new ImageIcon(imgURL);
				}
	
				picLabel = new JLabel(new ImageIcon(productoImg.getImage()
						.getScaledInstance(180, 140, Image.SCALE_SMOOTH)));
				imagePanel.add(picLabel);
				picLabel.repaint();
				imagePanel.repaint();
				ventaProductoPanel.repaint();
				picLabel.validate();
				imagePanel.validate();
				ventaProductoPanel.validate();
			} else {
				message.setText("No existe el producto con código "
						+ scanCodProducto.getText());
			}
		} else {
			message.setText("No existe el producto con código "
					+ scanCodProducto.getText());
		}

		scanCodProducto.setText(null);
		cantProd.setText("1");

	}

	public void decrementarSubtotal(Double valor) {
		subTotalVenta -= valor;
		subTotalVenta = Truncar(subTotalVenta, 2);
		subtotalVentaTextfield.setText(subTotalVenta.toString());
		totalVenta = OTORGAR_DESCUENTO ? subTotalVenta * DESCUENTO_EMPLEADO: subTotalVenta;
		totalVenta = Truncar(totalVenta, 2);
		totalVentaTextField.setText(totalVenta.toString());
	}

	public void otorgarDescuento() {
		OTORGAR_DESCUENTO = true;
		totalVenta = OTORGAR_DESCUENTO ? subTotalVenta * DESCUENTO_EMPLEADO: subTotalVenta;
		totalVenta = Truncar(totalVenta, 2);
		totalVentaTextField.setText(totalVenta.toString());
	}

	public void cancelarVenta() {
		String[] disabledButtons = new String[2];
		disabledButtons[0] = DailyMarketFrame.APERTURA_CAJA;
		disabledButtons[1] = DailyMarketFrame.CERRAR_APLICACION;
		((CajaFrame) parentFrame).deshabilitarBotones(disabledButtons);
		parentFrame.setVisible(true);
		dispose();

	}

	private double Truncar(double nD, int nDec) {
		if (nD > 0)
			nD = Math.floor(nD * Math.pow(10, nDec)) / Math.pow(10, nDec);
		else
			nD = Math.ceil(nD * Math.pow(10, nDec)) / Math.pow(10, nDec);

		return nD;
	}

	public boolean isDescuentoOtorgado() {
		return OTORGAR_DESCUENTO;
	}
	
}
