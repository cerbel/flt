package cl.cstit.msd.ccs.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


import org.apache.log4j.Logger;

import cl.cstit.msd.ccs.exception.DAOException;
import cl.cstit.msd.ccs.utils.ConnectionFactory;
import cl.cstit.msd.ccs.utils.ConstantsFinalObject;
import cl.cstit.msd.ccs.utils.FormatUtilities;
import cl.cstit.msd.ccs.vo.NotificationVO;

public class NotificationApproveProcessDAOImp{
	
	
	private ConnectionFactory dataConnection = null;
	private static Logger logger = null;
	
	public NotificationApproveProcessDAOImp(){
		dataConnection = ConnectionFactory.getInstance();	
		logger = Logger.getLogger(NotificationApproveProcessDAOImp.class);
	}
	
    public NotificationVO notificationContentStage(String idStage, String isidUserTo, String isidUserFrom) throws DAOException{
    	
    	Connection conn = null;
    	
    	NotificationVO notificationVO = new NotificationVO();
    	try {

    		conn = dataConnection.getConnectionOracleMSD();
			String queryExecute = 	"SELECT STG.STG_ID, STG.SGT_NAME,  USR.USR_USERNAME, STS.STS_NAME, STG.SIT_ID, SIT.SIT_NAME " +
									"FROM CCS_STAGE STG, CCS_USERS USR, CCS_STAGE_STATE STS, CCS_SITE SIT " +
									"WHERE STG.USR_ISID = USR.USR_ISID " +
									"AND   STG.STS_ID   = STS.STS_ID " +
									"AND   STG.SIT_ID   = SIT.SIT_ID " +
									"AND   STG.STG_ID   = "+idStage+"";
			
			logger.debug("queryExecute: "+queryExecute);
			
			ResultSet rs = conn.createStatement().executeQuery(queryExecute);
			
			if (rs.next()){
				notificationVO.setIdStageNotification(rs.getString(1));
				notificationVO.setNameStageNotification(rs.getString(2));
				notificationVO.setStatusStageNotification(rs.getString(4));
				notificationVO.setDateCurrentNotification(FormatUtilities.getCurrentDate("EEE MMM dd HH:mm:ss yyyy"));
				
				notificationVO.setSiteStageNotification(rs.getString(6));
				

				
				queryExecute = 	"SELECT USR_USERNAME FROM CCS_USERS WHERE USR_ISID = '"+isidUserFrom+"'";
				ResultSet rsUserFrom = conn.createStatement().executeQuery(queryExecute);
				if(rsUserFrom.next()){
					notificationVO.setNameFromUserNotification(rsUserFrom.getString(1));
				}
				
				
				queryExecute = 	"SELECT USR_USERNAME FROM CCS_USERS WHERE USR_ISID = '"+isidUserTo+"'";
				ResultSet rsUserTo = conn.createStatement().executeQuery(queryExecute);
				if(rsUserTo.next()){
					notificationVO.setNameToUserNotification(rsUserTo.getString(1).toUpperCase());
				}
				

				
				
				queryExecute = 	"SELECT STC_COMMENT, STC_REGISTER_DATE " +
								"FROM CCS_STAGE_COMMENT " +
								"WHERE STG_ID = "+idStage+" " +
								"AND ROWNUM   = 1 " +
								"ORDER BY STC_ID DESC";
				
				ResultSet rsComment = conn.createStatement().executeQuery(queryExecute);
				if(rsComment.next()){
					notificationVO.setCommentNotificacion(rsComment.getString(1));
				}
			}
    	}catch (SQLException e) {
    		try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			throw new DAOException(ConstantsFinalObject.EXCEPTION_DAO_GENERIC_ERR_COD, e);		
		}finally{		
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					throw new DAOException(ConstantsFinalObject.EXCEPTION_DAO_GENERIC_ERR_COD, e);
				}
		}
    	
    	return notificationVO;
    }	
}