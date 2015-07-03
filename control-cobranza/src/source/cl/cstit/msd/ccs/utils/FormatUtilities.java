package cl.cstit.msd.ccs.utils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FormatUtilities {

	
	public static String getSubString(String campo, int posIni, int posFin){
		
		if (campo.length()>posFin){
			return campo.substring(posIni, posFin);
		}else{
			return campo;
		}
	}
	
	public static String getCurrentDate(String format){
		DateFormat dateFormat = new SimpleDateFormat(format);
		Calendar cal = Calendar.getInstance();
		return dateFormat.format(cal.getTime());
	}	
	
	public static String addCurrentDateByDays(int days){
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Calendar cal = Calendar.getInstance();
		
		cal.add(Calendar.DATE,days);
		
		return dateFormat.format(cal.getTime());
	}
	
	public static String getCurrentDateMonth(){ 
		DateFormat dateFormat = new SimpleDateFormat("MM");
		Calendar cal = Calendar.getInstance();
		return dateFormat.format(cal.getTime());
	}	
	
	public static String getCurrentDateDay(){
		DateFormat dateFormat = new SimpleDateFormat("dd");
		Calendar cal = Calendar.getInstance();
		return dateFormat.format(cal.getTime());
	}	
	
	public static String getCurrentDateYear(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy");
		Calendar cal = Calendar.getInstance();
		return dateFormat.format(cal.getTime());
	}		
	
	public static int getMonthNumber(String date){
		String[] split = date.split("/");
		return Integer.parseInt(split[1]);
	}	
	
	public static String getCurrentHourJDE(){
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		
		return dateFormat.format(cal.getTime());
	}	
	
	public static String getMonthName(int idMonth){
		
		if (idMonth == 1) return "jan";
		if (idMonth == 2) return "feb";
		if (idMonth == 3) return "mar";
		if (idMonth == 4) return "apr";
		if (idMonth == 5) return "may";
		if (idMonth == 6) return "jun";
		if (idMonth == 7) return "jul";
		if (idMonth == 8) return "aug";
		if (idMonth == 9) return "sep";
		if (idMonth == 10) return "oct";
		if (idMonth == 11) return "nov";
		if (idMonth == 12) return "dec";
		
		return "-";
	}
	
	
	public static String dateToStringFormat(Date date, String format){
		
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		
		return formatter.format(date);
	}
	
	public static BigDecimal round(BigDecimal d, int scale, boolean roundUp) {
		  int mode = (roundUp) ? BigDecimal.ROUND_UP : BigDecimal.ROUND_DOWN;
		  return d.setScale(scale, mode);
	}
	
	public static String getDecimalNumber(float number, double decimals){
		double value =  Math.pow(10d, decimals) ;
		return String.valueOf(Math.round(number*value)/value);
	}
	
	public static void main(String[] arg){
//		System.out.println(getCurrentDate());
		System.out.println(addCurrentDateByDays(1));
		System.out.println(Integer.parseInt(getCurrentDateMonth()));
	}
}



