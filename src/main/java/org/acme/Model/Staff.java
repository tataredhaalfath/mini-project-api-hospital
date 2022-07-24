package org.acme.Model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.acme.Model.PosisiStaff.Posisi;

@Entity
@Table(name = "staff")
public class Staff extends AuditModel implements Serializable {
  @Id
  @SequenceGenerator(name = "staffSeq", sequenceName = "staff_sequence", initialValue = 1, allocationSize = 1)
  @GeneratedValue(generator = "staffSeq", strategy = GenerationType.SEQUENCE)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "nama_lengkap", nullable = false, length = 50, columnDefinition = "varchar(50)")
  private String namaLengkap;

  @Column(name = "gender", nullable = false, length = 15, columnDefinition = "varchar(15)")
  private String gender;

  // @Column(name = "posisi", nullable = false, length = 25, columnDefinition =
  // "varchar(25)")
  // private String posisi;
  @Enumerated(EnumType.STRING)
  private Posisi posisi;

  @Column(name = "start_time", nullable = false, columnDefinition = "timestamp")
  private LocalDateTime startTime;

  @Column(name = "end_time", nullable = false, columnDefinition = "timestamp")
  private LocalDateTime endTime;

  @Column(name = "gaji", nullable = false, columnDefinition = "bigint")
  private Long gaji;

  @Column(name = "email", unique = true, nullable = false, length = 50, columnDefinition = "varchar(50)")
  private String email;

  @Column(name = "phone_number", nullable = false, length = 15, columnDefinition = "varchar(15)")
  private String phoneNumber;

  @Column(name = "status", nullable = false, length = 15, columnDefinition = "varchar(15)")
  private String status;

  public void setId(Long id) {
    this.id = id;
  }

  public Posisi getPosisi() {
    return this.posisi;
  }

  public void setPosisi(Posisi posisi) {
    this.posisi = posisi;
  }

  public Long getId() {
    return this.id;
  }

  public String getNamaLengkap() {
    return this.namaLengkap;
  }

  public void setNamaLengkap(String namaLengkap) {
    this.namaLengkap = namaLengkap;
  }

  public String getGender() {
    return this.gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public LocalDateTime getStartTime() {
    return this.startTime;
  }

  public void setStartTime(LocalDateTime startTime) {
    this.startTime = startTime;
  }

  public LocalDateTime getEndTime() {
    return this.endTime;
  }

  public void setEndTime(LocalDateTime endTime) {
    this.endTime = endTime;
  }

  public Long getGaji() {
    return this.gaji;
  }

  public void setGaji(Long gaji) {
    this.gaji = gaji;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhoneNumber() {
    return this.phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

}
