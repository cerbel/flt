package cl.cstit.msd.ccs.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import cl.cstit.msd.ccs.enums.Meses;
import cl.cstit.msd.ccs.exception.DAOException;
import cl.cstit.msd.ccs.utils.ConnectionFactory;
import cl.cstit.msd.ccs.utils.ConstantsFinalObject;
import cl.cstit.msd.ccs.utils.FormatUtilities;
import cl.cstit.msd.ccs.utils.UtilValidation;
import cl.cstit.msd.ccs.vo.ErrorField;
import cl.cstit.msd.ccs.vo.FieldExcel;
import cl.cstit.msd.ccs.vo.ImportUnitVO;
import cl.cstit.msd.ccs.vo.StageVO;

public class ImportPlanForecastDAOImp{
	
	private ConnectionFactory dataConnection = null;
	private static Logger logger = null;
	private List<ErrorField> listErrorFields = new ArrayList<ErrorField>();
	
	public ImportPlanForecastDAOImp(){
		dataConnection = ConnectionFactory.getInstance();	
		logger = Logger.getLogger(ImportPlanForecastDAOImp.class);
	}
	
	
//	private void updateItemMasterJDE(Connection conn, String idSite){
//		
//		
//		
//		
//	}
	
	
    public List<ErrorField> savePlanForecast(String sitID, String usrIsid, String iphType, List<FieldExcel> listFieldExcel) throws DAOException{
    	
    	Connection conn = null;

    	try {

    		conn = dataConnection.getConnectionOracleMSD();
    		conn.setAutoCommit(false);
    		
    		
    		
    		if (iphType.equals("F")){
    			String queryExecute = "SELECT 1 FROM ccs_stage WHERE sit_id = "+sitID+" AND STS_ID IN (7,8) AND STG_PERIOD = "+FormatUtilities.getCurrentDateYear()+"";
//        		logger.debug("queryExecute: "+queryExecute);
        		
        		ResultSet rs = conn.createStatement().executeQuery(queryExecute);
        		if (!rs.next()){
        			throw new DAOException(ConstantsFinalObject.E_DAO_NO_EXIST_BUDGET);
        		}	
    		}
    		
    		
    		
    		if (listFieldExcel.size()>0){
	    		String iphID="";
	    		
	    		
				//Invocación método de actualización Item Master para el país.
//	    		updateItemMasterJDE(conn, sitID);
				
	    		
	    		String queryExecute = "DELETE "
	    							+ "FROM CCS_IMPORT_BASE_DETAIL "
	    							+ "WHERE IPH_ID = (SELECT IPH_ID "
	    							+ "					FROM CCS_IMPORT_BASE_HEAD b "
	    							+ "					WHERE b.SIT_ID="+sitID+" and b.IPH_TYPE='"+iphType+"')";
	
//	    		logger.debug("queryExecute: "+queryExecute);
				conn.createStatement().executeUpdate(queryExecute);
	
				queryExecute = 	"DELETE "
	    					  + "FROM CCS_IMPORT_BASE_HEAD "
	    					  + "WHERE SIT_ID="+sitID+" and IPH_TYPE='"+iphType+"'";
	    		
	    		
//	    		logger.debug("queryExecute: "+queryExecute);
				conn.createStatement().executeUpdate(queryExecute);
	
	    		queryExecute = "SELECT CCS_SEQ_CCS_IMPORT_BASE_HEAD.NEXTVAL FROM DUAL";
	    		ResultSet rs = conn.createStatement().executeQuery(queryExecute);
	    		if (rs.next())
					iphID = rs.getString(1);
	
				queryExecute = 	"INSERT INTO CCS_IMPORT_BASE_HEAD (IPH_ID, IPH_DATE_UPLOAD, SIT_ID, USR_ISID, IPH_TYPE, IPH_PERIOD) " +
										"VALUES ("+iphID+",sysdate,"+sitID+",'"+usrIsid+"','"+iphType+"',TO_CHAR(sysdate,'YYYY'))";
				logger.debug("queryExecute: "+queryExecute);
				
				conn.createStatement().executeUpdate(queryExecute);
				
				//Insertamos en el detalle
				//validamos aca antes de insertar y guardamos los errores
				for (FieldExcel fieldExcel : listFieldExcel) {
					
					
					logger.debug("fieldExcel.getHubCode(): "+fieldExcel.getHubCode());
					if (valField(fieldExcel, conn, iphID, sitID)){
						queryExecute = 	"INSERT INTO CCS_IMPORT_BASE_DETAIL (IMD_HUB_COD_PRODUCT, IPH_ID, IMD_JAN, IMD_FEB, IMD_MAR, IMD_APR, IMD_MAY, IMD_JUN, IMD_JUL, IMD_AUG, IMD_SEP, IMD_OCT, IMD_NOV, IMD_DEC) " +
										"VALUES ('"+replaceString(fieldExcel.getHubCode())+"', "+iphID+", "+converToNumber(fieldExcel.getJan())+", "+converToNumber(fieldExcel.getFeb())+", "+converToNumber(fieldExcel.getMar())+", "+converToNumber(fieldExcel.getApr())+", "+converToNumber(fieldExcel.getMay())+", "+converToNumber(fieldExcel.getJun())+", "+converToNumber(fieldExcel.getJul())+", "+converToNumber(fieldExcel.getAug())+", "+converToNumber(fieldExcel.getSep())+", "+converToNumber(fieldExcel.getOct())+", "+converToNumber(fieldExcel.getNov())+", "+converToNumber(fieldExcel.getDec())+")";
//						logger.debug("queryExecute: "+queryExecute);
						Statement st = conn.createStatement();
						st.executeUpdate(queryExecute);
						
						st.close();
						
						ImportUnitVO importUnitVO = new ImportUnitVO();
						
						importUnitVO.setJan(fieldExcel.getJan());
						importUnitVO.setFeb(fieldExcel.getFeb());
						importUnitVO.setMar(fieldExcel.getMar());
						importUnitVO.setApr(fieldExcel.getApr());
						importUnitVO.setMay(fieldExcel.getMay());
						importUnitVO.setJun(fieldExcel.getJun());
						importUnitVO.setJul(fieldExcel.getJul());
						importUnitVO.setAug(fieldExcel.getAug());
						importUnitVO.setSep(fieldExcel.getSep());
						importUnitVO.setOct(fieldExcel.getOct());
						importUnitVO.setNov(fieldExcel.getNov());
						importUnitVO.setDec(fieldExcel.getDec());
						
						
			    		queryExecute = "UPDATE CCS_ITEM_MASTER_JDE SET ITJ_AJUSTED_VALUE = "+calTotalMedia(importUnitVO)+" WHERE ITJ_COD_HUB = '"+fieldExcel.getHubCode()+"' AND SIT_ID = "+sitID+"";
			    		logger.debug("queryExecute: "+queryExecute);
			    		st = conn.createStatement();
			    		st.executeUpdate(queryExecute);
			    		st.close();
					}
				}
				
				
				
				//EN CASO DE QUE NO EXISTA INFORMACIÓN PARA EL SITE BASADO EN EL BUDGET, SE GENERARAN LOS STAGE ACTUAL Y BUDGET PARA ESE SITE.
				MoveCostDAOImp moveCostDAOImp = new MoveCostDAOImp();
				
	    		queryExecute = "SELECT 1 FROM CCS_STAGE WHERE sit_id = "+sitID+" AND STS_ID IN (7,8) AND STG_PERIOD = "+FormatUtilities.getCurrentDateYear()+"";
	    		rs = conn.createStatement().executeQuery(queryExecute);
	    		if (!rs.next()){
	    			
					//Creando un stage Actual para el pais
					StageVO stageVO = new StageVO();
					
					stageVO.setDescriptionStage("Actual Stage Description");
					stageVO.setNameStage("Actual Process");
					stageVO.setIsidUserStage(usrIsid);
					stageVO.setIdSiteStage(sitID); 
					stageVO.setIdStatusStage("6"); //actual en proceso
					stageVO.setIdTypeStage("4");   //actual
					
					moveCostDAOImp.calculateCostProduct(moveCostDAOImp.initSimulation(stageVO, conn), conn);
					
					
					//Creando un stage Budget en proceso, sólo para cuando se inicie un site
					stageVO = new StageVO();
					
					stageVO.setDescriptionStage("Budget Base");
					stageVO.setNameStage("Budget in process");
					stageVO.setIsidUserStage(usrIsid);
					stageVO.setIdSiteStage(sitID); 
					stageVO.setIdStatusStage("7"); //Budget Approved
					stageVO.setIdTypeStage("3");   //Budget
					
					
					initializeStageZero(conn, moveCostDAOImp.initSimulation(stageVO, conn));
					
	    		}
					
				
				//EN CASO DE SER FORECAST, ENTONCES AJUSTARÁ EL ACTUAL
				if (iphType.equals("F")){
					String[] idSite = new String[1];
					idSite[0] = sitID;
//					updateImportBaseDetail(conn, idSite );
				}
				
				
				conn.commit();
    		}
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
    	
    	
    	logger.debug("listErrorFields.size(): "+listErrorFields.size());
    	
    	
    	return listErrorFields;
    }	
    
    
    public List<FieldExcel> getPlanForecast(String sitID, String iphType) throws DAOException{
    	
    	Connection conn = null;
    	List<FieldExcel> listFieldExcel = new ArrayList<FieldExcel>();
    	try {
    		
    		conn = dataConnection.getConnectionOracleMSD();
    		
			
    		String queryExecute = "SELECT IMD_HUB_COD_PRODUCT, ITJ_ID_PRODUCT, ITJ_DESCRIPTION_PRODUCT, IMD_JAN, IMD_FEB, IMD_MAR, IMD_APR, IMD_MAY, IMD_JUN, IMD_JUL, IMD_AUG, IMD_SEP, IMD_OCT, IMD_NOV, IMD_DEC "
    							+ "FROM CCS_IMPORT_BASE_DETAIL ibd, CCS_ITEM_MASTER_JDE itj "
    							+ "WHERE ibd.IMD_HUB_COD_PRODUCT = itj.ITJ_COD_HUB "
    							+ "AND itj.SIT_ID=" + sitID + " "
    							+ "AND IPH_ID = (SELECT IPH_ID "
    							+ "					FROM CCS_IMPORT_BASE_HEAD b "
    							+ "					WHERE b.SIT_ID="+sitID+" and b.IPH_TYPE='"+iphType+"')";
			
//    		logger.debug("queryExecute: "+queryExecute);
			
    		ResultSet rs = conn.createStatement().executeQuery(queryExecute);
			
    		while (rs.next()){
    			FieldExcel fieldExcel = new FieldExcel();
    			fieldExcel.setHubCode(rs.getString(1));
    			fieldExcel.setLocalCode(rs.getString(2));
    			fieldExcel.setDescriptionProduct(rs.getString(3));
    			fieldExcel.setJan(rs.getString(4));
    			fieldExcel.setFeb(rs.getString(5));
    			fieldExcel.setMar(rs.getString(6));
    			fieldExcel.setApr(rs.getString(7));
    			fieldExcel.setMay(rs.getString(8));
    			fieldExcel.setJun(rs.getString(9));
    			fieldExcel.setJul(rs.getString(10));
    			fieldExcel.setAug(rs.getString(11));
    			fieldExcel.setSep(rs.getString(12));
    			fieldExcel.setOct(rs.getString(13));
    			fieldExcel.setNov(rs.getString(14));
    			fieldExcel.setDec(rs.getString(15));
    			
    			listFieldExcel.add(fieldExcel);
    		}
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
    	
    	return listFieldExcel;
    }	
    
    public String getLastUpload(String sitID , String type) throws DAOException{
     
       	Connection conn = null;
       	String detailLastUpload = "";
       	try {
    		
    		conn = dataConnection.getConnectionOracleMSD();
    		
			//Obtenemos el maximo ID
    	   	String queryExecute = "select to_char(IPH_DATE_UPLOAD, 'dd/MM/yyyy hh24:mi:ss') || ' (' || USR_ISID || ')' "
								+ "from CCS_IMPORT_BASE_HEAD "
				    			+ "where SIT_ID = " + sitID 
				    			+ " and IPH_TYPE = '"+type+"'";
			
    		logger.debug("queryExecute: "+queryExecute);
			
    		ResultSet rs = conn.createStatement().executeQuery(queryExecute);
			
    		if (rs.next()){
    			detailLastUpload = rs.getString(1);
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
    
       	return detailLastUpload;
    }
    
    
    //Valida producto contra JDE
    private boolean existJde(String hubCode, String idSite, Connection conn, String desc)  throws SQLException{
       	
    	boolean exist = false;
    	
    	float transferPrice = 0f;
		float hightPalette  = 0f;
		float longPalette   = 0f;
		float widthPalette  = 0f;
		float weightPalette = 0f;
		float unitByPalette = 0f;
		
		int isIVAProduct = 0;
		
		String customDutyCod      = "";
		String localProductCode   = "";
		String descriptionProduct = "";
		

       	
	   	String queryExecute = "SELECT SIT_COMPANY_COD_JDE FROM CCS_SITE WHERE SIT_ID = "+idSite+"";
		logger.debug("queryExecute: "+queryExecute);
		Statement st = conn.createStatement();
		
		ResultSet rs = st.executeQuery(queryExecute);
		String codeCompanyJDE = rs.next()?rs.getString(1):"";

		
		logger.debug("codeCompanyJDE: "+codeCompanyJDE);

       	
		//INVOCACION ITEM MASTER JDE
	   	queryExecute = "SELECT 1.2, DOSAGE_HIGTH, DOSAGE_LONG, DOSAGE_WIDH, GROSS_WEIGHT, UNIT_BY_PALLET, IVA_TAX_IMPORT, CUSTOM_DUTY, LOCAL_CODE, PRODUCT_DESCR FROM CCS_ITEM_MASTER WHERE COMPANY_CODE = '"+codeCompanyJDE+"' AND HUB_CODE = '"+hubCode+"'";
		logger.debug("queryExecute: "+queryExecute);
		st = conn.createStatement();
		
		rs = st.executeQuery(queryExecute);
		if (rs.next()){
			
			transferPrice      = rs.getFloat(1);
			hightPalette       = rs.getFloat(2);
			longPalette        = rs.getFloat(3);
			widthPalette       = rs.getFloat(4);
			weightPalette      = rs.getFloat(5);
			unitByPalette      = rs.getFloat(6);
			isIVAProduct       = rs.getInt(7);
			customDutyCod      = rs.getString(8);
			localProductCode   = rs.getString(9);
			descriptionProduct = rs.getString(10);
		}
		else{
			exist = false;
		}
		
		rs.close();
		st.close();
		
		
		
	   	queryExecute = "select 1 "
	   						+ "from CCS_ITEM_MASTER_JDE "
	   						+ "WHERE ITJ_COD_HUB='" + hubCode + "' "
	   						+ "AND SIT_ID=" + idSite;
	   	
	   	logger.debug("queryExecute: "+queryExecute);
	   	Statement st1 = conn.createStatement();
		
		ResultSet rs1 = st1.executeQuery(queryExecute);
		if (rs1.next()){
			
			exist = true;
		}else{

			
			//CQT: esta else es temporal dado que debe ser conectado a JDE
			exist = true;
		   	queryExecute = "INSERT INTO CCS_ITEM_MASTER_JDE (ITJ_COD_HUB, ITJ_ID_PRODUCT, ITJ_DESCRIPTION_PRODUCT, SIT_ID, ITJ_REPLY, ITJ_FOB_COST, ITJ_HIGH_PALETTE, ITJ_LARGE_PALETTE, ITJ_WIDE_PALETTE, ITJ_UNIT_BY_PALETTE, " +
		   			"ITJ_WEIGTH_PALETTE, PRM_ID, PFA_ID, PTY_ID, TRA_ID, SIS_ID, PRP_ID, PTR_ID, PTL_ID, SDD_COD, SLR_ID, SSR_ID, ITL_IS_IVA) " +
		   			"VALUES( " +
		   			"'"+hubCode+"', '"+localProductCode+"', '"+descriptionProduct+"', "+idSite+", 0, "+transferPrice+", "+hightPalette+", "+longPalette+", "+widthPalette+", "+unitByPalette+", "+weightPalette+", 1, 1, 1, 1, 126, 1, 1, 1, '"+customDutyCod+"', 1, 1, '"+(isIVAProduct==1?"Y":"N")+"')";
		   	st1.executeUpdate(queryExecute);
		}

		rs1.close();
		st1.close();
		
    	return exist;
    }
    
    private boolean existProduct(String hubCode, String idProcess, Connection conn)throws SQLException{
       	boolean exist = false;
       	
	   	String queryExecute = "SELECT 1 "
	   						+ "FROM CCS_IMPORT_BASE_DETAIL "
	   						+ "WHERE IMD_HUB_COD_PRODUCT='"+hubCode+"' "
	   						+ "AND IPH_ID = "+ idProcess;
		
		
		Statement st = conn.createStatement();
		
		ResultSet rs = st.executeQuery(queryExecute);
		
		if (rs.next()){
			exist = true;
		}

		rs.close();
		st.close();
		
    	return exist;
    }
    
    private boolean valField(FieldExcel fieldExcel,Connection conn, String iphID, String idSite)throws SQLException{
    	boolean validField = true;
    	String messageError= "";
    	ErrorField errorField = new ErrorField();
    	if (existJde(fieldExcel.getHubCode(), idSite, conn, fieldExcel.getDescriptionProduct() )){
    		
    		if (fieldExcel.getHubCode().length() > 20){
    			messageError += "Locale Code exceeds the maximum allowed (20 characters) <br>";
    		}else if (existProduct(fieldExcel.getHubCode(),iphID, conn)){
    			messageError += "Product repeated code: "+fieldExcel.getHubCode()+" <br>";
    		}
    	}else{
    		logger.debug("producto no existe JDE: "+fieldExcel.getHubCode());
    		
    		messageError += "HUB Code don't exist in JDE <br>";
    	}
    	
    	if (!UtilValidation.isNumeric(fieldExcel.getJan())){
			messageError += "Value must be numeric and positive for January <br>";
    	}
    	
    	if (!UtilValidation.isNumeric(fieldExcel.getFeb())){
			messageError += "Value must be numeric and positive for February <br>";
    	}
    	
    	if (!UtilValidation.isNumeric(fieldExcel.getMar())){
			messageError += "Value must be numeric and positive for March <br>";
    	}
    	
    	if (!UtilValidation.isNumeric(fieldExcel.getApr())){
			messageError += "Value must be numeric and positive for April  <br>";
    	}
    	
    	if (!UtilValidation.isNumeric(fieldExcel.getMay())){
			messageError += "Value must be numeric and positive for May <br>";
    	}
    	
    	if (!UtilValidation.isNumeric(fieldExcel.getJun())){
			messageError += "Value must be numeric and positive for June  <br>";
    	}
    	
    	if (!UtilValidation.isNumeric(fieldExcel.getJul())){
			messageError += "Value must be numeric and positive for July <br>";
    	}
    	
    	if (!UtilValidation.isNumeric(fieldExcel.getAug())){
			messageError += "Value must be numeric and positive for August <br>";
    	}
    	
    	if (!UtilValidation.isNumeric(fieldExcel.getSep())){
			messageError += "Value must be numeric and positive for September <br>";
    	}
    	
    	if (!UtilValidation.isNumeric(fieldExcel.getOct())){
			messageError += "Value must be numeric and positive for October <br>";
    	}
    	
    	if (!UtilValidation.isNumeric(fieldExcel.getNov())){
			messageError += "Value must be numeric and positive for November <br>";
    	}
    	
    	if (!UtilValidation.isNumeric(fieldExcel.getDec())){
			messageError += "Value must be numeric and positive for December <br>";
    	}

    	
    	if (!messageError.equals("")){
			errorField.setLine(fieldExcel.getLinea());
			errorField.setError(messageError);
			listErrorFields.add(errorField);
			validField = false;
    	}    	
    	return validField;
    }
    

    
    public String getNameSite(String sitID) throws DAOException{
    	
    	Connection conn = null;
    	String nombrePais="";
    	try {
    		
    		conn = dataConnection.getConnectionOracleMSD();
    		
			//Obtenemos el maximo ID
    		String queryExecute = "SELECT SIT_NAME "
    							+ "FROM CCS_SITE "
    							+ "WHERE SIT_ID = " + sitID;
			
//    		logger.debug("queryExecute: "+queryExecute);
			
    		ResultSet rs = conn.createStatement().executeQuery(queryExecute);
    		if (rs.next())
    			nombrePais = rs.getString(1);

			
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
    	
    	return nombrePais;
    }	
    
 
    //Actualimos con los datos del actual
    public static void updateImportBaseDetail(Connection conn, String[] sitID) throws SQLException{
    	
    	for (int i = 0; i< sitID.length; i++){
    	
    		String query = "SELECT IPH_ID "
    					 + "FROM CCS_IMPORT_BASE_HEAD "
    					 + "WHERE SIT_ID =" +sitID[i]
    					 + " AND IPH_TYPE='F'";
    		
    		Statement st = conn.createStatement();
    		ResultSet rs = st.executeQuery(query);
    		
    		if (rs.next()){
    			String querySelect = "SELECT IMD_HUB_COD_PRODUCT "
	    					   + "FROM CCS_IMPORT_BASE_DETAIL "
	    					   + "WHERE IPH_ID=" + rs.getString(1);

    	    	Statement stSelect = conn.createStatement();
    	    	ResultSet rsSelect = stSelect.executeQuery(querySelect);
    	    	
    	    	while (rsSelect.next()){
    	    		//Conexion a JDE
    	    		
    	    		List<String> valoresJDE = new ArrayList<String>();
    	    		valoresJDE.add("100");
    	    		valoresJDE.add("200");
    	    		valoresJDE.add("300");
    	    		valoresJDE.add("400");
    	    		valoresJDE.add("500");
    	    		valoresJDE.add("600");
    	    		valoresJDE.add("700");
    	    		valoresJDE.add("800");
    	    		valoresJDE.add("900");
    	    		valoresJDE.add("1000");
    	    		valoresJDE.add("1100");
    	    		valoresJDE.add("1200");
    	    		
    	    		int cont = 0;
    	    		for (Meses meses : Meses.values()){
    	
    	    			if (meses.getId() == Integer.parseInt(FormatUtilities.getCurrentDateMonth()))
    	    				break;
    	    			
    	    			String queryUpdate = "UPDATE CCS_IMPORT_BASE_DETAIL SET "
    	    							  +  meses.getDescripcion() + valoresJDE.get(cont)
    	                				  +  " WHERE IPH_ID = " + rs.getString(1)
    	                				  +  " AND IMD_HUB_COD_PRODUCT = '" + rsSelect.getString(1) + "'";
    	    			
    	    			Statement stUpdate = conn.createStatement();
    	    			stUpdate.executeUpdate(queryUpdate);
    	    			stUpdate.close();
    	    			cont++;
    	    		}
    	    		
    	    	}
    	    	
    	    	rsSelect.close();
    	    	stSelect.close();
    			
    		}
    		rs.close();
    		st.close();
    	}
    }
    
    
    private static void initializeStageZero(Connection conn, String idStage) throws SQLException{
    	
    	Statement st = conn.createStatement();
		
		
		st.executeQuery("UPDATE CCS_SETUP_MACRO_VARS SET SMV_AMOUNT = 0 WHERE STG_ID = "+idStage+"");
		
		st.executeQuery("UPDATE CCS_STAGE_FREIGHT_EXCHANGE SET SFE_RATE = 0 WHERE STG_ID = "+idStage+"");
		st.executeQuery("UPDATE CCS_SETUP_CUSTUM_DUTIES_HEAD SET   SDH_OTHER_TAXES_FODINFA = 0, SDH_VAT_IMPORTS = 0, SDH_OTHER_TAXES_IMP_CAP = 0 WHERE STG_ID = "+idStage+"");
		st.executeQuery("UPDATE CCS_SETUP_CUSTUM_DUTIES_DETAIL SET SDD_DUTY_TARIFF = 0, SDD_OTHER_TAXES = 0  WHERE STG_ID = "+idStage+"");
		st.executeQuery("UPDATE CCS_SETUP_OPE_MANAGE SET SOM_PROCESSING_FEE = 0, SOM_RATE_KG = 0, SOM_PERCENT_TP = 0 WHERE STG_ID = "+idStage+"");
		st.executeQuery("UPDATE CCS_SETUP_STORAGE_RATE SET SSR_RATE = 0 WHERE STG_ID = "+idStage+"");
		st.executeQuery("UPDATE CCS_SETUP_STORAGE_RANGE SET SSR_RANGE_RATE = 0 WHERE STG_ID = "+idStage+"");
		st.executeQuery("UPDATE CCS_SETUP_STORAGE_TEMP SET SST_PROCESSING_FEE = 0 WHERE STG_ID = "+idStage+"");
		st.executeQuery("UPDATE CCS_SETUP_LOCAL_RANGE SET SLR_RANGE_RATE_KG = 0 WHERE STG_ID = "+idStage+"");
		st.executeQuery("UPDATE CCS_SETUP_LOCAL_ROUTE SET SLR_RATE = 0 WHERE STG_ID = "+idStage+"");
		st.executeQuery("UPDATE CCS_SETUP_LOCAL_TRANSPORT SET SLT_PROCESSING_FEE = 0 WHERE STG_ID = "+idStage+"");
		st.executeQuery("UPDATE CCS_SETUP_CUSTODY_SERVICE SET SCS_BASE_LINE = 0, SCS_FEE_COST = 0 WHERE STG_ID = "+idStage+"");
		st.executeQuery("UPDATE CCS_STAGE_FREIGHT_AIR SET FAR_INCREASE = 0, FAR_AWB_KG_RATES = 0, FAR_FUEL_SURCHARGE = 0, FAR_SECURITY_CHARGE = 0, FAR_OTHER_FFW_FEES = 0,  FAR_ORDERS = 0,  FAR_INCREASE_AWB = 0,  FAR_INCREASE_FSC = 0,  FAR_INCREASE_SCC = 0,  FAR_INCREASE_FFW = 0 WHERE STG_ID = "+idStage+"");
		st.executeQuery("UPDATE CCS_STAGE_FREIGHT_AIR_RANGE SET FRR_RATE = 0 WHERE STG_ID = "+idStage+"");
		st.executeQuery("UPDATE CCS_STAGE_FREIGHT_OTHERS SET FOT_PALETTES = 0, FOT_ORDERS = 0, FOT_INCREASE = 0 WHERE STG_ID = "+idStage+"");
		st.executeQuery("UPDATE CCS_STAGE_FREIGHT_OTHERS_CSP SET OCP_RATE = 0 WHERE STG_ID = "+idStage+"");
		st.executeQuery("UPDATE CCS_STAGE_FREIGHT_APO SET APO_INCREASE = 0, APO_AWB_RATE = 0, APO_OTHER_FEE = 0, APO_FUEL_SURCHARGE = 0, APO_SECURITY_CHARGE = 0,  APO_ORDERS = 0,  APO_PALETTES = 0,  APO_INCREASE_AWB = 0,  APO_INCREASE_FFW = 0,  APO_INCREASE_FSC = 0,  APO_INCREASE_SCC = 0 WHERE STG_ID = "+idStage+"");
		st.executeQuery("UPDATE CCS_STAGE_FREIGHT_APO_RATE SET APR_RATE = 0 WHERE STG_ID = "+idStage+"");
    		
    }
    
    
 
    
    
    
//	private void updateImportUnits(Connection conn, String idStage) throws DAOException{
//		
//		try {
//			
//			String queryExecute = "SELECT IMU_JAN,  IMU_FEB,  IMU_MAR,  IMU_APR,  IMU_MAY,  IMU_JUN,  IMU_JUL,  IMU_AUG,  IMU_SEP,  IMU_OCT,  IMU_NOV,  IMU_DIC, IMU_COD_PRODUCT "
//								+ "FROM CCS_IMPORT_UNITS ciu , CCS_STAGE cs "
//								+ "WHERE ciu.STG_ID = cs.STG_ID "
//								+ "and cs.STG_ID=" + idStage
//								+ " and IMU_STATE=1";
//			
//			logger.debug("queryExecute: "+queryExecute);
//
//			
//			ResultSet rs = conn.createStatement().executeQuery(queryExecute);
//			while (rs.next()){
//				ImportUnitVO importUnitVO = new ImportUnitVO();
//
//				importUnitVO.setJan(rs.getString(1));
//                importUnitVO.setFeb(rs.getString(2));
//                importUnitVO.setMar(rs.getString(3));
//                importUnitVO.setApr(rs.getString(4));
//                importUnitVO.setMay(rs.getString(5));
//                importUnitVO.setJun(rs.getString(6));
//                importUnitVO.setJul(rs.getString(7));
//                importUnitVO.setAug(rs.getString(8));
//                importUnitVO.setSep(rs.getString(9));
//                importUnitVO.setOct(rs.getString(10));
//                importUnitVO.setNov(rs.getString(11));
//                importUnitVO.setDec(rs.getString(12));
//                importUnitVO.setLocalCode(rs.getString(13));
//                queryExecute = "UPDATE CCS_IMPORT_UNITS "
//                		+ " SET IMU_AJUSTED =" + calTotalMedia(importUnitVO,2)
//                		+ " WHERE IMU_COD_PRODUCT = '" + importUnitVO.getLocalCode()+"'"
//                		+ " AND STG_ID = " + idStage;
//	
//                logger.debug(queryExecute);
//        		Statement st = conn.createStatement();
//        		
//        		st.executeUpdate(queryExecute);
//        		
//        		st.close();
//			}
//			rs.close();
//		}catch (SQLException e) {
//			e.printStackTrace();
//			throw new DAOException(ConstantsFinalObject.EXCEPTION_DAO_GENERIC_ERR_COD, e);		
//		}
//	}
	
	
	
//	private void updateFreight(Connection conn, String idStage, String idLastStage, String idSiteStage) throws DAOException, SQLException{
//		
//		String qeInsertSelectSiteSource = "INSERT INTO CCS_SITE_SOURCE (SIS_ID,SIS_NAME, TRA_ID, STG_ID)(SELECT SIS_ID, SIS_NAME, TRA_ID, "+idStage+" FROM CCS_SITE_SOURCE WHERE STG_ID = "+idLastStage+" )";
//		
//		logger.debug("qeInsertSelectSiteSource: "+qeInsertSelectSiteSource);
//		conn.createStatement().executeUpdate(qeInsertSelectSiteSource);	
//		
//		
//		String qeInsertSelectFreightAir = "INSERT INTO CCS_STAGE_FREIGHT_AIR ( " +
//				"FAR_ID, " +
//				"FAR_INCREASE, " +
//				"FAR_AWB_KG_RATES, " +
//				"FAR_FUEL_SURCHARGE, " +
//				"FAR_SECURITY_CHARGE, " +
//				"FAR_OTHER_FFW_FEES, " +
//				"STG_ID, " +
//				"SFE_ID, " +
//				"SIS_ID " +
//				") " +
//				"SELECT " +
//				"CCS_SEQ_CCS_STAGE_FAI.NEXTVAL, " +
//				"FAR_INCREASE, " +
//				"FAR_AWB_KG_RATES, " +
//				"FAR_FUEL_SURCHARGE, " +
//				"FAR_SECURITY_CHARGE, " +
//				"FAR_OTHER_FFW_FEES, " +
//				""+idStage+", " +
//				"SFE_ID, " +
//				"SIS_ID " +
//				"FROM CCS_STAGE_FREIGHT_AIR WHERE STG_ID = "+idLastStage+"";
//		conn.createStatement().executeUpdate(qeInsertSelectFreightAir);
//		
//		
//		String qeInsertSelectFreightAirMode = "INSERT INTO CCS_STAGE_FREIGHT_AIR_RANGE ( " +
//				"FRR_ID, " +
//				"FRR_FROM, " +
//				"FRR_TO, " +
//				"FRR_RATE, " +
//				"STG_ID, " +
//				"SIS_ID" +
//				") " +
//				"(SELECT " +
//				"CCS_SEQ_CCS_STAGE_F_AIR_RANGE.NEXTVAL, " +
//				"FRR_FROM, " +
//				"FRR_TO, " +
//				"FRR_RATE, " +
//				""+idStage+", " +
//				"SIS_ID " +
//				"FROM CCS_STAGE_FREIGHT_AIR_RANGE WHERE STG_ID = "+idLastStage+")";
//		conn.createStatement().executeUpdate(qeInsertSelectFreightAirMode);
//		
//		
////		String qeInsertSelectFreightOther = "INSERT INTO CCS_STAGE_FREIGHT_OTHER ( " +
////				"FRO_CONTAINER_RFER, " +
////				"FRO_INTER_FREIGHT, " +
////				"FRO_OTHERS, " +
////				"FRO_DESTINATION_CHARGES, " +
////				"FRO_PALETTES_NUMBER, " +
////				"STG_ID, " +
////				"SIS_ID, " +
////				"CUR_ID " +
////				") " +
////				"(SELECT " +
////				"FRO_CONTAINER_RFER, " +
////				"FRO_INTER_FREIGHT, " +
////				"FRO_OTHERS, " +
////				"FRO_DESTINATION_CHARGES, " +
////				"FRO_PALETTES_NUMBER, " +
////				""+idStage+", " +
////				"SIS_ID, " +
////				"CUR_ID " +
////				"FROM CCS_STAGE_FREIGHT_OTHER WHERE STG_ID = "+idLastStage+" )";
////		conn.createStatement().executeUpdate(qeInsertSelectFreightOther);
//		
//		
//		
//		String qeInsertSelectFreightExchanges = "INSERT INTO CCS_STAGE_FREIGHT_EXCHANGE( " +
//				"SFE_ID, " +
//				"SFE_NAME, " +
//				"SFE_RATE, " +
//				"CUR_ID, " +
//				"STG_ID " +
//				") " +
//				"(SELECT " +
//				"CCS_SEQ_SFE.NEXTVAL, " +
//				"SFE_NAME, " +
//				"SFE_RATE, " +
//				"CUR_ID, " +
//				""+idStage+" " +
//				"FROM CCS_STAGE_FREIGHT_EXCHANGE WHERE STG_ID = "+idLastStage+" )";
//		
//		conn.createStatement().executeUpdate(qeInsertSelectFreightExchanges);
//	}
	
	
//	private void updateSetup(Connection conn, String idStage, String idLastStage) throws DAOException, SQLException{
//		
//		
//		String qeInsertSelectDutiesHead = "INSERT INTO CCS_SETUP_CUSTUM_DUTIES_HEAD ( " +
//				"SDH_ID, " +
//				"SDH_OTHER_TAXES_FODINFA, " +
//				"SDH_VAT_IMPORTS, " +
//				"SDH_OTHER_TAXES_IMP_CAP, " +
//				"STG_ID " +
//				")(SELECT " +
//				"CCS_SEQ_CCS_SETUP_CUS_DU_HEAD.NEXTVAL, " +
//				"SDH_OTHER_TAXES_FODINFA, " +
//				"SDH_VAT_IMPORTS, " +
//				"SDH_OTHER_TAXES_IMP_CAP, " +
//				""+idStage+" " +
//				"FROM CCS_SETUP_CUSTUM_DUTIES_HEAD WHERE STG_ID = "+idLastStage+")";
//		conn.createStatement().executeUpdate(qeInsertSelectDutiesHead);
//		
//		String qeInsertSelectDutiesDetail = "INSERT INTO CCS_SETUP_CUSTUM_DUTIES_DETAIL ( " +
//				"SDD_ID, " +
//				"SDD_DUTY_NAME, " +
//				"SDD_DUTY_TARIFF, " +
//				"SDD_OTHER_TAXES, " +
//				"SDD_COD, " +
//				"STG_ID " +
//				") (SELECT " +
//				"CCS_SEQ_CCS_SETUP_CUS_DU_DET.NEXTVAL, " +
//				"SDD_DUTY_NAME, " +
//				"SDD_DUTY_TARIFF, " +
//				"SDD_OTHER_TAXES, " +
//				"SDD_COD, " +
//				""+idStage+" " +
//				"FROM CCS_SETUP_CUSTUM_DUTIES_DETAIL WHERE STG_ID = "+idLastStage+" )";
//		conn.createStatement().executeUpdate(qeInsertSelectDutiesDetail);
//		
//		
////		String qeInsertSelectExchanges = "INSERT INTO CCS_SETUP_EXCHANGES ( " +
////				"SEE_ID, " +
////				"SEE_NAME, " +
////				"SEE_AMOUNT, " +
////				"SEE_DESCRIPTION, " +
////				"STG_ID " +
////				") (SELECT " +
////				"CSS_SEQ_CCS_SETUP_EXCHANGES.NEXTVAL, " +
////				"SEE_NAME, " +
////				"SEE_AMOUNT, " +
////				"SEE_DESCRIPTION, " +
////				""+idStage+" " +
////				"FROM CCS_SETUP_EXCHANGES WHERE STG_ID = "+idLastStage+")";
////		conn.createStatement().executeUpdate(qeInsertSelectExchanges);
//		
//		
//		String qeInsertSelectMacroVars = "INSERT INTO CCS_SETUP_MACRO_VARS( " +
//				"SMV_ID, " +	
//				"SMV_AMOUNT, " +
//				"SMV_NAME, " +
//				"SMV_COD, " +
//				"STG_ID " +
//				")( SELECT " +
//				"CCS_SEQ_CCS_SETUP_MACRO_VARS.NEXTVAL, " +
//				"SMV_AMOUNT, " +
//				"SMV_NAME, " +
//				"SMV_COD, " +
//				""+idStage+" " +
//				"FROM CCS_SETUP_MACRO_VARS WHERE STG_ID = "+idLastStage+")";
//		conn.createStatement().executeUpdate(qeInsertSelectMacroVars);
//		
//		
//		
//		String qeInsertSelectSetupOpConsolidation = "INSERT INTO CCS_SETUP_OPE_CONSOLIDATION ( " +
//				"SOC_ID, " +
//				"SOC_ORDERS, " +
//				"STG_ID, " +
//				"TRA_ID, " +
//				"SIS_ID " +
//				")(SELECT " +
//				"CCS_SEQ_CCS_SETUP_OPE_CONS.NEXTVAL, " +
//				"SOC_ORDERS, " +
//				""+idStage+", " +
//				"TRA_ID, " +
//				"SIS_ID " +
//				"FROM CCS_SETUP_OPE_CONSOLIDATION WHERE STG_ID = "+idLastStage+")";
//		conn.createStatement().executeUpdate(qeInsertSelectSetupOpConsolidation);
//		
//		String qeInsertSelectSetupOpManage = "INSERT INTO CCS_SETUP_OPE_MANAGE ( " +
//				"SOM_ID," +
//				"SOM_ENTRY," +
//				"SOM_PROCESSING_FEE," +
//				"SOM_RATE_KG," +
//				"SOM_PERCENT_TP," +
//				"CUR_ID," +
//				"STG_ID" +
//				")(SELECT " +
//				"CCS_SEQ_CCS_SETUP_OPE_MANAGE.NEXTVAL, " +
//			    "SOM_ENTRY," +
//			    "SOM_PROCESSING_FEE," +
//			    "SOM_RATE_KG," +
//			    "SOM_PERCENT_TP," +
//			    "CUR_ID," +
//				""+idStage+
//				" FROM CCS_SETUP_OPE_MANAGE WHERE STG_ID = "+idLastStage+")";
//		conn.createStatement().executeUpdate(qeInsertSelectSetupOpManage);
//		
//		String qeInsertSelectSetupSorageTemp = "INSERT INTO CCS_SETUP_STORAGE_TEMP ( " +
//				"SST_ID," +
//				"SST_ENTRY," +
//				"SST_PERCENT_COST," +
//				"SST_PROCESSING_FEE," +
//				"STG_ID," +
//				"CUR_ID" +
//				")(SELECT " +
//				"CCS_SEQ_CCS_SETUP_STORAGE_TEMP.NEXTVAL, " +
//			    "SST_ENTRY," +
//			    "SST_PERCENT_COST," +
//			    "SST_PROCESSING_FEE," +
//				idStage+","+
//			    "CUR_ID" +
//				" FROM CCS_SETUP_STORAGE_TEMP WHERE STG_ID = "+idLastStage+")";
//		conn.createStatement().executeUpdate(qeInsertSelectSetupSorageTemp);
//		
//		String qeInsertSelectSetupLocalTrasnport = "INSERT INTO CCS_SETUP_LOCAL_TRANSPORT ( " +
//				"SLT_ID,  " +
//				"SLT_ENTRY," +
//				"SLT_PERCENT_COST," +
//				"SLT_PROCESSING_FEE," +
//				" STG_ID," +
//				"CUR_ID" +
//				")(SELECT " +
//				"CCS_SEQ_CCS_SETUP_LOCAL_TRANSP.NEXTVAL, " +
//			    "SLT_ENTRY," +
//			    "SLT_PERCENT_COST," +
//			    "SLT_PROCESSING_FEE," +
//				idStage+","+
//			    "CUR_ID" +
//				" FROM CCS_SETUP_LOCAL_TRANSPORT WHERE STG_ID = "+idLastStage+")";
//		conn.createStatement().executeUpdate(qeInsertSelectSetupLocalTrasnport);
//		
//		
//		String qeInsertSelectSetupStorageRange = "INSERT INTO CCS_SETUP_STORAGE_RANGE ( " +
//				"SSR_ID, " +
//				"SSR_RANGE_FROM,  " +
//				"SSR_RANGE_UO," +
//				"SSR_RANGE_RATE," +
//				"STG_ID" +
//				")(SELECT " +
//				"CCS_SEQ_CCS_SETUP_STORAGE_RANG.NEXTVAL, " +
//			    "SSR_RANGE_FROM," +
//			    "SSR_RANGE_UO," +
//			    "SSR_RANGE_RATE," +
//				idStage+
//				" FROM CCS_SETUP_STORAGE_RANGE WHERE STG_ID = "+idLastStage+")";
//		conn.createStatement().executeUpdate(qeInsertSelectSetupStorageRange);
//		
//		
//		String qeInsertSelectLocalStorageRange = "INSERT INTO CCS_SETUP_LOCAL_RANGE ( " +
//				"SLR_ID,  "+
//				"SLR_RANGE_FROM,  "+ 
//				"SLR_RANGE_UP,  " + 
//				"SLR_RANGE_RATE_KG,  "+
//				"CUR_ID,  "+
//				"STG_ID" +
//				")(SELECT " +
//				"CCS_SEQ_CCS_SETUP_LOCAL_RANGE.NEXTVAL, " +
//			    "SLR_RANGE_FROM,  "+ 
//				"SLR_RANGE_UP,  " + 
//				"SLR_RANGE_RATE_KG," +
//				"CUR_ID," +
//				idStage+
//				" FROM CCS_SETUP_LOCAL_RANGE WHERE STG_ID = "+idLastStage+")";
//		conn.createStatement().executeUpdate(qeInsertSelectLocalStorageRange);
//		
//
//		String qeInsertSelectCustodyService = "INSERT INTO CCS_SETUP_CUSTODY_SERVICE(CUR_ID, SCS_BASE_LINE, SCS_FEE_COST, STG_ID)( " +
//												 "SELECT CUR_ID, SCS_BASE_LINE, SCS_FEE_COST, "+idStage+" FROM CCS_SETUP_CUSTODY_SERVICE WHERE STG_ID = "+idLastStage+")";
//		conn.createStatement().executeUpdate(qeInsertSelectCustodyService);
//	}
    
	
	
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
//			logger.debug("qeSelectProductsImportUnits: "+qeSelectProductsImportUnits);
//			
//			rs = conn.createStatement().executeQuery(qeSelectProductsImportUnits);
//			while (rs.next()){
//				
//				//Select de actualización con Item Master Base JDE
//				String qeSelectItemMasterJDE = "SELECT ITJ_COD_HUB, ITJ_ID_PRODUCT, ITJ_DESCRIPTION_PRODUCT, SIT_ID, ITJ_REPLY, " +
//												"ITJ_FOB_COST, ITJ_HIGH_PALETTE, ITJ_LARGE_PALETTE, ITJ_WIDE_PALETTE, PRM_ID, PFA_ID, PTY_ID, SDD_COD, PRP_ID,  PTR_ID, PTL_ID, ITJ_WEIGTH_PALETTE, TRA_ID, SIS_ID, SLR_ID, SSR_ID, ITJ_UNIT_BY_PALETTE " + // 
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
//												", LIM_TMP_SIS_ID      = "+rsSelectItemMasterJDE.getInt(19)+" " +
//												", SLR_ID              = "+rsSelectItemMasterJDE.getInt(20)+" " +
//												", SSR_ID              = "+rsSelectItemMasterJDE.getInt(21)+" " +
//												", LIM_UNIT_BY_PALETTE = "+rsSelectItemMasterJDE.getInt(22)+" " +
//												"WHERE IMU_COD_PRODUCT = '"+rs.getString(2)+"' AND STG_ID = "+idStage+"";
//					
//					logger.debug("qeUpdateItemMaster: "+qeUpdateItemMaster);
//					
//					Statement stmtUpdateLIM = conn.createStatement();
//					stmtUpdateLIM.executeUpdate(qeUpdateItemMaster);
//					
//					//Insertando sobre la tabla de transportes y sitios
//					String qeInsertSiteSourceTransport = "INSERT INTO CCS_SITE_SOURCE_TRANSPORT ( " +
//							"SIS_ID, IMU_COD_PRODUCT, STG_ID, TRA_ID ) " +
//							"VALUES( " +
//							""+rsSelectItemMasterJDE.getInt(19)+", '"+rs.getString(2)+"', "+rs.getString(1)+", "+rsSelectItemMasterJDE.getInt(18)+")";		
//					
//					logger.debug("qeInsertSiteSourceTransport: "+qeInsertSiteSourceTransport);
//					
//					Statement stmtInsertSST = conn.createStatement();
//					stmtInsertSST.executeUpdate(qeInsertSiteSourceTransport);
//					
//					//Cerrando cursores
//					stmtUpdateLIM.close();
//					stmtInsertSST.close();
//					
//				}
//				
//				stmtSelectItemMasterJDE.close();
//				rsSelectItemMasterJDE.close();
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new DAOException(ConstantsFinalObject.EXCEPTION_DAO_GENERIC_ERR_COD, e);
//		} 
//	}
	
    
	private String calTotalMedia(ImportUnitVO importUnitVO){
		String value ="";
		
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
		}
		
		
		return value;
	}
	
    private String replaceString(String cadena){
    	return cadena.replace("'", "''");
    }
    
    private int converToNumber(String number){
    	return (int)Math.floor(Double.parseDouble(number));
    }

	public List<ErrorField> getListErrorFields() {
		return listErrorFields;
	}

	public void setListErrorFields(List<ErrorField> listErrorFields) {
		this.listErrorFields = listErrorFields;
	}
	
	
	
	
	
	
  
}