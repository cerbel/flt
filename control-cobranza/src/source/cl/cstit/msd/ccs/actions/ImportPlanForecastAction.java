package cl.cstit.msd.ccs.actions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import cl.cstit.msd.ccs.delegate.ImportPlanForecastDelegate;
import cl.cstit.msd.ccs.exception.DAOException;
import cl.cstit.msd.ccs.utils.UtilExcel;
import cl.cstit.msd.ccs.vo.ErrorField;
import cl.cstit.msd.ccs.vo.FieldExcel;
import cl.cstit.msd.ccs.vo.SiteVO;
import cl.cstit.msd.ccs.vo.UserVO;

import com.opensymphony.xwork2.Action;

public class ImportPlanForecastAction{
 
	private File   fileUploadP;
	private String fileUploadPFileName;
	private File fileUploadF;
	private String fileUploadFFileName;
	private String fileName;
	private File file;
	private String sitID;
	private String iphType;
	private List<SiteVO> listSiteVO;
	private InputStream fileInputStream;
	private String contentDisposition;
	private List<ErrorField> listErrorFields;
	private String detailLastUpdate;
	private String statusMessageError = new String();
	private String statusMessage = new String();

	ImportPlanForecastDelegate importPlanForecastDelegate = new ImportPlanForecastDelegate();
	
	public String showImport() {
		
		UserVO userVO = (UserVO) ServletActionContext.getRequest().getSession().getAttribute("userSessionVO");
		listSiteVO = userVO.getListSiteVO();
		
		return "SUCCESS";
	}

	
	public String uploadExcel(){
		
		try{
			if (iphType.equals("P")){
				file = fileUploadP;
				fileName = fileUploadPFileName;
			}else{
				file = fileUploadF;
				fileName = fileUploadFFileName;
			}
			

			if (file == null){
				statusMessageError = "Please select a File";
				listErrorFields = new ArrayList<ErrorField>();
				statusMessage = "";
			}else if(!(fileName.substring(fileName.length() -4, fileName.length())).toLowerCase().equals("xlsx")){
				statusMessageError = "Not a valid File, only permitted XLSX";
				statusMessage = "";
				listErrorFields = new ArrayList<ErrorField>();
			}else{
			
				List<FieldExcel> listFieldExcel = UtilExcel.readExcel(file);
				UserVO userVO = (UserVO) ServletActionContext.getRequest().getSession().getAttribute("userSessionVO");
				if (listFieldExcel.size()>0){
					listErrorFields = importPlanForecastDelegate.savePlanForecast(sitID, userVO.getIsidUser(), iphType, listFieldExcel);
					detailLastUpdate = importPlanForecastDelegate.getLastUpload(sitID, iphType);
					
					
					statusMessage = "The file has been successfully loaded";
					statusMessageError = "";
				}else{
					listErrorFields = new ArrayList<ErrorField>();
					statusMessage = "";
					statusMessageError = "The file you are trying to upload is empty or contains only header";
				}
			}
		}catch(DAOException e){
			statusMessageError = e.getMessage();
		}
		
		return Action.SUCCESS;
	}

	public String downloadExcel(){
		
		try{
			Date now = new Date();
			String dateString = now.toString();
			System.out.println(" 1. " + dateString);
			SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
			ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
			UtilExcel.writtenExcel(importPlanForecastDelegate.getPlanForecast(sitID, iphType)).write(outByteStream);
			
			
			String nombreArchivo = importPlanForecastDelegate.getNameSite(sitID)+"_" + format.format(now);
			
			if (iphType.equals("P"))
				nombreArchivo += "_plan.xlsx";
			else if (iphType.equals("F"))
				nombreArchivo += "_forecast.xlsx";
				
			contentDisposition = "attachment;filename=" + nombreArchivo;
			fileInputStream    = new ByteArrayInputStream(outByteStream.toByteArray()); 
			
		}catch(DAOException e){
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		return Action.SUCCESS;
	}

	
	public String getLastUpload(){
		try{
			
			detailLastUpdate = importPlanForecastDelegate.getLastUpload(sitID, iphType);
		
		}catch(DAOException e){
			e.printStackTrace();
		}
		
		return Action.SUCCESS;
	}

	public String getSitID() {
		return sitID;
	}


	public void setSitID(String sitID) {
		this.sitID = sitID;
	}


	public String getIphType() {
		return iphType;
	}


	public void setIphType(String iphType) {
		this.iphType = iphType;
	}


	public List<SiteVO> getListSiteVO() {
		return listSiteVO;
	}


	public void setListSiteVO(List<SiteVO> listSiteVO) {
		this.listSiteVO = listSiteVO;
	}


	public InputStream getFileInputStream() {
		return fileInputStream;
	}


	public void setFileInputStream(InputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
	}


	public String getContentDisposition() {
		return contentDisposition;
	}


	public void setContentDisposition(String contentDisposition) {
		this.contentDisposition = contentDisposition;
	}


	public List<ErrorField> getListErrorFields() {
		return listErrorFields;
	}


	public void setListErrorFields(List<ErrorField> listErrorFields) {
		this.listErrorFields = listErrorFields;
	}


	public String getDetailLastUpdate() {
		return detailLastUpdate;
	}


	public void setDetailLastUpdate(String detailLastUpdate) {
		this.detailLastUpdate = detailLastUpdate;
	}


	public File getFileUploadP() {
		return fileUploadP;
	}


	public void setFileUploadP(File fileUploadP) {
		this.fileUploadP = fileUploadP;
	}


	public File getFileUploadF() {
		return fileUploadF;
	}


	public void setFileUploadF(File fileUploadF) {
		this.fileUploadF = fileUploadF;
	}


	public File getFile() {
		return file;
	}


	public void setFile(File file) {
		this.file = file;
	}


	public String getStatusMessageError() {
		return statusMessageError;
	}


	public void setStatusMessageError(String statusMessageError) {
		this.statusMessageError = statusMessageError;
	}

	public String getFileUploadPFileName() {
		return fileUploadPFileName;
	}


	public void setFileUploadPFileName(String fileUploadPFileName) {
		this.fileUploadPFileName = fileUploadPFileName;
	}


	public String getFileUploadFFileName() {
		return fileUploadFFileName;
	}


	public void setFileUploadFFileName(String fileUploadFFileName) {
		this.fileUploadFFileName = fileUploadFFileName;
	}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public String getStatusMessage() {
		return statusMessage;
	}


	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	

}