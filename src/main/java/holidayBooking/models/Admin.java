package holidayBooking.models;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "admins")
public class Admin implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String firstName;

  private String lastName;

  private String email;

  private String password;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;
  
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
  public String getPassword() {
    return this.password;
  }
  public LocalDateTime getCreatedAt() {
    return this.createdAt;
  }
  public LocalDateTime getUpdatedAt() {
    return this.updatedAt;
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
  public void setPassword(String password) {
    this.password = password;
  }
  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }
  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }
}
