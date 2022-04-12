package holidayBooking.services;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import holidayBooking.models.Employee;
import holidayBooking.models.HolidayRequest;

@ApplicationScoped
@Transactional
public class HolidayRequestService {
  @PersistenceContext
  private EntityManager entityManager;

  public HolidayRequest find(Long id) {
    return entityManager.find(HolidayRequest.class, id);
  }

  public List<HolidayRequest> findAllByEmployee(Employee employee) {
    try {
      return entityManager.createQuery("SELECT h FROM HolidayRequest h WHERE h.employee = ?1", HolidayRequest.class)
          .setParameter(1, employee)
          .getResultList();
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return null;
    }
  }

  public List<HolidayRequest> getAll() {
    return entityManager.createQuery("SELECT h FROM HolidayRequest h", HolidayRequest.class).getResultList();
  }

  public List<HolidayRequest> getApproved() {
    return entityManager.createQuery("SELECT h FROM HolidayRequest h WHERE h.status = 'approved'", HolidayRequest.class).getResultList();
  }

  public List<HolidayRequest> getPending() {
    return entityManager.createQuery("SELECT h FROM HolidayRequest h WHERE h.status = 'pending'", HolidayRequest.class).getResultList();
  }

  public void persist(HolidayRequest hr) {
    entityManager.persist(hr);
  }

  public void update(HolidayRequest hr) {
    entityManager.merge(hr);
  }
}
