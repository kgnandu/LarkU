package ttl.larku.exception;

import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import ttl.larku.controllers.resty.RestErrorObject;


@Provider
@Produces({"application/xml", "application/json"})
public class RuntimeExceptionMapper implements ExceptionMapper<RuntimeException>{

    public Response toResponse(RuntimeException e){
    	System.out.println("Got RuntimeException in RuntimeException Mapper");
    	e.printStackTrace();
    	String resp = "Unexpected RuntimeException: " + e.getMessage();
    	/*
    	RestErrorObject resp = new RestErrorObject("Awful stuff happened", e.toString(),
        				RestErrorObject.ErrorType.HIS_ERROR);
        				*/
        return Response.status(Response.Status.BAD_REQUEST).build();
        		//entity(resp).build();
    }
}
