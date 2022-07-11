package holidaysManager.services;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import holidaysManager.entities.Emp;

@ApplicationScoped
@Transactional
public class EmpService {
  @PersistenceContext
  private EntityManager entityManager;

  public Emp find(Long id) {
    return entityManager.find(Emp.class, id);
  }

  public Emp findByEmail(String email) {
    try {
      return entityManager.createQuery("SELECT e FROM Emp e WHERE email = ?1", Emp.class)
          .setParameter(1, email)
          .getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }

  public List<Emp> getAll() {
    return entityManager.createQuery("SELECT e FROM Emp e", Emp.class).getResultList();
  }

  public void delete(Emp e) {
    entityManager.createQuery("Delete From HRequest hr WHERE hr.emp = ?1")
        .setParameter(1, e)
        .executeUpdate();
    entityManager.createQuery("Delete From Emp e WHERE e.id = ?1")
        .setParameter(1, e.getId())
        .executeUpdate();
  }

  public void update(Emp e) {
    entityManager.merge(e);
  }

  public boolean persist(Emp e) {
    try {
      entityManager.persist(e);
    } catch (Exception ex) {
      return false;
    }

    return true;
  }
}
