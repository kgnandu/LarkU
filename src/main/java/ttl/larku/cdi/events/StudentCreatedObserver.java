package ttl.larku.cdi.events;

import javax.enterprise.event.Observes;

import ttl.larku.domain.Student;

public class StudentCreatedObserver {

    public void studentCreatedEvent(@Observes @Created Student student) { 
    	System.out.println("Observer saw new student " + student);
    }

}
