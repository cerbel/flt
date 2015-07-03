package Email.enviaCorreo;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws SQLException
    {
    	Conexion cn=new Conexion();
	     ResultSet resultado;
	     String rut, nombres, numFactura,fecha;
	     int monto,sumaMonto=0;
	     String filas = "";

	     resultado = cn.getQuery(" SELECT fac.pap_rut_proveedor, pro.nombre_proveedor, fac.pap_numero_factura_proveedor, fac.pap_fecha_vencimiento_proveedor, fac.pap_monto_pago_proveedor "
	     						  + " FROM flt_pago_proveedores AS fac, flt_proveedor pro "
	     						  + " WHERE fac.pap_rut_proveedor = pro.rut_proveedor "
	     						  + " AND fac.pap_estado_proveedor = 'NO PAGADO' ");
	 
	     try {
	        		
	    	 while(resultado.next()){
					rut			= resultado.getString(1);
					nombres		= resultado.getString(2);
					numFactura	= resultado.getString(3);
					fecha		= resultado.getString(4);
					monto		= (int)resultado.getFloat(5);
					sumaMonto	+= monto;
					
					filas  +=	"<tr>"
		 	  				   + "	<td>"+rut+"</td>"
		 	  				   + "	<td>"+nombres.toUpperCase()+"</td>"
		 	  				   + "	<td>"+numFactura+"</td>"
		 	  				   + "	<td>"+fecha+"</td>"
		 	  				   + "	<td align='right'>"+monto+"</td>"
		 	  				   + "</tr>";
				}
				
				filas	 +=  "<tr>"
	 	  				   + "	<td><h4>TOTAL</h4></td>"
	 	  				   + "	<td></td>"
	 	  				   + "	<td></td>"
	 	  				   + "	<td></td>"
	 	  				   + "	<td align='right'><b>"+sumaMonto+"</b></td>"
	 	  				   + "</tr>";
				
				//aqui se indica al correo que se desea enviar-el encabezado-el asunto- y los registros que se le adjuntaran a la tabla
				EnvioCorreo ec = new EnvioCorreo("juancernab@gmail.com","Señorita Susana","Pago de Facturas",filas);
				
				if(cn!=null){
					cn.conn.close();
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
	         
    }
}
