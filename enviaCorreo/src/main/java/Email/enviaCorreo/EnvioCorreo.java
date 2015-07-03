package Email.enviaCorreo;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EnvioCorreo {
//	public static void main(String[] args) {
		
	public EnvioCorreo(String mail_destinatario,String nombre, String asunto,String registros){	
		
		final String username = "ferreterialostroncos@gmail.com";
		final String password = "ferreteria2015";
		
		//creando la instancia de properties
	      Properties props = new Properties();
	      props.put("mail.smtp.auth", "true");
	      props.put("mail.smtp.starttls.enable", "true");
	      props.put("mail.smtp.host", "smtp.gmail.com");
	      props.put("mail.smtp.port", "587");
	      
	      
	      Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                        protected PasswordAuthentication
                        getPasswordAuthentication() {
                          return new PasswordAuthentication(username, password);
                        }
                  });
	      
	      try {
	    	//Creamos un nuevo mensaje, y le pasamos nuestra sesi�n iniciada en el paso anterior.
	    	  Message message = new MimeMessage(session);
	    	//Seteamos la direcci�n desde la cual enviaremos el mensaje
	    	  message.setFrom(new InternetAddress("juancernab@gmail.com  "));
	    	//Seteamos el destino de nuestro mensaje 
//	    	  message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("juanin_cb@hotmail.com"));
	    	  message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail_destinatario));
	    	//Seteamos el asunto
//	    	  message.setSubject("Prueba Java Mail");
	    	  message.setSubject(asunto);
	    	//Y por ultimo el texto.
	    	  
	    	  
	    	  message.setContent("<body>"
	    	  				   + "<h1>"+nombre+"</h1>\n Esta es la lista de las facturas que no se encuentran Pagadas: \n "
	    			  		   + "<table width='100%'>"
	    	  				   + "<tr bgcolor='gray'>"
	    	  				   + "	<td>Rut Proveedor</td>"
	    	  				   + "	<td>Nombre Proveedor</td>"
	    	  				   + "	<td>N&deg; Factura</td>"
	    	  				   + "	<td>Fecha pago</td>"
	    	  				   + "	<td>Monto</td>"
	    	  				   + "</tr>"
	    	  				   + registros
	    	  				   + "</table>"
	    	  				   + "</body>"
	    	  				   ,"text/html");
	    	  
	    	 //Esta orden env�a el mensaje
	    	  Transport.send(message);
	    	 //Con esta imprimimos en consola que el mensaje fue enviado
	    	   System.out.println("Mensaje Enviado a : "+mail_destinatario);
	    	 
	    	}catch (MessagingException e) {
	    	//Si existiera un error en el env�o lo hacemos saber al cliente y lanzamos una excepcion.
	    		System.out.println("Hubo un error al enviar el mensaje.");  
	    		throw new RuntimeException(e);
	    	 }
	    	  
	
	}
}
