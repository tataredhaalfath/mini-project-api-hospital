package org.acme.Model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User extends AuditModel implements Serializable {
  @Id
  @SequenceGenerator(name = "userSeq", sequenceName = "user_sequence", initialValue = 1, allocationSize = 1)
  @GeneratedValue(generator = "userSeq", strategy = GenerationType.SEQUENCE)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "name", nullable = false, columnDefinition = "varchar(50)", length = 50)
  private String name;

  @Column(name = "username", unique = true, nullable = false, columnDefinition = "varchar(50)", length = 50)
  private String username;

  @Column(name = "password", nullable = false, columnDefinition = "varchar(50)", length = 50)
  private String password;

  @Column(name = "email", unique = true, nullable = false, columnDefinition = "varchar(50)", length = 50)
  private String email;

  @Column(name = "phone_number", nullable = false, columnDefinition = "varchar(15)", length = 15)
  private String phoneNumber;

  @Column(name = "user_type", nullable = false, columnDefinition = "varchar(15)", length = 15)
  private String userType;

  @ElementCollection
  @CollectionTable(name = "user_permission", joinColumns = @JoinColumn(name = "user_id"))
  @Column(name = "name", nullable = false)
  Set<String> permissionName = new HashSet<>();

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getUserType() {
    return userType;
  }

  public void setUserType(String userType) {
    this.userType = userType;
  }

  public Set<String> getPermissionName() {
    return this.permissionName;
  }

  public void setPermissionName(Set<String> permissionName) {
    this.permissionName = permissionName;
  }

}
