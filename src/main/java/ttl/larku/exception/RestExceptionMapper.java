package ttl.larku.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import ttl.larku.controllers.resty.RestErrorObject;


@Provider
public class RestExceptionMapper implements ExceptionMapper<LarkUException>{


    public Response toResponse(LarkUException e){
    	//System.out.println("Got LarkUException in RestExceptionMapper");
    	//e.printStackTrace();
    	RestErrorObject reo = new RestErrorObject("Awful stuff happened", e.toString(),
        				RestErrorObject.ErrorType.HIS_ERROR);
        return Response.status(Response.Status.BAD_REQUEST).
        		entity(reo).build();
    }
}
