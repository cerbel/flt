package cl.cstit.msd.ccs.dao;

import java.math.BigDecimal;
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
import cl.cstit.msd.ccs.vo.ActivePackOutFreightVO;
import cl.cstit.msd.ccs.vo.ConceptCostAPOFreightVO;
import cl.cstit.msd.ccs.vo.ConceptCostOtherFreightVO;
import cl.cstit.msd.ccs.vo.CustodyVO;
import cl.cstit.msd.ccs.vo.FreighAirRangeVO;
import cl.cstit.msd.ccs.vo.FreightAirVO;
import cl.cstit.msd.ccs.vo.GeneralComboVO;
import cl.cstit.msd.ccs.vo.ImportUnitVO;
import cl.cstit.msd.ccs.vo.ItemMasterProductVO;
import cl.cstit.msd.ccs.vo.LocalTransportVO;
import cl.cstit.msd.ccs.vo.OpeManageVO;
import cl.cstit.msd.ccs.vo.OtherFreightVO;
import cl.cstit.msd.ccs.vo.RangeGenericVO;
import cl.cstit.msd.ccs.vo.RangeLocalTransportVO;
import cl.cstit.msd.ccs.vo.RangeWarehouseVO;
import cl.cstit.msd.ccs.vo.RateWarehouseVO;
import cl.cstit.msd.ccs.vo.RouteLocalTransportVO;
import cl.cstit.msd.ccs.vo.SetupCustomDutiesDetailVO;
import cl.cstit.msd.ccs.vo.SetupCustomDutiesHeadVO;
import cl.cstit.msd.ccs.vo.SetupExchangeVO;
import cl.cstit.msd.ccs.vo.SetupMacroValVO;
import cl.cstit.msd.ccs.vo.SetupOpConsolidationVO;
import cl.cstit.msd.ccs.vo.StageVO;
import cl.cstit.msd.ccs.vo.TempStorageVO;

public class MoveCostDAOImp{
	
	private ConnectionFactory dataConnection = null;
	private static Logger logger = null;
	
	public MoveCostDAOImp(){
		dataConnection = ConnectionFactory.getInstance();	
		logger = Logger.getLogger(MoveCostDAOImp.class);
	}
	
	public String initSimulation(StageVO stageVO) throws DAOException{
		Connection conn = null;
		
		String idStage = "";
		try {
    		conn = dataConnection.getConnectionOracleMSD();
    		conn.setAutoCommit(false);
			
			idStage =  initSimulation(stageVO, conn);
			
			conn.commit();
		} catch (SQLException e) {
			try {
				e.printStackTrace();
				conn.rollback();
			} catch (SQLException e1) {
				throw new DAOException(ConstantsFinalObject.EXCEPTION_DAO_GENERIC_ERR_COD, e);
			}
			throw new DAOException(ConstantsFinalObject.EXCEPTION_DAO_GENERIC_ERR_COD, e);
		}
		
		return idStage;
	}
	
