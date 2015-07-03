package cl.cstit.msd.ccs.delegate;


import java.util.List;

import cl.cstit.msd.ccs.dao.MoveCostDAOImp;
import cl.cstit.msd.ccs.exception.DAOException;
import cl.cstit.msd.ccs.vo.ActivePackOutFreightVO;
import cl.cstit.msd.ccs.vo.ConceptCostAPOFreightVO;
import cl.cstit.msd.ccs.vo.ConceptCostOtherFreightVO;
import cl.cstit.msd.ccs.vo.CustodyVO;
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

public class MoveCostDelegate{

    public String initSimulation(StageVO stageVO) throws DAOException{
    	return new MoveCostDAOImp().initSimulation(stageVO);
    }
   
	public List<ItemMasterProductVO> initItemMaster(String idStage, String idSite) throws DAOException{
		return new MoveCostDAOImp().initItemMaster(idStage, idSite);
	}
   
	public void simulateStage(StageVO stageVO) throws DAOException{
		new MoveCostDAOImp().simulateStage(stageVO);
	}
	
	public void itemMasterUpdateAttributes(String idStage, String idProduct, String valueAttribute, String attributeToUpdate) throws DAOException{
		new MoveCostDAOImp().itemMasterUpdateAttributes(idStage, idProduct, valueAttribute, attributeToUpdate);
	}
	
	public List<FreightAirVO> initFreightAir(String idStage, String idSite) throws DAOException{
		return new MoveCostDAOImp().initFreightAir(idStage, idSite);
	}
	
	public List<GeneralComboVO> listSiteSources(String idStage) throws DAOException{
		return new MoveCostDAOImp().listSiteSources(idStage);
	}
	
//	public FreightOthersVO getFreightOthers(String idSiteSource, String idStage, String idModeTransport) throws DAOException{
//		return new MoveCostDAOImp().getFreightOthers(idSiteSource, idStage, idModeTransport);
//	}
	
	public List<SetupExchangeVO> getSetupExchangeVO(String idStage) throws DAOException{
		return new MoveCostDAOImp().getSetupExchangeVO(idStage);
	}
	
	public List<SetupMacroValVO> getSetupMacroValVO(String idStage) throws DAOException{
		return new MoveCostDAOImp().getSetupMacroValVO(idStage);
	}
	
	public SetupCustomDutiesHeadVO initCustomDuties(String idStage) throws DAOException{
		return new MoveCostDAOImp().initCustomDuties(idStage);
	}
	
	public SetupOpConsolidationVO getSetupOperativeConsolidation(String idStage) throws DAOException{
		return new MoveCostDAOImp().getSetupOperativeConsolidation(idStage);
	}
	
	public List<ImportUnitVO> getListImporUnits(String stgId) throws DAOException{
		return new MoveCostDAOImp().getListImporUnits(stgId);
	}

	public ImportUnitVO importUnitUpdateAttributes(String idStage, String idProduct, String valueAttribute, String attributeToUpdate) throws DAOException{
		return new MoveCostDAOImp().importUnitUpdateAttributes(idStage, idProduct, valueAttribute, attributeToUpdate);
	}

	public void updateFreightOther(String attributeToUpdate, String valueAttribute, String idOtherFreight) throws DAOException{
		new MoveCostDAOImp().updateFreightOther(attributeToUpdate, valueAttribute, idOtherFreight);
	}
	
	public void updateFreightFly(String attributeToUpdate, String valueAttribute, String idStage, String siteSource, String idFreightAir) throws DAOException{
		new MoveCostDAOImp().updateFreightFly(attributeToUpdate, valueAttribute, idStage, siteSource, idFreightAir);
	}
	
	public StageVO getStage(String idStage) throws DAOException{
		return new MoveCostDAOImp().getStage(idStage);
	}
	public void updateFreightFlyRange(String attributeToUpdate, String valueAttribute, String idRangeFreight) throws DAOException{
		new MoveCostDAOImp().updateFreightFlyRange(attributeToUpdate, valueAttribute, idRangeFreight);
	}

