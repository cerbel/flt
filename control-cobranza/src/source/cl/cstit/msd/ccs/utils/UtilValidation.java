package cl.cstit.msd.ccs.utils;

public class UtilValidation {

	public static boolean isNumeric(String cadena){
		try {
			int numero = (int)Math.floor(Double.parseDouble(cadena));

			if (numero < 0)
				return false;
			
			return true;
		} catch (NumberFormatException nfe){
			return false;
		}
	}
	
}
