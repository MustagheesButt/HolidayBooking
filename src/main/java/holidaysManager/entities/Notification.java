package holidaysManager.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Notification implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String sendTo;

  private String message;

  private String msgStatus;

  public Long getId() {
    return this.id;
  }
  public String getSendTo() {
    return this.sendTo;
  }
  public String getMessage() {
    return this.message;
  }
  public String getMsgStatus() {
    return this.msgStatus;
  }

  public void setId(Long id) {
    this.id = id;
  }
  public void setSendTo(String to) {
    this.sendTo = to;
  }
  public void setMessage(String msg) {
    this.message = msg;
  }
  public void setMsgStatus(String status) {
    this.msgStatus = status;
  }

  /* Helpers */
  public String toString() {
    return this.sendTo + ": " + this.message;
  }
}
