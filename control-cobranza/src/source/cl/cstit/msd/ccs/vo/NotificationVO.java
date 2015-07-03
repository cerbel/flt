package cl.cstit.msd.ccs.vo;

public class NotificationVO {
	
	private String nameToUserNotification;
	private String nameFromUserNotification;
	private String dateCurrentNotification;
	
	private String idStageNotification;
	private String nameStageNotification;
	private String statusStageNotification;
	private String siteStageNotification;
	private String commentNotificacion;
	
	
	public String getCommentNotificacion() {
		return commentNotificacion;
	}
	public void setCommentNotificacion(String commentNotificacion) {
		this.commentNotificacion = commentNotificacion;
	}
	public String getNameToUserNotification() {
		return nameToUserNotification;
	}
	public void setNameToUserNotification(String nameToUserNotification) {
		this.nameToUserNotification = nameToUserNotification;
	}
	public String getNameFromUserNotification() {
		return nameFromUserNotification;
	}
	public void setNameFromUserNotification(String nameFromUserNotification) {
		this.nameFromUserNotification = nameFromUserNotification;
	}
	public String getDateCurrentNotification() {
		return dateCurrentNotification;
	}
	public void setDateCurrentNotification(String dateCurrentNotification) {
		this.dateCurrentNotification = dateCurrentNotification;
	}
	public String getIdStageNotification() {
		return idStageNotification;
	}
	public void setIdStageNotification(String idStageNotification) {
		this.idStageNotification = idStageNotification;
	}
	public String getNameStageNotification() {
		return nameStageNotification;
	}
	public void setNameStageNotification(String nameStageNotification) {
		this.nameStageNotification = nameStageNotification;
	}
	public String getStatusStageNotification() {
		return statusStageNotification;
	}
	public void setStatusStageNotification(String statusStageNotification) {
		this.statusStageNotification = statusStageNotification;
	}
	public String getSiteStageNotification() {
		return siteStageNotification;
	}
	public void setSiteStageNotification(String siteStageNotification) {
		this.siteStageNotification = siteStageNotification;
	}
	
}
