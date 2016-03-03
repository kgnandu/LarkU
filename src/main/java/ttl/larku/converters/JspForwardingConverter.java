package ttl.larku.converters;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.apache.cxf.jaxrs.ext.MessageContext;
import org.apache.cxf.transport.http.AbstractHTTPDestination;

//import org.apache.cxf.jaxrs.ext.MessageContext;
//import org.apache.cxf.transport.http.AbstractHTTPDestination;

/**
 * A "fake" writer that we use to forward the request to a JSP
 * WARNING - this CXF specific stuff, viz 
			context.put(AbstractHTTPDestination.REQUEST_REDIRECTED,
					Boolean.TRUE);
   to indicate that we have dealt with the response
 * @author whynot
 *
 */
@Provider
@Produces("text/html")
public class JspForwardingConverter implements MessageBodyWriter<Object> {

	private String htmlMimeType = "text/html";

	@Context
	private MessageContext context;

	@Override
	public boolean isWriteable(Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		boolean rv = htmlMimeType.equalsIgnoreCase(mediaType.getType()
				+ "/" + mediaType.getSubtype());
		return rv;
	}

	@Override
	public long getSize(Object t, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {

		return -1;
	}

	@Override
	public void writeTo(Object t, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders,
			OutputStream entityStream) throws IOException,
			WebApplicationException {

		HttpServletRequest request = context.getHttpServletRequest();
		HttpServletResponse response = context.getHttpServletResponse();
		// get the url to forward to
		String url = (String) request.getAttribute("url");

		try {
			//Add this here to tell the powers that be that we have
			//handled the output.  This is to avoid the 
			//"getWriter already called Exception
			context.put(AbstractHTTPDestination.REQUEST_REDIRECTED,
					Boolean.TRUE);
			request.getRequestDispatcher(url).forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
}