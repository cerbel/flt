package cl.cstit.msd.ccs.enums;

public enum Meses {

	ENE (1, "IMD_JAN= "),
	FEB (2, "IMD_FEB= "),
	MAR (3, "IMD_MAR= "),
	APR (4, "IMD_APR= "),
	MAY (5, "IMD_MAY= "),
	JUN (6, "IMD_JUN= "),
	JUL (7, "IMD_JUL= "),
	AUG (8, "IMD_AUG= "),
	SEP (9, "IMD_SEP= "),
	OCT (10, "IMD_OCT= "),
	NOV (11, "IMD_NOV= "),
	DEC (12, "IMD_DEC= ");
	
	private int id;
	private String descripcion;
	
	private Meses(int id, String descripcion){
		this.id = id;
		this.descripcion = descripcion;
	}
	
	public int getId(){
		return id;
	}
	
	public String getDescripcion(){
		return descripcion;
	}
}
