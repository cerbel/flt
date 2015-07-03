package cl.cstit.msd.ccs.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;

import cl.cstit.msd.ccs.delegate.AutenticationUserDelegate;
import cl.cstit.msd.ccs.delegate.GeneralTransactionsDelegate;
import cl.cstit.msd.ccs.exception.DAOException;
import cl.cstit.msd.ccs.utils.Authentication;
import cl.cstit.msd.ccs.vo.HomeCompareFamilyVO;
import cl.cstit.msd.ccs.vo.HomeCompareVO;
import cl.cstit.msd.ccs.vo.SiteVO;
import cl.cstit.msd.ccs.vo.StandardDetailVO;
import cl.cstit.msd.ccs.vo.SummaryVO;
import cl.cstit.msd.ccs.vo.UserVO;

public class AutenticationUserAction { 
	
	static AutenticationUserDelegate autenticationUserDelegate = new AutenticationUserDelegate();
	static Logger logger = Logger.getLogger(AutenticationUserAction.class);
	
	
	private UserVO userVO;
	private String statusMessageError = new String();
	private HomeCompareVO homeCompareVO;
	
	
	private List<HomeCompareFamilyVO> listHomeCompareFamilyVO;
	private List<StandardDetailVO> listStandardDetailVO; 
	private List<SummaryVO> listSummaryVO;
	
	private String principalAction;
	
	public String login() {
		return "SUCCESS";
	}
	
