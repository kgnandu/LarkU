package ttl.larku.service.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

public class JMSSender {

	public static void sendMessage(ConnectionFactory conFactory,
			Destination destination, String payload) {

		try {
			Connection connection = conFactory.createConnection();
			Session session = connection.createSession(true,
					Session.AUTO_ACKNOWLEDGE);
			MessageProducer producer = session.createProducer(destination);
			TextMessage message = session.createTextMessage();
			message.setText(payload);
			producer.send(message);
			System.out.println("Message sent: " + payload);
			session.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void sendMessage(ConnectionFactory conFactory,
			Destination destination, String payload, String propName,
			boolean propValue) {

		try {
			Connection connection = conFactory.createConnection();
			Session session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);
			MessageProducer producer = session.createProducer(destination);
			TextMessage message = session.createTextMessage();
			message.setBooleanProperty(propName, propValue);
			message.setText(payload);
			producer.send(message);
			System.out.println("Message sent: " + payload);
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
