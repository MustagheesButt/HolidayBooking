package holidayBooking.beans;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

@Stateless
public class MessageSender {
  @Resource(mappedName = "java:/ConnectionFactory")
  private ConnectionFactory connectionFactory;

  @Resource(mappedName = "java:/jms/myQueue")
  private Queue myQueue;

  public void sendMessage(String message) {
    MessageProducer mProducer;
    TextMessage textMessage;
    
    try {
      InitialContext ctx = new InitialContext();
      Connection connection = null;

      if (connectionFactory != null) {
        connection = connectionFactory.createConnection();
      } else {
        ConnectionFactory factory = (ConnectionFactory) ctx.lookup("java:jboss/DefaultJMSConnectionFactory");
        connection = factory.createConnection();
      }

      if (myQueue == null) {
        myQueue = (Queue)ctx.lookup("java:/jms/myQueue");
      }

      Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
      mProducer = session.createProducer(myQueue);
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