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
@Table(name = "dokter")
public class Dokter extends AuditModel implements Serializable {
  @Id
  @SequenceGenerator(name = "dokterSeq", sequenceName = "dokter_sequence", initialValue = 1, allocationSize = 1)
  @GeneratedValue(generator = "dokterSeq", strategy = GenerationType.SEQUENCE)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "nama_lengkap", nullable = false, length = 50, columnDefinition = "varchar(50)")
  private String namaLengkap;

  @Column(name = "is_spesialis", nullable = false, columnDefinition = "boolean")
  private boolean isSpesialis;

  @Column(name = "spesialis_nama", nullable = false, length = 50, columnDefinition = "varchar(50)")
  private String spesialisNama;

  @Column(name = "email", unique = true, nullable = false, length = 50, columnDefinition = "varchar(50)")
  private String email;

  @Column(name = "phone_number", nullable = false, length = 15, columnDefinition = "varchar(15)")
  private String phoneNumber;

  @Column(name = "status", nullable = false, length = 15, columnDefinition = "varchar(15)")
  private String status;

  @Column(name = "gaji", nullable = false, columnDefinition = "bigint")
  private Long gaji;

  public Dokter() {
    super();
  }

  public Dokter(Long id, String namaLengkap, boolean isSpesialis, String spesialisNama, String email,
      String phoneNumber, String status, Long gaji) {
    super();
    this.id = id;
    this.namaLengkap = namaLengkap;
    this.isSpesialis = isSpesialis;
    this.spesialisNama = spesialisNama;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.status = status;
    this.gaji = gaji;
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

  public boolean isIsSpesialis() {
    return this.isSpesialis;
  }

  public boolean getIsSpesialis() {
    return this.isSpesialis;
  }

  public void setIsSpesialis(boolean isSpesialis) {
    this.isSpesialis = isSpesialis;
  }

  public String getSpesialisNama() {
    return this.spesialisNama;
  }

  public void setSpesialisNama(String spesialisNama) {
    this.spesialisNama = spesialisNama;
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

  public Long getGaji() {
    return this.gaji;
  }

  public void setGaji(Long gaji) {
    this.gaji = gaji;
  }

}
