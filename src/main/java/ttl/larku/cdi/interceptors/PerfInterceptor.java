package ttl.larku.cdi.interceptors;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Interceptor
@Perf
public class PerfInterceptor {
    
    @AroundInvoke
    public Object logCall(InvocationContext ctx) {
	Object result = null;
	try {
		Instant start = Instant.now();
			
	    result = ctx.proceed();

	    Instant end = Instant.now();
	    System.out.println(ctx.getTarget().getClass().getSimpleName() + "." + ctx.getMethod().getName() + " finished at " + LocalDateTime.now() + " took " + start.until(end, ChronoUnit.MILLIS));
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return result;
    }

}
