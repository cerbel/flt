package cl.cstit.msd.ccs.actions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import cl.cstit.msd.ccs.delegate.GeneralTransactionsDelegate;
import cl.cstit.msd.ccs.delegate.ResultCostDelegate;
import cl.cstit.msd.ccs.exception.DAOException;
import cl.cstit.msd.ccs.utils.ReportExportExcel;
import cl.cstit.msd.ccs.utils.SendEmailNotification;
import cl.cstit.msd.ccs.utils.UtilExcel;
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

import com.opensymphony.xwork2.Action;

public class ResultCostAction{
	
	private static Logger logger = Logger.getLogger(ResultCostAction.class);
	
	private String statusMessageError = new String();
	
	private List<MonthlyDataVO>    listMonthlyDataVO;
	private List<SummaryVO>        listSummaryVO;
	private List<ProductivityVO>   listProductivityVO;
	private List<StageVO>          listStageVO;
	private List<GeneralComboVO>   listDuties;
	private List<StandardDetailVO> listStandardDetailVO;
	private List<CommentStageVO>   listCommentStageVO;
	private List<UnitCostProductVO>  listUnitCostProductVO;
	
	private String idStage;
	private String idBaseStage;
	private String idNewStage;
	private String idStatusStageBase;
	
	private String labelStatusStageBase;
	private String labelStatusStageNew;
	
	
	private InputStream fileInputStream;
	
	
	private String contentDisposition;
	private String commentStage;
	
	private List<PagoCobranzaVO> listPagoCobranzaVO;
	private String factura;
	
	private FilterProductStageVO filterProductStageVO;
 
	
	
