package cl.cstit.msd.ccs.exception;


import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class DAOException extends Exception {

	private static final long serialVersionUID = 1L;

	private static ResourceBundle propertiesList = ResourceBundle.getBundle("messagesErrorsBundle");
	static Logger logger = Logger.getLogger(DAOException.class);
	
	@Override
	public String getMessage() {
		String message = new String();
		try{
			message = propertiesList.getString("daoexception.err."+super.getMessage());	
		}catch(MissingResourceException e){
			return super.getMessage();
		}
		
		return message;
	}
	
	public DAOException(String code, Exception e){		
		super(code.replaceAll("[\n\r]",""));
	}
	
	public DAOException(String code) {
		super(code);
	}	
}
