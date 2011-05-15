package ar.com.tsoluciones.telefront.test.action;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import ar.com.tsoluciones.arcom.security.Product;
import ar.com.tsoluciones.arcom.security.User;
import ar.com.tsoluciones.arcom.security.services.factory.CajeroVentaFactory;
import ar.com.tsoluciones.arcom.security.services.factory.UserServiceFactory;
import ar.com.tsoluciones.arcom.security.services.proxyinterface.CajeroVentaServiceInterface;
import ar.com.tsoluciones.arcom.security.services.proxyinterface.UserServiceInterface;
import ar.com.tsoluciones.arcom.system.SystemManager;

public class ImagePersister {

	
	public static void main(String[] args) {
		
		persistirImagenUsuario();
		
	}

	private static void persistirImagenProducto() {
		SystemManager.init();
		
		File file = new File("C:/Documents and Settings/Administrador/Escritorio/pepsi.jpg");
		
		BufferedImage img = null;
	      try {
	        img = ImageIO.read(file);
	        
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        ImageIO.write(img, "jpg", baos);
	        byte[] bytesOut = baos.toByteArray();
	        
			CajeroVentaServiceInterface cajeroService = (CajeroVentaServiceInterface) new CajeroVentaFactory().newInstance();
			Product producto = cajeroService.getProductByCode("619659026769");
			producto.setFoto(bytesOut);
			cajeroService.actualizarProducto(producto);
			

	      } catch (IOException ex) {
	          System.out.println("No se pudo leer la imagen");
	      }
	}
	
	private static void persistirImagenUsuario() {
		SystemManager.init();
		
		File file = new File("C:/Documents and Settings/Administrador/Escritorio/vendedor.jpg");
		
		BufferedImage img = null;
	      try {
	        img = ImageIO.read(file);
	        
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        ImageIO.write(img, "jpg", baos);
	        byte[] bytesOut = baos.toByteArray();
	        
	    	UserServiceInterface userInterface = (UserServiceInterface) new UserServiceFactory().newInstance();
			User usuario = userInterface.getUserByUserName("apallich");
			usuario.setFoto(bytesOut);
			userInterface.update(usuario);
			

	      } catch (IOException ex) {
	          System.out.println("No se pudo leer la imagen");
	      }
	}
}
