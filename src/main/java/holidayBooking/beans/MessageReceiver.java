package holidayBooking.beans;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.TextMessage;

import holidayBooking.models.Notification;
import holidayBooking.services.AdminService;

@MessageDriven(activationConfig = {
  @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:/jms/myQueue"),
  @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class MessageReceiver implements MessageListener {
  @Resource(mappedName = "java:jboss/DefaultJMSConnectionFactory")
  private ConnectionFactory connectionFactory;

  @Resource(mappedName = "java:/jms/myQueue")
  private Queue myQueue;

  public MessageReceiver() {
  }

  @Inject
  AdminService adminService;

  // public List<String> receiveMessage() {
  //   List<String> tms = new ArrayList<String>();

  //   try {
  //     Connection connection = null;
  //     InitialContext ctx = new InitialContext();

  //     if (connectionFactory != null) {
  //       connection = connectionFactory.createConnection();
  //     } else {
  //       ConnectionFactory factory = (ConnectionFactory) ctx.lookup("java:jboss/DefaultJMSConnectionFactory");
  //       connection = factory.createConnection();
  //     }
      
  //     if (myQueue == null) {
  //         myQueue = (Queue)ctx.lookup("java:/jms/myQueue");
  //       }

  //     Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
  //     QueueBrowser queueBrowser = session.createBrowser(myQueue);
  //     Enumeration enumeration = queueBrowser.getEnumeration();
  //     while (enumeration.hasMoreElements()) {
  //       TextMessage tm = (TextMessage) enumeration.nextElement();
  //       tms.add(tm.getText());
  //     }

  //     session.close();
  //     connection.close();
  //   } catch (Exception e) {
  //     e.printStackTrace();
  //   }

  //   return tms;
  // }

  @Override
  public void onMessage(Message message) {
    try {
      String msg = ((TextMessage)message).getText();
      System.out.println("RECEIVED MESSAGE");
      System.out.println(msg);

      Notification n = new Notification();
      n.setMessage(msg);

      adminService.sendNotification(n);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}