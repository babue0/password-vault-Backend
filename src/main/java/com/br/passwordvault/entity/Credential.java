package com.br.passwordvault.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;



@Entity
@Table(name = "credentials")
public class Credential {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  private String serviceName;

  @NotBlank
  private String username;

  @NotBlank
  private String password;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  public Credential() {}


  public Credential(String serviceName, String username, String password, User user) {
    this.serviceName = serviceName;
    this.username = username;
    this.password = password;
    this.user = user;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getServiceName() {
    return serviceName;
  }

  public void setServiceName(String serviceName) {
    this.serviceName = serviceName;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @Override
  public String toString() {
    return "Service: " + serviceName + " | User: " + username + " | Pass: " + password;
  }
}