	public String autenticate() {
		
		GeneralTransactionsDelegate generalTransactionsDelegate = new GeneralTransactionsDelegate();
		
		try {
			
//			Authentication authentication = new Authentication();
//			System.out.println("autenticacion.authenticate("+userVO.getIsidUser()+","+userVO.getPasswdUser()+",''): "+authentication.authenticate(userVO.getIsidUser(),userVO.getPasswdUser(),""));
			
			userVO = autenticationUserDelegate.autenticateUser(userVO.getIsidUser());
			
			//Generando
			ServletActionContext.getRequest().getSession().setAttribute("userSessionVO", userVO);
			ServletActionContext.getRequest().getSession().setAttribute("listSitesBaseSession", userVO.getListSiteVO());
			
			ServletActionContext.getRequest().getSession().setAttribute("listProductFamiliesSession", generalTransactionsDelegate.listProductFamilies());
			ServletActionContext.getRequest().getSession().setAttribute("listProductTransportsSession", generalTransactionsDelegate.listProductTransports());
			ServletActionContext.getRequest().getSession().setAttribute("listProductUnitMeasureSession", generalTransactionsDelegate.listProductUnitMeasure());
			ServletActionContext.getRequest().getSession().setAttribute("listProductTypesSession", generalTransactionsDelegate.listProductTypes());
			
			ServletActionContext.getRequest().getSession().setAttribute("listTypeStageSession", generalTransactionsDelegate.listTypeStage());

//			ServletActionContext.getRequest().getSession().setAttribute("listSiteSourceSession", generalTransactionsDelegate.listSiteSource());			
			ServletActionContext.getRequest().getSession().setAttribute("listProductTradeSession", generalTransactionsDelegate.listProductTrade());
			ServletActionContext.getRequest().getSession().setAttribute("listProductPresentationSession", generalTransactionsDelegate.listProductPresentation());
			ServletActionContext.getRequest().getSession().setAttribute("listProductTypeLoadSession", generalTransactionsDelegate.listProductTypeLoad());

			ServletActionContext.getRequest().getSession().setAttribute("listCurrencySession", generalTransactionsDelegate.listCurrency());
			
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	
	public String homeCompareStages() {
		
		try {
			UserVO userSessionVO = (UserVO)ServletActionContext.getRequest().getSession().getAttribute("userSessionVO");
			
			homeCompareVO = autenticationUserDelegate.homeCompareStagesChart(userSessionVO.getListSiteVO());
			
			listHomeCompareFamilyVO = autenticationUserDelegate.homeCompareStagesFamily(userSessionVO.getListSiteVO());
			
			if (listHomeCompareFamilyVO.size() == 0)
				statusMessageError = "No existen datos el site o sites seleccionados, es necesario realizar una carga inicial desde la funcionalidad Load Import";
			
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		
		return Action.SUCCESS;
	}
	
	public String homeStandardCompareStages() {
		
		try {
			UserVO userSessionVO = (UserVO)ServletActionContext.getRequest().getSession().getAttribute("userSessionVO");
			
			listStandardDetailVO = autenticationUserDelegate.listStandarDetailStage(userSessionVO.getListSiteVO());
			
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	public String homeSummaryCompareStages() {
		
		try {
			UserVO userSessionVO = (UserVO)ServletActionContext.getRequest().getSession().getAttribute("userSessionVO");
			
			
			listSummaryVO = autenticationUserDelegate.initSummary(userSessionVO.getListSiteVO());
			
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	public String home() {
		
		UserVO userSessionVO = (UserVO)ServletActionContext.getRequest().getSession().getAttribute("userSessionVO");
		
		String[] sitesSelected = ServletActionContext.getRequest().getParameterValues("sitesSelected");
		
		if (sitesSelected != null){
			
			@SuppressWarnings("unchecked")
			List<SiteVO> listSitesBaseSession = (List<SiteVO>) ServletActionContext.getRequest().getSession().getAttribute("listSitesBaseSession");
			
			List<SiteVO>  newListSiteVO = new ArrayList<SiteVO>();
			for(int i=0; i<sitesSelected.length;i++){
				
				String idSite = sitesSelected[i];
				for (SiteVO siteVO : listSitesBaseSession) {
					if (siteVO.getIdSite().equals(idSite)){
						newListSiteVO.add(siteVO);
					}
				}
			}
			userSessionVO.setListSiteVO(newListSiteVO);
		}
		
		
		try {
			listHomeCompareFamilyVO = autenticationUserDelegate.homeCompareStagesFamily(userSessionVO.getListSiteVO());
			
			if (listHomeCompareFamilyVO.size() == 0){
				ServletActionContext.getRequest().getSession().setAttribute("isNotDataFound", "true");
			}else{
				ServletActionContext.getRequest().getSession().setAttribute("isNotDataFound", "false");
			}
			
		} catch (DAOException e) {
			statusMessageError = e.getMessage();
		}
		
		
		ServletActionContext.getRequest().getSession().setAttribute("menuSelectedOption", "home.action");
		return "SUCCESS";
	}
	
	
	public String selectMenuAction() throws IOException {
		
		logger.debug("principalAction: "+ principalAction);
		
		ServletActionContext.getRequest().getSession().setAttribute("menuSelectedOption", principalAction);
		
		ServletActionContext.getResponse().sendRedirect(principalAction);
		
		return null;	
	}
	
	public String logout() {
		ServletActionContext.getRequest().getSession().invalidate();
		return "SUCCESS";
	}
	public String sites() {
		return "SUCCESS";
	}
	
	
	/*Setter & Getters*/
	public UserVO getUserVO() {
		return userVO;
	}
	public void setUserVO(UserVO userVO) {
		this.userVO = userVO;
	}
	public String getStatusMessageError() {
		return statusMessageError;
	}
	public void setStatusMessageError(String statusMessageError) {
		this.statusMessageError = statusMessageError;
	}
	public HomeCompareVO getHomeCompareVO() {
		return homeCompareVO;
	}
	public void setHomeCompareVO(HomeCompareVO homeCompareVO) {
		this.homeCompareVO = homeCompareVO;
	}
	public List<HomeCompareFamilyVO> getListHomeCompareFamilyVO() {
		return listHomeCompareFamilyVO;
	}
	public void setListHomeCompareFamilyVO(
			List<HomeCompareFamilyVO> listHomeCompareFamilyVO) {
		this.listHomeCompareFamilyVO = listHomeCompareFamilyVO;
	}
	public List<StandardDetailVO> getListStandardDetailVO() {
		return listStandardDetailVO;
	}
	public void setListStandardDetailVO(List<StandardDetailVO> listStandardDetailVO) {
		this.listStandardDetailVO = listStandardDetailVO;
	}
	public List<SummaryVO> getListSummaryVO() {
		return listSummaryVO;
	}
	public void setListSummaryVO(List<SummaryVO> listSummaryVO) {
		this.listSummaryVO = listSummaryVO;
	}
	public String getPrincipalAction() {
		return principalAction;
	}
	public void setPrincipalAction(String principalAction) {
		this.principalAction = principalAction;
	}
	
}