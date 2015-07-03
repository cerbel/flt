package cl.cstit.msd.ccs.vo;

public class CurrencyVO {
	
	private String idCurrency;
	private String acronym;
	
	public CurrencyVO(){}
	
	
	public CurrencyVO(String idCurrency, String acronym){
		this.idCurrency = idCurrency;
		this.acronym = acronym;
	}
	
	public String getIdCurrency() {
		return idCurrency;
	}
	public void setIdCurrency(String idCurrency) {
		this.idCurrency = idCurrency;
	}
	public String getAcronym() {
		return acronym;
	}
	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}
	
}
