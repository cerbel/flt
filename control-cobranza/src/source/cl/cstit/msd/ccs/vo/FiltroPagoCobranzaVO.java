package cl.cstit.msd.ccs.vo;

public class FiltroPagoCobranzaVO {
	private String estado;
	private String factura;
	private String rut;//nuevo
	
	public String getRut() {
		return rut;
	}
	public void setRut(String rut) {
		this.rut = rut;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getFactura() {
		return factura;
	}
	public void setFactura(String factura) {
		this.factura = factura;
	}
	
}
