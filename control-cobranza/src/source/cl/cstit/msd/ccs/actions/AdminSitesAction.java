package cl.cstit.msd.ccs.actions;

import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.Action;

import cl.cstit.msd.ccs.delegate.AdminSitesDelegate;
import cl.cstit.msd.ccs.exception.DAOException;
import cl.cstit.msd.ccs.vo.RowCountVO;
import cl.cstit.msd.ccs.vo.SiteVO;

public class AdminSitesAction{
 
	private List<SiteVO> listSiteVO;	
	private String idSite;	
	private SiteVO siteVO;
	
	private int totalRows;
	private int numberPage;
	private int maxRows;
	
	private static Logger logger = Logger.getLogger(AdminSitesAction.class);
	
	private static ResourceBundle propertiesList = ResourceBundle.getBundle("configFileBundle");
	
	public String execute(){
		return "SUCCESS";
	}
	
	public String listSites() {
		try {
			
			numberPage = 1;		
			RowCountVO rowCountVO = new RowCountVO();
			listSiteVO = new AdminSitesDelegate().listSites(rowCountVO, numberPage);
			logger.debug("totalRows: "+totalRows);			
			maxRows = Integer.parseInt(propertiesList.getString("paginator.maxrows"));	
			
		} catch (DAOException e) {
			e.printStackTrace();
		} 
		return "SUCCESS";
	}
	
	public String editSite() {
		try {
			siteVO = new AdminSitesDelegate().getSite(idSite);
		} catch (DAOException e) {
			e.printStackTrace();
		} 
		return "SUCCESS";
	}
	
	public String createSite() {
		return "SUCCESS";
	}
	
	public String saveSite() {
		try {
			new AdminSitesDelegate().saveSite(siteVO);
		} catch (DAOException e) {
			e.printStackTrace();
		} 
		return Action.SUCCESS;
	}
	
	
	public String registerSite() {
		try {
			new AdminSitesDelegate().updateUser(siteVO);
		} catch (DAOException e) {
			e.printStackTrace();
		} 
		return Action.SUCCESS;
	}
	
	public List<SiteVO> getListSiteVO() {
		return listSiteVO;
	}
	public void setListSiteVO(List<SiteVO> listSiteVO) {
		this.listSiteVO = listSiteVO;
	}
	public String getIdSite() {
		return idSite;
	}
	public void setIdSite(String idSite) {
		this.idSite = idSite;
	}
	public SiteVO getSiteVO() {
		return siteVO;
	}
	public void setSiteVO(SiteVO siteVO) {
		this.siteVO = siteVO;
	}
	public int getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}
	public int getNumberPage() {
		return numberPage;
	}
	public void setNumberPage(int numberPage) {
		this.numberPage = numberPage;
	}
	public int getMaxRows() {
		return maxRows;
	}
	public void setMaxRows(int maxRows) {
		this.maxRows = maxRows;
	}
	
}