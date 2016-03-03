package ttl.larku.converters;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;

import ttl.larku.domain.Student;

public class MyConverter implements MessageBodyWriter<Student>{

	@Override
	public long getSize(Student t, Class<?> rawType, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		return 0;
	}

	@Override
	public boolean isWriteable(Class<?> rawType, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		return false;
	}

	@Override
	public void writeTo(Student t, Class<?> rawType, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders,
			OutputStream entityStream) throws IOException {
		
	}

}
