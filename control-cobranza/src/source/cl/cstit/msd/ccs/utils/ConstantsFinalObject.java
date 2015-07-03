package cl.cstit.msd.ccs.utils;


import java.util.ResourceBundle;

public final class ConstantsFinalObject {
	
	private static ResourceBundle bundle = ResourceBundle.getBundle("messagesErrorsBundle");
	public static String getValue(String key){return bundle.getString(key);}
	
	//Valores iniciales
	public static final int INITIAL_VALUE_CERO           = 0;
	public static final String INITIAL_VALUE_ZERO        = "0";
	public static final String INITIAL_VALUE_BLANK       = "";
	
	//Exceptions codes
	public static final String E_DAO_NO_ERROR_COD       = "000";
	public static final String E_DAO_NO_USER_VALID_COD  = "001";
	public static final String E_DAO_INSERT_ERR_COD     = "002";
	public static final String E_DAO_NO_REGISTER        = "003";
	public static final String E_DAO_BUDGET_EXIST       = "004";
	public static final String E_DAO_PERIOD_BUDGET      = "005";
	public static final String E_DAO_PRODUCT_FREIGHT_IS = "006";
	public static final String E_DAO_NO_EXIST_BUDGET    = "007";
	
	public static final String E_DAO_EXIST_CODE_DUTY    = "008";
	public static final String E_DAO_EXIST_CODE_DUTY_IM = "009";
	
	
	
	
	
	public static final String E_DAO_NO_FOUND_COD      = "100";
	public static final String E_DAO_NO_FOUND_COD_MOV  = "101";
	
	public static final String E_DAO_NO_KEY_VALID          = "102";
	public static final String E_DAO_NO_KEY_VALID_CONFIRM  = "103";
	
	
	public static final String E_DAO_USER_EXIST  = "104";
	
	
	public static final String EXCEPTION_DAO_GENERIC_ERR_COD     = "999";
	
	
	
	
	public static final String ITEM_MASTER_TYPE_PRODUCT     	= "ITEM_MASTER_TYPE_PRODUCT";
	public static final String ITEM_MASTER_FAMILY_PRODUCT   	= "ITEM_MASTER_FAMILY_PRODUCT";
	public static final String ITEM_MASTER_UMEASURE_PRODUCT 	= "ITEM_MASTER_UMEASURE_PRODUCT";
	public static final String ITEM_MASTER_FOB_PRODUCT      	= "ITEM_MASTER_FOB_PRODUCT";
	public static final String ITEM_MASTER_SITE_PRODUCT     	= "ITEM_MASTER_SITE_PRODUCT";
	public static final String ITEM_MASTER_IS_IVA_PRODUCT     	= "ITEM_MASTER_IS_IVA_PRODUCT";
	public static final String ITEM_MASTER_HIGH_P_PRODUCT   	= "ITEM_MASTER_HIGH_P_PRODUCT";
	public static final String ITEM_MASTER_LARGE_P_PRODUCT  	= "ITEM_MASTER_LARGE_P_PRODUCT";
	public static final String ITEM_MASTER_WIDE_P_PRODUCT   	= "ITEM_MASTER_WIDE_P_PRODUCT";
	public static final String ITEM_MASTER_WEIGHT_P_PRODUCT 	= "ITEM_MASTER_WEIGHT_P_PRODUCT";
	public static final String ITEM_MASTER_UNITS_P_PRODUCT  	= "ITEM_MASTER_UNITS_P_PRODUCT";
	public static final String ITEM_MASTER_TRANS_PRODUCT    	= "ITEM_MASTER_TRANS_PRODUCT";
	
	public static final String ITEM_MASTER_PRESEN_PRODUCT   	= "ITEM_MASTER_PRESEN_PRODUCT";
	public static final String ITEM_MASTER_TRADE_PRODUCT    	= "ITEM_MASTER_TRADE_PRODUCT";
	public static final String ITEM_MASTER_TYPE_L_PRODUCT   	= "ITEM_MASTER_TYPE_L_PRODUCT";
	public static final String ITEM_MASTER_CUSTOM_DUTIES     	= "ITEM_MASTER_CUSTOM_DUTIES";
	public static final String ITEM_MASTER_LOCAL_ROUTES     	= "ITEM_MASTER_LOCAL_ROUTES";
	public static final String ITEM_MASTER_RATE_STORAGE     	= "ITEM_MASTER_RATE_STORAGE";
	
	
	
