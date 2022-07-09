package holidaysManager.helpers;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.TextMessage;

import holidaysManager.entities.Notification;
import holidaysManager.services.AdminService;

@MessageDriven(activationConfig = {
  @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:/jms/holidayQueue"),
  @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class MessageReceiver implements MessageListener {
  @Resource(mappedName = "java:jboss/DefaultJMSConnectionFactory")
  private ConnectionFactory connectionFactory;

  @Resource(mappedName = "java:/jms/holidayQueue")
  private Queue holidayQueue;

  public MessageReceiver() {
  }

  @Inject
  AdminService adminService;

  // this gets automatically called when there is a message in the JMS queue
  @Override
  public void onMessage(Message message) {
    try {
      String msg = ((TextMessage)message).getText();

      Notification n = new Notification();
      n.setMessage(msg);

      // send notification to all admins
      adminService.sendNotification(n);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}