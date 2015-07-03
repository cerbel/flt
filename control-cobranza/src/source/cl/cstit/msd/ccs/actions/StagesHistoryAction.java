package cl.cstit.msd.ccs.actions;

import java.io.IOException;
//import java.util.ArrayList;
import java.util.List;

import javax.servlet.Servlet;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.util.logging.Logger;

import cl.cstit.msd.ccs.delegate.MoveCostDelegate;
import cl.cstit.msd.ccs.delegate.StagesHistoryDelegate;
import cl.cstit.msd.ccs.exception.DAOException;
import cl.cstit.msd.ccs.vo.FiltroPagoCobranzaVO;
import cl.cstit.msd.ccs.vo.PagoCobranzaVO;
import cl.cstit.msd.ccs.vo.StageVO;
import cl.cstit.msd.ccs.vo.UserVO;

public class StagesHistoryAction{
 
 
	private List<PagoCobranzaVO> listPagoVO;
	
	private String statusMessageError = new String();
	
	private StageVO stageVO;
	
	private PagoCobranzaVO idEstadoFacturaVO;

	private FiltroPagoCobranzaVO filtroPagoCobranzaVO;
	
	private static Logger logger = null;
	
	public String initStages() {
		
		return "SUCCESS";	
	}
	
	
	
	//-------------------------
	
	
	public String cambiaEstado() {
		
		try {
			
			
			idEstadoFacturaVO = new StagesHistoryDelegate().cambiaEstado(idEstadoFacturaVO);
		
		} catch (DAOException e) {
			
//			statusMessageError = e.getMessage();
		}	
		return Action.SUCCESS;
	}
	
	//------------------------
	




	public PagoCobranzaVO getIdEstadoFacturaVO() {
		return idEstadoFacturaVO;
	}



	public void setIdEstadoFacturaVO(PagoCobranzaVO idEstadoFacturaVO) {
		this.idEstadoFacturaVO = idEstadoFacturaVO;
	}



	public String listStages() {
		
		try {
						
//			UserVO userSessionVO = (UserVO)ServletActionContext.getRequest().getSession().getAttribute("userSessionVO");
//			
//			List<SiteVO> listSiteVO = null;
//			if (stageVO.getIdSiteStage().equals(""))
//				listSiteVO = userSessionVO.getListSiteVO();
//			else{
//				listSiteVO = new ArrayList<SiteVO>();
//				listSiteVO.add(new SiteVO(stageVO.getIdSiteStage()));
//			}	
//			listStagesVO = new StagesHistoryDelegate().listStages(listSiteVO, stageVO.getIdTypeStage(), userSessionVO.getIdProfileUser());
//			UserVO userSessionVO = (UserVO)ServletActionContext.getRequest().getSession().getAttribute("userSessionVO");
			
//			pagoCobranzaVO= (PagoCobranzaVO) ServletActionContext.getRequest().getSession().getAttribute("pagoCobranzaVO");
//			logger.debug("NOMBRE "+pagoCobranzaVO.getNombre());
			
			listPagoVO     = new StagesHistoryDelegate().listPagoCobranza(filtroPagoCobranzaVO);
//			listPagoVO     = new StagesHistoryDelegate().listPagoCobranza(factura, estado);
			
		} catch (DAOException e) {
			
			statusMessageError = e.getMessage();
		}
		
		return Action.SUCCESS;
	}
	




	public FiltroPagoCobranzaVO getFiltroPagoCobranzaVO() {
		return filtroPagoCobranzaVO;
	}


	public void setFiltroPagoCobranzaVO(FiltroPagoCobranzaVO filtroPagoCobranzaVO) {
		this.filtroPagoCobranzaVO = filtroPagoCobranzaVO;
	}


	public String deleteStage() throws IOException {
		
		try {
			
			new StagesHistoryDelegate().deleteStage(stageVO.getIdStage());
		} catch (DAOException e) {
			
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	
	public String changeStageHistory() throws IOException {
		
		ServletActionContext.getRequest().getSession().setAttribute("idStage", stageVO.getIdStage());
		

		ServletActionContext.getResponse().sendRedirect("changeStage.action");
		
		return null;	
	}
	
	
	public String viewStageHistory() throws IOException {
		
		
//		ServletActionContext.getRequest().getSession().setAttribute("factura",idEstadoFactura.getFactura());//aki
		
//		ServletActionContext.getRequest().getSession().setAttribute("idStage", stageVO.getIdStage());
//		ServletActionContext.getResponse().sendRedirect("resultCost.action");
//		return null;

		ServletActionContext.getResponse().sendRedirect("resultCost.action");
		return null;
		
	}
	
	
//	public PagoCobranzaVO getIdEstadoFactura() {
//		return idEstadoFactura;
//	}
//	public void setIdEstadoFactura(PagoCobranzaVO idEstadoFactura) {
//		this.idEstadoFactura = idEstadoFactura;
//	}

	public List<PagoCobranzaVO> getListStagesVO() {
		return listPagoVO;
	}
	public void setListStagesVO(List<PagoCobranzaVO> listStagesVO) {
		this.listPagoVO = listStagesVO;
	}
	public String getStatusMessageError() {
		return statusMessageError;
	}
	public void setStatusMessageError(String statusMessageError) {
		this.statusMessageError = statusMessageError;
	}
	public StageVO getStageVO() {
		return stageVO;
	}
	public void setStageVO(StageVO stageVO) {
		this.stageVO = stageVO;
	}
	public List<PagoCobranzaVO> getListPagoVO() {
		return listPagoVO;
	}
	public void setListPagoVO(List<PagoCobranzaVO> listPagoVO) {
		this.listPagoVO = listPagoVO;
	}
}