	public void updateSetupExchange(String valueAttribute, String attributeToUpdate, String idSetupExchange) throws DAOException{
		new MoveCostDAOImp().updateSetupExchange(valueAttribute, attributeToUpdate, idSetupExchange);
	}
	
	public void updateSetupMacrovars(String valueAttribute, String idStage, String idSetupMacrovars) throws DAOException{
		new MoveCostDAOImp().updateSetupMacrovars(valueAttribute, idStage, idSetupMacrovars);
	}
	public void updateSetupCustomDutiesDetail(String attributeToUpdate, String valueAttribute, String idStage, String idCustomsDetail) throws DAOException{
		new MoveCostDAOImp().updateSetupCustomDutiesDetail(attributeToUpdate, valueAttribute, idStage, idCustomsDetail);
	}

	public void updateSetupCustomDutiesHead(String attributeToUpdate, String valueAttribute, String idStage) throws DAOException{
		new MoveCostDAOImp().updateSetupCustomDutiesHead(attributeToUpdate, valueAttribute, idStage);
	}
	
	public List<OpeManageVO> getListOpeManage(String stgId) throws DAOException{
		return new MoveCostDAOImp().getListOpeManage(stgId);
	}
	
	public void updateSetupOpeMange(String attributeToUpdate, String valueAttribute, String idStage, String somID) throws DAOException{
		new MoveCostDAOImp().updateSetupOpeMange(attributeToUpdate, valueAttribute, idStage, somID);
	}
	
	public void updateSetupOpeConsolidation(String attributeToUpdate, String valueAttribute, String idStage, String siteSourceID) throws DAOException{
		new MoveCostDAOImp().updateSetupOpeConsolidation(attributeToUpdate, valueAttribute, idStage, siteSourceID);
	}

	public String getOpeConsolidationSocOrders(String idSiteSource, String idStage, String idMode) throws DAOException{
		return new MoveCostDAOImp().getOpeConsolidationSocOrders(idSiteSource, idStage, idMode);
	}
	
	public List<TempStorageVO> getListTempStorage(String stgId) throws DAOException{
		return new MoveCostDAOImp().getListTempStorage(stgId);
	}
	
	public void updateSetupTempStorage(String attributeToUpdate, String valueAttribute, String idStage, String sstId) throws DAOException{
		new MoveCostDAOImp().updateSetupTempStorage(attributeToUpdate, valueAttribute, idStage, sstId);
	}
	
	public RangeGenericVO getTempStorageRange(String ssrId) throws DAOException{
		return new MoveCostDAOImp().getTempStorageRange(ssrId);
	}
	
	public void updateSetupTempStorageRange(String attributeToUpdate, String valueAttribute, String idStage, String ssrId) throws DAOException{
		new MoveCostDAOImp().updateSetupTempStorageRange(attributeToUpdate, valueAttribute, idStage, ssrId);
	}
	
	public List<LocalTransportVO> getListLocalTransport(String stgId) throws DAOException{
		return new MoveCostDAOImp().getListLocalTransport(stgId);
	}	
	
	public void updateSetupLocalTransport(String attributeToUpdate, String valueAttribute, String idStage, String sltId) throws DAOException{
		new MoveCostDAOImp().updateSetupLocalTransport(attributeToUpdate, valueAttribute, idStage, sltId);
	}
	
	public List<RangeLocalTransportVO> getListLocalTransportRange(String stgId) throws DAOException{
		return new MoveCostDAOImp().getListLocalTransportRange(stgId);
	}
	
	public RangeGenericVO getLocalTransportRange(String slrId) throws DAOException{
		return new MoveCostDAOImp().getLocalTransportRange(slrId);
	}
	
	public List<GeneralComboVO> getListLocalRoute(String idStage) throws DAOException{
		return new MoveCostDAOImp().getListLocalRoute(idStage);
	}
	
