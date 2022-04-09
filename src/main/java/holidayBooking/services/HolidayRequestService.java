package holidayBooking.services;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import holidayBooking.models.HolidayRequest;

@ApplicationScoped
@Transactional
public class HolidayRequestService {
  @PersistenceContext
  private EntityManager entityManager;

  public HolidayRequest find(Long id) {
    return entityManager.find(HolidayRequest.class, id);
  }

  public List<HolidayRequest> getAll() {
    return entityManager.createQuery("SELECT h FROM HolidayRequest h", HolidayRequest.class).getResultList();
  }

  public void persist(HolidayRequest hr) {
    entityManager.persist(hr);
  }
}
