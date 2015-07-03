package cl.cstit.msd.ccs.vo;

public class SummaryVO {
	
	public SummaryVO(String monthSummary){
		this.monthSummary = monthSummary;
	}
	
	private String monthSummary;
	
	//Units
	private float  amountUnitStageASummary;
	private float  amountUnitStageBSummary;
	private float  amountUnitStageCSummary;
	private float  byMonthUnitSummary;
	
	//Fobs
	private float  amountFobsStageASummary;
	private float  amountFobsStageBSummary;
	private float  amountFobsStageCSummary;
	private float  byMonthFobsSummary;
	
	//Duties
	private float  amountDutiesStageASummary;
	private float  amountDutiesStageBSummary;
	private float  amountDutiesStageCSummary;
	private float  byMonthDutiesSummary;
	private float  dteDutiesSummary;
	
	//Freights
	private float  amountFreightsStageASummary;
	private float  amountFreightsStageBSummary;
	private float  amountFreightsStageCSummary;
	private float  byMonthFreightsSummary;
	private float  dteFreightsSummary;
	
	
	//Others
	private float  amountOthersStageASummary;
	private float  amountOthersStageBSummary;
	private float  amountOthersStageCSummary;
	private float  byMonthOthersSummary;
	private float  dteOthersSummary;
	
	public float getDteOthersSummary() {
		return dteOthersSummary;
	}
	public void setDteOthersSummary(float dteOthersSummary) {
		this.dteOthersSummary = dteOthersSummary;
	}
	public float getAmountFreightsStageASummary() {
		return amountFreightsStageASummary;
	}
	public void setAmountFreightsStageASummary(float amountFreightsStageASummary) {
		this.amountFreightsStageASummary = amountFreightsStageASummary;
	}
	public float getAmountFreightsStageBSummary() {
		return amountFreightsStageBSummary;
	}
	public void setAmountFreightsStageBSummary(float amountFreightsStageBSummary) {
		this.amountFreightsStageBSummary = amountFreightsStageBSummary;
	}
	public float getByMonthFreightsSummary() {
		return byMonthFreightsSummary;
	}
	public void setByMonthFreightsSummary(float byMonthFreightsSummary) {
		this.byMonthFreightsSummary = byMonthFreightsSummary;
	}
	public float getAmountDutiesStageASummary() {
		return amountDutiesStageASummary;
	}
	public void setAmountDutiesStageASummary(float amountDutiesStageASummary) {
		this.amountDutiesStageASummary = amountDutiesStageASummary;
	}
	public float getAmountDutiesStageBSummary() {
		return amountDutiesStageBSummary;
	}
	public void setAmountDutiesStageBSummary(float amountDutiesStageBSummary) {
		this.amountDutiesStageBSummary = amountDutiesStageBSummary;
	}
	public float getByMonthDutiesSummary() {
		return byMonthDutiesSummary;
	}
	public void setByMonthDutiesSummary(float byMonthDutiesSummary) {
		this.byMonthDutiesSummary = byMonthDutiesSummary;
	}
	public String getMonthSummary() {
		return monthSummary;
	}
	public void setMonthSummary(String monthSummary) {
		this.monthSummary = monthSummary;
	}
	public float getAmountUnitStageASummary() {
		return amountUnitStageASummary;
	}
	public void setAmountUnitStageASummary(float amountUnitStageASummary) {
		this.amountUnitStageASummary = amountUnitStageASummary;
	}
	public float getAmountUnitStageBSummary() {
		return amountUnitStageBSummary;
	}
	public void setAmountUnitStageBSummary(float amountUnitStageBSummary) {
		this.amountUnitStageBSummary = amountUnitStageBSummary;
	}
	public float getByMonthUnitSummary() {
		return byMonthUnitSummary;
	}
	public void setByMonthUnitSummary(float byMonthUnitSummary) {
		this.byMonthUnitSummary = byMonthUnitSummary;
	}
	public float getAmountFobsStageASummary() {
		return amountFobsStageASummary;
	}
	public void setAmountFobsStageASummary(float amountFobsStageASummary) {
		this.amountFobsStageASummary = amountFobsStageASummary;
	}
	public float getAmountFobsStageBSummary() {
		return amountFobsStageBSummary;
	}
	public void setAmountFobsStageBSummary(float amountFobsStageBSummary) {
		this.amountFobsStageBSummary = amountFobsStageBSummary;
	}
	public float getByMonthFobsSummary() {
		return byMonthFobsSummary;
	}
	public void setByMonthFobsSummary(float byMonthFobsSummary) {
		this.byMonthFobsSummary = byMonthFobsSummary;
	}
	public float getDteDutiesSummary() {
		return dteDutiesSummary;
	}
	public void setDteDutiesSummary(float dteDutiesSummary) {
		this.dteDutiesSummary = dteDutiesSummary;
	}
	public float getDteFreightsSummary() {
		return dteFreightsSummary;
	}
	public void setDteFreightsSummary(float dteFreightsSummary) {
		this.dteFreightsSummary = dteFreightsSummary;
	}
	public float getAmountUnitStageCSummary() {
		return amountUnitStageCSummary;
	}
	public void setAmountUnitStageCSummary(float amountUnitStageCSummary) {
		this.amountUnitStageCSummary = amountUnitStageCSummary;
	}
	public float getAmountFobsStageCSummary() {
		return amountFobsStageCSummary;
	}
	public void setAmountFobsStageCSummary(float amountFobsStageCSummary) {
		this.amountFobsStageCSummary = amountFobsStageCSummary;
	}
	public float getAmountDutiesStageCSummary() {
		return amountDutiesStageCSummary;
	}
	public void setAmountDutiesStageCSummary(float amountDutiesStageCSummary) {
		this.amountDutiesStageCSummary = amountDutiesStageCSummary;
	}
	public float getAmountFreightsStageCSummary() {
		return amountFreightsStageCSummary;
	}
	public void setAmountFreightsStageCSummary(float amountFreightsStageCSummary) {
		this.amountFreightsStageCSummary = amountFreightsStageCSummary;
	}
	public float getAmountOthersStageASummary() {
		return amountOthersStageASummary;
	}
	public void setAmountOthersStageASummary(float amountOthersStageASummary) {
		this.amountOthersStageASummary = amountOthersStageASummary;
	}
	public float getAmountOthersStageBSummary() {
		return amountOthersStageBSummary;
	}
	public void setAmountOthersStageBSummary(float amountOthersStageBSummary) {
		this.amountOthersStageBSummary = amountOthersStageBSummary;
	}
	public float getAmountOthersStageCSummary() {
		return amountOthersStageCSummary;
	}
	public void setAmountOthersStageCSummary(float amountOthersStageCSummary) {
		this.amountOthersStageCSummary = amountOthersStageCSummary;
	}
	public float getByMonthOthersSummary() {
		return byMonthOthersSummary;
	}
	public void setByMonthOthersSummary(float byMonthOthersSummary) {
		this.byMonthOthersSummary = byMonthOthersSummary;
	}
	
}
