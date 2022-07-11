package holidaysManager.services;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import holidaysManager.entities.Emp;
import holidaysManager.entities.HRequest;
import holidaysManager.services.HReqService;

@ApplicationScoped
@Transactional
public class HReqService {
  @PersistenceContext
  private EntityManager em;

  public HRequest find(Long id) {
    return em.find(HRequest.class, id);
  }

  public List<HRequest> findAllByEmployee(Emp employee) {
    try {
      return em
        .createQuery("SELECT h FROM HRequest h WHERE h.emp = ?1", HRequest.class)
        .setParameter(1, employee)
        .getResultList();
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return null;
    }
  }

  public List<HRequest> getAll() {
    return em
      .createQuery("SELECT h FROM HRequest h", HRequest.class)
      .getResultList();
  }

  public List<HRequest> getApproved() {
    return em
      .createQuery("SELECT h FROM HRequest h WHERE h.status = 'approved'", HRequest.class)
      .getResultList();
  }

  public List<HRequest> getPending() {
    return em
      .createQuery("SELECT h FROM HRequest h WHERE h.status = 'pending'", HRequest.class)
      .getResultList();
  }

  public void persist(HRequest hr) {
    em.persist(hr);
  }

  public void update(HRequest hr) {
    em.merge(hr);
  }
}
