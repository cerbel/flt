package cl.cstit.msd.ccs.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import cl.cstit.msd.ccs.exception.DAOException;
import cl.cstit.msd.ccs.utils.ConnectionFactory;
import cl.cstit.msd.ccs.utils.ConstantsFinalObject;
import cl.cstit.msd.ccs.utils.FormatUtilities;
import cl.cstit.msd.ccs.vo.CommentStageVO;
import cl.cstit.msd.ccs.vo.FilterProductStageVO;
import cl.cstit.msd.ccs.vo.GeneralComboVO;
import cl.cstit.msd.ccs.vo.MonthlyDataVO;
import cl.cstit.msd.ccs.vo.PagoCobranzaVO;
import cl.cstit.msd.ccs.vo.ProductivityVO;
import cl.cstit.msd.ccs.vo.StageVO;
import cl.cstit.msd.ccs.vo.StandardDetailVO;
import cl.cstit.msd.ccs.vo.SummaryVO;
import cl.cstit.msd.ccs.vo.UnitCostProductVO;
import cl.cstit.msd.ccs.vo.UserVO;

public class ResultCostDAOImp{
	
	private ConnectionFactory dataConnection = null;
	private static Logger logger = null;
	
	public ResultCostDAOImp(){
		dataConnection = ConnectionFactory.getInstance();	
		logger = Logger.getLogger(ResultCostDAOImp.class);
	}
	

