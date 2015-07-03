package cl.cstit.msd.ccs.delegate;


import java.util.List;

import cl.cstit.msd.ccs.dao.ResultCostDAOImp;
import cl.cstit.msd.ccs.exception.DAOException;
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

public class ResultCostDelegate{


	public List<MonthlyDataVO> initMonthlyData(String idStageA, String idStageB, String filtersProduct) throws DAOException{
		return new ResultCostDAOImp().initMonthlyData(idStageA, idStageB, filtersProduct);
	}

	public List<SummaryVO> initSummary(String idStageA, String idStageB, String filtersProduct) throws DAOException{
		return new ResultCostDAOImp().initSummary(idStageA, idStageB, filtersProduct);
	}
	
	public List<ProductivityVO> initProductivity(String idStageA, String idStageB) throws DAOException{
		return new ResultCostDAOImp().initProductivity(idStageA, idStageB);
	}
	
	
	public List<PagoCobranzaVO> listStagesFilter(String factura) throws DAOException{
		new ResultCostDAOImp().listStagesFilter(factura);
		return null;
	}
	
//	public List<PagoCobranzaVO> listStagesFilter(String factura) throws DAOException{
//		return new ResultCostDAOImp().listStagesFilter(factura);
//	}
	
	
//	public List<StageVO> listStagesFilter(String idStage) throws DAOException{
//		return new ResultCostDAOImp().listStagesFilter(idStage);
//	}
	
	public String getIdStagePlan(String idStageBase) throws DAOException{
		return new ResultCostDAOImp().getIdStagePlan(idStageBase);
	}
	
	public String getIdStateStage(String idStage) throws DAOException{
		return new ResultCostDAOImp().getIdStateStage(idStage);
	}
	
	public String getListProductFilter(FilterProductStageVO filterProductStageVO, String idStage) throws DAOException{
		return new ResultCostDAOImp().getListProductFilter(filterProductStageVO, idStage);
	}
	
	public List<GeneralComboVO> listDutiesStage(String idStage) throws DAOException{
		return new ResultCostDAOImp().listDutiesStage(idStage);
	}
	
	public void updateStageApproveForecast(String idStage) throws DAOException{
		new ResultCostDAOImp().updateStageApproveForecast(idStage);
	}
	
	public void updateStageApproveBudget(String idStage) throws DAOException{
		new ResultCostDAOImp().updateStageApproveBudget(idStage);
	}
	
	public void updateStageForecast(String idStage) throws DAOException{
		new ResultCostDAOImp().updateStageForecast(idStage);
	}
	
	public void updateStageBudget(String idStage) throws DAOException{
		new ResultCostDAOImp().updateStageBudget(idStage);
	}
	
	public List<StandardDetailVO> listStandarDetailStage(String idStageA, String idStageB, String filtersProduct) throws DAOException{
		return new ResultCostDAOImp().listStandarDetailStage(idStageA, idStageB, filtersProduct);
	}
	
	public List<StandardDetailVO> initImpactAnalisysStage(String idStageA, String idStageB, String filtersProduct) throws DAOException{
		return new ResultCostDAOImp().initImpactAnalisysStage(idStageA, idStageB, filtersProduct);
	}
	
	public void updateStageToApproveForecast(String idStage) throws DAOException{
		new ResultCostDAOImp().updateStageToApproveForecast(idStage);
	}
	
	public void updateStageToApproveBudget(String idStage) throws DAOException{
		new ResultCostDAOImp().updateStageToApproveBudget(idStage);
	}
	
	public void insertCommentStage(String idStage, String isidUser, String comment) throws DAOException{
		new ResultCostDAOImp().insertCommentStage(idStage, isidUser, comment);
	}
	
	public List<CommentStageVO> listCommentStage(String idStage) throws DAOException{
		return new ResultCostDAOImp().listCommentStage(idStage);
	}
	
	public List<UserVO> listApproverUsersEmails(String idStage) throws DAOException{
		return new ResultCostDAOImp().listApproverUsersEmails(idStage);
	}
	
	public void updateStageRejectForecast(String idStage) throws DAOException{
		new ResultCostDAOImp().updateStageRejectForecast(idStage);
	}
	
	public void updateStageRejectBudget(String idStage) throws DAOException{
		new ResultCostDAOImp().updateStageRejectBudget(idStage);
	}
	
	public String getNameStateStage(String idStage) throws DAOException{
		return new ResultCostDAOImp().getNameStateStage(idStage);
	}
	public List<UserVO> listNormalUsersEmails(String idStage) throws DAOException{
		return new ResultCostDAOImp().listNormalUsersEmails(idStage);
	}
	public List<UnitCostProductVO> initUnitCostProduct(String idStage) throws DAOException{
		 return new ResultCostDAOImp().initUnitCostProduct(idStage);
	}
}