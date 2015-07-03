package cl.cstit.msd.ccs.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import cl.cstit.msd.ccs.exception.DAOException;
import cl.cstit.msd.ccs.utils.ConnectionFactory;
import cl.cstit.msd.ccs.utils.ConstantsFinalObject;
import cl.cstit.msd.ccs.vo.PagoCobranzaVO;
import cl.cstit.msd.ccs.vo.ProfileVO;
import cl.cstit.msd.ccs.vo.ProveedorVO;
import cl.cstit.msd.ccs.vo.RowCountVO;
import cl.cstit.msd.ccs.vo.SiteVO;
import cl.cstit.msd.ccs.vo.UserVO;

public class AdminUserDAOImp{
	
	
	private ConnectionFactory dataConnection = null;
	private static Logger logger = Logger.getLogger(AdminUserDAOImp.class);
	
	private static ResourceBundle propertiesList = ResourceBundle.getBundle("configFileBundle");
	
	public AdminUserDAOImp(){
		dataConnection = ConnectionFactory.getInstance();	
	}
	
	
	
	
	
	
	public void saveUser(PagoCobranzaVO pagoCobranzaVO) throws DAOException{
    	
    	Connection conn = null;
    	try {

    		conn = dataConnection.getConnectionMysql();
    		
    		logger.debug("conn: "+conn);
    		
    		//vareifica que no exista otra factura con el mismo id
//    		ResultSet rs = conn.createStatement().executeQuery("SELECT 1 FROM flt_pago_proveedores WHERE pap_numero_factura_proveedor = '"+pagoCobranzaVO.getFactura()+"' AND pap_rut_proveedor = '"+pagoCobranzaVO.getRut()+"' ");
//    		if (rs.next())
//    			throw new DAOException("El número de factura asignado al proveedor ya existe");
    		
    		String queryExecute="";
    		
    		ResultSet verifica = conn.createStatement().executeQuery("select 1 from flt_proveedor where rut_proveedor = '"+pagoCobranzaVO.getRut()+"' ");
    		if (verifica.next())
    			queryExecute = "UPDATE flt_proveedor set nombre_proveedor = '"+pagoCobranzaVO.getNombre()+"' WHERE rut_proveedor = '"+pagoCobranzaVO.getRut()+"' ";
    		else
    			queryExecute = "INSERT INTO flt_proveedor (rut_proveedor, nombre_proveedor) VALUES ( '"+pagoCobranzaVO.getRut()+"', '"+pagoCobranzaVO.getNombre()+"');";
    		
    		conn.createStatement().executeUpdate(queryExecute);
    		
    		logger.debug("QUERY flt_proveedor: "+queryExecute);
    		
    		
    		queryExecute = "INSERT INTO flt_pago_proveedores(pap_rut_proveedor, pap_fecha_vencimiento_proveedor , pap_monto_pago_proveedor, pap_numero_factura_proveedor, pap_estado_proveedor, pap_observacion_proveedor) "+
						   " VALUES ('"+pagoCobranzaVO.getRut()+"', '"+pagoCobranzaVO.getFechaV()+"', "+pagoCobranzaVO.getMonto()+", '"+pagoCobranzaVO.getFactura()+"', 'NO PAGADO', '"+pagoCobranzaVO.getObservacion()+"'); ";
			
    		logger.debug("queryExecute: "+queryExecute); 
			
			conn.createStatement().executeUpdate(queryExecute);

			
    	}catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(ConstantsFinalObject.EXCEPTION_DAO_GENERIC_ERR_COD, e);		
		}finally{		
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					throw new DAOException(ConstantsFinalObject.EXCEPTION_DAO_GENERIC_ERR_COD, e);
				}
		}
    }	
	
	
	
