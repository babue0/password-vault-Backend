package com.br.passwordvault.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Entity
@Table(name="users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  @NotBlank
  private String username;

  @Column(nullable = false)
  @NotBlank
  private String masterPassword;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Credential> credentials;

  public User(){
  }

  public User(String username, String masterPassword) {
    this.username = username;
    this.masterPassword = masterPassword;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getMasterPassword() {
    return masterPassword;
  }

  public void setMasterPassword(String masterPassword) {
    this.masterPassword = masterPassword;
  }

  public List<Credential> getCredentials() {
    return credentials;
  }

  public void setCredentials(List<Credential> credentials) {
    this.credentials = credentials;
  }
}