    public String initSimulation(StageVO stageVO, Connection conn) throws DAOException, SQLException{
    	
//    	Connection conn = null;
    	String idStage="";
    		
//    		conn = dataConnection.getConnectionOracleMSD();
//    		
//    		conn.setAutoCommit(false);
		
		String qSelectIdStage = "SELECT CCS_SEQ_CSS_STAGE.NEXTVAL FROM DUAL";
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(qSelectIdStage);

		if (rs.next())
			idStage = rs.getString(1);
			
		rs.close();
		st.close();
		
		//Insertanto tabla principal 
		String queryExecute = 	"INSERT INTO ccs_stage (STG_ID, SGT_NAME, STG_DATE_CREATION, STG_DATE_LAST_UPDATE, STT_ID, STS_ID, SIT_ID, USR_ISID, STG_DESCRIPTION, STG_PERIOD) values("+idStage+",'"+
								stageVO.getNameStage()+"',SYSDATE, NULL, "+stageVO.getIdTypeStage()+", "+stageVO.getIdStatusStage()+","+stageVO.getIdSiteStage()+", '"+stageVO.getIsidUserStage()+"', '"+stageVO.getDescriptionStage()+"', '"+FormatUtilities.getCurrentDateYear()+"')";
		logger.debug("queryExecute: "+queryExecute);
		
		st = conn.createStatement();
		st.executeUpdate(queryExecute);
		st.close();

		String planForecast = "";
		String qeValImporUnit = "SELECT 1 FROM CCS_IMPORT_BASE_DETAIL DET, CCS_IMPORT_BASE_HEAD HED " +
				"WHERE HED.IPH_ID = DET.IPH_ID " +
				"AND   HED.IPH_TYPE = 'F' " +
				"AND   HED.IPH_PERIOD = '"+FormatUtilities.getCurrentDateYear()+"' " +
				"AND   HED.SIT_ID = "+stageVO.getIdSiteStage()+"";

		st = conn.createStatement();
		rs = st.executeQuery(qeValImporUnit);
		
		if (rs.next()){
			planForecast = "F";
		}else{
			qeValImporUnit = "SELECT 1 FROM CCS_IMPORT_BASE_DETAIL DET, CCS_IMPORT_BASE_HEAD HED " +
					"WHERE HED.IPH_ID = DET.IPH_ID " +
					"AND   HED.IPH_TYPE = 'P' " +
					"AND   HED.IPH_PERIOD = '"+FormatUtilities.getCurrentDateYear()+"' " +
					"AND   HED.SIT_ID = "+stageVO.getIdSiteStage()+"";

			Statement stDetail = conn.createStatement();
			ResultSet rsDetail = stDetail.executeQuery(qeValImporUnit);
//			st.close();
			if (rsDetail.next()){
				planForecast = "P";
			}
			rsDetail.close();
			stDetail.close();
		}
		rs.close();
		st.close();
		
//			String planForecast = "";
//			rs = conn.createStatement().executeQuery("SELECT STG_ID FROM CCS_STAGE WHERE SIT_ID = "+stageVO.getIdSiteStage()+" AND STS_ID = 5 AND STG_PERIOD = "+FormatUtilities.getCurrentDateYear());
//			if (rs.next()){
//				planForecast = rs.getString(1);
//			}else{
//				rs = conn.createStatement().executeQuery("SELECT STG_ID FROM CCS_STAGE WHERE SIT_ID = "+stageVO.getIdSiteStage()+" AND STS_ID = 8 AND STG_PERIOD = "+FormatUtilities.getCurrentDateYear());
//				if (rs.next()){
//					planForecast = rs.getString(1);
//				}
//			}
		
		
		
		String idLastStage = "0";
		
		String qeSelectLastStage = "SELECT STG_ID FROM CCS_STAGE WHERE STG_ID IN " +
										"(SELECT MAX(STG.STG_ID) FROM CCS_STAGE STG, CCS_IMPORT_UNITS IMP WHERE STG.STS_ID = 5 " +
										"AND STG.SIT_ID = "+stageVO.getIdSiteStage()+" " +
										"AND STG.STG_PERIOD = "+FormatUtilities.getCurrentDateYear()+" " +
										"AND STG.STG_ID = IMP.STG_ID) " ; //Forecast Aprobado
		
		
		st = conn.createStatement();
		rs = st.executeQuery(qeSelectLastStage);
		if (rs.next())
			idLastStage = rs.getString(1); 
		else{
//			qeSelectLastStage = "SELECT STG_ID FROM CCS_STAGE WHERE STG_ID IN (select MAX(STG_ID) FROM CCS_STAGE where  STS_ID = 8 AND SIT_ID = "+stageVO.getIdSiteStage()+" AND STG_PERIOD = "+FormatUtilities.getCurrentDateYear()+") " ; //Budget Aprobado
			
			
			qeSelectLastStage = "SELECT STG_ID FROM CCS_STAGE WHERE STG_ID IN " +
					"(SELECT MAX(STG.STG_ID) FROM CCS_STAGE STG, CCS_IMPORT_UNITS IMP WHERE STG.STS_ID = 8 " +
					"AND STG.SIT_ID = "+stageVO.getIdSiteStage()+" " +
					"AND STG.STG_PERIOD = "+FormatUtilities.getCurrentDateYear()+" " +
					"AND STG.STG_ID = IMP.STG_ID) " ; //Budget Aprobado
			
			
			logger.debug("qeSelectLastStage: "+qeSelectLastStage);
			Statement stStage = conn.createStatement();
			ResultSet rsStage = stStage.executeQuery(qeSelectLastStage);
			if (rsStage.next())
				idLastStage = rsStage.getString(1); 
		}
		rs.close();
		st.close();
		
		
		//En caso de no se encuentra un stage base en el paï¿½s y perï¿½odo, tomarï¿½ cualquier stage como base, sin importar el site ni el periodo.
		if (idLastStage.equals("0")){
			
			qeSelectLastStage = "SELECT STG_ID FROM CCS_STAGE WHERE STG_ID IN (select MAX(STG_ID) FROM CCS_STAGE where STS_ID = 5 ) "; //Forecast Aprobado
			st = conn.createStatement();
			rs = st.executeQuery(qeSelectLastStage);
			if (rs.next())
				idLastStage = rs.getString(1); 
			else{
				qeSelectLastStage = "SELECT STG_ID FROM CCS_STAGE WHERE STG_ID IN (select MAX(STG_ID) FROM CCS_STAGE where  STS_ID = 8) " ; //Budget Aprobado
				
				logger.debug("qeSelectLastStage: "+qeSelectLastStage);
				Statement stStage = conn.createStatement();
				ResultSet rsStage = stStage.executeQuery(qeSelectLastStage);
				if (rsStage.next())
					idLastStage = rsStage.getString(1); 
				
				rsStage.close();
				stStage.close();
				
			}
			rs.close();
			st.close();
		}
		
		
		
		//Insertando productos tablas de import plan TEMPORAL
		String qeInsertImportUnit = "INSERT "+
		"INTO CCS_IMPORT_UNITS (IMU_JAN, IMU_FEB, IMU_MAR, IMU_APR, IMU_MAY, IMU_JUN, IMU_JUL, IMU_AUG, IMU_SEP, IMU_OCT, IMU_NOV, IMU_DIC, IMU_AJUSTED, IMU_HUB_COD_PRODUCT, IMU_COD_PRODUCT, IMU_DESRIPTION_PRODUCT, STG_ID, IMU_STATE, IMU_REPLY) "+
		"SELECT DET.IMD_JAN, DET.IMD_FEB, DET.IMD_MAR, DET.IMD_APR, DET.IMD_MAY, DET.IMD_JUN, DET.IMD_JUL, DET.IMD_AUG, DET.IMD_SEP, DET.IMD_OCT, DET.IMD_NOV, DET.IMD_DEC,  ITJ_AJUSTED_VALUE, DET.IMD_HUB_COD_PRODUCT, ITJ_ID_PRODUCT, ITJ_DESCRIPTION_PRODUCT,  "+idStage+",  1, IMJ.ITJ_REPLY " +
		"FROM CCS_IMPORT_BASE_DETAIL DET, CCS_IMPORT_BASE_HEAD HED, CCS_ITEM_MASTER_JDE IMJ " +
		"WHERE HED.IPH_ID = DET.IPH_ID " +
		"AND   IMJ.ITJ_COD_HUB = DET.IMD_HUB_COD_PRODUCT "+
		"AND   IMJ.SIT_ID = HED.SIT_ID "+
		"AND   HED.IPH_TYPE = '"+planForecast+"' " +
		"AND   HED.SIT_ID = "+stageVO.getIdSiteStage()+"";
		
		
		
		
		
//			String qeInsertImportUnit = "INSERT INTO CCS_IMPORT_UNITS(IMU_JAN, IMU_FEB, IMU_MAR, IMU_APR, IMU_MAY, IMU_JUN, IMU_JUL, IMU_AUG, IMU_SEP, IMU_OCT, IMU_NOV, IMU_DIC, IMU_AJUSTED, IMU_COD_PRODUCT, IMU_DESRIPTION_PRODUCT, STG_ID, IMU_STATE, IMU_HUB_COD_PRODUCT, IMU_REPLY) " +
//					"(SELECT IMU_JAN, IMU_FEB, IMU_MAR, IMU_APR, IMU_MAY, IMU_JUN, IMU_JUL, IMU_AUG, IMU_SEP, IMU_OCT, IMU_NOV, IMU_DIC, IMU_AJUSTED, IMU_COD_PRODUCT, IMU_DESRIPTION_PRODUCT, "+idStage+", IMU_STATE, IMU_HUB_COD_PRODUCT, IMU_REPLY FROM CCS_IMPORT_UNITS " +
//					"WHERE STG_ID = "+idLastStage+" )";
		
		
		logger.debug("qeInsertImportUnit: "+qeInsertImportUnit);
		
		st = conn.createStatement();
		st.executeUpdate(qeInsertImportUnit);
		st.close();
		
		

		
		//calculamos ajuste
//			updateImportUnits(conn, idStage);
		
		//Insert into local item master
//			String qeInsertItemMaster = "INSERT INTO CCS_LOCAL_ITEM_MASTER (IMU_COD_PRODUCT, STG_ID) SELECT IMU_COD_PRODUCT, STG_ID FROM CCS_IMPORT_UNITS WHERE STG_ID = "+idStage+"";
//			conn.createStatement().executeUpdate(qeInsertItemMaster);
		
//		AQUI QUEDE, DEBO CAMBIAR FORMA DE OBTENER PRODUCTOS
		String qeInsertItemMaster = "INSERT INTO CCS_LOCAL_ITEM_MASTER ( " +
				"LIM_FOB_COST, " +
				"LIM_HIGH_PALETTE, " +
				"LIM_LARGE_PALETTE, " +
				"LIM_WIDE_PALETTE, " +
				"LIM_UNIT_BY_PALETTE, " +
				"LIM_WEIGTH_PALETTE, " +
				"IMU_COD_PRODUCT, " +
				"STG_ID, " +
				"PRM_ID, " +
				"PFA_ID, " +
				"PTY_ID, " +
				"LIM_TMP_TRA_ID, " +
				"LIM_TMP_SIS_ID, " +
				"PRP_ID, " +
				"PTR_ID, " +
				"PTL_ID, " +
				"SDD_COD, " +
				"SLR_ID, " +
				"SSR_ID, " +
				"SIS_ID, " +
				"LIM_IS_IVA " +
				") " +
				"(SELECT LIM_FOB_COST, " +
				"LIM_HIGH_PALETTE, " +
				"LIM_LARGE_PALETTE, " +
				"LIM_WIDE_PALETTE, " +
				"LIM_UNIT_BY_PALETTE, " +
				"LIM_WEIGTH_PALETTE, " +
				"IMU_COD_PRODUCT, " +
				""+idStage+", " +
				"PRM_ID, " +
				"PFA_ID, " +
				"PTY_ID, " +
				"LIM_TMP_TRA_ID, " +
				"LIM_TMP_SIS_ID, " +
				"PRP_ID, " +
				"PTR_ID, " +
				"PTL_ID, " +
				"SDD_COD, " +
				"SLR_ID, " +
				"SSR_ID, " +
				"SIS_ID, " + 
				"LIM_IS_IVA " +
				"FROM CCS_LOCAL_ITEM_MASTER WHERE STG_ID = "+idLastStage+" )";
		logger.debug("qeInsertItemMaster: "+qeInsertItemMaster);
		st = conn.createStatement();
		st.executeUpdate(qeInsertItemMaster);
		st.close();
		
		//Insertando fletes y otros
		updateFreight(conn, idStage, idLastStage, stageVO.getIdSiteStage());
		logger.debug("Freight updated!");
		
		
		//Insertando Setup
		updateSetup(conn, idStage, idLastStage);
		logger.debug("Setup updated!");
		
		
		//Actualizando Item Master From JDE
//			updateItemMasterFromJDE(conn, idStage, idLastStage);
//			logger.debug("Item Master updated!");
		
	
//			conn.commit();
		
    	
    	return idStage;
    }

    
	public List<ItemMasterProductVO> initItemMaster(String idStage, String idSite) throws DAOException{
		
		Connection conn = null;
		
		List<ItemMasterProductVO> listItemMasterProductVO = new ArrayList<ItemMasterProductVO>();
		try {
			
			conn = dataConnection.getConnectionOracleMSD();
			
			
			String qeSelectProductsImportUnits = "SELECT IMP.STG_ID, IMP.IMU_COD_PRODUCT, IMP.IMU_DESRIPTION_PRODUCT, ITM.LIM_FOB_COST, ITM.LIM_HIGH_PALETTE, ITM.LIM_LARGE_PALETTE, ITM.LIM_WIDE_PALETTE, ITM.PRM_ID, ITM.PFA_ID, " +
												 "ITM.SIS_ID, ITM.PTY_ID, ITM.LIM_WEIGTH_PALETTE, ITM.LIM_UNIT_BY_PALETTE, ITM.PRP_ID, ITM.PTR_ID, ITM.PTL_ID, ITM.SDD_COD, ITM.SLR_ID, ITM.SSR_ID, IMP.IMU_REPLY, ITM.LIM_IS_IVA " +
												 "FROM   CCS_IMPORT_UNITS IMP, CCS_LOCAL_ITEM_MASTER ITM " +
												 "WHERE  IMP.STG_ID = ITM.STG_ID " +
												 "AND    IMP.IMU_COD_PRODUCT = ITM.IMU_COD_PRODUCT " +
												 "AND    IMP.IMU_STATE = 1 " +
												 "AND    IMP.STG_ID = "+idStage+" " +
												 "ORDER BY  IMP.IMU_COD_PRODUCT";
			
			logger.debug("qeSelectProductsImportUnits: " + qeSelectProductsImportUnits);
			
			ResultSet rs = conn.createStatement().executeQuery(qeSelectProductsImportUnits);
			while (rs.next()){
				
				ItemMasterProductVO itemMasterProductVO = new ItemMasterProductVO();
				
				itemMasterProductVO.setIdStage(rs.getString(1));
				itemMasterProductVO.setLocalCodeProduct(rs.getString(2));
				itemMasterProductVO.setDescriptionProduct(rs.getString(3));
				itemMasterProductVO.setValueFOBProduct(rs.getString(4));
				

				itemMasterProductVO.setHighPalette(rs.getString(5));
				itemMasterProductVO.setLargePalette(rs.getString(6));
				itemMasterProductVO.setWidePalette(rs.getString(7));
				
				itemMasterProductVO.setIdUnitMeasureProduct(rs.getString(8));
				
				itemMasterProductVO.setIdFamilyProduct(rs.getString(9));
				itemMasterProductVO.setIdSiteSource(rs.getString(10));

				
				itemMasterProductVO.setIdTypeProduct(rs.getString(11));
				itemMasterProductVO.setWeightPalette(rs.getString(12));
				itemMasterProductVO.setUnitsByPalette(rs.getString(13));
				
				itemMasterProductVO.setIdPresentationProduct(rs.getString(14));
				itemMasterProductVO.setIdTradeProduct(rs.getString(15));
				itemMasterProductVO.setIdTypeLoadProduct(rs.getString(16));
				
				itemMasterProductVO.setIdCustomDutyProduct(rs.getString(17));
				itemMasterProductVO.setIdLocalRoute(rs.getString(18));
				itemMasterProductVO.setIdRateStorage(rs.getString(19));
				
				itemMasterProductVO.setIsReplica(rs.getString(20));
				itemMasterProductVO.setIsIVA(rs.getString(21));
				
				listItemMasterProductVO.add(itemMasterProductVO);
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
		return listItemMasterProductVO;
	}
    
//	private void updateItemMasterFromJDE(Connection conn, String idStage, String idLastStage) throws DAOException{
//		
//		int idSiteStage = 0;
//		try {
//			
//			String qeSelectIdSiteStage= "SELECT SIT_ID FROM CCS_STAGE WHERE STG_ID = "+idStage+"";		
//			ResultSet rs = conn.createStatement().executeQuery(qeSelectIdSiteStage);
//			if (rs.next())
//				idSiteStage = rs.getInt(1);
//			
//			
//			String qeSelectProductsImportUnits = "SELECT IMP.STG_ID, IMP.IMU_COD_PRODUCT from CCS_IMPORT_UNITS IMP, CCS_LOCAL_ITEM_MASTER ITM " +
//					 "WHERE IMP.STG_ID = ITM.STG_ID " +
//					 "AND   IMP.IMU_COD_PRODUCT = ITM.IMU_COD_PRODUCT " +
//					 "AND   IMP.STG_ID = "+idStage+"";		
//			
//			rs = conn.createStatement().executeQuery(qeSelectProductsImportUnits);
//			while (rs.next()){
//				
//				//Select de actualizaciï¿½n con Item Master Base JDE
//				String qeSelectItemMasterJDE = "SELECT ITJ_COD_HUB, ITJ_ID_PRODUCT, ITJ_DESCRIPTION_PRODUCT, SIT_ID, ITJ_REPLY, " +
//												"ITJ_FOB_COST, ITJ_HIGH_PALETTE, ITJ_LARGE_PALETTE, ITJ_WIDE_PALETTE, PRM_ID, PFA_ID, PTY_ID, SDD_COD, PRP_ID,  PTR_ID, PTL_ID, ITJ_WEIGTH_PALETTE, TRA_ID, SIS_ID, SLR_ID, SSR_ID, ITL_IS_IVA, ITJ_UNIT_BY_PALETTE " + // 
//												"FROM  CCS_ITEM_MASTER_JDE " +
//												"WHERE ITJ_ID_PRODUCT = '"+rs.getString(2)+"' " +
//												"AND SIT_ID = "+idSiteStage+"";
//				
//				logger.debug("qeUpdateItemMaster: "+qeSelectItemMasterJDE);
//
//				Statement stmtSelectItemMasterJDE = conn.createStatement();
//				ResultSet rsSelectItemMasterJDE = stmtSelectItemMasterJDE.executeQuery(qeSelectItemMasterJDE);
//				if (rsSelectItemMasterJDE.next()){
//					
//					//Local Item Master Update
//					String qeUpdateItemMaster = "UPDATE CCS_LOCAL_ITEM_MASTER SET " +
//												"  LIM_FOB_COST        = "+rsSelectItemMasterJDE.getFloat(6)+" " +
//												", LIM_HIGH_PALETTE    = "+rsSelectItemMasterJDE.getFloat(7)+" " +
//												", LIM_LARGE_PALETTE   = "+rsSelectItemMasterJDE.getFloat(8)+" " +
//												", LIM_WIDE_PALETTE    = "+rsSelectItemMasterJDE.getFloat(9)+" " +
//												", PRM_ID              = "+rsSelectItemMasterJDE.getInt(10)+" " +
//												", PFA_ID              = "+rsSelectItemMasterJDE.getInt(11)+" " +
//												", PTY_ID              = "+rsSelectItemMasterJDE.getInt(12)+" " +
//												", SDD_COD             = '"+rsSelectItemMasterJDE.getString(13)+"' " +
//												", PRP_ID              = "+rsSelectItemMasterJDE.getInt(14)+" " +
//												", PTR_ID              = "+rsSelectItemMasterJDE.getInt(15)+" " +
//												", PTL_ID              = "+rsSelectItemMasterJDE.getInt(16)+" " +
//												", LIM_WEIGTH_PALETTE  = "+rsSelectItemMasterJDE.getFloat(17)+" " +
//												", LIM_TMP_TRA_ID      = "+rsSelectItemMasterJDE.getInt(18)+" " +
//												", SIS_ID              = "+rsSelectItemMasterJDE.getInt(19)+" " +
//												", SLR_ID              = "+rsSelectItemMasterJDE.getInt(20)+" " +
//												", SSR_ID              = "+rsSelectItemMasterJDE.getInt(21)+" " +
//												", LIM_IS_IVA          = '"+rsSelectItemMasterJDE.getString(22)+"' " +
//												", LIM_UNIT_BY_PALETTE = "+rsSelectItemMasterJDE.getInt(23)+" " +
//												"WHERE IMU_COD_PRODUCT = '"+rs.getString(2)+"' AND STG_ID = "+idStage+"";
//					
//					logger.debug("qeUpdateItemMaster: "+qeUpdateItemMaster);
//					
//					Statement stmtUpdateLIM = conn.createStatement();
//					stmtUpdateLIM.executeUpdate(qeUpdateItemMaster);
//					
//					//Insertando sobre la tabla de transportes y sitios
////					String qeInsertSiteSourceTransport = "INSERT INTO CCS_SITE_SOURCE_TRANSPORT ( " +
////							"SIS_ID, IMU_COD_PRODUCT, STG_ID, TRA_ID ) " +
////							"VALUES( " +
////							""+rsSelectItemMasterJDE.getInt(19)+", '"+rs.getString(2)+"', "+rs.getString(1)+", "+rsSelectItemMasterJDE.getInt(18)+")";		
//					
////					logger.debug("qeInsertSiteSourceTransport: "+qeInsertSiteSourceTransport);
//					
////					Statement stmtInsertSST = conn.createStatement();
////					stmtInsertSST.executeUpdate(qeInsertSiteSourceTransport);
//					
//					//Cerrando cursores
//					stmtUpdateLIM.close();
////					stmtInsertSST.close();
//					
//				}
//				
//				stmtSelectItemMasterJDE.close();
//				rsSelectItemMasterJDE.close();
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new DAOException(ConstantsFinalObject.EXCEPTION_DAO_GENERIC_ERR_COD, e);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new DAOException(ConstantsFinalObject.EXCEPTION_DAO_GENERIC_ERR_COD, e);
//		}
//	}
	
	
	private void updateFreight(Connection conn, String idStage, String idLastStage, String idSiteStage) throws DAOException, SQLException{
		
		String qeInsertSelectFreightExchanges = "INSERT INTO CCS_STAGE_FREIGHT_EXCHANGE( " +
				"SFE_ID, " +
				"SFE_NAME, " +
				"SFE_RATE, " +
				"SFE_ACRONYM, " +
				"STG_ID " +
				") " +
				"(SELECT " +
				"CCS_SEQ_SFE.NEXTVAL, " +
				"SFE_NAME, " +
				"SFE_RATE, " +
				"SFE_ACRONYM, " +
				""+idStage+" " +
				"FROM CCS_STAGE_FREIGHT_EXCHANGE WHERE STG_ID = "+idLastStage+" )";
		logger.debug("query: "+qeInsertSelectFreightExchanges);
		conn.createStatement().executeUpdate(qeInsertSelectFreightExchanges);
		
		
		
		String query = "SELECT SIS_ID, SIS_NAME, STG_ID, SIS_JDE_COD, TRA_ID, SIS_JDE_COD FROM CCS_SITE_SOURCE WHERE STG_ID = "+idLastStage+"";
		logger.debug("query: "+query);
		ResultSet rs = conn.createStatement().executeQuery(query);
		
		while (rs.next()){
			
			Statement stSencuenceSIS = conn.createStatement();
			ResultSet rsSencuenceSIS = stSencuenceSIS.executeQuery("SELECT CCS_SEQ_CCS_SITE_SOURCE.NEXTVAL FROM DUAL");
			String idNewSiteSource = rsSencuenceSIS.next()==true?rsSencuenceSIS.getString(1):"";			
			rsSencuenceSIS.close();
			stSencuenceSIS.close();
			
			//Insertando Site Source para cada ruta
			query = "INSERT INTO CCS_SITE_SOURCE (SIS_ID, SIS_NAME, TRA_ID, STG_ID, SIS_JDE_COD) VALUES("+idNewSiteSource+", '"+rs.getString(2)+"', "+rs.getString(5)+", "+idStage+", '"+rs.getString(6)+"')";
			logger.debug("query: "+query);
			Statement st = conn.createStatement();
			st.executeUpdate(query);
			st.close();
			
			
			//Transporte aereo utilizarï¿½ tablas propias independientes
			if (rs.getString(5).equals("2")){
				
				
				
				query = "SELECT FAR_ID, FAR_INCREASE, FAR_AWB_KG_RATES, FAR_FUEL_SURCHARGE, FAR_SECURITY_CHARGE, FAR_OTHER_FFW_FEES, STG_ID, SIS_ID, FAR_ORDERS, SFE_ID, FAR_INCREASE_AWB, FAR_INCREASE_FSC, FAR_INCREASE_SCC, FAR_INCREASE_FFW  FROM CCS_STAGE_FREIGHT_AIR WHERE STG_ID = "+idLastStage+" AND SIS_ID = "+rs.getString(1)+"";
				logger.debug("query: "+query);
				Statement stAirFreight = conn.createStatement();
				ResultSet rsAirFreight = stAirFreight.executeQuery(query);
				
				if (rsAirFreight.next()){
					
					Statement stSequence  = conn.createStatement();
					ResultSet rsSequence  = stSequence.executeQuery("SELECT CCS_SEQ_CCS_STAGE_FAI.NEXTVAL FROM DUAL");
					String idNewFreightAir = rsSequence.next()==true?rsSequence.getString(1):"";
					
					rsSequence.close();
					stSequence.close();
					
					query = "INSERT INTO CCS_STAGE_FREIGHT_AIR ( " +
							"FAR_ID, " +
							"FAR_INCREASE, " +
							"FAR_AWB_KG_RATES, " +
							"FAR_FUEL_SURCHARGE, " +
							"FAR_SECURITY_CHARGE, " +
							"FAR_OTHER_FFW_FEES, " +
							"STG_ID, " +
							"SIS_ID, " +
							"FAR_ORDERS, " +
							"SFE_ID, " +
							"FAR_INCREASE_AWB, " +
							"FAR_INCREASE_FSC, " +
							"FAR_INCREASE_SCC, " +
							"FAR_INCREASE_FFW " +
							") VALUES( " +
							""+idNewFreightAir+", " +
							""+rsAirFreight.getString(2)+"," +
							""+rsAirFreight.getString(3)+"," +
							""+rsAirFreight.getString(4)+"," +
							""+rsAirFreight.getString(5)+"," +
							""+rsAirFreight.getString(6)+"," +
							""+idStage+"," +
							""+idNewSiteSource+"," +
							""+rsAirFreight.getString(9)+", " +
							""+rsAirFreight.getString(10)+"," +
							""+rsAirFreight.getString(11)+"," +
							""+rsAirFreight.getString(12)+"," +
							""+rsAirFreight.getString(13)+"," +
							""+rsAirFreight.getString(14)+"" +
							")";
					logger.debug("query: "+query);
					
					Statement stInsertFreightAir = conn.createStatement();
					stInsertFreightAir.executeUpdate(query);
					stInsertFreightAir.close();
					
					query = "INSERT INTO CCS_STAGE_FREIGHT_AIR_RANGE ( " +
							"FRR_ID, " +
							"FRR_FROM, " +
							"FRR_TO, " +
							"FRR_RATE, " +
							"STG_ID, " +
							"FAR_ID, " +
							"SIS_ID) " +
							"(SELECT " +
							"CCS_SEQ_CCS_STAGE_F_AIR_RANGE.NEXTVAL, " +
							"FRR_FROM, " +
							"FRR_TO, " +
							"FRR_RATE, " +
							""+idStage+", " +
							""+idNewFreightAir+", " +
							""+idNewSiteSource+" " +
							"FROM CCS_STAGE_FREIGHT_AIR_RANGE WHERE STG_ID = "+idLastStage+" AND FAR_ID = "+rsAirFreight.getString(1)+")";
					logger.debug("query: "+query);
					
					Statement stInsertFreightAirRange = conn.createStatement();
					stInsertFreightAirRange.executeUpdate(query);
					stInsertFreightAirRange.close();
					
				}
				
				stAirFreight.close();
				rsAirFreight.close();
				
			}

			if (rs.getString(5).equals("1") || rs.getString(5).equals("3")){
				
				query = "SELECT FOT_ID, FOT_PALETTES, STG_ID, SFE_ID, TRA_ID, SIS_ID, FOT_INCREASE, FOT_ORDERS FROM CCS_STAGE_FREIGHT_OTHERS WHERE STG_ID = "+idLastStage +" AND SIS_ID = "+rs.getString(1)+" ";
				Statement stOtherFreight = conn.createStatement();
				ResultSet rsOtherFreight = stOtherFreight.executeQuery(query);
				
				if (rsOtherFreight.next()){
					
					
					Statement stSelectSequence = conn.createStatement();
					ResultSet rsSequence   = stSelectSequence.executeQuery("SELECT CCS_SEQ_FREIGHT_OTHERS.NEXTVAL FROM DUAL");
					String idNewOtherFreight = rsSequence.next()==true?rsSequence.getString(1):"";
					
					rsSequence.close();
					stSelectSequence.close();
					
					query = "INSERT INTO CCS_STAGE_FREIGHT_OTHERS( " +
							"FOT_ID, " +
							"FOT_PALETTES, " +
							"STG_ID, " +
							"SFE_ID, " +
							"TRA_ID, " +
							"SIS_ID, " +
							"FOT_INCREASE, " +
							"FOT_ORDERS " +
							") VALUES( " +
							""+idNewOtherFreight+", " +
							""+rsOtherFreight.getString(2)+", " +
							""+idStage+", " +
							""+rsOtherFreight.getString(4)+", " +
							""+rsOtherFreight.getString(5)+", " +
							""+idNewSiteSource+", " +
							""+rsOtherFreight.getString(7)+", " +
							""+rsOtherFreight.getString(8)+" )";
					logger.debug("query: "+query);
					
					Statement stInsertFreightOthers = conn.createStatement();
					stInsertFreightOthers.executeUpdate(query);
					stInsertFreightOthers.close();
					
					query = "INSERT INTO CCS_STAGE_FREIGHT_OTHERS_CSP( " +
							"OCP_ID, " +
							"OCP_NAME, " +
							"OCP_RATE, " +
							"FOT_ID, " +
							"STG_ID " +
							") " +
							"(SELECT " +
							"CCS_SEQ_OCP.NEXTVAL, " +
							"OCP_NAME, " +
							"OCP_RATE, " +
							""+idNewOtherFreight+", " +
							""+idStage+" " +
							"FROM CCS_STAGE_FREIGHT_OTHERS_CSP WHERE STG_ID = "+idLastStage+" AND FOT_ID = "+rsOtherFreight.getString(1)+"  )";
					logger.debug("query: "+query);
					
					Statement stInsertFreightOthersCSP = conn.createStatement();
					stInsertFreightOthersCSP.executeUpdate(query);
					stInsertFreightOthersCSP.close();
				}
				
				rsOtherFreight.close();
				stOtherFreight.close();
			}
			
			if (rs.getString(5).equals("4")){
				
				query = "SELECT APO_ID, APO_INCREASE, APO_AWB_RATE, APO_OTHER_FEE, APO_FUEL_SURCHARGE, APO_SECURITY_CHARGE, APO_ORDERS, APO_PALETTES, SFE_ID, APO_INCREASE_AWB, APO_INCREASE_FFW, APO_INCREASE_FSC, APO_INCREASE_SCC "
						+ "FROM CCS_STAGE_FREIGHT_APO WHERE STG_ID = "+idLastStage+" AND SIS_ID = "+rs.getString(1)+" ";
				
				logger.debug("query: "+query);
				ResultSet rsAPOFreight = conn.createStatement().executeQuery(query);
				
				if (rsAPOFreight.next()){
					
					ResultSet rsSequence   = conn.createStatement().executeQuery("SELECT CCS_SEQ_STAGE_FREIGHT_APO.NEXTVAL FROM DUAL");
					String idNewAPOFreight = rsSequence.next()==true?rsSequence.getString(1):"";
					
					query = "INSERT INTO CCS_STAGE_FREIGHT_APO "
							+ "(APO_ID, "
							+ "APO_INCREASE, "
							+ "APO_AWB_RATE, "
							+ "APO_OTHER_FEE, "
							+ "APO_FUEL_SURCHARGE, "
							+ "APO_SECURITY_CHARGE, "
							+ "APO_ORDERS, "
							+ "APO_PALETTES, "
							+ "SFE_ID, "
							+ "STG_ID, "
							+ "SIS_ID, "
							+ "APO_INCREASE_AWB, "
							+ "APO_INCREASE_FFW, "
							+ "APO_INCREASE_FSC, "
							+ "APO_INCREASE_SCC "
							+ ") VALUES"
							+ "( "
							+ idNewAPOFreight+", "
							+ rsAPOFreight.getString(2) +", "
							+ rsAPOFreight.getString(3) +", "
							+ rsAPOFreight.getString(4) +", "
							+ rsAPOFreight.getString(5)+", "
							+ rsAPOFreight.getString(6)+", "
							+ rsAPOFreight.getString(7)+", "
							+ rsAPOFreight.getString(8)+", "
							+ rsAPOFreight.getString(9)+", "
							+ idStage+", "
							+ idNewSiteSource+", "
							+ rsAPOFreight.getString(10)+", "
							+ rsAPOFreight.getString(11)+", "
							+ rsAPOFreight.getString(12)+", "
							+ rsAPOFreight.getString(13)+" "
							+ " )";

					logger.debug("query: "+query);
					
					Statement stInsertFreightAPO = conn.createStatement();
					stInsertFreightAPO.executeUpdate(query);
					stInsertFreightAPO.close();
					
					
					
				
                    query="UPDATE CCS_STAGE_FREIGHT_APO SET SFE_ID = ( "
                            + "  SELECT SFE_ID FROM CCS_STAGE_FREIGHT_EXCHANGE WHERE STG_ID = "+idStage+" AND SFE_ACRONYM = ( "
                            + "    SELECT SFE_ACRONYM FROM "
                            + "    CCS_STAGE_FREIGHT_EXCHANGE "
                            + "    WHERE STG_ID = "+idLastStage+" "
                            + "    AND   SFE_ID = "+rsAPOFreight.getString(9)+" "
                            + "  ) "
                            + ") "
                            + "WHERE STG_ID = "+idStage+" AND APO_ID = "+idNewAPOFreight+" ";
                    
                    logger.debug("query: "+query);
                    
                    Statement stUpdateFreightAPO = conn.createStatement();
                    stUpdateFreightAPO.executeUpdate(query);
                    stUpdateFreightAPO.close();

                    
                    
					
					
					query = "INSERT INTO CCS_STAGE_FREIGHT_APO_RATE( "
							+ " APR_ID, "
							+ " APR_CONCEPT, "
							+ "APR_RATE, "
							+ "STG_ID, "
							+ "APO_ID "
							+ ") ( "
							+ "SELECT CCS_SEQ_CCS_APO_RATE.NEXTVAL, "
							+ "APR_CONCEPT, "
							+ "APR_RATE, "
							+ ""+idStage+", "
							+ ""+idNewAPOFreight+" "
							+ "FROM CCS_STAGE_FREIGHT_APO_RATE WHERE STG_ID = "+idLastStage+" AND APO_ID = "+rsAPOFreight.getString(1)+")";
					logger.debug("query: "+query);
					
					Statement stInsertFreightAPORate = conn.createStatement();
					stInsertFreightAPORate.executeUpdate(query);
					stInsertFreightAPORate.close();
					
					
					rsAPOFreight.close();
				}
			}
			
			
			
			//UPDATE ITEM MASTER SITE SOURCE
			query = "UPDATE CCS_LOCAL_ITEM_MASTER SET SIS_ID = "+idNewSiteSource+" WHERE STG_ID = "+idStage+" AND SIS_ID = "+rs.getString(1)+"";
			logger.debug("query: "+query);
			conn.createStatement().executeUpdate(query);
			
		}
		
	}
	
	
	private void updateSetup(Connection conn, String idStage, String idLastStage) throws DAOException, SQLException{
		
		
		String qeInsertSelectDutiesHead = "INSERT INTO CCS_SETUP_CUSTUM_DUTIES_HEAD ( " +
				"SDH_ID, " +
				"SDH_OTHER_TAXES_FODINFA, " +
				"SDH_VAT_IMPORTS, " +
				"SDH_OTHER_TAXES_IMP_CAP, " +
				"STG_ID " +
				")(SELECT " +
				"CCS_SEQ_CCS_SETUP_CUS_DU_HEAD.NEXTVAL, " +
				"SDH_OTHER_TAXES_FODINFA, " +
				"SDH_VAT_IMPORTS, " +
				"SDH_OTHER_TAXES_IMP_CAP, " +
				""+idStage+" " +
				"FROM CCS_SETUP_CUSTUM_DUTIES_HEAD WHERE STG_ID = "+idLastStage+")";
		
		Statement st = conn.createStatement();
		st.executeUpdate(qeInsertSelectDutiesHead);
		st.close();
		
		String qeInsertSelectDutiesDetail = "INSERT INTO CCS_SETUP_CUSTUM_DUTIES_DETAIL ( " +
				"SDD_ID, " +
				"SDD_DUTY_NAME, " +
				"SDD_DUTY_TARIFF, " +
				"SDD_OTHER_TAXES, " +
				"SDD_COD, " +
				"STG_ID " +
				") (SELECT " +
				"CCS_SEQ_CCS_SETUP_CUS_DU_DET.NEXTVAL, " +
				"SDD_DUTY_NAME, " +
				"SDD_DUTY_TARIFF, " +
				"SDD_OTHER_TAXES, " +
				"SDD_COD, " +
				""+idStage+" " +
				"FROM CCS_SETUP_CUSTUM_DUTIES_DETAIL WHERE STG_ID = "+idLastStage+" )";
		
		st = conn.createStatement();
		st.executeUpdate(qeInsertSelectDutiesDetail);
		st.close();
		
		String qeInsertSelectMacroVars = "INSERT INTO CCS_SETUP_MACRO_VARS( " +
				"SMV_ID, " +	
				"SMV_AMOUNT, " +
				"SMV_NAME, " +
				"SMV_COD, " +
				"STG_ID " +
				")( SELECT " +
				"CCS_SEQ_CCS_SETUP_MACRO_VARS.NEXTVAL, " +
				"SMV_AMOUNT, " +
				"SMV_NAME, " +
				"SMV_COD, " +
				""+idStage+" " +
				"FROM CCS_SETUP_MACRO_VARS WHERE STG_ID = "+idLastStage+")";
		
		st = conn.createStatement();
		st.executeUpdate(qeInsertSelectMacroVars);
		st.close();
		
		
		String qeInsertSelectSetupOpConsolidation = "INSERT INTO CCS_SETUP_OPE_CONSOLIDATION ( " +
				"SOC_ID, " +
				"SOC_ORDERS, " +
				"STG_ID, " +
				"TRA_ID, " +
				"SIS_ID " +
				")(SELECT " +
				"CCS_SEQ_CCS_SETUP_OPE_CONS.NEXTVAL, " +
				"SOC_ORDERS, " +
				""+idStage+", " +
				"TRA_ID, " +
				"SIS_ID " +
				"FROM CCS_SETUP_OPE_CONSOLIDATION WHERE STG_ID = "+idLastStage+")";
		
		st = conn.createStatement();
		st.executeUpdate(qeInsertSelectSetupOpConsolidation);
		st.close();
		
		String qeInsertSelectSetupOpManage = "INSERT INTO CCS_SETUP_OPE_MANAGE ( " +
				"SOM_ID," +
				"SOM_ENTRY," +
				"SOM_PROCESSING_FEE," +
				"SOM_RATE_KG," +
				"SOM_PERCENT_TP," +
				"CUR_ID," +
				"STG_ID" +
				")(SELECT " +
				"CCS_SEQ_CCS_SETUP_OPE_MANAGE.NEXTVAL, " +
			    "SOM_ENTRY," +
			    "SOM_PROCESSING_FEE," +
			    "SOM_RATE_KG," +
			    "SOM_PERCENT_TP," +
			    "CUR_ID," +
				""+idStage+
				" FROM CCS_SETUP_OPE_MANAGE WHERE STG_ID = "+idLastStage+")";
		
		st = conn.createStatement();
		st.executeUpdate(qeInsertSelectSetupOpManage);
		st.close();
		
		String qeInsertSelectSetupSorageTemp = "INSERT INTO CCS_SETUP_STORAGE_TEMP ( " +
				"SST_ID," +
				"SST_ENTRY," +
				"SST_PROCESSING_FEE," +
				"STG_ID," +
				"CUR_ID" +
				")(SELECT " +
				"CCS_SEQ_CCS_SETUP_STORAGE_TEMP.NEXTVAL, " +
			    "SST_ENTRY," +
			    "SST_PROCESSING_FEE," +
				idStage+","+
			    "CUR_ID" +
				" FROM CCS_SETUP_STORAGE_TEMP WHERE STG_ID = "+idLastStage+")";
		
		st = conn.createStatement();
		st.executeUpdate(qeInsertSelectSetupSorageTemp);
		st.close();
		
		String qeInsertSelectSetupLocalTrasnport = "INSERT INTO CCS_SETUP_LOCAL_TRANSPORT ( " +
				"SLT_ID,  " +
				"SLT_ENTRY," +
				"SLT_PROCESSING_FEE," +
				" STG_ID," +
				"CUR_ID" +
				")(SELECT " +
				"CCS_SEQ_CCS_SETUP_LOCAL_TRANSP.NEXTVAL, " +
			    "SLT_ENTRY," +
			    "SLT_PROCESSING_FEE," +
				idStage+","+
			    "CUR_ID" +
				" FROM CCS_SETUP_LOCAL_TRANSPORT WHERE STG_ID = "+idLastStage+")";
		
		st = conn.createStatement();
		st.executeUpdate(qeInsertSelectSetupLocalTrasnport);
		st.close();
		
		
		
		
		String query = "SELECT SLR_ID, SLR_NAME, SLR_RATE, STG_ID FROM CCS_SETUP_LOCAL_ROUTE WHERE STG_ID = "+idLastStage+"";
		
		st = conn.createStatement();
		ResultSet rs = st.executeQuery(query);
		
		while(rs.next()){
		
			query = "SELECT CCS_SEQ_SETUP_LOCAL_ROUTE.NEXTVAL FROM DUAL";
			
			
			st = conn.createStatement();
			ResultSet rsIdRoute = st.executeQuery(query);
			String idRoute = rsIdRoute.next()?rsIdRoute.getString(1):"";
			
			rsIdRoute.close();
			st.close();
			
			query = "INSERT INTO CCS_SETUP_LOCAL_ROUTE(SLR_ID,SLR_NAME, SLR_RATE, STG_ID) VALUES( " +
					idRoute +", '"+rs.getString(2)+"', "+rs.getString(3)+", "+idStage+ ")";
			
			logger.debug("query: "+query);
			conn.createStatement().executeUpdate(query);
			
			//UPDATE ITEM MASTER SITE SOURCE
			query = "UPDATE CCS_LOCAL_ITEM_MASTER SET SLR_ID = "+idRoute+" WHERE STG_ID = "+idStage+" AND SLR_ID = "+rs.getString(1)+"";
			logger.debug("query: "+query);
			
			st = conn.createStatement();
			st.executeUpdate(query);
			st.close();
		}
		rs.close();
//		st.close();
		
//		String query = "INSERT INTO CCS_SETUP_LOCAL_ROUTE ( " +
//				"SLR_ID, " +
//				"SLR_NAME, " +
//				"STG_ID, " +
//				"SLR_RATE " +
//				")( " +
//				"SELECT CCS_SEQ_SETUP_LOCAL_ROUTE.NEXTVAL, SLR_NAME, "+idStage+", SLR_RATE FROM CCS_SETUP_LOCAL_ROUTE  WHERE STG_ID = "+idLastStage+")";
//		conn.createStatement().executeUpdate(query);
		

		
		String qeInsertSelectSetupStorageRange = "INSERT INTO CCS_SETUP_STORAGE_RANGE ( " +
				"SSR_ID, " +
				"SSR_RANGE_FROM,  " +
				"SSR_RANGE_TO," +
				"SSR_RANGE_RATE," +
				"CUR_ID, " +
				"STG_ID" +
				")(SELECT " +
				"CCS_SEQ_CCS_SETUP_STORAGE_RANG.NEXTVAL, " +
			    "SSR_RANGE_FROM," +
			    "SSR_RANGE_TO," +
			    "SSR_RANGE_RATE, " +
			    "CUR_ID, " +
				idStage+
				" FROM CCS_SETUP_STORAGE_RANGE WHERE STG_ID = "+idLastStage+")";
		
		st = conn.createStatement();
		st.executeUpdate(qeInsertSelectSetupStorageRange);
		st.close();
		
		
		query = "SELECT SSR_ID,SSR_NAME, SSR_RATE, STG_ID FROM CCS_SETUP_STORAGE_RATE WHERE STG_ID = "+idLastStage+"";
		Statement stStorageRate = conn.createStatement();
		ResultSet rsStorageRate = stStorageRate.executeQuery(query);
		
		
		while(rsStorageRate.next()){
		
			query = "SELECT CCS_SEQ_CCS_SETUP_STORAGE_RATE.NEXTVAL FROM DUAL";
			
			st = conn.createStatement();
			ResultSet rsIdRate = st.executeQuery(query);
			String idRate = rsIdRate.next()?rsIdRate.getString(1):"";
			
			rsIdRate.close();
			st.close();
			
			query = "INSERT INTO CCS_SETUP_STORAGE_RATE(SSR_ID,SSR_NAME, SSR_RATE, STG_ID) VALUES( " +
					idRate +", '"+rsStorageRate.getString(2)+"', "+rsStorageRate.getString(3)+", "+idStage+ ")";
			
			logger.debug("query: "+query);
			
			st = conn.createStatement();
			st.executeUpdate(query);
			st.close();
			
			//UPDATE ITEM MASTER SITE SOURCE
			query = "UPDATE CCS_LOCAL_ITEM_MASTER SET SSR_ID = "+idRate+" WHERE STG_ID = "+idStage+" AND SSR_ID = "+rsStorageRate.getString(1)+"";
			logger.debug("query: "+query);
			
			st = conn.createStatement();
			st.executeUpdate(query);
			st.close();
		}
		rsStorageRate.close();
		stStorageRate.close();
		
		
		String qeInsertSelectLocalStorageRange = "INSERT INTO CCS_SETUP_LOCAL_RANGE ( " +
				"SLR_ID,  "+
				"SLR_RANGE_FROM,  "+ 
				"SLR_RANGE_TO,  " + 
				"SLR_RANGE_RATE_KG,  "+
				"CUR_ID,  "+
				"STG_ID" +
				")(SELECT " +
				"CCS_SEQ_CCS_SETUP_LOCAL_RANGE.NEXTVAL, " +
			    "SLR_RANGE_FROM,  "+ 
				"SLR_RANGE_TO,  " + 
				"SLR_RANGE_RATE_KG," +
				"CUR_ID," +
				idStage+
				" FROM CCS_SETUP_LOCAL_RANGE WHERE STG_ID = "+idLastStage+")";
		
		st = conn.createStatement();
		st.executeUpdate(qeInsertSelectLocalStorageRange);
		st.close();
		

		String qeInsertSelectCustodyService = "INSERT INTO CCS_SETUP_CUSTODY_SERVICE(CUR_ID, SCS_BASE_LINE, SCS_FEE_COST, STG_ID)( " +
												 "SELECT CUR_ID, SCS_BASE_LINE, SCS_FEE_COST, "+idStage+" FROM CCS_SETUP_CUSTODY_SERVICE WHERE STG_ID = "+idLastStage+")";
		st = conn.createStatement();
		st.executeUpdate(qeInsertSelectCustodyService);
		st.close();
		
	}
	
	public void simulateStage(StageVO stageVO) throws DAOException{
		
		Connection conn = null;
		
		int statusStage = 0;
		try {
			conn = dataConnection.getConnectionOracleMSD();
			
			conn.setAutoCommit(false);
			
			String qeSelectStatusStage = "SELECT STT_ID, STS_ID FROM CCS_STAGE WHERE STG_ID = "+stageVO.getIdStage()+"  ";
			ResultSet rs = conn.createStatement().executeQuery(qeSelectStatusStage);
			if (rs.next())
				statusStage = rs.getInt(2);
			
			if (statusStage == 1) statusStage = 2;
			
			String qeSimulateStageState = "UPDATE CCS_STAGE " +
					"SET SGT_NAME = '"+stageVO.getNameStage()+"', " +
					"STG_DATE_LAST_UPDATE = SYSDATE, " +
					"USR_ISID = '"+stageVO.getIsidUserStage()+"', " +
					"STS_ID = "+statusStage+", " +
					"STG_DESCRIPTION = '"+stageVO.getDescriptionStage()+"' "+
					"WHERE STG_ID             = "+stageVO.getIdStage();
			
			conn.createStatement().executeUpdate(qeSimulateStageState);
			
			calculateCostProduct(stageVO.getIdStage(), conn);
			
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
	
	protected void calculateCostProduct(String idStage, Connection conn) throws DAOException, SQLException{
		
		String qeSelectMonthlyData = "SELECT STG_A.IMU_COD_PRODUCT, STG_A.IMU_DESRIPTION_PRODUCT, STG_A.IMU_AJUSTED, STG_A.LIM_FOB_COST, STG_A.LIM_WEIGTH_PALETTE, STG_A.LIM_HIGH_PALETTE, STG_A.LIM_LARGE_PALETTE, STG_A.LIM_WIDE_PALETTE, STG_A.LIM_UNIT_BY_PALETTE, STG_A.FAR_AWB_KG_RATES, " +
									"STG_A.FAR_FUEL_SURCHARGE, STG_A.FAR_OTHER_FFW_FEES, STG_A.FAR_SECURITY_CHARGE, STG_A.INSURANCE, STG_A.SDD_DUTY_TARIFF, STG_A.SDH_OTHER_TAXES_FODINFA, STG_A.SDH_VAT_IMPORTS, STG_A.SDH_OTHER_TAXES_IMP_CAP, STG_A.FRM_ID, STG_A.SIS_ID, " +
									"STG_A.SDD_OTHER_TAXES, STG_A.IVA, UNIT.IMU_JAN, UNIT.IMU_FEB, UNIT.IMU_MAR, UNIT.IMU_APR, UNIT.IMU_MAY, UNIT.IMU_JUN, UNIT.IMU_JUL, UNIT.IMU_AUG, " +
									"UNIT.IMU_SEP, UNIT.IMU_OCT, UNIT.IMU_NOV, UNIT.IMU_DIC, STG_A.FAR_INCREASE, STG_A.EXCHANGE_RATE, STG_A.FRO_PALETTES_NUMBER, STG_A.CONCEPT_OTHER_FEE, STG_A.SOM_PROCESSING_FEE, STG_A.SOC_ORDERS, " +
									"STG_A.SOM_RATE_KG, STG_A.SOM_PERCENT_TP, STG_A.SST_PERCENT_COST, STG_A.SST_PROCESSING_FEE, STG_A.SLT_PERCENT_COST, STG_A.SLT_PROCESSING_FEE, STG_A.SCS_BASE_LINE, STG_A.SCS_FEE_COST, STG_A.LIM_IS_IVA, STG_A.FAR_INCREASE_AWB, " +
									"STG_A.FAR_INCREASE_FFW, STG_A.FAR_INCREASE_FSC, STG_A.FAR_INCREASE_SCC " +
									"FROM CCS_VIEW_COST_CALCULATION STG_A, CCS_IMPORT_UNITS UNIT " +
									"WHERE STG_A.STG_ID = "+idStage +" " +
									"AND   STG_A.STG_ID = UNIT.STG_ID " +
									"AND   STG_A.IMU_COD_PRODUCT = UNIT.IMU_COD_PRODUCT " +
									"ORDER BY  STG_A.IMU_COD_PRODUCT"; 
		logger.debug("qeSelectMonthlyDataB: "+qeSelectMonthlyData);
		ResultSet rs = conn.createStatement().executeQuery(qeSelectMonthlyData);
		while (rs.next()){
			
			System.out.println("SI HA ENCONTRADO REGISTROS");
			
			System.out.println("float trnPriceUnitProduct   = rs.getFloat(4);: "+rs.getFloat(4));
			
			String localCodeProduct      = rs.getString(1);
			
			float importAjustProduct    = rs.getFloat(3);
			float trnPriceUnitProduct   = rs.getFloat(4);
			float weigthPaletteProduct  = rs.getFloat(5);
			float hightPaletteProduct   = rs.getFloat(6);
			float largePaletteProduct   = rs.getFloat(7);
			float widePaletteProduct    = rs.getFloat(8);
			float unitPerPaletteProduct = rs.getFloat(9);
			float awKgRatesProduct      = rs.getFloat(10);
			float fuelSurChangeProduct  = rs.getFloat(11);
			float otherFFWfeesProduct   = rs.getFloat(12);
			float securityChargeProduct = rs.getFloat(13);
			float insuranceSetup        = rs.getFloat(14);
			float dutyTariffSetup       = rs.getFloat(15);
			float otherTaxesFODINFA     = rs.getFloat(16);
			float vatImportSetup        = rs.getFloat(17);
			float otherTaxesImpCapital  = rs.getFloat(18); 
//			float idModeTransporProduct = rs.getFloat(19);
			float idSiteSourceProduct   = rs.getFloat(20);
			float dutyOtherTaxesProduct = rs.getFloat(21);
			float ivaSiteProduct        = rs.getFloat(22);
			
			float janProduct = rs.getFloat(23);
			float febProduct = rs.getFloat(24);
			float marProduct = rs.getFloat(25);
			float aprProduct = rs.getFloat(26);
			float mayProduct = rs.getFloat(27);
			float junProduct = rs.getFloat(28);
			float julProduct = rs.getFloat(29);
			float augProduct = rs.getFloat(30);
			float sepProduct = rs.getFloat(31);
			float octProduct = rs.getFloat(32);
			float novProduct = rs.getFloat(33);
			float decProduct = rs.getFloat(34);
			
			float increaseRateFreight = rs.getFloat(35);
//			float exchangeRateStage   = rs.getFloat(36);
			float paletteFreightOther = rs.getFloat(37);
			float otherFreighteFee    = rs.getFloat(38);
			
			
			float activityFeeCost     = rs.getFloat(39);
			float consolidationOrders = rs.getFloat(40);
			float activitykgRateCost  = rs.getFloat(41);
			float activityPercentCost = rs.getFloat(42);
			
			
			float warehousingPercent = rs.getFloat(43);
			float warehousingFee     = rs.getFloat(44);
			
			
			float localTransportPercent = rs.getFloat(45);
			float localTransportFee     = rs.getFloat(46);
			
			
			float custodyBaseLine = rs.getFloat(47);
			float custodyFeeCost  = rs.getFloat(48);
			
			String isIVAProduct    = rs.getString(49);
			
			
			float increaseAWBFreight = rs.getFloat(50);
			float increaseFFWFreight = rs.getFloat(51);
			float increaseFSCFreight = rs.getFloat(52);
			float increaseSCCFreight = rs.getFloat(53);
			
			
			
			logger.debug("increaseAWBFreight: "+increaseAWBFreight);
			logger.debug("increaseFFWFreight: "+increaseFFWFreight);
			logger.debug("increaseFSCFreight: "+increaseFSCFreight);
			logger.debug("increaseSCCFreight: "+increaseSCCFreight);

			//falta agregar a la vista
			
			
			logger.debug("PRODUCT: "+ localCodeProduct);
			
			
			logger.debug("paletteFreightOther: "+paletteFreightOther);
			logger.debug("isIVAProduct: "+isIVAProduct);
			
			
			logger.debug("importAjustProduct: "+importAjustProduct);
			
			float yearUnitProduct = janProduct + febProduct + marProduct + aprProduct + mayProduct + junProduct + julProduct + augProduct + sepProduct + octProduct + novProduct + decProduct;
			logger.debug("yearUnitProduct: "+yearUnitProduct);
			
			
//			float totalPalettes = (float) (Math.round((importAjustProduct / unitPerPaletteProduct) * 100.0) / 100.0);
			float totalPalettes = 0f;
			if (unitPerPaletteProduct != 0)
				totalPalettes = (importAjustProduct / unitPerPaletteProduct);
			
			
			
			BigDecimal bigTotalPalettes = new BigDecimal(totalPalettes - Math.floor(totalPalettes));
			
			if (bigTotalPalettes.floatValue() > 0.5f)
				totalPalettes = (float) (Math.round((totalPalettes) * 1.0) / 1.0);
			
			
			
			
			
//			(Math.round(kilobytes*100.0)/100.0)
			
//			(double)Math.round(value * 100000) / 100000
			logger.debug("totalPalettes: "+totalPalettes);
					
			float chargeableWeightA = totalPalettes * (hightPaletteProduct * largePaletteProduct * widePaletteProduct / 6000);
			logger.debug("chargeableWeightA: "+chargeableWeightA);
			
			float chargeableWeightB = totalPalettes * weigthPaletteProduct;
			logger.debug("chargeableWeightB: "+chargeableWeightB);
			
			float chargeableWeight =  chargeableWeightA>chargeableWeightB?chargeableWeightA:chargeableWeightB;
			logger.debug("chargeableWeight: "+chargeableWeight);
			
			
			
			float freightRateKg = 0;
			String qeSelectFreightRateKg = "SELECT (CASE SFE.SFE_ACRONYM WHEN 'USD' THEN RAN.FRR_RATE ELSE RAN.FRR_RATE * SFE.SFE_RATE END) FRR_RATE FROM CCS_STAGE_FREIGHT_AIR_RANGE RAN, CCS_STAGE_FREIGHT_AIR FAR, CCS_STAGE_FREIGHT_EXCHANGE SFE  " +
					"WHERE  RAN.STG_ID = "+idStage+" " +
					"AND    RAN.FAR_ID = FAR.FAR_ID  " +
					"AND    FAR.SFE_ID = SFE.SFE_ID  " +
					"AND    "+chargeableWeight+" >= RAN.FRR_FROM AND "+chargeableWeight+" <= RAN.FRR_TO AND RAN.SIS_ID = "+idSiteSourceProduct;
			
			logger.debug("qeSelectFreightRateKg: "+qeSelectFreightRateKg);
			Statement stmtFreightRateKg = conn.createStatement(); //2,728467
			
			
			ResultSet rsFreightRateKg = stmtFreightRateKg.executeQuery(qeSelectFreightRateKg);
			if (rsFreightRateKg.next()){
				freightRateKg = rsFreightRateKg.getFloat(1);
			}
			rsFreightRateKg.close();
			stmtFreightRateKg.close();
			logger.debug("freightRateKg: "+freightRateKg);
			
			
//			float ordersNumbersFreight = 0;
//			String qeSelectOrdersNumbersFreight = "SELECT SOC_ORDERS FROM CCS_SETUP_OPE_CONSOLIDATION WHERE STG_ID = "+idStage+" and SIS_ID = "+idSiteSourceProduct;
//			Statement stmtOrdersNumbersFreight = conn.createStatement();
//			ResultSet rsOrdersNumbersFreight = stmtOrdersNumbersFreight.executeQuery(qeSelectOrdersNumbersFreight);
//			if (rsOrdersNumbersFreight.next()){
//				ordersNumbersFreight = rsOrdersNumbersFreight.getFloat(1);
//			}
//			rsOrdersNumbersFreight.close();
//			stmtOrdersNumbersFreight.close();
			
			
			logger.debug("freightRateKg: "+freightRateKg);
			logger.debug("chargeableWeight: "+chargeableWeight);
			logger.debug("importAjustProduct: "+importAjustProduct);
			logger.debug("increaseFreight: "+increaseRateFreight);
			logger.debug("consolidationOrders: "+consolidationOrders);
			logger.debug("otherFFWfeesProduct: "+otherFFWfeesProduct);
			logger.debug("fuelSurChangeProduct: "+fuelSurChangeProduct);
			logger.debug("securityChargeProduct: "+securityChargeProduct);
			logger.debug("awKgRatesProduct: "+awKgRatesProduct);
			
			
			/*FREIGHT COST UNIT*/
			float costUnitFreigthStage = 0;
			float costUnitFSCStage     = 0;
			float costUnitSCCStage     = 0;
			float costUnitAWBtage      = 0;
			float costUnitFFWFeedtage = 0;
			if (importAjustProduct !=0){
				
				costUnitFreigthStage = (chargeableWeight * freightRateKg          / importAjustProduct) * (1 + increaseRateFreight);
				
				costUnitFSCStage     = (chargeableWeight * fuelSurChangeProduct   / importAjustProduct) * (1 + increaseFSCFreight);
				
				costUnitSCCStage     = (chargeableWeight * securityChargeProduct  / importAjustProduct) * (1 + increaseSCCFreight);
				
				costUnitAWBtage      = (chargeableWeight * awKgRatesProduct       / importAjustProduct) * (1 + increaseAWBFreight);
				
				if (consolidationOrders != 0)
					costUnitFFWFeedtage  = ((otherFFWfeesProduct / consolidationOrders) /  importAjustProduct) * (1 + increaseFFWFreight);
				
			}

			
			
			float freigthUnitProduct = costUnitFreigthStage + costUnitFSCStage + costUnitSCCStage + costUnitAWBtage + costUnitFFWFeedtage;
			
			logger.debug("freigthUnitProduct: "+freigthUnitProduct);
			logger.debug("costUnitFreigthStage: "+costUnitFreigthStage);
			logger.debug("costUnitFSCStage: "+costUnitFSCStage);
			logger.debug("costUnitSCCStage: "+costUnitSCCStage);
			logger.debug("costUnitAWBtage: "+costUnitAWBtage);
			logger.debug("costUnitFFWFeedtage: "+costUnitFFWFeedtage);
			
			
			//Si es cero, entonces es marï¿½timo
			boolean isAirFreight = true;
			if (freigthUnitProduct == 0){
				if (paletteFreightOther != 0)
					if (unitPerPaletteProduct != 0)
						freigthUnitProduct = ((otherFreighteFee / paletteFreightOther) / unitPerPaletteProduct) * (1 + increaseRateFreight);
				
				isAirFreight = false;
				
				costUnitFreigthStage = freigthUnitProduct;
			}else{
				
				logger.debug("paletteFreightOther: "+paletteFreightOther);
				//Si el flete aereo tiene palette, entonces se considera como un flete aereo por contenedor.
				if(paletteFreightOther != 0){
					
					logger.debug("********************************************************************************");
					logger.debug("************************AIR APO FREIGHT*****************************************");
					logger.debug("freigthUnitProduct: "+freigthUnitProduct);
					logger.debug("otherFreighteFee: "+otherFreighteFee);
					logger.debug("paletteFreightOther: "+paletteFreightOther);
					logger.debug("unitPerPaletteProduct: "+unitPerPaletteProduct);
					logger.debug("increaseFreight: "+increaseRateFreight);
					logger.debug("********************************************************************************");
					logger.debug("********************************************************************************");
					
					float roundAPOFreight = 0;
					if (unitPerPaletteProduct != 0)
						if (paletteFreightOther != 0)
							roundAPOFreight = ((importAjustProduct / unitPerPaletteProduct) / paletteFreightOther);
					
					logger.debug("roundAPOFreight: "+roundAPOFreight);

					roundAPOFreight = FormatUtilities.round(new BigDecimal(roundAPOFreight), 0, true).floatValue();
					logger.debug("roundAPOFreight: "+roundAPOFreight);
					
					float APOFreightb = 0;
					if (importAjustProduct != 0)
						APOFreightb = ((roundAPOFreight * otherFreighteFee) / importAjustProduct) * (1 + increaseRateFreight);
					
					logger.debug("APOFreightb: "+APOFreightb);
							
					freigthUnitProduct = freigthUnitProduct + APOFreightb;
					
					costUnitFreigthStage = APOFreightb;
				}
			}
			
			logger.debug("freigthUnitProduct: "+freigthUnitProduct);
				
			float insuranceUnitProduct = (trnPriceUnitProduct + freigthUnitProduct ) * insuranceSetup;
			
			logger.debug("trnPriceUnitProduct: "+trnPriceUnitProduct);
			logger.debug("[[[freigthUnitProduct]]]: "+freigthUnitProduct);
			logger.debug("insuranceUnitProduct: "+insuranceUnitProduct);
			
			float baseCIFProduct = trnPriceUnitProduct + freigthUnitProduct + insuranceUnitProduct;
			logger.debug("baseCIFProduct: "+baseCIFProduct);
			
			logger.debug("baseCIFProduct: "+baseCIFProduct);
			logger.debug("dutyTariffSetup: "+dutyTariffSetup);
			
			/*DUTIES COST UNIT*/
			float dutyNominalTariffSetup   = baseCIFProduct * dutyTariffSetup;
			logger.debug("dutyNominalTariffSetup: "+dutyNominalTariffSetup);
			
			
			logger.debug("dutyOtherTaxesProduct: "+dutyOtherTaxesProduct);
			logger.debug("ivaSiteProduct: "+ivaSiteProduct);
			
			
			
			float dutyNominalOtherTaxes    = baseCIFProduct * dutyOtherTaxesProduct;
			logger.debug("dutyNominalOtherTaxes: "+dutyNominalOtherTaxes);
			
			
			float otherNominalTaxesFODINFA = baseCIFProduct * otherTaxesFODINFA;
			logger.debug("otherNominalTaxesFODINFA: "+otherNominalTaxesFODINFA);
			
			float vatNominalTaxesProduct   = (baseCIFProduct + dutyNominalTariffSetup + dutyNominalOtherTaxes  + otherNominalTaxesFODINFA) * (isIVAProduct.equals("Y")?vatImportSetup:0); 
			logger.debug("vatNominalTaxesProduct: "+vatNominalTaxesProduct);
			
			float otherNominalTaxesCapital = trnPriceUnitProduct * otherTaxesImpCapital;
			logger.debug("otherNominalTaxesCapital: "+otherNominalTaxesCapital);
			
			
//			float dutyUnitProducto = dutyNominalTariffSetup + dutyNominalOtherTaxes + vatNominalTaxesProduct + otherNominalTaxesCapital + otherNominalTaxesFODINFA;
			float dutyUnitProducto = dutyNominalTariffSetup + dutyNominalOtherTaxes + vatNominalTaxesProduct + otherNominalTaxesFODINFA;
			logger.debug("[[[dutyUnitProducto]]]: "+dutyUnitProducto);
			
			
			
			//Others
			
			logger.debug("activityFeeCost: "+activityFeeCost);
			logger.debug("activitykgRateCost: "+activitykgRateCost);
			
			float operativeCostFee = 0;
			if (consolidationOrders != 0)
				if (importAjustProduct != 0)
					operativeCostFee     = (activityFeeCost / consolidationOrders) / importAjustProduct;
			
			float operativeCostKgRate = 0;
			if (importAjustProduct != 0)
				operativeCostKgRate  = chargeableWeight * activitykgRateCost / importAjustProduct;
			
			
//			float operativeCostPercent = trnPriceUnitProduct * activityPercentCost;
			float operativeCostPercent = baseCIFProduct * activityPercentCost;
			
			float otherOperativeActUnit = operativeCostFee + operativeCostKgRate + operativeCostPercent;
			
			
			logger.debug("activityPercentCost: "+activityPercentCost);
			logger.debug("operativeCostFee: "+operativeCostFee);
			logger.debug("operativeCostKgRate: "+operativeCostKgRate);
			logger.debug("operativeCostPercent: "+operativeCostPercent);
			logger.debug("otherOperativeActUnit: "+otherOperativeActUnit);
			
			
			
			float warehousingCostKgRate = 0;
			String qeSelectWarehousingKgRate = "SELECT (CASE SSR.CUR_ID WHEN 1 THEN SSR.SSR_RANGE_RATE ELSE SSR.SSR_RANGE_RATE / (NULLIF((SELECT SMV_AMOUNT FROM CCS_SETUP_MACRO_VARS H_SMV WHERE H_SMV.STG_ID = SSR.STG_ID AND SMV_COD = 'ERA'),0)) END) SSR_RANGE_RATE FROM CCS_SETUP_STORAGE_RANGE SSR WHERE SSR.STG_ID = "+idStage+" AND "+chargeableWeight+" >= SSR.SSR_RANGE_FROM AND  "+chargeableWeight+" <= SSR.SSR_RANGE_TO ";
			logger.debug("qeSelectWarehousingKgRate: "+qeSelectWarehousingKgRate);
			Statement stmtWarehousingKgRate = conn.createStatement();
			ResultSet rsWarehousingKgRate = stmtWarehousingKgRate.executeQuery(qeSelectWarehousingKgRate);
			if (rsWarehousingKgRate.next()){
				
				if (importAjustProduct != 0)
					warehousingCostKgRate = rsWarehousingKgRate.getFloat(1) * chargeableWeightB / importAjustProduct;
				
				logger.debug("rsWarehousingKgRate.getFloat(1): "+rsWarehousingKgRate.getFloat(1));
			}
			rsWarehousingKgRate.close();
			stmtWarehousingKgRate.close();
			
			float warehousingCostFee = 0;
			if (consolidationOrders != 0)
				if (importAjustProduct != 0)
					warehousingCostFee = (warehousingFee / consolidationOrders) / importAjustProduct;
			
			float warehousingCostPercent = baseCIFProduct * warehousingPercent; //tarifa
//			float warehousingCostPercent = trnPriceUnitProduct * warehousingPercent; //tarifa
//			float warehousingCostKgRate  = warehousingKgRate;
			
			
			float otherTemporalWHUnit = warehousingCostFee + warehousingCostPercent + warehousingCostKgRate;
			
			//Esto ocurre para cuando el flete no es aereo
			if (!isAirFreight)
				otherTemporalWHUnit = 0;
			
			logger.debug("warehousingPercent: "+warehousingPercent);
			logger.debug("warehousingCostFee: "+warehousingCostFee);
			logger.debug("warehousingCostPercent: "+warehousingCostPercent);
			logger.debug("warehousingCostKgRate: "+warehousingCostKgRate);
			logger.debug("otherTemporalWHUnit: "+otherTemporalWHUnit);
			
			
			
//			
			
			float rangeLocalTransport = 0;
			String qeSelectLocalTransportKgRate = "SELECT  (CASE SLR.CUR_ID WHEN 1 THEN SLR.SLR_RANGE_RATE_KG ELSE SLR.SLR_RANGE_RATE_KG / (NULLIF((SELECT SMV_AMOUNT FROM CCS_SETUP_MACRO_VARS H_SMV WHERE H_SMV.STG_ID = SLR.STG_ID AND SMV_COD = 'ERA'),0)) END) SLR_RANGE_RATE_KG FROM CCS_SETUP_LOCAL_RANGE SLR WHERE SLR.STG_ID = "+idStage+" AND "+chargeableWeight+" >= SLR.SLR_RANGE_FROM AND  "+chargeableWeight+" <= SLR.SLR_RANGE_TO ";
			Statement stmtLocalTransportKgRate = conn.createStatement();
			ResultSet rsLocalTransportKgRate = stmtLocalTransportKgRate.executeQuery(qeSelectLocalTransportKgRate);
			if (rsLocalTransportKgRate.next()){
				
				if (importAjustProduct != 0)
					rangeLocalTransport = rsLocalTransportKgRate.getFloat(1) * chargeableWeightB / importAjustProduct ;
				
				logger.debug("rsLocalTransportKgRate.getFloat(1) LOCAL: "+rsLocalTransportKgRate.getFloat(1));
			}
			rsLocalTransportKgRate.close();
			stmtLocalTransportKgRate.close();
			
			
			float localTransportCostFee = 0;
			if (consolidationOrders != 0)
				if (importAjustProduct != 0)
					localTransportCostFee     = (localTransportFee / consolidationOrders) / importAjustProduct;
//			float localTransportCostPercent = trnPriceUnitProduct * localTransportPercent;
			float localTransportCostPercent = baseCIFProduct * localTransportPercent;
			
			
			logger.debug("localTransportPercent: "+localTransportPercent); //ruta local ok
			logger.debug("rangeLocalTransport: "+rangeLocalTransport); // rango ok
			logger.debug("localTransportCostFee: "+localTransportCostFee); //fee total
			logger.debug("localTransportCostPercent: "+localTransportCostPercent); // ruta local por transfer price ok
			
			
			float otherLocalTranUnit = localTransportCostFee + localTransportCostPercent + rangeLocalTransport;

			logger.debug("otherLocalTranUnit: "+otherLocalTranUnit); // total
			
			
//			custodyBaseLine
			
			logger.debug("localTransportFee: "+localTransportFee);
			logger.debug("importAjustProduct: "+importAjustProduct);
			logger.debug("custodyBaseLine: "+custodyBaseLine);
			logger.debug("custodyFeeCost: "+custodyFeeCost);
			
			float custodyOrderUnit  = 0;
			if ((trnPriceUnitProduct * importAjustProduct) > custodyBaseLine){
				if (consolidationOrders != 0)
					if (importAjustProduct != 0)
						custodyOrderUnit = (custodyFeeCost / consolidationOrders) / importAjustProduct;
			}
			
			logger.debug("[[[costUnitFreigthStage]]]: "+costUnitFreigthStage);
			logger.debug("otherOperativeActUnit: "+otherOperativeActUnit);
			logger.debug("otherTemporalWHUnit: "+otherTemporalWHUnit);
			
			logger.debug("custodyOrderUnit: "+custodyOrderUnit);
			
			float othersCostUnit = otherOperativeActUnit + otherTemporalWHUnit + otherLocalTranUnit + custodyOrderUnit;
			
			
			float otherVatCostUnit = othersCostUnit * ivaSiteProduct;
			
			logger.debug("vatImportSetup: "+vatImportSetup);
			logger.debug("[[[othersCostUnit]]]: "+othersCostUnit);
			logger.debug("otherVatCostUnit: "+otherVatCostUnit);
			
			
//			float othersCostUnit = otherOperativeActUnit + otherTemporalWHUnit + otherLocalTranUnit + custodyOrderUnit;
			
			
			/*Registro en base de datos de costos OTHERS*/
			String costEvalQuery = "SELECT 1 FROM CCS_UNITS_COST WHERE IMU_COD_PRODUCT = '"+localCodeProduct+"' AND STG_ID = "+idStage+" AND UNC_COST_COD = 'OTHER-COST-OPERATIVE-ACT' ";
			Statement stmtCostEvalQuery = conn.createStatement();
			ResultSet rsCostEvalQuery = stmtCostEvalQuery.executeQuery(costEvalQuery);
			if (rsCostEvalQuery.next()){
				costEvalQuery = "UPDATE CCS_UNITS_COST SET UNC_COST_AMOUNT = "+otherOperativeActUnit+" WHERE IMU_COD_PRODUCT = '"+localCodeProduct+"' AND STG_ID = "+idStage+" AND UNC_COST_COD = 'OTHER-COST-OPERATIVE-ACT'";
				stmtCostEvalQuery.executeUpdate(costEvalQuery);
			}else{
				costEvalQuery = "INSERT INTO CCS_UNITS_COST (UNC_COST_TYPE, UNC_COST_SUBTYPE, UNC_COST_NAME, UNC_COST_AMOUNT, IMU_COD_PRODUCT, STG_ID, UNC_COST_COD) VALUES('FREIGHTS & OTHERS', 'LOCAL EXPENSES', 'Custom Clearense',"+otherOperativeActUnit+", '"+localCodeProduct+"',  "+idStage+",  'OTHER-COST-OPERATIVE-ACT' )";
				stmtCostEvalQuery.executeUpdate(costEvalQuery);
			}
			rsCostEvalQuery.close();
			stmtCostEvalQuery.close();	

			
			costEvalQuery = "SELECT 1 FROM CCS_UNITS_COST WHERE IMU_COD_PRODUCT = '"+localCodeProduct+"' AND STG_ID = "+idStage+" AND UNC_COST_COD = 'OTHER-COST-WAREHOUSE' ";
			stmtCostEvalQuery = conn.createStatement();
			rsCostEvalQuery = stmtCostEvalQuery.executeQuery(costEvalQuery);
			if (rsCostEvalQuery.next()){
				costEvalQuery = "UPDATE CCS_UNITS_COST SET UNC_COST_AMOUNT = "+otherTemporalWHUnit+" WHERE IMU_COD_PRODUCT = '"+localCodeProduct+"' AND STG_ID = "+idStage+" AND UNC_COST_COD = 'OTHER-COST-WAREHOUSE'";
				stmtCostEvalQuery.executeUpdate(costEvalQuery);
			}else{
				costEvalQuery = "INSERT INTO CCS_UNITS_COST (UNC_COST_TYPE, UNC_COST_SUBTYPE, UNC_COST_NAME, UNC_COST_AMOUNT, IMU_COD_PRODUCT, STG_ID, UNC_COST_COD) VALUES('FREIGHTS & OTHERS', 'LOCAL EXPENSES', 'Temporal Warehousing',"+otherTemporalWHUnit+", '"+localCodeProduct+"',  "+idStage+",  'OTHER-COST-WAREHOUSE' )";
				stmtCostEvalQuery.executeUpdate(costEvalQuery);
			}
			rsCostEvalQuery.close();
			stmtCostEvalQuery.close();
			
			
			costEvalQuery = "SELECT 1 FROM CCS_UNITS_COST WHERE IMU_COD_PRODUCT = '"+localCodeProduct+"' AND STG_ID = "+idStage+" AND UNC_COST_COD = 'OTHER-COST-LOCAL-TRANSPORT' ";
			stmtCostEvalQuery = conn.createStatement();
			rsCostEvalQuery = stmtCostEvalQuery.executeQuery(costEvalQuery);
			if (rsCostEvalQuery.next()){
				costEvalQuery = "UPDATE CCS_UNITS_COST SET UNC_COST_AMOUNT = "+otherLocalTranUnit+" WHERE IMU_COD_PRODUCT = '"+localCodeProduct+"' AND STG_ID = "+idStage+" AND UNC_COST_COD = 'OTHER-COST-LOCAL-TRANSPORT'";
				stmtCostEvalQuery.executeUpdate(costEvalQuery);
			}else{
				costEvalQuery = "INSERT INTO CCS_UNITS_COST (UNC_COST_TYPE, UNC_COST_SUBTYPE, UNC_COST_NAME, UNC_COST_AMOUNT, IMU_COD_PRODUCT, STG_ID, UNC_COST_COD) VALUES('FREIGHTS & OTHERS', 'LOCAL EXPENSES', 'Local Transport',"+otherLocalTranUnit+", '"+localCodeProduct+"',  "+idStage+",  'OTHER-COST-LOCAL-TRANSPORT' )";
				stmtCostEvalQuery.executeUpdate(costEvalQuery);
			}
			rsCostEvalQuery.close();
			stmtCostEvalQuery.close();	
			
			
			costEvalQuery = "SELECT 1 FROM CCS_UNITS_COST WHERE IMU_COD_PRODUCT = '"+localCodeProduct+"' AND STG_ID = "+idStage+" AND UNC_COST_COD = 'OTHER-COST-CUSTODY' ";
			stmtCostEvalQuery = conn.createStatement();
			rsCostEvalQuery = stmtCostEvalQuery.executeQuery(costEvalQuery);
			if (rsCostEvalQuery.next()){
				costEvalQuery = "UPDATE CCS_UNITS_COST SET UNC_COST_AMOUNT = "+custodyOrderUnit+" WHERE IMU_COD_PRODUCT = '"+localCodeProduct+"' AND STG_ID = "+idStage+" AND UNC_COST_COD = 'OTHER-COST-CUSTODY'";
				stmtCostEvalQuery.executeUpdate(costEvalQuery);
			}else{
				costEvalQuery = "INSERT INTO CCS_UNITS_COST (UNC_COST_TYPE, UNC_COST_SUBTYPE, UNC_COST_NAME, UNC_COST_AMOUNT, IMU_COD_PRODUCT, STG_ID, UNC_COST_COD) VALUES('FREIGHTS & OTHERS', 'LOCAL EXPENSES', 'Security',"+custodyOrderUnit+", '"+localCodeProduct+"',  "+idStage+",  'OTHER-COST-CUSTODY' )";
				stmtCostEvalQuery.executeUpdate(costEvalQuery);
			}
			rsCostEvalQuery.close();
			stmtCostEvalQuery.close();
			
			
			costEvalQuery = "SELECT 1 FROM CCS_UNITS_COST WHERE IMU_COD_PRODUCT = '"+localCodeProduct+"' AND STG_ID = "+idStage+" AND UNC_COST_COD = 'OTHER-COST-VAT-IMPORT' ";
			stmtCostEvalQuery = conn.createStatement();
			rsCostEvalQuery = stmtCostEvalQuery.executeQuery(costEvalQuery);
			if (rsCostEvalQuery.next()){
				costEvalQuery = "UPDATE CCS_UNITS_COST SET UNC_COST_AMOUNT = "+otherVatCostUnit+" WHERE IMU_COD_PRODUCT = '"+localCodeProduct+"' AND STG_ID = "+idStage+" AND UNC_COST_COD = 'OTHER-COST-VAT-IMPORT'";
				stmtCostEvalQuery.executeUpdate(costEvalQuery);
			}else{
				costEvalQuery = "INSERT INTO CCS_UNITS_COST (UNC_COST_TYPE, UNC_COST_SUBTYPE, UNC_COST_NAME, UNC_COST_AMOUNT, IMU_COD_PRODUCT, STG_ID, UNC_COST_COD) VALUES('FREIGHTS & OTHERS', 'LOCAL EXPENSES', 'VAT Local Expenses',"+otherVatCostUnit+", '"+localCodeProduct+"',  "+idStage+",  'OTHER-COST-VAT-IMPORT' )";
				stmtCostEvalQuery.executeUpdate(costEvalQuery);
			}
			rsCostEvalQuery.close();
			stmtCostEvalQuery.close();	
			
			
			
			/*Registro en base de datos de costos FREIGHTS*/
			costEvalQuery = "SELECT 1 FROM CCS_UNITS_COST WHERE IMU_COD_PRODUCT = '"+localCodeProduct+"' AND STG_ID = "+idStage+" AND UNC_COST_COD = 'FREIGHT-COST-BASIC' ";
			stmtCostEvalQuery = conn.createStatement();
			rsCostEvalQuery = stmtCostEvalQuery.executeQuery(costEvalQuery);
			if (rsCostEvalQuery.next()){
				costEvalQuery = "UPDATE CCS_UNITS_COST SET UNC_COST_AMOUNT = "+costUnitFreigthStage+" WHERE IMU_COD_PRODUCT = '"+localCodeProduct+"' AND STG_ID = "+idStage+" AND UNC_COST_COD = 'FREIGHT-COST-BASIC'";
				stmtCostEvalQuery.executeUpdate(costEvalQuery);
			}else{
				costEvalQuery = "INSERT INTO CCS_UNITS_COST (UNC_COST_TYPE, UNC_COST_SUBTYPE, UNC_COST_NAME, UNC_COST_AMOUNT, IMU_COD_PRODUCT, STG_ID, UNC_COST_COD) VALUES('FREIGHTS & OTHERS', 'INTERNATIONAL FREIGHT', 'Freight',"+costUnitFreigthStage+", '"+localCodeProduct+"',  "+idStage+",  'FREIGHT-COST-BASIC' )";
				stmtCostEvalQuery.executeUpdate(costEvalQuery);
			}
			rsCostEvalQuery.close();
			stmtCostEvalQuery.close();
			
			
			costEvalQuery = "SELECT 1 FROM CCS_UNITS_COST WHERE IMU_COD_PRODUCT = '"+localCodeProduct+"' AND STG_ID = "+idStage+" AND UNC_COST_COD = 'FREIGHT-COST-FSCS' ";
			stmtCostEvalQuery = conn.createStatement();
			rsCostEvalQuery = stmtCostEvalQuery.executeQuery(costEvalQuery);
			if (rsCostEvalQuery.next()){
				costEvalQuery = "UPDATE CCS_UNITS_COST SET UNC_COST_AMOUNT = "+costUnitFSCStage+" WHERE IMU_COD_PRODUCT = '"+localCodeProduct+"' AND STG_ID = "+idStage+" AND UNC_COST_COD = 'FREIGHT-COST-FSCS'";
				stmtCostEvalQuery.executeUpdate(costEvalQuery);
			}else{
				costEvalQuery = "INSERT INTO CCS_UNITS_COST (UNC_COST_TYPE, UNC_COST_SUBTYPE, UNC_COST_NAME, UNC_COST_AMOUNT, IMU_COD_PRODUCT, STG_ID, UNC_COST_COD) VALUES('FREIGHTS & OTHERS','INTERNATIONAL FREIGHT', 'FSCS Cost', "+costUnitFSCStage+", '"+localCodeProduct+"',  "+idStage+",  'FREIGHT-COST-FSCS' )";
				stmtCostEvalQuery.executeUpdate(costEvalQuery);
			}
			rsCostEvalQuery.close();
			stmtCostEvalQuery.close();
			
			
			costEvalQuery = "SELECT 1 FROM CCS_UNITS_COST WHERE IMU_COD_PRODUCT = '"+localCodeProduct+"' AND STG_ID = "+idStage+" AND UNC_COST_COD = 'FREIGHT-COST-SCCS' ";
			stmtCostEvalQuery = conn.createStatement();
			rsCostEvalQuery = stmtCostEvalQuery.executeQuery(costEvalQuery);
			if (rsCostEvalQuery.next()){
				costEvalQuery = "UPDATE CCS_UNITS_COST SET UNC_COST_AMOUNT = "+costUnitSCCStage+" WHERE IMU_COD_PRODUCT = '"+localCodeProduct+"' AND STG_ID = "+idStage+" AND UNC_COST_COD = 'FREIGHT-COST-SCCS'";
				stmtCostEvalQuery.executeUpdate(costEvalQuery);
			}else{
				costEvalQuery = "INSERT INTO CCS_UNITS_COST (UNC_COST_TYPE, UNC_COST_SUBTYPE, UNC_COST_NAME, UNC_COST_AMOUNT, IMU_COD_PRODUCT, STG_ID, UNC_COST_COD) VALUES('FREIGHTS & OTHERS', 'INTERNATIONAL FREIGHT', 'SCCS Cost', "+costUnitSCCStage+", '"+localCodeProduct+"',  "+idStage+",  'FREIGHT-COST-SCCS' )";
				stmtCostEvalQuery.executeUpdate(costEvalQuery);
			}
			rsCostEvalQuery.close();
			stmtCostEvalQuery.close();
			
			
			costEvalQuery = "SELECT 1 FROM CCS_UNITS_COST WHERE IMU_COD_PRODUCT = '"+localCodeProduct+"' AND STG_ID = "+idStage+" AND UNC_COST_COD = 'FREIGHT-COST-AWB' ";
			stmtCostEvalQuery = conn.createStatement();
			rsCostEvalQuery = stmtCostEvalQuery.executeQuery(costEvalQuery);
			if (rsCostEvalQuery.next()){
				costEvalQuery = "UPDATE CCS_UNITS_COST SET UNC_COST_AMOUNT = "+costUnitAWBtage+" WHERE IMU_COD_PRODUCT = '"+localCodeProduct+"' AND STG_ID = "+idStage+" AND UNC_COST_COD = 'FREIGHT-COST-AWB'";
				stmtCostEvalQuery.executeUpdate(costEvalQuery);
			}else{
				costEvalQuery = "INSERT INTO CCS_UNITS_COST (UNC_COST_TYPE, UNC_COST_SUBTYPE, UNC_COST_NAME, UNC_COST_AMOUNT, IMU_COD_PRODUCT, STG_ID, UNC_COST_COD) VALUES('FREIGHTS & OTHERS', 'INTERNATIONAL FREIGHT', 'AWB Cost', "+costUnitAWBtage+", '"+localCodeProduct+"',  "+idStage+",  'FREIGHT-COST-AWB' )";
				stmtCostEvalQuery.executeUpdate(costEvalQuery);
			}
			rsCostEvalQuery.close();
			stmtCostEvalQuery.close();
			
			
			costEvalQuery = "SELECT 1 FROM CCS_UNITS_COST WHERE IMU_COD_PRODUCT = '"+localCodeProduct+"' AND STG_ID = "+idStage+" AND UNC_COST_COD = 'FREIGHT-COST-FFW-FEE' ";
			stmtCostEvalQuery = conn.createStatement();
			rsCostEvalQuery = stmtCostEvalQuery.executeQuery(costEvalQuery);
			if (rsCostEvalQuery.next()){
				costEvalQuery = "UPDATE CCS_UNITS_COST SET UNC_COST_AMOUNT = "+costUnitFFWFeedtage+" WHERE IMU_COD_PRODUCT = '"+localCodeProduct+"' AND STG_ID = "+idStage+" AND UNC_COST_COD = 'FREIGHT-COST-FFW-FEE'";
				
				stmtCostEvalQuery.executeUpdate(costEvalQuery);
			}else{
				costEvalQuery = "INSERT INTO CCS_UNITS_COST (UNC_COST_TYPE, UNC_COST_SUBTYPE, UNC_COST_NAME, UNC_COST_AMOUNT, IMU_COD_PRODUCT, STG_ID, UNC_COST_COD) VALUES('FREIGHTS & OTHERS', 'INTERNATIONAL FREIGHT', 'Other FFW Fees ', "+costUnitFFWFeedtage+", '"+localCodeProduct+"',  "+idStage+",  'FREIGHT-COST-FFW-FEE' )";
				stmtCostEvalQuery.executeUpdate(costEvalQuery);
			}
			rsCostEvalQuery.close();
			stmtCostEvalQuery.close();
			
			
			
			
			
			
			/*Registro en base de datos de costos DUTIES*/
			costEvalQuery = "SELECT 1 FROM CCS_UNITS_COST WHERE IMU_COD_PRODUCT = '"+localCodeProduct+"' AND STG_ID = "+idStage+" AND UNC_COST_COD = 'DUTY-COST-FIXED' ";
			stmtCostEvalQuery = conn.createStatement();
			rsCostEvalQuery = stmtCostEvalQuery.executeQuery(costEvalQuery);
			if (rsCostEvalQuery.next()){
				costEvalQuery = "UPDATE CCS_UNITS_COST SET UNC_COST_AMOUNT = "+dutyNominalTariffSetup+" WHERE IMU_COD_PRODUCT = '"+localCodeProduct+"' AND STG_ID = "+idStage+" AND UNC_COST_COD = 'DUTY-COST-FIXED'";
				
				logger.debug("costEvalQuery: "+costEvalQuery);
				stmtCostEvalQuery.executeUpdate(costEvalQuery);
			}else{
				costEvalQuery = "INSERT INTO CCS_UNITS_COST (UNC_COST_TYPE, UNC_COST_SUBTYPE, UNC_COST_NAME, UNC_COST_AMOUNT, IMU_COD_PRODUCT, STG_ID, UNC_COST_COD) VALUES('DUTIES', 'DUTIES TAXES', 'Duties',"+dutyNominalTariffSetup+", '"+localCodeProduct+"',  "+idStage+",  'DUTY-COST-FIXED' )";
				stmtCostEvalQuery.executeUpdate(costEvalQuery);
			}
			rsCostEvalQuery.close();
			stmtCostEvalQuery.close();
			
			
			costEvalQuery = "SELECT 1 FROM CCS_UNITS_COST WHERE IMU_COD_PRODUCT = '"+localCodeProduct+"' AND STG_ID = "+idStage+" AND UNC_COST_COD = 'DUTY-COST-OTHER' ";
			stmtCostEvalQuery = conn.createStatement();
			rsCostEvalQuery = stmtCostEvalQuery.executeQuery(costEvalQuery);
			if (rsCostEvalQuery.next()){
				costEvalQuery = "UPDATE CCS_UNITS_COST SET UNC_COST_AMOUNT = "+dutyNominalOtherTaxes+" WHERE IMU_COD_PRODUCT = '"+localCodeProduct+"' AND STG_ID = "+idStage+" AND UNC_COST_COD = 'DUTY-COST-OTHER'";
				stmtCostEvalQuery.executeUpdate(costEvalQuery);
			}else{
				costEvalQuery = "INSERT INTO CCS_UNITS_COST (UNC_COST_TYPE, UNC_COST_SUBTYPE, UNC_COST_NAME, UNC_COST_AMOUNT, IMU_COD_PRODUCT, STG_ID, UNC_COST_COD) VALUES('DUTIES', 'DUTIES TAXES', 'Other Taxes 1',"+dutyNominalOtherTaxes+", '"+localCodeProduct+"',  "+idStage+",  'DUTY-COST-OTHER' )";
				stmtCostEvalQuery.executeUpdate(costEvalQuery);
			}
			rsCostEvalQuery.close();
			stmtCostEvalQuery.close();
			
			
			costEvalQuery = "SELECT 1 FROM CCS_UNITS_COST WHERE IMU_COD_PRODUCT = '"+localCodeProduct+"' AND STG_ID = "+idStage+" AND UNC_COST_COD = 'DUTY-COST-VAT' ";
			stmtCostEvalQuery = conn.createStatement();
			rsCostEvalQuery = stmtCostEvalQuery.executeQuery(costEvalQuery);
			if (rsCostEvalQuery.next()){
				costEvalQuery = "UPDATE CCS_UNITS_COST SET UNC_COST_AMOUNT = "+vatNominalTaxesProduct+" WHERE IMU_COD_PRODUCT = '"+localCodeProduct+"' AND STG_ID = "+idStage+" AND UNC_COST_COD = 'DUTY-COST-VAT'";
				stmtCostEvalQuery.executeUpdate(costEvalQuery);
			}else{
				costEvalQuery = "INSERT INTO CCS_UNITS_COST (UNC_COST_TYPE, UNC_COST_SUBTYPE, UNC_COST_NAME, UNC_COST_AMOUNT, IMU_COD_PRODUCT, STG_ID, UNC_COST_COD) VALUES('DUTIES', 'DUTIES TAXES', 'VAT Import',"+vatNominalTaxesProduct+", '"+localCodeProduct+"',  "+idStage+",  'DUTY-COST-VAT' )";
				stmtCostEvalQuery.executeUpdate(costEvalQuery);
			}
			rsCostEvalQuery.close();
			stmtCostEvalQuery.close();
			
			
			costEvalQuery = "SELECT 1 FROM CCS_UNITS_COST WHERE IMU_COD_PRODUCT = '"+localCodeProduct+"' AND STG_ID = "+idStage+" AND UNC_COST_COD = 'DUTY-COST-CAPITAL' ";
			stmtCostEvalQuery = conn.createStatement();
			rsCostEvalQuery = stmtCostEvalQuery.executeQuery(costEvalQuery);
			if (rsCostEvalQuery.next()){
				costEvalQuery = "UPDATE CCS_UNITS_COST SET UNC_COST_AMOUNT = "+otherNominalTaxesCapital+" WHERE IMU_COD_PRODUCT = '"+localCodeProduct+"' AND STG_ID = "+idStage+" AND UNC_COST_COD = 'DUTY-COST-CAPITAL'";
				stmtCostEvalQuery.executeUpdate(costEvalQuery);
			}else{
				costEvalQuery = "INSERT INTO CCS_UNITS_COST (UNC_COST_TYPE, UNC_COST_SUBTYPE, UNC_COST_NAME, UNC_COST_AMOUNT, IMU_COD_PRODUCT, STG_ID, UNC_COST_COD) VALUES('DUTIES', 'DUTIES TAXES', 'Foreign Money Transfer Tax',"+otherNominalTaxesCapital+", '"+localCodeProduct+"',  "+idStage+",  'DUTY-COST-CAPITAL' )";
				stmtCostEvalQuery.executeUpdate(costEvalQuery);
			}
			rsCostEvalQuery.close();
			stmtCostEvalQuery.close();
			
			
			costEvalQuery = "SELECT 1 FROM CCS_UNITS_COST WHERE IMU_COD_PRODUCT = '"+localCodeProduct+"' AND STG_ID = "+idStage+" AND UNC_COST_COD = 'DUTY-OTHER-TAXES2' ";
			stmtCostEvalQuery = conn.createStatement();
			rsCostEvalQuery = stmtCostEvalQuery.executeQuery(costEvalQuery);
			if (rsCostEvalQuery.next()){
				costEvalQuery = "UPDATE CCS_UNITS_COST SET UNC_COST_AMOUNT = "+otherNominalTaxesFODINFA+" WHERE IMU_COD_PRODUCT = '"+localCodeProduct+"' AND STG_ID = "+idStage+" AND UNC_COST_COD = 'DUTY-OTHER-TAXES2'";
				stmtCostEvalQuery.executeUpdate(costEvalQuery);
			}else{
				costEvalQuery = "INSERT INTO CCS_UNITS_COST (UNC_COST_TYPE, UNC_COST_SUBTYPE, UNC_COST_NAME, UNC_COST_AMOUNT, IMU_COD_PRODUCT, STG_ID, UNC_COST_COD) VALUES('DUTIES', 'DUTIES TAXES', 'Other Taxes 2',"+otherNominalTaxesFODINFA+", '"+localCodeProduct+"',  "+idStage+",  'DUTY-OTHER-TAXES2' )";
				stmtCostEvalQuery.executeUpdate(costEvalQuery);
			}
			rsCostEvalQuery.close();
			stmtCostEvalQuery.close();
			
		}
	}
	
	public void itemMasterUpdateAttributes(String idStage, String idProduct, String valueAttribute, String attributeToUpdate) throws DAOException{
		
		Connection conn = null;
		String updateAttribute = new String()
		;
		try {
			conn = dataConnection.getConnectionOracleMSD();
			
			
			if (attributeToUpdate.equals(ConstantsFinalObject.ITEM_MASTER_TYPE_PRODUCT))
				updateAttribute = "PTY_ID = "+valueAttribute;
			
			if (attributeToUpdate.equals(ConstantsFinalObject.ITEM_MASTER_FAMILY_PRODUCT))
				updateAttribute = "PFA_ID = "+valueAttribute;
			
			if (attributeToUpdate.equals(ConstantsFinalObject.ITEM_MASTER_UMEASURE_PRODUCT))
				updateAttribute = "PRM_ID = "+valueAttribute;
			
			if (attributeToUpdate.equals(ConstantsFinalObject.ITEM_MASTER_FOB_PRODUCT))
				updateAttribute = "LIM_FOB_COST = "+valueAttribute;
		
			if (attributeToUpdate.equals(ConstantsFinalObject.ITEM_MASTER_HIGH_P_PRODUCT))
				updateAttribute = "LIM_HIGH_PALETTE = "+valueAttribute;

			if (attributeToUpdate.equals(ConstantsFinalObject.ITEM_MASTER_LARGE_P_PRODUCT))
				updateAttribute = "LIM_LARGE_PALETTE = "+valueAttribute;
			
			if (attributeToUpdate.equals(ConstantsFinalObject.ITEM_MASTER_WIDE_P_PRODUCT))
				updateAttribute = "LIM_WIDE_PALETTE = "+valueAttribute;
			
			if (attributeToUpdate.equals(ConstantsFinalObject.ITEM_MASTER_WEIGHT_P_PRODUCT))
				updateAttribute = "LIM_WEIGTH_PALETTE = "+valueAttribute;

			
			if (attributeToUpdate.equals(ConstantsFinalObject.ITEM_MASTER_UNITS_P_PRODUCT))
				updateAttribute = "LIM_UNIT_BY_PALETTE = "+valueAttribute;
			
			if (attributeToUpdate.equals(ConstantsFinalObject.ITEM_MASTER_PRESEN_PRODUCT))
				updateAttribute = "PRP_ID = "+valueAttribute;		
			
			if (attributeToUpdate.equals(ConstantsFinalObject.ITEM_MASTER_TRADE_PRODUCT))
				updateAttribute = "PTR_ID = "+valueAttribute;
			
			if (attributeToUpdate.equals(ConstantsFinalObject.ITEM_MASTER_TYPE_L_PRODUCT))
				updateAttribute = "PTL_ID = "+valueAttribute;
			
			
			if (attributeToUpdate.equals(ConstantsFinalObject.ITEM_MASTER_CUSTOM_DUTIES))
				updateAttribute = "SDD_COD = '"+valueAttribute+"'";
			
			if (attributeToUpdate.equals(ConstantsFinalObject.ITEM_MASTER_LOCAL_ROUTES))
				updateAttribute = "SLR_ID = '"+valueAttribute+"'";
			
			if (attributeToUpdate.equals(ConstantsFinalObject.ITEM_MASTER_RATE_STORAGE))
				updateAttribute = "SSR_ID = '"+valueAttribute+"'";
			
			if (attributeToUpdate.equals(ConstantsFinalObject.ITEM_MASTER_SITE_PRODUCT))
				updateAttribute = "SIS_ID = '"+valueAttribute+"'";	
			
			if (attributeToUpdate.equals(ConstantsFinalObject.ITEM_MASTER_IS_IVA_PRODUCT))
				updateAttribute = "LIM_IS_IVA = '"+valueAttribute+"'";	
			
			
			String qeUpdateItemMaster = "UPDATE CCS_LOCAL_ITEM_MASTER SET " +
					updateAttribute +" " +			
					"WHERE IMU_COD_PRODUCT = '"+idProduct+"' AND STG_ID = "+idStage+"";
			
			logger.debug("qeUpdateItemMaster: "+qeUpdateItemMaster);
			
			
			//Se sobreescribe la query
//			if (attributeToUpdate.equals(ConstantsFinalObject.ITEM_MASTER_SITE_PRODUCT)){
//				qeUpdateItemMaster = "UPDATE CCS_SITE_SOURCE_TRANSPORT SET " +
//						"SIS_ID = "+valueAttribute+" "+			
//						"WHERE IMU_COD_PRODUCT = '"+idProduct+"' AND STG_ID = "+idStage+"";
//			}
			
			
//			if (attributeToUpdate.equals(ConstantsFinalObject.ITEM_MASTER_TRANS_PRODUCT)){
//				qeUpdateItemMaster = "UPDATE CCS_SITE_SOURCE_TRANSPORT SET " +
//						"TRA_ID = "+valueAttribute+" "+			
//						"WHERE IMU_COD_PRODUCT = '"+idProduct+"' AND STG_ID = "+idStage+"";
//			}
			
			conn.createStatement().executeUpdate(qeUpdateItemMaster);
				
			
			
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
	
	
	
	public List<FreightAirVO> initFreightAir(String idStage, String idSite) throws DAOException{
		
		Connection conn = null;
		
		List<FreightAirVO> listFreightAirVO = new ArrayList<FreightAirVO>();
		try {
			
			conn = dataConnection.getConnectionOracleMSD();
			
			
			String qeSelectProductsImportUnits = "SELECT SIS.SIS_NAME, SFE.SFE_ACRONYM, FAR.FAR_INCREASE, FAR.FAR_AWB_KG_RATES, FAR.FAR_OTHER_FFW_FEES, FAR.FAR_FUEL_SURCHARGE, " +
												 "FAR.FAR_SECURITY_CHARGE, FAR.SIS_ID, FAR.STG_ID, FAR.FAR_ID, SFE.SFE_ID, FAR.FAR_ORDERS, " +
												 "FAR.FAR_INCREASE_AWB, FAR.FAR_INCREASE_FFW, FAR.FAR_INCREASE_FSC, FAR.FAR_INCREASE_SCC  " +
												 "FROM CCS_STAGE_FREIGHT_AIR FAR, CCS_SITE_SOURCE SIS, CCS_STAGE_FREIGHT_EXCHANGE SFE " +
												 "WHERE FAR.SIS_ID = SIS.SIS_ID " +
												 "AND   FAR.STG_ID = SIS.STG_ID " +
												 "AND   SIS.STG_ID = "+idStage+" "+
												 "AND   SFE.SFE_ID = FAR.SFE_ID " +
												 "ORDER BY FAR.FAR_ID ";
			
			logger.debug("qeSelectProductsImportUnits: "+qeSelectProductsImportUnits);
			
			ResultSet rs = conn.createStatement().executeQuery(qeSelectProductsImportUnits);
			while (rs.next()){
				
				FreightAirVO freightAirVO = new FreightAirVO();
				
				freightAirVO.setFromSiteSourceFreight(rs.getString(1));
				freightAirVO.setCurrencyFreight(rs.getString(2));
				freightAirVO.setIngreaseFreight(rs.getFloat(3));
				freightAirVO.setAwbRatesFreight(rs.getFloat(4));
				freightAirVO.setOtherFFWFeesFreight(rs.getFloat(5));
				freightAirVO.setFuelSurchargeFreight(rs.getFloat(6));
				freightAirVO.setSecurityChargeFreight(rs.getFloat(7));
				freightAirVO.setIdSiteSourceFreight(rs.getString(8));
				freightAirVO.setIdStageFreight(rs.getString(9));
				freightAirVO.setIdFreight(rs.getString(10));
				freightAirVO.setIdCurrencyFreight(rs.getString(11));
				freightAirVO.setOrdersFreight(rs.getString(12));
				
				freightAirVO.setIngreaseAWBFreight(rs.getFloat(13));
				freightAirVO.setIngreaseFFWFreight(rs.getFloat(14));
				freightAirVO.setIngreaseFSCFreight(rs.getFloat(15));
				freightAirVO.setIngreaseSCCFreight(rs.getFloat(16));
				
				
//				List<FreighAirRangeVO> listFreighAirRangeVO = new ArrayList<FreighAirRangeVO>();
//				
//				String qeSelectFreightRanges = "SELECT FRR_ID, FRR_FROM, FRR_TO, FRR_RATE, SIS_ID, STG_ID FROM CCS_STAGE_FREIGHT_AIR_RANGE WHERE SIS_ID = "+freightAirVO.getIdSiteSourceFreight() + " AND STG_ID=" + idStage;
//				logger.debug("qeSelectFreightRanges: "+qeSelectFreightRanges);
//				ResultSet rsFreigthRanges = conn.createStatement().executeQuery(qeSelectFreightRanges);
//				while (rsFreigthRanges.next()){
//					FreighAirRangeVO freighAirRangeVO = new FreighAirRangeVO();
//					
//					freighAirRangeVO.setIdFreighAir(rsFreigthRanges.getString(1));
//					freighAirRangeVO.setDescriptionFreighAir(rsFreigthRanges.getString(2)+" - "+rsFreigthRanges.getString(3));
//					freighAirRangeVO.setFromFreighAir(rsFreigthRanges.getInt(2));
//					freighAirRangeVO.setToFreighAir(rsFreigthRanges.getInt(3));
//					freighAirRangeVO.setRateFreighAir(rsFreigthRanges.getFloat(4));
//					
//					listFreighAirRangeVO.add(freighAirRangeVO);
//				}
//				
//				freightAirVO.setListFreighAirRangeVO(listFreighAirRangeVO);
				
				listFreightAirVO.add(freightAirVO);
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
		
		return listFreightAirVO;
	}
	
	
	public List<GeneralComboVO> listSiteSources(String idStage) throws DAOException{
		
		Connection conn = null;
		List<GeneralComboVO> listGeneralComboVO = new ArrayList<GeneralComboVO>();
		try {
			conn = dataConnection.getConnectionOracleMSD();
			
			String queryExecute = "SELECT SIS_ID, SIS_NAME FROM CCS_SITE_SOURCE WHERE STG_ID = "+idStage+" AND TRA_ID IN (1,3) ";
			
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
	
	
//	public FreightOthersVO getFreightOthers(String idSiteSource, String idStage, String idModeTransport) throws DAOException{
//		
//		Connection conn = null;
//		
//		
//		logger.debug("getFreightOthers:   getFreightOthers");
//		
//		FreightOthersVO freightOthersVO = new FreightOthersVO();
//		try {
//			conn = dataConnection.getConnectionOracleMSD();
//			
//			String queryExecute = "SELECT FRO_CONTAINER_RFER, FRO_INTER_FREIGHT, FRO_OTHERS, FRO_DESTINATION_CHARGES, STG_ID, SIS_ID, CUR_ID, FRO_PALETTES_NUMBER FROM CCS_STAGE_FREIGHT_OTHER WHERE STG_ID = "+idStage+" AND SIS_ID = "+idSiteSource+"  ";
//			
//			logger.debug("queryExecute: "+queryExecute);
//			
//			ResultSet rs = conn.createStatement().executeQuery(queryExecute);
//			if (rs.next()){
//				freightOthersVO.setContainerRferFreight(rs.getFloat(1));
//				freightOthersVO.setInterFreight(rs.getFloat(2));
//				freightOthersVO.setOtherCostFreight(rs.getFloat(3));
//				freightOthersVO.setDestinationChargesFreight(rs.getFloat(4));
//				freightOthersVO.setIdStageFreight(rs.getString(5));
//				freightOthersVO.setIdSiteSourceFreight(rs.getString(6));
//				freightOthersVO.setIdCurrencyFreight(rs.getString(7));
//				freightOthersVO.setIdModeFreight("");//Se debe eliminar
//				freightOthersVO.setPaletteChargesFreight(rs.getFloat(8));
//			}
//			
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
//		return freightOthersVO;
//	}
	
	
	
	public List<SetupExchangeVO> getSetupExchangeVO(String idStage) throws DAOException{
		
		Connection conn = null;
		
		List<SetupExchangeVO> listSetupExchangeVO = new ArrayList<SetupExchangeVO>();
		try {
			conn = dataConnection.getConnectionOracleMSD();
			
			String queryExecute = "SELECT EXG.SFE_ID, EXG.SFE_NAME, EXG.SFE_RATE, EXG.SFE_NAME, EXG.STG_ID, EXG.SFE_ACRONYM FROM CCS_STAGE_FREIGHT_EXCHANGE EXG WHERE STG_ID = "+idStage+" AND EXG.SFE_ACRONYM <> 'USD' ORDER BY 1 ";
			
			logger.debug("queryExecute: "+queryExecute);
			
			ResultSet rs = conn.createStatement().executeQuery(queryExecute);
			while (rs.next()){
				
				SetupExchangeVO setupExchangeVO = new SetupExchangeVO();
				
				setupExchangeVO.setIdExchange(rs.getString(1));
				setupExchangeVO.setNameExchange(rs.getString(2));
				setupExchangeVO.setAmountExchange(rs.getFloat(3));
				setupExchangeVO.setDescriptionExchange(rs.getString(4));
				setupExchangeVO.setIdStageExchange(rs.getString(5));
				setupExchangeVO.setAcronymExchage(rs.getString(6));
			
				listSetupExchangeVO.add(setupExchangeVO);
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
		
		return listSetupExchangeVO;
	}
	
	
	public List<SetupMacroValVO> getSetupMacroValVO(String idStage) throws DAOException{
		
		Connection conn = null;
		
		List<SetupMacroValVO> listSetupMacroValVO = new ArrayList<SetupMacroValVO>();
		try {
			conn = dataConnection.getConnectionOracleMSD();
			
			String queryExecute = "SELECT SMV_ID, SMV_AMOUNT, SMV_NAME, STG_ID FROM CCS_SETUP_MACRO_VARS WHERE STG_ID = "+idStage+"";
			
			logger.debug("queryExecute: "+queryExecute);
			
			ResultSet rs = conn.createStatement().executeQuery(queryExecute);
			while (rs.next()){
				
				SetupMacroValVO setupMacroValVO = new SetupMacroValVO();
				
				setupMacroValVO.setIdMacroVar(rs.getString(1));
				setupMacroValVO.setAmountMacroVar(rs.getFloat(2));
				setupMacroValVO.setNameMacroVar(rs.getString(3));
				setupMacroValVO.setIdStageMacroVar(rs.getString(4));
				
				listSetupMacroValVO.add(setupMacroValVO);
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
		
		return listSetupMacroValVO;
	}
	
	
	public SetupCustomDutiesHeadVO initCustomDuties(String idStage) throws DAOException{
		
		Connection conn = null;
		
		SetupCustomDutiesHeadVO setupCustomDutiesHeadVO = new SetupCustomDutiesHeadVO();
		try {
			conn = dataConnection.getConnectionOracleMSD();
			
			String queryExecute = "SELECT SDH_ID, SDH_OTHER_TAXES_FODINFA, SDH_VAT_IMPORTS, SDH_OTHER_TAXES_IMP_CAP, STG_ID FROM CCS_SETUP_CUSTUM_DUTIES_HEAD WHERE STG_ID = "+idStage+"  ";
			
			ResultSet rs = conn.createStatement().executeQuery(queryExecute);
			if (rs.next()){
				
				setupCustomDutiesHeadVO.setIdDutyHead(rs.getString(1));
				setupCustomDutiesHeadVO.setOtherTaxesDutyHead(rs.getFloat(2));
				setupCustomDutiesHeadVO.setVatImportDutyHead(rs.getFloat(3));
				setupCustomDutiesHeadVO.setOtherTaxesImpDutyHead(rs.getFloat(4));
				setupCustomDutiesHeadVO.setIdStageDutyHead(rs.getString(5));
				
				
				queryExecute = "SELECT SDD_ID, SDD_DUTY_NAME, SDD_DUTY_TARIFF, SDD_OTHER_TAXES, SDD_COD FROM CCS_SETUP_CUSTUM_DUTIES_DETAIL WHERE STG_ID = "+idStage+" ORDER BY 1 ";
				List<SetupCustomDutiesDetailVO> listSetupCustomDutiesDetailVO = new ArrayList<SetupCustomDutiesDetailVO>();
				ResultSet rsDetail = conn.createStatement().executeQuery(queryExecute);
				while (rsDetail.next()){
					SetupCustomDutiesDetailVO setupCustomDutiesDetailVO = new SetupCustomDutiesDetailVO();
					
					setupCustomDutiesDetailVO.setIdDutyDetail(rsDetail.getString(1));
					setupCustomDutiesDetailVO.setNameDutyDetail(rsDetail.getString(2));
					setupCustomDutiesDetailVO.setTariffDutyDetail(rsDetail.getFloat(3));
					setupCustomDutiesDetailVO.setOtherTaxesDutyDetail(rsDetail.getFloat(4));
					setupCustomDutiesDetailVO.setCodeDuty(rsDetail.getString(5));
					
					
					listSetupCustomDutiesDetailVO.add(setupCustomDutiesDetailVO);
				}
				setupCustomDutiesHeadVO.setListSetupCustomDutiesDetailVO(listSetupCustomDutiesDetailVO);
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
		
		return setupCustomDutiesHeadVO;
	}
	
	
	public SetupOpConsolidationVO getSetupOperativeConsolidation(String idStage) throws DAOException{
		
		Connection conn = null;
		
		SetupOpConsolidationVO setupOpConsolidationVO = new SetupOpConsolidationVO();
		try {
			conn = dataConnection.getConnectionOracleMSD();
			
			String queryExecute = "SELECT CON.SOC_ID, CON.SOC_ORDERS, CON.STG_ID, CON.SIS_ID, SIS.SIS_NAME FROM CCS_SETUP_OPE_CONSOLIDATION CON, CCS_SITE_SOURCE SIS WHERE STG_ID = "+idStage+" AND SIS.SIS_ID = CON.SIS_ID";
			
			ResultSet rs = conn.createStatement().executeQuery(queryExecute);
			if (rs.next()){
				
				setupOpConsolidationVO.setIdConsolidation(rs.getString(1));
				setupOpConsolidationVO.setOrderConsolidation(rs.getInt(2));
				setupOpConsolidationVO.setIdStageConsolidation(rs.getString(3));
				setupOpConsolidationVO.setIdSiteSource(rs.getString(4));
				setupOpConsolidationVO.setNameSiteSource(rs.getString(5));
				
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
		return setupOpConsolidationVO;
	}
	
	

	public List<ImportUnitVO> getListImporUnits(String stgId) throws DAOException{
		
		List<ImportUnitVO> listImportUnit = new ArrayList<ImportUnitVO>();
		Connection conn = null;
		
		try {
			conn = dataConnection.getConnectionOracleMSD();
			
			String queryExecute = "SELECT IMU_JAN,  IMU_FEB,  IMU_MAR,  IMU_APR,  IMU_MAY,  IMU_JUN,  IMU_JUL,  IMU_AUG,  IMU_SEP,  IMU_OCT,  IMU_NOV,  IMU_DIC,  IMU_COD_PRODUCT,  IMU_DESRIPTION_PRODUCT,  IMU_REPLY, IMU_AJUSTED "
								+ "FROM CCS_IMPORT_UNITS ciu , CCS_STAGE cs "
								+ "WHERE ciu.STG_ID = cs.STG_ID "
								+ "and cs.STG_ID=" + stgId +" "
								+ "and IMU_STATE=1 " +
								"ORDER BY IMU_COD_PRODUCT";
			
			logger.debug("queryExecute: "+queryExecute);

			
			ResultSet rs = conn.createStatement().executeQuery(queryExecute);
			while (rs.next()){

				ImportUnitVO importUnitVO = new ImportUnitVO();

				importUnitVO.setJan(rs.getString(1));
                importUnitVO.setFeb(rs.getString(2));
                importUnitVO.setMar(rs.getString(3));
                importUnitVO.setApr(rs.getString(4));
                importUnitVO.setMay(rs.getString(5));
                importUnitVO.setJun(rs.getString(6));
                importUnitVO.setJul(rs.getString(7));
                importUnitVO.setAug(rs.getString(8));
                importUnitVO.setSep(rs.getString(9));
                importUnitVO.setOct(rs.getString(10));
                importUnitVO.setNov(rs.getString(11));
                importUnitVO.setDec(rs.getString(12));
                importUnitVO.setLocalCode(rs.getString(13));
                importUnitVO.setDescriptionProduct(rs.getString(14));
                importUnitVO.setTotal(calTotalMedia(importUnitVO,1));
//                importUnitVO.setAjusted(rs.getString(16).equals(calTotalMedia(importUnitVO,2))?calTotalMedia(importUnitVO,2):rs.getString(16));
                importUnitVO.setAjusted(rs.getString(16));
                importUnitVO.setMedia(calTotalMedia(importUnitVO,2));
                importUnitVO.setReplica(rs.getString(15));
                
                listImportUnit.add(importUnitVO);
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
		
		return listImportUnit;
	}
	
	
	private String calTotalMedia(ImportUnitVO importUnitVO, int calc){
		String value ="";
		
		
		if (calc == 1){
			value = String.valueOf(Integer.parseInt(importUnitVO.getJan()) + Integer.parseInt(importUnitVO.getFeb()) + Integer.parseInt(importUnitVO.getMar()) + Integer.parseInt(importUnitVO.getApr()) + Integer.parseInt(importUnitVO.getMay()) + Integer.parseInt(importUnitVO.getJun()) + Integer.parseInt(importUnitVO.getJul()) + Integer.parseInt(importUnitVO.getAug()) + Integer.parseInt(importUnitVO.getSep()) + Integer.parseInt(importUnitVO.getOct()) + Integer.parseInt(importUnitVO.getNov()) + Integer.parseInt(importUnitVO.getDec())); 
		}else{
			int cont = 0;
			if (Integer.parseInt(importUnitVO.getJan()) !=0)
				cont++;
			if (Integer.parseInt(importUnitVO.getFeb()) !=0)
					cont++;
			if (Integer.parseInt(importUnitVO.getMar()) !=0)
				cont++;
			if (Integer.parseInt(importUnitVO.getApr()) !=0)
				cont++;
			if (Integer.parseInt(importUnitVO.getMay()) !=0)
				cont++;
			if (Integer.parseInt(importUnitVO.getJun()) !=0)
				cont++;
			if (Integer.parseInt(importUnitVO.getJul()) !=0)
				cont++;
			if (Integer.parseInt(importUnitVO.getAug()) !=0)
				cont++;
			if (Integer.parseInt(importUnitVO.getSep()) !=0)
				cont++;
			if (Integer.parseInt(importUnitVO.getOct()) !=0)
				cont++;
			if (Integer.parseInt(importUnitVO.getNov()) !=0)
				cont++;
			if (Integer.parseInt(importUnitVO.getDec()) !=0)
				cont++;
			try{
				value = String.valueOf((Integer.parseInt(importUnitVO.getJan()) + Integer.parseInt(importUnitVO.getFeb()) + Integer.parseInt(importUnitVO.getMar()) + Integer.parseInt(importUnitVO.getApr()) + Integer.parseInt(importUnitVO.getMay()) + Integer.parseInt(importUnitVO.getJun()) + Integer.parseInt(importUnitVO.getJul()) + Integer.parseInt(importUnitVO.getAug()) + Integer.parseInt(importUnitVO.getSep()) + Integer.parseInt(importUnitVO.getOct()) + Integer.parseInt(importUnitVO.getNov()) + Integer.parseInt(importUnitVO.getDec()))/cont);
				
			}catch(Exception e){
				value = "0";
				//e.printStackTrace();
			}
		}

		return value;
		
	}
	
	private void updateImportUnits(Connection conn, String idStage) throws DAOException{
		
		try {
			
			String queryExecute = "SELECT IMU_JAN,  IMU_FEB,  IMU_MAR,  IMU_APR,  IMU_MAY,  IMU_JUN,  IMU_JUL,  IMU_AUG,  IMU_SEP,  IMU_OCT,  IMU_NOV,  IMU_DIC, IMU_COD_PRODUCT "
								+ "FROM CCS_IMPORT_UNITS ciu , CCS_STAGE cs "
								+ "WHERE ciu.STG_ID = cs.STG_ID "
								+ "and cs.STG_ID=" + idStage
								+ "and IMU_STATE=1";
			
			logger.debug("queryExecute: "+queryExecute);

			
			ResultSet rs = conn.createStatement().executeQuery(queryExecute);
			while (rs.next()){
				ImportUnitVO importUnitVO = new ImportUnitVO();

				importUnitVO.setJan(rs.getString(1));
                importUnitVO.setFeb(rs.getString(2));
                importUnitVO.setMar(rs.getString(3));
                importUnitVO.setApr(rs.getString(4));
                importUnitVO.setMay(rs.getString(5));
                importUnitVO.setJun(rs.getString(6));
                importUnitVO.setJul(rs.getString(7));
                importUnitVO.setAug(rs.getString(8));
                importUnitVO.setSep(rs.getString(9));
                importUnitVO.setOct(rs.getString(10));
                importUnitVO.setNov(rs.getString(11));
                importUnitVO.setDec(rs.getString(12));
                importUnitVO.setLocalCode(rs.getString(13));
                queryExecute = "UPDATE CCS_IMPORT_UNITS "
                		+ " SET IMU_AJUSTED =" + calTotalMedia(importUnitVO,2)
                		+ " WHERE IMU_COD_PRODUCT = '" + importUnitVO.getLocalCode()+"'"
                		+ " AND STG_ID = " + idStage;
	
                logger.debug(queryExecute);
        		Statement st = conn.createStatement();
        		
        		st.executeUpdate(queryExecute);
                
			}
		}catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(ConstantsFinalObject.EXCEPTION_DAO_GENERIC_ERR_COD, e);		
		}
	}

	public ImportUnitVO importUnitUpdateAttributes(String idStage, String idProduct, String valueAttribute, String attributeToUpdate) throws DAOException{
		
		Connection conn = null;
		String updateAttribute = new String();
		ImportUnitVO importUnitVO= null;
		try {
			conn = dataConnection.getConnectionOracleMSD();
			
			if (attributeToUpdate.equals(ConstantsFinalObject.IMPORT_UNITS_JAN))
				updateAttribute="IMU_JAN="+valueAttribute;
			
			if (attributeToUpdate.equals(ConstantsFinalObject.IMPORT_UNITS_FEB))
				updateAttribute="IMU_FEB="+valueAttribute;
			
			if (attributeToUpdate.equals(ConstantsFinalObject.IMPORT_UNITS_MAR))
				updateAttribute="IMU_MAR="+valueAttribute;
			
			if (attributeToUpdate.equals(ConstantsFinalObject.IMPORT_UNITS_APR))
				updateAttribute="IMU_APR="+valueAttribute;
			
			if (attributeToUpdate.equals(ConstantsFinalObject.IMPORT_UNITS_MAY))
				updateAttribute="IMU_MAY="+valueAttribute;
			
			if (attributeToUpdate.equals(ConstantsFinalObject.IMPORT_UNITS_JUN))
				updateAttribute="IMU_JUN="+valueAttribute;
			
			if (attributeToUpdate.equals(ConstantsFinalObject.IMPORT_UNITS_JUL))
				updateAttribute="IMU_JUL="+valueAttribute;
			
			if (attributeToUpdate.equals(ConstantsFinalObject.IMPORT_UNITS_AUG))
				updateAttribute="IMU_AUG="+valueAttribute;
			
			if (attributeToUpdate.equals(ConstantsFinalObject.IMPORT_UNITS_SEP))
				updateAttribute="IMU_SEP="+valueAttribute;
			
			if (attributeToUpdate.equals(ConstantsFinalObject.IMPORT_UNITS_OCT))
				updateAttribute="IMU_OCT="+valueAttribute;
			
			if (attributeToUpdate.equals(ConstantsFinalObject.IMPORT_UNITS_NOV))
				updateAttribute="IMU_NOV="+valueAttribute;
			
			if (attributeToUpdate.equals(ConstantsFinalObject.IMPORT_UNITS_DEC))
				updateAttribute="IMU_DIC="+valueAttribute;
			
			if (attributeToUpdate.equals(ConstantsFinalObject.IMPORT_UNITS_AJUSTED))
				updateAttribute="IMU_AJUSTED="+valueAttribute;

			if (attributeToUpdate.equals(ConstantsFinalObject.IMPORT_DESRIPTION_PRODUCT))
				updateAttribute="IMU_DESRIPTION_PRODUCT='"+valueAttribute + "' ";
			
			if (attributeToUpdate.equals(ConstantsFinalObject.IMPORT_UNITS_LOCAL_CODE)){
				
				String queryExecute = "SELECT 1 FROM CCS_IMPORT_UNITS WHERE IMU_COD_PRODUCT ='"+valueAttribute+"' AND STG_ID = "+idStage+" AND IMU_COD_PRODUCT <> "+idProduct+"";
				
				logger.debug("queryExecute: "+queryExecute);
				ResultSet rsCount = conn.createStatement().executeQuery(queryExecute);
				
				if(rsCount.next()){
				
					throw new DAOException("El producto ya existe ");
				}
				
				String updateImportUnits = "UPDATE CCS_LOCAL_ITEM_MASTER SET IMU_COD_PRODUCT ='"+valueAttribute+"' WHERE STG_ID = "+idStage+" AND IMU_COD_PRODUCT = '"+idProduct+"'";
				logger.debug("updateImportUnits: "+updateImportUnits);
				
				conn.createStatement().executeUpdate(updateImportUnits);
				
				updateAttribute="IMU_COD_PRODUCT='"+valueAttribute+"'";
			}
				
			
			String qeUpdateImportUnit = "UPDATE CCS_IMPORT_UNITS SET " +
					updateAttribute +" " +			
					"WHERE IMU_COD_PRODUCT = '"+idProduct+"' AND STG_ID = "+idStage+"";
						
			
			logger.debug("qeUpdateItemMaster: "+qeUpdateImportUnit);
			conn.createStatement().executeUpdate(qeUpdateImportUnit);
				
			String queryExecute = "SELECT IMU_JAN,  IMU_FEB,  IMU_MAR,  IMU_APR,  IMU_MAY,  IMU_JUN,  IMU_JUL,  IMU_AUG,  IMU_SEP,  IMU_OCT,  IMU_NOV,  IMU_DIC,  IMU_COD_PRODUCT,  IMU_DESRIPTION_PRODUCT,  ciu.STG_ID "
					+ "FROM CCS_IMPORT_UNITS ciu , CCS_STAGE cs "
					+ "WHERE ciu.STG_ID = cs.STG_ID "
					+ "and cs.STG_ID=" + idStage
					+ " and IMU_STATE=1"
					+ " and ciu.IMU_COD_PRODUCT='"+idProduct+"'";

			logger.debug("queryExecute: "+queryExecute);
			
			
			ResultSet rs = conn.createStatement().executeQuery(queryExecute);
			if (rs.next()){
			
				importUnitVO = new ImportUnitVO();
			
				importUnitVO.setJan(rs.getString(1));
			    importUnitVO.setFeb(rs.getString(2));
			    importUnitVO.setMar(rs.getString(3));
			    importUnitVO.setApr(rs.getString(4));
			    importUnitVO.setMay(rs.getString(5));
			    importUnitVO.setJun(rs.getString(6));
			    importUnitVO.setJul(rs.getString(7));
			    importUnitVO.setAug(rs.getString(8));
			    importUnitVO.setSep(rs.getString(9));
			    importUnitVO.setOct(rs.getString(10));
			    importUnitVO.setNov(rs.getString(11));
			    importUnitVO.setDec(rs.getString(12));
			    importUnitVO.setLocalCode(rs.getString(13));
			    importUnitVO.setDescriptionProduct(rs.getString(14));
			    importUnitVO.setTotal(calTotalMedia(importUnitVO,1));
			    importUnitVO.setAjusted(String.valueOf(Math.floor(Float.parseFloat(calTotalMedia(importUnitVO,2))*(-1) / 100) * -100));
			    importUnitVO.setMedia(calTotalMedia(importUnitVO,2));
			    
			}
			
			if (!attributeToUpdate.equals(ConstantsFinalObject.IMPORT_UNITS_AJUSTED)){
	
				qeUpdateImportUnit = "UPDATE CCS_IMPORT_UNITS SET "
									+ " IMU_AJUSTED= " + importUnitVO.getAjusted()			
									+ " WHERE IMU_COD_PRODUCT = '"+idProduct+"' AND STG_ID = "+idStage+"";
				conn.createStatement().executeUpdate(qeUpdateImportUnit);
	
				logger.debug("qeUpdateItemMaster: "+qeUpdateImportUnit);
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
		return importUnitVO;
	}
		
	
	public void updateFreightOther(String attributeToUpdate, String valueAttribute, String idOtherFreight) throws DAOException{

		Connection conn = null;
	
		try{
			conn = dataConnection.getConnectionOracleMSD();

			String updateAttribute = new String("");
			
			if (attributeToUpdate.equals(ConstantsFinalObject.FOT_ID_TANSPORT))
				updateAttribute="TRA_ID="+valueAttribute;
			
//			
			if (attributeToUpdate.equals(ConstantsFinalObject.FOT_ID_CURRENCY))
				updateAttribute="SFE_ID="+valueAttribute;
//			
			if (attributeToUpdate.equals(ConstantsFinalObject.FOT_PALETTE_FREIGHT))
				updateAttribute="FOT_PALETTES="+valueAttribute;

			if (attributeToUpdate.equals(ConstantsFinalObject.FOT_ORDERS_NUMBER))
				updateAttribute="FOT_ORDERS="+valueAttribute;
			
			if (attributeToUpdate.equals(ConstantsFinalObject.FOT_INCREASE))
				updateAttribute="FOT_INCREASE="+valueAttribute;
			
			String qeUpdateImportUnit = "UPDATE CCS_STAGE_FREIGHT_OTHERS SET " +
					updateAttribute +" " +			
					"WHERE FOT_ID = " + idOtherFreight;
			logger.debug("qeUpdateItemMaster: "+qeUpdateImportUnit);

			
			if (attributeToUpdate.equals(ConstantsFinalObject.FOT_NAME_TANSPORT)){
				qeUpdateImportUnit = "UPDATE CCS_SITE_SOURCE SET SIS_NAME = '"+valueAttribute+"' WHERE SIS_ID IN (SELECT SIS_ID FROM CCS_STAGE_FREIGHT_OTHERS WHERE FOT_ID = "+idOtherFreight+")";
				logger.debug("qeUpdateImportUnit: "+qeUpdateImportUnit);
			}
			
			conn.createStatement().executeUpdate(qeUpdateImportUnit);
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
	
	public void updateFreightAPO(String attributeToUpdate, String valueAttribute, String idApoFreight) throws DAOException{

		Connection conn = null;
	
		try{
			conn = dataConnection.getConnectionOracleMSD();

			String updateAttribute = new String("");
			
			if (attributeToUpdate.equals(ConstantsFinalObject.APO_INCREASE_FREIGHT))
				updateAttribute="APO_INCREASE="+valueAttribute;
			
			if (attributeToUpdate.equals(ConstantsFinalObject.APO_CURRENCY))
				updateAttribute="SFE_ID="+valueAttribute;
			
			if (attributeToUpdate.equals(ConstantsFinalObject.APO_AWB_RATE_FREIGHT))
				updateAttribute="APO_AWB_RATE="+valueAttribute;
			
			if (attributeToUpdate.equals(ConstantsFinalObject.APO_OTHER_FEE_FREIGHT))
				updateAttribute="APO_OTHER_FEE="+valueAttribute;
			
			if (attributeToUpdate.equals(ConstantsFinalObject.APO_FSC_FREIGHT))
				updateAttribute="APO_FUEL_SURCHARGE="+valueAttribute;
			
			if (attributeToUpdate.equals(ConstantsFinalObject.APO_SSC_FREIGHT))
				updateAttribute="APO_SECURITY_CHARGE="+valueAttribute;
			
			if (attributeToUpdate.equals(ConstantsFinalObject.APO_ORDERS_FREIGHT))
				updateAttribute="APO_ORDERS="+valueAttribute;
			
			if (attributeToUpdate.equals(ConstantsFinalObject.APO_PALETTES_FREIGHT))
				updateAttribute="APO_PALETTES="+valueAttribute;
			
			if (attributeToUpdate.equals(ConstantsFinalObject.INC_APO_AWB_RATE_FREIGHT))
				updateAttribute="APO_INCREASE_AWB="+valueAttribute;
			
			if (attributeToUpdate.equals(ConstantsFinalObject.INC_APO_OTHER_FEE_FREIGHT))
				updateAttribute="APO_INCREASE_FFW="+valueAttribute;
			
			if (attributeToUpdate.equals(ConstantsFinalObject.INC_APO_OTHER_FEE_FREIGHT))
				updateAttribute="APO_INCREASE_FFW="+valueAttribute;
			
			if (attributeToUpdate.equals(ConstantsFinalObject.INC_APO_FSC_FREIGHT))
				updateAttribute="APO_INCREASE_FSC="+valueAttribute;
			
			if (attributeToUpdate.equals(ConstantsFinalObject.INC_APO_SSC_FREIGHT))
				updateAttribute="APO_INCREASE_SCC="+valueAttribute;
			
			
			String qeUpdateImportUnit = "";
			if (attributeToUpdate.equals(ConstantsFinalObject.APO_NAME_FREIGHT)){
				qeUpdateImportUnit = "UPDATE CCS_SITE_SOURCE SET SIS_NAME = '"+valueAttribute+"' WHERE SIS_ID IN (SELECT SIS_ID FROM CCS_STAGE_FREIGHT_APO WHERE APO_ID = "+idApoFreight+")";
				logger.debug("qeUpdateImportUnit: "+qeUpdateImportUnit);
			}else{
				
				qeUpdateImportUnit = "UPDATE CCS_STAGE_FREIGHT_APO SET " +
						updateAttribute +" " +			
						"WHERE APO_ID = " + idApoFreight;
				logger.debug("qeUpdateItemMaster: "+qeUpdateImportUnit);
			}
			
			conn.createStatement().executeUpdate(qeUpdateImportUnit);
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
	
	
	public void updateFreightFly(String attributeToUpdate, String valueAttribute, String idStage, String siteSource, String idFreightAir) throws DAOException{

		Connection conn = null;
	
		try{
			conn = dataConnection.getConnectionOracleMSD();

			String updateAttribute = new String("");
			
			if (attributeToUpdate.equals(ConstantsFinalObject.FAR_ORDERS_NUMBER))
				updateAttribute="FAR_ORDERS="+valueAttribute;
			
			if (attributeToUpdate.equals(ConstantsFinalObject.FAR_INCREASE))
				updateAttribute="FAR_INCREASE="+valueAttribute;
			
			if (attributeToUpdate.equals(ConstantsFinalObject.FAR_AWB_KG_RATES))
				updateAttribute="FAR_AWB_KG_RATES="+valueAttribute;
			
			if (attributeToUpdate.equals(ConstantsFinalObject.FAR_OTHER_FFW_FEES))
				updateAttribute="FAR_OTHER_FFW_FEES="+valueAttribute;
			
			if (attributeToUpdate.equals(ConstantsFinalObject.FAR_FUEL_SURCHARGE))
				updateAttribute="FAR_FUEL_SURCHARGE="+valueAttribute;
			
			if (attributeToUpdate.equals(ConstantsFinalObject.FAR_SECURITY_CHARGE))
				updateAttribute="FAR_SECURITY_CHARGE="+valueAttribute;
			
			if (attributeToUpdate.equals(ConstantsFinalObject.FAR_CURRENCY_ID))
				updateAttribute="SFE_ID="+valueAttribute;
			
			if (attributeToUpdate.equals(ConstantsFinalObject.FAR_INCREASE_AWB))
				updateAttribute="FAR_INCREASE_AWB="+valueAttribute;
			
			if (attributeToUpdate.equals(ConstantsFinalObject.FAR_INCREASE_FFW))
				updateAttribute="FAR_INCREASE_FFW="+valueAttribute;
			
			if (attributeToUpdate.equals(ConstantsFinalObject.FAR_INCREASE_FSC))
				updateAttribute="FAR_INCREASE_FSC="+valueAttribute;
			
			if (attributeToUpdate.equals(ConstantsFinalObject.FAR_INCREASE_SCC))
				updateAttribute="FAR_INCREASE_SCC="+valueAttribute;
			
			
			
			
			
//			public static final String FAR_INCREASE_AWB					= "FAR_INCREASE_AWB";
//			public static final String FAR_INCREASE_FFW					= "FAR_INCREASE_FFW";
//			public static final String FAR_INCREASE_FSC					= "FAR_INCREASE_FSC";
//			public static final String FAR_INCREASE_SCC					= "FAR_INCREASE_SCC";
			
			
			
			String qeUpdateImportUnit = "UPDATE CCS_STAGE_FREIGHT_AIR SET " +
					updateAttribute +" " +			
					"WHERE STG_ID = " + idStage
				  + "  AND FAR_ID = " + idFreightAir;
			logger.debug("qeUpdateItemMaster: "+qeUpdateImportUnit);
			
			
			
			//Para otras actualizaciones
			if (attributeToUpdate.equals(ConstantsFinalObject.FAR_ROUTE_DESCRIPTION)){
				
				qeUpdateImportUnit = "UPDATE CCS_SITE_SOURCE SET SIS_NAME = '"+valueAttribute+"' WHERE SIS_ID = " + siteSource + " AND STG_ID = "+idStage;
				logger.debug("qeUpdateImportUnit: "+qeUpdateImportUnit);
			}
			
			conn.createStatement().executeUpdate(qeUpdateImportUnit);
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
	
	
	public void updateFreightFlyRange(String attributeToUpdate, String valueAttribute, String idRangeFreight) throws DAOException{

		Connection conn = null;
	
		try{
			conn = dataConnection.getConnectionOracleMSD();

			String updateAttribute = new String("");
			
			if (attributeToUpdate.equals(ConstantsFinalObject.FRR_FROM))
				updateAttribute="FRR_FROM="+valueAttribute;
			
			if (attributeToUpdate.equals(ConstantsFinalObject.FRR_TO))
				updateAttribute="FRR_TO="+valueAttribute;
			
			if (attributeToUpdate.equals(ConstantsFinalObject.FRR_RATE))
				updateAttribute="FRR_RATE="+valueAttribute;
			
			String qeUpdateImportUnit = "UPDATE CCS_STAGE_FREIGHT_AIR_RANGE SET " +
					updateAttribute +" " +			
					"WHERE FRR_ID = " + idRangeFreight;
			
			logger.debug("qeUpdateItemMaster: "+qeUpdateImportUnit);
			
			conn.createStatement().executeUpdate(qeUpdateImportUnit);

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
	
	public void updateSetupExchange(String valueAttribute, String attributeToUpdate, String idSetupExchange) throws DAOException{

		Connection conn = null;
	
		try{
			conn = dataConnection.getConnectionOracleMSD();
			
			String updateAttribute = new String("");
			
			if (attributeToUpdate.equals(ConstantsFinalObject.SFE_NAME))
				updateAttribute="SFE_NAME='"+valueAttribute+"'";
			
			if (attributeToUpdate.equals(ConstantsFinalObject.SFE_RATE))
				updateAttribute="SFE_RATE="+valueAttribute;
			
			if (attributeToUpdate.equals(ConstantsFinalObject.SFE_ACRONYM))
				updateAttribute="SFE_ACRONYM='"+valueAttribute +"'";
			

			String qeUpdateImportUnit = "UPDATE CCS_STAGE_FREIGHT_EXCHANGE SET "+
										updateAttribute +
									    " WHERE SFE_ID   = " + idSetupExchange;
			
			
			logger.debug("qeUpdateItemMaster: "+qeUpdateImportUnit);
			
			conn.createStatement().executeUpdate(qeUpdateImportUnit);
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
	
	public void updateSetupMacrovars(String valueAttribute, String idStage, String idSetupMacrovars) throws DAOException{

		Connection conn = null;
	
		try{
			conn = dataConnection.getConnectionOracleMSD();
			conn.setAutoCommit(false);
			
			String qeUpdateImportUnit = "UPDATE CCS_SETUP_MACRO_VARS "
									  + " SET SMV_AMOUNT=" + valueAttribute
									  +	" WHERE STG_ID = " + idStage
									  + "  AND SMV_ID = " + idSetupMacrovars;
			
			
			logger.debug("qeUpdateItemMaster: "+qeUpdateImportUnit);
			
			conn.createStatement().executeUpdate(qeUpdateImportUnit);

			qeUpdateImportUnit = "SELECT SMV_NAME "
					+ "			FROM CCS_SETUP_MACRO_VARS "
					+ "			WHERE STG_ID= " + idStage
					+ "			AND SMV_ID=" + idSetupMacrovars;

			ResultSet rs = conn.createStatement().executeQuery(qeUpdateImportUnit);
			
			if (rs.next()){
				if (rs.getString(1).equals("IVA")){
					qeUpdateImportUnit = "UPDATE CCS_SETUP_CUSTUM_DUTIES_HEAD SET "
										+" SDH_VAT_IMPORTS=" 	+ valueAttribute		
										+" WHERE STG_ID = " + idStage;

					conn.createStatement().executeUpdate(qeUpdateImportUnit);

				}
			}
			
			conn.commit();
		}catch (SQLException e) {
			
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
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
	
	public void updateSetupCustomDutiesDetail(String attributeToUpdate, String valueAttribute, String idStage, String idCustomsDetail) throws DAOException{

		Connection conn = null;
	
		try{
			conn = dataConnection.getConnectionOracleMSD();


			String updateAttribute = new String("");
			
			
			if (attributeToUpdate.equals(ConstantsFinalObject.SDD_DUTY_NAME))
				updateAttribute="SDD_DUTY_NAME='"+valueAttribute+"' ";

			if (attributeToUpdate.equals(ConstantsFinalObject.SDD_DUTY_TARIFF))
				updateAttribute="SDD_DUTY_TARIFF="+valueAttribute;
			
			if (attributeToUpdate.equals(ConstantsFinalObject.SDD_OTHER_TAXES))
				updateAttribute="SDD_OTHER_TAXES="+valueAttribute;
			
			if (attributeToUpdate.equals(ConstantsFinalObject.SDD_DUTY_CODE)){
				
				
				
				String query = "SELECT 1 FROM CCS_SETUP_CUSTUM_DUTIES_DETAIL WHERE STG_ID = "+idStage+" AND SDD_COD = '"+valueAttribute+"' AND SDD_COD <> (SELECT SDD_COD FROM CCS_SETUP_CUSTUM_DUTIES_DETAIL WHERE STG_ID = "+idStage+" AND SDD_ID = "+idCustomsDetail+")";
				logger.debug("qeUpdateItemMaster: "+query);
				ResultSet rs = conn.createStatement().executeQuery(query);
				
				if (rs.next())
					throw new DAOException(ConstantsFinalObject.E_DAO_EXIST_CODE_DUTY);
					

				query = "SELECT 1 FROM CCS_LOCAL_ITEM_MASTER WHERE STG_ID = "+idStage+" AND SDD_COD = (SELECT SDD_COD FROM CCS_SETUP_CUSTUM_DUTIES_DETAIL WHERE STG_ID = "+idStage+" AND SDD_ID = "+idCustomsDetail+" AND SDD_COD <> '"+valueAttribute+"' ) ";
				logger.debug("qeUpdateItemMaster: "+query);
				rs = conn.createStatement().executeQuery(query);
				
				if (rs.next())
					throw new DAOException(ConstantsFinalObject.E_DAO_EXIST_CODE_DUTY_IM);

				
				
				updateAttribute="SDD_COD='"+valueAttribute+"'";
			}
				
			
			String qeUpdateImportUnit = "UPDATE CCS_SETUP_CUSTUM_DUTIES_DETAIL SET " +
					updateAttribute +" " 			
									  +	" WHERE STG_ID = " + idStage
									  + "  AND SDD_ID = " + idCustomsDetail;
			
			logger.debug("qeUpdateItemMaster: "+qeUpdateImportUnit);
			
			conn.createStatement().executeUpdate(qeUpdateImportUnit);

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
	
	
	public void updateSetupCustomDutiesHead(String attributeToUpdate, String valueAttribute, String idStage) throws DAOException{

		Connection conn = null;
	
		try{
			conn = dataConnection.getConnectionOracleMSD();
			conn.setAutoCommit(false);

			String updateAttribute = new String("");
			
			if (attributeToUpdate.equals(ConstantsFinalObject.SDH_OTHER_TAXES_FODINFA))
				updateAttribute="SDH_OTHER_TAXES_FODINFA="+valueAttribute;
			
			if (attributeToUpdate.equals(ConstantsFinalObject.SDH_VAT_IMPORTS))
				updateAttribute="SDH_VAT_IMPORTS="+valueAttribute;
			
			if (attributeToUpdate.equals(ConstantsFinalObject.SDH_OTHER_TAXES_IMP_CAP))
				updateAttribute="SDH_OTHER_TAXES_IMP_CAP="+valueAttribute;
			
			String qeUpdateImportUnit = "UPDATE CCS_SETUP_CUSTUM_DUTIES_HEAD SET " +
					updateAttribute +" " 			
									  +	" WHERE STG_ID = " + idStage;
			
			logger.debug("qeUpdateItemMaster: "+qeUpdateImportUnit);
			
			conn.createStatement().executeUpdate(qeUpdateImportUnit);

			qeUpdateImportUnit = "SELECT SMV_ID, SMV_NAME "
					+ "			FROM CCS_SETUP_MACRO_VARS "
					+ "			WHERE STG_ID= " + idStage;

			ResultSet rs = conn.createStatement().executeQuery(qeUpdateImportUnit);
			
			while (rs.next()){
				if (rs.getString(2).equals("IVA")){
					qeUpdateImportUnit = "UPDATE CCS_SETUP_MACRO_VARS "
							  + " SET SMV_AMOUNT=" + valueAttribute
							  +	" WHERE STG_ID = " + idStage
							  + "  AND SMV_ID = " + rs.getString(1);
					
					logger.debug(qeUpdateImportUnit);
					
					conn.createStatement().executeUpdate(qeUpdateImportUnit);
				}
			}
			
			conn.commit();
			
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
	
	
	public List<OpeManageVO> getListOpeManage(String stgId) throws DAOException{
		
		List<OpeManageVO> listOpeManageVO = new ArrayList<OpeManageVO>();
		Connection conn = null;
		
		try {
			conn = dataConnection.getConnectionOracleMSD();
			
			String queryExecute = "SELECT SOM_ID, SOM_ENTRY, ROUND(SOM_PROCESSING_FEE, 3) SOM_PROCESSING_FEE, SOM_RATE_KG, CUR_ID, SOM_PERCENT_TP "
								+ "FROM CCS_SETUP_OPE_MANAGE "
								+ "WHERE STG_ID=" + stgId 
								+ "ORDER BY SOM_ID";
			
			logger.debug("queryExecute: "+queryExecute);

			
			ResultSet rs = conn.createStatement().executeQuery(queryExecute);
			while (rs.next()){

				OpeManageVO opeManageVO = new OpeManageVO();
				opeManageVO.setSomID(rs.getString(1));
				opeManageVO.setSomEntry(rs.getString(2));
				opeManageVO.setSomProcessingFee(rs.getString(3));
				opeManageVO.setSomRateKg(rs.getString(4));
				opeManageVO.setCurId(rs.getString(5));
				opeManageVO.setPercentEntry(rs.getString(6));
               
                listOpeManageVO.add(opeManageVO);
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
		
		return listOpeManageVO;
	}	
	
	
	public void updateSetupOpeMange(String attributeToUpdate, String valueAttribute, String idStage, String somID) throws DAOException{

		Connection conn = null;
//		String valor = "";
		try{
			conn = dataConnection.getConnectionOracleMSD();


			String updateAttribute = new String("");
			
			if (attributeToUpdate.equals(ConstantsFinalObject.SOM_PERCENT_TP))
				updateAttribute="SOM_PERCENT_TP="+valueAttribute+"";
			
			
			if (attributeToUpdate.equals(ConstantsFinalObject.SOM_PROCESSING_ENTRY))
				updateAttribute="SOM_ENTRY='"+valueAttribute+"'";
			
			
			if (attributeToUpdate.equals(ConstantsFinalObject.SOM_PROCESSING_FEE)){
				
				updateAttribute = "SOM_PROCESSING_FEE = "+valueAttribute+"";
				
				//Si la moneda almacenada en la base de datos no es dolar, entonces se transforma cantidad a dolar segï¿½n tasa de intercambio
//				String qMonedaActual = "select 1  from CCS_SETUP_OPE_MANAGE   WHERE STG_ID = " + idStage  + " AND SOM_ID = " + somID +" AND CUR_ID <> 1";
//				ResultSet rs =  conn.createStatement().executeQuery(qMonedaActual);
//				if (rs.next()){
//					updateAttribute = "SOM_PROCESSING_FEE = "+valueAttribute+" / (SELECT SMV_AMOUNT FROM CCS_SETUP_MACRO_VARS where SMV_COD = 'ERA' AND stg_id = "+idStage+")" ;
//				}else{
//					updateAttribute = "SOM_PROCESSING_FEE = "+valueAttribute+"";
//				}
				
			}
				
			
			if (attributeToUpdate.equals(ConstantsFinalObject.SOM_RATE_KG))
				updateAttribute="SOM_RATE_KG="+valueAttribute;
			
			if (attributeToUpdate.equals(ConstantsFinalObject.CUR_ID)){
				updateAttribute="CUR_ID="+valueAttribute;

				//Si la moneda enviada es distinto a dolar, entonces se transforma monto almacenado en la base por la tasa de intercambio.
//				if (!valueAttribute.equals("1")){
//					updateAttribute = updateAttribute + ", SOM_PROCESSING_FEE = SOM_PROCESSING_FEE / (SELECT SMV_AMOUNT FROM CCS_SETUP_MACRO_VARS where SMV_COD = 'ERA' AND stg_id = "+idStage+")" ;
//				}else{
//					updateAttribute = updateAttribute + ", SOM_PROCESSING_FEE = SOM_PROCESSING_FEE * (SELECT SMV_AMOUNT FROM CCS_SETUP_MACRO_VARS where SMV_COD = 'ERA' AND stg_id = "+idStage+")" ;
//				}
			}
			
			
			String qeUpdateImportUnit = "UPDATE CCS_SETUP_OPE_MANAGE SET " +
					updateAttribute +" " 			
					+	" WHERE STG_ID = " + idStage 
									  + " AND SOM_ID = " + somID;
			
			logger.debug("qeUpdateItemMaster: "+qeUpdateImportUnit);
			
			conn.createStatement().executeUpdate(qeUpdateImportUnit);

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
//		return valor;
	}
	
	public void updateSetupOpeConsolidation(String attributeToUpdate, String valueAttribute, String idStage, String siteSourceID) throws DAOException{

		Connection conn = null;
	
		try{
			conn = dataConnection.getConnectionOracleMSD();

			String qeUpdateImportUnit = "UPDATE CCS_SETUP_OPE_CONSOLIDATION SET SOC_ORDERS="+valueAttribute
									  +	" WHERE STG_ID = " + idStage 
									  + " AND SIS_ID = " + siteSourceID ;
			
			logger.debug("qeUpdateItemMaster: "+qeUpdateImportUnit);
			
			conn.createStatement().executeUpdate(qeUpdateImportUnit);

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
	
	public String getOpeConsolidationSocOrders(String idSiteSource, String idStage, String idMode) throws DAOException{
		
		Connection conn = null;
		
		
		logger.debug("getFreightOthers:   getFreightOthers");
		
		String socOrders = new String("");
		
		try {
			conn = dataConnection.getConnectionOracleMSD();
			
			String queryExecute = "SELECT SOC_ORDERS "
								+ "FROM CCS_SETUP_OPE_CONSOLIDATION "
								+ "WHERE STG_ID = "+idStage+" AND SIS_ID = "+idSiteSource;
			
			logger.debug("queryExecute: "+queryExecute);
			
			ResultSet rs = conn.createStatement().executeQuery(queryExecute);
			if (rs.next()){
				socOrders = rs.getString(1);
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
		
		return socOrders;
	}
	
	public void filtros(String idStage, String pfaID, String prpID, String ptyID, String ptlID, String ptrID, String sisID, String sddID, String filterProduct) throws DAOException{
		
		Connection conn = null;
		try {
			conn = dataConnection.getConnectionOracleMSD();
			
			String queryExecute="UPDATE CCS_IMPORT_UNITS "
							  + " SET IMU_STATE = 0 "
							  + " WHERE STG_ID=" + idStage
							  + " AND IMU_COD_PRODUCT not in(SELECT ciu.IMU_COD_PRODUCT "
							  + " FROM CCS_IMPORT_UNITS ciu , CCS_STAGE cs, CCS_LOCAL_ITEM_MASTER clim  "
							  + " WHERE ciu.STG_ID = cs.STG_ID "
							  + " and clim.imu_cod_product = ciu.imu_cod_product "
							  + " and clim.STG_ID = cs.STG_ID "
							  + " and cs.STG_ID=" + idStage
							  + " and IMU_STATE=1";
			
			//Product Family
			
			// TODO Al resetear los filtros debiera mostrar todos los productos, pero no lo hace, es necesario hacer refresh
			
			//existe, en BD pero no en interfaz
			if (!pfaID.equals("0"))
				queryExecute += " and PFA_ID="+ pfaID;
			
			//Presentation
			if (!prpID.equals("0"))
				queryExecute += " and PRP_ID="+ prpID;

			//Prodcut type
			
			//existe, en BD pero no en interfaz
			if (!ptyID.equals("0"))
				queryExecute += " and PTY_ID="+ ptyID;

			//Type Load
			//existe, en BD pero no en interfaz
			if (!ptlID.equals("0"))
				queryExecute += " and PTL_ID="+ ptlID;

			//Trade
			//existe, en BD pero no en interfaz
			if (!ptrID.equals("0"))
				queryExecute += " and PTR_ID="+ ptrID;
			
			//Transport
//			if (!traID.equals("0"))
//				queryExecute += " and TRA_ID="+ traID;

			//Site Source
			//existe, en BD pero no en interfaz
			if (!sisID.equals("0"))
				queryExecute += " and SIS_ID="+ sisID;

			//Custom Duty
			//existe, en BD pero no en interfaz
			if (!sddID.equals("0"))
				queryExecute += " and SDD_COD='"+ sddID +"'";

			if (filterProduct != null && !filterProduct.trim().equals(""))
				queryExecute += " and UPPER(ciu.IMU_DESRIPTION_PRODUCT) like UPPER('%"+ filterProduct.trim() +"%')";
			
			
			
			queryExecute += ")";
			
			logger.debug("queryExecuteFILTRO : "+queryExecute);
			
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
	
	
	public StageVO getStage(String idStage) throws DAOException{
		
		StageVO stageVO = new StageVO();
		Connection conn = null;
		try {
			conn = dataConnection.getConnectionOracleMSD();
			
			String queryExecute = "SELECT STG_ID, SGT_NAME, STG_DATE_CREATION, STG_DATE_LAST_UPDATE, STT_ID, STS_ID, SIT_ID, USR_ISID, STG_DESCRIPTION FROM CCS_STAGE WHERE STG_ID = "+idStage+"";
			
			logger.debug("queryExecute: "+queryExecute);
			
			ResultSet rs = conn.createStatement().executeQuery(queryExecute);
			if (rs.next()){
				stageVO.setIdStage(rs.getString(1));
				stageVO.setNameStage(rs.getString(2));
				stageVO.setIdSiteStage(rs.getString(7));
				stageVO.setDescriptionStage(rs.getString(9));
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
		
		return stageVO;
	}
	
	
	public List<TempStorageVO> getListTempStorage(String stgId) throws DAOException{
		
		List<TempStorageVO> listTempStorageVO = new ArrayList<TempStorageVO>();
		Connection conn = null;
		
		try {
			conn = dataConnection.getConnectionOracleMSD();
			
			String queryExecute = "SELECT SST_ID, SST_ENTRY, ROUND(SST_PROCESSING_FEE, 3) SST_PROCESSING_FEE, CUR_ID  "
								+ "FROM CCS_SETUP_STORAGE_TEMP "
								+ "WHERE STG_ID=" + stgId + " " +
								" ORDER BY SST_ID";
			logger.debug("queryExecute: "+queryExecute);

			
			ResultSet rs = conn.createStatement().executeQuery(queryExecute);
			while (rs.next()){

				TempStorageVO tempStorageVO = new TempStorageVO();
				tempStorageVO.setSstId(rs.getString(1));
				tempStorageVO.setEntry(rs.getString(2));
				tempStorageVO.setProcessingFee(rs.getString(3));
				tempStorageVO.setCurrencyID(rs.getString(4));
				
                listTempStorageVO.add(tempStorageVO);
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
		
		return listTempStorageVO;
	}	
	
	public String updateSetupTempStorage(String attributeToUpdate, String valueAttribute, String idStage, String sstId) throws DAOException{

		Connection conn = null;
		String valor="";
		try{
			conn = dataConnection.getConnectionOracleMSD();


			String updateAttribute = new String("");
			

			
//			if (attributeToUpdate.equals(ConstantsFinalObject.SOM_PROCESSING_FEE)){
//				
//				//Si la moneda almacenada en la base de datos no es dolar, entonces se transforma cantidad a dolar segï¿½n tasa de intercambio
//				String qMonedaActual = "select 1  from CCS_SETUP_OPE_MANAGE   WHERE STG_ID = " + idStage  + " AND SOM_ID = " + somID +" AND CUR_ID <> 1";
//				ResultSet rs =  conn.createStatement().executeQuery(qMonedaActual);
//				if (rs.next()){
//					updateAttribute = "SOM_PROCESSING_FEE = "+valueAttribute+" / (SELECT SMV_AMOUNT FROM CCS_SETUP_MACRO_VARS where SMV_COD = 'ERA' AND stg_id = "+idStage+")" ;
//				}else{
//					updateAttribute = "SOM_PROCESSING_FEE = "+valueAttribute+"";
//				}
//					
//			}
//				
//			
//			
//			if (attributeToUpdate.equals(ConstantsFinalObject.CUR_ID)){
//				updateAttribute="CUR_ID="+valueAttribute;
//
//				//Si la moneda enviada es distinto a dolar, entonces se transforma monto almacenado en la base por la tasa de intercambio.
//				if (!valueAttribute.equals("1")){
//					updateAttribute = updateAttribute + ", SOM_PROCESSING_FEE = SOM_PROCESSING_FEE / (SELECT SMV_AMOUNT FROM CCS_SETUP_MACRO_VARS where SMV_COD = 'ERA' AND stg_id = "+idStage+")" ;
//				}
//			}
			
			
			
			
			
			
			if (attributeToUpdate.equals(ConstantsFinalObject.SST_ENTRY))
				updateAttribute="SST_ENTRY='"+valueAttribute+"'";
			
//			if (attributeToUpdate.equals(ConstantsFinalObject.SST_PERCENT_COST))
//				updateAttribute="SST_PERCENT_COST="+valueAttribute;
			
			if (attributeToUpdate.equals(ConstantsFinalObject.SST_PROCESSING_FEE)){
				
				updateAttribute = "SST_PROCESSING_FEE = "+valueAttribute;
				
				//Si la moneda almacenada en la base de datos no es dolar, entonces se transforma cantidad a dolar segï¿½n tasa de intercambio
//				String qMonedaActual = "select 1  from CCS_SETUP_STORAGE_TEMP   WHERE STG_ID = " + idStage  + " AND SST_ID = " + sstId +" AND CUR_ID <> 1";
//				ResultSet rs =  conn.createStatement().executeQuery(qMonedaActual);
//				if (rs.next()){
//					updateAttribute = "SST_PROCESSING_FEE = "+valueAttribute+" / (SELECT SMV_AMOUNT FROM CCS_SETUP_MACRO_VARS where SMV_COD = 'ERA' AND stg_id = "+idStage+")" ;
//				}else{
//					updateAttribute = "SST_PROCESSING_FEE = "+valueAttribute;
//				}
				
			}
//				updateAttribute="SST_PROCESSING_FEE="+valueAttribute;
			
			if (attributeToUpdate.equals(ConstantsFinalObject.CUR_ID)){
				updateAttribute = "CUR_ID = "+valueAttribute;
	
				//Si la moneda enviada es distinto a dolar, entonces se transforma monto almacenado en la base por la tasa de intercambio.
//				if (!valueAttribute.equals("1")){
//					updateAttribute = updateAttribute + ", SST_PROCESSING_FEE = SST_PROCESSING_FEE / (SELECT SMV_AMOUNT FROM CCS_SETUP_MACRO_VARS where SMV_COD = 'ERA' AND stg_id = "+idStage+")" ;
//				}else{
//					updateAttribute = updateAttribute + ", SST_PROCESSING_FEE = SST_PROCESSING_FEE * (SELECT SMV_AMOUNT FROM CCS_SETUP_MACRO_VARS where SMV_COD = 'ERA' AND stg_id = "+idStage+")" ;
//				}
			}
			
			
//			if (attributeToUpdate.equals(ConstantsFinalObject.CUR_ID)){
//				updateAttribute="CUR_ID="+valueAttribute;
//
//				String qMonedaActual = "select cur_id "
//				  + "from CCS_SETUP_STORAGE_TEMP "
//				  +	" WHERE STG_ID = " + idStage 
//				  + " AND SST_ID = " + sstId;
//				logger.debug("qMonedaActual: "+qMonedaActual);
//				ResultSet rs =  conn.createStatement().executeQuery(qMonedaActual);
//				if (rs.next()){
//					if (rs.getString(1).equals("1")){
//						//multiplico
//						String qDolar = "SELECT ROUND(SST_PROCESSING_FEE * (SELECT CUR_EXCHANGE_DOLAR FROM CCS_CURRENCY WHERE CUR_ID="+valueAttribute+"),2), SST_PROCESSING_FEE * (SELECT CUR_EXCHANGE_DOLAR FROM CCS_CURRENCY WHERE CUR_ID="+valueAttribute+")  "
//								+ "FROM CCS_SETUP_STORAGE_TEMP OPM "
//								+ "WHERE OPM.STG_ID = " + idStage 
//								+ " AND OPM.SST_ID = " + sstId;
//						
//						
//						logger.debug("qDolar xqt: "+qDolar);
//						ResultSet rsTotal = conn.createStatement().executeQuery(qDolar); 
//						
//						if (rsTotal.next()){
//							logger.debug("dentro del if");
//							updateAttribute = updateAttribute + ", SST_PROCESSING_FEE="+rsTotal.getString(2);
//							valor = rsTotal.getString(1);
//						}else{
//							logger.debug("fuera del if ");
//						}
//						
//					}else{
//						//divido
//						String qDolar = "SELECT ROUND(SST_PROCESSING_FEE / (SELECT CUR_EXCHANGE_DOLAR FROM CCS_CURRENCY WHERE CUR_ID="+rs.getString(1)+"),2),SST_PROCESSING_FEE / (SELECT CUR_EXCHANGE_DOLAR FROM CCS_CURRENCY WHERE CUR_ID="+rs.getString(1)+")  "
//								+ "FROM CCS_SETUP_STORAGE_TEMP OPM "
//								+ "WHERE OPM.STG_ID = " + idStage 
//								+ " AND OPM.SST_ID = " + sstId;
//						
//						logger.debug("qDolar: "+qDolar);
//						ResultSet rsTotal = conn.createStatement().executeQuery(qDolar);
//						if (rsTotal.next()){
//							updateAttribute +=", SST_PROCESSING_FEE="+rsTotal.getString(2);
//							valor = rsTotal.getString(1);
//						}
//						
//					}
//				}
//			}
			
			
			String qeUpdateImportUnit = "UPDATE CCS_SETUP_STORAGE_TEMP SET " +
					updateAttribute +" " 			
									  +	" WHERE STG_ID = " + idStage 
									  + " AND SST_ID = " + sstId;
			
			logger.debug("qeUpdateItemMaster: "+qeUpdateImportUnit);
			
			conn.createStatement().executeUpdate(qeUpdateImportUnit);

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
		return valor;
	}
	
	
	
	public void updateRatesWareHouse(String attributeToUpdate, String valueAttribute, String idRateWarehouse) throws DAOException{

		Connection conn = null;
		try{
			conn = dataConnection.getConnectionOracleMSD();

			String updateAttribute = new String("");
			
			if (attributeToUpdate.equals(ConstantsFinalObject.RATE_WAREHOUSE_NAME))
				updateAttribute="SSR_NAME='"+valueAttribute+"'";
			
			if (attributeToUpdate.equals(ConstantsFinalObject.RATE_WAREHOUSE_RATE))
				updateAttribute = "SSR_RATE = "+valueAttribute;
			
			String query = "UPDATE CCS_SETUP_STORAGE_RATE SET " +
					updateAttribute +" " 			
									  +	" WHERE SSR_ID = " + idRateWarehouse;
			logger.debug("qeUpdateItemMaster: "+query);
			
			conn.createStatement().executeUpdate(query);

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
	
	public void updateRangesWareHouse(String attributeToUpdate, String valueAttribute, String idRangeWarehouse) throws DAOException{

		Connection conn = null;
		try{
			conn = dataConnection.getConnectionOracleMSD();

			
			String updateAttribute = new String("");
			
			if (attributeToUpdate.equals(ConstantsFinalObject.RANGE_WAREHOUSE_FROM))
				updateAttribute = "SSR_RANGE_FROM='"+valueAttribute+"'";
			
			if (attributeToUpdate.equals(ConstantsFinalObject.RANGE_WAREHOUSE_TO))
				updateAttribute = "SSR_RANGE_TO = "+valueAttribute;
			
			if (attributeToUpdate.equals(ConstantsFinalObject.RANGE_WAREHOUSE_RATE))
				updateAttribute = "SSR_RANGE_RATE = "+valueAttribute;
			
			if (attributeToUpdate.equals(ConstantsFinalObject.CUR_ID))
				updateAttribute = "CUR_ID = "+valueAttribute;
			
			String query = "UPDATE CCS_SETUP_STORAGE_RANGE SET " +
					updateAttribute +" " 			
					+	" WHERE SSR_ID = " + idRangeWarehouse;
			logger.debug("qeUpdateItemMaster: "+query);
			
			conn.createStatement().executeUpdate(query);

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
	
	
	public RangeGenericVO getTempStorageRange(String ssrId) throws DAOException{
		
		RangeGenericVO rangeGenericVO = null;
		Connection conn = null;
		
		try {
			conn = dataConnection.getConnectionOracleMSD();
			
			String queryExecute = "	SELECT SSR_ID, SSR_RANGE_FROM, SSR_RANGE_TO, SSR_RANGE_RATE"
								+ "	FROM CCS_SETUP_STORAGE_RANGE "
								+ " WHERE SSR_ID =" + ssrId + " " +
									"ORDER BY SSR_RANGE_FROM ";
			
			logger.debug("queryExecute: "+queryExecute);

			
			ResultSet rs = conn.createStatement().executeQuery(queryExecute);
			while (rs.next()){

				rangeGenericVO = new RangeGenericVO();
				
				rangeGenericVO.setFrom(rs.getString(2));
				rangeGenericVO.setUo(rs.getString(3));
				rangeGenericVO.setRate(rs.getString(4));
//				rangeGenericVO.setIdCurrency(rs.getString(5));

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
		
		return rangeGenericVO;
	}
	
	public void updateSetupTempStorageRange(String attributeToUpdate, String valueAttribute, String idStage, String ssrId) throws DAOException{

		Connection conn = null;
	
		try{
			conn = dataConnection.getConnectionOracleMSD();


			String updateAttribute = new String("");
			
			if (attributeToUpdate.equals(ConstantsFinalObject.SSR_RANGE_FROM))
				updateAttribute="SSR_RANGE_FROM="+valueAttribute;
			
			if (attributeToUpdate.equals(ConstantsFinalObject.SSR_RANGE_UO))
				updateAttribute="SSR_RANGE_TO	="+valueAttribute;
			
			if (attributeToUpdate.equals(ConstantsFinalObject.SSR_RANGE_RATE))
				updateAttribute="SSR_RANGE_RATE="+valueAttribute;
			
			String qeUpdateImportUnit = "UPDATE CCS_SETUP_STORAGE_RANGE SET " +
					updateAttribute +" " 			
									  +	" WHERE STG_ID = " + idStage 
									  + " AND SSR_ID = " + ssrId;
			
			logger.debug("qeUpdateItemMaster: "+qeUpdateImportUnit);
			
			conn.createStatement().executeUpdate(qeUpdateImportUnit);

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

	
	//local transport
	public List<LocalTransportVO> getListLocalTransport(String stgId) throws DAOException{
		
		List<LocalTransportVO> listLocalTransportVO = new ArrayList<LocalTransportVO>();
		Connection conn = null;
		
		try {
			conn = dataConnection.getConnectionOracleMSD();
			
			String queryExecute = "SELECT SLT_ID, SLT_ENTRY, ROUND(SLT_PROCESSING_FEE, 3) SLT_PROCESSING_FEE, CUR_ID "
								+ "FROM CCS_SETUP_LOCAL_TRANSPORT "
								+ "WHERE STG_ID=" + stgId + " ORDER BY SLT_ID";
			logger.debug("queryExecute: "+queryExecute);

			
			ResultSet rs = conn.createStatement().executeQuery(queryExecute);
			while (rs.next()){

				LocalTransportVO localTransportVO = new LocalTransportVO();
				localTransportVO.setSltId(rs.getString(1));
				localTransportVO.setEntry(rs.getString(2));
				localTransportVO.setProcessingFee(rs.getString(3));
				localTransportVO.setCurrencyID(rs.getString(4));
				
                listLocalTransportVO.add(localTransportVO);
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
		
		return listLocalTransportVO;
	}	
	
	public void updateSetupLocalTransport(String attributeToUpdate, String valueAttribute, String idStage, String sltId) throws DAOException{

		Connection conn = null;
//		String valor="";
		try{
			conn = dataConnection.getConnectionOracleMSD();
			
			
			
			
			
			
			
//			
//			if (attributeToUpdate.equals(ConstantsFinalObject.SOM_PROCESSING_FEE)){
//				
//				//Si la moneda almacenada en la base de datos no es dolar, entonces se transforma cantidad a dolar segï¿½n tasa de intercambio
//				String qMonedaActual = "select 1  from CCS_SETUP_OPE_MANAGE   WHERE STG_ID = " + idStage  + " AND SOM_ID = " + somID +" AND CUR_ID <> 1";
//				ResultSet rs =  conn.createStatement().executeQuery(qMonedaActual);
//				if (rs.next()){
//					updateAttribute = "SOM_PROCESSING_FEE = "+valueAttribute+" / (SELECT SMV_AMOUNT FROM CCS_SETUP_MACRO_VARS where SMV_COD = 'ERA' AND stg_id = "+idStage+")" ;
//				}else{
//					updateAttribute = "SOM_PROCESSING_FEE = "+valueAttribute+"";
//				}
//					
//			}
//				
//			
//			if (attributeToUpdate.equals(ConstantsFinalObject.SOM_RATE_KG))
//				updateAttribute="SOM_RATE_KG="+valueAttribute;
//			
//			if (attributeToUpdate.equals(ConstantsFinalObject.CUR_ID)){
//				updateAttribute="CUR_ID="+valueAttribute;
//
//				//Si la moneda enviada es distinto a dolar, entonces se transforma monto almacenado en la base por la tasa de intercambio.
//				if (!valueAttribute.equals("1")){
//					updateAttribute = updateAttribute + ", SOM_PROCESSING_FEE = SOM_PROCESSING_FEE / (SELECT SMV_AMOUNT FROM CCS_SETUP_MACRO_VARS where SMV_COD = 'ERA' AND stg_id = "+idStage+")" ;
//				}else{
//					updateAttribute = updateAttribute + ", SOM_PROCESSING_FEE = SOM_PROCESSING_FEE * (SELECT SMV_AMOUNT FROM CCS_SETUP_MACRO_VARS where SMV_COD = 'ERA' AND stg_id = "+idStage+")" ;
//				}
//			}
			
			
			
			
			
			
			
			
			
			
			


			String updateAttribute = new String("");
			
			if (attributeToUpdate.equals(ConstantsFinalObject.SLT_ENTRY))
				updateAttribute="SLT_ENTRY='"+valueAttribute+"'";
			
			if (attributeToUpdate.equals(ConstantsFinalObject.SLT_PERCENT_COST))
				updateAttribute="SLT_PERCENT_COST="+valueAttribute;
			
			if (attributeToUpdate.equals(ConstantsFinalObject.SLT_PROCESSING_FEE)){
				
				
				updateAttribute = "SLT_PROCESSING_FEE = "+valueAttribute+"";
				
				//Si la moneda almacenada en la base de datos no es dolar, entonces se transforma cantidad a dolar segï¿½n tasa de intercambio
//				String qMonedaActual = "select 1  from CCS_SETUP_LOCAL_TRANSPORT   WHERE STG_ID = " + idStage  + " AND SLT_ID = " + sltId +" AND CUR_ID <> 1";
//				ResultSet rs =  conn.createStatement().executeQuery(qMonedaActual);
//				if (rs.next()){
//					updateAttribute = "SLT_PROCESSING_FEE = "+valueAttribute+" / (SELECT SMV_AMOUNT FROM CCS_SETUP_MACRO_VARS where SMV_COD = 'ERA' AND stg_id = "+idStage+")" ;
//				}else{
//					updateAttribute = "SLT_PROCESSING_FEE = "+valueAttribute+"";
//				}
				
			}
//				updateAttribute="SLT_PROCESSING_FEE="+valueAttribute;
			
			
			if (attributeToUpdate.equals(ConstantsFinalObject.CUR_ID)){
				updateAttribute="CUR_ID="+valueAttribute;
	
				//Si la moneda enviada es distinto a dolar, entonces se transforma monto almacenado en la base por la tasa de intercambio.
//				if (!valueAttribute.equals("1")){
//					updateAttribute = updateAttribute + ", SLT_PROCESSING_FEE = SLT_PROCESSING_FEE / (SELECT SMV_AMOUNT FROM CCS_SETUP_MACRO_VARS where SMV_COD = 'ERA' AND stg_id = "+idStage+")" ;
//				}else{
//					updateAttribute = updateAttribute + ", SLT_PROCESSING_FEE = SLT_PROCESSING_FEE * (SELECT SMV_AMOUNT FROM CCS_SETUP_MACRO_VARS where SMV_COD = 'ERA' AND stg_id = "+idStage+")" ;
//				}
			}
			
			
//			if (attributeToUpdate.equals(ConstantsFinalObject.CUR_ID)){
//				updateAttribute="CUR_ID="+valueAttribute;
//
//				String qMonedaActual = "select cur_id "
//				  + "from CCS_SETUP_LOCAL_TRANSPORT "
//				  +	" WHERE STG_ID = " + idStage 
//				  + " AND SLT_ID = " + sltId;
//				logger.debug("qMonedaActual: "+qMonedaActual);
//				ResultSet rs =  conn.createStatement().executeQuery(qMonedaActual);
//				if (rs.next()){
//					if (rs.getString(1).equals("1")){
//						//multiplico
//						String qDolar = "SELECT ROUND(SLT_PROCESSING_FEE * (SELECT CUR_EXCHANGE_DOLAR FROM CCS_CURRENCY WHERE CUR_ID="+valueAttribute+"),2), SLT_PROCESSING_FEE * (SELECT CUR_EXCHANGE_DOLAR FROM CCS_CURRENCY WHERE CUR_ID="+valueAttribute+")  "
//								+ "FROM CCS_SETUP_LOCAL_TRANSPORT OPM "
//								+ "WHERE OPM.STG_ID = " + idStage 
//								+ " AND OPM.SLT_ID = " + sltId;
//						
//						
//						logger.debug("qDolar xqt: "+qDolar);
//						ResultSet rsTotal = conn.createStatement().executeQuery(qDolar); 
//						
//						if (rsTotal.next()){
//							logger.debug("dentro del if");
//							updateAttribute = updateAttribute + ", SLT_PROCESSING_FEE="+rsTotal.getString(2);
//							valor = rsTotal.getString(1);
//						}else{
//							logger.debug("fuera del if ");
//						}
//						
//					}else{
//						//divido
//						String qDolar = "SELECT ROUND(SLT_PROCESSING_FEE / (SELECT CUR_EXCHANGE_DOLAR FROM CCS_CURRENCY WHERE CUR_ID="+rs.getString(1)+"),2), SLT_PROCESSING_FEE / (SELECT CUR_EXCHANGE_DOLAR FROM CCS_CURRENCY WHERE CUR_ID="+rs.getString(1)+")  "
//								+ "FROM CCS_SETUP_LOCAL_TRANSPORT OPM "
//								+ "WHERE OPM.STG_ID = " + idStage 
//								+ " AND OPM.SLT_ID = " + sltId;
//						
//						logger.debug("qDolar: "+qDolar);
//						ResultSet rsTotal = conn.createStatement().executeQuery(qDolar);
//						if (rsTotal.next()){
//							updateAttribute +=", SLT_PROCESSING_FEE="+rsTotal.getString(2);
//							valor = rsTotal.getString(1);
//						}
//						
//					}
//				}
//			}
			
			String qeUpdateImportUnit = "UPDATE CCS_SETUP_LOCAL_TRANSPORT SET " +
					updateAttribute +" " 			
									  +	" WHERE STG_ID = " + idStage 
									  + " AND SLT_ID = " + sltId;
			
			logger.debug("qeUpdateItemMaster: "+qeUpdateImportUnit);
			
			conn.createStatement().executeUpdate(qeUpdateImportUnit);

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
//		return valor;
	}
	
	
	
	public List<RangeLocalTransportVO> getListLocalTransportRange(String stgId) throws DAOException{
		
		List<RangeLocalTransportVO> listRangeLocalTransportVO = new ArrayList<RangeLocalTransportVO>();
		Connection conn = null;
		
		try {
			conn = dataConnection.getConnectionOracleMSD();
			
			String queryExecute = "	SELECT SLR_ID, SLR_RANGE_FROM, SLR_RANGE_TO, SLR_RANGE_RATE_KG, CUR_ID "
								+ "	FROM CCS_SETUP_LOCAL_RANGE "
								+ " WHERE STG_ID="+stgId +" ORDER BY SLR_ID ";
			
			logger.debug("queryExecute: "+queryExecute);

			
			ResultSet rs = conn.createStatement().executeQuery(queryExecute);
			while (rs.next()){

				RangeLocalTransportVO rangeLocalTransportVO = new RangeLocalTransportVO();
				rangeLocalTransportVO.setIdRangeLocalTransport(rs.getString(1));
				rangeLocalTransportVO.setFromRangeLocalTransport(rs.getString(2));
				rangeLocalTransportVO.setToRangeLocalTransport(rs.getString(3));
				rangeLocalTransportVO.setRateRangeLocalTransport(rs.getFloat(4));
				rangeLocalTransportVO.setCurrencyRangeLocalTransport(rs.getString(5));

				listRangeLocalTransportVO.add(rangeLocalTransportVO);
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
		
		return listRangeLocalTransportVO;
	}
	
	public RangeGenericVO getLocalTransportRange(String slrId) throws DAOException{
		
		RangeGenericVO rangeGenericVO = null;
		Connection conn = null;
		
		try {
			conn = dataConnection.getConnectionOracleMSD();
			
			String queryExecute = "	SELECT SLR_ID, SLR_RANGE_FROM, SLR_RANGE_TO, SLR_RANGE_RATE_KG, CUR_ID"
								+ "	FROM CCS_SETUP_LOCAL_RANGE "
								+ " WHERE SLR_ID =" + slrId;
			
			logger.debug("queryExecute: "+queryExecute);

			
			ResultSet rs = conn.createStatement().executeQuery(queryExecute);
			while (rs.next()){

				rangeGenericVO = new RangeGenericVO();
				
				rangeGenericVO.setFrom(rs.getString(2));
				rangeGenericVO.setUp(rs.getString(3));
				rangeGenericVO.setRate(rs.getString(4));
				rangeGenericVO.setIdCurrency(rs.getString(5));

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
		
		return rangeGenericVO;
	}
	
	public void updateSetupLocalTransportRange(String attributeToUpdate, String valueAttribute, String idStage, String slrId, String idCurrency) throws DAOException{

		Connection conn = null;
	
		try{
			conn = dataConnection.getConnectionOracleMSD();


			logger.debug("attributeToUpdate: "+attributeToUpdate);
			
			String updateAttribute = new String("");
			
			if (attributeToUpdate.equals(ConstantsFinalObject.SLR_RANGE_FROM))
				updateAttribute="SLR_RANGE_FROM="+valueAttribute;
			
			if (attributeToUpdate.equals(ConstantsFinalObject.SLR_RANGE_UP))
				updateAttribute="SLR_RANGE_TO="+valueAttribute;
			
			if (attributeToUpdate.equals(ConstantsFinalObject.SLR_RANGE_RATE_KG))
				updateAttribute="SLR_RANGE_RATE_KG="+valueAttribute;
			
			
			
			String qeUpdateImportUnit = "UPDATE CCS_SETUP_LOCAL_RANGE SET CUR_ID="+idCurrency+", " +
					updateAttribute +" " 			
									  +	" WHERE STG_ID = " + idStage 
									  + " AND SLR_ID = " + slrId;
			
			logger.debug("qeUpdateItemMaster: "+qeUpdateImportUnit);
			
			conn.createStatement().executeUpdate(qeUpdateImportUnit);

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

	public List<GeneralComboVO> getListCustomDuty(String stgId) throws DAOException{
		
		List<GeneralComboVO> listTempStoreRage = new ArrayList<GeneralComboVO>();
		Connection conn = null;
		
		try {
			conn = dataConnection.getConnectionOracleMSD();
			
			String queryExecute = "SELECT SDD_COD, SDD_DUTY_NAME"
								+ "	FROM CCS_SETUP_CUSTUM_DUTIES_DETAIL "
								+ " WHERE STG_ID="+stgId;
			
			logger.debug("queryExecute: "+queryExecute);

			
			ResultSet rs = conn.createStatement().executeQuery(queryExecute);
			while (rs.next()){

				GeneralComboVO generalComboVO = new GeneralComboVO();

				generalComboVO.setIdGeneralCombo(rs.getString(1));
				generalComboVO.setNameGeneralCombo(rs.getString(2));
				
                listTempStoreRage.add(generalComboVO);
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
		
		return listTempStoreRage;
	}
	
	public List<GeneralComboVO> getListLocalRoute(String idStage) throws DAOException{
		
		List<GeneralComboVO> listTempStoreRage = new ArrayList<GeneralComboVO>();
		Connection conn = null;
		
		try {
			conn = dataConnection.getConnectionOracleMSD();
			
			String queryExecute = "SELECT SLR_ID, SLR_NAME FROM CCS_SETUP_LOCAL_ROUTE WHERE STG_ID = "+idStage+"";
			
			logger.debug("queryExecute: "+queryExecute);

			
			ResultSet rs = conn.createStatement().executeQuery(queryExecute);
			while (rs.next()){

				GeneralComboVO generalComboVO = new GeneralComboVO();

				generalComboVO.setIdGeneralCombo(rs.getString(1));
				generalComboVO.setNameGeneralCombo(rs.getString(2));
				
                listTempStoreRage.add(generalComboVO);
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
		
		return listTempStoreRage;
	}
	
	public List<GeneralComboVO> getListRateStorage(String idStage) throws DAOException{
		
		List<GeneralComboVO> listTempStoreRage = new ArrayList<GeneralComboVO>();
		Connection conn = null;
		
		try {
			conn = dataConnection.getConnectionOracleMSD();
			
			String queryExecute = "SELECT SSR_ID, SSR_NAME FROM CCS_SETUP_STORAGE_RATE WHERE STG_ID = "+idStage+"";
			
			logger.debug("queryExecute: "+queryExecute);

			
			ResultSet rs = conn.createStatement().executeQuery(queryExecute);
			while (rs.next()){

				GeneralComboVO generalComboVO = new GeneralComboVO();

				generalComboVO.setIdGeneralCombo(rs.getString(1));
				generalComboVO.setNameGeneralCombo(rs.getString(2));
				
                listTempStoreRage.add(generalComboVO);
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
		
		return listTempStoreRage;
	}
	
	
	public void deleteFilter(String idStage) throws DAOException{

		Connection conn = null;
	
		try{
			conn = dataConnection.getConnectionOracleMSD();


			String qeUpdateImportUnit = "UPDATE CCS_IMPORT_UNITS "
									  + " SET IMU_STATE=1" 			
									  +	" WHERE STG_ID = " + idStage;
			
			logger.debug("qeUpdateItemMaster: "+qeUpdateImportUnit);
			
			conn.createStatement().executeUpdate(qeUpdateImportUnit);

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

	public void filtroPorUno(String idStage, String localeProduct) throws DAOException{
		
		Connection conn = null;
		try {
			conn = dataConnection.getConnectionOracleMSD();
			
			String queryExecute="UPDATE CCS_IMPORT_UNITS "
							  + " SET IMU_STATE=0 "
							  + " WHERE STG_ID=" + idStage
							  + " AND IMU_COD_PRODUCT ='" + localeProduct +"'";
			
			logger.debug("queryExecuteFILTRO : "+queryExecute);
			
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
	

	public void replicarProducto(String idStage, String localeProduct) throws DAOException{
		
		Connection conn = null;
		try {
			
			conn = dataConnection.getConnectionOracleMSD();
			conn.setAutoCommit(false);
			
			String queryExecute = "SELECT COUNT(*) FROM CCS_IMPORT_UNITS "
								+ "WHERE STG_ID = " + idStage + " "
								+ "AND IMU_COD_PRODUCT LIKE '"+localeProduct+"_%'";
			
			ResultSet rs = conn.createStatement().executeQuery(queryExecute);
			int cont = 0;
			if (rs.next())
				cont = rs.getInt(1);
			
			cont ++;
			queryExecute="INSERT INTO CCS_IMPORT_UNITS(IMU_JAN, IMU_FEB, IMU_MAR, IMU_APR, IMU_MAY, IMU_JUN, IMU_JUL, IMU_AUG, IMU_SEP, IMU_OCT, IMU_NOV, IMU_DIC, IMU_AJUSTED, IMU_COD_PRODUCT, IMU_DESRIPTION_PRODUCT, STG_ID, IMU_STATE, IMU_HUB_COD_PRODUCT, IMU_REPLY) "
					   + "SELECT IMU_JAN, IMU_FEB, IMU_MAR, IMU_APR, IMU_MAY, IMU_JUN, IMU_JUL, IMU_AUG, IMU_SEP, IMU_OCT, IMU_NOV, IMU_DIC, IMU_AJUSTED, IMU_COD_PRODUCT || '_"+cont+"', IMU_DESRIPTION_PRODUCT, STG_ID, IMU_STATE, IMU_HUB_COD_PRODUCT || '_"+cont+"', 1 "
					   + "FROM CCS_IMPORT_UNITS "
					   + "WHERE IMU_COD_PRODUCT ='" + localeProduct + "' "
					   + "AND STG_ID="+ idStage;
			
			logger.debug("queryExecuteFILTRO : "+queryExecute);
			
			conn.createStatement().executeUpdate(queryExecute);

			queryExecute="INSERT INTO CCS_LOCAL_ITEM_MASTER(LIM_FOB_COST, LIM_HIGH_PALETTE, LIM_LARGE_PALETTE, LIM_WIDE_PALETTE, LIM_UNIT_BY_PALETTE, LIM_WEIGTH_PALETTE, IMU_COD_PRODUCT, STG_ID, PRM_ID, PFA_ID, PTY_ID, LIM_TMP_TRA_ID, LIM_TMP_SIS_ID, PRP_ID, PTR_ID, PTL_ID, SDD_COD, SIS_ID, SLR_ID, SSR_ID, LIM_IS_IVA) "
					  + "SELECT LIM_FOB_COST,  LIM_HIGH_PALETTE,  LIM_LARGE_PALETTE,  LIM_WIDE_PALETTE,  LIM_UNIT_BY_PALETTE,  LIM_WEIGTH_PALETTE,  IMU_COD_PRODUCT || '_"+cont+"',  STG_ID,  PRM_ID,  PFA_ID,  PTY_ID,  LIM_TMP_TRA_ID,  LIM_TMP_SIS_ID,  PRP_ID,  PTR_ID,  PTL_ID,  SDD_COD, SIS_ID, SLR_ID, SSR_ID, LIM_IS_IVA "
					  + "FROM CCS_LOCAL_ITEM_MASTER "
					  + "WHERE IMU_COD_PRODUCT='" + localeProduct +"' "
					  + "AND STG_ID=" + idStage;
			
			logger.debug("queryExecuteFILTRO : "+queryExecute);
			
			conn.createStatement().executeUpdate(queryExecute);
			
			queryExecute= "INSERT INTO CCS_SITE_SOURCE_TRANSPORT(SIS_ID, IMU_COD_PRODUCT,STG_ID,TRA_ID) "
					    + "SELECT SIS_ID, IMU_COD_PRODUCT||'_"+cont+"',STG_ID,TRA_ID "
					    + "FROM CCS_SITE_SOURCE_TRANSPORT "
					    + "WHERE IMU_COD_PRODUCT='" + localeProduct + "' "
					    + "AND STG_ID=" + idStage;
			
			logger.debug("queryExecuteFILTRO : "+queryExecute);
			
			conn.createStatement().executeUpdate(queryExecute);
			
			conn.commit();
			
		}catch (SQLException e) {
			try {
				e.printStackTrace();
				conn.rollback();
			} catch (SQLException e1) {
				e.printStackTrace();
				throw new DAOException(ConstantsFinalObject.EXCEPTION_DAO_GENERIC_ERR_COD, e);		
			}

		}finally{		
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					throw new DAOException(ConstantsFinalObject.EXCEPTION_DAO_GENERIC_ERR_COD, e);
				}
		}
	}
	
	public void removerProducto(String idStage, String localeProduct) throws DAOException{
		
		Connection conn = null;
		try {
			
			conn = dataConnection.getConnectionOracleMSD();
			conn.setAutoCommit(false);
			
			String queryExecute= "DELETE FROM CCS_SITE_SOURCE_TRANSPORT "
				    + "WHERE IMU_COD_PRODUCT='" + localeProduct + "' "
				    + "AND STG_ID=" + idStage;
		
			logger.debug("queryExecuteFILTRO : "+queryExecute);
			
			conn.createStatement().executeUpdate(queryExecute);
	
			queryExecute="DELETE FROM CCS_LOCAL_ITEM_MASTER "
					  + "WHERE IMU_COD_PRODUCT='" + localeProduct +"' "
					  + "AND STG_ID=" + idStage;
			
			logger.debug("queryExecuteFILTRO : "+queryExecute);
			
			conn.createStatement().executeUpdate(queryExecute);

			queryExecute="DELETE FROM CCS_IMPORT_UNITS "
					   		  + "WHERE IMU_COD_PRODUCT ='" + localeProduct + "' "
					   		  + "AND STG_ID="+ idStage;
			
			logger.debug("queryExecuteFILTRO : "+queryExecute);
			
			conn.createStatement().executeUpdate(queryExecute);

			conn.commit();
			
		}catch (SQLException e) {
			try {
				e.printStackTrace();
				conn.rollback();
			} catch (SQLException e1) {
				e.printStackTrace();
				throw new DAOException(ConstantsFinalObject.EXCEPTION_DAO_GENERIC_ERR_COD, e);		
			}

		}finally{		
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					throw new DAOException(ConstantsFinalObject.EXCEPTION_DAO_GENERIC_ERR_COD, e);
				}
		}
	}
	
	
	public void replicateFreight(String idSiteSource, String idStage) throws DAOException{
		
		
		Connection conn = null;
		try {
			conn = dataConnection.getConnectionOracleMSD();
			
			conn.setAutoCommit(false);
			
			
			
			ResultSet rs = conn.createStatement().executeQuery("SELECT CCS_SEQ_CCS_SITE_SOURCE.NEXTVAL FROM DUAL");
			String idSiteSourceNext = rs.next()==true?rs.getString(1):"";

			rs = conn.createStatement().executeQuery("SELECT CCS_SEQ_CCS_STAGE_FAI.NEXTVAL FROM DUAL");
			String idFreightAirNext = rs.next()==true?rs.getString(1):"";
			

			logger.debug("idSiteSource: "+idSiteSource);
			logger.debug("idStage: "+idStage);
			logger.debug("idSiteSourceNext: "+idSiteSourceNext);
			
			
			String queryExecute= "INSERT INTO CCS_SITE_SOURCE (SIS_ID, SIS_NAME, STG_ID, TRA_ID)(SELECT "+idSiteSourceNext+", CONCAT(SIS_NAME, ''), STG_ID, TRA_ID FROM CCS_SITE_SOURCE WHERE SIS_ID = "+idSiteSource+" AND STG_ID = "+idStage+")";
			conn.createStatement().executeUpdate(queryExecute);
			
			
			queryExecute= "INSERT INTO CCS_STAGE_FREIGHT_AIR (FAR_INCREASE, FAR_AWB_KG_RATES, FAR_FUEL_SURCHARGE, FAR_SECURITY_CHARGE, FAR_OTHER_FFW_FEES, STG_ID, SFE_ID, SIS_ID, FAR_ID, FAR_ORDERS, FAR_INCREASE_AWB, FAR_INCREASE_FSC, FAR_INCREASE_SCC, FAR_INCREASE_FFW)(SELECT FAR_INCREASE,FAR_AWB_KG_RATES, FAR_FUEL_SURCHARGE, FAR_SECURITY_CHARGE, FAR_OTHER_FFW_FEES, STG_ID, SFE_ID, "+idSiteSourceNext+", "+idFreightAirNext+", FAR_ORDERS, FAR_INCREASE_AWB, FAR_INCREASE_FSC, FAR_INCREASE_SCC, FAR_INCREASE_FFW FROM CCS_STAGE_FREIGHT_AIR WHERE STG_ID = "+idStage+" AND SIS_ID = "+idSiteSource+")";
			conn.createStatement().executeUpdate(queryExecute);

			
			queryExecute= "INSERT INTO CCS_STAGE_FREIGHT_AIR_RANGE(FRR_ID, FRR_FROM, FRR_TO, FRR_RATE, STG_ID, SIS_ID, FAR_ID)(SELECT CCS_SEQ_CCS_STAGE_F_AIR_RANGE.NEXTVAL, FRR_FROM, FRR_TO, FRR_RATE, STG_ID, "+idSiteSourceNext+", "+idFreightAirNext+" FROM CCS_STAGE_FREIGHT_AIR_RANGE WHERE STG_ID = "+idStage+" AND SIS_ID = "+idSiteSource+")";
			
			logger.debug("queryExecute: "+queryExecute);
			conn.createStatement().executeUpdate(queryExecute);
			
			
			queryExecute= "INSERT INTO CCS_SETUP_OPE_CONSOLIDATION (SOC_ID, SOC_ORDERS, STG_ID, SIS_ID, FRM_ID, TRA_ID) VALUES(CCS_SEQ_CCS_SETUP_OPE_CONS.NEXTVAL, "+1+", "+idStage+", "+idSiteSourceNext+", NULL, NULL )";
			conn.createStatement().executeUpdate(queryExecute);
			
			
//			String qeSelectFreightRanges = "INSERT INTO CCS_STAGE_FREIGHT_AIR_RANGE( FRR_ID, FRR_FROM, FRR_TO, FRR_RATE, STG_ID, SIS_ID, FAR_ID)  (SELECT CCS_SEQ_CCS_STAGE_F_AIR_RANGE.NEXTVAL, FRR_FROM, FRR_TO, FRR_RATE, STG_ID, SIS_ID, FAR_ID FROM CCS_STAGE_FREIGHT_AIR_RANGE WHERE FAR = "+idRangeFreighAir+" )";
//			logger.debug("qeSelectFreightRanges: "+qeSelectFreightRanges);
//			
//			conn.createStatement().executeUpdate(qeSelectFreightRanges);
			
			
			conn.commit();
			
			logger.debug("Commit!!!");
			
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
	
	public void deleteFreightRoute(String idSiteSource, String idStage) throws DAOException{
		
		
		Connection conn = null;
		try {
			conn = dataConnection.getConnectionOracleMSD();
			
			conn.setAutoCommit(false);
			
			String qeSelectProductsImportUnits = "SELECT COUNT(*) " +
					 "FROM CCS_STAGE_FREIGHT_AIR FAR, CCS_SITE_SOURCE SIS, CCS_STAGE_FREIGHT_EXCHANGE SFE " +
					 "WHERE FAR.SIS_ID = SIS.SIS_ID " +
					 "AND   FAR.STG_ID = SIS.STG_ID " +
					 "AND   SIS.STG_ID = "+idStage+" "+
					 "AND   SFE.SFE_ID = FAR.SFE_ID " +
					 "ORDER BY FAR.FAR_ID ";
			int totalRow=0;
			ResultSet rsCount = conn.createStatement().executeQuery(qeSelectProductsImportUnits);
			if(rsCount.next())
				totalRow=rsCount.getInt(1);
			if(totalRow == 1)
				throw new DAOException("El registro no puede ser eliminado, debe existir como minimo uno vigente.");
			
			
			String queryExecute= "SELECT 1 FROM CCS_LOCAL_ITEM_MASTER WHERE STG_ID = "+idStage+" AND SIS_ID = "+idSiteSource;
			ResultSet rs = conn.createStatement().executeQuery(queryExecute);
			if (rs.next())
				throw new DAOException(ConstantsFinalObject.E_DAO_PRODUCT_FREIGHT_IS);
			
			queryExecute= "DELETE FROM CCS_SETUP_OPE_CONSOLIDATION WHERE STG_ID = "+idStage+" AND SIS_ID = "+idSiteSource+"";
			logger.debug("queryExecute: "+queryExecute);
			conn.createStatement().executeUpdate(queryExecute);
			
			queryExecute= "DELETE FROM CCS_STAGE_FREIGHT_AIR_RANGE WHERE STG_ID = "+idStage+" AND SIS_ID = "+idSiteSource+"";
			logger.debug("queryExecute: "+queryExecute);
			conn.createStatement().executeUpdate(queryExecute);
			
			queryExecute= "DELETE FROM CCS_STAGE_FREIGHT_AIR WHERE STG_ID = "+idStage+" AND SIS_ID = "+idSiteSource+"";
			logger.debug("queryExecute: "+queryExecute);
			conn.createStatement().executeUpdate(queryExecute);
			
			queryExecute= "DELETE FROM CCS_SITE_SOURCE WHERE STG_ID = "+idStage+" AND SIS_ID = "+idSiteSource+"";
			logger.debug("queryExecute: "+queryExecute);
			conn.createStatement().executeUpdate(queryExecute);
			
			
			conn.commit();
			
			logger.debug("Commit Delete!!!");
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
	
	public List<GeneralComboVO> listCurrencBySite(String idSite) throws DAOException{
		
		Connection conn = null;
		List<GeneralComboVO> listGeneralComboVO = new ArrayList<GeneralComboVO>();
		try {
			conn = dataConnection.getConnectionOracleMSD();
			
			String queryExecute = "SELECT a.CUR_ID, a.CUR_ACRONYM from CCS_CURRENCY a, CCS_SITE_CURRENCY b " +
					"wHERE a.CUR_ID = b.CUR_ID " +
					"AND b.SIT_ID = "+idSite+" " +
					"uNION ALL " +
					"sELECT a.CUR_ID, a.CUR_ACRONYM from CCS_CURRENCY a " +
					"WHERE a.CUR_ACRONYM = 'USD'";
			
			logger.debug(queryExecute);
			
			ResultSet rs = conn.createStatement().executeQuery(queryExecute);
			
			
			while (rs.next()) {
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
	
	
	public CustodyVO getCustudySetup(String idStage) throws DAOException{
		
		Connection conn = null;
		
		CustodyVO custodyVO = new CustodyVO();
		try {
			conn = dataConnection.getConnectionOracleMSD();
			
			String queryExecute = "SELECT CUR_ID, SCS_BASE_LINE, SCS_FEE_COST, STG_ID FROM CCS_SETUP_CUSTODY_SERVICE WHERE STG_ID = "+idStage+"";
			logger.debug(queryExecute);
			
			ResultSet rs = conn.createStatement().executeQuery(queryExecute);
			if (rs.next()){
				
				custodyVO.setIdCurrencyCustody(rs.getString(1));
				custodyVO.setBaseLineCustody(rs.getFloat(2));
				custodyVO.setFeeCostCustody(rs.getFloat(3));
				custodyVO.setIdStageCustody(rs.getString(4));
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
		return custodyVO;
	}
	
	
	public void updateBaseLineCustody(float baseLineCustody, String idStage) throws DAOException{
		
		Connection conn = null;
		try {
			conn = dataConnection.getConnectionOracleMSD();
			
			String queryExecute = "UPDATE CCS_SETUP_CUSTODY_SERVICE SET SCS_BASE_LINE = "+baseLineCustody+" WHERE STG_ID = "+idStage+"";
			logger.debug(queryExecute);
			
			conn.createStatement().executeQuery(queryExecute);
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

	public void updateFeeCostCustody(float feeCostCustody, String idStage, String idCurrency) throws DAOException{
		
		
//		(SELECT SMV_AMOUNT FROM CCS_SETUP_MACRO_VARS where SMV_COD = 'ERA' AND stg_id = "+idStage+")
		
		Connection conn = null;
		try {
			conn = dataConnection.getConnectionOracleMSD();


			String queryExecute = "UPDATE CCS_SETUP_CUSTODY_SERVICE SET SCS_FEE_COST = "+feeCostCustody+" , CUR_ID = "+idCurrency+" WHERE STG_ID = "+idStage+"";
			logger.debug("queryExecute: "+queryExecute);
			conn.createStatement().executeUpdate(queryExecute);
			
//			if (!idCurrency.equals("1")){
//				String queryExecute = "UPDATE CCS_SETUP_CUSTODY_SERVICE SET SCS_FEE_COST = "+feeCostCustody+" / (SELECT SMV_AMOUNT FROM CCS_SETUP_MACRO_VARS where SMV_COD = 'ERA' AND stg_id = "+idStage+"), CUR_ID = "+idCurrency+" WHERE STG_ID = "+idStage+"";
//				logger.debug("queryExecute: "+queryExecute);
//				conn.createStatement().executeQuery(queryExecute);
//			}else{
//				String queryExecute = "UPDATE CCS_SETUP_CUSTODY_SERVICE SET SCS_FEE_COST = "+feeCostCustody+" , CUR_ID = "+idCurrency+" WHERE STG_ID = "+idStage+"";
//				logger.debug("queryExecute: "+queryExecute);
//				conn.createStatement().executeQuery(queryExecute);
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
	}
	
	public List<GeneralComboVO> listCurrencyFreight(String idStage) throws DAOException{
		
		List<GeneralComboVO> listGeneralComboVO = new ArrayList<GeneralComboVO>();
		
		Connection conn = null;
		try {
			conn = dataConnection.getConnectionOracleMSD();


			String queryExecute = "SELECT EXG.SFE_ID, EXG.SFE_ACRONYM FROM CCS_STAGE_FREIGHT_EXCHANGE EXG WHERE STG_ID = "+idStage;
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
	
	
	public List<OtherFreightVO> listOtherFreights(String idStage) throws DAOException{
		
		List<OtherFreightVO> listOtherFreightVO = new ArrayList<OtherFreightVO>();
		
		Connection conn = null;
		try {
			conn = dataConnection.getConnectionOracleMSD();


			String queryExecute = "SELECT OTH.FOT_ID, SIS.SIS_NAME, OTH.FOT_PALETTES, OTH.TRA_ID, SUM(CSP.OCP_RATE), OTH.SFE_ID, OTH.FOT_ORDERS, OTH.FOT_INCREASE  " +
								  "FROM   CCS_STAGE_FREIGHT_OTHERS OTH, CCS_STAGE_FREIGHT_OTHERS_CSP CSP, CCS_SITE_SOURCE SIS " +
								  "WHERE  OTH.STG_ID = "+idStage+" " +
								  "AND    OTH.FOT_ID = CSP.FOT_ID " +
								  "AND    OTH.SIS_ID = SIS.SIS_ID " +
								  "GROUP BY OTH.FOT_ID, SIS.SIS_NAME, OTH.FOT_PALETTES, OTH.TRA_ID, OTH.SFE_ID, OTH.FOT_ORDERS, OTH.FOT_INCREASE " +
								  "ORDER BY OTH.FOT_ID ";
			
			
			logger.debug("queryExecute: "+queryExecute);
			ResultSet rs = conn.createStatement().executeQuery(queryExecute);
			
			while (rs.next()){
				
				OtherFreightVO otherFreightVO = new OtherFreightVO();
				
				otherFreightVO.setIdOtherFreight(rs.getString(1));
				otherFreightVO.setNameOtherFreight(rs.getString(2));
				otherFreightVO.setPalettesOtherFreight(rs.getInt(3));
				otherFreightVO.setIdTransportOtherFreight(rs.getString(4));
				otherFreightVO.setTotalConceptsFreight(rs.getFloat(5));
				otherFreightVO.setIdCrrencyOtherFreight(rs.getString(6));
				otherFreightVO.setOrdersNumberOtherFreight(rs.getString(7));
				otherFreightVO.setIncreaseOtherFreight(rs.getFloat(8));
				
				listOtherFreightVO.add(otherFreightVO);
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
		
		return listOtherFreightVO;
	}
	
	
	public List<GeneralComboVO> listOtherTransports() throws DAOException{
		
		List<GeneralComboVO> listGeneralComboVO = new ArrayList<GeneralComboVO>();
		
		Connection conn = null;
		try {
			conn = dataConnection.getConnectionOracleMSD();

			String queryExecute = "select tra_id, tra_name from ccs_transport where tra_id <> 2";
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
	
	public List<FreighAirRangeVO> listRangeFreightAir(String idFreightAir) throws DAOException{
		
		List<FreighAirRangeVO> listFreighAirRangeVO = new ArrayList<FreighAirRangeVO>();
		
		Connection conn = null;
		try {
			conn = dataConnection.getConnectionOracleMSD();
			
			String qeSelectFreightRanges = "SELECT FRR_ID, FRR_FROM, FRR_TO, FRR_RATE, FAR_ID FROM CCS_STAGE_FREIGHT_AIR_RANGE WHERE FAR_ID = "+idFreightAir +"ORDER BY FRR_FROM ASC";
			logger.debug("qeSelectFreightRanges: "+qeSelectFreightRanges);
			
			ResultSet rsFreigthRanges = conn.createStatement().executeQuery(qeSelectFreightRanges);
			
			while (rsFreigthRanges.next()){
				FreighAirRangeVO freighAirRangeVO = new FreighAirRangeVO();
				
				freighAirRangeVO.setIdRangeFreighAir(rsFreigthRanges.getString(1));
//				freighAirRangeVO.setDescriptionFreighAir(rsFreigthRanges.getString(2)+" - "+rsFreigthRanges.getString(3));
				freighAirRangeVO.setFromFreighAir(rsFreigthRanges.getInt(2));
				freighAirRangeVO.setToFreighAir(rsFreigthRanges.getInt(3));
				freighAirRangeVO.setRateFreighAir(rsFreigthRanges.getFloat(4));
				freighAirRangeVO.setIdFreighAir(rsFreigthRanges.getString(5));
				
				listFreighAirRangeVO.add(freighAirRangeVO);
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
		
		return listFreighAirRangeVO;
	}
	
	
	public void replicateRangeFraightAir(String idRangeFreighAir) throws DAOException{
		
		Connection conn = null;
		try {
			conn = dataConnection.getConnectionOracleMSD();
			
			String qeSelectFreightRanges = "INSERT INTO CCS_STAGE_FREIGHT_AIR_RANGE( FRR_ID, FRR_FROM, FRR_TO, FRR_RATE, STG_ID, SIS_ID, FAR_ID)  (SELECT CCS_SEQ_CCS_STAGE_F_AIR_RANGE.NEXTVAL, FRR_FROM, FRR_TO, FRR_RATE, STG_ID, SIS_ID, FAR_ID FROM CCS_STAGE_FREIGHT_AIR_RANGE WHERE FRR_ID = "+idRangeFreighAir+" )";
			logger.debug("qeSelectFreightRanges: "+qeSelectFreightRanges);
			
			conn.createStatement().executeUpdate(qeSelectFreightRanges);
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
	
	public void deleteRangeFraightAir(String idRangeFreighAir) throws DAOException{
		
		Connection conn = null;
		try {
			conn = dataConnection.getConnectionOracleMSD();
			
			String qeSelectFreightRanges = "DELETE FROM CCS_STAGE_FREIGHT_AIR_RANGE WHERE frr_id = "+idRangeFreighAir+"";
			logger.debug("qeSelectFreightRanges: "+qeSelectFreightRanges);
			
			conn.createStatement().executeUpdate(qeSelectFreightRanges);
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
	
	
	public List<ConceptCostOtherFreightVO> listConceptOtherFreight(String idOtherFreight) throws DAOException{
		
		Connection conn = null;
		
		List<ConceptCostOtherFreightVO> listConceptCostOtherFreightVO = new ArrayList<ConceptCostOtherFreightVO>();
		try {
			conn = dataConnection.getConnectionOracleMSD();
			
			String qeSelectFreightRanges = "SELECT OCP_ID, OCP_NAME, OCP_RATE, FOT_ID FROM CCS_STAGE_FREIGHT_OTHERS_CSP WHERE FOT_ID = "+idOtherFreight+"";
			logger.debug("qeSelectFreightRanges: "+qeSelectFreightRanges);
			
			ResultSet rs = conn.createStatement().executeQuery(qeSelectFreightRanges);
			
			while(rs.next()){
				ConceptCostOtherFreightVO conceptCostOtherFreightVO = new ConceptCostOtherFreightVO();
				
				conceptCostOtherFreightVO.setIdConceptOtherFreight(rs.getString(1));
				conceptCostOtherFreightVO.setNameConceptOtherFreight(rs.getString(2));
				conceptCostOtherFreightVO.setAmountConceptOtherFreight(rs.getFloat(3));
				conceptCostOtherFreightVO.setIdOtherFreight(rs.getString(4));
				
				listConceptCostOtherFreightVO.add(conceptCostOtherFreightVO);
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
		
		return listConceptCostOtherFreightVO;
	}
	
	
	public void updateConceptOtherFreight(String attributeToUpdate, String valueAttribute, String idConceptOtherFreigh) throws DAOException{

		Connection conn = null;
	
		try{
			conn = dataConnection.getConnectionOracleMSD();


			logger.debug("attributeToUpdate: "+attributeToUpdate);
			
			String updateAttribute = new String("");
			
			
			if (attributeToUpdate.equals(ConstantsFinalObject.OCP_NAME_CONCEPT))
				updateAttribute="OCP_NAME='"+valueAttribute+"'";
			
			if (attributeToUpdate.equals(ConstantsFinalObject.OCP_RATE_CONCEPT))
				updateAttribute="OCP_RATE="+valueAttribute;
			
			
			String qeUpdateImportUnit = "UPDATE CCS_STAGE_FREIGHT_OTHERS_CSP SET " +
										updateAttribute +" " +			
									  	"WHERE OCP_ID = "+ idConceptOtherFreigh;
			
			logger.debug("qeUpdateItemMaster: "+qeUpdateImportUnit);
			
			conn.createStatement().executeUpdate(qeUpdateImportUnit);

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
	
	public void updateConceptAPOFreight(String attributeToUpdate, String valueAttribute, String idRateAPOFreight) throws DAOException{

		Connection conn = null;
	
		try{
			conn = dataConnection.getConnectionOracleMSD();

			String updateAttribute = new String("");
			
			if (attributeToUpdate.equals(ConstantsFinalObject.APO_RATE_NAME_CONCEPT))
				updateAttribute="APR_CONCEPT='"+valueAttribute+"'";
			
			if (attributeToUpdate.equals(ConstantsFinalObject.APO_RATE_VALUE_CONCEPT))
				updateAttribute="APR_RATE="+valueAttribute;
			
			
			String qeUpdateImportUnit = "UPDATE CCS_STAGE_FREIGHT_APO_RATE SET " +
										updateAttribute +" " +			
									  	"WHERE APR_ID = "+ idRateAPOFreight;
			
			logger.debug("qeUpdateItemMaster: "+qeUpdateImportUnit);
			
			conn.createStatement().executeUpdate(qeUpdateImportUnit);

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
	
	
	
	public void replicateConceptOtherFreight(String idRangeFreighAir) throws DAOException{
		
		Connection conn = null;
		try {
			conn = dataConnection.getConnectionOracleMSD();
			
			String qeSelectFreightRanges = "INSERT INTO CCS_STAGE_FREIGHT_OTHERS_CSP(OCP_ID, OCP_NAME, OCP_RATE, FOT_ID, STG_ID)(SELECT CCS_SEQ_OCP.NEXTVAL, OCP_NAME, OCP_RATE, FOT_ID, STG_ID FROM CCS_STAGE_FREIGHT_OTHERS_CSP WHERE OCP_ID = "+idRangeFreighAir+")";
			logger.debug("qeSelectFreightRanges: "+qeSelectFreightRanges);
			
			conn.createStatement().executeUpdate(qeSelectFreightRanges);
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
	
	
	public void deleteConceptOtherFreight(String idRangeFreighAir) throws DAOException{
		
		Connection conn = null;
		try {
			conn = dataConnection.getConnectionOracleMSD();
			
			String qeSelectFreightRanges = "DELETE FROM CCS_STAGE_FREIGHT_OTHERS_CSP WHERE OCP_ID = "+idRangeFreighAir+"";
			logger.debug("qeSelectFreightRanges: "+qeSelectFreightRanges);
			
			conn.createStatement().executeUpdate(qeSelectFreightRanges);
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
	
	
	public void replicateOtherFreight(String idOtherFreight) throws DAOException{
		
		Connection conn = null;
		try {
			conn = dataConnection.getConnectionOracleMSD();
			
			conn.setAutoCommit(false);
			
			ResultSet rs = conn.createStatement().executeQuery("SELECT SIS_ID FROM CCS_STAGE_FREIGHT_OTHERS WHERE FOT_ID = "+idOtherFreight+"");
			String idSiteSource = rs.next()==true?rs.getString(1):"";
			
			
			rs = conn.createStatement().executeQuery("SELECT CCS_SEQ_CCS_SITE_SOURCE.NEXTVAL FROM DUAL");
			String idNewSiteSource = rs.next()==true?rs.getString(1):"";
			
			
			rs = conn.createStatement().executeQuery("SELECT CCS_SEQ_FREIGHT_OTHERS.NEXTVAL FROM DUAL");
			String idNewOtherFreight = rs.next()==true?rs.getString(1):"";
			
			
			/*Generando sentencias de inserciï¿½n para la replicaciï¿½n del Other Freight*/
			String query = "INSERT INTO CCS_SITE_SOURCE(SIS_ID, SIS_NAME, STG_ID, SIS_JDE_COD, TRA_ID)(SELECT "+idNewSiteSource+", SIS_NAME, STG_ID, SIS_JDE_COD, TRA_ID FROM CCS_SITE_SOURCE WHERE SIS_ID = "+idSiteSource+" )";
			
			conn.createStatement().executeUpdate(query);
			
			
			query = "INSERT INTO CCS_STAGE_FREIGHT_OTHERS(FOT_ID, FOT_PALETTES, STG_ID, SFE_ID, TRA_ID, SIS_ID, FOT_ORDERS, FOT_INCREASE) (SELECT "+idNewOtherFreight+", FOT_PALETTES, STG_ID, SFE_ID, TRA_ID, "+idNewSiteSource+", FOT_ORDERS, FOT_INCREASE FROM CCS_STAGE_FREIGHT_OTHERS WHERE FOT_ID = "+idOtherFreight+" )";
			
			conn.createStatement().executeUpdate(query);
			
			
			query = "INSERT INTO CCS_STAGE_FREIGHT_OTHERS_CSP(OCP_ID, OCP_NAME, OCP_RATE, FOT_ID, STG_ID)(SELECT CCS_SEQ_OCP.NEXTVAL, OCP_NAME, OCP_RATE, "+idNewOtherFreight+", STG_ID FROM CCS_STAGE_FREIGHT_OTHERS_CSP WHERE FOT_ID = "+idOtherFreight+" )";
			
			conn.createStatement().executeUpdate(query);
			
			
			conn.commit();
		}catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				throw new DAOException(ConstantsFinalObject.EXCEPTION_DAO_GENERIC_ERR_COD, e);
			}
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
	
	
	public void deleteOtherFreight(String idOtherFreight) throws DAOException{
		
		Connection conn = null;
		try {
			conn = dataConnection.getConnectionOracleMSD();
			
			conn.setAutoCommit(false);
			
			
			
			
			ResultSet rs = conn.createStatement().executeQuery("SELECT SIS_ID, STG_ID FROM CCS_STAGE_FREIGHT_OTHERS WHERE FOT_ID = "+idOtherFreight+"");
			String idSiteSource = "";
			String idStage      = "";
			
			if (rs.next()){
				idSiteSource = rs.getString(1);
				idStage      = rs.getString(2);
			}
			
			String queryCount ="SELECT COUNT(*) FROM( "+ 
					  "SELECT OTH.FOT_ID, SIS.SIS_NAME, OTH.FOT_PALETTES, OTH.TRA_ID, SUM(CSP.OCP_RATE), OTH.SFE_ID, OTH.FOT_ORDERS, OTH.FOT_INCREASE " +
					  "FROM   CCS_STAGE_FREIGHT_OTHERS OTH, CCS_STAGE_FREIGHT_OTHERS_CSP CSP, CCS_SITE_SOURCE SIS " +
					  "WHERE  OTH.STG_ID = "+idStage+" " +
					  "AND    OTH.FOT_ID = CSP.FOT_ID " +
					  "AND    OTH.SIS_ID = SIS.SIS_ID " +
					  "GROUP BY OTH.FOT_ID, SIS.SIS_NAME, OTH.FOT_PALETTES, OTH.TRA_ID, OTH.SFE_ID, OTH.FOT_ORDERS, OTH.FOT_INCREASE " +
					  "ORDER BY OTH.FOT_ID )";
			logger.debug("queryCount: "+queryCount);
			int totalRow=0;
			ResultSet rsCount = conn.createStatement().executeQuery(queryCount);
			if(rsCount.next())
				totalRow=rsCount.getInt(1);
			logger.debug("totalRow: "+totalRow);

			if(totalRow == 1)
				throw new DAOException("El registro no puede ser eliminado, debe existir como minimo uno vigente.");
			
			String queryExecute= "SELECT 1 FROM CCS_LOCAL_ITEM_MASTER WHERE STG_ID = "+idStage+" AND SIS_ID = "+idSiteSource;
			ResultSet rsExistItemMaster = conn.createStatement().executeQuery(queryExecute);
			if (rsExistItemMaster.next())
				throw new DAOException(ConstantsFinalObject.E_DAO_PRODUCT_FREIGHT_IS);
			
			String query = "DELETE FROM CCS_STAGE_FREIGHT_OTHERS_CSP WHERE FOT_ID = "+idOtherFreight+"";
			logger.debug("query: "+query);
			conn.createStatement().executeUpdate(query);
			
			
			query = "DELETE FROM CCS_STAGE_FREIGHT_OTHERS WHERE FOT_ID = "+idOtherFreight+"";
			logger.debug("query: "+query);
			conn.createStatement().executeUpdate(query);
			
			
			
			/*Generando sentencias de eliminaciï¿½n para Other Freight*/
			query = "DELETE FROM CCS_SITE_SOURCE WHERE SIS_ID = "+idSiteSource+"";
			logger.debug("query: "+query);
			conn.createStatement().executeUpdate(query);
			
			
			conn.commit();
		}catch (SQLException e) {
			try {
				e.printStackTrace();
				conn.rollback();
			} catch (SQLException e1) {
				throw new DAOException(ConstantsFinalObject.EXCEPTION_DAO_GENERIC_ERR_COD, e);
			}
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
	
	public List<RateWarehouseVO> listRateWarehouse(String idStage) throws DAOException{
		
		Connection conn = null;
		List<RateWarehouseVO> listRateWarehouseVO = new ArrayList<RateWarehouseVO>();
		try {
			conn = dataConnection.getConnectionOracleMSD();
			
			String query = "SELECT SSR_ID, SSR_NAME, SSR_RATE, STG_ID FROM CCS_SETUP_STORAGE_RATE WHERE STG_ID = "+idStage+" ORDER BY SSR_ID";
			logger.debug("query: "+query);
			ResultSet rs = conn.createStatement().executeQuery(query);
			
			while(rs.next()){
				RateWarehouseVO rateWarehouseVO = new RateWarehouseVO();
				
				rateWarehouseVO.setIdRateWarehouse(rs.getString(1));
				rateWarehouseVO.setNameRateWarehouse(rs.getString(2));
				rateWarehouseVO.setAmountRateWarehouse(rs.getFloat(3));
				
				listRateWarehouseVO.add(rateWarehouseVO);
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
		
		return listRateWarehouseVO;
	}
	
	public List<RangeWarehouseVO> listRangesWarehouse(String idStage) throws DAOException{
		
		List<RangeWarehouseVO> listRangeWarehouseVO = new ArrayList<RangeWarehouseVO>();
		Connection conn = null;
		
		try {
			conn = dataConnection.getConnectionOracleMSD();
			
			String queryExecute = "SELECT SSR_ID, SSR_RANGE_FROM, SSR_RANGE_TO, SSR_RANGE_RATE, CUR_ID FROM CCS_SETUP_STORAGE_RANGE WHERE STG_ID = "+idStage+"  ORDER BY SSR_RANGE_FROM ";
			
			logger.debug("queryExecute: "+queryExecute);

			
			ResultSet rs = conn.createStatement().executeQuery(queryExecute);
			while (rs.next()){

				RangeWarehouseVO rangeWarehouseVO = new RangeWarehouseVO();
				
				rangeWarehouseVO.setIdRangeWarehouse(rs.getString(1));
				rangeWarehouseVO.setFromRangeWarehouse(rs.getString(2));
				rangeWarehouseVO.setToRangeWarehouse(rs.getString(3));
				rangeWarehouseVO.setRateRangeWarehouse(rs.getFloat(4));
				rangeWarehouseVO.setCurrencyRangeWarehouse(rs.getString(5));

				
				listRangeWarehouseVO.add(rangeWarehouseVO);
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
		return listRangeWarehouseVO;
	}
	
	public List<RouteLocalTransportVO> listRoutesLocalTransport(String idStage) throws DAOException{
		
		List<RouteLocalTransportVO> listRouteLocalTransportVO = new ArrayList<RouteLocalTransportVO>();
		Connection conn = null;
		
		try {
			conn = dataConnection.getConnectionOracleMSD();
			
			String queryExecute = "SELECT SLR_ID, SLR_NAME, SLR_RATE FROM CCS_SETUP_LOCAL_ROUTE  WHERE STG_ID = "+idStage+" ORDER BY SLR_ID";
			
			logger.debug("queryExecute: "+queryExecute);

			
			ResultSet rs = conn.createStatement().executeQuery(queryExecute);
			while (rs.next()){

				RouteLocalTransportVO routeLocalTransportVO = new RouteLocalTransportVO();
				
				routeLocalTransportVO.setIdRouteLocalTransport(rs.getString(1));
				routeLocalTransportVO.setNameRouteLocalTransport(rs.getString(2));
				routeLocalTransportVO.setRateRouteLocalTransport(rs.getFloat(3));
				
				listRouteLocalTransportVO.add(routeLocalTransportVO);
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
		
		return listRouteLocalTransportVO;
	}
	
	
	public void updateRoutesLocalTransport(String attributeToUpdate, String valueAttribute, String idRouteLocalTransport) throws DAOException{

		Connection conn = null;
		try{
			conn = dataConnection.getConnectionOracleMSD();

			String updateAttribute = new String("");
			
			
			if (attributeToUpdate.equals(ConstantsFinalObject.ROUTE_LOCALTRANS_NAME))
				updateAttribute = "SLR_NAME='"+valueAttribute+"'";
			
			if (attributeToUpdate.equals(ConstantsFinalObject.ROUTE_LOCALTRANS_RATE))
				updateAttribute = "SLR_RATE = "+valueAttribute;
			
			String query = "UPDATE CCS_SETUP_LOCAL_ROUTE SET " +
					updateAttribute +" " 			
									  +	" WHERE SLR_ID = " + idRouteLocalTransport;
			logger.debug("qeUpdateItemMaster: "+query);
			
			conn.createStatement().executeUpdate(query);
			
			

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
	
	
	public void updateRangesLocalTransport(String attributeToUpdate, String valueAttribute, String idRangeLocalTransport) throws DAOException{

		Connection conn = null;
		try{
			conn = dataConnection.getConnectionOracleMSD();

			String updateAttribute = new String("");
			
			if (attributeToUpdate.equals(ConstantsFinalObject.RANGE_LOCALTRANS_FROM))
				updateAttribute = "SLR_RANGE_FROM='"+valueAttribute+"'";
			
			if (attributeToUpdate.equals(ConstantsFinalObject.RANGE_LOCALTRANS_TO))
				updateAttribute = "SLR_RANGE_TO = "+valueAttribute;
			
			if (attributeToUpdate.equals(ConstantsFinalObject.RANGE_LOCALTRANS_RATE))
				updateAttribute = "SLR_RANGE_RATE_KG = "+valueAttribute;
			
			if (attributeToUpdate.equals(ConstantsFinalObject.CUR_ID))
				updateAttribute = "CUR_ID = "+valueAttribute;
			
			String query = "UPDATE CCS_SETUP_LOCAL_RANGE SET " +
					updateAttribute +" " 			
									  +	" WHERE SLR_ID = " + idRangeLocalTransport;
			logger.debug("qeUpdateItemMaster: "+query);
			
			conn.createStatement().executeUpdate(query);

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
	
	
	public void replicateOperativeActivities(String idOperativeActivity) throws DAOException{

		Connection conn = null;
		try{
			conn = dataConnection.getConnectionOracleMSD();

			String query = new String("INSERT INTO CCS_SETUP_OPE_MANAGE(SOM_ID, SOM_ENTRY, SOM_PROCESSING_FEE, SOM_RATE_KG, CUR_ID, STG_ID, SOM_PERCENT_TP) " +
					"(SELECT CCS_SEQ_CCS_SETUP_OPE_MANAGE.NEXTVAL, SOM_ENTRY, SOM_PROCESSING_FEE, SOM_RATE_KG, CUR_ID, STG_ID, SOM_PERCENT_TP FROM CCS_SETUP_OPE_MANAGE WHERE SOM_ID = "+idOperativeActivity+")");
			
			conn.createStatement().executeUpdate(query);
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
	
	
	public void deleteOperativeActivities(String idOperativeActivity) throws DAOException{

		Connection conn = null;
		try{
			conn = dataConnection.getConnectionOracleMSD();
			
			String queryExecute = "SELECT STG_ID FROM CCS_SETUP_OPE_MANAGE WHERE SOM_ID = "+idOperativeActivity+" ";
			ResultSet rsStage = conn.createStatement().executeQuery(queryExecute);			
			String idStage      = "";
			if (rsStage.next()){				
				idStage      = rsStage.getString(1);
			}
						
			String queryCount = "SELECT COUNT(*) "
					+ "FROM CCS_SETUP_OPE_MANAGE "
			        + "WHERE STG_ID=" + idStage ;
			int totalRow=0;
			ResultSet rsCount = conn.createStatement().executeQuery(queryCount);
			if(rsCount.next())
				totalRow=rsCount.getInt(1);		
			
			logger.debug("totalRow: "+totalRow);	
			
			if(totalRow == 1){
				    logger.debug("El registro no puede ser eliminado, debe existir como minimo uno vigente.");
					throw new DAOException("El registro no puede ser eliminado, debe existir como minimo uno vigente.");
					}

			String query = "DELETE FROM CCS_SETUP_OPE_MANAGE WHERE SOM_ID = "+idOperativeActivity;
			
			conn.createStatement().executeUpdate(query);
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
	
	
	public void replicateRateWarehouse(String idRateStorage) throws DAOException{

		Connection conn = null;
		try{
			conn = dataConnection.getConnectionOracleMSD();

			String query = new String("INSERT INTO CCS_SETUP_STORAGE_RATE (SSR_ID, SSR_NAME, SSR_RATE, STG_ID)(SELECT CCS_SEQ_CCS_SETUP_STORAGE_RATE.NEXTVAL , SSR_NAME, SSR_RATE, STG_ID FROM CCS_SETUP_STORAGE_RATE WHERE SSR_ID = "+idRateStorage+")");
			
			conn.createStatement().executeUpdate(query);
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
	
	public void deleteRateWarehouse(String idRateStorage) throws DAOException{
		
		Connection conn = null;
		try{
			conn = dataConnection.getConnectionOracleMSD();
			
			String queryExecute = "SELECT STG_ID FROM CCS_SETUP_STORAGE_RATE WHERE SSR_ID = "+idRateStorage+" ";
			ResultSet rsStage = conn.createStatement().executeQuery(queryExecute);			
			String idStage      = "";
			if (rsStage.next()){				
				idStage      = rsStage.getString(1);
			}
			
			String queryCount = "SELECT COUNT(*) FROM CCS_SETUP_STORAGE_RATE WHERE STG_ID = "+idStage+" ORDER BY SSR_ID";
			int totalRow=0;
			
			ResultSet rsCount = conn.createStatement().executeQuery(queryCount);
			
			if(rsCount.next()){
				totalRow=rsCount.getInt(1);	}		
			logger.debug("totalRow: "+totalRow);	
			
			if(totalRow == 1){
				throw new DAOException("El registro no puede ser eliminado, debe existir como minimo uno vigente.");}

			String queryCount2 ="select 1 FROM CCS_LOCAL_ITEM_MASTER WHERE SSR_ID = "+idRateStorage; 
			ResultSet rsCount2 = conn.createStatement().executeQuery(queryCount2);
			if(rsCount2.next())
				throw new DAOException("El registro no puede ser eliminado, debido a una asociaciòn existente con Item Master ");
			
			
			
			String query = new String("DELETE FROM CCS_SETUP_STORAGE_RATE WHERE SSR_ID = "+idRateStorage+" ");
			
			conn.createStatement().executeUpdate(query);
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
	
	public void replicateRangeWarehouse(String idRangeStorage) throws DAOException{
		
		Connection conn = null;
		try{
			conn = dataConnection.getConnectionOracleMSD();
			
			String query = new String("INSERT INTO CCS_SETUP_STORAGE_RANGE (SSR_ID, SSR_RANGE_FROM, SSR_RANGE_TO, SSR_RANGE_RATE, STG_ID,  CUR_ID) " +
					"(SELECT CCS_SEQ_CCS_SETUP_STORAGE_RANG.NEXTVAL, SSR_RANGE_FROM, SSR_RANGE_TO, SSR_RANGE_RATE, STG_ID, CUR_ID FROM CCS_SETUP_STORAGE_RANGE WHERE SSR_ID = "+idRangeStorage+" )");
			
			conn.createStatement().executeUpdate(query);
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
	
	public void deleteRangeWarehouse(String idRangeStorage) throws DAOException{
		
		Connection conn = null;
		try{
			conn = dataConnection.getConnectionOracleMSD();
			
			String queryExecute = "SELECT STG_ID FROM CCS_SETUP_STORAGE_RANGE WHERE SSR_ID = "+idRangeStorage;
			ResultSet rsStage = conn.createStatement().executeQuery(queryExecute);			
			String idStage      = "";
			if (rsStage.next()){				
				idStage      = rsStage.getString(1);
			}

			String queryCount = "SELECT COUNT(*) FROM CCS_SETUP_STORAGE_RANGE WHERE STG_ID = "+idStage;
			int totalRow=0;
			ResultSet rsCount = conn.createStatement().executeQuery(queryCount);
			if(rsCount.next())
				totalRow=rsCount.getInt(1);			
			logger.debug("totalRow: "+totalRow);	
			if(totalRow == 1)
				throw new DAOException("El registro no puede ser eliminado, debe existir como minimo uno vigente.");
			
			String query = new String("DELETE FROM CCS_SETUP_STORAGE_RANGE WHERE SSR_ID = "+idRangeStorage+"");
			
			conn.createStatement().executeUpdate(query);
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
	
	public List<ActivePackOutFreightVO> listAPOFreights(String idStage) throws DAOException{
		
		Connection conn = null;
		
		List<ActivePackOutFreightVO> listActivePackOutFreightVO = new ArrayList<ActivePackOutFreightVO>();
		try{
			conn = dataConnection.getConnectionOracleMSD();
			
			String query = new String("SELECT APO.APO_ID, SIS.SIS_NAME, APO.APO_INCREASE, APO.APO_AWB_RATE, APO.APO_OTHER_FEE, APO.APO_FUEL_SURCHARGE, APO.APO_SECURITY_CHARGE, " +
					"APO.APO_ORDERS, APO.APO_PALETTES, APO.SFE_ID, APO.SIS_ID, APO.APO_INCREASE_AWB, APO.APO_INCREASE_FFW, APO.APO_INCREASE_FSC, APO.APO_INCREASE_SCC " +
					"FROM CCS_STAGE_FREIGHT_APO APO, CCS_SITE_SOURCE SIS " +
					"WHERE APO.STG_ID = "+idStage+" " +
					"AND APO.SIS_ID = SIS.SIS_ID");
			
			logger.debug("query: "+query);
			
			ResultSet rs = conn.createStatement().executeQuery(query);
			
			while (rs.next()){
				ActivePackOutFreightVO activePackOutFreightVO = new ActivePackOutFreightVO();
				
				activePackOutFreightVO.setIdActivePackOut(rs.getString(1));
				activePackOutFreightVO.setNameActivePackOut(rs.getString(2));
				activePackOutFreightVO.setIncreaseActivePackOut(rs.getFloat(3));
				activePackOutFreightVO.setAwbRateActivePackOut(rs.getFloat(4));
				activePackOutFreightVO.setOtherFeeActivePackOut(rs.getFloat(5));
				activePackOutFreightVO.setFuelSurchargeActivePackOut(rs.getFloat(6));
				activePackOutFreightVO.setSecurityChargeActivePackOut(rs.getFloat(7));
				activePackOutFreightVO.setOrdersActivePackOut(rs.getFloat(8));
				activePackOutFreightVO.setPalettesActivePackOut(rs.getFloat(9));
				activePackOutFreightVO.setIdCurrencyActivePackOut(rs.getString(10));
				
				activePackOutFreightVO.setIncreaseAWBActivePackOut(rs.getFloat(12));
				activePackOutFreightVO.setIncreaseFFWActivePackOut(rs.getFloat(13));
				activePackOutFreightVO.setIncreaseFSCActivePackOut(rs.getFloat(14));
				activePackOutFreightVO.setIncreaseSCCActivePackOut(rs.getFloat(15));
				
				listActivePackOutFreightVO.add(activePackOutFreightVO);
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
		
		return listActivePackOutFreightVO;
	}
	
	public List<ConceptCostAPOFreightVO> listConceptCostApoFreight(String idAPOFreight) throws DAOException{
		
		Connection conn = null;
		
		List<ConceptCostAPOFreightVO> listConceptCostAPOFreightVO = new ArrayList<ConceptCostAPOFreightVO>();
		try{
			conn = dataConnection.getConnectionOracleMSD();
			
			String query = new String("SELECT APR_ID, APR_CONCEPT, APR_RATE, APO_ID FROM CCS_STAGE_FREIGHT_APO_RATE WHERE APO_ID = "+idAPOFreight+" ");
			
			ResultSet rs = conn.createStatement().executeQuery(query);
			
			while (rs.next()){
				ConceptCostAPOFreightVO conceptCostAPOFreightVO = new ConceptCostAPOFreightVO();
				
				conceptCostAPOFreightVO.setIdConceptAPOFreight(rs.getString(1));
				conceptCostAPOFreightVO.setNameConceptAPOFreight(rs.getString(2));
				conceptCostAPOFreightVO.setAmountConceptAPOFreight(rs.getFloat(3));
				conceptCostAPOFreightVO.setIdAPOFreight(rs.getString(4));
				
				listConceptCostAPOFreightVO.add(conceptCostAPOFreightVO);
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
		
		return listConceptCostAPOFreightVO;
	}
	
	public void replicateConceptCostWarehouse(String idConceptCostWarehouse) throws DAOException{
		
		Connection conn = null;
		try{
			conn = dataConnection.getConnectionOracleMSD();
			
			String query = new String("INSERT INTO CCS_SETUP_STORAGE_TEMP( SST_ID, SST_ENTRY, SST_PROCESSING_FEE, STG_ID, CUR_ID)( " +
									  "SELECT CCS_SEQ_CCS_SETUP_STORAGE_TEMP.NEXTVAL, SST_ENTRY, SST_PROCESSING_FEE, STG_ID, CUR_ID FROM CCS_SETUP_STORAGE_TEMP WHERE SST_ID = "+idConceptCostWarehouse+" )");
			
			conn.createStatement().executeUpdate(query);
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
	
	public void deleteConceptCostWarehouse(String idConceptCostWarehouse) throws DAOException{
		
		Connection conn = null;
		try{
			conn = dataConnection.getConnectionOracleMSD();
			
			String queryExecute = "SELECT STG_ID FROM CCS_SETUP_STORAGE_TEMP WHERE SST_ID = "+idConceptCostWarehouse;
			ResultSet rsStage = conn.createStatement().executeQuery(queryExecute);			
			String idStage      = "";
			if (rsStage.next()){				
				idStage      = rsStage.getString(1);
			}

			String queryCount = "SELECT COUNT(*)  "
								+ "FROM CCS_SETUP_STORAGE_TEMP "
								+ "WHERE STG_ID=" + idStage;
			int totalRow=0;
			ResultSet rsCount = conn.createStatement().executeQuery(queryCount);
			if(rsCount.next())
				totalRow=rsCount.getInt(1);			
			logger.debug("totalRow: "+totalRow);	
			if(totalRow == 1)
				throw new DAOException("El registro no puede ser eliminado, debe existir como minimo uno vigente.");
			
			String query = new String("DELETE FROM CCS_SETUP_STORAGE_TEMP WHERE SST_ID = "+idConceptCostWarehouse+" ");
			
			conn.createStatement().executeUpdate(query);
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
	
	public void replicateRouteLocalTransport(String idRouteLocalTransport) throws DAOException{
		
		Connection conn = null;
		try{
			conn = dataConnection.getConnectionOracleMSD();
			
			String query = new String("INSERT INTO CCS_SETUP_LOCAL_ROUTE(SLR_ID, SLR_NAME, STG_ID, SLR_RATE) " +
					"(SELECT CCS_SEQ_SETUP_LOCAL_ROUTE.NEXTVAL, SLR_NAME, STG_ID, SLR_RATE FROM CCS_SETUP_LOCAL_ROUTE WHERE SLR_ID = "+idRouteLocalTransport+")");
			
			logger.debug("query: "+query);
			conn.createStatement().executeUpdate(query);
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
	
	public void deleteRouteLocalTransport(String idRouteLocalTransport) throws DAOException{
		
		Connection conn = null;
		try{
			conn = dataConnection.getConnectionOracleMSD();
			
			String queryExecute = "SELECT STG_ID FROM CCS_SETUP_LOCAL_ROUTE WHERE SLR_ID = "+idRouteLocalTransport;
			ResultSet rsStage = conn.createStatement().executeQuery(queryExecute);			
			String idStage      = "";
			if (rsStage.next()){				
				idStage      = rsStage.getString(1);
			}

			String queryCount = "SELECT COUNT(*) FROM CCS_SETUP_LOCAL_ROUTE  WHERE STG_ID = "+idStage;
			int totalRow=0;
			ResultSet rsCount = conn.createStatement().executeQuery(queryCount);
			if(rsCount.next())
				totalRow=rsCount.getInt(1);			
			logger.debug("totalRow: "+totalRow);	
			if(totalRow == 1)
				throw new DAOException("El registro no puede ser eliminado, debe existir como minimo uno vigente.");
			
			
			
			
			String queryCount2 ="select 1 FROM CCS_local_item_master WHERE SLR_ID = "+idRouteLocalTransport; 
			ResultSet rsCount2 = conn.createStatement().executeQuery(queryCount2);
			if(rsCount2.next())
				throw new DAOException("El registro no puede ser eliminado, debido a una asociaciòn existente con Item Master ");
			
			
			
			
			String query = new String("DELETE FROM CCS_SETUP_LOCAL_ROUTE WHERE SLR_ID = "+idRouteLocalTransport+" ");
			
			
			logger.debug("query: "+query);
			conn.createStatement().executeUpdate(query);
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
	
	
	public void replicateRangeLocalTransport(String idRangeLocalTransport) throws DAOException{
		
		Connection conn = null;
		try{
			conn = dataConnection.getConnectionOracleMSD();
			
			String query = new String("	INSERT INTO CCS_SETUP_LOCAL_RANGE(SLR_ID, SLR_RANGE_FROM,  SLR_RANGE_TO, SLR_RANGE_RATE_KG, STG_ID, CUR_ID) " +
					"(SELECT CCS_SEQ_CCS_SETUP_LOCAL_RANGE.NEXTVAL, SLR_RANGE_FROM, SLR_RANGE_TO, SLR_RANGE_RATE_KG, STG_ID, CUR_ID FROM CCS_SETUP_LOCAL_RANGE WHERE SLR_ID = "+idRangeLocalTransport+" )");
			
			logger.debug("query: "+query);
			conn.createStatement().executeUpdate(query);
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
	
	public void deleteRangeLocalTransport(String idRangeLocalTransport) throws DAOException{
		
		Connection conn = null;
		try{
			conn = dataConnection.getConnectionOracleMSD();
			
			String queryExecute = "SELECT STG_ID FROM CCS_SETUP_LOCAL_RANGE WHERE SLR_ID = "+idRangeLocalTransport+" ";
			ResultSet rsStage = conn.createStatement().executeQuery(queryExecute);			
			String idStage      = "";
			if (rsStage.next()){				
				idStage      = rsStage.getString(1);
			}

			String queryCount = "SELECT COUNT(*) "
								+ "	FROM CCS_SETUP_LOCAL_RANGE "
								+ " WHERE STG_ID="+idStage +" ORDER BY SLR_ID";
			int totalRow=0;
			ResultSet rsCount = conn.createStatement().executeQuery(queryCount);
			if(rsCount.next())
				totalRow=rsCount.getInt(1);			
			logger.debug("totalRow: "+totalRow);	
			if(totalRow == 1)
				throw new DAOException("El registro no puede ser eliminado, debe existir como minimo uno vigente.");
			
			String query = new String("DELETE FROM CCS_SETUP_LOCAL_RANGE WHERE SLR_ID = "+idRangeLocalTransport+" ");
			
			logger.debug("query: "+query);
			conn.createStatement().executeUpdate(query);
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
	
	public void replicateConceptCostLocalTransport(String idConceptCostLocalTransport) throws DAOException{
		
		Connection conn = null;
		try{
			conn = dataConnection.getConnectionOracleMSD();
			
			String query = new String("INSERT INTO CCS_SETUP_LOCAL_TRANSPORT(SLT_ID, SLT_ENTRY, SLT_PROCESSING_FEE, STG_ID, CUR_ID, SLR_ID) " +
					"(SELECT CCS_SEQ_CCS_SETUP_LOCAL_TRANSP.NEXTVAL, SLT_ENTRY, SLT_PROCESSING_FEE, STG_ID, CUR_ID, SLR_ID FROM CCS_SETUP_LOCAL_TRANSPORT WHERE SLT_ID = "+idConceptCostLocalTransport+" ) ");
			
			logger.debug("query: "+query);
			conn.createStatement().executeUpdate(query);
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
	
	public void deleteConceptCostLocalTransport(String idConceptCostLocalTransport) throws DAOException{
		
		Connection conn = null;
		try{
			conn = dataConnection.getConnectionOracleMSD();
			
			String queryExecute = "SELECT STG_ID FROM CCS_SETUP_LOCAL_TRANSPORT WHERE SLT_ID = "+idConceptCostLocalTransport;
			ResultSet rsStage = conn.createStatement().executeQuery(queryExecute);			
			String idStage      = "";
			if (rsStage.next()){				
				idStage      = rsStage.getString(1);
			}

			String queryCount = "SELECT COUNT(*) "
								+ "FROM CCS_SETUP_LOCAL_TRANSPORT "
								+ "WHERE STG_ID=" + idStage;
			int totalRow=0;
			ResultSet rsCount = conn.createStatement().executeQuery(queryCount);
			if(rsCount.next())
				totalRow=rsCount.getInt(1);			
			logger.debug("totalRow: "+totalRow);	
			if(totalRow == 1)
				throw new DAOException("El registro no puede ser eliminado, debe existir como minimo uno vigente.");
			
			String query = new String("DELETE FROM CCS_SETUP_LOCAL_TRANSPORT WHERE SLT_ID = "+idConceptCostLocalTransport+" ");
			
			logger.debug("query: "+query);
			conn.createStatement().executeUpdate(query);
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
	
	
	public void replicateAPOFreight(String idAPOFreight) throws DAOException{
		
		Connection conn = null;
		try{
			conn = dataConnection.getConnectionOracleMSD();
			conn.setAutoCommit(false);
			
			ResultSet rs = conn.createStatement().executeQuery("SELECT CCS_SEQ_STAGE_FREIGHT_APO.NEXTVAL FROM DUAL");
			
			String idApoFreight = rs.next()?rs.getString(1):"";
			
			rs = conn.createStatement().executeQuery("SELECT CCS_SEQ_CCS_SITE_SOURCE.NEXTVAL FROM DUAL");
			String idSiteSourceNext = rs.next()==true?rs.getString(1):"";

			String queryExecute = "INSERT INTO CCS_SITE_SOURCE (SIS_ID, SIS_NAME, STG_ID, TRA_ID)(SELECT "+idSiteSourceNext+", SIS_NAME, STG_ID, TRA_ID FROM CCS_SITE_SOURCE WHERE SIS_ID = (SELECT SIS_ID FROM CCS_STAGE_FREIGHT_APO WHERE APO_ID = "+idAPOFreight+"))";
			conn.createStatement().executeUpdate(queryExecute);
			
			String query = new String("INSERT INTO CCS_STAGE_FREIGHT_APO( APO_ID, APO_INCREASE, APO_AWB_RATE, APO_OTHER_FEE, APO_FUEL_SURCHARGE, APO_SECURITY_CHARGE, APO_ORDERS, APO_PALETTES, SFE_ID,  STG_ID, SIS_ID, APO_INCREASE_AWB, APO_INCREASE_FFW, APO_INCREASE_FSC, APO_INCREASE_SCC) "
					+ "(SELECT "+idApoFreight+", APO_INCREASE, APO_AWB_RATE,  APO_OTHER_FEE, APO_FUEL_SURCHARGE, APO_SECURITY_CHARGE, APO_ORDERS, APO_PALETTES, SFE_ID, STG_ID, "+idSiteSourceNext+", APO_INCREASE_AWB, APO_INCREASE_FFW, APO_INCREASE_FSC, APO_INCREASE_SCC "
					+ "FROM CCS_STAGE_FREIGHT_APO WHERE APO_ID = "+idAPOFreight+")");
			
			logger.debug("query: "+query);
			conn.createStatement().executeUpdate(query);
			
			
			query = new String("INSERT INTO CCS_STAGE_FREIGHT_APO_RATE(APR_ID, APR_CONCEPT, APR_RATE, STG_ID, APO_ID) "
					+ "(SELECT CCS_SEQ_CCS_APO_RATE.NEXTVAL, APR_CONCEPT, APR_RATE, STG_ID, "+idApoFreight+" FROM CCS_STAGE_FREIGHT_APO_RATE WHERE APO_ID = "+idAPOFreight+")");
			logger.debug("query: "+query);
			conn.createStatement().executeUpdate(query);
			
			
			conn.commit();
		}catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				throw new DAOException(ConstantsFinalObject.EXCEPTION_DAO_GENERIC_ERR_COD, e);
			}
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
	
	
	public void deleteAPOFreight(String idAPOFreight) throws DAOException{
		
		Connection conn = null;
		try{
			conn = dataConnection.getConnectionOracleMSD();
			conn.setAutoCommit(false);
			
			ResultSet rs = conn.createStatement().executeQuery("SELECT SIS_ID, STG_ID FROM CCS_STAGE_FREIGHT_APO WHERE APO_ID = "+idAPOFreight+"");
			String idSiteSource = "";
			String idStage      = "";
			
			if (rs.next()){
				idSiteSource = rs.getString(1);
				idStage      = rs.getString(2);
			}
			
			String query = new String("SELECT COUNT(*) " +
					"FROM CCS_STAGE_FREIGHT_APO APO, CCS_SITE_SOURCE SIS " +
					"WHERE APO.STG_ID = "+idStage+" " +
					"AND APO.SIS_ID = SIS.SIS_ID");
			logger.debug("query: "+query);
			
			int totalRow=0;
			ResultSet rsCount = conn.createStatement().executeQuery(query);
			if(rsCount.next())
				totalRow=rsCount.getInt(1);
			
			

			if(totalRow == 1)
				throw new DAOException("El registro no puede ser eliminado, debe existir como minimo uno vigente.");
			
			
			

			
			String queryExecute= "SELECT 1 FROM CCS_LOCAL_ITEM_MASTER WHERE STG_ID = "+idStage+" AND SIS_ID = "+idSiteSource;
			ResultSet rsExistItemMaster = conn.createStatement().executeQuery(queryExecute);
			if (rsExistItemMaster.next())
				throw new DAOException(ConstantsFinalObject.E_DAO_PRODUCT_FREIGHT_IS);
			
			query = new String("SELECT SIS_ID FROM CCS_STAGE_FREIGHT_APO WHERE APO_ID = "+idAPOFreight+"");
			
			
			
			
			query = new String("DELETE FROM CCS_STAGE_FREIGHT_APO_RATE WHERE APO_ID = "+idAPOFreight+" ");
			logger.debug("query: "+query);
			conn.createStatement().executeUpdate(query);
			
			
			query = new String("DELETE FROM CCS_STAGE_FREIGHT_APO WHERE APO_ID = "+idAPOFreight+" ");
			logger.debug("query: "+query);
			conn.createStatement().executeUpdate(query);
			
			
			query = new String("DELETE FROM CCS_SITE_SOURCE WHERE SIS_ID = "+idSiteSource+" ");
			logger.debug("query: "+query);
			conn.createStatement().executeUpdate(query);
			
			conn.commit();
		}catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				throw new DAOException(ConstantsFinalObject.EXCEPTION_DAO_GENERIC_ERR_COD, e);
			}
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
	
	
	
	public void replicateAPOFreightRate(String idRateAPOFreight) throws DAOException{
		
		Connection conn = null;
		try{
			conn = dataConnection.getConnectionOracleMSD();
			conn.setAutoCommit(false);
			
			String query = new String("INSERT INTO CCS_STAGE_FREIGHT_APO_RATE(APR_ID, APR_CONCEPT, APR_RATE, STG_ID, APO_ID) "
					+ "(SELECT CCS_SEQ_CCS_APO_RATE.NEXTVAL, APR_CONCEPT, APR_RATE, STG_ID, APO_ID FROM CCS_STAGE_FREIGHT_APO_RATE WHERE APR_ID = "+idRateAPOFreight+")");
			logger.debug("query: "+query);
			conn.createStatement().executeUpdate(query);
			
			conn.commit();
		}catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				throw new DAOException(ConstantsFinalObject.EXCEPTION_DAO_GENERIC_ERR_COD, e);
			}
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
	
	
	public void deleteAPOFreightRate(String idRateAPOFreight) throws DAOException{
		
		Connection conn = null;
		try{
			conn = dataConnection.getConnectionOracleMSD();
			conn.setAutoCommit(false);
			
			
			
			
			
			
			String query = new String("DELETE FROM CCS_STAGE_FREIGHT_APO_RATE WHERE APR_ID = "+idRateAPOFreight+" ");
			logger.debug("query: "+query);
			conn.createStatement().executeUpdate(query);
			
			conn.commit();
		}catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				throw new DAOException(ConstantsFinalObject.EXCEPTION_DAO_GENERIC_ERR_COD, e);
			}
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
	
	
	public void replicateExchangeCurrency(String idExchangeCurrency) throws DAOException{

		Connection conn = null;
		try{
			conn = dataConnection.getConnectionOracleMSD();

			String query = new String("INSERT INTO CCS_STAGE_FREIGHT_EXCHANGE(SFE_ID, SFE_NAME, SFE_RATE, STG_ID, SFE_ACRONYM)( SELECT CCS_SEQ_SFE.NEXTVAL, SFE_NAME, SFE_RATE, STG_ID, SFE_ACRONYM FROM CCS_STAGE_FREIGHT_EXCHANGE WHERE SFE_ID = "+idExchangeCurrency+")");
			logger.debug("query: "+query);
			conn.createStatement().executeUpdate(query);
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
	
	
	public void deleteExchangeCurrency(String idExchangeCurrency) throws DAOException{

		Connection conn = null;
		try{
			conn = dataConnection.getConnectionOracleMSD();
			ResultSet rs = conn.createStatement().executeQuery("SELECT STG_ID FROM CCS_STAGE_FREIGHT_EXCHANGE WHERE SFE_ID = "+idExchangeCurrency+"");			
			String idStage      = "";
			if (rs.next()){				
				idStage      = rs.getString(1);
			}
			
			String queryExecute = "SELECT COUNT(*) FROM CCS_STAGE_FREIGHT_EXCHANGE EXG WHERE STG_ID = "+idStage+" AND EXG.SFE_ACRONYM <> 'USD' ORDER BY 1 ";
			logger.debug("queryExecute: "+queryExecute);
			int totalRow=0;
			ResultSet rsCount = conn.createStatement().executeQuery(queryExecute);
			if(rsCount.next())
				totalRow=rsCount.getInt(1);			
			logger.debug("totalRow: "+totalRow);	
			if(totalRow == 1)
				throw new DAOException("El registro no puede ser eliminado, debe existir como minimo uno vigente.");
			
			String queryCount2 ="select 1 FROM CCS_STAGE_FREIGHT_EXCHANGE WHERE SFE_ID = "+idExchangeCurrency; 
			ResultSet rsCount2 = conn.createStatement().executeQuery(queryCount2);
			if(rsCount2.next())
				throw new DAOException("El registro no puede ser eliminado, debido a una asociaciòn existente con Stage Freight Exchange");
			
			
			
			
			
			
			String query = new String("DELETE FROM CCS_STAGE_FREIGHT_EXCHANGE WHERE SFE_ID = "+idExchangeCurrency+"");
			logger.debug("query: "+query);
			conn.createStatement().executeUpdate(query);
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
	
	
	public void replicateLocalDuty(String idLocalDuty) throws DAOException{

		Connection conn = null;
		try{
			conn = dataConnection.getConnectionOracleMSD();

			String query = new String("INSERT INTO CCS_SETUP_CUSTUM_DUTIES_DETAIL(SDD_ID, SDD_DUTY_NAME, SDD_DUTY_TARIFF, SDD_OTHER_TAXES, SDD_COD, STG_ID)(SELECT CCS_SEQ_CCS_SETUP_CUS_DU_DET.NEXTVAL, SDD_DUTY_NAME, SDD_DUTY_TARIFF, SDD_OTHER_TAXES, 'ZXC', STG_ID FROM CCS_SETUP_CUSTUM_DUTIES_DETAIL WHERE SDD_ID = "+idLocalDuty+")");
			logger.debug("query: "+query);
			conn.createStatement().executeUpdate(query);
			
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
	
	public void deleteLocalDuty(String idLocalDuty) throws DAOException{

		Connection conn = null;
		try{
			conn = dataConnection.getConnectionOracleMSD();
			
			String queryExecute = "SELECT STG_ID FROM CCS_SETUP_CUSTUM_DUTIES_DETAIL WHERE SDD_ID = "+idLocalDuty+"  ";
			ResultSet rsStage = conn.createStatement().executeQuery(queryExecute);			
			String idStage      = "";
			if (rsStage.next()){				
				idStage      = rsStage.getString(1);
			}
			
			String queryCount = "SELECT COUNT(*) FROM CCS_SETUP_CUSTUM_DUTIES_DETAIL WHERE STG_ID = "+idStage+"  ";
			int totalRow=0;
			ResultSet rsCount = conn.createStatement().executeQuery(queryCount);
			if(rsCount.next())
				totalRow=rsCount.getInt(1);			
			logger.debug("totalRow: "+totalRow);	
			if(totalRow == 1)
				throw new DAOException("El registro no puede ser eliminado, debe existir como minimo uno vigente.");
			
			String query = "SELECT 1 FROM CCS_LOCAL_ITEM_MASTER WHERE STG_ID = (SELECT STG_ID FROM CCS_SETUP_CUSTUM_DUTIES_DETAIL WHERE SDD_ID = "+idLocalDuty+") AND SDD_COD = (SELECT SDD_COD FROM CCS_SETUP_CUSTUM_DUTIES_DETAIL WHERE STG_ID = (SELECT STG_ID FROM CCS_SETUP_CUSTUM_DUTIES_DETAIL WHERE SDD_ID = "+idLocalDuty+") AND SDD_ID = "+idLocalDuty+" ) ";
			logger.debug("qeUpdateItemMaster: "+query);
			ResultSet rs = conn.createStatement().executeQuery(query);
			
			if (rs.next())
				throw new DAOException(ConstantsFinalObject.E_DAO_EXIST_CODE_DUTY_IM);
			

			query = new String("DELETE FROM CCS_SETUP_CUSTUM_DUTIES_DETAIL WHERE SDD_ID = "+idLocalDuty+" ");
			logger.debug("query: "+query);
			conn.createStatement().executeUpdate(query);
			
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
	
}