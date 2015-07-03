package cl.cstit.msd.ccs.delegate;

import java.util.List;

import cl.cstit.msd.ccs.dao.GeneralTransactionsDAOImp;
import cl.cstit.msd.ccs.exception.DAOException;
import cl.cstit.msd.ccs.vo.GeneralComboVO;

public class GeneralTransactionsDelegate{
 
	public List<GeneralComboVO> listProductFamilies() throws DAOException{
		return new GeneralTransactionsDAOImp().listProductFamilies();
	}
	
    public List<GeneralComboVO> listProductTransports() throws DAOException{
    	return new GeneralTransactionsDAOImp().listProductTransports();
    }
    
	public List<GeneralComboVO> listProductUnitMeasure() throws DAOException{
		return new GeneralTransactionsDAOImp().listProductUnitMeasure();
	}
	
	
	public List<GeneralComboVO> listSiteSource(String idStage) throws DAOException{
		return new GeneralTransactionsDAOImp().listSiteSource(idStage);
	}


	public List<GeneralComboVO> listTypeStage() throws DAOException{
		return new GeneralTransactionsDAOImp().listTypeStage();	
	}
	
	
	public List<GeneralComboVO> listProductTypes() throws DAOException{
		return new GeneralTransactionsDAOImp().listProductTypes();
	}
	
	public List<GeneralComboVO> listProductTrade() throws DAOException{
		return new GeneralTransactionsDAOImp().listProductTrade();
	}
	
	public List<GeneralComboVO> listProductPresentation() throws DAOException{
		return new GeneralTransactionsDAOImp().listProductPresentation();
	}
	
	public List<GeneralComboVO> listProductTypeLoad() throws DAOException{
		return new GeneralTransactionsDAOImp().listProductTypeLoad();
	}
	
	public List<GeneralComboVO> listCurrency() throws DAOException{
		return new GeneralTransactionsDAOImp().listCurrency();
	}
	
//	public void deleteStageTrash() throws DAOException{
//		new GeneralTransactionsDAOImp().deleteStageTrash();
//	}
}