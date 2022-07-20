package holidaysManager.helpers;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

public class MessageSender {
  public static void sendMessage(String message, ConnectionFactory connectionFactory, Queue queue) {
    MessageProducer mProducer;
    TextMessage textMessage;
    
    try {
      Connection connection = connectionFactory.createConnection();
      Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
      mProducer = session.createProducer(queue);
      textMessage = session.createTextMessage();

      textMessage.setText(message);
      mProducer.send(textMessage);

      mProducer.close();
      session.close();
      connection.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}