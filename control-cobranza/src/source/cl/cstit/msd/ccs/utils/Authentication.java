package cl.cstit.msd.ccs.utils;


	import java.util.Random;
//	import com.merck.is.arch.Authenticate;

	/**
	 * 
	 * 
	 * 
	 * To change the template for this generated type comment go to Window -
	 * Preferences - Java - Code Generation - Code and Comments
	 */
	public class Authentication
	{

		// atributos
		String session = "";
		boolean authenticated = false;

		/**
		 * 
		 * @param sess
		 */
		public void setSession( String sess )
		{
			// metodo inservible
			sess = "";
			this.session = sess;
		}
		/**
		 * 
		 * @param auth
		 */
		public void setAuthenticated( boolean auth )
		{
			// metodo inservible
			auth = false;
			this.authenticated = auth;
		}
		/**
		 * 
		 * @return
		 */
		public String getSession()
		{
			return this.session;
		}
		/**
		 * 
		 * @return
		 */
		public boolean getAuthenticated()
		{
			return this.authenticated;
		}
		
		/**
		 * 
		 * @param sUser
		 * @param sPassword
		 * @param sDomain
		 * @return
		 */
		// clase para autenticar
		public boolean authenticate( String sUser, String sPassword, String sDomain )
		{

			String       sMessage   = new String( "" );
			try
			{
				System.out.println("3");
				
				System.out.println("sUser: "+sUser);
				System.out.println("sPassword: "+sPassword);
				System.out.println("sDomain: "+sDomain);
				
//				Authenticate.NTAuthenticate( sDomain, sUser, sPassword );
				crearSesion();
				return true;
			}
			catch ( Exception e )
			{
				e.printStackTrace();
				return false;
			} 
		}
		
		private void crearSesion()
		{
			Random rand = new Random();
			long seed = rand.nextLong();
			rand = new Random( seed );
			long key = rand.nextLong();
			this.session = ""+seed+"|"+key+"";
		}

	/*
	 * //Initialize global variables public void init( ServletConfig config )
	 * throws ServletException { super.init( config ); }
	 */
		
		
		
		
	}




