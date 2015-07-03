package cl.cstit.msd.ccs.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import cl.cstit.msd.ccs.exception.DAOException;
import cl.cstit.msd.ccs.utils.ConnectionFactory;
import cl.cstit.msd.ccs.utils.ConstantsFinalObject;
import cl.cstit.msd.ccs.vo.GeneralComboVO;

public class GeneralTransactionsDAOImp{
	
	
	private ConnectionFactory dataConnection = null;
	private static Logger logger = null;
	
	public GeneralTransactionsDAOImp(){
		dataConnection = ConnectionFactory.getInstance();	
		logger = Logger.getLogger(GeneralTransactionsDAOImp.class);
	}
	
	public List<GeneralComboVO> listProductFamilies() throws DAOException{
		
		Connection conn = null;
		List<GeneralComboVO> listGeneralComboVO = new ArrayList<GeneralComboVO>();
		try {
			conn = dataConnection.getConnectionOracleMSD();
			
			String queryExecute = 	"select PFA_ID, PFA_NAME from ccs_product_family";
			logger.debug("queryExecute: "+queryExecute);
			
			ResultSet rs = conn.createStatement().executeQuery(queryExecute);
			
			while (rs.next()){
				GeneralComboVO generalComboVO = new GeneralComboVO();
				
				generalComboVO.setIdGeneralCombo(rs.getString(1));
				generalComboVO.setNameGeneralCombo(rs.getString(2));
				
				listGeneralComboVO.add(generalComboVO);
			}
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
		
		return listGeneralComboVO;
	}	
    
    
    
	public List<GeneralComboVO> listProductTransports() throws DAOException{
		
		Connection conn = null;
		List<GeneralComboVO> listGeneralComboVO = new ArrayList<GeneralComboVO>();
		try {
			conn = dataConnection.getConnectionOracleMSD();
			
			String queryExecute = 	"SELECT TRA_ID, TRA_NAME from CCS_TRANSPORT";
			logger.debug("queryExecute: "+queryExecute);
			
			ResultSet rs = conn.createStatement().executeQuery(queryExecute);
			
			while (rs.next()){
				GeneralComboVO generalComboVO = new GeneralComboVO();
				
				generalComboVO.setIdGeneralCombo(rs.getString(1));
				generalComboVO.setNameGeneralCombo(rs.getString(2));
				
				
				listGeneralComboVO.add(generalComboVO);
				
			}
			
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
		
		return listGeneralComboVO;
	}	
	
	
	public List<GeneralComboVO> listProductUnitMeasure() throws DAOException{
		
		Connection conn = null;
		List<GeneralComboVO> listGeneralComboVO = new ArrayList<GeneralComboVO>();
		try {
			conn = dataConnection.getConnectionOracleMSD();
			
			String queryExecute = "SELECT PRM_ID, PRM_NAME from CCS_PRODUCT_UNIT_MEASURE";
			logger.debug("queryExecute: "+queryExecute);
			
			ResultSet rs = conn.createStatement().executeQuery(queryExecute);
			
			while (rs.next()){
				GeneralComboVO generalComboVO = new GeneralComboVO();
				
				generalComboVO.setIdGeneralCombo(rs.getString(1));
				generalComboVO.setNameGeneralCombo(rs.getString(2));
				
				
				listGeneralComboVO.add(generalComboVO);
				
			}
			
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
		
		return listGeneralComboVO;
	}	
	
	
	public List<GeneralComboVO> listSiteSource(String idStage) throws DAOException{
		
		Connection conn = null;
		List<GeneralComboVO> listGeneralComboVO = new ArrayList<GeneralComboVO>();
		try {
			conn = dataConnection.getConnectionOracleMSD();
			
			String queryExecute = "SELECT SIS_ID, SIS_NAME FROM CCS_SITE_SOURCE WHERE STG_ID = "+idStage+" order by SIS_NAME";
			logger.debug("queryExecute: "+queryExecute);
			
			ResultSet rs = conn.createStatement().executeQuery(queryExecute);
			
			while (rs.next()){
				GeneralComboVO generalComboVO = new GeneralComboVO();
				
				generalComboVO.setIdGeneralCombo(rs.getString(1));
				generalComboVO.setNameGeneralCombo(rs.getString(2));
				
				
				listGeneralComboVO.add(generalComboVO);
			}
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
		
		return listGeneralComboVO;
	}
	
//	public List<GeneralComboVO> listSiteSourceOthers() throws DAOException{
//		
//		Connection conn = null;
//		List<GeneralComboVO> listGeneralComboVO = new ArrayList<GeneralComboVO>();
//		try {
//			conn = dataConnection.getConnectionOracleMSD();
//			
//			String queryExecute = "SELECT SIS_ID, SIS_NAME FROM CCS_SITE_SOURCE WHERE TRA_ID IN (1,3)";
//			logger.debug("queryExecute: "+queryExecute);
//			
//			ResultSet rs = conn.createStatement().executeQuery(queryExecute);
//			
//			while (rs.next()){
//				GeneralComboVO generalComboVO = new GeneralComboVO();
//				
//				generalComboVO.setIdGeneralCombo(rs.getString(1));
//				generalComboVO.setNameGeneralCombo(rs.getString(2));
//				
//				
//				listGeneralComboVO.add(generalComboVO);
//			}
//		}catch (SQLException e) {
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
//		
//		return listGeneralComboVO;
//	}
	
	
	
	
	public List<GeneralComboVO> listTypeStage() throws DAOException{
		
		Connection conn = null;
		List<GeneralComboVO> listGeneralComboVO = new ArrayList<GeneralComboVO>();
		try {
			conn = dataConnection.getConnectionOracleMSD();
			
			String queryExecute = "SELECT STT_ID, STT_NAME FROM CCS_STAGE_TYPE WHERE STT_ID <> 5";
			logger.debug("queryExecute: "+queryExecute);
			
			ResultSet rs = conn.createStatement().executeQuery(queryExecute);
			
			while (rs.next()){
				GeneralComboVO generalComboVO = new GeneralComboVO();
				
				generalComboVO.setIdGeneralCombo(rs.getString(1));
				generalComboVO.setNameGeneralCombo(rs.getString(2));
				
				
				listGeneralComboVO.add(generalComboVO);
			}
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
		
		return listGeneralComboVO;
	}
	
	
	public List<GeneralComboVO> listProductTypes() throws DAOException{
		
		Connection conn = null;
		List<GeneralComboVO> listGeneralComboVO = new ArrayList<GeneralComboVO>();
		try {
			conn = dataConnection.getConnectionOracleMSD();
			
			String queryExecute = "SELECT PTY_ID, PTY_NAME FROM CCS_PRODUCT_TYPE ";
			logger.debug("queryExecute: "+queryExecute);
			
			ResultSet rs = conn.createStatement().executeQuery(queryExecute);
			while (rs.next()){
				GeneralComboVO generalComboVO = new GeneralComboVO();
				
				generalComboVO.setIdGeneralCombo(rs.getString(1));
				generalComboVO.setNameGeneralCombo(rs.getString(2));
				
				
				listGeneralComboVO.add(generalComboVO);
			}
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
		
		return listGeneralComboVO;
	}
	
	
	
	public List<GeneralComboVO> listProductTrade() throws DAOException{
		
		Connection conn = null;
		List<GeneralComboVO> listGeneralComboVO = new ArrayList<GeneralComboVO>();
		try {
			conn = dataConnection.getConnectionOracleMSD();
			
			String queryExecute = "SELECT PTR_ID, PRT_NAME FROM CCS_PRODUCT_TRADE ";
			logger.debug("queryExecute: "+queryExecute);
			
			ResultSet rs = conn.createStatement().executeQuery(queryExecute);
			while (rs.next()){
				GeneralComboVO generalComboVO = new GeneralComboVO();
				
				generalComboVO.setIdGeneralCombo(rs.getString(1));
				generalComboVO.setNameGeneralCombo(rs.getString(2));
				
				
				listGeneralComboVO.add(generalComboVO);
			}
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
		return listGeneralComboVO;
	}
	
	
	public List<GeneralComboVO> listProductPresentation() throws DAOException{
		
		Connection conn = null;
		List<GeneralComboVO> listGeneralComboVO = new ArrayList<GeneralComboVO>();
		try {
			conn = dataConnection.getConnectionOracleMSD();
			
			String queryExecute = "SELECT PRP_ID, PRP_NAME FROM CCS_PRODUCT_PRESENTATION ";
			logger.debug("queryExecute: "+queryExecute);
			
			ResultSet rs = conn.createStatement().executeQuery(queryExecute);
			while (rs.next()){
				GeneralComboVO generalComboVO = new GeneralComboVO();
				
				generalComboVO.setIdGeneralCombo(rs.getString(1));
				generalComboVO.setNameGeneralCombo(rs.getString(2));
				
				listGeneralComboVO.add(generalComboVO);
			}
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
		return listGeneralComboVO;
	}
	
	public List<GeneralComboVO> listProductTypeLoad() throws DAOException{
		
		Connection conn = null;
		List<GeneralComboVO> listGeneralComboVO = new ArrayList<GeneralComboVO>();
		try {
			conn = dataConnection.getConnectionOracleMSD();
			
			String queryExecute = "SELECT PTL_ID, PTL_NAME FROM CCS_PRODUCT_TYPE_LOAD ";
			logger.debug("queryExecute: "+queryExecute);
			
			ResultSet rs = conn.createStatement().executeQuery(queryExecute);
			while (rs.next()){
				GeneralComboVO generalComboVO = new GeneralComboVO();
				
				generalComboVO.setIdGeneralCombo(rs.getString(1));
				generalComboVO.setNameGeneralCombo(rs.getString(2));
				
				listGeneralComboVO.add(generalComboVO);
			}
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
		return listGeneralComboVO;
	}
	
//	public List<GeneralComboVO> listFreightModes() throws DAOException{
//		
//		Connection conn = null;
//		List<GeneralComboVO> listGeneralComboVO = new ArrayList<GeneralComboVO>();
//		try {
//			conn = dataConnection.getConnectionOracleMSD();
//			
//			String queryExecute = "SELECT FRM_ID, FRM_NAME FROM CCS_STAGE_FREIGHT_MODE ";
//			logger.debug("queryExecute: "+queryExecute);
//			
//			ResultSet rs = conn.createStatement().executeQuery(queryExecute);
//			while (rs.next()){
//				GeneralComboVO generalComboVO = new GeneralComboVO();
//				
//				generalComboVO.setIdGeneralCombo(rs.getString(1));
//				generalComboVO.setNameGeneralCombo(rs.getString(2));
//				
//				listGeneralComboVO.add(generalComboVO);
//			}
//		}catch (SQLException e) {
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
//		return listGeneralComboVO;
//	}
	
	public List<GeneralComboVO> listCurrency() throws DAOException{
		
		Connection conn = null;
		List<GeneralComboVO> listGeneralComboVO = new ArrayList<GeneralComboVO>();
		try {
			conn = dataConnection.getConnectionOracleMSD();
			
			String queryExecute = "SELECT CUR_ID, CUR_ACRONYM FROM CCS_CURRENCY ";
			logger.debug("queryExecute: "+queryExecute);
			
			ResultSet rs = conn.createStatement().executeQuery(queryExecute);
			while (rs.next()){
				GeneralComboVO generalComboVO = new GeneralComboVO();
				
				generalComboVO.setIdGeneralCombo(rs.getString(1));
				generalComboVO.setNameGeneralCombo(rs.getString(2));
				
				listGeneralComboVO.add(generalComboVO);
			}
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
		return listGeneralComboVO;
	}
	
	
//	public void deleteStageTrash() throws DAOException{
//		
//		Connection conn = null;
//		try {
//			
//			conn = dataConnection.getConnectionOracleMSD();
//			
//			conn.setAutoCommit(false);
//			
//			
//			
//			conn.createStatement().executeUpdate("DELETE FROM CCS_SETUP_LOCAL_RANGE WHERE STG_ID IN (SELECT STG_ID FROM ccs_stage WHERE STG_DATE_CREATION < SYSDATE -1 AND STS_ID = 1)");
//			conn.createStatement().executeUpdate("DELETE FROM CCS_SETUP_STORAGE_RANGE WHERE STG_ID IN (SELECT STG_ID FROM ccs_stage WHERE STG_DATE_CREATION < SYSDATE -1 AND STS_ID = 1)");
//			conn.createStatement().executeUpdate("DELETE FROM CCS_SETUP_LOCAL_TRANSPORT WHERE STG_ID IN (SELECT STG_ID FROM ccs_stage WHERE STG_DATE_CREATION < SYSDATE -1 AND STS_ID = 1)");
//			conn.createStatement().executeUpdate("DELETE FROM CCS_SETUP_STORAGE_TEMP WHERE STG_ID IN (SELECT STG_ID FROM ccs_stage WHERE STG_DATE_CREATION < SYSDATE -1 AND STS_ID = 1)");
//			conn.createStatement().executeUpdate("DELETE FROM CCS_SETUP_OPE_MANAGE WHERE STG_ID IN (SELECT STG_ID FROM ccs_stage WHERE STG_DATE_CREATION < SYSDATE -1 AND STS_ID = 1)");
//			conn.createStatement().executeUpdate("DELETE FROM CCS_SETUP_OPE_CONSOLIDATION WHERE STG_ID IN (SELECT STG_ID FROM ccs_stage WHERE STG_DATE_CREATION < SYSDATE -1 AND STS_ID = 1)");
//			conn.createStatement().executeUpdate("DELETE FROM CCS_SETUP_MACRO_VARS WHERE STG_ID IN (SELECT STG_ID FROM ccs_stage WHERE STG_DATE_CREATION < SYSDATE -1 AND STS_ID = 1)");
////			conn.createStatement().executeUpdate("DELETE FROM CCS_SETUP_EXCHANGES WHERE STG_ID IN (SELECT STG_ID FROM ccs_stage WHERE STG_DATE_CREATION < SYSDATE -1 AND STS_ID = 1)");
//			conn.createStatement().executeUpdate("DELETE FROM CCS_SETUP_CUSTUM_DUTIES_DETAIL WHERE STG_ID IN (SELECT STG_ID FROM ccs_stage WHERE STG_DATE_CREATION < SYSDATE -1 AND STS_ID = 1)");
//			conn.createStatement().executeUpdate("DELETE FROM CCS_SETUP_CUSTUM_DUTIES_HEAD WHERE STG_ID IN (SELECT STG_ID FROM ccs_stage WHERE STG_DATE_CREATION < SYSDATE -1 AND STS_ID = 1)");
//			
//			
//			conn.createStatement().executeUpdate("DELETE FROM CCS_STAGE_FREIGHT_OTHERS_CSP WHERE STG_ID IN (SELECT STG_ID FROM ccs_stage WHERE STG_DATE_CREATION < SYSDATE -1 AND STS_ID = 1)");
//			
//			conn.createStatement().executeUpdate("DELETE FROM CCS_STAGE_FREIGHT_OTHERS WHERE STG_ID IN (SELECT STG_ID FROM ccs_stage WHERE STG_DATE_CREATION < SYSDATE -1 AND STS_ID = 1)");
//			
//			
//			conn.createStatement().executeUpdate("DELETE FROM CCS_STAGE_FREIGHT_EXCHANGE WHERE STG_ID IN (SELECT STG_ID FROM ccs_stage WHERE STG_DATE_CREATION < SYSDATE -1 AND STS_ID = 1)");
//			conn.createStatement().executeUpdate("DELETE FROM CCS_STAGE_FREIGHT_AIR_RANGE WHERE STG_ID IN (SELECT STG_ID FROM ccs_stage WHERE STG_DATE_CREATION < SYSDATE -1 AND STS_ID = 1)");
//			conn.createStatement().executeUpdate("DELETE FROM CCS_STAGE_FREIGHT_AIR WHERE STG_ID IN (SELECT STG_ID FROM ccs_stage WHERE STG_DATE_CREATION < SYSDATE -1 AND STS_ID = 1)");
//			
//			conn.createStatement().executeUpdate("DELETE FROM CCS_SITE_SOURCE_TRANSPORT WHERE STG_ID IN (SELECT STG_ID FROM ccs_stage WHERE STG_DATE_CREATION < SYSDATE -1 AND STS_ID = 1)");
//			
//			
//			conn.createStatement().executeUpdate("DELETE FROM CCS_LOCAL_ITEM_MASTER WHERE STG_ID IN (SELECT STG_ID FROM ccs_stage WHERE STG_DATE_CREATION < SYSDATE -1 AND STS_ID = 1)");
//			conn.createStatement().executeUpdate("DELETE FROM CCS_SETUP_LOCAL_ROUTE WHERE STG_ID IN (SELECT STG_ID FROM ccs_stage WHERE STG_DATE_CREATION < SYSDATE -1 AND STS_ID = 1)");
//			conn.createStatement().executeUpdate("DELETE FROM CCS_SETUP_CUSTODY_SERVICE WHERE STG_ID IN (SELECT STG_ID FROM ccs_stage WHERE STG_DATE_CREATION < SYSDATE -1 AND STS_ID = 1)");
//			conn.createStatement().executeUpdate("DELETE FROM CCS_UNITS_COST WHERE STG_ID IN (SELECT STG_ID FROM ccs_stage WHERE STG_DATE_CREATION < SYSDATE -1 AND STS_ID = 1)");
//			conn.createStatement().executeUpdate("DELETE FROM CCS_IMPORT_UNITS WHERE STG_ID IN (SELECT STG_ID FROM ccs_stage WHERE STG_DATE_CREATION < SYSDATE -1 AND STS_ID = 1)");
//
//			conn.createStatement().executeUpdate("DELETE FROM CCS_STAGE_COMMENT WHERE STG_ID IN (SELECT STG_ID FROM ccs_stage WHERE STG_DATE_CREATION < SYSDATE -1 AND STS_ID = 1)");
//			
//			
//			conn.createStatement().executeUpdate("DELETE FROM CCS_STAGE_FREIGHT_APO_RATE WHERE STG_ID IN (SELECT STG_ID FROM ccs_stage WHERE STG_DATE_CREATION < SYSDATE -1 AND STS_ID = 1)");
//			conn.createStatement().executeUpdate("DELETE FROM CCS_STAGE_FREIGHT_APO WHERE STG_ID IN (SELECT STG_ID FROM ccs_stage WHERE STG_DATE_CREATION < SYSDATE -1 AND STS_ID = 1)");
//			
//			
//			conn.createStatement().executeUpdate("DELETE FROM CCS_SITE_SOURCE WHERE STG_ID IN (SELECT STG_ID FROM ccs_stage WHERE STG_DATE_CREATION < SYSDATE -1 AND STS_ID = 1)");
//			
//			conn.createStatement().executeUpdate("DELETE FROM CCS_STAGE WHERE STG_DATE_CREATION < SYSDATE -1 AND STS_ID = 1");
//			
//			conn.commit();
//			
//			logger.debug("Commit");
//		}catch (SQLException e) {
//			e.printStackTrace();
//			try {
//				conn.rollback();
//			} catch (SQLException e1) {
//				throw new DAOException(ConstantsFinalObject.EXCEPTION_DAO_GENERIC_ERR_COD, e);		
//			}
//			throw new DAOException(ConstantsFinalObject.EXCEPTION_DAO_GENERIC_ERR_COD, e);		
//		}finally{		
//			if (conn != null)
//				try {
//					conn.close();
//				} catch (SQLException e) {
//					throw new DAOException(ConstantsFinalObject.EXCEPTION_DAO_GENERIC_ERR_COD, e);
//				}
//		}
//	}
	
}