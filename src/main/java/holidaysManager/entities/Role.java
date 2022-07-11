package holidaysManager.entities;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table
public class Role implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;
  
  public Long getId() {
    return this.id;
  }
  public String getTitle() {
    return this.title;
  }

  public void setId(Long id) {
    this.id = id;
  }
  public void setTitle(String title) {
    this.title = title;
  }

  public String toString() {
    return this.title;
  }
}