	public void updateSetupLocalTransportRange(String attributeToUpdate, String valueAttribute, String idStage, String slrId, String idCurrency) throws DAOException{
		new MoveCostDAOImp().updateSetupLocalTransportRange(attributeToUpdate, valueAttribute, idStage, slrId, idCurrency);
	}

	public void filtros(String idStage, String pfaID, String prpID, String ptyID, String ptlID, String ptrID, String sisID, String sddID, String filterProduct) throws DAOException{
		new MoveCostDAOImp().filtros(idStage, pfaID, prpID, ptyID, ptlID, ptrID, sisID, sddID, filterProduct);
	}
	
	public List<GeneralComboVO> getListCustomDuty(String stgId) throws DAOException{
		return new MoveCostDAOImp().getListCustomDuty(stgId);
	}
	
	public void deleteFilter(String idStage) throws DAOException{
		new MoveCostDAOImp().deleteFilter(idStage);
	}
	public void filtroPorUno(String idStage, String localeProduct) throws DAOException{
		new MoveCostDAOImp().filtroPorUno(idStage, localeProduct);
	}
	
	public void replicarProducto(String idStage, String localeProduct) throws DAOException{
		new MoveCostDAOImp().replicarProducto(idStage, localeProduct);
	}
	
	public void removerProducto(String idStage, String localeProduct) throws DAOException{
		new MoveCostDAOImp().removerProducto(idStage, localeProduct);
	}
	
	public List<GeneralComboVO> getListRateStorage(String idStage) throws DAOException{
		return new MoveCostDAOImp().getListRateStorage(idStage);
	}
	public void replicateFreight(String idSiteSource, String idStage) throws DAOException{
		new MoveCostDAOImp().replicateFreight(idSiteSource, idStage);
	}
	
	public void deleteFreightRoute(String idSiteSource, String idStage) throws DAOException{
		new MoveCostDAOImp().deleteFreightRoute(idSiteSource, idStage);
	}
	
	public List<GeneralComboVO> listCurrencBySite(String idSite) throws DAOException{
		return new MoveCostDAOImp().listCurrencBySite(idSite);
	}

	public CustodyVO getCustudySetup(String idStage) throws DAOException{
		return new MoveCostDAOImp().getCustudySetup(idStage);
	}
	
	public void updateBaseLineCustody(float baseLineCustody, String idStage) throws DAOException{
		new MoveCostDAOImp().updateBaseLineCustody(baseLineCustody, idStage);
	}
	
	public void updateFeeCostCustody(float feeCostCustody, String idStage, String idCurrency) throws DAOException{
		new MoveCostDAOImp().updateFeeCostCustody(feeCostCustody, idStage, idCurrency);
	}
	
	public List<GeneralComboVO> listCurrencyFreight(String idStage) throws DAOException{
		return new MoveCostDAOImp().listCurrencyFreight(idStage);
	}
	
	public List<OtherFreightVO> listOtherFreights(String idStage) throws DAOException{
		return new MoveCostDAOImp().listOtherFreights(idStage);
	}
	
	public List<GeneralComboVO> listOtherTransports() throws DAOException{
		return new MoveCostDAOImp().listOtherTransports();
	}
	
	public List<FreighAirRangeVO> listRangeFreightAir(String idFreightAir) throws DAOException{
		return new MoveCostDAOImp().listRangeFreightAir(idFreightAir);
	}
	
	public void replicateRangeFraightAir(String idRangeFreighAir) throws DAOException{
		new MoveCostDAOImp().replicateRangeFraightAir(idRangeFreighAir);
	}
	
	public void deleteRangeFraightAir(String idRangeFreighAir) throws DAOException{
		new MoveCostDAOImp().deleteRangeFraightAir(idRangeFreighAir);
	}
	
