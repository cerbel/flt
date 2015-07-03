package cl.cstit.msd.ccs.actions;

import java.util.List;


import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import cl.cstit.msd.ccs.delegate.GeneralTransactionsDelegate;
import cl.cstit.msd.ccs.delegate.MoveCostDelegate;
import cl.cstit.msd.ccs.exception.DAOException;
import cl.cstit.msd.ccs.vo.ActivePackOutFreightVO;
import cl.cstit.msd.ccs.vo.ConceptCostAPOFreightVO;
import cl.cstit.msd.ccs.vo.ConceptCostOtherFreightVO;
import cl.cstit.msd.ccs.vo.CustodyVO;
import cl.cstit.msd.ccs.vo.FiltrosVO;
import cl.cstit.msd.ccs.vo.FreighAirRangeVO;
import cl.cstit.msd.ccs.vo.FreightAirVO;
import cl.cstit.msd.ccs.vo.FreightOthersVO;
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
import cl.cstit.msd.ccs.vo.SetupCustomDutiesHeadVO;
import cl.cstit.msd.ccs.vo.SetupExchangeVO;
import cl.cstit.msd.ccs.vo.SetupMacroValVO;
import cl.cstit.msd.ccs.vo.SetupOpConsolidationVO;
import cl.cstit.msd.ccs.vo.StageVO;
import cl.cstit.msd.ccs.vo.TempStorageVO;
import cl.cstit.msd.ccs.vo.UserVO;

import com.opensymphony.xwork2.Action;

public class MoveCostAction{
	
	private static Logger logger = Logger.getLogger(MoveCostAction.class);
	
	private String statusMessageError = new String();
	
	private String idConceptOtherFreight;
	private String idOtherFreight;
	private String idFreightAir;
	private String idRangeFreighAir;
	private String itemMasterAttribute; 
	private String importUnitAttribute; 
	private String freightOthersAttributes; 
	private String freightFlyAttributes; 
	private String setupMacrovarAttributes;
	private String setupExchangeAttributes;
	private String setupCustomDuties;
	private String setupOpeManage;
	private String setupTempStorage;
	private String locaTransportStorage;
	private String socOrders;
	private String conceptOtherFreight;
	private String idOperativeActivity;
	private String idRateStorage;
	private String idRangeStorage;
	private String idAPOFreight;
	private String idConceptCostWarehouse;
	private String idRouteLocalTransport;
	private String idRangeLocalTransport;
	private String idConceptCostLocalTransport;
	private String idRateAPOFreight;
	private String idExchangeCurrency;
	private String idLocalDuty;
	
	
	private List<ItemMasterProductVO> listItemMasterProductVO;
	private List<FreightAirVO> listFreightAirVO;
	private List<SetupExchangeVO> listSetupExchangeVO;
	private List<SetupMacroValVO> listSetupMacroValVO;
	private List<SetupOpConsolidationVO> listSetupOpConsolidationVO;
	private List<OpeManageVO> listOpeManageVO;
	private List<TempStorageVO> listTempStorageVO;
	private List<LocalTransportVO> listLocalTransportVO;
	private List<OtherFreightVO> listOtherFreightVO;
	private List<FreighAirRangeVO> listFreighAirRangeVO;
	private List<ConceptCostOtherFreightVO> listConceptCostOtherFreightVO;
	private List<RateWarehouseVO> listRateWarehouseVO;
	private List<RangeWarehouseVO> listRangeWarehouseVO;
	private List<RouteLocalTransportVO> listRouteLocalTransportVO;
	private List<RangeLocalTransportVO> listLocalTransportRange;
	private List<ActivePackOutFreightVO> listActivePackOutFreightVO;
	private List<ConceptCostAPOFreightVO> listConceptCostAPOFreightVO;

	private List<GeneralComboVO> listProductFamilies;
	private List<GeneralComboVO> listProductTransports;
	private List<GeneralComboVO> listProductUnitMeasure;
	private List<GeneralComboVO> listSiteSource;
	private List<GeneralComboVO> listProductTypes;
//	private List<GeneralComboVO> listSiteSources;
	private List<GeneralComboVO> listProductTrade;
	private List<GeneralComboVO> listProductPresentation;
	private List<GeneralComboVO> listProductTypeLoad;
	private List<GeneralComboVO> listCurrency;
	private List<GeneralComboVO> listTempStorageRange;
	
	private List<GeneralComboVO> listCustomDuty;
	private List<GeneralComboVO> listLocalRoute;
	private List<GeneralComboVO> listRateStorage;
	private List<GeneralComboVO> listCurrencyFreight;
	private List<GeneralComboVO> listOtherTransports;
	
	private List<ImportUnitVO> listImportUnitVO;
	private ImportUnitVO importUnitVO;
	
	private SetupCustomDutiesHeadVO setupCustomDutiesHeadVO;
	private FreightOthersVO freightOthersVO;
	private StageVO stageVO;
	private RangeGenericVO rangeGenericVO;
	private FiltrosVO filtrosVO;
	private CustodyVO custodyVO;
	
	
	private String valor;
	
	
	public String moveCost() {
		
		ServletActionContext.getRequest().getSession().removeAttribute("idStage");
		
		return "SUCCESS";
	}
	
	
	public String changeStage() {
		
		try {
			stageVO = new MoveCostDelegate().getStage((String) ServletActionContext.getRequest().getSession().getAttribute("idStage"));
			listCustomDuty 	= new MoveCostDelegate().getListCustomDuty((String) ServletActionContext.getRequest().getSession().getAttribute("idStage"));
			new MoveCostDelegate().deleteFilter((String) ServletActionContext.getRequest().getSession().getAttribute("idStage"));


		} catch (DAOException e) {
			
			e.printStackTrace();
		}
		
		return "SUCCESS";
	}
	
	
	
