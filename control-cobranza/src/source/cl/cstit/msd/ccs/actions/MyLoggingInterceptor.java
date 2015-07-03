package cl.cstit.msd.ccs.actions;
 

import cl.cstit.msd.ccs.vo.UserVO;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
 
public class MyLoggingInterceptor implements Interceptor{
 
    private static final long serialVersionUID = 1L;
 
    public String intercept(ActionInvocation invocation) throws Exception { 
    	
 
        final ActionContext context = invocation.getInvocationContext();
        
        UserVO userSessionVO = (UserVO) context.getSession().get("userSessionVO");
        
//        if (userSessionVO == null){
//        	String methodName = invocation.getProxy().getMethod();
//        	if (!methodName.equals("notificationToApproveForecast") && !methodName.equals("notificationApproveForecast") && !methodName.equals("notificationRejectForecast") 
//        			&& !methodName.equals("notificationToApproveBudget") && !methodName.equals("notificationApproveBudget") && !methodName.equals("notificationRejectBudget"))
//        		return "login";
//        	
//        }
        
        return invocation.invoke();
    }
 
    public void destroy() {
        System.out.println("Destroying MyLoggingInterceptor...");
    }
    public void init() {
        System.out.println("Initializing MyLoggingInterceptor...");
    } 


}