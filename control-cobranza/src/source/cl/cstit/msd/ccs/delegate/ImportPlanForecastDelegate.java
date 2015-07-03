package cl.cstit.msd.ccs.delegate;

import java.util.List;

import cl.cstit.msd.ccs.dao.ImportPlanForecastDAOImp;
import cl.cstit.msd.ccs.exception.DAOException;
import cl.cstit.msd.ccs.vo.ErrorField;
import cl.cstit.msd.ccs.vo.FieldExcel;

public class ImportPlanForecastDelegate{
 
	ImportPlanForecastDAOImp importPlanForecastDAOImp = new ImportPlanForecastDAOImp();
	
    public List<ErrorField> savePlanForecast(String sitID, String usrIsid, String iphType, List<FieldExcel> listFieldExcel) throws DAOException{
    	return importPlanForecastDAOImp.savePlanForecast(sitID, usrIsid, iphType, listFieldExcel);
	}
    
    public List<FieldExcel> getPlanForecast(String sitID, String iphType) throws DAOException{
    	return importPlanForecastDAOImp.getPlanForecast(sitID, iphType);
    }
    	 
    public String getLastUpload(String sitID , String type) throws DAOException{
    	return importPlanForecastDAOImp.getLastUpload(sitID, type);
    }
    
    public String getNameSite(String sitID) throws DAOException{
    	return importPlanForecastDAOImp.getNameSite(sitID);
    }
}