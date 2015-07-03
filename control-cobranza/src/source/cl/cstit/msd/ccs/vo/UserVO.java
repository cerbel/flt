package cl.cstit.msd.ccs.vo;

import java.util.ArrayList;
import java.util.List;

public class UserVO {
	
	private String isidUser;
	private String nameUser;
	private String emailUser;
	private String idProfileUser;
	private String passwdUser;
	private String nameProfileUser;
	private String listSite;
	
	private String lastAccessDate;
	
	private List<SiteVO> listSiteVO = new ArrayList<SiteVO>();
	
	public List<SiteVO> getListSiteVO() {
		return listSiteVO;
	}
	public void setListSiteVO(List<SiteVO> listSiteVO) {
		this.listSiteVO = listSiteVO;
	}
	public String getIsidUser() {
		return isidUser;
	}
	public void setIsidUser(String isidUser) {
		this.isidUser = isidUser;
	}
	public String getNameUser() {
		return nameUser;
	}
	public void setNameUser(String nameUser) {
		this.nameUser = nameUser;
	}
	public String getEmailUser() {
		return emailUser;
	}
	public void setEmailUser(String emailUser) {
		this.emailUser = emailUser;
	}
	public String getIdProfileUser() {
		return idProfileUser;
	}
	public void setIdProfileUser(String idProfileUser) {
		this.idProfileUser = idProfileUser;
	}
	public String getPasswdUser() {
		return passwdUser;
	}
	public void setPasswdUser(String passwdUser) {
		this.passwdUser = passwdUser;
	}
	public String getNameProfileUser() {
		return nameProfileUser;
	}
	public void setNameProfileUser(String nameProfileUser) {
		this.nameProfileUser = nameProfileUser;
	}
	public String getListSite() {
		return listSite;
	}
	public void setListSite(String listSite) {
		this.listSite = listSite;
	}
	public String getLastAccessDate() {
		return lastAccessDate;
	}
	public void setLastAccessDate(String lastAccessDate) {
		this.lastAccessDate = lastAccessDate;
	}


}
