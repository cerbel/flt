package cl.cstit.msd.ccs.actions;

import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import cl.cstit.msd.ccs.delegate.AdminUserDelegate;
import cl.cstit.msd.ccs.exception.DAOException;
import cl.cstit.msd.ccs.vo.PagoCobranzaVO;
import cl.cstit.msd.ccs.vo.ProfileVO;
import cl.cstit.msd.ccs.vo.ProveedorVO;
import cl.cstit.msd.ccs.vo.RowCountVO;
import cl.cstit.msd.ccs.vo.SiteVO;
import cl.cstit.msd.ccs.vo.UserVO; 

import com.opensymphony.xwork2.Action;

public class AdminUserAction{
 
	private List<UserVO> listUserVO;
	private List<ProfileVO> listProfile;

	private List<SiteVO> listSiteVO;
	private UserVO userVO;
	private PagoCobranzaVO pagoCobranzaVO;
	private String statusMessage;
	
	private int totalRows;
	private int numberPage;
	private int maxRows;
	
	private String nombreProveedor;
	private String rutProveedor;
	
	private List<ProveedorVO> listProveedor;
	
	
	public List<ProveedorVO> getRutProveedor2() {
		return listProveedor;
	}
	public void setRutProveedor2(List<ProveedorVO> rutProveedor2) {
		this.listProveedor = rutProveedor2;
	}

	
	public String getRutProveedor() {
		return rutProveedor;
	}

	public void setRutProveedor(String rutProveedor) {
		this.rutProveedor = rutProveedor;
	}

	public String getNombreProveedor() {
		return nombreProveedor;
	}

	public void setNombreProveedor(String nombreProveedor) {
		this.nombreProveedor = nombreProveedor;
	}
	
	AdminUserDelegate adminUserDelegate = new AdminUserDelegate();
	
	private static Logger logger = Logger.getLogger(AdminUserAction.class);
	
	private static ResourceBundle propertiesList = ResourceBundle.getBundle("configFileBundle");
	
	public String execute(){
		return "SUCCESS";
	}
	
	
	public String usersAdmin() {
		try {
			
			numberPage = 1;
			
			RowCountVO rowCountVO = new RowCountVO();
			setListUserVO(adminUserDelegate.listUser(null, rowCountVO, numberPage));
			
			this.totalRows = rowCountVO.getRowCount();
			
			logger.debug("totalRows: "+totalRows);
			
			
			
			maxRows = Integer.parseInt(propertiesList.getString("paginator.maxrows"));	
			
		} catch (DAOException e) {
			e.printStackTrace();
		} 
		return "SUCCESS";
	}

	
	public String listUser(){

		try {
			
			
			RowCountVO rowCountVO = new RowCountVO();
			setListUserVO(adminUserDelegate.listUser(userVO, rowCountVO, numberPage));
			
			
			this.totalRows = rowCountVO.getRowCount();
			
			logger.debug("totalRows: "+totalRows);
			
		} catch (DAOException e) {
			e.printStackTrace();
		} 
		return Action.SUCCESS;
	}


	//flt
	public String obtieneProveedor(){

		try {
			this.nombreProveedor = adminUserDelegate.obtieneProveedor(rutProveedor);
		} catch (DAOException e) {
			e.printStackTrace();
		} 
		return Action.SUCCESS;
	}
	
	//modificar
	public String obtieneRutProveedor(){

		try {
			this.listProveedor = adminUserDelegate.obtieneRutProveedor(nombreProveedor);
		} catch (DAOException e) {
			e.printStackTrace();
		} 
		return Action.SUCCESS;
	}
	
	
//	public String obtieneRutProveedor(){
//
//		try {
//			this.rutProveedor = adminUserDelegate.obtieneRutProveedor(nombreProveedor);
//		} catch (DAOException e) {
//			e.printStackTrace();
//		} 
//		return Action.SUCCESS;
//	}
	
	


	public String saveUser(){
		
		try {
			
//			adminUserDelegate.saveUser(userVO);
			adminUserDelegate.saveUser(pagoCobranzaVO);
			statusMessage = "El registro ha sido generado exitosamente!";
		} catch (DAOException e) {
			statusMessage = e.getMessage();
		}
		return Action.SUCCESS;
	}
	
	public String deleteUser(){
		
		try {
			adminUserDelegate.deleteUser(userVO);
			
			statusMessage = "El registro ha sido eliminado correctamente!";
		} catch (DAOException e) {
			statusMessage = e.getMessage();
		}
		
		return Action.SUCCESS;
	}

	public String createUser() {
		
		
		
		
		
//		try{
//			setListProfile(adminUserDelegate.listProfile());
//			setListSiteVO(adminUserDelegate.listSite());
//		}catch(DAOException e){
//			statusMessage = e.getMessage();
//		}
		
		return "SUCCESS";
	}
	
	public String listUserEdit(){

		try {
			RowCountVO rowCountVO = new RowCountVO();
			adminUserDelegate.listUser(userVO, rowCountVO, numberPage);
			logger.debug("totalRows: "+totalRows);
			
			
		} catch (DAOException e) {
			e.printStackTrace();
		} 
		return Action.SUCCESS;
	}
	
	public String editUser() {
		try {
			String isidUser =  ServletActionContext.getRequest().getParameter("isidUser");
			this.userVO = new UserVO();
			this.userVO.setIsidUser(isidUser);
			RowCountVO rowCountVO = new RowCountVO();
			for (UserVO userVO : adminUserDelegate.listUser(this.userVO, rowCountVO, numberPage)) {
				this.userVO.setIsidUser(userVO.getIsidUser());
				this.userVO.setNameUser(userVO.getNameUser());
				this.userVO.setEmailUser(userVO.getEmailUser());
				this.userVO.setIdProfileUser(userVO.getIdProfileUser());
			}
			setListProfile(adminUserDelegate.listProfile());
			setListSiteVO(adminUserDelegate.listSiteByUser(userVO.getIsidUser()));
			logger.debug("totalRows: "+totalRows);

			
		} catch (DAOException e) {
			e.printStackTrace();
		} 
		return "SUCCESS";
	}

	public String modifyUser(){
		
		try{
			adminUserDelegate.updateUser(userVO);
			
		}catch(DAOException e){
			e.printStackTrace();
		}
		
		return Action.SUCCESS;
	}

	/* GET & SET*/
	public List<UserVO> getListUserVO() {
		return listUserVO;
	}


	public void setListUserVO(List<UserVO> listUserVO) {
		this.listUserVO = listUserVO;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public UserVO getUserVO() {
		return userVO;
	}

	public void setUserVO(UserVO userVO) {
		this.userVO = userVO;
	}


	public List<ProfileVO> getListProfile() {
		return listProfile;
	}
	public void setListProfile(List<ProfileVO> listProfile) {
		this.listProfile = listProfile;
	}
	public List<SiteVO> getListSiteVO() {
		return listSiteVO;
	}
	public void setListSiteVO(List<SiteVO> listSiteVO) {
		this.listSiteVO = listSiteVO;
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
	public PagoCobranzaVO getPagoCobranzaVO() {
		return pagoCobranzaVO;
	}
	public void setPagoCobranzaVO(PagoCobranzaVO pagoCobranzaVO) {
		this.pagoCobranzaVO = pagoCobranzaVO;
	}
	

}