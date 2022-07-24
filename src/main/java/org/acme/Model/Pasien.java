package org.acme.Model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "pasien")
public class Pasien extends AuditModel implements Serializable {
  @Id
  @SequenceGenerator(name = "pasienSeq", sequenceName = "pasien_sequence", initialValue = 1, allocationSize = 1)
  @GeneratedValue(generator = "pasienSeq", strategy = GenerationType.SEQUENCE)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "nama_lengkap", nullable = false, length = 50, columnDefinition = "varchar(50)")
  private String namaLengkap;

  @Column(name = "gender", nullable = false, length = 15, columnDefinition = "varchar(15)")
  private String gender;

  @Column(name = "status", nullable = false, length = 15, columnDefinition = "varchar(15)")
  private String status;

  @Column(name = "address", nullable = false, length = 255, columnDefinition = "varchar(255)")
  private String address;

  @Column(name = "email", nullable = false, length = 50, columnDefinition = "varchar(50)", unique = true)
  private String email;

  @Column(name = "phone_number", nullable = false, length = 15, columnDefinition = "varchar(15)")
  private String phoneNumber;

  @Column(name = "is_cover_bpjs", nullable = false, columnDefinition = "boolean")
  private boolean isCoverBpjs;

  public Long getId() {
    return id;
  }

  public String getNamaLengkap() {
    return namaLengkap;
  }

  public void setNamaLengkap(String namaLengkap) {
    this.namaLengkap = namaLengkap;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
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

  public boolean getIsCoverBpjs() {
    return isCoverBpjs;
  }

  public void setIsCoverBpjs(boolean isCoverBpjs) {
    this.isCoverBpjs = isCoverBpjs;
  }

}
