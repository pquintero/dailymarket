package ar.com.dailyMarket.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;
import org.hibernate.Criteria;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import ar.com.dailyMarket.model.Configuration;
import ar.com.dailyMarket.model.Image;
import ar.com.dailyMarket.model.Product;

public class ProductService extends MailService{
	
	public void copyProperties(Object obj, DynaActionForm form) {			
		Product product = (Product)obj;
		product.setName((String)form.get("name"));
		product.setDescription((String)form.get("description"));
		product.setActualStock(Integer.parseInt((String)form.get("actualStock")));
		product.setPrice(Double.parseDouble((String)form.get("price")));
		product.setSizeOfPurchase(Integer.parseInt((String)form.get("sizeOfPurchase")));
		GroupProductService groupProductService = new GroupProductService();
		product.setGroupProduct(groupProductService.getGroupProductByPK((Long)form.get("groupProductId")));
		product.setRepositionStock(Integer.parseInt((String)form.get("repositionStock")));
		product.setActive(new Boolean(true));
		
		/** FIXME que pasa si actualizas un producto despues de haber mandado el mail y el estado haya pasado a enviado????**/
		if (product.getActualStock() >= product.getRepositionStock()) {
			product.setState(Product.PRODUCT_STATE_STOCK);
		} else {
			product.setState(Product.PRODUCT_STATE_PENDING);
		}
	}
	
	public void save (ActionForm form) {
		Transaction tx = null;
		try {
			tx = HibernateHelper.currentSession().beginTransaction();
			
			Product product = new Product();
			copyProperties(product, (DynaActionForm)form);
			/**
			 * FIXME poner el codigo ingresado x el lector o usuario
			 * pasar al copy properties ???? porque juani lo puso aca????
			 */
			product.setCode("FALTA PONER CODIGO");
			save(product);
			
			tx.commit();
		}
		catch (RuntimeException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		}
		finally {
			tx = null;
		}
	}
	
	public void update (ActionForm form, Product product) {
		Transaction tx = null;
		try {
			tx = HibernateHelper.currentSession().beginTransaction();
			
			copyProperties(product, (DynaActionForm)form);
			save(product);
			
			tx.commit();
		}
		catch (RuntimeException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		}
		finally {
			tx = null;
		}
	}
	
	public void delete (Long id) {
		Transaction tx = null;
		try {
			tx = HibernateHelper.currentSession().beginTransaction();
			
			Product product = getProductByPK(id);
			product.setActive(false);
			save(product);
			
			tx.commit();
		}
		catch (RuntimeException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		}
		finally {
			tx = null;
		}
	}
	
	public void save (Product product) {
		HibernateHelper.currentSession().saveOrUpdate(product);
	}
	
