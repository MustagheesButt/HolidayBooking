package holidayBooking.services;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import holidayBooking.models.Employee;
import holidayBooking.models.HolidayRequest;

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
  
  public void delete(Employee e) {
	  entityManager.createQuery("Delete From HolidayRequest hr WHERE hr.employee = ?1")
		 .setParameter(1,e)
		 .executeUpdate();
	 entityManager.createQuery("Delete From Employee e WHERE e.id = ?1")
	 .setParameter(1,e.getId())
	 .executeUpdate();
  }
  public void update(Employee e) {
	    entityManager.merge(e);
	  }
}
