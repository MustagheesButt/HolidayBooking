package holidayBooking.beans;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

@Stateless
public class MessageReceiver implements MessageListener {
  @Resource(lookup = "java:jboss/DefaultJMSConnectionFactory")
  private ConnectionFactory connectionFactory;

  @Resource(lookup = "java:/jms/myQueue")
  private Queue myQueue;

  public List<String> receiveMessage() {
    List<String> tms = new ArrayList<String>();

    try {
      Connection connection = null;
      InitialContext ctx = new InitialContext();

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
      QueueBrowser queueBrowser = session.createBrowser(myQueue);
      Enumeration enumeration = queueBrowser.getEnumeration();
      while (enumeration.hasMoreElements()) {
        TextMessage tm = (TextMessage) enumeration.nextElement();
        tms.add(tm.getText());
      }

      session.close();
      connection.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return tms;
  }

  @Override
  public void onMessage(Message message) {
    // TODO Auto-generated method stub
    System.out.println("RECEIVED MESSAGE");
    System.out.println(message);
  }
}