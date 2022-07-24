package org.acme.Model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "riwayat_penyakit")
public class RiwayarPenyakit extends AuditModel implements Serializable {
  @Id
  @SequenceGenerator(name = "riwayatPenyakitSeq", sequenceName = "riwayat_penyakit_sequence", initialValue = 1, allocationSize = 1)
  @GeneratedValue(generator = "riwayarPenyakitSeq", strategy = GenerationType.SEQUENCE)
  @Column(name = "id", nullable = false)
  private Long id;

  @OneToMany(targetEntity = Pasien.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "pasien_id")
  private Set<Pasien> pasien = new HashSet<Pasien>();

  @Column(name = "nama", nullable = false, length = 50, columnDefinition = "varchar(50)")
  private String nama;

  @Column(name = "deskripsi", nullable = false, length = 255, columnDefinition = "varchar(255)")
  private String deskripsi;

  @Column(name = "awal_date", nullable = false, columnDefinition = "timestamp")
  private LocalDateTime awalDate;

  @Column(name = "sembuh_date", nullable = false, columnDefinition = "timestamp")
  private LocalDateTime sembuhDate;

  public Long getId() {
    return this.id;
  }

  public Pasien getPasien() {
    return (Pasien) this.pasien;
  }

  public void setPasien(Pasien pasien) {
    this.pasien = (Set<Pasien>) pasien;
  }

  public String getNama() {
    return this.nama;
  }

  public void setNama(String nama) {
    this.nama = nama;
  }

  public String getDeskripsi() {
    return this.deskripsi;
  }

  public void setDeskripsi(String deskripsi) {
    this.deskripsi = deskripsi;
  }

  public LocalDateTime getAwalDate() {
    return this.awalDate;
  }

  public void setAwalDate(LocalDateTime awalDate) {
    this.awalDate = awalDate;
  }

  public LocalDateTime getSembuhDate() {
    return this.sembuhDate;
  }

  public void setSembuhDate(LocalDateTime sembuhDate) {
    this.sembuhDate = sembuhDate;
  }

}