//    public void saveUser(UserVO userVO) throws DAOException{
//    	
//    	Connection conn = null;
//
//    	try {
//
//    		conn = dataConnection.getConnectionOracleMSD();
//    		conn.setAutoCommit(false);
//			String queryExecute = 	"INSERT INTO ccs_users (USR_ISID, USR_USERNAME , USR_EMAIL, PER_ID, USR_STATUS) " +
//									"VALUES ('"+userVO.getIsidUser()+"','"+userVO.getNameUser()+"','"+userVO.getEmailUser()+"', "+userVO.getIdProfileUser()+", 1)";
//			
//			logger.debug("queryExecute: "+queryExecute);
//			
//			conn.createStatement().executeUpdate(queryExecute);
//
//			String[] split = userVO.getListSite().split(",");
//			for (int i = 0; i < split.length; i++) {
//				queryExecute = "INSERT INTO CCS_SITES_USER(SIT_ID, USR_ISID) "
//					         + "VALUES("+split[i]+",'"+userVO.getIsidUser()+"')";
//		
//				logger.debug("queryExecute: "+queryExecute);
//				conn.createStatement().executeUpdate(queryExecute);
//			}
//			
//			
//			
//			conn.commit();
//			
//    	}catch (SQLException e) {
//    	
//    		try {
//				conn.rollback();
//			} catch (SQLException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			e.printStackTrace();
//			throw new DAOException(ConstantsFinalObject.EXCEPTION_DAO_GENERIC_ERR_COD, e);		
//		}finally{		
//			if (conn != null)
//				try {
//					conn.close();
//				} catch (SQLException e) {
//					throw new DAOException(ConstantsFinalObject.EXCEPTION_DAO_GENERIC_ERR_COD, e);
//				}
//		}
//    }	
    
   public void deleteUser(UserVO userVO) throws DAOException{
    	
    	Connection conn = null;
    	try {
    		conn = dataConnection.getConnectionOracleMSD();
    		
			String queryExecute = 	"update ccs_users set " +
									"USR_STATUS = 0 " +
									"where USR_ISID='" + userVO.getIsidUser() +"'"	;
			conn.createStatement().executeUpdate(queryExecute);
			
			logger.debug("queryExecute: "+queryExecute);
			
		}catch (SQLException e) {
			throw new DAOException(ConstantsFinalObject.EXCEPTION_DAO_GENERIC_ERR_COD, e);		
		}finally{		
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					throw new DAOException(ConstantsFinalObject.EXCEPTION_DAO_GENERIC_ERR_COD, e);
				}
		}
    }
   
   
   public String obtieneProveedor(String rutProveedor)throws DAOException{
		   
   	Connection conn = null;
   	String nombreProveedor = null;
   	try {
   			conn = dataConnection.getConnectionMysql();
			String queryExecute = "SELECT nombre_proveedor from flt_proveedor where rut_proveedor = '"+rutProveedor+"' ";
			
			ResultSet rs = conn.createStatement().executeQuery(queryExecute);
			if (rs.next())
				nombreProveedor = rs.getString(1);
			
		}catch (SQLException e) {
			throw new DAOException(ConstantsFinalObject.EXCEPTION_DAO_GENERIC_ERR_COD, e);		
		}finally{		
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					throw new DAOException(ConstantsFinalObject.EXCEPTION_DAO_GENERIC_ERR_COD, e);
				}
		}
   		return nombreProveedor;
   }
   
