package holidaysManager.services;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import holidaysManager.entities.Dept;

@ApplicationScoped
@Transactional
public class DeptService {
  @PersistenceContext
  private EntityManager em;

  public Dept find(Long id) {
    return em
      .find(Dept.class, id);
  }

  public List<Dept> getAll() {
    return em
      .createQuery("SELECT d FROM Dept d", Dept.class)
      .getResultList();
  }
}
