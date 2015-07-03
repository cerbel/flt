package cl.cstit.msd.ccs.delegate;

import java.util.List;

import cl.cstit.msd.ccs.dao.AdminUserDAOImp;
import cl.cstit.msd.ccs.exception.DAOException;
import cl.cstit.msd.ccs.vo.PagoCobranzaVO;
import cl.cstit.msd.ccs.vo.ProfileVO;
import cl.cstit.msd.ccs.vo.ProveedorVO;
import cl.cstit.msd.ccs.vo.RowCountVO;
import cl.cstit.msd.ccs.vo.SiteVO;
import cl.cstit.msd.ccs.vo.UserVO;

public class AdminUserDelegate{
 
	AdminUserDAOImp adminUserDAOImp = new AdminUserDAOImp();

   public void saveUser(PagoCobranzaVO pagoCobranzaVO) throws DAOException{
	   adminUserDAOImp.saveUser(pagoCobranzaVO);
   }
   
//   public void saveUser(UserVO userVO) throws DAOException{
//	   adminUserDAOImp.saveUser(userVO);
//   }
    
   public void deleteUser(UserVO userVO) throws DAOException{
	   adminUserDAOImp.deleteUser(userVO);
   }
   
   public List<UserVO> listUser(UserVO usrVO, RowCountVO rowCountVO, int numberPage) throws DAOException{
	   return adminUserDAOImp.listUser(usrVO, rowCountVO, numberPage);
   }
   
   public String obtieneProveedor(String rutProveedor)throws DAOException{
	   return adminUserDAOImp.obtieneProveedor(rutProveedor);
   }
   //modificar
   public List<ProveedorVO> obtieneRutProveedor(String nombreProveedor)throws DAOException{
	   return adminUserDAOImp.obtieneRutProveedor(nombreProveedor);
   }
//   public String obtieneRutProveedor(String nombreProveedor)throws DAOException{
//	   return adminUserDAOImp.obtieneRutProveedor(nombreProveedor);
//   }

   public void updateUser(UserVO userVO) throws DAOException{
	   adminUserDAOImp.updateUser(userVO);
   }
   
   public List<ProfileVO> listProfile()throws DAOException{
	   return adminUserDAOImp.listProfile();
   }

   public List<SiteVO> listSite() throws DAOException{
	   return adminUserDAOImp.listSite();
   }
   
	public List<SiteVO> listSiteByUser(String isidUser) throws DAOException{
		return adminUserDAOImp.listSiteByUser(isidUser);
	}
}