//modificar
//   public String obtieneRutProveedor(String nombreProveedor)throws DAOException{
//	   
//	   	Connection conn = null;
//	   	String rutProveedor = null;
//	   	try {
//	   			conn = dataConnection.getConnectionMysql();
//				String queryExecute = "SELECT rut_proveedor, nombre_proveedor from flt_proveedor where nombre_proveedor LIKE '%"+nombreProveedor+"%' ";
//				
//				ResultSet rs = conn.createStatement().executeQuery(queryExecute);
//				if (rs.next())
//					rutProveedor = rs.getString(1);
//				
//			}catch (SQLException e) {
//				throw new DAOException(ConstantsFinalObject.EXCEPTION_DAO_GENERIC_ERR_COD, e);		
//			}finally{		
//				if (conn != null)
//					try {
//						conn.close();
//					} catch (SQLException e) {
//						throw new DAOException(ConstantsFinalObject.EXCEPTION_DAO_GENERIC_ERR_COD, e);
//					}
//			}
//	   		return rutProveedor;
//	   }
   
   
   
   
   public List<ProveedorVO> obtieneRutProveedor(String nombreProveedor)throws DAOException{
	   
	   	Connection conn = null;
//	   	String rutProveedor = null;
	   	List<ProveedorVO> listProveedorVO = new ArrayList<ProveedorVO>();
	   	try {
	   			conn = dataConnection.getConnectionMysql();
				String queryExecute = "SELECT rut_proveedor, nombre_proveedor from flt_proveedor where nombre_proveedor LIKE '%"+nombreProveedor+"%' ORDER BY nombre_proveedor ";
//				String queryExecute = "SELECT rut_proveedor from flt_proveedor where nombre_proveedor= '"+nombreProveedor+"' ";
				
				ResultSet rs = conn.createStatement().executeQuery(queryExecute);
//				if (rs.next())
//					rutProveedor 	= rs.getString(1);
//					nombreProveedor	= rs.getString(2);
				
				while (rs.next()){
					ProveedorVO pVO = new ProveedorVO();
					pVO.setRutProveedor(rs.getString(1));
					pVO.setNombreProveedor(rs.getString(2));
					
					listProveedorVO.add(pVO);
				}
				
			}catch (SQLException e) {
				throw new DAOException(ConstantsFinalObject.EXCEPTION_DAO_GENERIC_ERR_COD, e);		
			}finally{		
				if (conn != null)
					try {
						conn.close();
					} catch (SQLException e) {
						throw new DAOException(ConstantsFinalObject.EXCEPTION_DAO_GENERIC_ERR_COD, e);
					}
			}
	   		return listProveedorVO;
	   	
	   }
   
   
   public List<UserVO> listUser(UserVO usrVO, RowCountVO rowCountVO, int numberPage)throws DAOException{
   		   
	int maxRows = Integer.parseInt(propertiesList.getString("paginator.maxrows"));	
   	Connection conn = null;
   	List<UserVO> listUserVO = new ArrayList<UserVO>();
   	try {
   			conn = dataConnection.getConnectionOracleMSD();
   		
   			
			String queryExecute = 	"SELECT COUNT(*) "
					  + "FROM ccs_users a, ccs_profile b "
					  + "where a.per_id = b.pro_id "
					  + "and USR_STATUS = 1 ";
			if (usrVO != null){
				queryExecute += "and USR_ISID like('%" + usrVO.getIsidUser() +"%')";
			}
			
			ResultSet rs = conn.createStatement().executeQuery(queryExecute);
			if (rs.next())
				rowCountVO.setRowCount(rs.getInt(1));
			
			logger.debug("totalRows: "+rowCountVO.getRowCount());
			
			queryExecute = 	"SELECT USR_ISID, USR_USERNAME , USR_EMAIL, PER_ID, PRO_NAME, row_num_val FROM( " 
						+ "SELECT USR_ISID, USR_USERNAME , USR_EMAIL, PER_ID, PRO_NAME, ROWNUM as row_num_val FROM ccs_users a, ccs_profile b where a.per_id = b.pro_id and USR_STATUS = 1)"
						+"WHERE row_num_val > "+(numberPage-1) * maxRows+" AND row_num_val <=  "+numberPage * maxRows+"";
			
			if (usrVO != null){
				queryExecute += "and USR_ISID like('%" + usrVO.getIsidUser() +"%')";
			}
			
			logger.debug(queryExecute);
			rs = conn.createStatement().executeQuery(queryExecute);
			
			while (rs.next()){
				UserVO userVO = new UserVO();
				userVO.setIsidUser(rs.getString(1));
				userVO.setNameUser(rs.getString(2));
				userVO.setEmailUser(rs.getString(3));
				userVO.setIdProfileUser(rs.getString(4));
				userVO.setNameProfileUser(rs.getString(5));
				listUserVO.add(userVO);
			}
		}catch (SQLException e) {
			throw new DAOException(ConstantsFinalObject.EXCEPTION_DAO_GENERIC_ERR_COD, e);		
		}finally{		
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					throw new DAOException(ConstantsFinalObject.EXCEPTION_DAO_GENERIC_ERR_COD, e);
				}
		}
   		return listUserVO;
   }
   
   

   public void updateUser(UserVO userVO) throws DAOException{
   	
	   	Connection conn = null;
	   	try {
	   		conn = dataConnection.getConnectionOracleMSD();
	   		conn.setAutoCommit(false);
			String queryExecute = 	"update ccs_users set "
								+   "USR_USERNAME = '"+userVO.getNameUser()+"', "
								+   "USR_EMAIL = '"+userVO.getEmailUser()+"' , "
								+   "PER_ID = " + userVO.getIdProfileUser()
								+   " where USR_ISID = '" + userVO.getIsidUser() +"'";
			
			logger.debug("queryExecute: "+queryExecute);
			
			conn.createStatement().executeUpdate(queryExecute);
			
			queryExecute = "delete from CCS_SITES_USER "
			             + "where USR_ISID = '"+userVO.getIsidUser()+"'";

			logger.debug("queryExecute: "+queryExecute);

			conn.createStatement().executeUpdate(queryExecute);
			
			String[] split = userVO.getListSite().split(",");
			for (int i = 0; i < split.length; i++) {
				queryExecute = "INSERT INTO CCS_SITES_USER(SIT_ID, USR_ISID) "
					         + "VALUES("+split[i]+",'"+userVO.getIsidUser()+"')";
		
				logger.debug("queryExecute: "+queryExecute);
				conn.createStatement().executeUpdate(queryExecute);
			}
			
			
			
			conn.commit();
		}catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			throw new DAOException(ConstantsFinalObject.EXCEPTION_DAO_GENERIC_ERR_COD, e);		
		}finally{		
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					throw new DAOException(ConstantsFinalObject.EXCEPTION_DAO_GENERIC_ERR_COD, e);
				}
		}
   }

   public List<ProfileVO> listProfile()throws DAOException{
	   		
   	   	Connection conn = null;
   	   	List<ProfileVO> listProfileVO = new ArrayList<ProfileVO>();
   	   	try {
   	   			conn = dataConnection.getConnectionOracleMSD();
   	   		
   				String queryExecute = 	"select pro_id, pro_name from ccs_profile ";
   				
   				logger.debug(queryExecute);
   				ResultSet rs = conn.createStatement().executeQuery(queryExecute);
   				
   				while (rs.next()){
   					ProfileVO profileVO = new ProfileVO();
   					profileVO.setIdProfile(rs.getString(1));
   					profileVO.setNameProfile(rs.getString(2));
   					listProfileVO.add(profileVO);
   				}
   			}catch (SQLException e) {
   				throw new DAOException(ConstantsFinalObject.EXCEPTION_DAO_GENERIC_ERR_COD, e);		
   			}finally{		
   				if (conn != null)
   					try {
   						conn.close();
   					} catch (SQLException e) {
   						throw new DAOException(ConstantsFinalObject.EXCEPTION_DAO_GENERIC_ERR_COD, e);
   					}
   			}
   	   		return listProfileVO;
   	}
   
   public List<SiteVO> listSite() throws DAOException{
  		
  	   	Connection conn = null;
  	   	List<SiteVO> listSiteVO = new ArrayList<SiteVO>();
  	   	try {
  	   			conn = dataConnection.getConnectionOracleMSD();
  	   		
  				String queryExecute = 	"select sit_id, sit_name, sit_flag from CCS_SITE ORDER BY 2 ASC ";
  				
  				logger.debug(queryExecute);
  				ResultSet rs = conn.createStatement().executeQuery(queryExecute);
  				
  				while (rs.next()){
  					SiteVO siteVO = new SiteVO();
  					siteVO.setIdSite(rs.getString(1));
  					siteVO.setNameSite(rs.getString(2));
  					
  					siteVO.setFlagSite(rs.getString(3));
  					
  					listSiteVO.add(siteVO);
  				}
  			}catch (SQLException e) {
  				throw new DAOException(ConstantsFinalObject.EXCEPTION_DAO_GENERIC_ERR_COD, e);		
  			}finally{		
  				if (conn != null)
  					try {
  						conn.close();
  					} catch (SQLException e) {
  						throw new DAOException(ConstantsFinalObject.EXCEPTION_DAO_GENERIC_ERR_COD, e);
  					}
  			}
  	   		return listSiteVO;
  	}
    
   
	public List<SiteVO> listSiteByUser(String isidUser) throws DAOException{
 		
 	   	Connection conn = null;
 	   	List<SiteVO> listSiteVO = new ArrayList<SiteVO>();
 	   	try {
 	   			conn = dataConnection.getConnectionOracleMSD();
 	   		
 				String queryExecute = 	"  select a.sit_id, a.sit_name, DECODE(b.USR_ISID,null,' ', 'checked=''checked'''), a.sit_flag"
 									  + "  from CCS_SITE a "
 									  + "  left join CCS_SITES_USER b "
 									  + "  on a.SIT_ID = b.SIT_ID "
 									  + " and b.USR_ISID =  '"+isidUser+"'";
 				
 				logger.debug(queryExecute);
 				ResultSet rs = conn.createStatement().executeQuery(queryExecute);
 				
 				while (rs.next()){
 					SiteVO siteVO = new SiteVO();
 					siteVO.setIdSite(rs.getString(1));
 					siteVO.setNameSite(rs.getString(2));
 					siteVO.setCheckedSite(rs.getString(3));
 					
 					siteVO.setFlagSite(rs.getString(4));
 					
 					listSiteVO.add(siteVO);
 				}
 			}catch (SQLException e) {
 				throw new DAOException(ConstantsFinalObject.EXCEPTION_DAO_GENERIC_ERR_COD, e);		
 			}finally{		
 				if (conn != null)
 					try {
 						conn.close();
 					} catch (SQLException e) {
 						throw new DAOException(ConstantsFinalObject.EXCEPTION_DAO_GENERIC_ERR_COD, e);
 					}
 			}
 	   		return listSiteVO;
 	}
}