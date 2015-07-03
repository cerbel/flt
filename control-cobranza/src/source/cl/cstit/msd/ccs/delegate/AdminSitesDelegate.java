package cl.cstit.msd.ccs.delegate;

import java.util.List;

import cl.cstit.msd.ccs.dao.AdminSitesDAOImp;
import cl.cstit.msd.ccs.exception.DAOException;
import cl.cstit.msd.ccs.vo.RowCountVO;
import cl.cstit.msd.ccs.vo.SiteVO;

public class AdminSitesDelegate{
 

	
	public List<SiteVO> listSites(RowCountVO rowCountVO, int numberPage) throws DAOException{
		
		return new AdminSitesDAOImp().listSites(rowCountVO, numberPage);
	}
	
	public SiteVO getSite(String idSite) throws DAOException{
	
		return new AdminSitesDAOImp().getSite(idSite);
	}

	public void updateUser(SiteVO siteVO) throws DAOException{
		new AdminSitesDAOImp().updateUser(siteVO);
	}
	
    public void saveSite(SiteVO siteVO) throws DAOException{
    	new AdminSitesDAOImp().saveSite(siteVO);	
    }
}