	public static final String IMPORT_UNITS_LOCAL_CODE			= "IMPORT_UNITS_LOCAL_CODE";
	public static final String IMPORT_UNITS_DESCRIPTION_PRODUCT	= "IMPORT_UNITS_DESCRIPTION_PRODUCT";
	public static final String IMPORT_UNITS_JAN					= "IMPORT_UNITS_JAN";
	public static final String IMPORT_UNITS_FEB					= "IMPORT_UNITS_FEB";
	public static final String IMPORT_UNITS_MAR					= "IMPORT_UNITS_MAR";
	public static final String IMPORT_UNITS_APR					= "IMPORT_UNITS_APR";
	public static final String IMPORT_UNITS_MAY					= "IMPORT_UNITS_MAY";
	public static final String IMPORT_UNITS_JUN					= "IMPORT_UNITS_JUN";
	public static final String IMPORT_UNITS_JUL					= "IMPORT_UNITS_JUL";
	public static final String IMPORT_UNITS_AUG					= "IMPORT_UNITS_AUG";
	public static final String IMPORT_UNITS_SEP					= "IMPORT_UNITS_SEP";
	public static final String IMPORT_UNITS_OCT					= "IMPORT_UNITS_OCT";
	public static final String IMPORT_UNITS_NOV					= "IMPORT_UNITS_NOV";
	public static final String IMPORT_UNITS_DEC					= "IMPORT_UNITS_DEC";
	public static final String IMPORT_UNITS_AJUSTED				= "IMPORT_UNITS_AJUSTED";
	public static final String IMPORT_DESRIPTION_PRODUCT		= "IMPORT_DESRIPTION_PRODUCT";
	
//	public static final String FRO_CONTAINER_RFER				= "FRO_CONTAINER_RFER";
//	public static final String FRO_INTER_FREIGHT				= "FRO_INTER_FREIGHT";
//	public static final String FRO_OTHERS						= "FRO_OTHERS";
//	public static final String FRO_DESTINATION_CHARGES			= "FRO_DESTINATION_CHARGES";
//	public static final String FRO_PALETTES_CHARGES				= "FRO_PALETTES_CHARGES";
	
	public static final String FOT_ID_TANSPORT					= "FOT_ID_TANSPORT";
	public static final String FOT_NAME_TANSPORT				= "FOT_NAME_TANSPORT";
	public static final String FOT_ID_CURRENCY					= "FOT_ID_CURRENCY";
	public static final String FOT_PALETTE_FREIGHT				= "FOT_PALETTE_FREIGHT";
	public static final String FOT_ORDERS_NUMBER				= "FOT_ORDERS_NUMBER";
	public static final String FOT_INCREASE						= "FOT_INCREASE";
	
	
	public static final String APO_CURRENCY						= "APO_CURRENCY";
	public static final String APO_NAME_FREIGHT					= "APO_NAME_FREIGHT";
	public static final String APO_INCREASE_FREIGHT				= "APO_INCREASE_FREIGHT";
	
	public static final String APO_AWB_RATE_FREIGHT				= "APO_AWB_RATE_FREIGHT";
	public static final String APO_OTHER_FEE_FREIGHT			= "APO_OTHER_FEE_FREIGHT";
	public static final String APO_FSC_FREIGHT					= "APO_FSC_FREIGHT";
	public static final String APO_SSC_FREIGHT					= "APO_SSC_FREIGHT";
	public static final String APO_ORDERS_FREIGHT				= "APO_ORDERS_FREIGHT";
	public static final String APO_PALETTES_FREIGHT				= "APO_PALETTES_FREIGHT";
	public static final String INC_APO_AWB_RATE_FREIGHT			= "INC_APO_AWB_RATE_FREIGHT";
	public static final String INC_APO_OTHER_FEE_FREIGHT		= "INC_APO_OTHER_FEE_FREIGHT";
	public static final String INC_APO_FSC_FREIGHT				= "INC_APO_FSC_FREIGHT";
	public static final String INC_APO_SSC_FREIGHT				= "INC_APO_SSC_FREIGHT";
	
	
	
	public static final String OCP_NAME_CONCEPT					= "OCP_NAME_CONCEPT";
	public static final String OCP_RATE_CONCEPT					= "OCP_RATE_CONCEPT";
	
	
	
	public static final String APO_RATE_NAME_CONCEPT			= "APO_RATE_NAME_CONCEPT";
	public static final String APO_RATE_VALUE_CONCEPT			= "APO_RATE_VALUE_CONCEPT";
	
	
	
	public static final String ROUTE_LOCALTRANS_NAME			= "ROUTE_LOCALTRANS_NAME";
	public static final String ROUTE_LOCALTRANS_RATE			= "ROUTE_LOCALTRANS_RATE";
	
