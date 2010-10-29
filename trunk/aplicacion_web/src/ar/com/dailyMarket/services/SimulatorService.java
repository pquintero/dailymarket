package ar.com.dailyMarket.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.DynaActionForm;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import ar.com.dailyMarket.model.Product;
import ar.com.dailyMarket.model.ProductoVenta;

public class SimulatorService extends MailService{
			
	@SuppressWarnings("unchecked")
	public List<Product> executeFilter(DynaActionForm form) {		
		Long productId = form.get("productId") != null && ((Long)form.get("productId")).longValue() != new Long(-1) ? (Long)form.get("productId") : null;				
		Long groupProductId = form.get("groupProductId") != null && ((Long)form.get("groupProductId")).longValue() != new Long(-1).longValue() ? (Long)form.get("groupProductId") : null;
		
		Criteria c = HibernateHelper.currentSession().createCriteria(Product.class);
		if (productId != null) {
			c.add(Restrictions.eq("id", productId));
		} 
		if (groupProductId != null) {
			c.createCriteria("groupProduct").add(Restrictions.eq("id", groupProductId));
		}
		c.add(Restrictions.eq("active", new Boolean(true)));
		List products = (List)c.list();		
		return products.isEmpty() ? new ArrayList() : products;
	}

	public void executeSimulation(DynaActionForm form) {
		String[] productsIds = (String[]) form.get("productsArray");
		List<String> checks =  Arrays.asList(((String[]) form.get("simuladorArray")));
		String[] ssopa = new String[productsIds.length];
		String[] srsa = new String[productsIds.length];
		Integer dias = (Integer) form.get("days");
		String margen = (String) form.get("margen");
		String yearFrom = (String) form.get("yearFrom");
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date simulatedDay;
		try {
			simulatedDay = df.parse((String) form.get("simulatedDay"));
		} catch (ParseException e) {
			simulatedDay = new Date();
		}
		
		Double porcentaje = calcularPorcentaje(yearFrom);
		
		for (int i = 0; i < productsIds.length; i++) {
			if (checks.contains(productsIds[i])) {
				String[] res = simularProducto(productsIds[i], dias, Integer.valueOf(margen), yearFrom, porcentaje, simulatedDay);
				ssopa[i]=res[0];
				srsa[i]=res[1];
			} else {
				ssopa[i]="";
				srsa[i]="";
			}
		}
    	
    	((DynaActionForm)form).set("simulatedSizeOfPurchaseArray", ssopa);
    	((DynaActionForm)form).set("simulatedRepositionStockArray", srsa);
	}
	
	
	private Double calcularPorcentaje(String yearFrom) {
		Integer yearDesde = Integer.valueOf(yearFrom);
		
		GregorianCalendar hoy = new GregorianCalendar();
		Integer yearHoy = hoy.get(GregorianCalendar.YEAR);
		
		ArrayList<Integer> ventas = new ArrayList<Integer>();
		for (Integer aux = yearDesde.intValue(); aux.intValue() <= yearHoy.intValue(); aux++) {
			ventas.add(ventasAnio(aux));
		}
		Double sumatoria = 0D;
		Iterator<Integer> iterator = ventas.iterator();
		Integer primera = iterator.next();
		while (iterator.hasNext()) {
			Integer venta = iterator.next();
			if (primera.intValue() == 0) {
				primera = 1;
			}
			sumatoria += new Double((venta.doubleValue() / primera.doubleValue()));
			primera = venta.intValue();
		}
		
		return sumatoria/(ventas.size() - 1);
	}

