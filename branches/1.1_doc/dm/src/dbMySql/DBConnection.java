package dbMySql;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPTemplate;


public class DBConnection {


   private ByteArrayInputStream fingerprintData;
   
   private int fingerprintDataLength;
      
   public void inicializarCaptura() {
   }
   
   private Connection dbConnection;
   
   private PreparedStatement guardarStmt;
   private PreparedStatement identificarStmt;
   private PreparedStatement verificarStmt;
   
  
   public void initDB() {
       try {
           //Carga el driver ODBC
           Class.forName("com.mysql.jdbc.Driver");

           //Se conecta a la bd
           dbConnection = DriverManager.getConnection("jdbc:mysql://localhost/dailymarket","root", "root");

           Statement stm = dbConnection.createStatement();
           
           guardarStmt     = dbConnection.prepareStatement("INSERT INTO user( name, user, huelladigital) values(?,?,?)");
           identificarStmt   = dbConnection.prepareStatement("SELECT * FROM user");
           verificarStmt     = dbConnection.prepareStatement("SELECT huelladigital FROM user WHERE user=?");
                   
       } catch (Exception e) {
           e.printStackTrace();
       }
       
   }
   
  
   public void destroyDB() {
       try {
           //Se cierran todas las sentencias
           guardarStmt.close();           
           identificarStmt.close();
           //Cierra la conexión a la  base
           dbConnection.close();
           
       } catch (Exception e) {
           e.printStackTrace();
       }        
   }
   
   

   
   /*
    * Guarda los datos de la huella digital actual en la base de datos  
    * */
   public void guardarHuella( DPFPTemplate template, String user ){

           fingerprintData = new ByteArrayInputStream(template.serialize());
           fingerprintDataLength = template.serialize().length;

        try {
            //guardarStmt.setString(1,"default");
            guardarStmt.setString(1, user);
            guardarStmt.setString(2, user);
            guardarStmt.setBinaryStream(3, fingerprintData, fingerprintDataLength);
            
            guardarStmt.execute();
        } catch (SQLException ex) {
            System.err.println("Error al guardar los datos de la huella.");
            ex.printStackTrace();
        }
   }
  
 
   public DPFPTemplate loadTemplate(String nom){
	   DPFPTemplate referenceTemplate = DPFPGlobal.getTemplateFactory().createTemplate();
	
       try {
           verificarStmt.setString(1,"test");
           ResultSet rs = verificarStmt.executeQuery();
           
           //Si se encuentra el nombre en la base de datos
           if (rs.next()){
               //Lee la plantilla de la base de datos
               byte templateBuffer[] = rs.getBytes("huehuella");
               //Creo un Template
               referenceTemplate.deserialize(templateBuffer);
		
               }
               
       } catch (SQLException e) {
           e.printStackTrace();           
       } 
       
       return referenceTemplate;

   } 

}
