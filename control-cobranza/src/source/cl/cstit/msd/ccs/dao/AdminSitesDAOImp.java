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
import cl.cstit.msd.ccs.vo.CurrencyVO;
import cl.cstit.msd.ccs.vo.SiteVO;
import cl.cstit.msd.ccs.vo.RowCountVO;

public class AdminSitesDAOImp{
	
	private static ResourceBundle propertiesList = ResourceBundle.getBundle("configFileBundle");
	private ConnectionFactory dataConnection = null;
	private static Logger logger = null;
	
	public AdminSitesDAOImp(){
		dataConnection = ConnectionFactory.getInstance();	
		logger = Logger.getLogger(AdminSitesDAOImp.class);
	}
	
    public void saveSite(SiteVO siteVO) throws DAOException{
    	
    	Connection conn = null;

    	try {

    		conn = dataConnection.getConnectionOracleMSD();
    		conn.setAutoCommit(false);
    		
			String queryExecute = 	"SELECT CCS_SEQ_CCS_SITE.NEXTVAL FROM DUAL";
			ResultSet rs = conn.createStatement().executeQuery(queryExecute);
			String idSite = rs.next()==true?rs.getString(1):"";
			
			queryExecute = 	"INSERT INTO CCS_SITE (SIT_ID, SIT_NAME, SIT_LIBRARY_JDE, SIT_FLAG) VALUES("+idSite+",'"+siteVO.getNameSite()+"', '', '"+siteVO.getFlagSite()+"')";
			conn.createStatement().executeUpdate(queryExecute);

			
			queryExecute = 	"INSERT INTO CCS_SITE_CURRENCY (CUR_ID, SIT_ID, SIC_LOCAL_CUR) VALUES("+siteVO.getLocalCurrency().getIdCurrency()+", "+idSite+", 'Y')";
			conn.createStatement().executeUpdate(queryExecute);
			
			
			conn.commit();
			
    	}catch (SQLException e) {
    	
    		try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
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
    
//   public void deleteUser(UserVO userVO) throws DAOException{
//    	
//    	Connection conn = null;
//    	try {
//    		conn = dataConnection.getConnectionOracleMSD();
//    		
//			String queryExecute = 	"update ccs_users set " +
//									"USR_STATUS = 0 " +
//									"where USR_ISID='" + userVO.getIsidUser() +"'"	;
//			conn.createStatement().executeUpdate(queryExecute);
//			
//			logger.debug("queryExecute: "+queryExecute);
//			
//		}catch (SQLException e) {
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
   
   public List<SiteVO> listSites(RowCountVO rowCountVO, int numberPage) throws DAOException{
   	
	int maxRows = Integer.parseInt(propertiesList.getString("paginator.maxrows"));
   	Connection conn = null;
   	List<SiteVO> listSiteVO = new ArrayList<SiteVO>();  	
   	
   	try {   		
   				
   			conn = dataConnection.getConnectionOracleMSD();
   			
   			
			String queryExecute = 	"SELECT COUNT(*) " +
					"FROM CCS_SITE sit, CCS_SITE_CURRENCY sic, CCS_CURRENCY cur " +
					"WHERE sit.SIT_ID = sic.SIT_ID " +
					"AND   sic.CUR_ID = cur.CUR_ID " +
					"AND   sic.SIC_LOCAL_CUR = 'Y' ";
			logger.debug(queryExecute);
			ResultSet rs2 = conn.createStatement().executeQuery(queryExecute);
			if (rs2.next())
				rowCountVO.setRowCount(rs2.getInt(1));
		 	
			

   		
			queryExecute =	"SELECT sit_id, sit_name, sit_flag, CUR_ID, CUR_ACRONYM, row_num_val FROM("
								+"SELECT sit.sit_id, sit.sit_name, sit.sit_flag, cur.CUR_ID, cur.CUR_ACRONYM, ROWNUM as row_num_val" 
								+"FROM CCS_SITE sit, CCS_SITE_CURRENCY sic, CCS_CURRENCY cur" 
								+"WHERE sit.SIT_ID = sic.SIT_ID"
								+"AND sic.CUR_ID = cur.CUR_ID"
								+"AND   sic.SIC_LOCAL_CUR = 'Y')"
								+"WHERE row_num_val > "+(numberPage-1) * maxRows+" AND row_num_val <=  "+numberPage * maxRows+"";
			
			logger.debug(queryExecute);
			
			ResultSet rs = conn.createStatement().executeQuery(queryExecute);
			while (rs.next()){
				SiteVO siteVO = new SiteVO();
				
				siteVO.setIdSite(rs.getString(1));
				siteVO.setNameSite(rs.getString(2));
				siteVO.setFlagSite(rs.getString(3));
				siteVO.setLocalCurrency(new CurrencyVO(rs.getString(4), rs.getString(5)));
				
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
   
   
   public SiteVO getSite(String idSite) throws DAOException{
	   	
	   	Connection conn = null;
	   	SiteVO siteVO = new SiteVO();
	   	try {
	   			conn = dataConnection.getConnectionOracleMSD();
	   		
				String queryExecute = 	"SELECT sit.sit_id, sit.sit_name, sit.sit_flag, cur.CUR_ID, cur.CUR_ACRONYM " +
										"FROM CCS_SITE sit, CCS_SITE_CURRENCY sic, CCS_CURRENCY cur " +
										"WHERE sit.SIT_ID = sic.SIT_ID " +
										"AND   sic.CUR_ID = cur.CUR_ID " +
										"AND   sic.SIC_LOCAL_CUR = 'Y' " +
										"AND   sit.SIT_ID = "+idSite+" ";
				
				logger.debug(queryExecute);
				ResultSet rs = conn.createStatement().executeQuery(queryExecute);
				
				if (rs.next()){
					
					siteVO.setIdSite(rs.getString(1));
					siteVO.setNameSite(rs.getString(2));
					siteVO.setFlagSite(rs.getString(3));
					siteVO.setLocalCurrency(new CurrencyVO(rs.getString(4), rs.getString(5)));
					
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
	   		return siteVO;
	   }

   public void updateUser(SiteVO siteVO) throws DAOException{
   	
	   	Connection conn = null;
	   	try {
	   		conn = dataConnection.getConnectionOracleMSD();
	   		conn.setAutoCommit(false);
	   		
			String queryExecute = 	"UPDATE CCS_SITE SET sit_name = '"+siteVO.getNameSite() +"', sit_flag='"+siteVO.getFlagSite() +"' WHERE sit_id = "+siteVO.getIdSite() +"";
			conn.createStatement().executeUpdate(queryExecute);

			
			queryExecute = 	"select cur.CUR_ID from CCS_SITE sit, CCS_SITE_CURRENCY sic, CCS_CURRENCY cur " +
					"WHERE sit.SIT_ID = sic.SIT_ID " +
					"AND   sic.CUR_ID = cur.CUR_ID " +
					"AND   sit.sit_id = "+siteVO.getIdSite()+" " +
					"AND   sic.SIC_LOCAL_CUR = 'Y' ";
			ResultSet rs = conn.createStatement().executeQuery(queryExecute);
			String idCurrentCurrency = rs.next()==true?rs.getString(1):"";
			
			
			queryExecute = 	"UPDATE CCS_SITE_CURRENCY SET CUR_ID = "+siteVO.getLocalCurrency().getIdCurrency() +" WHERE CUR_ID = "+idCurrentCurrency+" AND SIT_ID = "+siteVO.getIdSite();
			conn.createStatement().executeUpdate(queryExecute);
			
			
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


}