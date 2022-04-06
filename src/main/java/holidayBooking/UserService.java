package holidayBooking;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@ApplicationScoped
@Transactional
public class UserService {
  @PersistenceContext
  private EntityManager entityManager;

  public User findUser(Long id) {
    return entityManager.find(User.class, id);
  }

  public List<User> getAllUsers() {
    return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
  }
}
