package holidaysManager.services;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import holidaysManager.entities.Role;

@ApplicationScoped
@Transactional
public class RoleService {
  @PersistenceContext
  private EntityManager em;

  public Role findRole(long id) {
    return em.find(Role.class, id);
  }
  public List<Role> getAll() {
	    return em
        .createQuery("SELECT r FROM Role r", Role.class)
        .getResultList();
	  }
}
