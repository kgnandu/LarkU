package ttl.larku.controllers.resty;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import ttl.larku.domain.Student;
import ttl.larku.rest.app.LarkURestApp;
import ttl.larku.service.StudentService;

@RunWith(Arquillian.class)
public class StudentControllerITTestCXF {

	@ArquillianResource
	private URL serviceURL;

	@Deployment
	public static WebArchive createDeployment() {
		WebArchive jar = ShrinkWrap
				.create(WebArchive.class)
				//.addClass(LarkURestApp.class)
				//.addClass(StudentService.class)
				//.addClass(SomeController.class)
				.addPackages(true, "ttl")
				.addAsLibrary(new File("lib/jackson-annotations-2.5.4.jar"))
				.addAsLibrary(new File("lib/jackson-core-2.5.4.jar"))
				.addAsLibrary(new File("lib/jackson-databind-2.5.4.jar"))
				.addAsLibrary(new File("lib/jackson-jaxrs-base-2.5.4.jar"))
				.addAsLibrary(new File("lib/jackson-jaxrs-json-provider-2.5.4.jar"))
				.addAsWebInfResource(
						new File("src/main/java/META-INF/beans.xml"))
				.addAsWebInfResource(
						new File("src/main/java/META-INF/persistence.xml"))
				.addAsWebInfResource(
						new File("src/main/webapp/WEB-INF/openejb-jar.xml"))
				.addAsWebInfResource(
						new File("src/main/webapp/WEB-INF/resources.xml"));

		System.out.println("createDeployment: " + jar.toString(true));
		return jar;
	}

	@Test
	public void testgetStudentRawGoodId() throws Throwable {
		try {
			String port = System.getProperty("http.port");
			String sURL = serviceURL.toString();
			System.out.println("testgetStudentRawGoodId: " + "serviceURL is " + serviceURL);
			System.out.println("testgetStudentRawGoodId: " + "sURL is " + sURL);
			int idToTest = 1;
			WebClient webClient = WebClient.create(sURL).path("/registration/v1/students");
			webClient.accept(MediaType.APPLICATION_JSON);
			Response response = webClient.path("{id}", idToTest).get();
			System.out.println("testgetStudentRawGoodId: " + "Response is " + response);

			assertEquals(200, response.getStatus());

			MultivaluedMap<String, Object> props = response.getMetadata();
			System.out.println("testgetStudentRawGoodId: " + "props are " + props);

			System.out.println("testgetStudentRawGoodId: " + "InputStream contents: ");
			InputStream is = (InputStream) response.getEntity();
			int i;
			StringWriter swriter = new StringWriter();
			while ((i = is.read()) != -1) {
				System.out.print("testgetStudentRawGoodId: " + (char) i);
				swriter.write((char) i);
			}
			System.out.println("testgetStudentRawGoodId: " + "end of stream");

            String jsonString = swriter.toString();
            System.out.println("testgetStudentRawGoodId: " + "json is " + jsonString);
            
            ObjectMapper mapper = new ObjectMapper();
    		Student student = mapper.readValue(jsonString, Student.class);
    		System.out.println("testgetStudentRawGoodId: " + "student is " + student);
			assertEquals("Manoj", student.getName());

		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Test
	public void testgetStudentRawBadId() throws Throwable {
		try {
			String port = System.getProperty("http.port");
			String sURL = serviceURL.toString();
			System.out.println("testGetStudentRawLowId: " + "serviceURL is " + serviceURL);
			System.out.println("testGetStudentRawLowId: " + "sURL is " + sURL);
			int idToTest = 999999;
			WebClient webClient = WebClient.create(sURL);
			webClient.accept(MediaType.APPLICATION_JSON);
			Response response = webClient.path("registration/v1/students/{id}", idToTest).get();
			System.out.println("testGetStudentRawLowId: " + "Response is " + response);
			assertEquals(400, response.getStatus());

		} catch (Throwable e) {
			System.out.println("In testgetStudentRawLowId got exception " + e.getMessage());
			throw e;
		}
	}

	@Test
	public void testgetStudentConvertedGoodId() throws Throwable {
		try {
			String port = System.getProperty("http.port");
			String sURL = serviceURL.toString();
			System.out.println("testGetStudentRawLowId: " + "serviceURL is " + serviceURL);
			System.out.println("testGetStudentRawLowId: " + "sURL is " + sURL);

			List<Object> providers = new ArrayList<Object>();
			providers.add(new JacksonJsonProvider());

			int idToTest = 1;
			WebClient webClient = WebClient.create(sURL, providers);
			webClient.accept(MediaType.APPLICATION_JSON);
			Student student = webClient.path("registration/v1/students/{id}", idToTest).get(
					Student.class);
			System.out.println("testGetStudentRawLowId: " + "student is " + student);
			assertEquals("Manoj", student.getName());
		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Test(expected=WebApplicationException.class)
	public void testgetStudentConvertedBadId() throws Throwable {
		try {
			String port = System.getProperty("http.port");
			String sURL = serviceURL.toString();
			System.out.println("testgetStudentConvertedLowId: " + "serviceURL is " + serviceURL);
			System.out.println("testgetStudentConvertedLowId: " + "sURL is " + sURL);

			List<Object> providers = new ArrayList<Object>();
			providers.add(new JacksonJsonProvider());

			int idToTest = 99999;
			WebClient webClient = WebClient.create(sURL, providers);
			webClient.accept(MediaType.APPLICATION_JSON);
			Student student = webClient.path("registration/v1//students/{id}", idToTest).get(
					Student.class);
			System.out.println("student is " + student);
			assertEquals("Roberta", student.getName());
		} catch (Throwable e) {
			System.out.println("ConvertedBadId got Exception: " + e.getMessage());
			throw e;
		}
	}
	
	@Test
	public void testWithJaxRS2_0() {

		String port = System.getProperty("http.port");
		String sURL = serviceURL.toString() + "/registration/v1/students/{id}";
		System.out.println("sURL is " + sURL);

		ClientBuilder builder = ClientBuilder.newBuilder();
        builder.register(JacksonJsonProvider.class);
        
        Client client = builder.build();

        WebTarget studentResource = client.target(sURL);
        
        int idToTest = 1;
        WebTarget wt = studentResource.resolveTemplate("id", idToTest);

        Response response = wt.request(MediaType.APPLICATION_JSON).get();
        
        System.out.println(response);
        
        System.out.println("Entity is " + response.getEntity());
        
        Student c = response.readEntity(Student.class);
        
        System.out.println("student is " + c);
	}
}

