package cl.cstit.msd.ccs.delegate;

import java.util.List;

import cl.cstit.msd.ccs.dao.StagesHistoryDAOImp;
import cl.cstit.msd.ccs.exception.DAOException;
import cl.cstit.msd.ccs.vo.FiltroPagoCobranzaVO;
import cl.cstit.msd.ccs.vo.PagoCobranzaVO;
import cl.cstit.msd.ccs.vo.SiteVO;
import cl.cstit.msd.ccs.vo.StageVO;

public class StagesHistoryDelegate{

	public PagoCobranzaVO cambiaEstado(PagoCobranzaVO idEstadoFactura) throws DAOException{
		return new StagesHistoryDAOImp().cambiaEstado(idEstadoFactura);
	}
	
	public List<PagoCobranzaVO> listPagoCobranza(FiltroPagoCobranzaVO filtroPagoCobranzaVO) throws DAOException{
		return new StagesHistoryDAOImp().listPagoCobranza(filtroPagoCobranzaVO); 
	}
	
//	public List<PagoCobranzaVO> listStages(String factura, String estado) throws DAOException{
//		return new StagesHistoryDAOImp().listStages(factura, estado); 
//	}
	
//	public List<StageVO> listStages(List<SiteVO> listSiteVO, String idTypeStage, String idProfileUser) throws DAOException{
//		return new StagesHistoryDAOImp().listStages(listSiteVO, idTypeStage, idProfileUser); 
//	}

	public void deleteStage(String idStage) throws DAOException{
		new StagesHistoryDAOImp().deleteStage(idStage);
	}
}