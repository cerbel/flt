package cl.cstit.msd.ccs.actions;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		float kilobytes = 3.2682539f;
		
//		float newKB = (float) (Math.round(kilobytes*1.0)/1.0);
		
		System.out.println(round(new BigDecimal(kilobytes), 0, true)); 
		
//		BigDecimal kilobytes = ;
		
//		float num = new BigDecimal(kilobytes);
		
		BigDecimal num = new BigDecimal(kilobytes - Math.floor(kilobytes));
		
//		if (num.floatValue() > 0.5f)
//			System.out.println("mayor: "+Math.round(kilobytes*1.0) /1.0);
//		else
//			System.out.println("menor: "+kilobytes);
		
//		if ( > )
		
//		BigDecimal rounded = new BigDecimal(kilobytes).setScale(0, RoundingMode.HALF_UP);
//		BigDecimal rounded = new BigDecimal(kilobytes).round(new MathContext(0, RoundingMode.HALF_UP));
//		System.out.println(kilobytes + " -> " + rounded.floatValue());

				
		
	}

	
	public static BigDecimal round(BigDecimal d, int scale, boolean roundUp) {
		  int mode = (roundUp) ? BigDecimal.ROUND_UP : BigDecimal.ROUND_DOWN;
		  return d.setScale(scale, mode);
	}
	
}
