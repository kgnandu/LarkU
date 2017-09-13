package ttl.larku.converters;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import ttl.larku.domain.Student;

@Provider
public class StudentToSerialConverter implements MessageBodyReader<Student>,
		MessageBodyWriter<Student> {

	private String javaSerialMimeType = "application/x-java-serialized-object";
	private int studentSize;

	public StudentToSerialConverter() throws IOException {
	}

	@Override
	public boolean isReadable(Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		boolean rv = ((type == Student.class) && javaSerialMimeType
				.equalsIgnoreCase(mediaType.getType() + "/"
						+ mediaType.getSubtype()));
		return rv;
	}

	@Override
	public Student readFrom(Class<Student> type, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
			throws IOException, WebApplicationException {

		ObjectInputStream ois = new ObjectInputStream(entityStream);
		Object object = null;
		try {
			object = ois.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (Student) object;
	}

	@Override
	public boolean isWriteable(Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		boolean rv = ((type == Student.class) && javaSerialMimeType
				.equalsIgnoreCase(mediaType.getType() + "/"
						+ mediaType.getSubtype()));
		return rv;
	}

	@Override
	public long getSize(Student t, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		
		return -1;
	}

	@Override
	public void writeTo(Student t, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders,
			OutputStream entityStream) throws IOException,
			WebApplicationException {
		ObjectOutputStream oos = new ObjectOutputStream(entityStream);
		oos.writeObject(t);
		
	}
}
