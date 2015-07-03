package cl.cstit.msd.ccs.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmailNotification {

	public static final int FORECAST_TO_APPROVE_NOTIFICATION = 1;
	
	private static ResourceBundle bundle = ResourceBundle.getBundle("configFileBundle");
	private static String getValue(String key){return bundle.getString(key);}

	private String contextURLEnviroment;
	
	public SendEmailNotification(String contextURLEnviroment){
		this.contextURLEnviroment = contextURLEnviroment;
	}
	
	public void sendNotificationToApproveForecast(String idStage, String toEmail, String isidUserTo, String isidUserFrom){
		
	      
//	      String to   = toEmail;
	      String to   = "cquezada@cstit.cl";
	      String from = getValue("connect.email.user");
	      String host = getValue("connect.email.host");


	      // Get system properties
	      Properties properties = System.getProperties();
	      properties.setProperty("mail.smtp.host", host);

	      Session session = Session.getDefaultInstance(properties);
	      try{
	         MimeMessage message = new MimeMessage(session);
	         message.setFrom(new InternetAddress(from));
	         message.addRecipient(Message.RecipientType.TO,
	                                  new InternetAddress(to));

	         message.setSubject("Financial Cost Notification; Forecast to approve");

	         URL oracle = new URL(contextURLEnviroment+"/notificationToApproveForecast.action?idStage="+idStage+"&isidUserTo="+isidUserTo+"&isidUserFrom="+isidUserFrom);
	         BufferedReader in = new BufferedReader(
	         new InputStreamReader(oracle.openStream()));

	         StringBuffer sb = new StringBuffer();
	         String inputLine;
	         while ((inputLine = in.readLine()) != null){
	         	sb.append(inputLine);
	         }
	         
	         in.close();
	         
	         message.setContent(sb.toString(), "text/html" );

	         // Send message
	         Transport.send(message);
	         
	         System.out.println("correo enviado: "+toEmail);
	      }catch (MessagingException mex) {
	         mex.printStackTrace();
	      } catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void sendNotificationToApproveBudget(String idStage, String toEmail, String isidUserTo, String isidUserFrom){
		
	      
//	      String to   = toEmail;
	      String to   = "cquezada@cstit.cl";
	      String from = getValue("connect.email.user");
	      String host = getValue("connect.email.host");


	      // Get system properties
	      Properties properties = System.getProperties();
	      properties.setProperty("mail.smtp.host", host);

	      Session session = Session.getDefaultInstance(properties);
	      try{
	         MimeMessage message = new MimeMessage(session);
	         message.setFrom(new InternetAddress(from));
	         message.addRecipient(Message.RecipientType.TO,
	                                  new InternetAddress(to));

	         message.setSubject("Financial Cost Notification; Budget to approve");

	         URL oracle = new URL(contextURLEnviroment+"/notificationToApproveBudget.action?idStage="+idStage+"&isidUserTo="+isidUserTo+"&isidUserFrom="+isidUserFrom);
	         BufferedReader in = new BufferedReader(
	         new InputStreamReader(oracle.openStream()));

	         StringBuffer sb = new StringBuffer();
	         String inputLine;
	         while ((inputLine = in.readLine()) != null){
	         	sb.append(inputLine);
	         }
	         
	         in.close();
	         
	         message.setContent(sb.toString(), "text/html" );

	         // Send message
	         Transport.send(message);
	         
	         System.out.println("correo enviado: "+toEmail);
	      }catch (MessagingException mex) {
	         mex.printStackTrace();
	      } catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void sendNotificationApproveForecast(String idStage, String toEmail, String isidUserTo, String isidUserFrom){
		
	      
//	      String to   = toEmail;
	      String to   = "cquezada@cstit.cl";
	      String from = getValue("connect.email.user");
	      String host = getValue("connect.email.host");


	      // Get system properties
	      Properties properties = System.getProperties();
	      properties.setProperty("mail.smtp.host", host);

	      Session session = Session.getDefaultInstance(properties);
	      try{
	         MimeMessage message = new MimeMessage(session);
	         message.setFrom(new InternetAddress(from));
	         message.addRecipient(Message.RecipientType.TO,
	                                  new InternetAddress(to));

	         message.setSubject("Financial Cost Notification; Forecast approved");

	         URL oracle = new URL(contextURLEnviroment+"/notificationApproveForecast.action?idStage="+idStage+"&isidUserTo="+isidUserTo+"&isidUserFrom="+isidUserFrom);
	         BufferedReader in = new BufferedReader(
	         new InputStreamReader(oracle.openStream()));

	         StringBuffer sb = new StringBuffer();
	         String inputLine;
	         while ((inputLine = in.readLine()) != null){
	         	sb.append(inputLine);
	         }
	         
	         in.close();
	         
	         message.setContent(sb.toString(), "text/html" );

	         // Send message
	         Transport.send(message);
	         
	         System.out.println("correo enviado: "+toEmail);
	      }catch (MessagingException mex) {
	         mex.printStackTrace();
	      } catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void sendNotificationApproveBudget(String idStage, String toEmail, String isidUserTo, String isidUserFrom){
		
	      
//	      String to   = toEmail;
	      String to   = "cquezada@cstit.cl";
	      String from = getValue("connect.email.user");
	      String host = getValue("connect.email.host");


	      // Get system properties
	      Properties properties = System.getProperties();
	      properties.setProperty("mail.smtp.host", host);

	      Session session = Session.getDefaultInstance(properties);
	      try{
	         MimeMessage message = new MimeMessage(session);
	         message.setFrom(new InternetAddress(from));
	         message.addRecipient(Message.RecipientType.TO,
	                                  new InternetAddress(to));

	         message.setSubject("Financial Cost Notification; Budget approved");

	         URL oracle = new URL(contextURLEnviroment+"/notificationApproveBudget.action?idStage="+idStage+"&isidUserTo="+isidUserTo+"&isidUserFrom="+isidUserFrom);
	         BufferedReader in = new BufferedReader(
	         new InputStreamReader(oracle.openStream()));

	         StringBuffer sb = new StringBuffer();
	         String inputLine;
	         while ((inputLine = in.readLine()) != null){
	         	sb.append(inputLine);
	         }
	         
	         in.close();
	         
	         message.setContent(sb.toString(), "text/html" );

	         // Send message
	         Transport.send(message);
	         
	         System.out.println("correo enviado: "+toEmail);
	      }catch (MessagingException mex) {
	         mex.printStackTrace();
	      } catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void sendNotificationRejectForecast(String idStage, String toEmail, String isidUserTo, String isidUserFrom){
		
	      
//	      String to   = toEmail;
	      String to   = "cquezada@cstit.cl";
	      String from = getValue("connect.email.user");
	      String host = getValue("connect.email.host");


	      // Get system properties
	      Properties properties = System.getProperties();
	      properties.setProperty("mail.smtp.host", host);

	      Session session = Session.getDefaultInstance(properties);
	      try{
	         MimeMessage message = new MimeMessage(session);
	         message.setFrom(new InternetAddress(from));
	         message.addRecipient(Message.RecipientType.TO,
	                                  new InternetAddress(to));

	         message.setSubject("Financial Cost Notification; Forecast reject");

	         URL oracle = new URL(contextURLEnviroment+"/notificationRejectForecast.action?idStage="+idStage+"&isidUserTo="+isidUserTo+"&isidUserFrom="+isidUserFrom);
	         BufferedReader in = new BufferedReader(
	         new InputStreamReader(oracle.openStream()));

	         StringBuffer sb = new StringBuffer();
	         String inputLine;
	         while ((inputLine = in.readLine()) != null){
	         	sb.append(inputLine);
	         }
	         
	         in.close();
	         
	         message.setContent(sb.toString(), "text/html" );

	         // Send message
	         Transport.send(message);
	         
	         System.out.println("correo enviado: "+toEmail);
	      }catch (MessagingException mex) {
	         mex.printStackTrace();
	      } catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendNotificationRejectBudget(String idStage, String toEmail, String isidUserTo, String isidUserFrom){
		
	      
//	      String to   = toEmail;
	      String to   = "cquezada@cstit.cl";
	      String from = getValue("connect.email.user");
	      String host = getValue("connect.email.host");


	      // Get system properties
	      Properties properties = System.getProperties();
	      properties.setProperty("mail.smtp.host", host);

	      Session session = Session.getDefaultInstance(properties);
	      try{
	         MimeMessage message = new MimeMessage(session);
	         message.setFrom(new InternetAddress(from));
	         message.addRecipient(Message.RecipientType.TO,
	                                  new InternetAddress(to));

	         message.setSubject("Financial Cost Notification; Budget reject");

	         URL oracle = new URL(contextURLEnviroment+"/notificationRejectBudget.action?idStage="+idStage+"&isidUserTo="+isidUserTo+"&isidUserFrom="+isidUserFrom);
	         BufferedReader in = new BufferedReader(
	         new InputStreamReader(oracle.openStream()));

	         StringBuffer sb = new StringBuffer();
	         String inputLine;
	         while ((inputLine = in.readLine()) != null){
	         	sb.append(inputLine);
	         }
	         
	         in.close();
	         
	         message.setContent(sb.toString(), "text/html" );

	         // Send message
	         Transport.send(message);
	         
	         System.out.println("correo enviado: "+toEmail);
	      }catch (MessagingException mex) {
	         mex.printStackTrace();
	      } catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}