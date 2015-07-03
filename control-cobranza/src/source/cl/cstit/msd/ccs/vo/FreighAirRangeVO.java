package cl.cstit.msd.ccs.vo;


public class FreighAirRangeVO {

	private String idRangeFreighAir;
	private String idFreighAir;
	private String descriptionFreighAir;
	private int    fromFreighAir;
	private int    toFreighAir;
	private float  rateFreighAir;
	

	public String getIdFreighAir() {
		return idFreighAir;
	}
	public void setIdFreighAir(String idFreighAir) {
		this.idFreighAir = idFreighAir;
	}
	public String getDescriptionFreighAir() {
		return descriptionFreighAir;
	}
	
	public String getIdRangeFreighAir() {
		return idRangeFreighAir;
	}
	public void setIdRangeFreighAir(String idRangeFreighAir) {
		this.idRangeFreighAir = idRangeFreighAir;
	}
	public void setDescriptionFreighAir(String descriptionFreighAir) {
		this.descriptionFreighAir = descriptionFreighAir;
	}
	public int getFromFreighAir() {
		return fromFreighAir;
	}
	public void setFromFreighAir(int fromFreighAir) {
		this.fromFreighAir = fromFreighAir;
	}
	public int getToFreighAir() {
		return toFreighAir;
	}
	public void setToFreighAir(int toFreighAir) {
		this.toFreighAir = toFreighAir;
	}
	public float getRateFreighAir() {
		return rateFreighAir;
	}
	public void setRateFreighAir(float rateFreighAir) {
		this.rateFreighAir = rateFreighAir;
	}
	
}
