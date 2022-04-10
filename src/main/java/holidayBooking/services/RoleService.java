package holidayBooking.services;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import holidayBooking.models.Employee;
import holidayBooking.models.HolidayRequest;
import holidayBooking.models.Role;

@ApplicationScoped
@Transactional
public class RoleService {
  @PersistenceContext
  private EntityManager entityManager;

  public Role findRole(long id) {
    return entityManager.find(Role.class, id);
  }
  public List<Role> getAll() {
	    return entityManager.createQuery("SELECT r FROM Role r", Role.class).getResultList();
	  }
}
