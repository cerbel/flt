package cl.cstit.msd.ccs.vo;

import java.util.List;

public class UnitProductCostVO {
	
	private float units;
	private float fobs;
	private float duties;
	private float freight;
	private float others;
	
	
	private float janProduct;
	private float febProduct;
	private float marProduct;
	private float aprProduct;
	private float mayProduct;
	private float junProduct;
	private float julProduct;
	private float augProduct;
	private float sepProduct;
	private float octProduct;
	private float novProduct;
	private float decProduct;
	
	private List<ConstUnitVO> listConstUnitFreigth;
	private List<ConstUnitVO> listConstUnitOthers;
	private List<ConstUnitVO> listConstUnitDuties;
	
	public float getJanProduct() {
		return janProduct;
	}
	public void setJanProduct(float janProduct) {
		this.janProduct = janProduct;
	}
	public float getFebProduct() {
		return febProduct;
	}
	public void setFebProduct(float febProduct) {
		this.febProduct = febProduct;
	}
	public float getMarProduct() {
		return marProduct;
	}
	public void setMarProduct(float marProduct) {
		this.marProduct = marProduct;
	}
	public float getAprProduct() {
		return aprProduct;
	}
	public void setAprProduct(float aprProduct) {
		this.aprProduct = aprProduct;
	}
	public float getMayProduct() {
		return mayProduct;
	}
	public void setMayProduct(float mayProduct) {
		this.mayProduct = mayProduct;
	}
	public float getJunProduct() {
		return junProduct;
	}
	public void setJunProduct(float junProduct) {
		this.junProduct = junProduct;
	}
	public float getJulProduct() {
		return julProduct;
	}
	public void setJulProduct(float julProduct) {
		this.julProduct = julProduct;
	}
	public float getAugProduct() {
		return augProduct;
	}
	public void setAugProduct(float augProduct) {
		this.augProduct = augProduct;
	}
	public float getSepProduct() {
		return sepProduct;
	}
	public void setSepProduct(float sepProduct) {
		this.sepProduct = sepProduct;
	}
	public float getOctProduct() {
		return octProduct;
	}
	public void setOctProduct(float octProduct) {
		this.octProduct = octProduct;
	}
	public float getNovProduct() {
		return novProduct;
	}
	public void setNovProduct(float novProduct) {
		this.novProduct = novProduct;
	}
	public float getDecProduct() {
		return decProduct;
	}
	public void setDecProduct(float decProduct) {
		this.decProduct = decProduct;
	}
	public float getUnits() {
		return units;
	}
	public void setUnits(float units) {
		this.units = units;
	}
	public float getFobs() {
		return fobs;
	}
	public void setFobs(float fobs) {
		this.fobs = fobs;
	}
	public float getDuties() {
		return duties;
	}
	public void setDuties(float duties) {
		this.duties = duties;
	}
	public float getFreight() {
		return freight;
	}
	public void setFreight(float freight) {
		this.freight = freight;
	}
	public float getOthers() {
		return others;
	}
	public void setOthers(float others) {
		this.others = others;
	}
	public List<ConstUnitVO> getListConstUnitFreigth() {
		return listConstUnitFreigth;
	}
	public void setListConstUnitFreigth(List<ConstUnitVO> listConstUnitFreigth) {
		this.listConstUnitFreigth = listConstUnitFreigth;
	}
	public List<ConstUnitVO> getListConstUnitOthers() {
		return listConstUnitOthers;
	}
	public void setListConstUnitOthers(List<ConstUnitVO> listConstUnitOthers) {
		this.listConstUnitOthers = listConstUnitOthers;
	}
	public List<ConstUnitVO> getListConstUnitDuties() {
		return listConstUnitDuties;
	}
	public void setListConstUnitDuties(List<ConstUnitVO> listConstUnitDuties) {
		this.listConstUnitDuties = listConstUnitDuties;
	}
	
}
