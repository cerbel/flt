package cl.cstit.msd.ccs.vo;

import java.util.List;

public class SetupCustomDutiesHeadVO {
	
	
	private String idDutyHead;
	private float  otherTaxesDutyHead;
	private float  vatImportDutyHead;
	private float  otherTaxesImpDutyHead;
	private String idStageDutyHead;
	
	private List<SetupCustomDutiesDetailVO> listSetupCustomDutiesDetailVO;
	
	public String getIdDutyHead() {
		return idDutyHead;
	}
	public void setIdDutyHead(String idDutyHead) {
		this.idDutyHead = idDutyHead;
	}
	public float getOtherTaxesDutyHead() {
		return otherTaxesDutyHead;
	}
	public void setOtherTaxesDutyHead(float otherTaxesDutyHead) {
		this.otherTaxesDutyHead = otherTaxesDutyHead;
	}
	public float getVatImportDutyHead() {
		return vatImportDutyHead;
	}
	public void setVatImportDutyHead(float vatImportDutyHead) {
		this.vatImportDutyHead = vatImportDutyHead;
	}
	public float getOtherTaxesImpDutyHead() {
		return otherTaxesImpDutyHead;
	}
	public void setOtherTaxesImpDutyHead(float otherTaxesImpDutyHead) {
		this.otherTaxesImpDutyHead = otherTaxesImpDutyHead;
	}
	public String getIdStageDutyHead() {
		return idStageDutyHead;
	}
	public void setIdStageDutyHead(String idStageDutyHead) {
		this.idStageDutyHead = idStageDutyHead;
	}
	public List<SetupCustomDutiesDetailVO> getListSetupCustomDutiesDetailVO() {
		return listSetupCustomDutiesDetailVO;
	}
	public void setListSetupCustomDutiesDetailVO(
			List<SetupCustomDutiesDetailVO> listSetupCustomDutiesDetailVO) {
		this.listSetupCustomDutiesDetailVO = listSetupCustomDutiesDetailVO;
	}
	
}
