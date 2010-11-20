package dailymarket.lectorDeHuellas;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;
import java.util.prefs.AbstractPreferences;
import java.util.prefs.Preferences;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

import telefront.TelefrontGUI;

import com.digitalpersona.onetouch.DPFPCaptureFeedback;
import com.digitalpersona.onetouch.DPFPDataPurpose;
import com.digitalpersona.onetouch.DPFPFeatureSet;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPSample;
import com.digitalpersona.onetouch.capture.DPFPCapture;
import com.digitalpersona.onetouch.capture.event.DPFPDataAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPDataEvent;
import com.digitalpersona.onetouch.capture.event.DPFPImageQualityAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPImageQualityEvent;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusEvent;
import com.digitalpersona.onetouch.capture.event.DPFPSensorAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPSensorEvent;
import com.digitalpersona.onetouch.processing.DPFPFeatureExtraction;
import com.digitalpersona.onetouch.processing.DPFPImageQualityException;

import dailymarket.model.Context;
import dailymarket.model.Empleado;
import dailymarket.model.GroupEmpleado;
import dailymarket.model.ProductModel.MyBase64;
import dailymarket.swing.ui.AperturaCajaFrame;
import dailymarket.swing.ui.CerrarCajaFrame;
import dailymarket.swing.ui.Configuration;
import dailymarket.swing.ui.HuellaDigitalInterface;
import dailymarket.swing.ui.SupervisorFrame;
import dailymarket.swing.ui.TicketFrame;

public class UtilLectorHuellasSingleton {
	   private static final String CONTROLLER_CLASS = "ar.com.tsoluciones.emergencies.server.gui.core.telefront.action.AperturaCajaManagerService";
	   private DPFPCapture capturer = DPFPGlobal.getCaptureFactory().createCapture();
	   public static String TEMPLATE_PROPERTY = "template";
    	
	   public UtilLectorHuellasSingleton(){
	   }
	   public void start(JLabel messageLabel){
		 
		   if(!capturer.isStarted())
			capturer.startCapture();
			messageLabel.setText("Listo Para leer");
		}

	   public void initLogin(final HuellaDigitalInterface frame ){
			capturer.addDataListener(new DPFPDataAdapter() {
				@Override public void dataAcquired(final DPFPDataEvent e) {
					SwingUtilities.invokeLater(new Runnable() {	public void run() {
						frame.getMensajeLector().setText("La Huella Fue Capturada");
					    processLogin(e.getSample(), frame);
					}});
				}
			});
			capturer.addReaderStatusListener(new DPFPReaderStatusAdapter() {
				@Override public void readerConnected(final DPFPReaderStatusEvent e) {
					SwingUtilities.invokeLater(new Runnable() {	public void run() {
						frame.getMensajeLector().setText("Listo Para Leer");

					}});
				}
				@Override public void readerDisconnected(final DPFPReaderStatusEvent e) {
					SwingUtilities.invokeLater(new Runnable() {	public void run() {
						frame.getMensajeLector().setText("Lector Off-Line");
						frame.getMensajeLector().setFont(	new Font("", Font.PLAIN, 12));

					}});
				}
			});
			capturer.addSensorListener(new DPFPSensorAdapter() {
				@Override public void fingerTouched(final DPFPSensorEvent e) {
					SwingUtilities.invokeLater(new Runnable() {	public void run() {
						frame.getMensajeLector().setText("Leyendo..");
					}});
				}
				@Override public void fingerGone(final DPFPSensorEvent e) {
					SwingUtilities.invokeLater(new Runnable() {	public void run() {
					}});
				}
			});
			capturer.addImageQualityListener(new DPFPImageQualityAdapter() {
				@Override public void onImageQuality(final DPFPImageQualityEvent e) {
					SwingUtilities.invokeLater(new Runnable() {	public void run() {
						if (e.getFeedback().equals(DPFPCaptureFeedback.CAPTURE_FEEDBACK_GOOD)){
						//	frame.getMensajeLector()
						}
						else {
							frame.getMensajeLector().setText("Re intente nuevamente");

						}
					}});
				}
			});
		}
		protected void process(DPFPSample sample, JLabel imagen, JPanel imageHuellaPanel){
			drawPicture(convertSampleToBitmap(sample), imageHuellaPanel, imagen);
		}
	
		public void drawPicture(Image image, JPanel imageHuellaPanel, JLabel picture) {
			imageHuellaPanel.remove(picture);
			picture = new JLabel(new ImageIcon(image.getScaledInstance(140, 90, Image.SCALE_SMOOTH)));

			GridBagConstraints constraintHuella = new GridBagConstraints();
			constraintHuella.gridx = 0;
			constraintHuella.gridy = 0;
			imageHuellaPanel.add(picture, constraintHuella);
			
//			imageHuellaPanel.firePropertyChange(null,true, true);
//			imageHuellaPanel.setVisible(true);

		}
	protected DPFPFeatureSet extractFeatures(DPFPSample sample, DPFPDataPurpose purpose)
			{
				DPFPFeatureExtraction extractor = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction();
				try {
					return extractor.createFeatureSet(sample, purpose);
				} catch (DPFPImageQualityException e) {
					return null;
				}
			}
	
