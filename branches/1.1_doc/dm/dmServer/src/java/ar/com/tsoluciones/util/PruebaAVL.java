package ar.com.tsoluciones.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PruebaAVL {
	private static Connection conexion = null;
	static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public Connection getConexion(){
		return conexion;
	}

	public static void main(String[] args) {
		String avl = "1007";
		long sleep = 10; //segundos

		Statement stmt;
		double lat = -31.560582;
		double lon = -68.537126;
		try{

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conexion = DriverManager.getConnection("jdbc:mysql://f911server4/radio", "root", "rlink");
			stmt = conexion.createStatement();

			while(true)
			{
				lat = lat + 0.00123;
				lon = lon + 0.00123;
				String now = dateFormat.format(new Date());
				String in = "INSERT INTO reportes (Movid, fecha, Lat, Lon) VALUES ('" + avl + "', 	'"+now+"', "+lat+","+lon+"  )";
				System.out.println("INSERT INTO reportes (Movid, fecha, Lat, Lon) VALUES ('" + avl + "', 	"+now+", "+lat+","+lon+"  )");
				stmt.execute(in);

				Thread.sleep(sleep * 1000);
			}

		} catch (java.lang.ClassNotFoundException e) {
			System.err.println(" Error, no se encontro: " + e.getMessage());
		} catch (SQLException e) {
			System.err.println(" Error, Excepcion SQL: " + e.getMessage());
		} catch (Exception e) {
			System.err.println(" Error Interno GuardarDatos: " + e);
		}
		try {
			conexion.close();
		} catch (SQLException e) {
			System.err.println("Error. No se puede cerrar la Base: " + e.getMessage());
		}
	}

}

