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
import cl.cstit.msd.ccs.utils.FormatUtilities;
import cl.cstit.msd.ccs.vo.HomeCompareFamilyVO;
import cl.cstit.msd.ccs.vo.HomeCompareVO;
import cl.cstit.msd.ccs.vo.SiteVO;
import cl.cstit.msd.ccs.vo.StageVO;
import cl.cstit.msd.ccs.vo.StandardDetailVO;
import cl.cstit.msd.ccs.vo.SummaryVO;
import cl.cstit.msd.ccs.vo.UserVO;

public class AutenticationUserDAOImp{
	
	
	private ConnectionFactory dataConnection = null;
	private static Logger logger = null;
	
	public AutenticationUserDAOImp(){
		dataConnection = ConnectionFactory.getInstance();	
		logger = Logger.getLogger(AutenticationUserDAOImp.class);
	}
	

    public UserVO autenticateUser(String isid) throws DAOException{
    	
    	Connection conn = null;
    	UserVO userVO   = null;
    	
    	try {
    		conn = dataConnection.getConnectionOracleMSD();
			String queryExecute = "SELECT USR_ISID, USR_USERNAME , USR_EMAIL, PER_ID FROM ccs_users WHERE LOWER(usr_isid) = LOWER('"+isid+"')";
			
			ResultSet rs = conn.createStatement().executeQuery(queryExecute);
			if (!rs.next()){
				throw new DAOException(ConstantsFinalObject.E_DAO_NO_USER_VALID_COD);		
			}else{
				
				userVO = new UserVO();
				
				userVO.setIsidUser(rs.getString(1));
				userVO.setNameUser(rs.getString(2));
				userVO.setEmailUser(rs.getString(3));
				userVO.setIdProfileUser(rs.getString(4));
				
				userVO.setLastAccessDate(FormatUtilities.getCurrentDate("dd/MM/yyyy hh:mm"));
				
				
				String queryExecuteSites = "select SIU.SIT_ID, SIU.USR_ISID, SIT.SIT_NAME, SIT.SIT_FLAG from CCS_SITES_USER SIU, CCS_SITE SIT WHERE SIU.SIT_ID = SIT.SIT_ID  AND LOWER(SIU.USR_ISID) = LOWER('"+isid+"')";
				
				ResultSet rsSites = conn.createStatement().executeQuery(queryExecuteSites);
				
				while(rsSites.next()){
					
					SiteVO siteVO = new SiteVO();
					
					siteVO.setIdSite(rsSites.getString(1));
					siteVO.setNameSite(rsSites.getString(3));
					
					siteVO.setFlagSite(rsSites.getString(4));
					
//					logger.debug("rsSites.getString(1): "+rsSites.getString(1));
					
					String queryExecuteGrey = "select 1 from CCS_STAGE WHERE STS_ID = 6 AND SIT_ID = ('"+rsSites.getString(1)+"')";
					
					ResultSet rsGrey = conn.createStatement().executeQuery(queryExecuteGrey);
					
					if (rsGrey.next())
						siteVO.setActiveImage("Y");
					else
						siteVO.setActiveImage("N");
					
					
					userVO.getListSiteVO().add(siteVO);
				}
				
				
				String queryRegister = "insert into ccs_register_user values(sysdate,'"+isid+"')";
				
				conn.createStatement().executeUpdate(queryRegister);
				
			}
			
			logger.debug("Todo ok en login");
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
    	
    	return userVO;
    }
    
    
    public HomeCompareVO homeCompareStagesChart(List<SiteVO> listSiteVO) throws DAOException{
    	
    	Connection conn = null;
    	
    	HomeCompareVO homeCompareVO = new HomeCompareVO();
    	
		String idActualStage = "0";
		String idForecastBudgetStage = "0";
    	try {
    		
    		
    		conn = dataConnection.getConnectionOracleMSD();
    		
    		//recorriendo paises lista
    		String idStageList = "0";
    		
    		for (SiteVO siteVO: listSiteVO){
    			idStageList = idStageList.concat(","+siteVO.getIdSite());
    		}

    		//identificando id stage del actual para el per�odo para todos los paises.
			String qeTotalFamily = "SELECT STG_ID FROM CCS_STAGE WHERE SIT_ID IN ("+idStageList+") AND STS_ID = 6 AND STG_PERIOD = "+FormatUtilities.getCurrentDateYear()+"";
			logger.debug("qeTotalFamily: "+qeTotalFamily);
			ResultSet rs = conn.createStatement().executeQuery(qeTotalFamily);
			while (rs.next()){
				idActualStage = idActualStage.concat(","+ rs.getString(1));
			}
			
			//identificando last forecast en su defecto para todos los paises.
			qeTotalFamily = "SELECT SIT_ID FROM CCS_STAGE WHERE SIT_ID IN ("+idStageList+") AND STS_ID IN(5,8) AND STG_PERIOD = "+FormatUtilities.getCurrentDateYear() +" GROUP BY SIT_ID";
			logger.debug("qeTotalFamily: "+qeTotalFamily);
			
			rs = conn.createStatement().executeQuery(qeTotalFamily);
			while(rs.next()){
				qeTotalFamily = "SELECT MAX(STG_ID) FROM CCS_STAGE WHERE SIT_ID IN ("+rs.getString(1)+") AND STS_ID = 5 AND STG_PERIOD = "+FormatUtilities.getCurrentDateYear();
				logger.debug("qeTotalFamily: "+qeTotalFamily);
				
				ResultSet rsMax = conn.createStatement().executeQuery(qeTotalFamily);
				if (rsMax.next()){

					if (rsMax.getString(1) != null){
						idForecastBudgetStage = idForecastBudgetStage.concat(","+rsMax.getString(1)) ;	
					}else{
						
						//esto en caso de que no encuentre forecast, entonces tomaremos el budget para el site.
						qeTotalFamily = "SELECT MAX(STG_ID) FROM CCS_STAGE WHERE SIT_ID IN ("+rs.getString(1)+") AND STS_ID = 8 AND STG_PERIOD = "+FormatUtilities.getCurrentDateYear();
						
						logger.debug("qeTotalFamily: "+qeTotalFamily);
						ResultSet rsBudget = conn.createStatement().executeQuery(qeTotalFamily);
						if (rsBudget.next()){
							idForecastBudgetStage = idForecastBudgetStage.concat(","+rsBudget.getString(1)) ;
						}	
					}
					
				}
			}
    		
    		
    		
			String qeTotals = "SELECT SUM(FREIGHT) FREIGHT, SUM(DUTIES) DUTIES, SUM(UNITS) UNITS, SUM(OTHER) OTHER, SUM(FOB) FOB FROM ( " +
					"SELECT SUM(FREIGHT) FREIGHT, SUM(0) DUTIES, SUM(0) UNITS, SUM(0) OTHER, SUM(0) FOB, STG_ID FROM CCS_VIEW_COST_PRODUCT " +
					"WHERE STG_ID IN("+idActualStage+") " +
					"GROUP BY STG_ID " +
					"UNION ALL " +
					"SELECT SUM(0) FREIGHT, SUM(DUTIES) DUTIES, SUM(0) UNITS, SUM(0) OTHER, SUM(0) FOB, STG_ID FROM CCS_VIEW_COST_PRODUCT " +
					"WHERE STG_ID IN("+idActualStage+") " +
					"GROUP BY STG_ID " +
					"UNION ALL " +
					"SELECT SUM(0) FREIGHT, SUM(0) DUTIES, SUM(UNITS) UNITS, SUM(0) OTHER, SUM(0) FOB, STG_ID FROM CCS_VIEW_COST_PRODUCT " +
					"WHERE STG_ID IN("+idActualStage+") " +
					"GROUP BY STG_ID " +
					"UNION ALL " +
					"SELECT SUM(0) FREIGHT, SUM(0) DUTIES, SUM(0) UNITS, SUM(OTHER) OTHER, SUM(0) FOB, STG_ID FROM CCS_VIEW_COST_PRODUCT " +
					"WHERE STG_ID IN("+idActualStage+") " +
					"GROUP BY STG_ID " +
					"UNION ALL " +
					"SELECT SUM(0) FREIGHT, SUM(0) DUTIES, SUM(0) UNITS, SUM(0) OTHER, SUM(FOB) FOB, STG_ID FROM CCS_VIEW_COST_PRODUCT " +
					"WHERE STG_ID IN("+idActualStage+") " +
					"GROUP BY STG_ID " +
					") ";
			
			logger.debug("qeTotals: "+qeTotals);
			
			rs = conn.createStatement().executeQuery(qeTotals);
			if (rs.next()){
				
				StageVO stageVO = new StageVO();
				
				stageVO.setTotalUnitsStage(rs.getFloat(3));
				stageVO.setTotalFobsStage(rs.getFloat(5));
				stageVO.setTotalDutiesStage(rs.getFloat(2));
				stageVO.setTotalFreightsStage(rs.getFloat(1));
				stageVO.setTotalOthersStage(rs.getFloat(4));
				
				
				homeCompareVO.setStageA(stageVO);
			}
			
			
			
			qeTotals = "SELECT SUM(FREIGHT) FREIGHT, SUM(DUTIES) DUTIES, SUM(UNITS) UNITS, SUM(OTHER) OTHER, SUM(FOB) FOB FROM ( " +
					"SELECT SUM(FREIGHT) FREIGHT, SUM(0) DUTIES, SUM(0) UNITS, SUM(0) OTHER, SUM(0) FOB, STG_ID FROM CCS_VIEW_COST_PRODUCT " +
					"WHERE STG_ID IN ("+idForecastBudgetStage+") " +
					"GROUP BY STG_ID " +
					"UNION ALL " +
					"SELECT SUM(0) FREIGHT, SUM(DUTIES) DUTIES, SUM(0) UNITS, SUM(0) OTHER, SUM(0) FOB, STG_ID FROM CCS_VIEW_COST_PRODUCT " +
					"WHERE STG_ID IN ("+idForecastBudgetStage+") " +
					"GROUP BY STG_ID " +
					"UNION ALL " +
					"SELECT SUM(0) FREIGHT, SUM(0) DUTIES, SUM(UNITS) UNITS, SUM(0) OTHER, SUM(0) FOB, STG_ID FROM CCS_VIEW_COST_PRODUCT " +
					"WHERE STG_ID IN ("+idForecastBudgetStage+") " +
					"GROUP BY STG_ID " +
					"UNION ALL " +
					"SELECT SUM(0) FREIGHT, SUM(0) DUTIES, SUM(0) UNITS, SUM(OTHER) OTHER, SUM(0) FOB, STG_ID FROM CCS_VIEW_COST_PRODUCT " +
					"WHERE STG_ID IN ("+idForecastBudgetStage+") " +
					"GROUP BY STG_ID " +
					"UNION ALL " +
					"SELECT SUM(0) FREIGHT, SUM(0) DUTIES, SUM(0) UNITS, SUM(0) OTHER, SUM(FOB) FOB, STG_ID FROM CCS_VIEW_COST_PRODUCT " +
					"WHERE STG_ID IN ("+idForecastBudgetStage+") " +
					"GROUP BY STG_ID " +
					") ";
			
			logger.debug("qeTotals: "+qeTotals);
			
			rs = conn.createStatement().executeQuery(qeTotals);
			if (rs.next()){
				
				StageVO stageVO = new StageVO();
				
				stageVO.setTotalUnitsStage(rs.getFloat(3));
				stageVO.setTotalFobsStage(rs.getFloat(5));
				stageVO.setTotalDutiesStage(rs.getFloat(2));
				stageVO.setTotalFreightsStage(rs.getFloat(1));
				stageVO.setTotalOthersStage(rs.getFloat(4));
				
				homeCompareVO.setStageB(stageVO);
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
    	return homeCompareVO;
    }
    
    
    
    
    
    public List<HomeCompareFamilyVO> homeCompareStagesFamily(List<SiteVO> listSiteVO) throws DAOException{
    	
    	Connection conn = null;
    	
    	List<HomeCompareFamilyVO> listaHomeCompareFamilyVO = new ArrayList<HomeCompareFamilyVO>();
    	
    	String idActualStage = "0";
    	String idForecastBudgetStage = "0";
    	
    	try {
    		conn = dataConnection.getConnectionOracleMSD();
    		
    		//recorriendo paises lista
    		String idStageList = "0";
    		
    		for (SiteVO siteVO: listSiteVO){
    			idStageList = idStageList.concat(","+siteVO.getIdSite());
    		}

    		//identificando id stage del actual para el per�odo para todos los paises.
			String qeTotalFamily = "SELECT STG_ID FROM CCS_STAGE WHERE SIT_ID IN ("+idStageList+") AND STS_ID = 6 AND STG_PERIOD = "+FormatUtilities.getCurrentDateYear()+"";
//			logger.debug("qeTotalFamily: "+qeTotalFamily);
			ResultSet rs = conn.createStatement().executeQuery(qeTotalFamily);
			while (rs.next()){
				idActualStage = idActualStage.concat(","+rs.getString(1)) ;
			}
			
			
			
			
			
			
			
			//identificando last forecast en su defecto para todos los paises.
			qeTotalFamily = "SELECT SIT_ID FROM CCS_STAGE WHERE SIT_ID IN ("+idStageList+") AND STS_ID IN(5,8) AND STG_PERIOD = "+FormatUtilities.getCurrentDateYear() +" GROUP BY SIT_ID";
//			logger.debug("qeTotalFamily: "+qeTotalFamily);
			
			rs = conn.createStatement().executeQuery(qeTotalFamily);
			while(rs.next()){
				qeTotalFamily = "SELECT MAX(STG_ID) FROM CCS_STAGE WHERE SIT_ID IN ("+rs.getString(1)+") AND STS_ID = 5 AND STG_PERIOD = "+FormatUtilities.getCurrentDateYear();
				logger.debug("qeTotalFamily: "+qeTotalFamily);
				ResultSet rsMax = conn.createStatement().executeQuery(qeTotalFamily);
				if (rsMax.next()){
					
					if (rsMax.getString(1) != null){
						idForecastBudgetStage = idForecastBudgetStage.concat(","+rsMax.getString(1)) ;	
					}else{
						
						//esto en caso de que no encuentre forecast, entonces tomaremos el budget para el site.
						qeTotalFamily = "SELECT MAX(STG_ID) FROM CCS_STAGE WHERE SIT_ID IN ("+rs.getString(1)+") AND STS_ID = 8 AND STG_PERIOD = "+FormatUtilities.getCurrentDateYear();
						
						logger.debug("qeTotalFamily: "+qeTotalFamily);
						ResultSet rsBudget = conn.createStatement().executeQuery(qeTotalFamily);
						if (rsBudget.next()){
							idForecastBudgetStage = idForecastBudgetStage.concat(","+rsBudget.getString(1)) ;
						}	
					}
				}
			}
			
			
			
			qeTotalFamily = "SELECT FAM.PFA_NAME, SUM(VPR.FREIGHT), SUM(VPR.DUTIES), SUM(VPR.UNITS), SUM(VPR.OTHER), SUM(VPR.FOB)  FROM CCS_VIEW_COST_PRODUCT VPR, CCS_LOCAL_ITEM_MASTER LIM, CCS_PRODUCT_FAMILY FAM " +
					 		  "WHERE VPR.STG_ID IN ("+idActualStage+") " +
					 		  "AND   VPR.STG_ID = LIM.STG_ID " +
					 		  "AND   VPR.IMU_COD_PRODUCT = LIM.IMU_COD_PRODUCT " +
					 		  "AND   LIM.PFA_ID = FAM.PFA_ID " +
					 		  "GROUP BY FAM.PFA_NAME "+
					 		  "ORDER BY FAM.PFA_NAME ";
			
//			logger.debug("qeTotalFamily: "+qeTotalFamily);
			
			rs = conn.createStatement().executeQuery(qeTotalFamily);
			while (rs.next()){
				
				HomeCompareFamilyVO homeCompareFamilyVO = new HomeCompareFamilyVO();
				
				homeCompareFamilyVO.setUnitStageAFamily(rs.getFloat(4));
				
				homeCompareFamilyVO.setNameFamily(rs.getString(1));
				homeCompareFamilyVO.setFreightStageAFamily(rs.getFloat(2) * homeCompareFamilyVO.getUnitStageAFamily());
				homeCompareFamilyVO.setDutiesStageAFamily(rs.getFloat(3) * homeCompareFamilyVO.getUnitStageAFamily());
				homeCompareFamilyVO.setOtherStageAFamily(rs.getFloat(5) * homeCompareFamilyVO.getUnitStageAFamily());
				homeCompareFamilyVO.setFobStageAFamily(rs.getFloat(6) * homeCompareFamilyVO.getUnitStageAFamily());
				
				
				listaHomeCompareFamilyVO.add(homeCompareFamilyVO);
			}
			
			
			qeTotalFamily = "SELECT FAM.PFA_NAME, SUM(VPR.FREIGHT), SUM(VPR.DUTIES), SUM(VPR.UNITS), SUM(VPR.OTHER), SUM(VPR.FOB)  FROM CCS_VIEW_COST_PRODUCT VPR, CCS_LOCAL_ITEM_MASTER LIM, CCS_PRODUCT_FAMILY FAM " +
			 		  "WHERE VPR.STG_ID IN ( "+idForecastBudgetStage+") " +
			 		  "AND   VPR.STG_ID = LIM.STG_ID " +
			 		  "AND   VPR.IMU_COD_PRODUCT = LIM.IMU_COD_PRODUCT " +
			 		  "AND   LIM.PFA_ID = FAM.PFA_ID " +
			 		  "GROUP BY FAM.PFA_NAME " +
			 		  "ORDER BY FAM.PFA_NAME ";
	
//			logger.debug("qeTotalFamily: "+qeTotalFamily);
			
			rs = conn.createStatement().executeQuery(qeTotalFamily);
			while (rs.next()){
				
				for(HomeCompareFamilyVO homeCompareFamilyVO :listaHomeCompareFamilyVO){
					
					if (homeCompareFamilyVO.getNameFamily().equals(rs.getString(1))){
						
						homeCompareFamilyVO.setUnitStageBFamily(rs.getFloat(4));
						
						homeCompareFamilyVO.setFreightStageBFamily(rs.getFloat(2) * homeCompareFamilyVO.getUnitStageBFamily());
						homeCompareFamilyVO.setDutiesStageBFamily(rs.getFloat(3) * homeCompareFamilyVO.getUnitStageBFamily());
						homeCompareFamilyVO.setOtherStageBFamily(rs.getFloat(5) * homeCompareFamilyVO.getUnitStageBFamily());
						homeCompareFamilyVO.setFobStageBFamily(rs.getFloat(6) * homeCompareFamilyVO.getUnitStageBFamily());
					}
				}
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
    	return listaHomeCompareFamilyVO;
    }
    
    
	public List<StandardDetailVO> listStandarDetailStage(List<SiteVO> listSiteVO) throws DAOException{
		
		
		Connection conn = null;
		List<StandardDetailVO> listStandardDetailVO = new ArrayList<StandardDetailVO>();
		
		String idStageA = "0";
		String idStageB = "0";
		try {
			conn = dataConnection.getConnectionOracleMSD();
			
			
    		//recorriendo paises lista
    		String idStageList = "0";
    		
    		for (SiteVO siteVO: listSiteVO){
    			idStageList = idStageList.concat(","+siteVO.getIdSite());
    		}

    		//identificando id stage del actual para el per�odo para todos los paises.
			String qeTotalFamily = "SELECT STG_ID FROM CCS_STAGE WHERE SIT_ID IN ("+idStageList+") AND STS_ID = 6 AND STG_PERIOD = "+FormatUtilities.getCurrentDateYear()+"";
			logger.debug("qeTotalFamily: "+qeTotalFamily);
			ResultSet rs = conn.createStatement().executeQuery(qeTotalFamily);
			while (rs.next()){
				idStageB = idStageB.concat(","+ rs.getString(1));
			}
			
			//identificando last forecast en su defecto para todos los paises.
			qeTotalFamily = "SELECT SIT_ID FROM CCS_STAGE WHERE SIT_ID IN ("+idStageList+") AND STS_ID IN (5,8) AND STG_PERIOD = "+FormatUtilities.getCurrentDateYear() +" GROUP BY SIT_ID";
			logger.debug("qeTotalFamily: "+qeTotalFamily);
			
			rs = conn.createStatement().executeQuery(qeTotalFamily);
			while(rs.next()){
				qeTotalFamily = "SELECT MAX(STG_ID) FROM CCS_STAGE WHERE SIT_ID IN ("+rs.getString(1)+") AND STS_ID = 5 AND STG_PERIOD = "+FormatUtilities.getCurrentDateYear();
				logger.debug("qeTotalFamily: "+qeTotalFamily);
				
				ResultSet rsMax = conn.createStatement().executeQuery(qeTotalFamily);
				if (rsMax.next()){
//					idStageB = idStageB.concat(","+ rsMax.getString(1));asdsad
					
					
					if (rsMax.getString(1) != null){
						idStageA = idStageA.concat(","+rsMax.getString(1)) ;	
					}else{
						
						//esto en caso de que no encuentre forecast, entonces tomaremos el budget para el site.
						qeTotalFamily = "SELECT MAX(STG_ID) FROM CCS_STAGE WHERE SIT_ID IN ("+rs.getString(1)+") AND STS_ID = 8 AND STG_PERIOD = "+FormatUtilities.getCurrentDateYear();
						
						ResultSet rsBudget = conn.createStatement().executeQuery(qeTotalFamily);
						if (rsBudget.next()){
							idStageA = idStageA.concat(","+rsBudget.getString(1)) ;
						}	
					}
					
					
				}
			}
			
			
			
			//Obteniendo tasas de intercambio
			float exchangeStageA = 0;
			float exchangeStageB = 0;
			
			rs = conn.createStatement().executeQuery("SELECT (SMV_AMOUNT / 100) FROM CCS_SETUP_MACRO_VARS WHERE STG_ID IN("+idStageA+") AND SMV_COD = 'ERA'");
			if (rs.next()) exchangeStageA = rs.getFloat(1);
			
			rs = conn.createStatement().executeQuery("SELECT (SMV_AMOUNT / 100) FROM CCS_SETUP_MACRO_VARS WHERE STG_ID IN("+idStageB+") AND SMV_COD = 'ERA'");
			if (rs.next()) exchangeStageB = rs.getFloat(1);

			

			String queryExecute = "select SUM(LIM_FOB_COST * (IMU_JAN + IMU_FEB + IMU_MAR + IMU_APR + IMU_MAY + IMU_JUN + IMU_JUL + IMU_AUG + IMU_SEP + IMU_OCT + IMU_NOV + IMU_DIC)) from CCS_LOCAL_ITEM_MASTER LIM, CCS_IMPORT_UNITS UNI "
					 + "WHERE  LIM.STG_ID IN ("+idStageA+") "
					 + "AND    LIM.IMU_COD_PRODUCT = UNI.IMU_COD_PRODUCT " 
					 + "AND    LIM.STG_ID = UNI.STG_ID";
			rs = conn.createStatement().executeQuery(queryExecute);
			if (rs.next()){
				StandardDetailVO standardDetailVO = new StandardDetailVO();
				
				standardDetailVO.setTypeCostStandarDetail("FOB");
				standardDetailVO.setSubTypeCostStandarDetail("INTERCOMPANY");
				standardDetailVO.setNameCostStandarDetail("Transfer Price");
				standardDetailVO.setCodCostStandarDetail("TRF");
				standardDetailVO.setAmountStageAStandarDetail(rs.getFloat(1) / 1000);//luka
				
				listStandardDetailVO.add(standardDetailVO);
			}
			
			
			
			queryExecute = "SELECT COS.UNC_COST_TYPE, COS.UNC_COST_SUBTYPE, COS.UNC_COST_NAME, SUM(COS.UNC_COST_AMOUNT * (IMU_JAN + IMU_FEB + IMU_MAR + IMU_APR + IMU_MAY + IMU_JUN + IMU_JUL + IMU_AUG + IMU_SEP + IMU_OCT + IMU_NOV + IMU_DIC) ), COS.UNC_COST_COD FROM CCS_UNITS_COST COS, CCS_IMPORT_UNITS IMP " +
					"WHERE  COS.STG_ID IN ("+idStageA+") " +
					"AND    COS.STG_ID = IMP.STG_ID " +
					"AND    COS.IMU_COD_PRODUCT = IMP.IMU_COD_PRODUCT " +
					"GROUP BY COS.UNC_COST_TYPE, COS.UNC_COST_SUBTYPE, COS.UNC_COST_NAME, COS.UNC_COST_COD " +
					"ORDER BY 1,2,3";
			
			logger.debug("queryExecute: "+queryExecute);
			
			rs = conn.createStatement().executeQuery(queryExecute);
			while (rs.next()){
				
				StandardDetailVO standardDetailVO = new StandardDetailVO();
				
				standardDetailVO.setTypeCostStandarDetail(rs.getString(1));
				standardDetailVO.setSubTypeCostStandarDetail(rs.getString(2));
				standardDetailVO.setNameCostStandarDetail(rs.getString(3));
				standardDetailVO.setAmountStageAStandarDetail(rs.getFloat(4) / 1000);//luka
				standardDetailVO.setCodCostStandarDetail(rs.getString(5));
				
				listStandardDetailVO.add(standardDetailVO);
			}
			
			
			
			queryExecute = "select SUM(LIM_FOB_COST * (IMU_JAN + IMU_FEB + IMU_MAR + IMU_APR + IMU_MAY + IMU_JUN + IMU_JUL + IMU_AUG + IMU_SEP + IMU_OCT + IMU_NOV + IMU_DIC)) from CCS_LOCAL_ITEM_MASTER LIM, CCS_IMPORT_UNITS UNI "
						 + "WHERE  LIM.STG_ID IN ("+idStageB+") "
						 + "AND    LIM.IMU_COD_PRODUCT = UNI.IMU_COD_PRODUCT " 
						 + "AND    LIM.STG_ID = UNI.STG_ID";
			rs = conn.createStatement().executeQuery(queryExecute);
			if (rs.next()){
				for(StandardDetailVO standardDetailVO: listStandardDetailVO){
					
					if (standardDetailVO.getCodCostStandarDetail().equals("TRF")){
						standardDetailVO.setAmountStageBStandarDetail(rs.getFloat(1) / 1000);//luka
						
						standardDetailVO.setNominalAmoutStandarDetail(standardDetailVO.getAmountStageBStandarDetail() - standardDetailVO.getAmountStageAStandarDetail());
						standardDetailVO.setDteAmoutStandarDetail(standardDetailVO.getAmountStageBStandarDetail() - ((standardDetailVO.getAmountStageBStandarDetail() * (1 / exchangeStageA)) / (1 / exchangeStageB)));
						standardDetailVO.setExchangeAmoutStandarDetail(standardDetailVO.getNominalAmoutStandarDetail() - standardDetailVO.getDteAmoutStandarDetail());
//						standardDetailVO.setPercentAmoutStandarDetail(standardDetailVO.getAmountStageBStandarDetail() / (standardDetailVO.getExchangeAmoutStandarDetail()==0?1:standardDetailVO.getExchangeAmoutStandarDetail()));
						standardDetailVO.setPercentAmoutStandarDetail(standardDetailVO.getExchangeAmoutStandarDetail() / (standardDetailVO.getAmountStageBStandarDetail()) * 100);
					}
				}
			}
			
			
			queryExecute = "SELECT COS.UNC_COST_TYPE, COS.UNC_COST_SUBTYPE, COS.UNC_COST_NAME, SUM(COS.UNC_COST_AMOUNT * (IMU_JAN + IMU_FEB + IMU_MAR + IMU_APR + IMU_MAY + IMU_JUN + IMU_JUL + IMU_AUG + IMU_SEP + IMU_OCT + IMU_NOV + IMU_DIC) ), COS.UNC_COST_COD  FROM CCS_UNITS_COST COS, CCS_IMPORT_UNITS IMP " +
					"WHERE  COS.STG_ID IN ("+idStageB+") " +
					"AND    COS.STG_ID = IMP.STG_ID " +
					"AND    COS.IMU_COD_PRODUCT = IMP.IMU_COD_PRODUCT " +
					"GROUP BY COS.UNC_COST_TYPE, COS.UNC_COST_SUBTYPE, COS.UNC_COST_NAME, COS.UNC_COST_COD " +
					"ORDER BY 1,2,3";
			
			rs = conn.createStatement().executeQuery(queryExecute);
			while (rs.next()){
				
				for(StandardDetailVO standardDetailVO: listStandardDetailVO){
					if (standardDetailVO.getCodCostStandarDetail().equals(rs.getString(5))){
						standardDetailVO.setAmountStageBStandarDetail(rs.getFloat(4) / 1000);//luka
						standardDetailVO.setNominalAmoutStandarDetail(standardDetailVO.getAmountStageBStandarDetail() - standardDetailVO.getAmountStageAStandarDetail());
						
						
						standardDetailVO.setDteAmoutStandarDetail(standardDetailVO.getAmountStageBStandarDetail() - ((standardDetailVO.getAmountStageBStandarDetail() * (1 / exchangeStageA)) / (1 / exchangeStageB)));
						standardDetailVO.setExchangeAmoutStandarDetail(standardDetailVO.getNominalAmoutStandarDetail() - standardDetailVO.getDteAmoutStandarDetail());
												
						
						standardDetailVO.setPercentAmoutStandarDetail(standardDetailVO.getExchangeAmoutStandarDetail() / (standardDetailVO.getAmountStageBStandarDetail()) * 100);
						
						logger.debug("getCodCostStandarDetail: "+standardDetailVO.getCodCostStandarDetail());
						logger.debug("setDteAmoutStandarDetail - getAmountStageAStandarDetail: "+standardDetailVO.getAmountStageAStandarDetail());
						logger.debug("setDteAmoutStandarDetail -getexchangeStageA: "+exchangeStageA);
						logger.debug("setDteAmoutStandarDetail - getexchangeStageB: "+exchangeStageB);
						
						
//						5 17:03:31,664  DEBUG msd.ccs.dao.AutenticationUserDAOImp(listStandarDetailStage:544)---> getCodCostStandarDetail: DUTY-COST-FIXED
//						28 ene 2015 17:03:31,664  DEBUG msd.ccs.dao.AutenticationUserDAOImp(listStandarDetailStage:545)---> setDteAmoutStandarDetail - getAmountStageAStandarDetail: 181239.2
//						28 ene 2015 17:03:31,665  DEBUG msd.ccs.dao.AutenticationUserDAOImp(listStandarDetailStage:546)---> setDteAmoutStandarDetail -getexchangeStageA: 0.1
//						28 ene 2015 17:03:31,665  DEBUG msd.ccs.dao.AutenticationUserDAOImp(listStandarDetailStage:547)---> setDteAmoutStandarDetail - getexchangeStageB: 0.11
//						(181239.2) - ((181239.2 * (1/0.1)) / (1/0.11) ) = (181239.2) - ((1812392) / 9,090909090909091) = 181239,2 - (199363,12) 
						
						
					}
				}
			}
		}catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(ConstantsFinalObject.EXCEPTION_DAO_GENERIC_ERR_COD, e);		
		}catch (Exception e) {
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
		return listStandardDetailVO;
	}
	
	
	public List<SummaryVO> initSummary(List<SiteVO> listSiteVO) throws DAOException{
		
		Connection conn = null;
		
		String idStageA = "0";
		String idStageB = "0";
		String idStageC = "0";
		
		List<SummaryVO> listSummaryVO = new ArrayList<SummaryVO>();
		try {
			
			conn = dataConnection.getConnectionOracleMSD();
			
    		//recorriendo paises lista
    		String idStageList = "0";
    		for (SiteVO siteVO: listSiteVO){
    			idStageList = idStageList.concat(","+siteVO.getIdSite());
    		}

    		//identificando id stage del actual para el per�odo para todos los paises.
			String qeTotalFamily = "SELECT STG_ID FROM CCS_STAGE WHERE SIT_ID IN ("+idStageList+") AND STS_ID = 6 AND STG_PERIOD = "+FormatUtilities.getCurrentDateYear()+"";
			logger.debug("qeTotalFamily: "+qeTotalFamily);
			ResultSet rs = conn.createStatement().executeQuery(qeTotalFamily);
			while (rs.next()){
				idStageA = idStageA.concat(","+ rs.getString(1));
			}
			
			//identificando last forecast en su defecto para todos los paises.
			qeTotalFamily = "SELECT SIT_ID FROM CCS_STAGE WHERE SIT_ID IN ("+idStageList+") AND STS_ID IN (5,8) AND STG_PERIOD = "+FormatUtilities.getCurrentDateYear() +" GROUP BY SIT_ID";
			
			rs = conn.createStatement().executeQuery(qeTotalFamily);
			while(rs.next()){
				qeTotalFamily = "SELECT MAX(STG_ID) FROM CCS_STAGE WHERE SIT_ID IN ("+rs.getString(1)+") AND STS_ID = 5 AND STG_PERIOD = "+FormatUtilities.getCurrentDateYear();
				
				ResultSet rsMax = conn.createStatement().executeQuery(qeTotalFamily);
				if (rsMax.next()){
					
					if (rsMax.getString(1) != null){
						idStageB = idStageB.concat(","+rsMax.getString(1)) ;	
					}else{
						
						//esto en caso de que no encuentre forecast, entonces tomaremos el budget para el site.
						qeTotalFamily = "SELECT MAX(STG_ID) FROM CCS_STAGE WHERE SIT_ID IN ("+rs.getString(1)+") AND STS_ID = 8 AND STG_PERIOD = "+FormatUtilities.getCurrentDateYear();
						
						ResultSet rsBudget = conn.createStatement().executeQuery(qeTotalFamily);
						if (rsBudget.next()){
							idStageB = idStageB.concat(","+rsBudget.getString(1)) ;
						}	
					}
					
				}
			}
			
			
			//identificando budget para todos los paises
			qeTotalFamily = "SELECT SIT_ID FROM CCS_STAGE WHERE SIT_ID IN ("+idStageList+") AND STS_ID = 8 AND STG_PERIOD = "+FormatUtilities.getCurrentDateYear() +" GROUP BY SIT_ID";
			
			rs = conn.createStatement().executeQuery(qeTotalFamily);
			while(rs.next()){
				qeTotalFamily = "SELECT MAX(STG_ID) FROM CCS_STAGE WHERE SIT_ID IN ("+rs.getString(1)+") AND STS_ID = 8 AND STG_PERIOD = "+FormatUtilities.getCurrentDateYear();
				
				ResultSet rsMax = conn.createStatement().executeQuery(qeTotalFamily);
				if (rsMax.next()){
					idStageC = idStageC.concat(","+ rsMax.getString(1));
				}
			}
			
			
			
			//Obteniendo tasas de intercambio
			float exchangeStageA = 0;
			float exchangeStageB = 0;
			
			rs = conn.createStatement().executeQuery("SELECT SMV_AMOUNT FROM CCS_SETUP_MACRO_VARS WHERE STG_ID IN ("+idStageA+") AND SMV_COD = 'ERA'");
			if (rs.next()) exchangeStageA = rs.getFloat(1);
			
			rs = conn.createStatement().executeQuery("SELECT SMV_AMOUNT FROM CCS_SETUP_MACRO_VARS WHERE STG_ID IN ("+idStageB+") AND SMV_COD = 'ERA'");
			if (rs.next()) exchangeStageB = rs.getFloat(1);
			
			
			String qeSelectProductsImportUnits = "SELECT SUM(UNI.IMU_JAN), SUM(UNI.IMU_FEB), SUM(UNI.IMU_MAR), SUM(UNI.IMU_APR), SUM(UNI.IMU_MAY), SUM(UNI.IMU_JUN), SUM(UNI.IMU_JUL), SUM(UNI.IMU_AUG), SUM(UNI.IMU_SEP), SUM(UNI.IMU_OCT), SUM(UNI.IMU_NOV), SUM(UNI.IMU_DIC), SUM(FOB), SUM(DUTIES), SUM(FREIGHT), SUM(OTHER), COUNT(*) CANT " +
												 "FROM CCS_VIEW_COST_PRODUCT VPR, CCS_IMPORT_UNITS UNI " +
												 "WHERE VPR.STG_ID = UNI.STG_ID " +
												 "AND   VPR.IMU_COD_PRODUCT = UNI.IMU_COD_PRODUCT " +
												 "AND   UNI.STG_ID IN ("+idStageA+") ";
			
			
			rs = conn.createStatement().executeQuery(qeSelectProductsImportUnits);
			if (rs.next()){
				
//				float totalProduct = rs.getFloat(17); 
				
				for(int i=1; i<=12; i++){
					
					float totalUnits = rs.getFloat(1) + rs.getFloat(2) + rs.getFloat(3) + rs.getFloat(4) + rs.getFloat(5) + rs.getFloat(6) + rs.getFloat(7) +
							rs.getFloat(8) + rs.getFloat(9) + rs.getFloat(10) + rs.getFloat(11) + rs.getFloat(12);
					
					SummaryVO summaryVO = new SummaryVO(FormatUtilities.getMonthName(i));
					
					summaryVO.setAmountUnitStageASummary(rs.getFloat(i));
					
					summaryVO.setAmountFobsStageASummary((rs.getFloat(13)     / totalUnits) * summaryVO.getAmountUnitStageASummary() / 1000);
					summaryVO.setAmountDutiesStageASummary((rs.getFloat(14)   / totalUnits) * summaryVO.getAmountUnitStageASummary() / 1000);
					summaryVO.setAmountFreightsStageASummary((rs.getFloat(15) / totalUnits) * summaryVO.getAmountUnitStageASummary() / 1000);
					summaryVO.setAmountOthersStageASummary((rs.getFloat(16)   / totalUnits) * summaryVO.getAmountUnitStageASummary() / 1000);
					listSummaryVO.add(summaryVO);
				}
			}
			
			
			qeSelectProductsImportUnits = "SELECT SUM(UNI.IMU_JAN), SUM(UNI.IMU_FEB), SUM(UNI.IMU_MAR), SUM(UNI.IMU_APR), SUM(UNI.IMU_MAY), SUM(UNI.IMU_JUN), SUM(UNI.IMU_JUL), SUM(UNI.IMU_AUG), SUM(UNI.IMU_SEP), SUM(UNI.IMU_OCT), SUM(UNI.IMU_NOV), SUM(UNI.IMU_DIC), SUM(FOB), SUM(DUTIES), SUM(FREIGHT), SUM(OTHER), COUNT(*) CANT " +
					 "FROM CCS_VIEW_COST_PRODUCT VPR, CCS_IMPORT_UNITS UNI " +
					 "WHERE VPR.STG_ID = UNI.STG_ID " +
					 "AND   VPR.IMU_COD_PRODUCT = UNI.IMU_COD_PRODUCT " +
					 "AND   UNI.STG_ID IN ("+idStageB+") ";

			rs = conn.createStatement().executeQuery(qeSelectProductsImportUnits);
			if (rs.next()){
				
//				float totalProduct = rs.getFloat(17); 
				int i = 1;
				for(SummaryVO summaryVO: listSummaryVO){
					
					float totalUnits = rs.getFloat(1) + rs.getFloat(2) + rs.getFloat(3) + rs.getFloat(4) + rs.getFloat(5) + rs.getFloat(6) + rs.getFloat(7) +
							rs.getFloat(8) + rs.getFloat(9) + rs.getFloat(10) + rs.getFloat(11) + rs.getFloat(12);
					
					
					summaryVO.setAmountUnitStageBSummary(rs.getFloat(i));
					
					summaryVO.setAmountFobsStageBSummary((rs.getFloat(13)     / totalUnits) * summaryVO.getAmountUnitStageBSummary() / 1000);
					summaryVO.setAmountDutiesStageBSummary((rs.getFloat(14)   / totalUnits) * summaryVO.getAmountUnitStageBSummary() / 1000);
					summaryVO.setAmountFreightsStageBSummary((rs.getFloat(15) / totalUnits) * summaryVO.getAmountUnitStageBSummary() / 1000);	
					summaryVO.setAmountOthersStageBSummary((rs.getFloat(16)   / totalUnits) * summaryVO.getAmountUnitStageBSummary() / 1000);
					
					summaryVO.setByMonthUnitSummary(summaryVO.getAmountUnitStageBSummary() - summaryVO.getAmountUnitStageASummary());
					summaryVO.setByMonthDutiesSummary(summaryVO.getAmountDutiesStageBSummary() - summaryVO.getAmountDutiesStageASummary());
					summaryVO.setByMonthFobsSummary(summaryVO.getAmountFobsStageBSummary() - summaryVO.getAmountFobsStageASummary());
					summaryVO.setByMonthFreightsSummary(summaryVO.getAmountFreightsStageBSummary() - summaryVO.getAmountFreightsStageASummary());
					summaryVO.setByMonthOthersSummary(summaryVO.getAmountOthersStageBSummary() - summaryVO.getAmountOthersStageASummary());
					
					summaryVO.setDteDutiesSummary((summaryVO.getAmountDutiesStageBSummary() - ((summaryVO.getAmountDutiesStageBSummary() * (1 / exchangeStageB)) / (1 / exchangeStageA))));
					
					summaryVO.setDteFreightsSummary((summaryVO.getAmountFreightsStageBSummary() - ((summaryVO.getAmountFreightsStageBSummary() * (1 / exchangeStageB)) / (1 / exchangeStageA))));
					
					
					summaryVO.setDteOthersSummary((summaryVO.getAmountOthersStageBSummary() - ((summaryVO.getAmountOthersStageBSummary() * (1 / exchangeStageB)) / (1 / exchangeStageA))));
					
					i++;
				}
			}
			
			
			
			qeSelectProductsImportUnits = "SELECT SUM(UNI.IMU_JAN), SUM(UNI.IMU_FEB), SUM(UNI.IMU_MAR), SUM(UNI.IMU_APR), SUM(UNI.IMU_MAY), SUM(UNI.IMU_JUN), SUM(UNI.IMU_JUL), SUM(UNI.IMU_AUG), SUM(UNI.IMU_SEP), SUM(UNI.IMU_OCT), SUM(UNI.IMU_NOV), SUM(UNI.IMU_DIC), SUM(FOB), SUM(DUTIES), SUM(FREIGHT), SUM(OTHER), COUNT(*) CANT " +
					 "FROM CCS_VIEW_COST_PRODUCT VPR, CCS_IMPORT_UNITS UNI " +
					 "WHERE VPR.STG_ID = UNI.STG_ID " +
					 "AND   VPR.IMU_COD_PRODUCT = UNI.IMU_COD_PRODUCT " +
					 "AND   UNI.STG_ID IN ("+idStageC+") ";


			rs = conn.createStatement().executeQuery(qeSelectProductsImportUnits);
			if (rs.next()){
			
//				float totalProduct = rs.getFloat(17); 
				
				int i = 1;
				for(SummaryVO summaryVO: listSummaryVO){
				
//					SummaryVO summaryVO = new SummaryVO(FormatUtilities.getMonthName(i));
					
					float totalUnits = rs.getFloat(1) + rs.getFloat(2) + rs.getFloat(3) + rs.getFloat(4) + rs.getFloat(5) + rs.getFloat(6) + rs.getFloat(7) +
							rs.getFloat(8) + rs.getFloat(9) + rs.getFloat(10) + rs.getFloat(11) + rs.getFloat(12);
					
					summaryVO.setAmountUnitStageCSummary(rs.getFloat(i));
					summaryVO.setAmountFobsStageCSummary((rs.getFloat(13)     / totalUnits) * summaryVO.getAmountUnitStageCSummary() / 1000);
					summaryVO.setAmountDutiesStageCSummary((rs.getFloat(14)   / totalUnits) * summaryVO.getAmountUnitStageCSummary() / 1000);
					summaryVO.setAmountFreightsStageCSummary((rs.getFloat(15) / totalUnits) * summaryVO.getAmountUnitStageCSummary() / 1000);
					summaryVO.setAmountOthersStageCSummary((rs.getFloat(16)   / totalUnits) * summaryVO.getAmountUnitStageCSummary() / 1000);
					
//					listSummaryVO.add(summaryVO);
					i++;
				}
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
		
		return listSummaryVO;
	}

}