	public String resultCost() {//aki
		
		ResultCostDelegate resultCostDelegate = new ResultCostDelegate();
		try {
			
			factura = (String) ServletActionContext.getRequest().getSession().getAttribute("factura");
//			idBaseStage = (String) ServletActionContext.getRequest().getSession().getAttribute("idStage");
			
			logger.debug("factura::: "+factura);

			listPagoCobranzaVO	  = resultCostDelegate.listStagesFilter(factura);
//			listStageVO           = resultCostDelegate.listStagesFilter(idBaseStage);
//			idStatusStageBase     = resultCostDelegate.getIdStateStage(idBaseStage);
//			listDuties            = resultCostDelegate.listDutiesStage(idBaseStage);
//			labelStatusStageBase  = resultCostDelegate.getNameStateStage(idBaseStage);
//			
//			idNewStage            = resultCostDelegate.getIdStagePlan(idBaseStage);
//			labelStatusStageNew   = resultCostDelegate.getNameStateStage(idNewStage);
			
//			logger.debug("idNewStage::: "+idNewStage);
			//listSiteSource          = new GeneralTransactionsDelegate().listSiteSource((String) ServletActionContext.getRequest().getSession().getAttribute("idStage")); 
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return "SUCCESS";
	}
	
//	public String resultCost() {//aki
//		
//		ResultCostDelegate resultCostDelegate = new ResultCostDelegate();
//		try {
//			
//			
//			
//			
//			idBaseStage = (String) ServletActionContext.getRequest().getSession().getAttribute("idStage");
//			
//			logger.debug("idBaseStage::: "+idBaseStage);
//
//			
//			listStageVO           = resultCostDelegate.listStagesFilter(idBaseStage);
//			idStatusStageBase     = resultCostDelegate.getIdStateStage(idBaseStage);
//			listDuties            = resultCostDelegate.listDutiesStage(idBaseStage);
//			labelStatusStageBase  = resultCostDelegate.getNameStateStage(idBaseStage);
//			
//			idNewStage            = resultCostDelegate.getIdStagePlan(idBaseStage);
//			labelStatusStageNew   = resultCostDelegate.getNameStateStage(idNewStage);
//			
//			logger.debug("idNewStage::: "+idNewStage);
//			//listSiteSource          = new GeneralTransactionsDelegate().listSiteSource((String) ServletActionContext.getRequest().getSession().getAttribute("idStage")); 
//		} catch (DAOException e) {
//			statusMessageError = e.getMessage();
//		}
//		return "SUCCESS";
//	}
	
	public String applyFilterProduct() {
		
		try {
			
			ServletActionContext.getRequest().getSession().setAttribute("filterProductSession", new ResultCostDelegate().getListProductFilter(filterProductStageVO, idBaseStage));
			
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	
	public String insertCommentStage() {
		
		UserVO userSessionVO = (UserVO)ServletActionContext.getRequest().getSession().getAttribute("userSessionVO");
		try {
			
			new ResultCostDelegate().insertCommentStage(idBaseStage, userSessionVO.getIsidUser(), commentStage);
			
			listCommentStageVO = new ResultCostDelegate().listCommentStage(idBaseStage);
			
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	public String listCommentStage() {
		
		try {
			
			listCommentStageVO = new ResultCostDelegate().listCommentStage(idBaseStage);
			
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	public String updateStageBudget() {
		
		try {
			
			new ResultCostDelegate().updateStageBudget(idBaseStage);
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	public String updateStageForecast() {
		
		try {
			
			new ResultCostDelegate().updateStageForecast(idBaseStage);
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	
	public String updateStageRejectForecast() {
		
		String urlContextApp = ServletActionContext.getRequest().getProtocol().split("/")[0]+"://"+ServletActionContext.getRequest().getLocalAddr()+":"+ServletActionContext.getRequest().getLocalPort()+""+ServletActionContext.getRequest().getContextPath();
		
		ResultCostDelegate resultCostDelegate = new ResultCostDelegate();
		
		UserVO userSessionVO = (UserVO)ServletActionContext.getRequest().getSession().getAttribute("userSessionVO");
		try {
			resultCostDelegate.updateStageRejectForecast(idBaseStage);
			
			
			List<UserVO> listUserVO = resultCostDelegate.listNormalUsersEmails(idBaseStage);
			
//			SendEmailNotification sendEmailNotification = new SendEmailNotification(urlContextApp);
//			for (UserVO userVO: listUserVO){
//				sendEmailNotification.sendNotificationRejectForecast(idBaseStage, userVO.getEmailUser(), userVO.getIsidUser(), userSessionVO.getIsidUser());
//			}
			
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	public String updateStageToApproveForecast() {
		
		String urlContextApp = ServletActionContext.getRequest().getProtocol().split("/")[0]+"://"+ServletActionContext.getRequest().getLocalAddr()+":"+ServletActionContext.getRequest().getLocalPort()+""+ServletActionContext.getRequest().getContextPath();
		ResultCostDelegate resultCostDelegate = new ResultCostDelegate();
		
		UserVO userSessionVO = (UserVO)ServletActionContext.getRequest().getSession().getAttribute("userSessionVO");
		try {
			resultCostDelegate.updateStageToApproveForecast(idBaseStage);
			
			List<UserVO> listUserVO = resultCostDelegate.listApproverUsersEmails(idBaseStage);
			
//			SendEmailNotification sendEmailNotification = new SendEmailNotification(urlContextApp);
//			for (UserVO userVO: listUserVO){
//				sendEmailNotification.sendNotificationToApproveForecast(idBaseStage, userVO.getEmailUser(), userVO.getIsidUser(), userSessionVO.getIsidUser());
//			}
			
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	public String updateStageApproveForecast() {
		
		String urlContextApp = ServletActionContext.getRequest().getProtocol().split("/")[0]+"://"+ServletActionContext.getRequest().getLocalAddr()+":"+ServletActionContext.getRequest().getLocalPort()+""+ServletActionContext.getRequest().getContextPath();
		
		UserVO userSessionVO = (UserVO)ServletActionContext.getRequest().getSession().getAttribute("userSessionVO");
		try {
			
			if (!userSessionVO.getIdProfileUser().equals("2"))
				statusMessageError = "El usuario no tiene el perfil de aprobador para resolver esta acción";
			else{
				new ResultCostDelegate().updateStageApproveForecast(idBaseStage);
				
				
				List<UserVO> listUserVO = new ResultCostDelegate().listNormalUsersEmails(idBaseStage);
				
//				SendEmailNotification sendEmailNotification = new SendEmailNotification(urlContextApp);
//				for (UserVO userVO: listUserVO){
//					sendEmailNotification.sendNotificationApproveForecast(idBaseStage, userVO.getEmailUser(), userVO.getIsidUser(), userSessionVO.getIsidUser());
//				}
			}
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	
	public String updateStageToApproveBudget() {
		
		String urlContextApp = ServletActionContext.getRequest().getProtocol().split("/")[0]+"://"+ServletActionContext.getRequest().getLocalAddr()+":"+ServletActionContext.getRequest().getLocalPort()+""+ServletActionContext.getRequest().getContextPath();
		
		try {
			
			UserVO userSessionVO = (UserVO)ServletActionContext.getRequest().getSession().getAttribute("userSessionVO");
			
			new ResultCostDelegate().updateStageToApproveBudget(idBaseStage);
			
			List<UserVO> listUserVO = new ResultCostDelegate().listApproverUsersEmails(idBaseStage);
			
//			SendEmailNotification sendEmailNotification = new SendEmailNotification(urlContextApp);
//			for (UserVO userVO: listUserVO){
//				sendEmailNotification.sendNotificationToApproveBudget(idBaseStage, userVO.getEmailUser(), userVO.getIsidUser(), userSessionVO.getIsidUser());
//			}
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	
	public String updateStageRejectBudget() {
		
		String urlContextApp = ServletActionContext.getRequest().getProtocol().split("/")[0]+"://"+ServletActionContext.getRequest().getLocalAddr()+":"+ServletActionContext.getRequest().getLocalPort()+""+ServletActionContext.getRequest().getContextPath();
		
		ResultCostDelegate resultCostDelegate = new ResultCostDelegate();
		try {
			
			
			
			UserVO userSessionVO = (UserVO)ServletActionContext.getRequest().getSession().getAttribute("userSessionVO");
			
			if (!userSessionVO.getIdProfileUser().equals("2"))
				statusMessageError = "El usuario no tiene el perfil de aprobador para resolver esta acción";
			else{
				resultCostDelegate.updateStageRejectBudget(idBaseStage);
				
				List<UserVO> listUserVO = new ResultCostDelegate().listNormalUsersEmails(idBaseStage);
				
//				SendEmailNotification sendEmailNotification = new SendEmailNotification(urlContextApp);
//				for (UserVO userVO: listUserVO){
//					sendEmailNotification.sendNotificationRejectBudget(idBaseStage, userVO.getEmailUser(), userVO.getIsidUser(), userSessionVO.getIsidUser());
//				}
			}
			
			
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	public String updateStageApproveBudget() {
		
		String urlContextApp = ServletActionContext.getRequest().getProtocol().split("/")[0]+"://"+ServletActionContext.getRequest().getLocalAddr()+":"+ServletActionContext.getRequest().getLocalPort()+""+ServletActionContext.getRequest().getContextPath();
		
		try {
			
			UserVO userSessionVO = (UserVO)ServletActionContext.getRequest().getSession().getAttribute("userSessionVO");
			
			if (!userSessionVO.getIdProfileUser().equals("2"))
				statusMessageError = "El usuario no tiene el perfil de aprobador para resolver esta acción";
			else{
				new ResultCostDelegate().updateStageApproveBudget(idBaseStage);
				
				List<UserVO> listUserVO = new ResultCostDelegate().listNormalUsersEmails(idBaseStage);
				
//				SendEmailNotification sendEmailNotification = new SendEmailNotification(urlContextApp);
//				for (UserVO userVO: listUserVO){
//					sendEmailNotification.sendNotificationApproveBudget(idBaseStage, userVO.getEmailUser(), userVO.getIsidUser(), userSessionVO.getIsidUser());
//				}
			}
			
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	public String obtieneIdStatusStageBase() {
		
		try {
			
			idStatusStageBase  = new ResultCostDelegate().getIdStateStage(idBaseStage);
			
			//actualizamos session id Stage al cambiar el selector
			ServletActionContext.getRequest().getSession().setAttribute("idStage",idBaseStage);
			
			labelStatusStageBase  = new ResultCostDelegate().getNameStateStage(idBaseStage);
			
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	public String obtieneIdStatusStageNew() {
		
		try {
			
			labelStatusStageNew  = new ResultCostDelegate().getNameStateStage(idNewStage);
			
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	
	public String initMonthlyData() {
		
		try {
			
			listMonthlyDataVO = new ResultCostDelegate().initMonthlyData(idBaseStage, idNewStage, (String) ServletActionContext.getRequest().getSession().getAttribute("filterProductSession"));
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	
	public String initSummary() {
		
		try {
			listSummaryVO = new ResultCostDelegate().initSummary(idBaseStage, idNewStage, (String) ServletActionContext.getRequest().getSession().getAttribute("filterProductSession"));
		
			if(listSummaryVO.size() == 0)
				statusMessageError = "The filter applied has not generated results";
			
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	
	public String initStandarDetail() {
		
		try {
			
			listStandardDetailVO = new ResultCostDelegate().listStandarDetailStage(idBaseStage, idNewStage, (String) ServletActionContext.getRequest().getSession().getAttribute("filterProductSession"));
			
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	public String initImpactAnalisysStage() {
		
		try {
			
			listStandardDetailVO = new ResultCostDelegate().initImpactAnalisysStage(idBaseStage, idNewStage, (String) ServletActionContext.getRequest().getSession().getAttribute("filterProductSession"));
			
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	
	public String initProductivity() {
		
		try {
			
			listProductivityVO = new ResultCostDelegate().initProductivity(idBaseStage, idNewStage);
			
			
			logger.debug("listProductivityVO.size():  "+listProductivityVO.size());
			
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	
	public String initUnitCostProduct() {
		
		 try {
			listUnitCostProductVO =  new ResultCostDelegate().initUnitCostProduct(idBaseStage);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.debug("dentro del action con jquery ajax");
		return Action.SUCCESS;
	}
	
	public String exportExcelResult() {
		
		ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
		try{
//			Date now = new Date();
//			String dateString = now.toString();
//			SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
			
			
			ReportExportExcel reportExportExcel = new ReportExportExcel();
			
			
			
			List<StandardDetailVO> listStandardDetailVO = new ResultCostDelegate().listStandarDetailStage(idBaseStage, idNewStage, (String) ServletActionContext.getRequest().getSession().getAttribute("filterProductSession"));			
			List<ProductivityVO> listProductivityVO     = new ResultCostDelegate().initProductivity(idBaseStage, idNewStage);
			List<SummaryVO> listSummaryVO               = new ResultCostDelegate().initSummary(idBaseStage, idNewStage, (String) ServletActionContext.getRequest().getSession().getAttribute("filterProductSession"));
			List<MonthlyDataVO> listMonthlyDataVO       = new ResultCostDelegate().initMonthlyData(idBaseStage, idNewStage, (String) ServletActionContext.getRequest().getSession().getAttribute("filterProductSession"));
			List<UnitCostProductVO> listUnitCostVO      = new ResultCostDelegate().initUnitCostProduct(idBaseStage);
			
			
			
			reportExportExcel.writeStandardDetail(listStandardDetailVO);			
			reportExportExcel.writeProductivity(listProductivityVO);
			reportExportExcel.writeSummaryUnits(listSummaryVO);
			reportExportExcel.writeSummaryTrnPrice(listSummaryVO);
			reportExportExcel.writeSummaryDuties(listSummaryVO);
			reportExportExcel.writeSummaryFreight(listSummaryVO);
			reportExportExcel.writeMonthlyData(listMonthlyDataVO);
			reportExportExcel.writeUnitCost(listUnitCostVO);
			
			reportExportExcel.getXSSFWorkbook().write(outByteStream);
			
			String nombreArchivo = "report-excel-outputs.xlsx";
			contentDisposition = "attachment;filename=" + nombreArchivo;
			fileInputStream    = new ByteArrayInputStream(outByteStream.toByteArray()); 
			
		}catch (IOException e) {
			e.printStackTrace();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
		return Action.SUCCESS;
	}

	
	//Setter & Getter
	
	public String getFactura() {
		return factura;
	}
	public void setFactura(String factura) {
		this.factura = factura;
	}
	
	public String getStatusMessageError() {
		return statusMessageError;
	}
	public void setStatusMessageError(String statusMessageError) {
		this.statusMessageError = statusMessageError;
	}
	public List<MonthlyDataVO> getListMonthlyDataVO() {
		return listMonthlyDataVO;
	}
	public void setListMonthlyDataVO(List<MonthlyDataVO> listMonthlyDataVO) {
		this.listMonthlyDataVO = listMonthlyDataVO;
	}
	public List<SummaryVO> getListSummaryVO() {
		return listSummaryVO;
	}
	public void setListSummaryVO(List<SummaryVO> listSummaryVO) {
		this.listSummaryVO = listSummaryVO;
	}
	public List<ProductivityVO> getListProductivityVO() {
		return listProductivityVO;
	}
	public void setListProductivityVO(List<ProductivityVO> listProductivityVO) {
		this.listProductivityVO = listProductivityVO;
	}
	public List<StageVO> getListStageVO() {
		return listStageVO;
	}
	public void setListStageVO(List<StageVO> listStageVO) {
		this.listStageVO = listStageVO;
	}
	public String getIdBaseStage() {
		return idBaseStage;
	}
	public void setIdBaseStage(String idBaseStage) {
		this.idBaseStage = idBaseStage;
	}
	public String getIdNewStage() {
		return idNewStage;
	}
	public void setIdNewStage(String idNewStage) {
		this.idNewStage = idNewStage;
	}
	public String getIdStatusStageBase() {
		return idStatusStageBase;
	}
	public void setIdStatusStageBase(String idStatusStageBase) {
		this.idStatusStageBase = idStatusStageBase;
	}
	public FilterProductStageVO getFilterProductStageVO() {
		return filterProductStageVO;
	}
	public void setFilterProductStageVO(FilterProductStageVO filterProductStageVO) {
		this.filterProductStageVO = filterProductStageVO;
	}
	public List<GeneralComboVO> getListDuties() {
		return listDuties;
	}
	public void setListDuties(List<GeneralComboVO> listDuties) {
		this.listDuties = listDuties;
	}
	public List<StandardDetailVO> getListStandardDetailVO() {
		return listStandardDetailVO;
	}
	public void setListStandardDetailVO(List<StandardDetailVO> listStandardDetailVO) {
		this.listStandardDetailVO = listStandardDetailVO;
	}
	public InputStream getFileInputStream() {
		return fileInputStream;
	}
	public void setFileInputStream(InputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
	}
	public String getContentDisposition() {
		return contentDisposition;
	}
	public void setContentDisposition(String contentDisposition) {
		this.contentDisposition = contentDisposition;
	}
	public String getCommentStage() {
		return commentStage;
	}
	public void setCommentStage(String commentStage) {
		this.commentStage = commentStage;
	}
	public List<CommentStageVO> getListCommentStageVO() {
		return listCommentStageVO;
	}
	public void setListCommentStageVO(List<CommentStageVO> listCommentStageVO) {
		this.listCommentStageVO = listCommentStageVO;
	}
	public String getLabelStatusStageBase() {
		return labelStatusStageBase;
	}
	public void setLabelStatusStageBase(String labelStatusStageBase) {
		this.labelStatusStageBase = labelStatusStageBase;
	}
	public String getLabelStatusStageNew() {
		return labelStatusStageNew;
	}
	public void setLabelStatusStageNew(String labelStatusStageNew) {
		this.labelStatusStageNew = labelStatusStageNew;
	}
	public List<UnitCostProductVO> getListUnitCostProductVO() {
		return listUnitCostProductVO;
	}
	public void setListUnitCostProductVO(
			List<UnitCostProductVO> listUnitCostProductVO) {
		this.listUnitCostProductVO = listUnitCostProductVO;
	}
	
}