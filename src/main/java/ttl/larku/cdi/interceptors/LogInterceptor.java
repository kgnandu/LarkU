package ttl.larku.cdi.interceptors;

import java.util.Date;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Interceptor
@Logged
public class LogInterceptor {
    
    @AroundInvoke
    public Object logCall(InvocationContext ctx) {
	Object result = null;
	try {
	    result = ctx.proceed();

	    System.out.println(ctx.getMethod().getName() + " finished at " + new Date() +" with result " + result);
	    Object [] params = ctx.getParameters();
	    for(Object param : params) {
	    	System.out.println("arg: " + param);
	    }
	    
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return result;
    }

}
