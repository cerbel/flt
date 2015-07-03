package cl.cstit.msd.ccs.delegate;


import cl.cstit.msd.ccs.dao.NotificationApproveProcessDAOImp;
import cl.cstit.msd.ccs.exception.DAOException;
import cl.cstit.msd.ccs.vo.NotificationVO;

public class NotificationApproveProcessDelegate{

    public NotificationVO notificationContentStage(String idStage, String isidUser, String isidUserFrom) throws DAOException{
    	return new NotificationApproveProcessDAOImp().notificationContentStage(idStage, isidUser, isidUserFrom);
    }

}