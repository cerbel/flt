package Email.enviaCorreo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Conexion {

	Connection conn	= null;

	public Conexion() {
		
//		try {

			try {
				Class.forName("com.sun.java.util.jar.pack.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://localhost/flt","flt","asdqwe123");
				System.out.println("Coneccion exitosa");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
	}
	
	
	
	public ResultSet getQuery(String query){
		Statement st		= null;
		ResultSet resultado	= null;
		try{
			st =conn.createStatement();
			resultado= st.executeQuery(query);
		}catch(SQLException e){
			e.printStackTrace();
		}
		return resultado;
	}
	
	public void setQuery(String query){
		Statement st = null;
		
		try{
			st = conn.createStatement();
			st.execute(query);
		}catch(SQLException e){
			e.printStackTrace();
		}
		
	}
	
}