	public List<ConceptCostOtherFreightVO> listConceptOtherFreight(String idOtherFreight) throws DAOException{
		return new MoveCostDAOImp().listConceptOtherFreight(idOtherFreight);
	}
	
	public void updateConceptOtherFreight(String attributeToUpdate, String valueAttribute, String idConceptOtherFreigh) throws DAOException{
		new MoveCostDAOImp().updateConceptOtherFreight(attributeToUpdate, valueAttribute, idConceptOtherFreigh);
	}
	
	public void replicateConceptOtherFreight(String idRangeFreighAir) throws DAOException{
		new MoveCostDAOImp().replicateConceptOtherFreight(idRangeFreighAir);
	}
	
	public void deleteConceptOtherFreight(String idRangeFreighAir) throws DAOException{
		new MoveCostDAOImp().deleteConceptOtherFreight(idRangeFreighAir);
	}
	
	public void replicateOtherFreight(String idOtherFreight) throws DAOException{
		new MoveCostDAOImp().replicateOtherFreight(idOtherFreight);
	}
	
	public void deleteOtherFreight(String idOtherFreight) throws DAOException{
		new MoveCostDAOImp().deleteOtherFreight(idOtherFreight);
	}
	
	public List<RateWarehouseVO> listRateWarehouse(String idStage) throws DAOException{
		return new MoveCostDAOImp().listRateWarehouse(idStage);
	}
	
	public List<RangeWarehouseVO> listRangesWarehouse(String idStage) throws DAOException{
		return new MoveCostDAOImp().listRangesWarehouse(idStage);
	}
	
	public List<RouteLocalTransportVO> listRoutesLocalTransport(String idStage) throws DAOException{
		return new MoveCostDAOImp().listRoutesLocalTransport(idStage);
	}
	
	public void updateRatesWareHouse(String attributeToUpdate, String valueAttribute, String idRateWarehouse) throws DAOException{
		new MoveCostDAOImp().updateRatesWareHouse(attributeToUpdate, valueAttribute, idRateWarehouse);
	}
	
	public void updateRangesWareHouse(String attributeToUpdate, String valueAttribute, String idRangeWarehouse) throws DAOException{
		new MoveCostDAOImp().updateRangesWareHouse(attributeToUpdate, valueAttribute, idRangeWarehouse);
	}
	
	public void updateRoutesLocalTransport(String attributeToUpdate, String valueAttribute, String idRouteLocalTransport) throws DAOException{
		new MoveCostDAOImp().updateRoutesLocalTransport(attributeToUpdate, valueAttribute, idRouteLocalTransport);
	}

	public void updateRangesLocalTransport(String attributeToUpdate, String valueAttribute, String idRangeLocalTransport) throws DAOException{
		new MoveCostDAOImp().updateRangesLocalTransport(attributeToUpdate, valueAttribute, idRangeLocalTransport);
	}
	
	public void replicateOperativeActivities(String idOperativeActivity) throws DAOException{
		new MoveCostDAOImp().replicateOperativeActivities(idOperativeActivity);
	}
	
	public void deleteOperativeActivities(String idOperativeActivity) throws DAOException{
		new MoveCostDAOImp().deleteOperativeActivities(idOperativeActivity);
	}
	
	public void replicateRateWarehouse(String idRateStorage) throws DAOException{
		new MoveCostDAOImp().replicateRateWarehouse(idRateStorage);
	}
	
	public void deleteRateWarehouse(String idRateStorage) throws DAOException{
		new MoveCostDAOImp().deleteRateWarehouse(idRateStorage);
	}
	
	public void replicateRangeWarehouse(String idRangeStorage) throws DAOException{
		new MoveCostDAOImp().replicateRangeWarehouse(idRangeStorage);
	}
	
	public void deleteRangeWarehouse(String idRangeStorage) throws DAOException{
		new MoveCostDAOImp().deleteRangeWarehouse(idRangeStorage);
	}
	
	public List<ActivePackOutFreightVO> listAPOFreights(String idStage) throws DAOException{
		return new MoveCostDAOImp().listAPOFreights(idStage);
	}
	
