package cl.cstit.msd.ccs.actions;

import org.apache.struts2.ServletActionContext;

import cl.cstit.msd.ccs.delegate.NotificationApproveProcessDelegate;
import cl.cstit.msd.ccs.exception.DAOException;
import cl.cstit.msd.ccs.vo.NotificationVO;



public class NotificationApproveProcessAction{
	
	
	private String idStage;
	private String isidUserTo;
	private String isidUserFrom;
	
	public String getIsidUserFrom() {
		return isidUserFrom;
	}


	public void setIsidUserFrom(String isidUserFrom) {
		this.isidUserFrom = isidUserFrom;
	}
	private NotificationVO notificationVO;
	
	private String urlContextApp;
	
	public String notificationToApproveForecast() {
		
		try {
			notificationVO = new NotificationApproveProcessDelegate().notificationContentStage(idStage, isidUserTo, isidUserFrom);
			
			urlContextApp = ServletActionContext.getRequest().getProtocol().split("/")[0]+"://"+ServletActionContext.getRequest().getLocalAddr()+":"+ServletActionContext.getRequest().getLocalPort()+""+ServletActionContext.getRequest().getContextPath();
			
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return "SUCCESS";
	}
	
	
	public String notificationApproveForecast() {
		
		try {
			notificationVO = new NotificationApproveProcessDelegate().notificationContentStage(idStage, isidUserTo, isidUserFrom);
			
			urlContextApp = ServletActionContext.getRequest().getProtocol().split("/")[0]+"://"+ServletActionContext.getRequest().getLocalAddr()+":"+ServletActionContext.getRequest().getLocalPort()+""+ServletActionContext.getRequest().getContextPath();
			
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
		return "SUCCESS";
	}
	
	public String notificationRejectForecast() {
		
		try {
			notificationVO = new NotificationApproveProcessDelegate().notificationContentStage(idStage, isidUserTo, isidUserFrom);
			
			urlContextApp = ServletActionContext.getRequest().getProtocol().split("/")[0]+"://"+ServletActionContext.getRequest().getLocalAddr()+":"+ServletActionContext.getRequest().getLocalPort()+""+ServletActionContext.getRequest().getContextPath();
			
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
		return "SUCCESS";
	}
	
	
	public String notificationToApproveBudget() {
		
		try {
			notificationVO = new NotificationApproveProcessDelegate().notificationContentStage(idStage, isidUserTo, isidUserFrom);
			
			urlContextApp = ServletActionContext.getRequest().getProtocol().split("/")[0]+"://"+ServletActionContext.getRequest().getLocalAddr()+":"+ServletActionContext.getRequest().getLocalPort()+""+ServletActionContext.getRequest().getContextPath();
			
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return "SUCCESS";
	}
	
	
	public String notificationApproveBudget() {
		
		try {
			notificationVO = new NotificationApproveProcessDelegate().notificationContentStage(idStage, isidUserTo, isidUserFrom);
			
			urlContextApp = ServletActionContext.getRequest().getProtocol().split("/")[0]+"://"+ServletActionContext.getRequest().getLocalAddr()+":"+ServletActionContext.getRequest().getLocalPort()+""+ServletActionContext.getRequest().getContextPath();
			
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
		return "SUCCESS";
	}
	
	
	public String notificationRejectBudget() {
		
		try {
			notificationVO = new NotificationApproveProcessDelegate().notificationContentStage(idStage, isidUserTo, isidUserFrom);
			
			urlContextApp = ServletActionContext.getRequest().getProtocol().split("/")[0]+"://"+ServletActionContext.getRequest().getLocalAddr()+":"+ServletActionContext.getRequest().getLocalPort()+""+ServletActionContext.getRequest().getContextPath();
			
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
		return "SUCCESS";
	}
	
	
	public String getIdStage() {
		return idStage;
	}
	public void setIdStage(String idStage) {
		this.idStage = idStage;
	}
	public NotificationVO getNotificationVO() {
		return notificationVO;
	}
	public void setNotificationVO(NotificationVO notificationVO) {
		this.notificationVO = notificationVO;
	}
	public String getUrlContextApp() {
		return urlContextApp;
	}
	public void setUrlContextApp(String urlContextApp) {
		this.urlContextApp = urlContextApp;
	}
	public String getIsidUserTo() {
		return isidUserTo;
	}
	public void setIsidUserTo(String isidUserTo) {
		this.isidUserTo = isidUserTo;
	}

}