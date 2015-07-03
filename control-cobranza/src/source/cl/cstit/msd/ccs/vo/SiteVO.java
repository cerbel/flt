package cl.cstit.msd.ccs.vo;

import java.io.Serializable;

public class SiteVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String idSite;
	private String nameSite;
	private String checkedSite;
	
	private String flagSite;
	
	private String activeImage;
	
	private CurrencyVO localCurrency;
	
	
	public SiteVO(){}
	
	public SiteVO(String idSite){
		this.idSite = idSite;
	}
	
	

	
	public String getActiveImage() {
		return activeImage;
	}

	public void setActiveImage(String activeImage) {
		this.activeImage = activeImage;
	}

	public String getFlagSite() {
		return flagSite;
	}
	public void setFlagSite(String flagSite) {
		this.flagSite = flagSite;
	}
	public String getIdSite() {
		return idSite;
	}
	public void setIdSite(String idSite) {
		this.idSite = idSite;
	}
	public String getNameSite() {
		return nameSite;
	}
	public void setNameSite(String nameSite) {
		this.nameSite = nameSite;
	}
	public String getCheckedSite() {
		return checkedSite;
	}
	public void setCheckedSite(String checkedSite) {
		this.checkedSite = checkedSite;
	}
	public CurrencyVO getLocalCurrency() {
		return localCurrency;
	}
	public void setLocalCurrency(CurrencyVO localCurrency) {
		this.localCurrency = localCurrency;
	}
	
}
