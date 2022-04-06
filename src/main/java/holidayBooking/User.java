package holidayBooking;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String firstName;

  private String lastName;

  private String email;

  private String type; // employee or admin

  private String password;
  
  public Long getId() {
    return this.id;
  }
  public String getFirstName() {
    return this.firstName;
  }
  public String getLastName() {
    return this.lastName;
  }
  public String getEmail() {
    return this.email;
  }
  public String getType() {
    return this.type;
  }
  public String getPassword() {
    return this.password;
  }

  public void setId(Long id) {
    this.id = id;
  }
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public void setType(String type) {
    this.type = type;
  }
  public void setPassword(String password) {
    this.password = password;
  }
}