	public List<ConceptCostAPOFreightVO> listConceptCostApoFreight(String idAPOFreight) throws DAOException{
		return new MoveCostDAOImp().listConceptCostApoFreight(idAPOFreight);
	}
	
	public void replicateConceptCostWarehouse(String idConceptCostWarehouse) throws DAOException{
		new MoveCostDAOImp().replicateConceptCostWarehouse(idConceptCostWarehouse);
	}
	
	public void deleteConceptCostWarehouse(String idConceptCostWarehouse) throws DAOException{
		new MoveCostDAOImp().deleteConceptCostWarehouse(idConceptCostWarehouse);
	}
	
	public void replicateRouteLocalTransport(String idRouteLocalTransport) throws DAOException{
		new MoveCostDAOImp().replicateRouteLocalTransport(idRouteLocalTransport);
	}
	
	public void deleteRouteLocalTransport(String idRouteLocalTransport) throws DAOException{
		new MoveCostDAOImp().deleteRouteLocalTransport(idRouteLocalTransport);
	}
	
	public void replicateRangeLocalTransport(String idRangeLocalTransport) throws DAOException{
		new MoveCostDAOImp().replicateRangeLocalTransport(idRangeLocalTransport);
	}
	
	public void deleteRangeLocalTransport(String idRangeLocalTransport) throws DAOException{
		new MoveCostDAOImp().deleteRangeLocalTransport(idRangeLocalTransport);
	}
	
	public void replicateConceptCostLocalTransport(String idConceptCostLocalTransport) throws DAOException{
		new MoveCostDAOImp().replicateConceptCostLocalTransport(idConceptCostLocalTransport);
	}
	
	public void deleteConceptCostLocalTransport(String idConceptCostLocalTransport) throws DAOException{
		new MoveCostDAOImp().deleteConceptCostLocalTransport(idConceptCostLocalTransport);
	}
	
	public void updateFreightAPO(String attributeToUpdate, String valueAttribute, String idApoFreight) throws DAOException{
		new MoveCostDAOImp().updateFreightAPO(attributeToUpdate, valueAttribute,  idApoFreight);
	}
	
	public void replicateAPOFreight(String idAPOFreight) throws DAOException{
		new MoveCostDAOImp().replicateAPOFreight(idAPOFreight);
	}
	
	public void deleteAPOFreight(String idAPOFreight) throws DAOException{
		new MoveCostDAOImp().deleteAPOFreight(idAPOFreight);
	}
	
	public void replicateAPOFreightRate(String idRateAPOFreight) throws DAOException{
		new MoveCostDAOImp().replicateAPOFreightRate(idRateAPOFreight);
	}
	
	public void deleteAPOFreightRate(String idRateAPOFreight) throws DAOException{
		new MoveCostDAOImp().deleteAPOFreightRate(idRateAPOFreight);
	}
	
	public void updateConceptAPOFreight(String attributeToUpdate, String valueAttribute, String idRateAPOFreight) throws DAOException{
		new MoveCostDAOImp().updateConceptAPOFreight(attributeToUpdate, valueAttribute, idRateAPOFreight);
	}
	
	public void replicateExchangeCurrency(String idExchangeCurrency) throws DAOException{
		new MoveCostDAOImp().replicateExchangeCurrency(idExchangeCurrency);
	}
	
	public void deleteExchangeCurrency(String idExchangeCurrency) throws DAOException{
		new MoveCostDAOImp().deleteExchangeCurrency(idExchangeCurrency);
	}
	
	public void replicateLocalDuty(String idLocalDuty) throws DAOException{
		new MoveCostDAOImp().replicateLocalDuty(idLocalDuty);
	}
	
	public void deleteLocalDuty(String idLocalDuty) throws DAOException{
		new MoveCostDAOImp().deleteLocalDuty(idLocalDuty);
	}
	
}
