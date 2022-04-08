package holidayBooking.services;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import holidayBooking.models.Department;

@ApplicationScoped
@Transactional
public class DepartmentService {
  @PersistenceContext
  private EntityManager entityManager;

  public Department find(Long id) {
    return entityManager.find(Department.class, id);
  }

  public List<Department> getAll() {
    return entityManager.createQuery("SELECT d FROM Department d", Department.class).getResultList();
  }
}