	public List<SummaryVO> initSummary(String idStageA, String idStageB, String filtersProduct) throws DAOException{
		
		Connection conn = null;
		
		List<SummaryVO> listSummaryVO = new ArrayList<SummaryVO>();
		
		try {
			
			System.out.println("filtersProduct: "+filtersProduct);
			if (filtersProduct != null)
				filtersProduct = "AND   VPR.IMU_COD_PRODUCT IN " +filtersProduct;
			else
				filtersProduct = " ";
			
			
			conn = dataConnection.getConnectionOracleMSD();
			
			//Obteniendo tasas de intercambio
			float exchangeStageA = 0;
			float exchangeStageB = 0;
			
			ResultSet rs = conn.createStatement().executeQuery("SELECT (SMV_AMOUNT / 100) FROM CCS_SETUP_MACRO_VARS WHERE STG_ID = "+idStageA+" AND SMV_COD = 'ERA'");
			if (rs.next()) exchangeStageA = rs.getFloat(1);
			
			rs = conn.createStatement().executeQuery("SELECT (SMV_AMOUNT / 100) FROM CCS_SETUP_MACRO_VARS WHERE STG_ID = "+idStageB+" AND SMV_COD = 'ERA'");
			if (rs.next()) exchangeStageB = rs.getFloat(1);
			
			
			String qeSelectProductsImportUnits = "SELECT SUM(UNI.IMU_JAN), SUM(UNI.IMU_FEB), SUM(UNI.IMU_MAR), SUM(UNI.IMU_APR), SUM(UNI.IMU_MAY), SUM(UNI.IMU_JUN), SUM(UNI.IMU_JUL), SUM(UNI.IMU_AUG), SUM(UNI.IMU_SEP), SUM(UNI.IMU_OCT), SUM(UNI.IMU_NOV), SUM(UNI.IMU_DIC), SUM(FOB), SUM(DUTIES), SUM(FREIGHT), SUM(OTHER), COUNT(*) CANT " +
												 "FROM CCS_VIEW_COST_PRODUCT VPR, CCS_IMPORT_UNITS UNI " +
												 "WHERE VPR.STG_ID = UNI.STG_ID " +
												 "AND   VPR.IMU_COD_PRODUCT = UNI.IMU_COD_PRODUCT " +
												 filtersProduct+
												 "AND   UNI.STG_ID = "+idStageA+"";
			
			logger.debug("qeSelectProductsImportUnits: "+qeSelectProductsImportUnits);
			rs = conn.createStatement().executeQuery(qeSelectProductsImportUnits);
			if (rs.next()){
				
				float totalProduct = rs.getFloat(17);
				if(totalProduct > 0){
					
					float totalUnits = rs.getFloat(1) + rs.getFloat(2) + rs.getFloat(3) + rs.getFloat(4) + rs.getFloat(5) + rs.getFloat(6) + rs.getFloat(7) +
							rs.getFloat(8) + rs.getFloat(9) + rs.getFloat(10) + rs.getFloat(11) + rs.getFloat(12);
					
					for(int i=1; i<=12; i++){
						
						SummaryVO summaryVO = new SummaryVO(FormatUtilities.getMonthName(i));
						
						summaryVO.setAmountUnitStageASummary(rs.getFloat(i));
						
						summaryVO.setAmountFobsStageASummary((rs.getFloat(13)     / totalUnits) * summaryVO.getAmountUnitStageASummary());
						summaryVO.setAmountDutiesStageASummary((rs.getFloat(14)   / totalUnits) * summaryVO.getAmountUnitStageASummary());
						summaryVO.setAmountFreightsStageASummary((rs.getFloat(15) / totalUnits) * summaryVO.getAmountUnitStageASummary());
						summaryVO.setAmountOthersStageASummary((rs.getFloat(16)   / totalUnits) * summaryVO.getAmountUnitStageASummary());
						listSummaryVO.add(summaryVO);
					}
				}
			}
			
			qeSelectProductsImportUnits = "SELECT SUM(UNI.IMU_JAN), SUM(UNI.IMU_FEB), SUM(UNI.IMU_MAR), SUM(UNI.IMU_APR), SUM(UNI.IMU_MAY), SUM(UNI.IMU_JUN), SUM(UNI.IMU_JUL), SUM(UNI.IMU_AUG), SUM(UNI.IMU_SEP), SUM(UNI.IMU_OCT), SUM(UNI.IMU_NOV), SUM(UNI.IMU_DIC), SUM(FOB), SUM(DUTIES), SUM(FREIGHT), SUM(OTHER), COUNT(*) CANT " +
					 "FROM CCS_VIEW_COST_PRODUCT VPR, CCS_IMPORT_UNITS UNI " +
					 "WHERE VPR.STG_ID = UNI.STG_ID " +
					 "AND   VPR.IMU_COD_PRODUCT = UNI.IMU_COD_PRODUCT " +
					 filtersProduct+
					 "AND   UNI.STG_ID = "+idStageB+"";

			rs = conn.createStatement().executeQuery(qeSelectProductsImportUnits);
			if (rs.next()){
				
				float totalProduct = rs.getFloat(17); 
				if(totalProduct > 0){
					
					float totalUnits = rs.getFloat(1) + rs.getFloat(2) + rs.getFloat(3) + rs.getFloat(4) + rs.getFloat(5) + rs.getFloat(6) + rs.getFloat(7) +
							rs.getFloat(8) + rs.getFloat(9) + rs.getFloat(10) + rs.getFloat(11) + rs.getFloat(12);
					
					int i = 1;
					for(SummaryVO summaryVO: listSummaryVO){
						
						summaryVO.setAmountUnitStageBSummary(rs.getFloat(i));
						
						summaryVO.setAmountFobsStageBSummary((rs.getFloat(13)     / totalUnits) * summaryVO.getAmountUnitStageBSummary());
						summaryVO.setAmountDutiesStageBSummary((rs.getFloat(14)   / totalUnits) * summaryVO.getAmountUnitStageBSummary());
						summaryVO.setAmountFreightsStageBSummary((rs.getFloat(15) / totalUnits) * summaryVO.getAmountUnitStageBSummary());
						summaryVO.setAmountOthersStageBSummary((rs.getFloat(16)   / totalUnits) * summaryVO.getAmountUnitStageBSummary());
						
						summaryVO.setByMonthUnitSummary(summaryVO.getAmountUnitStageBSummary() - summaryVO.getAmountUnitStageASummary());
						summaryVO.setByMonthDutiesSummary(summaryVO.getAmountDutiesStageBSummary() - summaryVO.getAmountDutiesStageASummary());
						summaryVO.setByMonthFobsSummary(summaryVO.getAmountFobsStageBSummary() - summaryVO.getAmountFobsStageASummary());
						summaryVO.setByMonthFreightsSummary(summaryVO.getAmountFreightsStageBSummary() - summaryVO.getAmountFreightsStageASummary());
						summaryVO.setByMonthOthersSummary(summaryVO.getAmountOthersStageBSummary() - summaryVO.getAmountOthersStageASummary() );
						
						summaryVO.setDteDutiesSummary((summaryVO.getAmountDutiesStageBSummary() - ((summaryVO.getAmountDutiesStageBSummary() * (1 / exchangeStageA)) / (1 / exchangeStageB))));
						
						summaryVO.setDteFreightsSummary(((summaryVO.getAmountFreightsStageBSummary() + summaryVO.getAmountOthersStageBSummary()) - (((summaryVO.getAmountFreightsStageBSummary() + summaryVO.getAmountOthersStageBSummary()) * (1 / exchangeStageA)) / (1 / exchangeStageB))));
						
						
//						standardDetailVO.setDteAmoutStandarDetail(standardDetailVO.getAmountStageBStandarDetail() - ((standardDetailVO.getAmountStageBStandarDetail() * (1 / exchangeStageA)) / (1 / exchangeStageB))); //341.765
//						standardDetailVO.setExchangeAmoutStandarDetail(standardDetailVO.getNominalAmoutStandarDetail() - standardDetailVO.getDteAmoutStandarDetail());
						
						
						i++;
					}	
				}
			}
			
			
			
			String idStageC = "0";
			
			
    		//identificando id stage del budget para el período para todos los paises.
			String qeTotalFamily = "SELECT STG_ID FROM CCS_STAGE WHERE SIT_ID IN (SELECT SIT_ID FROM ccs_stage WHERE STG_ID = "+idStageA+") AND STS_ID = 8 AND STG_PERIOD = "+FormatUtilities.getCurrentDateYear()+"";
			logger.debug("qeTotalFamily: "+qeTotalFamily);
			rs = conn.createStatement().executeQuery(qeTotalFamily);
			if (rs.next()){
				idStageC = idStageC.concat(","+ rs.getString(1));
			}
			
			
			qeSelectProductsImportUnits = "SELECT SUM(UNI.IMU_JAN), SUM(UNI.IMU_FEB), SUM(UNI.IMU_MAR), SUM(UNI.IMU_APR), SUM(UNI.IMU_MAY), SUM(UNI.IMU_JUN), SUM(UNI.IMU_JUL), SUM(UNI.IMU_AUG), SUM(UNI.IMU_SEP), SUM(UNI.IMU_OCT), SUM(UNI.IMU_NOV), SUM(UNI.IMU_DIC), SUM(FOB), SUM(DUTIES), SUM(FREIGHT), SUM(OTHER), COUNT(*) CANT " +
					 "FROM CCS_VIEW_COST_PRODUCT VPR, CCS_IMPORT_UNITS UNI " +
					 "WHERE VPR.STG_ID = UNI.STG_ID " +
					 "AND   VPR.IMU_COD_PRODUCT = UNI.IMU_COD_PRODUCT " +
					 filtersProduct+
					 "AND   UNI.STG_ID IN ("+idStageC+") ";


			logger.debug("qeSelectProductsImportUnits: "+qeSelectProductsImportUnits);
			rs = conn.createStatement().executeQuery(qeSelectProductsImportUnits);
			if (rs.next()){
			
				float totalProduct = rs.getFloat(17);
				if(totalProduct > 0){
					
					int i = 1;
					for(SummaryVO summaryVO: listSummaryVO){
					
						
						summaryVO.setAmountUnitStageCSummary(rs.getFloat(i));
						summaryVO.setAmountFobsStageCSummary((rs.getFloat(13)     / totalProduct) * summaryVO.getAmountUnitStageCSummary());
						summaryVO.setAmountDutiesStageCSummary((rs.getFloat(14)   / totalProduct) * summaryVO.getAmountUnitStageCSummary());
						summaryVO.setAmountFreightsStageCSummary((rs.getFloat(15) / totalProduct) * summaryVO.getAmountUnitStageCSummary());
						summaryVO.setAmountOthersStageCSummary((rs.getFloat(16)   / totalProduct) * summaryVO.getAmountUnitStageCSummary());
						
						i++;
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
		
		return listSummaryVO;
	}
	
	
    
	public List<MonthlyDataVO> initMonthlyData(String idStageA, String idStageB, String filtersProduct) throws DAOException{
		
		Connection conn = null;
		
		List<MonthlyDataVO> listMonthlyDataVO = new ArrayList<MonthlyDataVO>();
		try {
			
			conn = dataConnection.getConnectionOracleMSD();
			
			if (filtersProduct != null)
				filtersProduct = "AND STG_A.IMU_COD_PRODUCT IN "+filtersProduct+" ";	
			else
				filtersProduct = "";
			
			
			
			//Obteniendo tasas de intercambio
			float exchangeStageA = 0;
			float exchangeStageB = 0;
			
			ResultSet rs = conn.createStatement().executeQuery("SELECT (SMV_AMOUNT / 100) FROM CCS_SETUP_MACRO_VARS WHERE STG_ID = "+idStageA+" AND SMV_COD = 'ERA'");
			if (rs.next()) exchangeStageA = rs.getFloat(1);
			
			rs = conn.createStatement().executeQuery("SELECT (SMV_AMOUNT / 100) FROM CCS_SETUP_MACRO_VARS WHERE STG_ID = "+idStageB+" AND SMV_COD = 'ERA'");
			if (rs.next()) exchangeStageB = rs.getFloat(1);
			
			
			
			
			String qeSelectMonthlyData = "SELECT IMU_COD_PRODUCT, IMU_DESRIPTION_PRODUCT, FREIGHT, DUTIES, UNITS, OTHER, FOB FROM CCS_VIEW_COST_PRODUCT WHERE STG_ID =  "+idStageA+""; 
			
			logger.debug("qeSelectMonthlyDataA: "+qeSelectMonthlyData);
			
			rs = conn.createStatement().executeQuery(qeSelectMonthlyData);
			while (rs.next()){
				
				MonthlyDataVO monthlyDataVO = new MonthlyDataVO();
				
				monthlyDataVO.setIdProductMonthly(rs.getString(1));
				monthlyDataVO.setDescProductMonthly(rs.getString(2));
				
				//Stage A
				monthlyDataVO.setStageAFreightMonthly(rs.getFloat(3) + rs.getFloat(6));
				monthlyDataVO.setStageADutiesMonthly(rs.getFloat(4));
				monthlyDataVO.setStageAUnitMonthly(rs.getFloat(5));
				monthlyDataVO.setStageAFOBsMonthly(rs.getFloat(7));
				
				
				listMonthlyDataVO.add(monthlyDataVO);
			}
			
			
			qeSelectMonthlyData = "SELECT IMU_COD_PRODUCT, IMU_DESRIPTION_PRODUCT, FREIGHT, DUTIES, UNITS, OTHER, FOB FROM CCS_VIEW_COST_PRODUCT WHERE STG_ID =  "+idStageB+""; 
			
			rs = conn.createStatement().executeQuery(qeSelectMonthlyData);
			while (rs.next()){
				
				
				for (int i=0; i<listMonthlyDataVO.size();i++){
					
					MonthlyDataVO monthlyDataVO = listMonthlyDataVO.get(i);
					
					if (monthlyDataVO.getIdProductMonthly().equals(rs.getString(1))){
						
						monthlyDataVO.setStageBFreightMonthly(rs.getFloat(3) + rs.getFloat(6));
						monthlyDataVO.setStageBDutiesMonthly(rs.getFloat(4));
						monthlyDataVO.setStageBUnitMonthly(rs.getFloat(5));
						monthlyDataVO.setStageBFOBsMonthly(rs.getFloat(7));
						
						
//						monthlyDataVO.setUnitVarianceMonthly((monthlyDataVO.getStageBUnitMonthly() - monthlyDataVO.getStageAUnitMonthly()) * 100 / monthlyDataVO.getStageAUnitMonthly());
//						monthlyDataVO.setDutiesVarianceMonthly((monthlyDataVO.getStageBDutiesMonthly() - monthlyDataVO.getStageADutiesMonthly()) * 100 / monthlyDataVO.getStageADutiesMonthly());
//						monthlyDataVO.setfOBsVarianceMonthly((monthlyDataVO.getStageBFOBsMonthly() - monthlyDataVO.getStageAFOBsMonthly()) * 100 / monthlyDataVO.getStageAFOBsMonthly());
//						monthlyDataVO.setFreightVarianceMonthly((monthlyDataVO.getStageBFreightMonthly() - monthlyDataVO.getStageAFreightMonthly() ) * 100 / monthlyDataVO.getStageAFreightMonthly());
						
						logger.debug("monthlyDataVO.getStageBUnitMonthly(): "+monthlyDataVO.getStageBUnitMonthly());
						logger.debug("monthlyDataVO.getStageAUnitMonthly(): "+monthlyDataVO.getStageAUnitMonthly());
						monthlyDataVO.setUnitVarianceMonthly(monthlyDataVO.getStageBUnitMonthly() - monthlyDataVO.getStageAUnitMonthly());
						monthlyDataVO.setDutiesVarianceMonthly(monthlyDataVO.getStageBDutiesMonthly() - monthlyDataVO.getStageADutiesMonthly());
						monthlyDataVO.setfOBsVarianceMonthly(monthlyDataVO.getStageBFOBsMonthly() - monthlyDataVO.getStageAFOBsMonthly());
						monthlyDataVO.setFreightVarianceMonthly((monthlyDataVO.getStageBFreightMonthly()) - (monthlyDataVO.getStageAFreightMonthly()));
						
						
						monthlyDataVO.setDteDutiesMonthly(monthlyDataVO.getStageBDutiesMonthly() - ((monthlyDataVO.getStageBDutiesMonthly() * (1 / exchangeStageA)) / (1 / exchangeStageB)));
						monthlyDataVO.setExchangeDutiesMonthly(monthlyDataVO.getDutiesVarianceMonthly()  - monthlyDataVO.getDteDutiesMonthly()); 
						
						
//						standardDetailVO.setDteAmoutStandarDetail(standardDetailVO.getAmountStageBStandarDetail() - ((standardDetailVO.getAmountStageBStandarDetail() * (1 / exchangeStageA)) / (1 / exchangeStageB))); //341.765
//						standardDetailVO.setExchangeAmoutStandarDetail(standardDetailVO.getNominalAmoutStandarDetail() - standardDetailVO.getDteAmoutStandarDetail());
						
						
						
						monthlyDataVO.setDteFreightMonthly(monthlyDataVO.getStageBFreightMonthly() - ((monthlyDataVO.getStageBFreightMonthly() * (1 / exchangeStageA)) / (1 / exchangeStageB)));
						monthlyDataVO.setExchangeFreightMonthly(monthlyDataVO.getFreightVarianceMonthly()  - monthlyDataVO.getDteFreightMonthly());
						
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
		
		return listMonthlyDataVO;
	}
    
    
	public List<ProductivityVO> initProductivity(String idStageA, String idStageB) throws DAOException{
		
		Connection conn = null;
		
		List<ProductivityVO> listProductivityVO = new ArrayList<ProductivityVO>();
		try {
			
			conn = dataConnection.getConnectionOracleMSD();
			
			
			float exchangeStageA = 0;
			float exchangeStageB = 0;
			
			float inflationStageB = 0;
			
			ResultSet rs = conn.createStatement().executeQuery("SELECT (SMV_AMOUNT) FROM CCS_SETUP_MACRO_VARS WHERE STG_ID = "+idStageA+" AND SMV_COD = 'ERA'");
			if (rs.next()) exchangeStageA = rs.getFloat(1);
			
			rs = conn.createStatement().executeQuery("SELECT (SMV_AMOUNT) FROM CCS_SETUP_MACRO_VARS WHERE STG_ID = "+idStageB+" AND SMV_COD = 'ERA'");
			if (rs.next()) exchangeStageB = rs.getFloat(1);
			
			
			rs = conn.createStatement().executeQuery("SELECT (SMV_AMOUNT / 100) FROM CCS_SETUP_MACRO_VARS WHERE STG_ID = "+idStageB+" AND SMV_COD = 'INF'");
			if (rs.next()) inflationStageB = rs.getFloat(1);
			
			
			
			//Base Stage
			String qeSelectProductsImportUnits = "SELECT IMU_COD_PRODUCT, IMU_DESRIPTION_PRODUCT, FREIGHT, DUTIES, UNITS, OTHER FROM CCS_VIEW_COST_PRODUCT WHERE STG_ID =  "+idStageA+" "; 
			rs = conn.createStatement().executeQuery(qeSelectProductsImportUnits);
			
			
//			float freightBaseUSD = 0f;
//			float dutiesBaseUSD  = 0f;
//			float otherBaseUSD   = 0f;
			while (rs.next()){
				
				

				
				ProductivityVO productivityVO = new ProductivityVO();
				productivityVO.setUnitStageAProductivity(rs.getFloat(5));
				
				
				logger.debug("rs.getFloat(1): "+rs.getString(1));
				logger.debug("rs.getFloat(4): "+rs.getFloat(4));
				logger.debug("productivityVO.getUnitStageAProductivity(): "+productivityVO.getUnitStageAProductivity());
				
//				freightBaseUSD = rs.getFloat(3) / productivityVO.getUnitStageAProductivity();
//				dutiesBaseUSD  = rs.getFloat(4) / productivityVO.getUnitStageAProductivity(); //1.2
				
//				logger.debug("dutiesBaseUSD: "+dutiesBaseUSD);
				logger.debug("**********************************************");
//				otherBaseUSD   = rs.getFloat(6) / productivityVO.getUnitStageAProductivity();
				
				productivityVO.setIdProductProductivity(rs.getString(1));
				productivityVO.setDescProductProductivity(rs.getString(2));
				
				
				if (productivityVO.getUnitStageAProductivity() == 0)
					productivityVO.setFreightStageAProductivity(0f);
				else
					productivityVO.setFreightStageAProductivity(rs.getFloat(3) / productivityVO.getUnitStageAProductivity() * exchangeStageA);
				
				if(productivityVO.getUnitStageAProductivity() == 0)
					productivityVO.setDutiesStageAProductivity(0f);
				else
					productivityVO.setDutiesStageAProductivity(rs.getFloat(4)  / productivityVO.getUnitStageAProductivity() * exchangeStageA);
				
				if(productivityVO.getUnitStageAProductivity() == 0)
					productivityVO.setOtherStageAProductivity(0f);
				else
					productivityVO.setOtherStageAProductivity(rs.getFloat(6)   / productivityVO.getUnitStageAProductivity() * exchangeStageA);
				
				listProductivityVO.add(productivityVO);
				
			}
			
			//New Stage
			qeSelectProductsImportUnits = "SELECT IMU_COD_PRODUCT, IMU_DESRIPTION_PRODUCT, FREIGHT, DUTIES, UNITS, OTHER FROM CCS_VIEW_COST_PRODUCT WHERE STG_ID =  "+idStageB+" "; 
			
			logger.debug("qeSelectProductsImportUnits: "+qeSelectProductsImportUnits); 
			rs = conn.createStatement().executeQuery(qeSelectProductsImportUnits);
			
			while (rs.next()){
				
				for (int i=0; i<listProductivityVO.size();i++){
					
					ProductivityVO productivityVO = listProductivityVO.get(i);
					
					if (productivityVO.getIdProductProductivity().equals(rs.getString(1))){
						
						
						productivityVO.setUnitStageBProductivity(rs.getFloat(5));
						
						float freightNewUSD = 0;
						if (productivityVO.getUnitStageBProductivity() != 0)
							freightNewUSD = rs.getFloat(3) / productivityVO.getUnitStageBProductivity();
						
						float dutiesNewUSD = 0;
						 if(productivityVO.getUnitStageBProductivity() != 0)
							dutiesNewUSD  = rs.getFloat(4) / productivityVO.getUnitStageBProductivity();
						
						 float otherNewUSD = 0;
						 if(productivityVO.getUnitStageBProductivity() !=0)							 
							otherNewUSD   = rs.getFloat(6) / productivityVO.getUnitStageBProductivity();						 
						 
						 
						 if (productivityVO.getUnitStageBProductivity() == 0)
							 productivityVO.setFreightStageBProductivity(0f);
							else
								productivityVO.setFreightStageBProductivity(rs.getFloat(3) / productivityVO.getUnitStageBProductivity() * exchangeStageB);
						 
						 if(productivityVO.getUnitStageBProductivity() == 0)
							 productivityVO.setDutiesStageBProductivity(0f);
						 	else
						 		productivityVO.setDutiesStageBProductivity(rs.getFloat(4)  / productivityVO.getUnitStageBProductivity() * exchangeStageB);
						 
						 if(productivityVO.getUnitStageBProductivity() == 0)
							 productivityVO.setOtherStageBProductivity(0f);
						 	else
						 		productivityVO.setOtherStageBProductivity(rs.getFloat(6)   / productivityVO.getUnitStageBProductivity() * exchangeStageB);
						
						 
						 
//						productivityVO.setFreightStageBProductivity(rs.getFloat(3) / productivityVO.getUnitStageBProductivity() * exchangeStageB);
//						productivityVO.setDutiesStageBProductivity(rs.getFloat(4)  / productivityVO.getUnitStageBProductivity() * exchangeStageB);
//						productivityVO.setOtherStageBProductivity(rs.getFloat(6)   / productivityVO.getUnitStageBProductivity() * exchangeStageB);

						
						//para el total de duties solo restar el exchange de los duties
						
						productivityVO.setInflationProductivity((productivityVO.getOtherStageAProductivity() * inflationStageB * productivityVO.getUnitStageBProductivity()) / exchangeStageB); //otros costos por la inflación del stage B
						
						
						logger.debug("*******************************************************************");
						logger.debug("rs.getString(1): "+rs.getString(1));
						logger.debug("productivityVO.getUnitStageBProductivity(): "+productivityVO.getUnitStageBProductivity());
						logger.debug("productivityVO.getDutiesStageAProductivity(): "+productivityVO.getDutiesStageAProductivity());
						logger.debug("productivityVO.getFreightStageAProductivity(): "+productivityVO.getFreightStageAProductivity());
						logger.debug("productivityVO.getOtherStageAProductivity(): "+productivityVO.getOtherStageAProductivity());
						logger.debug("exchangeStageB: "+exchangeStageB);
						logger.debug("exchangeStageA: "+exchangeStageA);
						
									
						
						
						//Duties
						float exchangeBTotalDuties = 0;
						if(exchangeStageB != 0)
							exchangeBTotalDuties = (productivityVO.getUnitStageBProductivity()  * productivityVO.getDutiesStageAProductivity()  / exchangeStageB);
														
						float exchangeATotalDuties = 0;
						if(exchangeStageA != 0)
							exchangeATotalDuties  = (productivityVO.getUnitStageBProductivity() * productivityVO.getDutiesStageAProductivity() / exchangeStageA);
						
						float exchangeTotalDuties  = (exchangeBTotalDuties) - (exchangeATotalDuties);
						
						
						
						
						//Freight
						float exchangeBTotalFreight = 0;
						if(exchangeStageB != 0)
							exchangeBTotalFreight = (productivityVO.getUnitStageBProductivity() * productivityVO.getFreightStageAProductivity()  / exchangeStageB);
						
						float exchangeATotalFreight = 0;
						if(exchangeStageA != 0)
							exchangeATotalFreight = (productivityVO.getUnitStageBProductivity() * productivityVO.getFreightStageAProductivity() / exchangeStageA);
						
						float exchangeTotalFreight = (exchangeBTotalFreight) - (exchangeATotalFreight);
						
						
						//Others
						float exchangeBTotalOthers = 0;
						if(exchangeStageB != 0)
							exchangeBTotalOthers = (productivityVO.getUnitStageBProductivity()  * productivityVO.getOtherStageAProductivity()  / exchangeStageB);
						
						float exchangeATotalOthers = 0;
						if(exchangeStageA != 0)
							exchangeATotalOthers = (productivityVO.getUnitStageBProductivity() * productivityVO.getOtherStageAProductivity() / exchangeStageA);
						
						float exchangeTotalOthers = (exchangeBTotalOthers) - (exchangeATotalOthers);
							
//						float exchangeTotalDuties  = (productivityVO.getUnitStageBProductivity()  * productivityVO.getDutiesStageAProductivity()  / exchangeStageB) - (productivityVO.getUnitStageBProductivity() * productivityVO.getDutiesStageAProductivity() / exchangeStageA);
//						float exchangeTotalFreight  = (productivityVO.getUnitStageBProductivity() * productivityVO.getFreightStageAProductivity()  / exchangeStageB) - (productivityVO.getUnitStageBProductivity() * productivityVO.getFreightStageAProductivity() / exchangeStageA);
//						float exchangeTotalOthers  = (productivityVO.getUnitStageBProductivity()  * productivityVO.getOtherStageAProductivity()  / exchangeStageB) - (productivityVO.getUnitStageBProductivity() * productivityVO.getOtherStageAProductivity() / exchangeStageA);
						
						
						
						logger.debug("exchangeTotalDuties: "+exchangeTotalDuties);
						logger.debug("exchangeTotalFreight: "+exchangeTotalFreight);
						logger.debug("exchangeTotalOthers: "+exchangeTotalOthers);
						
						
						
						
						
						productivityVO.setExchangeProductivity(exchangeTotalDuties + exchangeTotalFreight + exchangeTotalOthers);
						
						
						logger.debug("dutiesNewUSD: "+dutiesNewUSD);
						logger.debug("exchangeTotalDuties: "+exchangeTotalDuties);
						logger.debug("productivityVO.getInflationProductivity(): "+productivityVO.getInflationProductivity());
						
						productivityVO.setTotalDutiesProductivity((productivityVO.getUnitStageBProductivity() * ((dutiesNewUSD) - (productivityVO.getDutiesStageAProductivity() / exchangeStageA))) - exchangeTotalDuties);
						
						
						productivityVO.setTotalFreightProductivity((productivityVO.getUnitStageBProductivity() * ((freightNewUSD + otherNewUSD) - ((productivityVO.getFreightStageAProductivity() / exchangeStageA) + (productivityVO.getOtherStageAProductivity() / exchangeStageA)))) - (exchangeTotalFreight + exchangeTotalOthers)- productivityVO.getInflationProductivity());
						productivityVO.setTotalLocalAddProductivity(productivityVO.getTotalDutiesProductivity() + productivityVO.getTotalFreightProductivity());
					
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
		
		logger.debug("listProductivityVO.size(): "+listProductivityVO.size());
		
		return listProductivityVO;
	}
	
	
//	public List<StageVO> listStagesFilter(String idStage) throws DAOException{
//	public List<PagoCobranzaVO> listStagesFilter(String factura) throws DAOException{
	public void listStagesFilter(String factura) throws DAOException{
				
		Connection conn = null;
		
		List<PagoCobranzaVO> listPagoCobranzaVO = new ArrayList<PagoCobranzaVO>();
		
		List<StageVO> listStageVO = new ArrayList<StageVO>();
		try {
//			conn = dataConnection.getConnectionOracleMSD();
			conn = dataConnection.getConnectionMysql();
			
			String queryActualiza = "UPDATE flt_pago_proveedores SET pap_estado_proveedor='PAGADO' WHERE pap_numero_factura_proveedor= '"+factura+"' ;";
			conn.createStatement().executeQuery(queryActualiza);
			
//			String queryExecute = "SELECT SIT_ID FROM CCS_STAGE WHERE STG_ID = "+idStage+"";
			
			
//			ResultSet rs = conn.createStatement().executeQuery(queryExecute);
			
//			String idSiteStage = "0";
//			if(rs.next())
//				idSiteStage = rs.getString(1);
//			
//			
//			String currentPeriod = FormatUtilities.getCurrentDateYear();
			
			
			
			
//			String qeSelectProductsImportUnits = "SELECT STG_ID, SGT_NAME FROM CCS_STAGE WHERE STG_ID IN (select STG_ID FROM CCS_STAGE where STS_ID = 2 AND SIT_ID = "+idSiteStage+") AND STG_PERIOD = "+currentPeriod+" " + 
//												 "UNION ALL " +
//												 "SELECT STG_ID, SGT_NAME FROM CCS_STAGE WHERE STG_ID IN (select STG_ID FROM CCS_STAGE where STS_ID = 4 AND SIT_ID = "+idSiteStage+") AND STG_PERIOD = "+currentPeriod+" " + 
//												 "UNION ALL " +
//												 "SELECT STG_ID, SGT_NAME FROM CCS_STAGE WHERE STG_ID IN (select STG_ID FROM CCS_STAGE where STS_ID = 5 AND SIT_ID = "+idSiteStage+") AND STG_PERIOD = "+currentPeriod+" " + 
//												 "UNION ALL " +
//												 "SELECT STG_ID, SGT_NAME FROM CCS_STAGE WHERE STG_ID IN (select STG_ID FROM CCS_STAGE where STS_ID = 9 AND SIT_ID = "+idSiteStage+") AND STG_PERIOD = "+currentPeriod+" " + 
//												 "UNION ALL " +
//												 "SELECT STG_ID, SGT_NAME FROM CCS_STAGE WHERE STG_ID IN (select STG_ID FROM CCS_STAGE where STS_ID = 7 AND SIT_ID = "+idSiteStage+") AND STG_PERIOD = "+(Integer.parseInt(currentPeriod) + 1)+" " + 
//												 "UNION ALL " +
//												 "SELECT STG_ID, SGT_NAME FROM CCS_STAGE WHERE STG_ID IN (select STG_ID FROM CCS_STAGE where STS_ID = 8 AND SIT_ID = "+idSiteStage+") AND STG_PERIOD = "+currentPeriod+" " + 
//												 "UNION ALL " +
//												 "SELECT STG_ID, SGT_NAME FROM CCS_STAGE WHERE STG_ID IN (select STG_ID FROM CCS_STAGE where STS_ID = 10 AND SIT_ID = "+idSiteStage+") AND STG_PERIOD = "+(Integer.parseInt(currentPeriod) + 1)+" " + 
//												 "UNION ALL " +
//												 "SELECT STG_ID, SGT_NAME FROM CCS_STAGE WHERE STS_ID IN (6) AND SIT_ID = "+idSiteStage+" AND STG_PERIOD = "+currentPeriod+" "; 

			
//			logger.debug(qeSelectProductsImportUnits);
//			rs = conn.createStatement().executeQuery(qeSelectProductsImportUnits);
//			while (rs.next()){
//				PagoCobranzaVO pagoCobranzaVO = new PagoCobranzaVO();
//				
//				pagoCobranzaVO.setIdStage(rs.getString(1));
//				pagoCobranzaVO.setNameStage(rs.getString(2));
//				
//				listStageVO.add(stageVO);
//				listPagoCobranzaVO.add(pagoCobranzaVO);
//			}
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
	
//		return listStageVO;
		
	}
	
	public String getIdStagePlan(String idStageBase) throws DAOException{
		
		Connection conn = null;
		
		String idStagePlan = "";
		try {
			
			conn = dataConnection.getConnectionOracleMSD();
			
			String qeSelectidStagePlan = "SELECT STG_ID FROM CCS_STAGE WHERE STS_ID = 8 AND STG_PERIOD = "+FormatUtilities.getCurrentDateYear()+" AND SIT_ID IN (select sit_id from ccs_stage where stg_id = "+idStageBase+")"; 
			
			ResultSet rs = conn.createStatement().executeQuery(qeSelectidStagePlan);
			if (rs.next())
				idStagePlan = rs.getString(1);
			
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
	
		return idStagePlan;
	}
	
	public String getIdStateStage(String idStage) throws DAOException{
		
		Connection conn = null;
		
		String idStateStage = "";
		try {
			
			conn = dataConnection.getConnectionOracleMSD();
			
			String qeSelectidStagePlan = "SELECT STS_ID FROM CCS_STAGE WHERE STG_ID = "+idStage+"";
			
			logger.debug("qeSelectidStagePlan: "+qeSelectidStagePlan);

			ResultSet rs = conn.createStatement().executeQuery(qeSelectidStagePlan);
			if (rs.next())
				idStateStage = rs.getString(1);
			
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
	
		return idStateStage;
	}
	
	public String getNameStateStage(String idStage) throws DAOException{
		
		Connection conn = null;
		
		String idStateStage = "";
		try {
			
			conn = dataConnection.getConnectionOracleMSD();
			
			String qeSelectidStagePlan = "SELECT STS.STS_NAME FROM CCS_STAGE STG, CCS_STAGE_STATE STS WHERE  STG.STS_ID = STS.STS_ID AND STG.STG_ID = "+idStage+"";
			
			logger.debug("qeSelectidStagePlan: "+qeSelectidStagePlan);

			ResultSet rs = conn.createStatement().executeQuery(qeSelectidStagePlan);
			if (rs.next())
				idStateStage = rs.getString(1);
			
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
	
		return idStateStage;
	}
	
	
	public String getListProductFilter(FilterProductStageVO filterProductStageVO, String idStage) throws DAOException{
		
		Connection conn = null;
		
		String filterProductsIn = "('0'";
		try {
			
			conn = dataConnection.getConnectionOracleMSD();
			
			String qeSelectidFilterProducts = "SELECT MAS.IMU_COD_PRODUCT " +
					"FROM CCS_LOCAL_ITEM_MASTER MAS, CCS_IMPORT_UNITS IMP " +
					"WHERE MAS.STG_ID =  "+idStage+" " +
					
					
					"AND   MAS.STG_ID = IMP.STG_ID " +
					"AND   MAS.IMU_COD_PRODUCT = IMP.IMU_COD_PRODUCT " +
					
					"AND  (MAS.PFA_ID = '"+filterProductStageVO.getIdFamilyProduct()+"' OR '"+filterProductStageVO.getIdFamilyProduct()+"' is null ) " +
					
					"AND  (MAS.PTY_ID = '"+filterProductStageVO.getIdTypeProduct()+"' OR '"+filterProductStageVO.getIdTypeProduct()+"' is null ) " +
					"AND  (MAS.PRP_ID = '"+filterProductStageVO.getIdPresentationProduct()+"' OR '"+filterProductStageVO.getIdPresentationProduct()+"' is null ) " +
					"AND  (MAS.PTL_ID = '"+filterProductStageVO.getIdTypeLoadProduct()+"' OR '"+filterProductStageVO.getIdTypeLoadProduct()+"' is null ) " +
					"AND  (MAS.PTR_ID = '"+filterProductStageVO.getIdTradeProduct()+"' OR '"+filterProductStageVO.getIdTradeProduct()+"' is null ) " +
					"AND  (MAS.SDD_COD = '"+filterProductStageVO.getIdCustomDutiesProduct()+"' OR '"+filterProductStageVO.getIdCustomDutiesProduct()+"' is null ) " + 
					"AND  UPPER(IMP.IMU_DESRIPTION_PRODUCT) like UPPER('%"+filterProductStageVO.getNameProduct()+"%') " ;
			logger.debug("qeSelectidFilterProducts: "+qeSelectidFilterProducts);

			ResultSet rs = conn.createStatement().executeQuery(qeSelectidFilterProducts);
			while (rs.next()){
				filterProductsIn = filterProductsIn.concat(",'").concat(rs.getString(1)).concat("'");
			}
			filterProductsIn = filterProductsIn.concat(") ");
			
			logger.debug("filterProductsIn: "+filterProductsIn);
			
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
	
		return filterProductsIn;
	}
	
	
	public List<GeneralComboVO> listDutiesStage(String idStage) throws DAOException{
		
		Connection conn = null;
		List<GeneralComboVO> listGeneralComboVO = new ArrayList<GeneralComboVO>();
		try {
			conn = dataConnection.getConnectionOracleMSD();
			
			String queryExecute = "SELECT SDD_COD, SDD_DUTY_NAME FROM CCS_SETUP_CUSTUM_DUTIES_DETAIL WHERE STG_ID = "+idStage+" ";
			
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
	
	
	
	public List<StandardDetailVO> listStandarDetailStage(String idStageA, String idStageB, String filtersProduct) throws DAOException{
		
		Connection conn = null;
		List<StandardDetailVO> listStandardDetailVO = new ArrayList<StandardDetailVO>();
		try {
			conn = dataConnection.getConnectionOracleMSD();
			
			//Obteniendo tasas de intercambio
			float exchangeStageA = 0;
			float exchangeStageB = 0;
			
			ResultSet rs = conn.createStatement().executeQuery("SELECT (SMV_AMOUNT) FROM CCS_SETUP_MACRO_VARS WHERE STG_ID = "+idStageA+" AND SMV_COD = 'ERA'");
			if (rs.next()) exchangeStageA = rs.getFloat(1);
			
			rs = conn.createStatement().executeQuery("SELECT (SMV_AMOUNT) FROM CCS_SETUP_MACRO_VARS WHERE STG_ID = "+idStageB+" AND SMV_COD = 'ERA'");
			if (rs.next()) exchangeStageB = rs.getFloat(1);
			
			
			
			String queryExecute = "select SUM(LIM_FOB_COST * (IMU_JAN + IMU_FEB + IMU_MAR + IMU_APR + IMU_MAY + IMU_JUN + IMU_JUL + IMU_AUG + IMU_SEP + IMU_OCT + IMU_NOV + IMU_DIC)) from CCS_LOCAL_ITEM_MASTER LIM, CCS_IMPORT_UNITS UNI "
					 + "WHERE  LIM.STG_ID = "+idStageA+" "
					 + "AND    LIM.IMU_COD_PRODUCT = UNI.IMU_COD_PRODUCT " 
					 + "AND    LIM.STG_ID = UNI.STG_ID";
			
			logger.debug("queryExecute: "+queryExecute);
			rs = conn.createStatement().executeQuery(queryExecute);
			if (rs.next()){
				StandardDetailVO standardDetailVO = new StandardDetailVO();
				
				standardDetailVO.setTypeCostStandarDetail("FOB");
				standardDetailVO.setSubTypeCostStandarDetail("INTERCOMPANY");
				standardDetailVO.setNameCostStandarDetail("Transfer Price");
				standardDetailVO.setCodCostStandarDetail("TRF");
				standardDetailVO.setAmountStageAStandarDetail(rs.getFloat(1));
				
				listStandardDetailVO.add(standardDetailVO);
			}
			
			
			
			queryExecute = "SELECT COS.UNC_COST_TYPE, COS.UNC_COST_SUBTYPE, COS.UNC_COST_NAME, SUM(COS.UNC_COST_AMOUNT * (IMU_JAN + IMU_FEB + IMU_MAR + IMU_APR + IMU_MAY + IMU_JUN + IMU_JUL + IMU_AUG + IMU_SEP + IMU_OCT + IMU_NOV + IMU_DIC) ), COS.UNC_COST_COD FROM CCS_UNITS_COST COS, CCS_IMPORT_UNITS IMP " +
					"WHERE  COS.STG_ID = "+idStageA+" " +
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
				standardDetailVO.setAmountStageAStandarDetail(rs.getFloat(4));
				standardDetailVO.setCodCostStandarDetail(rs.getString(5));
				
				listStandardDetailVO.add(standardDetailVO);
			}
			
			
			
			queryExecute = "select SUM(LIM_FOB_COST * (IMU_JAN + IMU_FEB + IMU_MAR + IMU_APR + IMU_MAY + IMU_JUN + IMU_JUL + IMU_AUG + IMU_SEP + IMU_OCT + IMU_NOV + IMU_DIC)) from CCS_LOCAL_ITEM_MASTER LIM, CCS_IMPORT_UNITS UNI "
					 + "WHERE  LIM.STG_ID = "+idStageB+" "
					 + "AND    LIM.IMU_COD_PRODUCT = UNI.IMU_COD_PRODUCT " 
					 + "AND    LIM.STG_ID = UNI.STG_ID";
			logger.debug("queryExecute: "+queryExecute);
			rs = conn.createStatement().executeQuery(queryExecute);
			if (rs.next()){
				for(StandardDetailVO standardDetailVO: listStandardDetailVO){
					
					if (standardDetailVO.getCodCostStandarDetail().equals("TRF")){ 
						standardDetailVO.setAmountStageBStandarDetail(rs.getFloat(1));
						
						standardDetailVO.setNominalAmoutStandarDetail(standardDetailVO.getAmountStageBStandarDetail() - standardDetailVO.getAmountStageAStandarDetail());
						
						logger.debug("standardDetailVO.getAmountStageBStandarDetail(): "+ standardDetailVO.getAmountStageBStandarDetail());
						logger.debug("exchangeStageB: "+ exchangeStageB);
						logger.debug("exchangeStageA: "+ exchangeStageA);
						standardDetailVO.setDteAmoutStandarDetail(standardDetailVO.getAmountStageBStandarDetail() - ((standardDetailVO.getAmountStageBStandarDetail() * (1 / exchangeStageA)) / (1 / exchangeStageB))); //341.765
						standardDetailVO.setExchangeAmoutStandarDetail(standardDetailVO.getNominalAmoutStandarDetail() - standardDetailVO.getDteAmoutStandarDetail());
						
						if ((int)standardDetailVO.getExchangeAmoutStandarDetail() == 0)
							standardDetailVO.setPercentAmoutStandarDetail(0f);
						else{
							logger.debug("standardDetailVO.getExchangeAmoutStandarDetail(): "+standardDetailVO.getExchangeAmoutStandarDetail());
							logger.debug("standardDetailVO.getAmountStageBStandarDetail(): "+standardDetailVO.getAmountStageBStandarDetail());
							standardDetailVO.setPercentAmoutStandarDetail((standardDetailVO.getExchangeAmoutStandarDetail() / standardDetailVO.getAmountStageAStandarDetail()) *100);
						}
						
//						standardDetailVO.setPercentAmoutStandarDetail(standardDetailVO.getAmountStageBStandarDetail() / (standardDetailVO.getExchangeAmoutStandarDetail()==0?1:standardDetailVO.getExchangeAmoutStandarDetail()));
					}
				}
			}
			
			queryExecute = "SELECT COS.UNC_COST_TYPE, COS.UNC_COST_SUBTYPE, COS.UNC_COST_NAME, SUM(COS.UNC_COST_AMOUNT * (IMU_JAN + IMU_FEB + IMU_MAR + IMU_APR + IMU_MAY + IMU_JUN + IMU_JUL + IMU_AUG + IMU_SEP + IMU_OCT + IMU_NOV + IMU_DIC) ), COS.UNC_COST_COD  FROM CCS_UNITS_COST COS, CCS_IMPORT_UNITS IMP " +
					"WHERE  COS.STG_ID = "+idStageB+" " +
					"AND    COS.STG_ID = IMP.STG_ID " +
					"AND    COS.IMU_COD_PRODUCT = IMP.IMU_COD_PRODUCT " +
					"GROUP BY COS.UNC_COST_TYPE, COS.UNC_COST_SUBTYPE, COS.UNC_COST_NAME, COS.UNC_COST_COD " +
					"ORDER BY 1,2,3";
			logger.debug("queryExecute: "+queryExecute);
			rs = conn.createStatement().executeQuery(queryExecute);
			while (rs.next()){
				
				for(StandardDetailVO standardDetailVO: listStandardDetailVO){
					if (standardDetailVO.getCodCostStandarDetail().equals(rs.getString(5))){
						standardDetailVO.setAmountStageBStandarDetail(rs.getFloat(4));
						standardDetailVO.setNominalAmoutStandarDetail(standardDetailVO.getAmountStageBStandarDetail() - standardDetailVO.getAmountStageAStandarDetail());
						
						
						
						standardDetailVO.setDteAmoutStandarDetail(standardDetailVO.getAmountStageBStandarDetail() - ((standardDetailVO.getAmountStageBStandarDetail() * (1 / exchangeStageA)) / (1 / exchangeStageB)));
						
						standardDetailVO.setExchangeAmoutStandarDetail(standardDetailVO.getNominalAmoutStandarDetail() - standardDetailVO.getDteAmoutStandarDetail());
						
						
						
						
						
						
						if ((int)standardDetailVO.getExchangeAmoutStandarDetail() == 0)
							standardDetailVO.setPercentAmoutStandarDetail(0f);
						else
							standardDetailVO.setPercentAmoutStandarDetail((standardDetailVO.getExchangeAmoutStandarDetail() / standardDetailVO.getAmountStageAStandarDetail()) *100);
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
		return listStandardDetailVO;
	}
	
	
	public List<StandardDetailVO> initImpactAnalisysStage(String idStageA, String idStageB, String filtersProduct) throws DAOException{
		
		Connection conn = null;
		List<StandardDetailVO> listStandardDetailVO = new ArrayList<StandardDetailVO>();
		try {
			conn = dataConnection.getConnectionOracleMSD();
			
			
			//Obteniendo tasas de intercambio
			float exchangeStageA  = 0;
			float exchangeStageB  = 0;
			float inflationStageB = 0;
			
			ResultSet rs = conn.createStatement().executeQuery("SELECT (SMV_AMOUNT / 100) FROM CCS_SETUP_MACRO_VARS WHERE STG_ID = "+idStageA+" AND SMV_COD = 'ERA'");
			if (rs.next()) exchangeStageA = rs.getFloat(1);
			
			rs = conn.createStatement().executeQuery("SELECT (SMV_AMOUNT / 100) FROM CCS_SETUP_MACRO_VARS WHERE STG_ID = "+idStageB+" AND SMV_COD = 'ERA'");
			if (rs.next()) exchangeStageB = rs.getFloat(1);
			
			
			rs = conn.createStatement().executeQuery("SELECT (SMV_AMOUNT / 100) FROM CCS_SETUP_MACRO_VARS WHERE STG_ID = "+idStageB+" AND SMV_COD = 'INF'");
			if (rs.next()) inflationStageB = rs.getFloat(1);
			
			
			String queryExecute = "SELECT COS.UNC_COST_TYPE, COS.UNC_COST_SUBTYPE, COS.UNC_COST_NAME, SUM(COS.UNC_COST_AMOUNT * (IMU_JAN + IMU_FEB + IMU_MAR + IMU_APR + IMU_MAY + IMU_JUN + IMU_JUL + IMU_AUG + IMU_SEP + IMU_OCT + IMU_NOV + IMU_DIC)), COS.UNC_COST_COD, SUM(IMU_JAN + IMU_FEB + IMU_MAR + IMU_APR + IMU_MAY + IMU_JUN + IMU_JUL + IMU_AUG + IMU_SEP + IMU_OCT + IMU_NOV + IMU_DIC) UNITS " +
					"FROM CCS_UNITS_COST COS, CCS_IMPORT_UNITS IMP " +
					"WHERE  COS.STG_ID = "+idStageA+" " +
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
				standardDetailVO.setAmountStageAStandarDetail(rs.getFloat(4));
				standardDetailVO.setCodCostStandarDetail(rs.getString(5));
				standardDetailVO.setUnitsStageAStandarDetail(rs.getFloat(6));
				
				listStandardDetailVO.add(standardDetailVO);
			}
			
			queryExecute = "SELECT COS.UNC_COST_TYPE, COS.UNC_COST_SUBTYPE, COS.UNC_COST_NAME, SUM(COS.UNC_COST_AMOUNT * (IMU_JAN + IMU_FEB + IMU_MAR + IMU_APR + IMU_MAY + IMU_JUN + IMU_JUL + IMU_AUG + IMU_SEP + IMU_OCT + IMU_NOV + IMU_DIC)), COS.UNC_COST_COD, SUM(IMU_JAN + IMU_FEB + IMU_MAR + IMU_APR + IMU_MAY + IMU_JUN + IMU_JUL + IMU_AUG + IMU_SEP + IMU_OCT + IMU_NOV + IMU_DIC) UNITS  " +
					"FROM CCS_UNITS_COST COS, CCS_IMPORT_UNITS IMP " +
					"WHERE  COS.STG_ID = "+idStageB+" " +
					"AND    COS.STG_ID = IMP.STG_ID " +
					"AND    COS.IMU_COD_PRODUCT = IMP.IMU_COD_PRODUCT " +
					"GROUP BY COS.UNC_COST_TYPE, COS.UNC_COST_SUBTYPE, COS.UNC_COST_NAME, COS.UNC_COST_COD " +
					"ORDER BY 1,2,3";
			
			rs = conn.createStatement().executeQuery(queryExecute);
			while (rs.next()){
				
				
				for(StandardDetailVO standardDetailVO: listStandardDetailVO){
					if (standardDetailVO.getCodCostStandarDetail().equals(rs.getString(5))){
						
						standardDetailVO.setUnitsStageBStandarDetail(rs.getFloat(6));
						
						float costNewUSD = rs.getFloat(4) / standardDetailVO.getUnitsStageBStandarDetail();
						
						float amountLocalCurrencyCost = rs.getFloat(4) / standardDetailVO.getUnitsStageBStandarDetail() * exchangeStageB;
						
						float inflationCost = 0;
						
//						logger.debug("rs.getString(21): "+rs.getString(2));
						if (rs.getString(2).equals("LOCAL EXPENSES")){
							inflationCost = ((amountLocalCurrencyCost * inflationStageB * standardDetailVO.getUnitsStageBStandarDetail()) / exchangeStageB);
						}
						
						if (rs.getString(3).equals("Freight")){
							logger.debug("standardDetailVO.getUnitsStageBStandarDetail(): "+standardDetailVO.getUnitsStageBStandarDetail());
							logger.debug("amountTotal: "+rs.getFloat(4));
							logger.debug("costNewUSD: "+costNewUSD);
							logger.debug("inflationStageB: "+inflationStageB);
							logger.debug("standardDetailVO.getAmountStageBStandarDetail(): "+standardDetailVO.getAmountStageBStandarDetail());
							logger.debug("amountLocalCurrencyCost: "+amountLocalCurrencyCost);
							logger.debug("standardDetailVO.getAmountStageAStandarDetail(): "+standardDetailVO.getAmountStageAStandarDetail());
							logger.debug("standardDetailVO.getUnitsStageAStandarDetail(): "+standardDetailVO.getUnitsStageAStandarDetail());
							
							logger.debug("exchangeStageA: "+exchangeStageA);
							logger.debug("exchangeStageB: "+exchangeStageB);
							logger.debug("amountLocalCurrencyCost: "+amountLocalCurrencyCost);
						}

						

						
						
						
						
//						productivityVO.setInflationProductivity((productivityVO.getOtherStageAProductivity() * inflationStageB * productivityVO.getUnitStageBProductivity()) / exchangeStageB); //otros costos por la inflación del stage B
//						float exchangeTotalDuties  = (productivityVO.getUnitStageBProductivity()  * productivityVO.getDutiesStageAProductivity()  / exchangeStageB) - (productivityVO.getUnitStageBProductivity() * productivityVO.getDutiesStageAProductivity() / exchangeStageA);
//						
						
						
						
						
						
						
						
						float exchangeCost  = (standardDetailVO.getAmountStageAStandarDetail()  / exchangeStageB) - (standardDetailVO.getAmountStageAStandarDetail() / exchangeStageA);
						
						float totalProductivityCost = standardDetailVO.getUnitsStageBStandarDetail() * (costNewUSD - (standardDetailVO.getAmountStageAStandarDetail() / standardDetailVO.getUnitsStageAStandarDetail())) - exchangeCost - inflationCost;
						
						
//						productivityVO.setTotalDutiesProductivity((productivityVO.getUnitStageBProductivity() * ((dutiesNewUSD) - (productivityVO.getDutiesStageAProductivity() / exchangeStageA))) - exchangeTotalDuties);
						
						
						
						if (rs.getString(3).equals("Freight")){
							logger.debug("inflationCost: "+inflationCost);
							logger.debug("exchangeCost: "+exchangeCost);
							logger.debug("totalProductivityCost: "+totalProductivityCost);
						}
						
						standardDetailVO.setAmountStageBStandarDetail(totalProductivityCost);
						
						standardDetailVO.setNominalAmoutStandarDetail(standardDetailVO.getAmountStageBStandarDetail() - standardDetailVO.getAmountStageAStandarDetail());
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
		return listStandardDetailVO;
	}
	
	
	public void updateStageToApproveForecast(String idStage) throws DAOException{
		
		Connection conn = null;
		try {
			conn = dataConnection.getConnectionOracleMSD();
			
			
			String queryExecute = "UPDATE CCS_STAGE SET STS_ID = 9, STG_DATE_LAST_UPDATE = SYSDATE WHERE STG_ID = "+idStage+"";
			logger.debug("queryExecute: "+queryExecute);
			
			conn.createStatement().executeUpdate(queryExecute);
			
		}catch (SQLException e) {
			
			try {
				conn.rollback();
			} catch (SQLException e1) {
				throw new DAOException(ConstantsFinalObject.EXCEPTION_DAO_GENERIC_ERR_COD, e);
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
	
	
	public void updateStageRejectForecast(String idStage) throws DAOException{
		
		Connection conn = null;
		try {
			conn = dataConnection.getConnectionOracleMSD();
			
			
			String queryExecute = "UPDATE CCS_STAGE SET STS_ID = 4, STG_DATE_LAST_UPDATE = SYSDATE, STT_ID = 2 WHERE STG_ID = "+idStage+"";
			logger.debug("queryExecute: "+queryExecute);
			
			conn.createStatement().executeUpdate(queryExecute);
			
		}catch (SQLException e) {
			
			try {
				conn.rollback();
			} catch (SQLException e1) {
				throw new DAOException(ConstantsFinalObject.EXCEPTION_DAO_GENERIC_ERR_COD, e);
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
	
	public void updateStageRejectBudget(String idStage) throws DAOException{
		
		Connection conn = null;
		try {
			conn = dataConnection.getConnectionOracleMSD();
			
			
			String queryExecute = "UPDATE CCS_STAGE SET STS_ID = 7, STG_DATE_LAST_UPDATE = SYSDATE, STT_ID = 3 WHERE STG_ID = "+idStage+"";
			logger.debug("queryExecute: "+queryExecute);
			
			conn.createStatement().executeUpdate(queryExecute);
			
		}catch (SQLException e) {
			
			try {
				conn.rollback();
			} catch (SQLException e1) {
				throw new DAOException(ConstantsFinalObject.EXCEPTION_DAO_GENERIC_ERR_COD, e);
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
	
	
	public void updateStageApproveForecast(String idStage) throws DAOException{
		
		Connection conn = null;
		try {
			conn = dataConnection.getConnectionOracleMSD();
			
			conn.setAutoCommit(false);
			
			
			String queryExecute = "SELECT SIT_ID, STG_PERIOD FROM CCS_STAGE WHERE STG_ID = "+idStage+"";
			ResultSet rs = conn.createStatement().executeQuery(queryExecute);
			
			String idSiteStage = "0";
			String periodStage = "0";
			if(rs.next()){
				idSiteStage = rs.getString(1);
				periodStage = rs.getString(2);
			}
				
			

			
			
			String idHeaderLoading = "0";
			rs = conn.createStatement().executeQuery("SELECT IPH_ID FROM CCS_IMPORT_BASE_HEAD WHERE IPH_TYPE = 'F' AND IPH_PERIOD = '"+periodStage+"' AND SIT_ID = "+idSiteStage+"");
			if(rs.next()){
				idHeaderLoading = rs.getString(1);
			}else{
				rs = conn.createStatement().executeQuery("SELECT IPH_ID FROM CCS_IMPORT_BASE_HEAD WHERE IPH_TYPE = 'P' AND IPH_PERIOD = '"+periodStage+"' AND SIT_ID = "+idSiteStage+"");
				if(rs.next()){
					idHeaderLoading = rs.getString(1);
				}
			}
			
			
			queryExecute = "DELETE CCS_IMPORT_BASE_DETAIL WHERE IPH_ID =  "+idHeaderLoading+" ";
			conn.createStatement().executeUpdate(queryExecute);
			
			
			queryExecute = "INSERT INTO CCS_IMPORT_BASE_DETAIL (" +
					"IMD_HUB_COD_PRODUCT, " +
					"IPH_ID, " +
					"IMD_JAN, " +
					"IMD_FEB, " +
					"IMD_MAR, " +
					"IMD_APR, " +
					"IMD_MAY, " +
					"IMD_JUN, " +
					"IMD_JUL, " +
					"IMD_AUG, " +
					"IMD_SEP, " +
					"IMD_OCT, " +
					"IMD_NOV, " +
					"IMD_DEC " +
					")(SELECT " +
					"IMU_HUB_COD_PRODUCT, " +
					""+idHeaderLoading+", " +
					"IMU_JAN, " +
					"IMU_FEB, " +
					"IMU_MAR, " +
					"IMU_APR, " +
					"IMU_MAY, " +
					"IMU_JUN, " +
					"IMU_JUL, " +
					"IMU_AUG, " +
					"IMU_SEP, " +
					"IMU_OCT, " +
					"IMU_NOV, " +
					"IMU_DIC " +
					"FROM CCS_IMPORT_UNITS WHERE STG_ID = "+idStage+")";
			logger.debug("queryExecute: "+queryExecute);
			
			
			conn.createStatement().executeUpdate(queryExecute);
			
			
			queryExecute = "UPDATE CCS_STAGE SET STS_ID = 5, STG_DATE_LAST_UPDATE = SYSDATE WHERE STG_ID = "+idStage+"";
			logger.debug("queryExecute: "+queryExecute);
			
			conn.createStatement().executeUpdate(queryExecute);
			
			//llamada a nuevo procedimiento
			uploadItemMasterJDE(conn,idStage, idSiteStage);
			
			
			conn.commit();
		}catch (SQLException e) {
			
			try {
				conn.rollback();
			} catch (SQLException e1) {
				throw new DAOException(ConstantsFinalObject.EXCEPTION_DAO_GENERIC_ERR_COD, e);
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
	
	public void updateStageToApproveBudget(String idStage) throws DAOException{
		
		Connection conn = null;
		try {
			conn = dataConnection.getConnectionOracleMSD();
			
				
			String queryExecute = "UPDATE CCS_STAGE SET STS_ID =10, STG_DATE_LAST_UPDATE = SYSDATE WHERE STG_ID = "+idStage+"";
			logger.debug("queryExecute: "+queryExecute);
			conn.createStatement().executeUpdate(queryExecute);

		}catch (SQLException e) {
			
			try {
				conn.rollback();
			} catch (SQLException e1) {
				throw new DAOException(ConstantsFinalObject.EXCEPTION_DAO_GENERIC_ERR_COD, e);
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
	
	
	public void updateStageApproveBudget(String idStage) throws DAOException{
		
		Connection conn = null;
		try {
			conn = dataConnection.getConnectionOracleMSD();
			
			conn.setAutoCommit(false);
			
			
			String queryExecute = "SELECT SIT_ID, STG_PERIOD FROM CCS_STAGE WHERE STG_ID = "+idStage+"";
			ResultSet rs = conn.createStatement().executeQuery(queryExecute);
			
			String idSiteStage = "0";
			int    periodStage = 0;
			if(rs.next()){
				idSiteStage = rs.getString(1);
				periodStage = rs.getInt(2);
			}
			
			if(true){
//			if (periodStage + 1 == Integer.parseInt(FormatUtilities.getCurrentDateYear()) && FormatUtilities.getCurrentDateMonth().equals("1")){
				
				queryExecute = "SELECT IPH_ID FROM CCS_IMPORT_BASE_HEAD WHERE IPH_TYPE = 'P' AND IPH_PERIOD = '"+ (periodStage) +"' AND SIT_ID = "+idSiteStage+"";
				logger.debug("queryExecute: "+queryExecute);
				rs = conn.createStatement().executeQuery(queryExecute);
				
				String idHeaderLoading = "0";
				if(rs.next()){
					idHeaderLoading = rs.getString(1);
				}
				
				queryExecute = "DELETE CCS_IMPORT_BASE_DETAIL WHERE IPH_ID =  "+idHeaderLoading+" ";
				
				logger.debug("queryExecute: "+queryExecute);
				conn.createStatement().executeUpdate(queryExecute);
				
				
				queryExecute = "INSERT INTO CCS_IMPORT_BASE_DETAIL (" +
						"IMD_HUB_COD_PRODUCT, " +
						"IPH_ID, " +
						"IMD_JAN, " +
						"IMD_FEB, " +
						"IMD_MAR, " +
						"IMD_APR, " +
						"IMD_MAY, " +
						"IMD_JUN, " +
						"IMD_JUL, " +
						"IMD_AUG, " +
						"IMD_SEP, " +
						"IMD_OCT, " +
						"IMD_NOV, " +
						"IMD_DEC " +
						")(SELECT " +
						"IMU_HUB_COD_PRODUCT, " +
						""+idHeaderLoading+", " +
						"IMU_JAN, " +
						"IMU_FEB, " +
						"IMU_MAR, " +
						"IMU_APR, " +
						"IMU_MAY, " +
						"IMU_JUN, " +
						"IMU_JUL, " +
						"IMU_AUG, " +
						"IMU_SEP, " +
						"IMU_OCT, " +
						"IMU_NOV, " +
						"IMU_DIC " +
						"FROM CCS_IMPORT_UNITS WHERE STG_ID = "+idStage+")";
				logger.debug("queryExecute: "+queryExecute);
				
				
				conn.createStatement().executeUpdate(queryExecute);
				
				
				queryExecute = "UPDATE CCS_STAGE SET STS_ID = 8, STG_DATE_LAST_UPDATE = SYSDATE, STG_PERIOD = "+(periodStage)+"  WHERE STG_ID = "+idStage+"";
				logger.debug("queryExecute: "+queryExecute);
				conn.createStatement().executeUpdate(queryExecute);

				
				queryExecute = "update CCS_IMPORT_BASE_HEAD SET IPH_PERIOD = "+(periodStage)+", IPH_DATE_UPLOAD = SYSDATE WHERE IPH_ID = "+idHeaderLoading+" ";
				logger.debug("queryExecute: "+queryExecute);
				conn.createStatement().executeUpdate(queryExecute);
				
				
				
				//Ejecutamos para cargar los productos nuevos
				uploadItemMasterJDE(conn,idStage, idSiteStage);
				
				conn.commit();
			}else{
				throw new DAOException(ConstantsFinalObject.E_DAO_PERIOD_BUDGET);
			}

		}catch (SQLException e) {
			
			try {
				conn.rollback();
			} catch (SQLException e1) {
				throw new DAOException(ConstantsFinalObject.EXCEPTION_DAO_GENERIC_ERR_COD, e);
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
	
	
	public void updateStageForecast(String idStage) throws DAOException{
		
		Connection conn = null;
		try {
			conn = dataConnection.getConnectionOracleMSD();
			
			
			String queryExecute = "UPDATE CCS_STAGE SET STS_ID = 4, STT_ID = 2, STG_DATE_LAST_UPDATE = SYSDATE WHERE STG_ID = "+idStage+"";
			
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
	
	
	public void updateStageBudget(String idStage) throws DAOException{
		
		Connection conn = null;
		try {
			conn = dataConnection.getConnectionOracleMSD();
			
			String queryExecute = "SELECT 1 FROM CCS_STAGE WHERE STG_PERIOD = "+FormatUtilities.getCurrentDateYear()+" AND STS_ID IN (7, 8, 10) AND SIT_ID = (SELECT SIT_ID FROM CCS_STAGE WHERE STG_ID = "+idStage+")"; 
			
			ResultSet rs = conn.createStatement().executeQuery(queryExecute);
			if (rs.next()){
				throw new DAOException(ConstantsFinalObject.E_DAO_BUDGET_EXIST);	
			}
			
			queryExecute = "UPDATE CCS_STAGE SET STS_ID = 7 STG_DATE_LAST_UPDATE = SYSDATE, STG_PERIOD = STG_PERIOD + 1 WHERE STG_ID = "+idStage+"";
			
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
	
	
	private void uploadItemMasterJDE(Connection conn, String idStage, String idSiteStage){
		
		try{
			String queryProduct = "SELECT IMU_HUB_COD_PRODUCT, IMU_DESRIPTION_PRODUCT, IMU_REPLY, IMU_AJUSTED "
					+ "FROM CCS_IMPORT_UNITS "
					+ "WHERE STG_ID=" + idStage;
			
			logger.debug("queryProduct: "+queryProduct);
	
			Statement st = conn.createStatement();
			ResultSet rsProduct = st.executeQuery(queryProduct);
			
			while (rsProduct.next()){
				queryProduct = "SELECT 1 "
							 + "FROM CCS_ITEM_MASTER_JDE "
							 + "WHERE ITJ_COD_HUB = '"+rsProduct.getString(1)+"'";
				
				Statement stJDE = conn.createStatement();
				ResultSet rsItemJde = stJDE.executeQuery(queryProduct);
				if (rsItemJde.next()){
					queryProduct = "UPDATE CCS_ITEM_MASTER_JDE "
								 + "SET ITJ_DESCRIPTION_PRODUCT = '"+rsProduct.getString(2)+"', "
								 + "ITJ_AJUSTED_VALUE           = '"+rsProduct.getString(4)+"', "
								 + "ITJ_REPLY		            = '"+rsProduct.getString(3)+"' "
								 + "WHERE ITJ_COD_HUB           = '"+rsProduct.getString(1)+"' "
								 + "AND SIT_ID                  = "+idSiteStage;
					
				}else{
					queryProduct = "INSERT INTO CCS_ITEM_MASTER_JDE(ITJ_COD_HUB, ITJ_ID_PRODUCT, ITJ_DESCRIPTION_PRODUCT, SIT_ID, ITJ_REPLY, ITJ_AJUSTED_VALUE) "
								 + "VALUES  ('"+rsProduct.getString(1)+"', '"+rsProduct.getString(1)+"', '"+rsProduct.getString(2)+"', "+idSiteStage+", "+rsProduct.getString(3)+", "+rsProduct.getString(4)+")";
					
				}
				
				logger.debug("queryProduct: "+queryProduct);
				
				Statement stExecuteJDE = conn.createStatement(); 
				stExecuteJDE.executeUpdate(queryProduct);
				
				stExecuteJDE.close();
				stJDE.close();
			}

			rsProduct.close();
			st.close();

		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	
	public void insertCommentStage(String idStage, String isidUser, String comment) throws DAOException{
		
		Connection conn = null;
		try {
			conn = dataConnection.getConnectionOracleMSD();
			
			String queryExecute = "INSERT INTO CCS_STAGE_COMMENT (STC_ID, USR_ISID, STC_COMMENT, STC_REGISTER_DATE, STG_ID) VALUES(CCS_SEQ_CCSSTAGE_COMMENT.NEXTVAL, '"+isidUser+"', '"+comment+"', SYSDATE, "+idStage+")"; 
			
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
	
	
	public List<CommentStageVO> listCommentStage(String idStage) throws DAOException{
		
		Connection conn = null;
		
		List<CommentStageVO> listCommentStageVO = new ArrayList<CommentStageVO>();
		try {
			conn = dataConnection.getConnectionOracleMSD();
			
			String queryExecute = "SELECT STC_ID, USR_ISID, STC_COMMENT, to_char(STC_REGISTER_DATE, 'DD/MM/YYYY HH24:MI:SS') FROM CCS_STAGE_COMMENT WHERE STG_ID = "+idStage+" ORDER BY STC_ID DESC";
			
			logger.debug("queryExecute:_ "+queryExecute);
			
			ResultSet rs = conn.createStatement().executeQuery(queryExecute);
			
			while (rs.next()){
				
				CommentStageVO commentStageVO = new CommentStageVO();
				commentStageVO.setIdCommentStage(rs.getString(1));
				commentStageVO.setIsidCommentStage(rs.getString(2));
				commentStageVO.setLabelCommentStage(rs.getString(3));
				commentStageVO.setDateRegisterCommentStage(rs.getString(4));
				
				listCommentStageVO.add(commentStageVO);
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
		
		return listCommentStageVO;
	}
	
	
	
	public List<UserVO> listApproverUsersEmails(String idStage) throws DAOException{
		
		Connection conn = null;
		
		List<UserVO> listUserVO = new ArrayList<UserVO>();
		try {
			conn = dataConnection.getConnectionOracleMSD();
			
			String queryExecute = "SELECT USR.USR_EMAIL, USR.USR_ISID " +
					"FROM CCS_USERS USR, CCS_SITES_USER SUS " +
					"WHERE USR.PER_ID     = 2 " +
					"AND   SUS.SIT_ID     = (select SIT_ID from CCS_STAGE WHERE STG_ID = "+idStage+") " +
					"AND   USR.USR_STATUS = 1 " +
					"AND   USR.USR_ISID   = SUS.USR_ISID";
			
			logger.debug("queryExecute:_ "+queryExecute);
			
			ResultSet rs = conn.createStatement().executeQuery(queryExecute);
			while (rs.next()){
				UserVO userVO = new UserVO();
				
				userVO.setEmailUser(rs.getString(1));
				userVO.setIsidUser(rs.getString(2));
				
				listUserVO.add(userVO);
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
		
		return listUserVO;
	}	
	
	public List<UserVO> listNormalUsersEmails(String idStage) throws DAOException{
		
		Connection conn = null;
		
		List<UserVO> listUserVO = new ArrayList<UserVO>();
		try {
			conn = dataConnection.getConnectionOracleMSD();
			
			String queryExecute = "SELECT USR.USR_EMAIL, USR.USR_ISID " +
					"FROM CCS_USERS USR, CCS_SITES_USER SUS " +
					"WHERE USR.PER_ID     = 3 " +
					"AND   SUS.SIT_ID     = (select SIT_ID from CCS_STAGE WHERE STG_ID = "+idStage+") " +
					"AND   USR.USR_STATUS = 1 " +
					"AND   USR.USR_ISID   = SUS.USR_ISID";
			
			logger.debug("queryExecute:_ "+queryExecute);
			
			ResultSet rs = conn.createStatement().executeQuery(queryExecute);
			while (rs.next()){
				UserVO userVO = new UserVO();
				
				userVO.setEmailUser(rs.getString(1));
				userVO.setIsidUser(rs.getString(2));
				
				listUserVO.add(userVO);
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
		
		return listUserVO;
	}
	
	
	public List<UnitCostProductVO> initUnitCostProduct(String idStage) throws DAOException{
		
//		localTransport,temporalWarehousing,ivaImports,presentation,busines,shipmentType,itemClass,transpMode,hubCode,localCode,productDescription,fob,duties,freight,other					
		Connection conn = null;
		
		List<UnitCostProductVO> listUnitCostProductVO = new ArrayList<UnitCostProductVO>();		
		try {
			conn = dataConnection.getConnectionOracleMSD();
									
			float exchangeStageA = 0;
			
			
			ResultSet rs = conn.createStatement().executeQuery("SELECT (SMV_AMOUNT / 100) FROM CCS_SETUP_MACRO_VARS WHERE STG_ID = "+idStage+" AND SMV_COD = 'ERA'");
			if (rs.next()) exchangeStageA = rs.getFloat(1);
			
			String queryExecute = "SELECT SLR.SLR_NAME, SSR.SSR_NAME, LIM.LIM_IS_IVA, PRP.PRP_NAME, PRT.PRT_NAME, PTL.PTL_NAME, TRA.TRA_NAME, IMU.IMU_HUB_COD_PRODUCT, VCP.IMU_COD_PRODUCT, VCP.IMU_DESRIPTION_PRODUCT,  VCP.FOB, VCP.DUTIES, VCP.FREIGHT, VCP.OTHER, VCP.UNITS "
								+ "FROM   CCS_VIEW_COST_PRODUCT VCP, CCS_LOCAL_ITEM_MASTER LIM, CCS_SETUP_LOCAL_ROUTE SLR, CCS_SETUP_STORAGE_RATE SSR, CCS_PRODUCT_PRESENTATION PRP, CCS_PRODUCT_TRADE PRT, ccs_site_source CSS, CCS_TRANSPORT TRA, CCS_IMPORT_UNITS IMU, CCS_PRODUCT_TYPE_LOAD PTL "
								+ "WHERE  VCP.STG_ID = "+idStage+""
								+ "AND    VCP.IMU_COD_PRODUCT = LIM.IMU_COD_PRODUCT "
								+ "AND    VCP.STG_ID = LIM.STG_ID "
								+ "AND    LIM.SLR_ID = SLR.SLR_ID "
								+ "AND    SSR.SSR_ID = LIM.SSR_ID "
								+ "AND    PRP.PRP_ID = LIM.PRP_ID "
								+ "AND    PRT.PTR_ID = LIM.PTR_ID "
								+ "AND    CSS.SIS_ID = LIM.SIS_ID "
								+ "AND    TRA.TRA_ID = CSS.TRA_ID "
								+ "AND    IMU.IMU_COD_PRODUCT = LIM.IMU_COD_PRODUCT "
								+ "AND    IMU.STG_ID = LIM.STG_ID "
								+ "AND    PTL.PTL_ID = LIM.PTL_ID ";
			
			logger.debug("queryExecute:_ "+queryExecute);
			
		    rs = conn.createStatement().executeQuery(queryExecute);
			while (rs.next()){
				
				UnitCostProductVO unitCostProductVO = new UnitCostProductVO();
				
				unitCostProductVO.setLocalTransportUnitCostProduct(rs.getString(1));
				unitCostProductVO.setTemporalWarehousingUnitCostProduct(rs.getString(2));
				unitCostProductVO.setIvaImportsUnitCostProduct(rs.getString(3));
				unitCostProductVO.setPresentationUnitCostProduct(rs.getString(4));
				unitCostProductVO.setBusinesUnitCostProduct(rs.getString(5));
				unitCostProductVO.setShipmentTypeUnitCostProduct(rs.getString(6));
//				unitCostProductVO.setItemClassUnitCostProduct(rs.getString(7));
				unitCostProductVO.setTranspModeUnitCostProduct(rs.getString(7));
				unitCostProductVO.setHubCodeUnitCostProduct(rs.getString(8));
				unitCostProductVO.setLocalCodeUnitCostProduct(rs.getString(9));
				unitCostProductVO.setProductDescriptionUnitCostProduct(rs.getString(10));
				
				//Dolar																
				if(rs.getFloat(15) != 0)
					unitCostProductVO.setFobUnitCostProduct(rs.getFloat(11) / rs.getFloat(15));
					
				if(rs.getFloat(15) != 0)
					unitCostProductVO.setDutiesUnitCostProduct(rs.getFloat(12) / rs.getFloat(15));

				if(rs.getFloat(15) != 0)
					unitCostProductVO.setFreightUnitCostProduct(rs.getFloat(13) / rs.getFloat(15));
				
				if(rs.getFloat(15) != 0)
					unitCostProductVO.setOtherUnitCostProduct(rs.getFloat(14) / rs.getFloat(15));
				
//				unitCostProductVO.setFobUnitCostProduct(rs.getFloat(11) / rs.getFloat(15));
//				unitCostProductVO.setDutiesUnitCostProduct(rs.getFloat(12) / rs.getFloat(15));
//				unitCostProductVO.setFreightUnitCostProduct(rs.getFloat(13) / rs.getFloat(15));
//				unitCostProductVO.setOtherUnitCostProduct(rs.getFloat(14) / rs.getFloat(15));
//				
				//Modeda local
												
				
				if(exchangeStageA == 0 || rs.getFloat(15) == 0)
					unitCostProductVO.setFobUnitCostLCProduct(0);
				else 
					unitCostProductVO.setFobUnitCostLCProduct(rs.getFloat(11) / exchangeStageA / rs.getFloat(15));
				
				
				if(exchangeStageA == 0 || rs.getFloat(15) == 0)
					unitCostProductVO.setDutiesUnitCostLCProduct(0);
				else
					unitCostProductVO.setDutiesUnitCostLCProduct(rs.getFloat(12) / exchangeStageA / rs.getFloat(15));
				
				if(exchangeStageA == 0 || rs.getFloat(15) == 0)
					unitCostProductVO.setFreightUnitCostLCProduct(0);
				else 
					unitCostProductVO.setFreightUnitCostLCProduct(rs.getFloat(13) / exchangeStageA / rs.getFloat(15));
				
				
				if(exchangeStageA == 0 || rs.getFloat(15) == 0)
					unitCostProductVO.setOtherUnitCostLCProduct(0);
				else 
					unitCostProductVO.setOtherUnitCostLCProduct(rs.getFloat(14) / exchangeStageA / rs.getFloat(15));
				
//				unitCostProductVO.setFobUnitCostLCProduct(rs.getFloat(11) / exchangeStageA);
//				unitCostProductVO.setDutiesUnitCostLCProduct(rs.getFloat(12) / exchangeStageA);
//				unitCostProductVO.setFreightUnitCostLCProduct(rs.getFloat(13) / exchangeStageA);
//				unitCostProductVO.setOtherUnitCostLCProduct(rs.getFloat(14) / exchangeStageA);				
				
				listUnitCostProductVO.add(unitCostProductVO);
								
			}
		} catch (SQLException e) {
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
		
		return listUnitCostProductVO;		
		
	}
	
	
}