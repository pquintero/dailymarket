package dailymarket.lectorDeHuellas;

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
import telefront.TelefrontGUI;

import com.digitalpersona.onetouch.DPFPCaptureFeedback;
import com.digitalpersona.onetouch.DPFPDataPurpose;
import com.digitalpersona.onetouch.DPFPFeatureSet;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPSample;
import com.digitalpersona.onetouch.DPFPTemplate;
import com.digitalpersona.onetouch.capture.DPFPCapture;
import com.digitalpersona.onetouch.capture.event.DPFPDataAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPDataEvent;
import com.digitalpersona.onetouch.capture.event.DPFPImageQualityAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPImageQualityEvent;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusEvent;
import com.digitalpersona.onetouch.capture.event.DPFPSensorAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPSensorEvent;
import com.digitalpersona.onetouch.processing.DPFPEnrollment;
import com.digitalpersona.onetouch.processing.DPFPFeatureExtraction;
import com.digitalpersona.onetouch.processing.DPFPImageQualityException;

import dailymarket.swing.ui.HuellaDigitalInterface;

public class LectorDeHuellasFirstLogin {
	   private static final String CONTROLLER_CLASS = "ar.com.tsoluciones.emergencies.server.gui.core.telefront.action.AperturaCajaManagerService";
	   private volatile static LectorDeHuellasFirstLogin singleton;
	   private DPFPCapture capturer = DPFPGlobal.getCaptureFactory().createCapture();
	   public static String TEMPLATE_PROPERTY = "template";
       private DPFPEnrollment enroller = DPFPGlobal.getEnrollmentFactory().createEnrollment();
		
	   private LectorDeHuellasFirstLogin(){
	   }
	 
	   public static LectorDeHuellasFirstLogin getInstance(){
	     if(singleton==null) {
	       synchronized(LectorDeHuellasFirstLogin.class){
	          if(singleton==null)
	        	  singleton= new LectorDeHuellasFirstLogin();
	       }
	    }
	   return singleton;
	  }
	   
	   public void start(JLabel messageLabel){
		   if(!capturer.isStarted())
			   capturer.startCapture();
			messageLabel.setText("Listo Para leer");
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
		
		 protected void process(DPFPSample sample, HuellaDigitalInterface frame) {
			 	JPanel imageHuellaPanel = frame.getImageHuellaPanel();
			 	JLabel imagen = frame.getFingerPrintPicture();
			 	JLabel mensajeLector = frame.getMensajeLector();
			 	JLabel mensaje = frame.getFrameMensaje();
			 	String usuario = frame.getUserName();
			 	String password = frame.getUserPassword();
			 	
			 
			 	this.process(sample,imagen, imageHuellaPanel);
				DPFPFeatureSet features = extractFeatures(sample, DPFPDataPurpose.DATA_PURPOSE_ENROLLMENT);

				if (features != null) 
					try {
					enroller.addFeatures(features);		
				}
				catch (DPFPImageQualityException ex) { }
				finally {
					updateStatus(mensajeLector);

					switch(enroller.getTemplateStatus()){
						case TEMPLATE_STATUS_READY:	
							stop(mensaje);
				
							DPFPTemplate template = enroller.getTemplate();
							String huella = MyBase64.encode(template.serialize());
							
							 Object params[] = new String[] { usuario, password, huella };
				             Document doc = TelefrontGUI.getInstance().executeMethod(CONTROLLER_CLASS, "altaHuellaDigital", params);

				            if (doc != null){
				            	mensaje.setText("Huella Digital guardada con exito!!!");
					            frame.loguear();
					            	
				            }
				            	
							else
								mensaje.setText("No se pudo guardar la hsuella!!!");

							
			                break;

						case TEMPLATE_STATUS_FAILED:	
							enroller.clear();
							stop(mensaje);
							updateStatus(mensajeLector);
							JOptionPane.showMessageDialog(null, "The fingerprint template is not valid. Repeat fingerprint enrollment.", "Fingerprint Enrollment", JOptionPane.ERROR_MESSAGE);
							start(mensaje);
							break;

					}
				}
			}
		 
			 private void updateStatus(JLabel mensaje){
					 mensaje.setText((String.format("Restan : %1$s", enroller.getFeaturesNeeded() + " capturas")));
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

		
		
	   public void init(final HuellaDigitalInterface frame){
			capturer.addDataListener(new DPFPDataAdapter() {
				@Override public void dataAcquired(final DPFPDataEvent e) {
					SwingUtilities.invokeLater(new Runnable() {	public void run() {
						frame.getMensajeLector().setText("Apoye su dedo nuevamente");
						process(e.getSample(), frame);
					}});
				}
			});
			capturer.addReaderStatusListener(new DPFPReaderStatusAdapter() {
				@Override public void readerConnected(final DPFPReaderStatusEvent e) {
					SwingUtilities.invokeLater(new Runnable() {	public void run() {
						frame.getMensajeLector().setText("Lector Online");
					}});
				}
				@Override public void readerDisconnected(final DPFPReaderStatusEvent e) {
					SwingUtilities.invokeLater(new Runnable() {	public void run() {
						frame.getMensajeLector().setText("Lector Offline");
					}});
				}
			});
			capturer.addSensorListener(new DPFPSensorAdapter() {
				@Override public void fingerTouched(final DPFPSensorEvent e) {
					SwingUtilities.invokeLater(new Runnable() {	public void run() {
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
						if (!e.getFeedback().equals(DPFPCaptureFeedback.CAPTURE_FEEDBACK_GOOD))
							frame.getMensajeLector().setText("Calidad de la huella pobre");
					}});
				}
			});
		}


	   public void stop(  JLabel mensajeLector){
		    mensajeLector.setText("Lector Offline");
			capturer.stopCapture();
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

		
	
	}