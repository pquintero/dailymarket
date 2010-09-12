package dailymarket.swing.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.Point2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.AttributedString;
import java.util.Vector;

public class PrinterTicket implements Printable{
	protected String[] ticketString;
	 
	public PrinterTicket(String[] s){
		ticketString = s;
	}
	
	public int print(Graphics g,PageFormat pf, int page){
		if(page == 0 ){
			
		
		final int PULGADA = 17;
		
		Graphics2D g2d = (Graphics2D)g;
		g2d.translate(pf.getImageableX(),pf.getImageableY());
		g2d.setColor(Color.black);
		g2d.setStroke(new BasicStroke(2));
			
		Point2D.Double punto = new Point2D.Double(0.25 * PULGADA, 0.25 * PULGADA);

		double ancho = 8 * PULGADA;

//		AttributedString parrafo = new AttributedString(ticketString);
//		parrafo.addAttribute(TextAttribute.FONT,new Font("Serif", Font.PLAIN, 8));
//		LineBreakMeasurer lineaBreaker = new LineBreakMeasurer(parrafo.getIterator(),new FontRenderContext(null,true,true));

		TextLayout Campo;
		TextLayout justCampo;
		
				
		Font f = new Font( "Times",Font.BOLD,8 );
		
		Vector lineas = new Vector();
		
		for(int con = 0; con < ticketString.length ;con++  ){
			TextLayout tl = new TextLayout( ticketString[con],f,new FontRenderContext(null,true,true) );	
			lineas.add(tl);
		}
		
		
		
		
		
		
		for (int i = 0; i < lineas.size(); i++) {
		Campo = (TextLayout)lineas.get(i);

		if (i != lineas.size()-1) {
		justCampo = Campo.getJustifiedLayout((float)ancho);

			} else {
			justCampo = Campo;
			}

		punto.y += justCampo.getAscent();
		justCampo.draw(g2d,(float)punto.x,(float)punto.y);

		punto.y += justCampo.getDescent() + justCampo.getLeading();
		}
		
		return (PAGE_EXISTS);
		}
		else
			return NO_SUCH_PAGE;
}

	public static void main (String[] args){
	
		// Creamos un objeto de impresión.
		PrinterJob job = PrinterJob.getPrinterJob();
		
		// Hacemos imprimible el objeto ObjetoAImprimir
		String texto = new String();
		//De la manera en que esta son 28 Caracteres por linea
		texto += "SUPERMERCADOS EL CHINO LOCO ";
		texto += "1234567890123456789012345678";
		texto += "Av.Rivadavia 6094 TEL: 67059 055 ";
		texto += "Cajero: Ottaviano Gabriel ";
		texto += "1 x Pepsi 1.5 Lt _____ 8.5 ";
		texto += "TOTAL: 67,0";

//		job.setPrintable(new PrinterTicket(texto));
//	
		
		//Pondrá algo tipo Información job: sun.awt.windows.WPrinterJob@4a5ab2
		System.out.println("Información job: " + job.toString());
	
		//Abre el cuadro de diálogo de la impresora, si queremos que imprima
		//directamente sin cuadro de diálogo quitamos el if...
//		if (job.printDialog()){
			//Imprime, llama a la función print del objeto a imprimir
			//en nuestro caso el Objeto ObjetoAImprimir
			try { 
				job.print();
				}
			catch (PrinterException e) {
				System.out.println("Error de impresión: " + e);
				}
	//}
 }
}