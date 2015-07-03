package cl.cstit.msd.ccs.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class InitLoadProject extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	static Logger logger = Logger.getLogger(InitLoadProject.class);
	
	
	SimpleDateFormat sdf= new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	Calendar initDateCalendar   = Calendar.getInstance(TimeZone.getTimeZone("GMT-4"));
	Calendar finishDateCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT-4"));

	public void init() {
		
		String prefix =  getServletContext().getRealPath("/");
		String file = getInitParameter("log4j-init-file");
		
		System.out.println("file: "+file);
		if(file != null) {
			try{
				
				PropertyConfigurator.configure(prefix+file);
				logger.debug("Logger configurado correctamente!");
			}catch(Exception e){
				System.out.println("e.getMessage(): "+e.getMessage());
			}
		}
	}
}	