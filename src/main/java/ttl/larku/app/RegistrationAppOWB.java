package ttl.larku.app;

import java.util.List;

import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;

import org.apache.webbeans.config.WebBeansContext;
import org.apache.webbeans.spi.ContainerLifecycle;

import ttl.larku.domain.ScheduledClass;
import ttl.larku.domain.Student;
import ttl.larku.service.RegistrationService;
import ttl.larku.service.StudentService;

public class RegistrationAppOWB {
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		boot(null);
		BeanManager beanManager = lifecycle.getBeanManager();
		Bean<?> bean = beanManager.getBeans(RegistrationService.class).iterator().next();

		//RegistrationService registrationService = (RegistrationService) lifecycle.getBeanManager().getReference(bean, RegistrationService.class, beanManager.createCreationalContext(bean));
		RegistrationService registrationService = 
				(RegistrationService)beanManager.getReference(bean, 
						RegistrationService.class, 
						beanManager.createCreationalContext(bean));
		

		StudentService studentService = registrationService.getStudentService();
		System.out.println("ss is " + studentService);

		// Interceptor
		Object result = studentService.getStudent(1);

		// Created Event
		Student student = new Student("Vivek");
		student = studentService.createStudent(student);
		System.out.println("new student = " + student);

		registrationService.addNewClassToSchedule("Math-101", "10/10/14", "10/10/15");
		List<ScheduledClass> classes = registrationService.getClassService().getAllScheduledClasses();
		for (ScheduledClass sc : classes) {
			System.out.println("Class: " + sc);
		}

		registrationService.registerStudentForClass(1, "Math101", "10/10/14");

		List<Student> students = registrationService.getStudentsForClass("Math101", "10/10/14");
		for (Student s : students) {
			System.out.println("Student: " + s);
		}

	}

	private static ContainerLifecycle lifecycle = null;


	private static void boot(Object startupObject) throws Exception {
		try {
			lifecycle = WebBeansContext.getInstance().getService(ContainerLifecycle.class);
			lifecycle.startApplication(startupObject);

		} catch (Exception e) {
			throw e;
		}
	}

	private static void shutdown(Object endObject) throws Exception {
		try {
			lifecycle.stopApplication(endObject);

		} catch (Exception e) {
			throw e;
		}

	}

}