    protected Image convertSampleToBitmap(DPFPSample sample) {
				return DPFPGlobal.getSampleConversionFactory().createImage(sample);
			}

	   public void stop(  JLabel mensajeLector){
			capturer.stopCapture();
		}
		
		public void processLogin(DPFPSample sample,  HuellaDigitalInterface frame ){
			String user = frame.getUserName();
			JLabel mensajeLector = frame.getMensajeLector();
			JLabel mensaje = frame.getFrameMensaje();
			JPanel imageHuellaPanel = frame.getImageHuellaPanel();
			JLabel picture = frame.getFingerPrintPicture();
	    	DPFPFeatureExtraction featureExtractor = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction();
	    	
	          try {
	        	//TODO MODULARIZAR Todo este tratamiento
				DPFPFeatureSet featureSet = featureExtractor.createFeatureSet(sample, DPFPDataPurpose.DATA_PURPOSE_VERIFICATION);
				String featureSetString = MyBase64.encode(featureSet.serialize()); 
			     
	            Object params[] = new String[] { user, "", "", featureSetString };
	            Document doc = null;
	            
	            if(frame instanceof SupervisorFrame){
	            	
	            	if(((SupervisorFrame)frame).getActualAction().equals(SupervisorFrame.CANCELAR_VENTA)){
	    	            params = new String[] { ((SupervisorFrame) frame).getMotivoDeCancelacion(), featureSetString };
			    		doc = TelefrontGUI.getInstance().executeMethod(CONTROLLER_CLASS, "cancelarVenta", params);
			    		
			            if( doc != null){
			        		JOptionPane.showMessageDialog(null, "Operación realizada con éxito");
			            	((SupervisorFrame) frame ).cancelarVenta();

			            }else{
			            	((SupervisorFrame) frame ).habilitarFirma();
			            }
			            
	            	}else if(((SupervisorFrame)frame).getActualAction().equals(SupervisorFrame.CANCELAR_PRODUCTOS)){
	    	            params = new String[] { featureSetString };

	            		doc = TelefrontGUI.getInstance().executeMethod(CONTROLLER_CLASS, "validarSupervisor", params);
	            		if( doc != null){
				            mensaje.setText("Cancelando...")	;
	            			((SupervisorFrame) frame).doCancelProducts();
				            	
				            }else{
				            	mensaje.setText("El usuario es inválido");
				            	((SupervisorFrame) frame).habilitarFirma();
				            }
	            	}else if(((SupervisorFrame)frame).getActualAction().equals(SupervisorFrame.OTORGAR_DESCUENTOS_EMP)){

	            		params = new String[] { featureSetString };
 	            		doc = TelefrontGUI.getInstance().executeMethod(CONTROLLER_CLASS, "validarUsuario", params);

 	            		if( doc != null){
 	            			((SupervisorFrame)frame).getFrameMensaje().setText("Empleado Validado");
 	            			((SupervisorFrame)frame).empleadoValidated();
 	            		}else{
 	            			((SupervisorFrame)frame).habilitarFirmaEmpleado();
 	            			((SupervisorFrame)frame).getFrameMensaje().setText("Re intente nuevamente");
					      }
	            	}else if(((SupervisorFrame)frame).getActualAction().equals(SupervisorFrame.OTORGAR_DESCUENTOS_SUP)){
	            		    params = new String[] { featureSetString };
		            		doc = TelefrontGUI.getInstance().executeMethod(CONTROLLER_CLASS, "validarSupervisor", params);
		            	
		            		if( doc != null){
	 	            			((SupervisorFrame)frame).getFrameMensaje().setText("Supervisor Validado");
	 	            			((SupervisorFrame)frame).supervisorValidated();
					            }else{
		 	            			((SupervisorFrame)frame).getFrameMensaje().setText("Re intente nuevamente");
		 	            			((SupervisorFrame)frame).habilitarFirmaSupervisor();

					            }


	            	}
	            }
	            else if( frame instanceof CerrarCajaFrame){
		    		System.out.println("Cerrar Caja");
		    		params = new String[] { user, ((CerrarCajaFrame)frame).getMonto(), "", featureSetString };
		    		doc = TelefrontGUI.getInstance().executeMethod(CONTROLLER_CLASS, "cerrarCaja", params);

		    		if (doc != null && doc.getRootElement().selectSingleNode("cajaCerrada") != null && doc.getRootElement().selectSingleNode("cajaCerrada").getText().equals("OK")){
		    				new TicketFrame(frame);
		    				((CerrarCajaFrame) frame).backToInitLogin();
			            }
						else{
							((CerrarCajaFrame)frame).habilitarBotonFirmar();
							mensaje.setText("Usuario incorrecto, Re intentenuevamente");
						}
		    	}else{
		    		System.out.println("Abrir Caja");
		    		params = new String[] { user, ((AperturaCajaFrame)frame).getMonto(), Configuration.getInstance().getCaja(), featureSetString };
		    		doc = TelefrontGUI.getInstance().executeMethod(CONTROLLER_CLASS, "abrirCaja", params);
		            if( doc != null){
						((AperturaCajaFrame) frame ).backToInitSession();
						setCurrentUser(doc);
					}else{
						((AperturaCajaFrame) frame ).habilitarBotonFirmar();
						mensaje.setText("Presione en fimar y apoye el dedo en el lector nuevamente");
					}
		    	 }
	            
	            stop(new JLabel());

	             
	          } catch (DPFPImageQualityException e) {
	        	  mensaje.setText(e.getMessage());
	        	  e.printStackTrace();
				}
	        
			drawPicture(convertSampleToBitmap(sample), imageHuellaPanel, picture);
		}
		
