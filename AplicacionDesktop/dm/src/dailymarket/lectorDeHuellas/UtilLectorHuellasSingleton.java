package dailymarket.lectorDeHuellas;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;



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
import com.digitalpersona.onetouch.processing.DPFPFeatureExtraction;
import com.digitalpersona.onetouch.verification.DPFPVerification;
import com.digitalpersona.onetouch.verification.DPFPVerificationResult;

import dailymarket.swing.ui.HuellaDigitalInterface;
import dbMySql.DBConnection;

public class UtilLectorHuellasSingleton {
	   private volatile static UtilLectorHuellasSingleton singleton;
	   private DPFPCapture capturer = DPFPGlobal.getCaptureFactory().createCapture();
	   private UtilLectorHuellasSingleton(){
	   }
	 
	   public static UtilLectorHuellasSingleton getInstance(){
	     if(singleton==null) {
	       synchronized(UtilLectorHuellasSingleton.class){
	          if(singleton==null)
	        	  singleton= new UtilLectorHuellasSingleton();
	       }
	    }
	   return singleton;
	  }
	   
	   public void start(JLabel messageLabel){
			capturer.startCapture();
			messageLabel.setText("Listo Para leer");
		}

	   public void init(final HuellaDigitalInterface frame ){
			capturer.addDataListener(new DPFPDataAdapter() {
				@Override public void dataAcquired(final DPFPDataEvent e) {
					SwingUtilities.invokeLater(new Runnable() {	public void run() {
						frame.getMensajeLector().setText("La Huella Fue Capturada");
					    process(e.getSample(), frame);
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

	   public void stop(  JLabel mensajeLector){
		    mensajeLector.setText("OffLine");
			capturer.stopCapture();
		}
		
		public void process(DPFPSample sample,  HuellaDigitalInterface frame ){
			String user = frame.getUserName();
			JLabel mensajeLector = frame.getMensajeLector();
			JLabel mensaje = frame.getFrameMensaje();
			JPanel imageHuellaPanel = frame.getImageHuellaPanel();
			JLabel picture = frame.getFingerPrintPicture();
			
	         try {
	             DPFPFeatureExtraction featureExtractor = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction();
	             DPFPFeatureSet featureSet = featureExtractor.createFeatureSet(sample, DPFPDataPurpose.DATA_PURPOSE_VERIFICATION);

	             DPFPVerification matcher = DPFPGlobal.getVerificationFactory().createVerification();
	             matcher.setFARRequested(DPFPVerification.MEDIUM_SECURITY_FAR);
	             
	             /******************LLAMAR A SERVICIO*************************************/                         
	             DBConnection db = new DBConnection();
	             db.initDB();
	             
	             DPFPTemplate template = db.loadTemplate(user);
	             //Traerme el Objeto USUARIO
	             
	             /*
	              * 
	              * 	Object params[] = new String[] { cajero.getText() };
						Document doc = TelefrontGUI.getInstance().executeMethod(CONTROLLER_CLASS, "loadUser", params);
						User user
	              */
	             
	             db.destroyDB();
	             /******************************************************/       
	                 
	             if (template != null) {
	                DPFPVerificationResult result = matcher.verify(featureSet, template);
	                if (result.isVerified()) {
	                 	 stop(mensajeLector);
	                	 mensaje.setText("USUARIO: " + user + " LOGEADO");
	                	 //PASARLE al FRAME el USUARIO para loguearlo
	                	 frame.loguear();
	                     }else{
	                    	 mensaje.setText("Por favor Reintente nuevamente");
	                     }
	                    	}
	             
	         } catch (Exception e) {
	        	 //Logear
	        	 mensajeLector.setText("Falló:" + e.getCause());
	         }
			drawPicture(convertSampleToBitmap(sample), imageHuellaPanel, picture);
		}
		
		protected Image convertSampleToBitmap(DPFPSample sample) {
			return DPFPGlobal.getSampleConversionFactory().createImage(sample);
		}
		
		public void drawPicture(Image image, JPanel imageHuellaPanel, JLabel picture) {
			imageHuellaPanel.remove(picture);
			picture = new JLabel(new ImageIcon(image.getScaledInstance(140, 90, Image.SCALE_SMOOTH)));
			GridBagConstraints constraintHuella = new GridBagConstraints();
			constraintHuella.gridx = 0;
			constraintHuella.gridy = 0;
			imageHuellaPanel.add(picture, constraintHuella);
		}
	}