	public Product getProductByPK (Long id) {
		return (Product)HibernateHelper.currentSession().load(Product.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<Product> executeFilter(DynaActionForm form) {
		String code = !((String)form.get("code")).equals("") ? (String)form.get("code") : null;
		String name = !((String)form.get("name")).equals("") ? (String)form.get("name") : null;
		String description = !((String)form.get("description")).equals("") ? (String)form.get("description") : null;			
		Long groupProduct = form.get("groupProductId") != null && ((Long)form.get("groupProductId")).longValue() != new Long(-1).longValue() ? (Long)form.get("groupProductId") : null;
		
		Criteria c = HibernateHelper.currentSession().createCriteria(Product.class);
		if (code != null) {
			c.add(Restrictions.eq("code", code));
		}
		if (name != null) {
			c.add(Restrictions.ilike("name", name,MatchMode.ANYWHERE));
		}
		if (description != null) {
			c.add(Restrictions.ilike("description", description,MatchMode.ANYWHERE));
		}
		if (groupProduct != null) {
			c.createCriteria("groupProduct").add(Restrictions.eq("id", groupProduct));
		}
		c.add(Restrictions.eq("active", new Boolean(true)));
		List<Product> products = c.list();		
		return products.isEmpty() ? new ArrayList<Product>() : products;
	}
	
	@SuppressWarnings("unchecked")
	public List<Product> getProductWithoutStock() {					
		/* actualizo el estado antes de verlo
		*  lo tengo q hacer xq NO tengo forma de saber cuando recibi mercaderia
		*/				
		Transaction tx = null;
		try {
			//TODO sacar el closesession y probar si anda bien
			HibernateHelper.closeSession();
		    tx = HibernateHelper.currentSession().beginTransaction();
		    
		    //TODO ACTUALIZO ESTADO A LOS PRODUCTOS CON STOCK (Mayor estricto????? o mayor o igual???)
		    Criteria productosCriteria = HibernateHelper.currentSession().createCriteria(Product.class)
		    							.add(Restrictions.gtProperty("actualStock", "repositionStock"));
		    List<Product> productList = productosCriteria.list();
		    
		    for (Iterator<Product> iterator = productList.iterator(); iterator.hasNext();) {
		    	Product product = iterator.next();
		    	product.setState(Product.PRODUCT_STATE_STOCK);
	    		HibernateHelper.currentSession().update(product);
			}
		    
		    tx.commit();
		}
		catch (RuntimeException e) {
		    if (tx != null) tx.rollback();
		    e.printStackTrace();
		}
		finally {
		    tx = null;
		}
		//TODO Menor o igual???? o solo menor????
		Criteria c = HibernateHelper.currentSession().createCriteria(Product.class);
		c.add(Restrictions.leProperty("actualStock", "repositionStock"));
		c.add(Restrictions.eq("active", new Boolean(true)));
		return c.list();
	}
	
	public String[] getProductsIdsArray(List<Product> list) {
		String[] vector = new String[list.size()];
		int i = 0;
		for (Product prod : list) {
			HibernateHelper.currentSession().refresh(prod);
			if (Product.PRODUCT_STATE_PENDING.equals(prod.getState())) {
				vector[i] = prod.getId().toString();
			} else {
				vector[i] = "-1";
			}
			i++;
		}
		return vector;
	}
	
	public List<Product> getProductsFromArray(Long[] ids) {
		List<Product> products = new ArrayList<Product>();
		for (Long productid : ids) {
			products.add(getProductByPK(productid));
		}
		return products;
	}
	
	public List<Product> getProductsFromArray(String[] ids) {
		List<Product> products = new ArrayList<Product>();
		for (String productidStr : ids) {
			Long id = Long.valueOf(productidStr);
			if (id > 0) {
				products.add(getProductByPK(id));
			}
		}
		return products;
	}
	
	public void sendOrder(String[] idsStr, String to, String subject, String body) {								
		StringTokenizer st = new StringTokenizer(to, ";");
		String[] emailTo = new String[st.countTokens()];
    	for (int i = 0; st.hasMoreTokens(); i++) {
			emailTo[i] = (String)st.nextToken();
		}
    	
		super.sendMail(emailTo, new StringBuffer(body), subject);
    	
		//Actualizo estado LUEGO DE ENVIAR MAIL
		Transaction tx = null;
		try {
			HibernateHelper.closeSession();
		    tx = HibernateHelper.currentSession().beginTransaction();
		    
		    for (int i = 0; i < idsStr.length; i++) {
	    		Long id = Long.valueOf(idsStr[i]);
				if (id > 0) {
		    		Product product = getProductByPK(id);
		    		product.setState(Product.PRODUCT_STATE_SEND);
		    		HibernateHelper.currentSession().update(product);
				}
	    	}
		    tx.commit();
		}
		catch (RuntimeException e) {
		    if (tx != null) tx.rollback();
		    e.printStackTrace();
		}
		finally {
		    tx = null;
		}
	}
	
	public StringBuffer createMessage (List<Product> products) {
		StringBuffer message = new StringBuffer();		
		
		if (!products.isEmpty()) {
			message.append("PRODUCTOS PENDIENTE DE MERCADERIA: \n\n");
			message.append("Pedido: \n\n");
			for (Iterator<Product> it = products.iterator(); it.hasNext();) {
				Product product = it.next();
				message.append("\tCódigo: " + product.getCode() + " - Producto: " + product.getName() + " - Tamaño de Pedido: " + product.getSizeOfPurchase() + "\n");
			}
			message.append(" \n");
		}								
		return message;
	}
	
	@SuppressWarnings("unchecked")
	public List<Product> getAllProducts() {
		return (List<Product>)HibernateHelper.currentSession().createCriteria(Product.class)
		.add(Restrictions.eq("active", new Boolean(true))).list();
	}
	


	public String getMaildestinataries() {
		ConfigurationService configurationService = new ConfigurationService();
		Configuration conf = configurationService.getConfiguration();
		if (conf != null) {
			return conf.getEmailDeposito();
		}
		return "";
	}
	
	@SuppressWarnings("unchecked")
	public List<Product> getProductsByGroup(Long groupId) {
		Criteria c = HibernateHelper.currentSession().createCriteria(Product.class);
		c.add(Restrictions.eq("active", new Boolean(true)));
		c.createCriteria("groupProduct").add(Restrictions.eq("id", groupId));
		return c.list();
	}
	
	public Product getLastProduct() {
		Criteria c = HibernateHelper.currentSession().createCriteria(Product.class);
		c.addOrder(Order.desc("id"));
		return (Product)c.list().get(0);
	}

	public void saveImage(Product product, Image img, FormFile file) {
		Transaction tx = null;
		try {
			tx = HibernateHelper.currentSession().beginTransaction();
			
			product.setImage(img);
	        product.setFoto(file.getFileData());
	        save(product);
			
			tx.commit();
		}
		catch (Exception e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		}
		finally {
			tx = null;
		}
	}
}
