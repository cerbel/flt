package cl.cstit.msd.ccs.delegate;

import java.util.List;

import cl.cstit.msd.ccs.dao.AutenticationUserDAOImp;
import cl.cstit.msd.ccs.exception.DAOException;
import cl.cstit.msd.ccs.vo.HomeCompareFamilyVO;
import cl.cstit.msd.ccs.vo.HomeCompareVO;
import cl.cstit.msd.ccs.vo.SiteVO;
import cl.cstit.msd.ccs.vo.StandardDetailVO;
import cl.cstit.msd.ccs.vo.SummaryVO;
import cl.cstit.msd.ccs.vo.UserVO;

public class AutenticationUserDelegate{
 
	AutenticationUserDAOImp autenticationUserDAOImp = new AutenticationUserDAOImp();
	
    public UserVO autenticateUser(String isid) throws DAOException{
    	return autenticationUserDAOImp.autenticateUser(isid);
    }
    
    public HomeCompareVO homeCompareStagesChart(List<SiteVO> listSiteVO) throws DAOException{
    	return autenticationUserDAOImp.homeCompareStagesChart(listSiteVO);
    }
    
    public List<HomeCompareFamilyVO> homeCompareStagesFamily(List<SiteVO> listSiteVO) throws DAOException{
    	return autenticationUserDAOImp.homeCompareStagesFamily(listSiteVO);
    }
    
	public List<StandardDetailVO> listStandarDetailStage(List<SiteVO> listSiteVO) throws DAOException{
		return autenticationUserDAOImp.listStandarDetailStage(listSiteVO);
	}
	
	public List<SummaryVO> initSummary(List<SiteVO> listSiteVO) throws DAOException{
		return autenticationUserDAOImp.initSummary(listSiteVO);
	}

}