	//Devuelve el promedio de ventas en los años pedidos
	private String[] simularProducto(String productoId, Integer dias, Integer margen, String yearFrom, Double porcentaje, Date simulated) {
		String[] res = {"",""};
		Product producto = (Product) HibernateHelper.currentSession().load(Product.class, Long.valueOf(productoId));
		GregorianCalendar hoy = new GregorianCalendar();
		hoy.setTime(simulated);
		GregorianCalendar cal = new GregorianCalendar(Integer.valueOf(yearFrom), 
									hoy.get(GregorianCalendar.MONTH), 
									hoy.get(GregorianCalendar.DAY_OF_MONTH));
		
		Integer years = 0;
		ArrayList<Integer> tpList = new ArrayList<Integer>();
		ArrayList<Integer> srList = new ArrayList<Integer>();
		
		while (cal.get(GregorianCalendar.YEAR) < hoy.get(GregorianCalendar.YEAR)) {
			Integer tp = 0;
			Integer sr = 0;
			Integer aux = 0;
			while(cal.get(GregorianCalendar.DAY_OF_YEAR) < (hoy.get(GregorianCalendar.DAY_OF_YEAR) + dias)){
				//obtener ventas del producto para ese dia
				aux = ventasDia(producto, cal.getTime());
				tp += aux;
				if (cal.get(GregorianCalendar.DAY_OF_YEAR) < (hoy.get(GregorianCalendar.DAY_OF_YEAR) - margen)){
					sr += aux;
				}
				cal.add(GregorianCalendar.DATE, 1);
		    }
			tpList.add(tp);
			srList.add(sr);
			
			cal.add(GregorianCalendar.YEAR, 1);
			cal.set(cal.get(GregorianCalendar.YEAR),
					hoy.get(GregorianCalendar.MONTH), 
					hoy.get(GregorianCalendar.DAY_OF_MONTH));
			years++;
		}
		
		Integer aux = 0;
		Integer i = 0;
		for (Iterator<Integer> iterator = tpList.iterator(); iterator.hasNext();i++) {
			Integer tpi = iterator.next();
			aux+= tpi;
		}
		
		res[0] = new Integer(new Double(new Double(aux.doubleValue() / i.doubleValue()) * porcentaje).intValue()).toString();
		
		aux = 0;
		i = 0;
		for (Iterator<Integer> iterator = srList.iterator(); iterator.hasNext();i++) {
			Integer sri = iterator.next();
			aux+= sri;
		}
		res[1] = new Integer(new Double(new Double(aux.doubleValue() / i.doubleValue()) * porcentaje).intValue()).toString();
		
		return res;
	}

	private Integer ventasDia(Product producto, Date date) {
		Criteria c = HibernateHelper.currentSession().createCriteria(ProductoVenta.class);
		c.createCriteria("producto").add(Restrictions.eq("id", producto.getId()));
		c.createCriteria("sesionVenta").add(Restrictions.eq("fechaInicio", date));
		
		return c.list().size();
	}
	
	private Integer ventasAnio(Integer year) {
		GregorianCalendar desde = new GregorianCalendar(year.intValue(), 0, 1);
		GregorianCalendar hasta = new GregorianCalendar(year.intValue(), 11, 31);
		
		Criteria c = HibernateHelper.currentSession().createCriteria(ProductoVenta.class);
		Criteria c2 = c.createCriteria("sesionVenta");
		c2.add(Restrictions.ge("fechaInicio", desde.getTime()));
		c2.add(Restrictions.le("fechaInicio", hasta.getTime()));
		
		return c.list().size();
	}
	
	public void aplicarCambios(ActionForm form) {
		String[] productsIds = (String[]) ((DynaActionForm)form).get("productsArray");
		List<String> checks =  Arrays.asList(((String[]) ((DynaActionForm)form).get("simuladorArray")));
		String[] ssopa = (String[]) ((DynaActionForm)form).get("simulatedSizeOfPurchaseArray");
		String[] srsa = (String[]) ((DynaActionForm)form).get("simulatedRepositionStockArray");
		ProductService ps = new ProductService();
		
		for (int i = 0; i < productsIds.length; i++) {
			if (checks.contains(productsIds[i])) {
				Product pr = (Product) ps.getProductByPK(Long.valueOf(productsIds[i]));
				if (StringUtils.isNumeric(ssopa[i]) && !StringUtils.isEmpty(ssopa[i]) && Integer.valueOf(ssopa[i])>0) {
					pr.setSizeOfPurchase(Integer.valueOf(ssopa[i]));
				}
				if (StringUtils.isNumeric(srsa[i]) && !StringUtils.isEmpty(srsa[i]) && Integer.valueOf(srsa[i]) > 0) {
					pr.setRepositionStock(Integer.valueOf(srsa[i]));
				}
				HibernateHelper.currentSession().update(pr);
				HibernateHelper.currentSession().flush();	
			}
		}
	}
}
