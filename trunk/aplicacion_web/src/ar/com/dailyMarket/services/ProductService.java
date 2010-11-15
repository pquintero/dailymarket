package ar.com.dailyMarket.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.DynaActionForm;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import ar.com.dailyMarket.model.Configuration;
import ar.com.dailyMarket.model.Product;
import ar.com.dailyMarket.model.User;

import com.mysql.jdbc.Statement;

public class ProductService extends MailService{
	
	public void copyProperties(Object obj, DynaActionForm form) {			
		Product product = (Product)obj;
		product.setName((String)form.get("name"));
		product.setDescription((String)form.get("description"));
		product.setActualStock((Integer)form.get("actualStock"));
		product.setPrice((Double)form.get("price"));
		product.setSizeOfPurchase((Integer)form.get("sizeOfPurchase"));
		GroupProductService groupProductService = new GroupProductService();
		product.setGroupProduct(groupProductService.getGroupProductByPK((Long)form.get("groupProductId")));
		product.setRepositionStock((Integer)form.get("repositionStock"));
		product.setActive(new Boolean(true));
		if (product.getActualStock() >= product.getRepositionStock()) {
			product.setState(Product.PRODUCT_STATE_STOCK);
		} else {
			product.setState(Product.PRODUCT_STATE_PENDING);
		}
	}
	
	public void save (ActionForm form) {	
		Product product = new Product();
		copyProperties(product, (DynaActionForm)form);
		save(product);	
		//poner el codigo ingresado x el lector o usuario
		product.setCode(product.getId().toString());
		save(product);
	}	
	
	public void update (ActionForm form, Product product) {
		copyProperties(product, (DynaActionForm)form);
		save(product);
	}
	
	public void save (Product product) {
		HibernateHelper.currentSession().saveOrUpdate(product);
		HibernateHelper.currentSession().flush();		
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
		List products = (List)c.list();		
		return products.isEmpty() ? new ArrayList() : products;
	}
	
	@SuppressWarnings("unchecked")
	public List<Product> getProductWithoutStock() {					
		/* actualizo el estado antes de verlo
		*  lo tengo q hacer xq NO tengo forma de saber cuando recibi mercaderia
		*/				
		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context)initCtx.lookup("java:comp/env");
		    String url = (String) envCtx.lookup("urlDataBase"); //obtengo del contexto la url de la base
		    String usr = (String) envCtx.lookup("usrDataBase");
		    String pass = (String) envCtx.lookup("passDataBase");
		    
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, usr,pass);
			Statement stmt = (Statement) con.createStatement();
			stmt.executeUpdate("update product set state = \"" + Product.PRODUCT_STATE_STOCK + "\" where actualstock >= repositionstock;");
		} catch (SQLException e) {			
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		} 						
		Criteria c = HibernateHelper.currentSession().createCriteria(Product.class);
		c.add(Restrictions.leProperty("actualStock", "repositionStock"));
		c.add(Restrictions.eq("active", new Boolean(true)));
		return c.list();
	}
	
	public String[] getProductsIdsArray() {
		List <Product> list = getProductWithoutStock();
		String[] vector = new String[list.size()];
		int i = 0;
		for (Product prod : list) {
			vector[i]= prod.getId().toString();
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
	
	public void sendOrder(String[] idsStr, String to, String from, String subject, String body) {								
		StringTokenizer st = new StringTokenizer(to, ";");
		String[] emailTo = new String[st.countTokens()];
    	for (int i = 0; st.hasMoreTokens(); i++) {
			emailTo[i] = (String)st.nextToken();
		}
    	
		super.sendMail(emailTo, new StringBuffer(body));
    	//Actualizo estado LUEGO DE ENVIAR MAIL
    	for (int i = 0; i < idsStr.length; i++) {
    		Long id = Long.valueOf(idsStr[i]);
			if (id > 0) {
	    		Product product = getProductByPK(Long.valueOf(idsStr[i]));
	    		product.setState(Product.PRODUCT_STATE_SEND);
	    		save(product);
			}
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
	
	public void delete (Long id) {
		Product product = getProductByPK(id);
		product.setActive(false);
		save(product);
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
}
