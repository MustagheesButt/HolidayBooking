package holidayBooking.services;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import holidayBooking.models.Admin;
import holidayBooking.models.Notification;

@ApplicationScoped
@Transactional
public class AdminService {
  @PersistenceContext
  private EntityManager entityManager;

  public Admin findAdmin(Long id) {
    return entityManager.find(Admin.class, id);
  }

  public Admin findByEmail(String email) {
    try {
      return entityManager.createQuery("SELECT a FROM Admin a WHERE email = ?1", Admin.class)
              .setParameter(1, email)
              .getSingleResult();
    } catch (Exception e) {
      return null;
    }
  }

  public List<Admin> getAll() {
    return entityManager.createQuery("SELECT a FROM Admin a", Admin.class).getResultList();
  }

  // save notification for each admin
  public void sendNotification(Notification notification) {
    this.getAll().forEach(admin -> {
      notification.setSendTo(admin.getEmail());
      notification.setMsgStatus("unread");
      entityManager.persist(notification);
    });
  }
}