	public String replicateFreight() {
		
		try {
			new MoveCostDelegate().replicateFreight(freightFlyAttributes, (String) ServletActionContext.getRequest().getSession().getAttribute("idStage"));

		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	
	public String deleteFreightRoute() {
		
		try {
			new MoveCostDelegate().deleteFreightRoute(freightFlyAttributes, (String) ServletActionContext.getRequest().getSession().getAttribute("idStage"));

		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	
	public String initSimulate() {
		
		UserVO userSessionVO = (UserVO)ServletActionContext.getRequest().getSession().getAttribute("userSessionVO");
		
		stageVO.setIsidUserStage(userSessionVO.getIsidUser());
		stageVO.setIdTypeStage("1");
		stageVO.setIdStatusStage("1");
		
		try {
			String idStage = new MoveCostDelegate().initSimulation(stageVO);
			ServletActionContext.getRequest().getSession().setAttribute("idStage", idStage);
			listCustomDuty 			= new MoveCostDelegate().getListCustomDuty((String) ServletActionContext.getRequest().getSession().getAttribute("idStage"));

		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String initItemMaster() {
		
		try {
			listItemMasterProductVO = new MoveCostDelegate().initItemMaster((String) ServletActionContext.getRequest().getSession().getAttribute("idStage"), stageVO.getIdSiteStage());
			
			listProductFamilies     = (List<GeneralComboVO>) ServletActionContext.getRequest().getSession().getAttribute("listProductFamiliesSession");
			listProductTransports   = (List<GeneralComboVO>) ServletActionContext.getRequest().getSession().getAttribute("listProductTransportsSession");
			listProductUnitMeasure  = (List<GeneralComboVO>) ServletActionContext.getRequest().getSession().getAttribute("listProductUnitMeasureSession");
			listProductTypes        = (List<GeneralComboVO>) ServletActionContext.getRequest().getSession().getAttribute("listProductTypesSession");
			listProductTrade        = (List<GeneralComboVO>) ServletActionContext.getRequest().getSession().getAttribute("listProductTradeSession");
			listProductPresentation = (List<GeneralComboVO>) ServletActionContext.getRequest().getSession().getAttribute("listProductPresentationSession");
			listProductTypeLoad     = (List<GeneralComboVO>) ServletActionContext.getRequest().getSession().getAttribute("listProductTypeLoadSession");
			

			listSiteSource          = new GeneralTransactionsDelegate().listSiteSource((String) ServletActionContext.getRequest().getSession().getAttribute("idStage")); 
			
			
					
			listCustomDuty 	= new MoveCostDelegate().getListCustomDuty((String) ServletActionContext.getRequest().getSession().getAttribute("idStage"));
			
			listLocalRoute	= new MoveCostDelegate().getListLocalRoute((String) ServletActionContext.getRequest().getSession().getAttribute("idStage"));
			listRateStorage	= new MoveCostDelegate().getListRateStorage((String) ServletActionContext.getRequest().getSession().getAttribute("idStage"));
			
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		
		return Action.SUCCESS;
	}
	
	
	public String simulateStage() {
		
		try {
			
			stageVO.setIdStage((String) ServletActionContext.getRequest().getSession().getAttribute("idStage"));
			stageVO.setIsidUserStage(((UserVO)ServletActionContext.getRequest().getSession().getAttribute("userSessionVO")).getIsidUser());
			
			logger.debug("idStage: "+ServletActionContext.getRequest().getSession().getAttribute("idStage"));
			
			new MoveCostDelegate().simulateStage(stageVO);
			
		} catch (DAOException e) {
			
			statusMessageError = e.getMessage();
			
		}
		
		return Action.SUCCESS;
	}


	public String itemMasterUpdateAttributes() {
		
		try {
			String[] arrayItemMasterAttribute = itemMasterAttribute.split("-");
			
			new MoveCostDelegate().itemMasterUpdateAttributes((String) ServletActionContext.getRequest().getSession().getAttribute("idStage"), 
					arrayItemMasterAttribute[1], arrayItemMasterAttribute[2], arrayItemMasterAttribute[0]);
			
		} catch (DAOException e) {
			
			statusMessageError = e.getMessage();
			
		}
		
		return Action.SUCCESS;
	}
	
	
	public String initFreightAir() {
		
		try {
			
			listFreightAirVO = new MoveCostDelegate().initFreightAir((String) ServletActionContext.getRequest().getSession().getAttribute("idStage"), stageVO.getIdSiteStage());
			
			listCurrencyFreight = new MoveCostDelegate().listCurrencyFreight((String) ServletActionContext.getRequest().getSession().getAttribute("idStage"));
			
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		
		return Action.SUCCESS;
	}
	
	
	public String listRangeFreight() {
		
		try {

			listFreighAirRangeVO = new MoveCostDelegate().listRangeFreightAir(idFreightAir);
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	public String replicateRangeFraightAir() {
		
		try {

			new MoveCostDelegate().replicateRangeFraightAir(idRangeFreighAir);
			
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	
	public String deleteRangeFraightAir() {
		
		try {

			new MoveCostDelegate().deleteRangeFraightAir(idRangeFreighAir);
			
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	
	public String initFreightOthers() {
		
		try {
			
//			listSiteSources = new MoveCostDelegate().listSiteSources((String) ServletActionContext.getRequest().getSession().getAttribute("idStage"));
			
			listOtherFreightVO = new MoveCostDelegate().listOtherFreights((String) ServletActionContext.getRequest().getSession().getAttribute("idStage"));
			
			listOtherTransports = new MoveCostDelegate().listOtherTransports();
			
			listCurrencyFreight = new MoveCostDelegate().listCurrencyFreight((String) ServletActionContext.getRequest().getSession().getAttribute("idStage"));
			
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	
	public String initSetupMacroExchange() {
		
		try {
			
			listSetupExchangeVO = new MoveCostDelegate().getSetupExchangeVO((String) ServletActionContext.getRequest().getSession().getAttribute("idStage"));
			
			listSetupMacroValVO = new MoveCostDelegate().getSetupMacroValVO((String) ServletActionContext.getRequest().getSession().getAttribute("idStage"));
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	public String initSetupCustomDuties() {
		
		try {
			
			setupCustomDutiesHeadVO = new MoveCostDelegate().initCustomDuties((String) ServletActionContext.getRequest().getSession().getAttribute("idStage"));
			
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	
	public String initSetupOpConsolidation() {
		
		try {
			listSiteSource   = new GeneralTransactionsDelegate().listSiteSource((String) ServletActionContext.getRequest().getSession().getAttribute("idStage"));
//			listFreightModes = (List<GeneralComboVO>) ServletActionContext.getRequest().getSession().getAttribute("listFreightModesSession");
			
//			listSetupOpConsolidationVO = (List<SetupOpConsolidationVO>) new MoveCostDelegate().getSetupOperativeConsolidation((String) ServletActionContext.getRequest().getSession().getAttribute("idStage"));
			listOpeManageVO = new MoveCostDelegate().getListOpeManage((String) ServletActionContext.getRequest().getSession().getAttribute("idStage"));
			

			
			listCurrency	= new MoveCostDelegate().listCurrencBySite(freightOthersVO.getIdSiteSourceFreight());

		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
//	public String obtieneFreightOthers() {
//		
//		try {
//			freightOthersVO = new MoveCostDelegate().getFreightOthers(freightOthersVO.getIdSiteSourceFreight() , (String) ServletActionContext.getRequest().getSession().getAttribute("idStage"), freightOthersVO.getIdModeFreight());
//			
//			logger.debug("freightOthersVO.getIdModeFreight(): "+freightOthersVO.getIdModeFreight());
//		} catch (DAOException e) {
//			statusMessageError = e.getMessage();
//		}
//		return Action.SUCCESS;
//	}
	
	public String initImportUnit(){
		
		try{	
			listImportUnitVO = new MoveCostDelegate().getListImporUnits((String) ServletActionContext.getRequest().getSession().getAttribute("idStage"));

		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		
		return Action.SUCCESS;
	}
	
	//
	public String importUnitUpdateAttributes() {
		
		
		
		try {
			String[] arrayImportUnitAttribute = importUnitAttribute.split("-");
			
			if (arrayImportUnitAttribute.length == 3){
				
				
				
				
				setImportUnitVO(new MoveCostDelegate().importUnitUpdateAttributes((String) ServletActionContext.getRequest().getSession().getAttribute("idStage"), 
						arrayImportUnitAttribute[1], arrayImportUnitAttribute[2], arrayImportUnitAttribute[0]));
				
			}else{
				
				
				
				setImportUnitVO(new MoveCostDelegate().importUnitUpdateAttributes((String) ServletActionContext.getRequest().getSession().getAttribute("idStage"), 
						arrayImportUnitAttribute[1], arrayImportUnitAttribute[3], arrayImportUnitAttribute[0]));
				
			}
			
		} catch (DAOException e) {
			
			statusMessageError = e.getMessage();
			
		}
		
		return Action.SUCCESS;
	}

	
	public String freightOthersUpdateAttributes() {
		
		try {
			String[] arrayImportUnitAttribute = freightOthersAttributes.split("-");

			new MoveCostDelegate().updateFreightOther(arrayImportUnitAttribute[0],
													  arrayImportUnitAttribute[1], 
													  arrayImportUnitAttribute[2]);
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	
	public String freightAPOUpdateAttributes() {
		
		try {
			String[] arrayImportUnitAttribute = freightOthersAttributes.split("-");

			new MoveCostDelegate().updateFreightAPO(arrayImportUnitAttribute[0],
													  arrayImportUnitAttribute[1], 
													  arrayImportUnitAttribute[2]);
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}	
	
	
	
	public String freightFlyUpdateAttributes() {
		
		try {
			String[] arrayImportUnitAttribute = freightFlyAttributes.split("-");

			new MoveCostDelegate().updateFreightFly(arrayImportUnitAttribute[0],
													arrayImportUnitAttribute[1], 
													(String) ServletActionContext.getRequest().getSession().getAttribute("idStage"), 
													arrayImportUnitAttribute[2],
													arrayImportUnitAttribute[3]);
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		
		return Action.SUCCESS;
	}
	

	public String freightFlyRangeUpdateAttributes() {
		
		try {
			String[] arrayImportUnitAttribute = freightFlyAttributes.split("-");

			new MoveCostDelegate().updateFreightFlyRange(arrayImportUnitAttribute[0],
													arrayImportUnitAttribute[1], 
													arrayImportUnitAttribute[2]
													);
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		
		return Action.SUCCESS;
	}

	
	
	public String setupExchangeUpdateAttributes() {
		
		try {
			String[] arrayImportUnitAttribute = setupExchangeAttributes.split("-");

			new MoveCostDelegate().updateSetupExchange(arrayImportUnitAttribute[0], 
													   arrayImportUnitAttribute[1], 
													   arrayImportUnitAttribute[2]);
			
			
			
			//invocar combo selector dao
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		
		return Action.SUCCESS;
	}

	
	public String setupMacrovarUpdateAttributes() {
		
		try {
			String[] arrayImportUnitAttribute = setupMacrovarAttributes.split("-");

			new MoveCostDelegate().updateSetupMacrovars(arrayImportUnitAttribute[0], 
														(String) ServletActionContext.getRequest().getSession().getAttribute("idStage"), 
														arrayImportUnitAttribute[1]);
			
			
			
			//invocar combo selector dao
		} catch (DAOException e) {
			
			statusMessageError = e.getMessage();
			
		}
		
		return Action.SUCCESS;
	}

	public String setupCustomDutiesUpdateAttributes() {
		
		try {
			String[] arrayImportUnitAttribute = setupCustomDuties.split("-");

			new MoveCostDelegate().updateSetupCustomDutiesDetail(	arrayImportUnitAttribute[0],
																	arrayImportUnitAttribute[1],
																	(String) ServletActionContext.getRequest().getSession().getAttribute("idStage"), 
																	arrayImportUnitAttribute[2]);
			
			
			
			//invocar combo selector dao
		} catch (DAOException e) {
			
			statusMessageError = e.getMessage();
			
		}
		
		return Action.SUCCESS;
	}	
	
	public String setupCustomDutiesHeadUpdateAttributes() {
		
		try {
			String[] arrayImportUnitAttribute = setupCustomDuties.split("-");

			new MoveCostDelegate().updateSetupCustomDutiesHead(	arrayImportUnitAttribute[0],
																		arrayImportUnitAttribute[1],
																		(String) ServletActionContext.getRequest().getSession().getAttribute("idStage"));
			
			
			
			//invocar combo selector dao
		} catch (DAOException e) {
			
			statusMessageError = e.getMessage();
			
		}
		
		return Action.SUCCESS;
	}
	
	
	public String setupOpeManageUpdateAttributes() {
		
		try {
			String[] arrayImportUnitAttribute = setupOpeManage.split("-");

//			setValor(new MoveCostDelegate().updateSetupOpeMange(	arrayImportUnitAttribute[0],
//												  		arrayImportUnitAttribute[1],
//												  		(String) ServletActionContext.getRequest().getSession().getAttribute("idStage"),
//												  		arrayImportUnitAttribute[2]) + "-" + arrayImportUnitAttribute[2]);
			
			

			new MoveCostDelegate().updateSetupOpeMange(	arrayImportUnitAttribute[0],
			  		arrayImportUnitAttribute[1],
			  		(String) ServletActionContext.getRequest().getSession().getAttribute("idStage"),
			  		arrayImportUnitAttribute[2]);
			
			//LG
			
			//invocar combo selector dao
		} catch (DAOException e) {
			
			statusMessageError = e.getMessage();
			
		}
		
		return Action.SUCCESS;
	}
	
//	public String obtieneOperativeManageConsolidation() {
//		
//		try {
//			freightOthersVO = new MoveCostDelegate().getFreightOthers(freightOthersVO.getIdSiteSourceFreight() , (String) ServletActionContext.getRequest().getSession().getAttribute("idStage"), freightOthersVO.getIdModeFreight());
//			
//			logger.debug("freightOthersVO.getIdModeFreight(): "+freightOthersVO.getIdModeFreight());
//		} catch (DAOException e) {
//			statusMessageError = e.getMessage();
//		}
//		return Action.SUCCESS;
//	}
	
	public String obtieneOpeConsolidation() {
		
		try {

			socOrders = new MoveCostDelegate().getOpeConsolidationSocOrders(freightOthersVO.getIdSiteSourceFreight() , (String) ServletActionContext.getRequest().getSession().getAttribute("idStage"), freightOthersVO.getIdModeFreight());
			
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}

	
	public String setupOpeConsolidationUpdateAttributes() {
		
		try {
			String[] arrayImportUnitAttribute = setupOpeManage.split("-");

			new MoveCostDelegate().updateSetupOpeConsolidation(	arrayImportUnitAttribute[0],
												  				arrayImportUnitAttribute[1],
												  				(String) ServletActionContext.getRequest().getSession().getAttribute("idStage"),
												  				arrayImportUnitAttribute[2]);
			
			//LG
			
			//invocar combo selector dao
		} catch (DAOException e) {
			
			statusMessageError = e.getMessage();
			
		}
		
		return Action.SUCCESS;
	}
	
	
	
//	public String initSetupTempStorage() {
//		
//		try {
//			
//			listTempStorageVO = new MoveCostDelegate().getListTempStorage((String) ServletActionContext.getRequest().getSession().getAttribute("idStage"));
//			listCurrency	=  new MoveCostDelegate().listCurrencBySite(freightOthersVO.getIdSiteSourceFreight());
//			listTempStorageRange = new MoveCostDelegate().getListTempStorageRange((String) ServletActionContext.getRequest().getSession().getAttribute("idStage"));
//		} catch (DAOException e) {
//			statusMessageError = e.getMessage();
//		}
//		return Action.SUCCESS;
//	}
	
	
	public String setupTempStorageUpdateAttributes() {
		
		try {
			String[] arrayImportUnitAttribute = setupTempStorage.split("-");

			new MoveCostDelegate().updateSetupTempStorage(	arrayImportUnitAttribute[0],
												  				arrayImportUnitAttribute[1],
												  				(String) ServletActionContext.getRequest().getSession().getAttribute("idStage"),
												  				arrayImportUnitAttribute[2]);
		} catch (DAOException e) {
			
			statusMessageError = e.getMessage();
		}
		
		return Action.SUCCESS;
	}
	
	public String updateRatesWareHouse() {
		
		try {
			String[] arrayImportUnitAttribute = setupTempStorage.split("-");

			new MoveCostDelegate().updateRatesWareHouse(arrayImportUnitAttribute[0],
												  		arrayImportUnitAttribute[1],
												  		arrayImportUnitAttribute[2]);
		} catch (DAOException e) {
			
			statusMessageError = e.getMessage();
		}
		
		return Action.SUCCESS;
	}
	
	
	public String updateRoutesLocalTransport() {
		
		try {
			String[] arrayImportUnitAttribute = setupTempStorage.split("-");

			new MoveCostDelegate().updateRoutesLocalTransport(arrayImportUnitAttribute[0],
												  		arrayImportUnitAttribute[1],
												  		arrayImportUnitAttribute[2]);
		} catch (DAOException e) {
			
			statusMessageError = e.getMessage();
		}
		
		return Action.SUCCESS;
	}
	
	public String updateRangesLocalTransport() {
		
		try {
			String[] arrayImportUnitAttribute = setupTempStorage.split("-");

			new MoveCostDelegate().updateRangesLocalTransport(arrayImportUnitAttribute[0],
												  		arrayImportUnitAttribute[1],
												  		arrayImportUnitAttribute[2]);
		} catch (DAOException e) {
			
			statusMessageError = e.getMessage();
		}
		
		return Action.SUCCESS;
	}
	
	
	public String updateRangesWareHouse() {
		
		try {
			String[] arrayImportUnitAttribute = setupTempStorage.split("-");

			new MoveCostDelegate().updateRangesWareHouse(arrayImportUnitAttribute[0],
												  		arrayImportUnitAttribute[1],
												  		arrayImportUnitAttribute[2]);
		} catch (DAOException e) {
			
			statusMessageError = e.getMessage();
		}
		
		return Action.SUCCESS;
	}
	
	
	public String setupGetTempStorageRange() {
		
		try {
			rangeGenericVO = new MoveCostDelegate().getTempStorageRange(rangeGenericVO.getId());
			
		} catch (DAOException e) {
			
			statusMessageError = e.getMessage();
		}
		
		return Action.SUCCESS;
	}
	
	
	public String setupLocalRangeUpdateAttributes() {
		
		try {
			String[] arrayImportUnitAttribute = setupTempStorage.split("-");

			new MoveCostDelegate().updateSetupTempStorageRange(arrayImportUnitAttribute[0],
												  			   arrayImportUnitAttribute[1],
												  			   (String) ServletActionContext.getRequest().getSession().getAttribute("idStage"),
												  			   arrayImportUnitAttribute[2]);
			
		} catch (DAOException e) {
			
			statusMessageError = e.getMessage();
		}
		
		return Action.SUCCESS;
	}
	
	//Local Transport
//	public String initSetupLocalTransport() {
//		
//		try {
//			
//			listLocalTransportVO 	= new MoveCostDelegate().getListLocalTransport((String) ServletActionContext.getRequest().getSession().getAttribute("idStage"));
//			listCurrency			= new MoveCostDelegate().listCurrencBySite(freightOthersVO.getIdSiteSourceFreight());
//			listLocalTransportRange = new MoveCostDelegate().getListLocalTransportRange((String) ServletActionContext.getRequest().getSession().getAttribute("idStage"));
//			
//		} catch (DAOException e) {
//			statusMessageError = e.getMessage();
//		}
//		return Action.SUCCESS;
//	}
	
	
	public String setupLocalTransportUpdateAttributes() {
		
		try {
			String[] arrayImportUnitAttribute = locaTransportStorage.split("-");

			new MoveCostDelegate().updateSetupLocalTransport(	arrayImportUnitAttribute[0],
												  				arrayImportUnitAttribute[1],
												  				(String) ServletActionContext.getRequest().getSession().getAttribute("idStage"),
												  				arrayImportUnitAttribute[2]);
		} catch (DAOException e) {
			
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	
	public String setupGetLocalTransportRange() {
		
		try {
			rangeGenericVO = new MoveCostDelegate().getLocalTransportRange(rangeGenericVO.getId());
			
		} catch (DAOException e) {
			
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	
	public String setupLocalTransportRangeUpdateAttributes() {
		
		try {
			String[] arrayImportUnitAttribute = locaTransportStorage.split("-");

			new MoveCostDelegate().updateSetupLocalTransportRange(	arrayImportUnitAttribute[0],
												  			   		arrayImportUnitAttribute[1],
												  			   		(String) ServletActionContext.getRequest().getSession().getAttribute("idStage"),
												  			   		arrayImportUnitAttribute[2],
												  			   		arrayImportUnitAttribute[3]);
			
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		
		return Action.SUCCESS;
	}
	

	public String filtros(){
		
		try {
			new MoveCostDelegate().filtros((String) ServletActionContext.getRequest().getSession().getAttribute("idStage"), 
											filtrosVO.getFilterFamily(), 
											filtrosVO.getFilterPresentation(), 
											filtrosVO.getFilterType(), 
											filtrosVO.getFilterTypeLoad(), 
											filtrosVO.getFilterTrade(),
											filtrosVO.getFilterSiteSource(),
											filtrosVO.getFilterCustomDuty(),
											filtrosVO.getFilterProduct());
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	public String deleteFilterCustomDuty(){
		
		try{
			
			new MoveCostDelegate().deleteFilter((String) ServletActionContext.getRequest().getSession().getAttribute("idStage"));
			
		}catch (DAOException e){
			statusMessageError = e.getMessage();
		}
		
		return Action.SUCCESS;
	}	
	
	public String filterUno(){
		
		try {
			new MoveCostDelegate().filtroPorUno((String) ServletActionContext.getRequest().getSession().getAttribute("idStage"), 
												importUnitAttribute);
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	public String replicar(){
		
		try {
			new MoveCostDelegate().replicarProducto((String) ServletActionContext.getRequest().getSession().getAttribute("idStage"), 
												importUnitAttribute);
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	public String remove(){
		
		try {
			new MoveCostDelegate().removerProducto((String) ServletActionContext.getRequest().getSession().getAttribute("idStage"), 
												importUnitAttribute);
			
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	
	public String initCustodySetup(){
		
		try {
			custodyVO = new MoveCostDelegate().getCustudySetup((String) ServletActionContext.getRequest().getSession().getAttribute("idStage"));
			
			listCurrency = new MoveCostDelegate().listCurrencBySite(stageVO.getIdSiteStage());
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	
	public String updateBaseLineCustody(){
		
		try {
			new MoveCostDelegate().updateBaseLineCustody(custodyVO.getBaseLineCustody(), (String) ServletActionContext.getRequest().getSession().getAttribute("idStage"));
			
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	
	public String updateFeeCostCustody(){
		
		try {
			new MoveCostDelegate().updateFeeCostCustody(custodyVO.getFeeCostCustody(), (String) ServletActionContext.getRequest().getSession().getAttribute("idStage"), custodyVO.getIdCurrencyCustody());
			
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	
	public String listConceptOtherFreight(){
		
		try {
			listConceptCostOtherFreightVO = new MoveCostDelegate().listConceptOtherFreight(idOtherFreight);
			
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	
	public String updateConceptOtherFreight() {
		
		try {
			String[] arrayImportUnitAttribute = conceptOtherFreight.split("-");

			new MoveCostDelegate().updateConceptOtherFreight(arrayImportUnitAttribute[0],
												  			 arrayImportUnitAttribute[1],
												  			 arrayImportUnitAttribute[2]);
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		
		return Action.SUCCESS;
	}
	
	
	public String updateConceptAPOFreight() {
		
		try {
			String[] arrayImportUnitAttribute = conceptOtherFreight.split("-");

			new MoveCostDelegate().updateConceptAPOFreight(arrayImportUnitAttribute[0],
												  			 arrayImportUnitAttribute[1],
												  			 arrayImportUnitAttribute[2]);
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		
		return Action.SUCCESS;
	}
	
	
	public String replicateConceptOtherFreight() {
		
		try {
			new MoveCostDelegate().replicateConceptOtherFreight(idConceptOtherFreight);

		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	public String replicateLocalDuty() {
		
		try {
			new MoveCostDelegate().replicateLocalDuty(idLocalDuty);

		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	public String deleteLocalDuty() {
		
		try {
			new MoveCostDelegate().deleteLocalDuty(idLocalDuty);
			
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	public String replicateExchangeCurrency() {
		
		try {
			new MoveCostDelegate().replicateExchangeCurrency(idExchangeCurrency);

		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	public String deleteExchangeCurrency() {
		
		try {
			new MoveCostDelegate().deleteExchangeCurrency(idExchangeCurrency);

		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	public String replicateOtherFreight() {
		
		try {
			new MoveCostDelegate().replicateOtherFreight(idOtherFreight);

		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	public String deleteOtherFreight() {
		
		try {
			new MoveCostDelegate().deleteOtherFreight(idOtherFreight);

		} catch (DAOException e) {			
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	
	public String listRateWarehouse() {
		
		try {
			listRateWarehouseVO = new MoveCostDelegate().listRateWarehouse((String) ServletActionContext.getRequest().getSession().getAttribute("idStage"));

		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	
	public String listRangeWarehouse() {
		
		try {
			listRangeWarehouseVO = new MoveCostDelegate().listRangesWarehouse((String) ServletActionContext.getRequest().getSession().getAttribute("idStage"));
			listCurrency	=  new MoveCostDelegate().listCurrencBySite(stageVO.getIdSiteStage());

		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	public String listConceptCostWarehouse() {
		
		try {
			listTempStorageVO = new MoveCostDelegate().getListTempStorage((String) ServletActionContext.getRequest().getSession().getAttribute("idStage"));
			listCurrency	=  new MoveCostDelegate().listCurrencBySite(stageVO.getIdSiteStage());
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	
	
	
	
	public String deleteConceptOtherFreight() {
		
		try {
			new MoveCostDelegate().deleteConceptOtherFreight(idConceptOtherFreight);

		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	
	public String listRoutesLocalTransport() {
		
		try {
			listRouteLocalTransportVO = new MoveCostDelegate().listRoutesLocalTransport((String) ServletActionContext.getRequest().getSession().getAttribute("idStage"));

		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	
	public String listRangesLocalTransport() {
		
		try {
			listLocalTransportRange = new MoveCostDelegate().getListLocalTransportRange((String) ServletActionContext.getRequest().getSession().getAttribute("idStage"));
			listCurrency	=  new MoveCostDelegate().listCurrencBySite(stageVO.getIdSiteStage());
			
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	public String listConceptCostLocalTransport() {
		
		try {
			listLocalTransportVO 	= new MoveCostDelegate().getListLocalTransport((String) ServletActionContext.getRequest().getSession().getAttribute("idStage"));
			listCurrency			= new MoveCostDelegate().listCurrencBySite(stageVO.getIdSiteStage());
			
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	public String replicateOperativeActivities() {
		
		try {
			new MoveCostDelegate().replicateOperativeActivities(idOperativeActivity);
			
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	
	public String deleteOperativeActivities() {
		
		try {
			new MoveCostDelegate().deleteOperativeActivities(idOperativeActivity);
			
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	public String replicateRateWarehouse() {
		
		try {
			new MoveCostDelegate().replicateRateWarehouse(idRateStorage);
			
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	public String deleteRateWarehouse() {
		
		try {
			new MoveCostDelegate().deleteRateWarehouse(idRateStorage);
			
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	public String replicateRangeWarehouse() {
		
		try {
			new MoveCostDelegate().replicateRangeWarehouse(idRangeStorage);
			
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	public String deleteRangeWarehouse() {
		
		try {
			new MoveCostDelegate().deleteRangeWarehouse(idRangeStorage);
			
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	public String listAPOFreights() {
		
		try {
			listActivePackOutFreightVO = new MoveCostDelegate().listAPOFreights((String) ServletActionContext.getRequest().getSession().getAttribute("idStage"));
			
			listCurrencyFreight = new MoveCostDelegate().listCurrencyFreight((String) ServletActionContext.getRequest().getSession().getAttribute("idStage"));
			
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	public String listConceptCostApoFreight() {
		
		try {
			listConceptCostAPOFreightVO = new MoveCostDelegate().listConceptCostApoFreight(idAPOFreight);
			
			
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	public String replicateConceptCostWarehouse() {
		
		try {
			new MoveCostDelegate().replicateConceptCostWarehouse(idConceptCostWarehouse);
			
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	public String replicateAPOFreightRate() {
		
		try {
			new MoveCostDelegate().replicateAPOFreightRate(idRateAPOFreight);
			
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	public String deleteAPOFreightRate() {
		
		try {
			new MoveCostDelegate().deleteAPOFreightRate(idRateAPOFreight);
			
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	public String deleteConceptCostWarehouse() {
		
		try {
			new MoveCostDelegate().deleteConceptCostWarehouse(idConceptCostWarehouse);
			
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	public String replicateRouteLocalTransport() {
		
		try {
			new MoveCostDelegate().replicateRouteLocalTransport(idRouteLocalTransport);
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	public String deleteRouteLocalTransport() {
		
		try {
			new MoveCostDelegate().deleteRouteLocalTransport(idRouteLocalTransport);
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	public String replicateRangeLocalTransport() {
		
		try {
			new MoveCostDelegate().replicateRangeLocalTransport(idRangeLocalTransport);
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	public String deleteRangeLocalTransport() {
		
		try {
			new MoveCostDelegate().deleteRangeLocalTransport(idRangeLocalTransport);
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	public String replicateConceptCostLocalTransport() {
		
		try {
			new MoveCostDelegate().replicateConceptCostLocalTransport(idConceptCostLocalTransport);
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	public String deleteConceptCostLocalTransport() {
		
		try {
			new MoveCostDelegate().deleteConceptCostLocalTransport(idConceptCostLocalTransport);
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	
	public String replicateAPOFreight() {
		
		try {
			new MoveCostDelegate().replicateAPOFreight(idAPOFreight);
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	
	public String deleteAPOFreight() {
		
		try {
			new MoveCostDelegate().deleteAPOFreight(idAPOFreight);
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	

	//Setter & Getter
	public StageVO getStageVO() {
		return stageVO;
	}
	public String getIdRangeStorage() {
		return idRangeStorage;
	}
	public void setIdRangeStorage(String idRangeStorage) {
		this.idRangeStorage = idRangeStorage;
	}
	public List<ItemMasterProductVO> getListItemMasterProductVO() {
		return listItemMasterProductVO;
	}
	public void setListItemMasterProductVO(
			List<ItemMasterProductVO> listItemMasterProductVO) {
		this.listItemMasterProductVO = listItemMasterProductVO;
	}
	public void setStageVO(StageVO stageVO) {
		this.stageVO = stageVO;
	}
	public String getStatusMessageError() {
		return statusMessageError;
	}
	public void setStatusMessageError(String statusMessageError) {
		this.statusMessageError = statusMessageError;
	}
	public List<GeneralComboVO> getListProductFamilies() {
		return listProductFamilies;
	}
	public void setListProductFamilies(List<GeneralComboVO> listProductFamilies) {
		this.listProductFamilies = listProductFamilies;
	}
	public List<GeneralComboVO> getListProductTransports() {
		return listProductTransports;
	}
	public void setListProductTransports(List<GeneralComboVO> listProductTransports) {
		this.listProductTransports = listProductTransports;
	}
	public List<GeneralComboVO> getListProductUnitMeasure() {
		return listProductUnitMeasure;
	}
	public void setListProductUnitMeasure(
			List<GeneralComboVO> listProductUnitMeasure) {
		this.listProductUnitMeasure = listProductUnitMeasure;
	}
	public List<GeneralComboVO> getListSiteSource() {
		return listSiteSource;
	}
	public void setListSiteSource(List<GeneralComboVO> listSiteSource) {
		this.listSiteSource = listSiteSource;
	}
	public List<GeneralComboVO> getListProductTypes() {
		return listProductTypes;
	}
	public void setListProductTypes(List<GeneralComboVO> listProductTypes) {
		this.listProductTypes = listProductTypes;
	}
	public String getItemMasterAttribute() {
		return itemMasterAttribute;
	}
	public void setItemMasterAttribute(String itemMasterAttribute) {
		this.itemMasterAttribute = itemMasterAttribute;
	}
	public List<FreightAirVO> getListFreightAirVO() {
		return listFreightAirVO;
	}
	public void setListFreightAirVO(List<FreightAirVO> listFreightAirVO) {
		this.listFreightAirVO = listFreightAirVO;
	}
//	public List<GeneralComboVO> getListSiteSources() {
//		return listSiteSources;
//	}
//	public void setListSiteSources(List<GeneralComboVO> listSiteSources) {
//		this.listSiteSources = listSiteSources;
//	}
	public FreightOthersVO getFreightOthersVO() {
		return freightOthersVO;
	}
	public void setFreightOthersVO(FreightOthersVO freightOthersVO) {
		this.freightOthersVO = freightOthersVO;
	}
	public List<GeneralComboVO> getListProductTrade() {
		return listProductTrade;
	}
	public void setListProductTrade(List<GeneralComboVO> listProductTrade) {
		this.listProductTrade = listProductTrade;
	}
	public List<GeneralComboVO> getListProductPresentation() {
		return listProductPresentation;
	}
	public void setListProductPresentation(
			List<GeneralComboVO> listProductPresentation) {
		this.listProductPresentation = listProductPresentation;
	}
	public List<GeneralComboVO> getListProductTypeLoad() {
		return listProductTypeLoad;
	}
	public void setListProductTypeLoad(List<GeneralComboVO> listProductTypeLoad) {
		this.listProductTypeLoad = listProductTypeLoad;
	}
	public List<SetupExchangeVO> getListSetupExchangeVO() {
		return listSetupExchangeVO;
	}
	public void setListSetupExchangeVO(List<SetupExchangeVO> listSetupExchangeVO) {
		this.listSetupExchangeVO = listSetupExchangeVO;
	}
	public List<SetupMacroValVO> getListSetupMacroValVO() {
		return listSetupMacroValVO;
	}
	public void setListSetupMacroValVO(List<SetupMacroValVO> listSetupMacroValVO) {
		this.listSetupMacroValVO = listSetupMacroValVO;
	}
	public SetupCustomDutiesHeadVO getSetupCustomDutiesHeadVO() {
		return setupCustomDutiesHeadVO;
	}
	public void setSetupCustomDutiesHeadVO(
			SetupCustomDutiesHeadVO setupCustomDutiesHeadVO) {
		this.setupCustomDutiesHeadVO = setupCustomDutiesHeadVO;
	}
	public List<SetupOpConsolidationVO> getListSetupOpConsolidationVO() {
		return listSetupOpConsolidationVO;
	}
	public void setListSetupOpConsolidationVO(
			List<SetupOpConsolidationVO> listSetupOpConsolidationVO) {
		this.listSetupOpConsolidationVO = listSetupOpConsolidationVO;
	}
//	public List<GeneralComboVO> getListFreightModes() {
//		return listFreightModes;
//	}
//	public void setListFreightModes(List<GeneralComboVO> listFreightModes) {
//		this.listFreightModes = listFreightModes;
//	}

	public List<ImportUnitVO> getListImportUnitVO() {
		return listImportUnitVO;
	}

	public void setListImportUnitVO(List<ImportUnitVO> listImportUnitVO) {
		this.listImportUnitVO = listImportUnitVO;
	}
	public String getImportUnitAttribute() {
		return importUnitAttribute;
	}
	public void setImportUnitAttribute(String importUnitAttribute) {
		this.importUnitAttribute = importUnitAttribute;
	}
	public ImportUnitVO getImportUnitVO() {
		return importUnitVO;
	}
	public void setImportUnitVO(ImportUnitVO importUnitVO) {
		this.importUnitVO = importUnitVO;
	}
	public String getFreightOthersAttributes() {
		return freightOthersAttributes;
	}
	public void setFreightOthersAttributes(String freightOthersAttributes) {
		this.freightOthersAttributes = freightOthersAttributes;
	}
	public String getFreightFlyAttributes() {
		return freightFlyAttributes;
	}
	public void setFreightFlyAttributes(String freightFlyAttributes) {
		this.freightFlyAttributes = freightFlyAttributes;
	}
	public String getSetupMacrovarAttributes() {
		return setupMacrovarAttributes;
	}
	public void setSetupMacrovarAttributes(String setupMacrovarAttributes) {
		this.setupMacrovarAttributes = setupMacrovarAttributes;
	}
	public String getSetupExchangeAttributes() {
		return setupExchangeAttributes;
	}
	public void setSetupExchangeAttributes(String setupExchangeAttributes) {
		this.setupExchangeAttributes = setupExchangeAttributes;
	}
	public String getSetupCustomDuties() {
		return setupCustomDuties;
	}
	public void setSetupCustomDuties(String setupCustomDuties) {
		this.setupCustomDuties = setupCustomDuties;
	}
	public List<OpeManageVO> getListOpeManageVO() {
		return listOpeManageVO;
	}
	public void setListOpeManageVO(List<OpeManageVO> listOpeManageVO) {
		this.listOpeManageVO = listOpeManageVO;
	}
	public String getSetupOpeManage() {
		return setupOpeManage;
	}
	public void setSetupOpeManage(String setupOpeManage) {
		this.setupOpeManage = setupOpeManage;
	}
	public List<GeneralComboVO> getListCurrency() {
		return listCurrency;
	}
	public void setListCurrency(List<GeneralComboVO> listCurrency) {
		this.listCurrency = listCurrency;
	}
	public String getSocOrders() {
		return socOrders;
	}
	public void setSocOrders(String socOrders) {
		this.socOrders = socOrders;
	}
	public List<TempStorageVO> getListTempStorageVO() {
		return listTempStorageVO;
	}
	public void setListTempStorageVO(List<TempStorageVO> listTempStorageVO) {
		this.listTempStorageVO = listTempStorageVO;
	}
	public String getSetupTempStorage() {
		return setupTempStorage;
	}
	public void setSetupTempStorage(String setupTempStorage) {
		this.setupTempStorage = setupTempStorage;
	}
	public List<GeneralComboVO> getListTempStorageRange() {
		return listTempStorageRange;
	}
	public void setListTempStorageRange(List<GeneralComboVO> listTempStorageRange) {
		this.listTempStorageRange = listTempStorageRange;
	}
	public RangeGenericVO getRangeGenericVO() {
		return rangeGenericVO;
	}
	public void setRangeGenericVO(RangeGenericVO rangeGenericVO) {
		this.rangeGenericVO = rangeGenericVO;
	}
	public List<LocalTransportVO> getListLocalTransportVO() {
		return listLocalTransportVO;
	}
	public void setListLocalTransportVO(List<LocalTransportVO> listLocalTransportVO) {
		this.listLocalTransportVO = listLocalTransportVO;
	}
	public List<RangeLocalTransportVO> getListLocalTransportRange() {
		return listLocalTransportRange;
	}
	public void setListLocalTransportRange(
			List<RangeLocalTransportVO> listLocalTransportRange) {
		this.listLocalTransportRange = listLocalTransportRange;
	}
	public String getLocaTransportStorage() {
		return locaTransportStorage;
	}
	public void setLocaTransportStorage(String locaTransportStorage) {
		this.locaTransportStorage = locaTransportStorage;
	}
	public FiltrosVO getFiltrosVO() {
		return filtrosVO;
	}
	public void setFiltrosVO(FiltrosVO filtrosVO) {
		this.filtrosVO = filtrosVO;
	}
	public List<GeneralComboVO> getListCustomDuty() {
		return listCustomDuty;
	}
	public void setListCustomDuty(List<GeneralComboVO> listCustomDuty) {
		this.listCustomDuty = listCustomDuty;
	}
	public List<GeneralComboVO> getListLocalRoute() {
		return listLocalRoute;
	}
	public void setListLocalRoute(List<GeneralComboVO> listLocalRoute) {
		this.listLocalRoute = listLocalRoute;
	}
	public List<GeneralComboVO> getListRateStorage() {
		return listRateStorage;
	}
	public void setListRateStorage(List<GeneralComboVO> listRateStorage) {
		this.listRateStorage = listRateStorage;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public CustodyVO getCustodyVO() {
		return custodyVO;
	}
	public void setCustodyVO(CustodyVO custodyVO) {
		this.custodyVO = custodyVO;
	}
	public List<GeneralComboVO> getListCurrencyFreight() {
		return listCurrencyFreight;
	}
	public void setListCurrencyFreight(List<GeneralComboVO> listCurrencyFreight) {
		this.listCurrencyFreight = listCurrencyFreight;
	}
	public List<OtherFreightVO> getListOtherFreightVO() {
		return listOtherFreightVO;
	}
	public void setListOtherFreightVO(List<OtherFreightVO> listOtherFreightVO) {
		this.listOtherFreightVO = listOtherFreightVO;
	}
	public List<GeneralComboVO> getListOtherTransports() {
		return listOtherTransports;
	}
	public void setListOtherTransports(List<GeneralComboVO> listOtherTransports) {
		this.listOtherTransports = listOtherTransports;
	}
	public String getIdFreightAir() {
		return idFreightAir;
	}
	public void setIdFreightAir(String idFreightAir) {
		this.idFreightAir = idFreightAir;
	}
	public List<FreighAirRangeVO> getListFreighAirRangeVO() {
		return listFreighAirRangeVO;
	}
	public void setListFreighAirRangeVO(List<FreighAirRangeVO> listFreighAirRangeVO) {
		this.listFreighAirRangeVO = listFreighAirRangeVO;
	}
	public String getIdRangeFreighAir() {
		return idRangeFreighAir;
	}
	public void setIdRangeFreighAir(String idRangeFreighAir) {
		this.idRangeFreighAir = idRangeFreighAir;
	}
	public String getIdOtherFreight() {
		return idOtherFreight;
	}
	public void setIdOtherFreight(String idOtherFreight) {
		this.idOtherFreight = idOtherFreight;
	}
	public List<ConceptCostOtherFreightVO> getListConceptCostOtherFreightVO() {
		return listConceptCostOtherFreightVO;
	}
	public void setListConceptCostOtherFreightVO(
			List<ConceptCostOtherFreightVO> listConceptCostOtherFreightVO) {
		this.listConceptCostOtherFreightVO = listConceptCostOtherFreightVO;
	}
	public String getConceptOtherFreight() {
		return conceptOtherFreight;
	}
	public void setConceptOtherFreight(String conceptOtherFreight) {
		this.conceptOtherFreight = conceptOtherFreight;
	}
	public String getIdConceptOtherFreight() {
		return idConceptOtherFreight;
	}
	public void setIdConceptOtherFreight(String idConceptOtherFreight) {
		this.idConceptOtherFreight = idConceptOtherFreight;
	}
	public List<RateWarehouseVO> getListRateWarehouseVO() {
		return listRateWarehouseVO;
	}
	public void setListRateWarehouseVO(List<RateWarehouseVO> listRateWarehouseVO) {
		this.listRateWarehouseVO = listRateWarehouseVO;
	}
	public List<RangeWarehouseVO> getListRangeWarehouseVO() {
		return listRangeWarehouseVO;
	}
	public void setListRangeWarehouseVO(List<RangeWarehouseVO> listRangeWarehouseVO) {
		this.listRangeWarehouseVO = listRangeWarehouseVO;
	}
	public List<RouteLocalTransportVO> getListRouteLocalTransportVO() {
		return listRouteLocalTransportVO;
	}
	public void setListRouteLocalTransportVO(
			List<RouteLocalTransportVO> listRouteLocalTransportVO) {
		this.listRouteLocalTransportVO = listRouteLocalTransportVO;
	}
	public String getIdOperativeActivity() {
		return idOperativeActivity;
	}
	public void setIdOperativeActivity(String idOperativeActivity) {
		this.idOperativeActivity = idOperativeActivity;
	}
	public String getIdRateStorage() {
		return idRateStorage;
	}
	public void setIdRateStorage(String idRateStorage) {
		this.idRateStorage = idRateStorage;
	}
	public List<ActivePackOutFreightVO> getListActivePackOutFreightVO() {
		return listActivePackOutFreightVO;
	}
	public void setListActivePackOutFreightVO(
			List<ActivePackOutFreightVO> listActivePackOutFreightVO) {
		this.listActivePackOutFreightVO = listActivePackOutFreightVO;
	}
	public List<ConceptCostAPOFreightVO> getListConceptCostAPOFreightVO() {
		return listConceptCostAPOFreightVO;
	}
	public void setListConceptCostAPOFreightVO(
			List<ConceptCostAPOFreightVO> listConceptCostAPOFreightVO) {
		this.listConceptCostAPOFreightVO = listConceptCostAPOFreightVO;
	}
	public String getIdAPOFreight() {
		return idAPOFreight;
	}
	public void setIdAPOFreight(String idAPOFreight) {
		this.idAPOFreight = idAPOFreight;
	}
	public String getIdConceptCostWarehouse() {
		return idConceptCostWarehouse;
	}
	public void setIdConceptCostWarehouse(String idConceptCostWarehouse) {
		this.idConceptCostWarehouse = idConceptCostWarehouse;
	}
	public String getIdRouteLocalTransport() {
		return idRouteLocalTransport;
	}
	public void setIdRouteLocalTransport(String idRouteLocalTransport) {
		this.idRouteLocalTransport = idRouteLocalTransport;
	}
	public String getIdRangeLocalTransport() {
		return idRangeLocalTransport;
	}
	public void setIdRangeLocalTransport(String idRangeLocalTransport) {
		this.idRangeLocalTransport = idRangeLocalTransport;
	}
	public String getIdConceptCostLocalTransport() {
		return idConceptCostLocalTransport;
	}
	public void setIdConceptCostLocalTransport(String idConceptCostLocalTransport) {
		this.idConceptCostLocalTransport = idConceptCostLocalTransport;
	}
	public String getIdRateAPOFreight() {
		return idRateAPOFreight;
	}
	public void setIdRateAPOFreight(String idRateAPOFreight) {
		this.idRateAPOFreight = idRateAPOFreight;
	}
	public String getIdExchangeCurrency() {
		return idExchangeCurrency;
	}
	public void setIdExchangeCurrency(String idExchangeCurrency) {
		this.idExchangeCurrency = idExchangeCurrency;
	}
	public String getIdLocalDuty() {
		return idLocalDuty;
	}
	public void setIdLocalDuty(String idLocalDuty) {
		this.idLocalDuty = idLocalDuty;
	}
	
}	