	public static final String RANGE_LOCALTRANS_FROM			= "RANGE_LOCALTRANS_FROM";
	public static final String RANGE_LOCALTRANS_TO				= "RANGE_LOCALTRANS_TO";
	public static final String RANGE_LOCALTRANS_RATE			= "RANGE_LOCALTRANS_RATE";
	
	
	public static final String RATE_WAREHOUSE_NAME				= "RATE_WAREHOUSE_NAME";
	public static final String RATE_WAREHOUSE_RATE				= "RATE_WAREHOUSE_RATE";
	
	public static final String RANGE_WAREHOUSE_FROM				= "RANGE_WAREHOUSE_FROM";
	public static final String RANGE_WAREHOUSE_TO				= "RANGE_WAREHOUSE_TO";
	public static final String RANGE_WAREHOUSE_RATE				= "RANGE_WAREHOUSE_RATE";
	
	
	
	public static final String FAR_ORDERS_NUMBER				= "FAR_ORDERS_NUMBER";
	public static final String FAR_ROUTE_DESCRIPTION			= "FAR_ROUTE_DESCRIPTION";
	public static final String FAR_INCREASE						= "FAR_INCREASE";
	public static final String FAR_AWB_KG_RATES					= "FAR_AWB_KG_RATES";
	public static final String FAR_OTHER_FFW_FEES				= "FAR_OTHER_FFW_FEES";
	public static final String FAR_FUEL_SURCHARGE				= "FAR_FUEL_SURCHARGE";
	public static final String FAR_SECURITY_CHARGE				= "FAR_SECURITY_CHARGE";
	public static final String FAR_CURRENCY_ID					= "FAR_CURRENCY_ID";
	
	public static final String FAR_INCREASE_AWB					= "FAR_INCREASE_AWB";
	public static final String FAR_INCREASE_FFW					= "FAR_INCREASE_FFW";
	public static final String FAR_INCREASE_FSC					= "FAR_INCREASE_FSC";
	public static final String FAR_INCREASE_SCC					= "FAR_INCREASE_SCC";
	
	
	
	public static final String SFE_NAME							= "SFE_NAME";
	public static final String SFE_RATE							= "SFE_RATE";
	public static final String SFE_ACRONYM						= "SFE_ACRONYM";
	
	
	
	public static final String FRR_FROM							= "FRR_FROM";
	public static final String FRR_TO							= "FRR_TO";
	public static final String FRR_RATE							= "FRR_RATE";
	
	public static final String SDD_DUTY_NAME					= "SDD_DUTY_NAME";
	public static final String SDD_DUTY_TARIFF					= "SDD_DUTY_TARIFF";
	public static final String SDD_OTHER_TAXES					= "SDD_OTHER_TAXES";
	public static final String SDD_DUTY_CODE					= "SDD_DUTY_CODE";
	
	public static final String SDH_OTHER_TAXES_FODINFA			= "SDH_OTHER_TAXES_FODINFA";
	public static final String SDH_VAT_IMPORTS					= "SDH_VAT_IMPORTS";
	public static final String SDH_OTHER_TAXES_IMP_CAP			= "SDH_OTHER_TAXES_IMP_CAP";

	
	public static final String SOM_PERCENT_TP					= "SOM_PERCENT_TP";
	public static final String SOM_PROCESSING_ENTRY				= "SOM_PROCESSING_ENTRY";
	public static final String SOM_PROCESSING_FEE				= "SOM_PROCESSING_FEE";
	public static final String SOM_RATE_KG						= "SOM_RATE_KG";
	public static final String CUR_ID							= "CUR_ID";
	
	public static final String SST_ENTRY						= "SST_ENTRY";
	public static final String SST_PERCENT_COST					= "SST_PERCENT_COST";
	public static final String SST_PROCESSING_FEE				= "SST_PROCESSING_FEE";
	
	public static final String SSR_RANGE_FROM					= "SSR_RANGE_FROM";
	public static final String SSR_RANGE_UO						= "SSR_RANGE_UO";
	public static final String SSR_RANGE_RATE					= "SSR_RANGE_RATE";
	

	public static final String SLT_ENTRY						= "SLT_ENTRY";
	public static final String SLT_PERCENT_COST					= "SLT_PERCENT_COST";
	public static final String SLT_PROCESSING_FEE				= "SLT_PROCESSING_FEE";
	
	public static final String SLR_RANGE_FROM					= "SLR_RANGE_FROM";
	public static final String SLR_RANGE_UP						= "SLR_RANGE_UP";
	public static final String SLR_RANGE_RATE_KG				= "SLR_RANGE_RATE";
	
	
} 
