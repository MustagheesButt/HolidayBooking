package holidayBooking.services;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import holidayBooking.models.Employee;

@ApplicationScoped
@Transactional
public class EmployeeService {
  @PersistenceContext
  private EntityManager entityManager;

  public Employee findUser(Long id) {
    return entityManager.find(Employee.class, id);
  }

  public Employee findByEmail(String email) {
    try {
      return entityManager.createQuery("SELECT e FROM Employee e WHERE email = ?1", Employee.class)
        .setParameter(1, email)
        .getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }

  public List<Employee> getAll() {
    return entityManager.createQuery("SELECT e FROM Employee e", Employee.class).getResultList();
  }
}
