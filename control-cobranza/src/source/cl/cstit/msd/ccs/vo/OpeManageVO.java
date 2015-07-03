package cl.cstit.msd.ccs.vo;

public class OpeManageVO {

	private String somID;
	private String somEntry;
	private String somProcessingFee;
	private String somRateKg;
	private String curId;
	private String percentEntry;
	
	public String getPercentEntry() {
		return percentEntry;
	}
	public void setPercentEntry(String percentEntry) {
		this.percentEntry = percentEntry;
	}
	public String getSomID() {
		return somID;
	}
	public void setSomID(String somID) {
		this.somID = somID;
	}
	public String getSomEntry() {
		return somEntry;
	}
	public void setSomEntry(String somEntry) {
		this.somEntry = somEntry;
	}
	public String getSomProcessingFee() {
		return somProcessingFee;
	}
	public void setSomProcessingFee(String somProcessingFee) {
		this.somProcessingFee = somProcessingFee;
	}
	public String getSomRateKg() {
		return somRateKg;
	}
	public void setSomRateKg(String somRateKg) {
		this.somRateKg = somRateKg;
	}
	public String getCurId() {
		return curId;
	}
	public void setCurId(String curId) {
		this.curId = curId;
	}
	
}
