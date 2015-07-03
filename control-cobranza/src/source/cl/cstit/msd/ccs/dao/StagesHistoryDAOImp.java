package cl.cstit.msd.ccs.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.components.Debug;

import cl.cstit.msd.ccs.exception.DAOException;
import cl.cstit.msd.ccs.utils.ConnectionFactory;
import cl.cstit.msd.ccs.utils.ConstantsFinalObject;
import cl.cstit.msd.ccs.utils.FormatUtilities;
import cl.cstit.msd.ccs.vo.FiltroPagoCobranzaVO;
import cl.cstit.msd.ccs.vo.PagoCobranzaVO;
import cl.cstit.msd.ccs.vo.SiteVO;
import cl.cstit.msd.ccs.vo.StageVO;

public class StagesHistoryDAOImp{
	
	private ConnectionFactory dataConnection = null;
	private static Logger logger = null;
	
	public StagesHistoryDAOImp(){
		dataConnection = ConnectionFactory.getInstance();	
		logger = Logger.getLogger(StagesHistoryDAOImp.class);
	}

	
	
	//-----------------------------------
	
	
    public PagoCobranzaVO cambiaEstado(PagoCobranzaVO idEstadoFactura) throws DAOException{ 
    	
    	Connection conn = null;
    	
//    	List<PagoCobranzaVO> listPagoVO = new ArrayList<PagoCobranzaVO>();
    	
    	try {

    		
    		conn = dataConnection.getConnectionMysql();
    		String query   			= "";
    		String facturaEstado	=  idEstadoFactura.getFactura();
    		String facturaRut		=  idEstadoFactura.getRut();
    		logger.debug("Factura: "+facturaEstado);

    		query = "UPDATE flt_pago_proveedores SET pap_estado_proveedor='PAGADO' WHERE pap_numero_factura_proveedor='"+facturaEstado+"' AND pap_rut_proveedor='"+facturaRut+"' ;";
			logger.debug("query: "+query);
			conn.createStatement().executeUpdate(query);
		
			
//			query = " SELECT  pap_rut_proveedor, pap_fecha_vencimiento_proveedor, pap_monto_pago_proveedor, pap_numero_factura_proveedor, pap_estado_proveedor, nombre_proveedor "
//					+ " FROM flt_pago_proveedores, flt_proveedor "
//					+ " WHERE flt_proveedor.rut_proveedor = flt_pago_proveedores.pap_rut_proveedor ";
			
			
			
			
		}catch (SQLException e) {
//			e.printStackTrace();
			throw new DAOException(ConstantsFinalObject.EXCEPTION_DAO_GENERIC_ERR_COD, e);		
		}finally{		
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					throw new DAOException(ConstantsFinalObject.EXCEPTION_DAO_GENERIC_ERR_COD, e);
				}
		}
//		return idEstadoFactura;
//    	return listPagoVO;
		return idEstadoFactura;
    	
    }
	
	
	//--------------------------------------
	
	
	
	
	

	
    public List<PagoCobranzaVO> listPagoCobranza(FiltroPagoCobranzaVO filtroPagoCobranzaVO) throws DAOException{ //String idTypeStage
    	
    	Connection conn = null;

    	List<PagoCobranzaVO> listPagoVO = new ArrayList<PagoCobranzaVO>();
    	
    	try {

    		
    		conn = dataConnection.getConnectionMysql();
    		String query   = "";
    		String factura = filtroPagoCobranzaVO.getFactura();
    		String estado  = filtroPagoCobranzaVO.getEstado();
    		
    		logger.debug("Estado: "+estado);
    		logger.debug("Factura: "+factura);
    	    		
    		if(factura.equals("") && estado.equals("") ){
    			query = " SELECT  pap_rut_proveedor, pap_fecha_vencimiento_proveedor, pap_monto_pago_proveedor, pap_numero_factura_proveedor, pap_estado_proveedor, nombre_proveedor, pap_observacion_proveedor "
    					+ " FROM flt_pago_proveedores, flt_proveedor "
    					+ " WHERE flt_proveedor.rut_proveedor = flt_pago_proveedores.pap_rut_proveedor ";
    		}
    		if(!factura.equals("") && !estado.equals("")){
    			query = " SELECT  pap_rut_proveedor, pap_fecha_vencimiento_proveedor, pap_monto_pago_proveedor, pap_numero_factura_proveedor, pap_estado_proveedor, nombre_proveedor, pap_observacion_proveedor "
    					+ " FROM flt_pago_proveedores, flt_proveedor "
    					+ " WHERE flt_proveedor.rut_proveedor = flt_pago_proveedores.pap_rut_proveedor "
    					+ " AND pap_estado_proveedor='"+estado+"' "
    					+ " AND pap_numero_factura_proveedor = '"+factura+"' ";
    		}
    		if(factura.equals("") && !estado.equals("")){
    			query = " SELECT  pap_rut_proveedor, pap_fecha_vencimiento_proveedor, pap_monto_pago_proveedor, pap_numero_factura_proveedor, pap_estado_proveedor, nombre_proveedor, pap_observacion_proveedor "
    					+ " FROM flt_pago_proveedores, flt_proveedor "
    					+ " WHERE flt_proveedor.rut_proveedor = flt_pago_proveedores.pap_rut_proveedor "
    					+ " AND pap_estado_proveedor='"+estado+"' ";
    		}
    		if(!factura.equals("") && estado.equals("") ){
    			query = " SELECT  pap_rut_proveedor, pap_fecha_vencimiento_proveedor, pap_monto_pago_proveedor, pap_numero_factura_proveedor, pap_estado_proveedor, nombre_proveedor, pap_observacion_proveedor "
    					+ " FROM flt_pago_proveedores, flt_proveedor "
    					+ " WHERE flt_proveedor.rut_proveedor = flt_pago_proveedores.pap_rut_proveedor "
    					+ " AND pap_numero_factura_proveedor = '"+factura+"' ";
    		}
			
			logger.debug("query: "+query);
    		
			ResultSet rs = conn.createStatement().executeQuery(query);
			
			while(rs.next()){
				
				PagoCobranzaVO pagoCobranzaVO =new PagoCobranzaVO();
				
				pagoCobranzaVO.setRut(rs.getString(1));
				pagoCobranzaVO.setNombre(rs.getString(6));
				pagoCobranzaVO.setFechaV(rs.getString(2));
				pagoCobranzaVO.setObservacion(rs.getString(7));
				pagoCobranzaVO.setMonto(rs.getFloat(3));
				pagoCobranzaVO.setFactura(rs.getString(4));
				pagoCobranzaVO.setEstado(rs.getString(5));

				
				listPagoVO.add(pagoCobranzaVO);
				
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
    	
    	return listPagoVO;
    }
	
    
	
	
	
//	public List<StageVO> listStages(List<SiteVO> listSiteVO, String idTypeStage, String idProfileUser) throws DAOException{ 
//    	
//    	Connection conn = null;
//    	List<StageVO> listStageVO = new ArrayList<StageVO>();
//    	try {
//    		
//    		String idSites = "0";
//    		for(SiteVO siteVO: listSiteVO){
//    			idSites = idSites.concat(","+siteVO.getIdSite());
//    		}
//    		
//    		
//    		String filterOnlyApprover = "";
//    		if (idProfileUser.equals("2"))
//    			filterOnlyApprover = "AND STG.STS_ID IN (9,10) ";
//    		
//    		conn = dataConnection.getConnectionOracleMSD();
//
//			String qeListStages = "SELECT STG.STG_ID, STG.SGT_NAME, STG.STG_DATE_CREATION, STG.STG_DATE_LAST_UPDATE, STT_ID.STT_NAME, STS.STS_NAME, STG.SIT_ID, STG.USR_ISID, STG.STG_DESCRIPTION, STG.STS_ID, STG.STT_ID, STG.STG_PERIOD, SIT.SIT_NAME " +
//								  "FROM CCS_STAGE STG, CCS_STAGE_TYPE STT_ID, CCS_STAGE_STATE STS, CCS_SITE SIT " +
//								  "WHERE STG.STT_ID = STT_ID.STT_ID " +
//								  "AND   STG.STS_ID = STS.STS_ID " +
//								  "AND   STG.SIT_ID = SIT.SIT_ID " +
//								  "AND   STG.SIT_ID IN ("+idSites+") " +
//								  filterOnlyApprover +
//								  "AND   (STG.STT_ID = '"+idTypeStage+"' OR '"+idTypeStage+"' is null) " +
//								  "ORDER BY 1 DESC";
//			
//			
//			logger.debug("qeListStages: "+qeListStages);
//			ResultSet rs = conn.createStatement().executeQuery(qeListStages);
//			
//			while(rs.next()){
//				
//				StageVO stageVO = new StageVO();
//				
//	
//				stageVO.setIdStage(rs.getString(1));
//				stageVO.setNameStage(rs.getString(2));
//				stageVO.setDateUpdateStage(FormatUtilities.dateToStringFormat(rs.getTimestamp(3), "dd/MM/yyyy hh:mm") );
//				stageVO.setTypeStage(rs.getString(5));
//				stageVO.setStateStage(rs.getString(6));
//				stageVO.setIsidUserStage(rs.getString(8));
//				stageVO.setDescriptionStage(rs.getString(9));
//				
//				stageVO.setIdStatusStage(rs.getString(10));
//				stageVO.setIdTypeStage(rs.getString(11));
//				stageVO.setPeriodStage(rs.getString(12));
//				stageVO.setSiteNameStage(rs.getString(13));
//				
//				
//				//Aplicando totales
//				String qeTotals = "SELECT SUM(FREIGHT) FREIGHT, SUM(DUTIES) DUTIES, SUM(UNITS) UNITS, SUM(OTHER) OTHER, SUM(FOB) FOB, STG_ID FROM ( " +
//						"SELECT SUM(FREIGHT) FREIGHT, SUM(0) DUTIES, SUM(0) UNITS, SUM(0) OTHER, SUM(0) FOB, STG_ID FROM CCS_VIEW_COST_PRODUCT " +
//						"WHERE STG_ID = "+stageVO.getIdStage()+" " +
//						"GROUP BY STG_ID " +
//						"UNION ALL " +
//						"SELECT SUM(0) FREIGHT, SUM(DUTIES) DUTIES, SUM(0) UNITS, SUM(0) OTHER, SUM(0) FOB, STG_ID FROM CCS_VIEW_COST_PRODUCT " +
//						"WHERE STG_ID = "+stageVO.getIdStage()+" " +
//						"GROUP BY STG_ID " +
//						"UNION ALL " +
//						"SELECT SUM(0) FREIGHT, SUM(0) DUTIES, SUM(UNITS) UNITS, SUM(0) OTHER, SUM(0) FOB, STG_ID FROM CCS_VIEW_COST_PRODUCT " +
//						"WHERE STG_ID = "+stageVO.getIdStage()+" " +
//						"GROUP BY STG_ID " +
//						"UNION ALL " +
//						"SELECT SUM(0) FREIGHT, SUM(0) DUTIES, SUM(0) UNITS, SUM(OTHER) OTHER, SUM(0) FOB, STG_ID FROM CCS_VIEW_COST_PRODUCT " +
//						"WHERE STG_ID = "+stageVO.getIdStage()+" " +
//						"GROUP BY STG_ID " +
//						"UNION ALL " +
//						"SELECT SUM(0) FREIGHT, SUM(0) DUTIES, SUM(0) UNITS, SUM(0) OTHER, SUM(FOB) FOB, STG_ID FROM CCS_VIEW_COST_PRODUCT " +
//						"WHERE STG_ID = "+stageVO.getIdStage()+" " +
//						"GROUP BY STG_ID " +
//						") " +
//						"GROUP BY STG_ID";
//				logger.debug("qeTotals: "+qeTotals);
//				Statement stmtQeTotals = conn.createStatement();
//				ResultSet rsQeTotals = stmtQeTotals.executeQuery(qeTotals);
//				if (rsQeTotals.next()){
//					
//					stageVO.setTotalUnitsStage(rsQeTotals.getFloat(3));
//					
//					stageVO.setTotalFreightsStage(rsQeTotals.getFloat(1));
//					stageVO.setTotalDutiesStage(rsQeTotals.getFloat(2));
//					stageVO.setTotalOthersStage(rsQeTotals.getFloat(4));
//					stageVO.setTotalFobsStage(rsQeTotals.getFloat(5));
//				}
//				stmtQeTotals.close();
//				rsQeTotals.close();
//				
//				
//				
//				listStageVO.add(stageVO);
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
//    	return listStageVO;
//    }
    
    
	public void deleteStage(String idStage) throws DAOException{
		
		Connection conn = null;
		try {
			
			logger.debug("deleting.... idStage: "+idStage);
			conn = dataConnection.getConnectionOracleMSD();
			
			conn.setAutoCommit(false);
			
			
			
			conn.createStatement().executeUpdate("DELETE FROM CCS_SETUP_LOCAL_RANGE WHERE STG_ID = "+idStage+"");
			conn.createStatement().executeUpdate("DELETE FROM CCS_SETUP_STORAGE_RANGE WHERE STG_ID = "+idStage+"");
			conn.createStatement().executeUpdate("DELETE FROM CCS_SETUP_LOCAL_TRANSPORT WHERE STG_ID = "+idStage+"");
			conn.createStatement().executeUpdate("DELETE FROM CCS_SETUP_STORAGE_TEMP WHERE STG_ID = "+idStage+"");
			conn.createStatement().executeUpdate("DELETE FROM CCS_SETUP_OPE_MANAGE WHERE STG_ID = "+idStage+"");
			conn.createStatement().executeUpdate("DELETE FROM CCS_SETUP_OPE_CONSOLIDATION WHERE STG_ID = "+idStage+"");
			conn.createStatement().executeUpdate("DELETE FROM CCS_SETUP_MACRO_VARS WHERE STG_ID = "+idStage+"");
//			conn.createStatement().executeUpdate("DELETE FROM CCS_SETUP_EXCHANGES WHERE STG_ID = "+idStage+"");
			conn.createStatement().executeUpdate("DELETE FROM CCS_SETUP_CUSTUM_DUTIES_DETAIL WHERE STG_ID = "+idStage+"");
			conn.createStatement().executeUpdate("DELETE FROM CCS_SETUP_CUSTUM_DUTIES_HEAD WHERE STG_ID = "+idStage+"");
			conn.createStatement().executeUpdate("DELETE FROM CCS_STAGE_FREIGHT_OTHERS_CSP WHERE STG_ID = "+idStage+"");
			conn.createStatement().executeUpdate("DELETE FROM CCS_STAGE_FREIGHT_OTHERS WHERE STG_ID = "+idStage+"");
			
			
			
			
			
			
			
			
			
			
			
			conn.createStatement().executeUpdate("DELETE FROM CCS_STAGE_FREIGHT_AIR_RANGE WHERE STG_ID = "+idStage+"");
			conn.createStatement().executeUpdate("DELETE FROM CCS_STAGE_FREIGHT_AIR WHERE STG_ID = "+idStage+"");
			conn.createStatement().executeUpdate("DELETE FROM CCS_SITE_SOURCE_TRANSPORT WHERE STG_ID = "+idStage+"");
			
			
			
			conn.createStatement().executeUpdate("DELETE FROM CCS_LOCAL_ITEM_MASTER WHERE STG_ID = "+idStage+"");
			
			
			conn.createStatement().executeUpdate("DELETE FROM CCS_SETUP_LOCAL_ROUTE WHERE STG_ID = "+idStage+"");
			
			conn.createStatement().executeUpdate("DELETE FROM CCS_SETUP_CUSTODY_SERVICE WHERE STG_ID = "+idStage+"");
			conn.createStatement().executeUpdate("DELETE FROM CCS_UNITS_COST WHERE STG_ID = "+idStage+"");
			conn.createStatement().executeUpdate("DELETE FROM CCS_IMPORT_UNITS WHERE STG_ID = "+idStage+"");
			
			conn.createStatement().executeUpdate("DELETE FROM CCS_STAGE_COMMENT WHERE STG_ID = "+idStage+"");
			
			
			conn.createStatement().executeUpdate("DELETE FROM CCS_STAGE_FREIGHT_APO_RATE WHERE STG_ID = "+idStage+"");
			
			conn.createStatement().executeUpdate("DELETE FROM CCS_STAGE_FREIGHT_APO WHERE STG_ID = "+idStage+"");
			
			conn.createStatement().executeUpdate("DELETE FROM CCS_STAGE_FREIGHT_EXCHANGE WHERE STG_ID = "+idStage+"");
			
			
			conn.createStatement().executeUpdate("DELETE FROM CCS_SITE_SOURCE WHERE STG_ID = "+idStage+"");
			
			conn.createStatement().executeUpdate("DELETE FROM CCS_STAGE WHERE STG_ID = "+idStage+"");
			
			
			conn.commit();
			
			logger.debug("Commit");
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
	
	

}