		 public static class MyBase64 {
		     private static class MyPreferences extends AbstractPreferences {
		         private Map<String,String> map = new HashMap<String,String>();
		         MyPreferences() { super(null,""); }
		         protected void putSpi(String key,String value) { map.put(key,value); }
		         protected String getSpi(String key) { return map.get(key); }
		         protected void removeSpi(String key) { map.remove(key); }
		         protected void removeNodeSpi() { }
		         protected String[] keysSpi() { return null; }
		         protected String[] childrenNamesSpi() { return null; }
		         protected AbstractPreferences childSpi(String key) { return null; }
		         protected void syncSpi() {}
		         protected void flushSpi() {}
		     }
		     static String encode(byte[] ba) {
		         Preferences p = new MyPreferences();
		         p.putByteArray("",ba);
		         return p.get("",null);
		     }
		     static byte[] decode(String s) {
		         Preferences p = new MyPreferences();
		         p.put("",s);
		         return p.getByteArray("",null);
		     }
		
		 }
		 
		 /**
			 * Setea el usuario actual en el contexto de sesión del cliente.
			 * @param doc Representación XML del usuario.
			 */
			public static void setCurrentUser(Document doc) {
				Element root = doc.getRootElement();
				
				//Setear todos los valores necesarios para el cajero
				
				Empleado user = new Empleado();
				
				Long id = Long.valueOf(root.selectSingleNode("id").getStringValue());
				String username = root.selectSingleNode("user").getStringValue();
				String name = root.selectSingleNode("name").getStringValue();
				String lastName = root.selectSingleNode("lastName").getStringValue();
				String password = root.selectSingleNode("password").getStringValue();
				String passwordOld = root.selectSingleNode("passwordOld").getStringValue();
				String dni = root.selectSingleNode("dni").getStringValue();
				String dateCreated = root.selectSingleNode("dateCreated").getStringValue();
				String huelladigital = root.selectSingleNode("huelladigital").getStringValue();
				
				String foto = root.selectSingleNode("foto").getStringValue();
				
				if(foto!=null && !"".equalsIgnoreCase(foto)){
					byte [] fotoArray = MyBase64.decode(foto);
					user.setFoto(fotoArray);
				}
				
				Node groupUser = root.selectSingleNode("groupUser");
				Long idGroup = Long.valueOf(groupUser.selectSingleNode("id").getStringValue());
				String nameGroup = groupUser.selectSingleNode("name").getStringValue();
				String descriptionGroup = groupUser.selectSingleNode("description").getStringValue();
				String email = root.selectSingleNode("email").getStringValue();
				String receiveNotifications = root.selectSingleNode("receiveNotifications").getStringValue();
				
				user.setDateCreated(dateCreated);
				user.setDni(dni);
				user.setEmail(email);
				user.setHuelladigital(huelladigital);
				user.setId(id);
				user.setLastName(lastName);
				user.setName(name);
				user.setPassword(password);
				user.setPasswordOld(passwordOld);
				user.setReceiveNotifications(Boolean.valueOf(receiveNotifications));
				user.setUser(username);
				GroupEmpleado groupEmpleado = new GroupEmpleado();
				groupEmpleado.setDescription(descriptionGroup);
				groupEmpleado.setId(idGroup);
				groupEmpleado.setName(nameGroup);
				user.setGroupEmpleado(groupEmpleado);

				Context.getInstance().setCurrentUser(user);
			}


		
	
	}