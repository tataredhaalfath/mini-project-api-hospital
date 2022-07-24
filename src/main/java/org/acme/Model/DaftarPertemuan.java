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
@Table(name = "daftar_pertemuan")
public class DaftarPertemuan extends AuditModel implements Serializable {
  @Id
  @SequenceGenerator(name = "daftarPerSeq", sequenceName = "daftar_pertemuan_sequence", initialValue = 1, allocationSize = 1)
  @GeneratedValue(generator = "daftarPerSeq", strategy = GenerationType.SEQUENCE)
  @Column(name = "id")
  private Long id;

  @OneToMany(targetEntity = Pasien.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "pasien_id")
  private Set<Pasien> pasien = new HashSet<Pasien>();

  @OneToMany(targetEntity = Dokter.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "dokter_id")
  private Set<Dokter> dokter = new HashSet<Dokter>();

  @Column(name = "kategori", nullable = false, length = 50, columnDefinition = "varchar(50)")
  private String kategori;

  @Column(name = "deskripsi", nullable = false, length = 255, columnDefinition = "text")
  private String deskripsi;

  @Column(name = "tanggal", nullable = false, columnDefinition = "timestamp")
  private LocalDateTime tanggal;

  public Long getId() {
    return this.id;
  }

  public Pasien getPasien() {
    return (Pasien) this.pasien;
  }

  public void setPasien(Pasien pasien) {
    this.pasien = (Set<Pasien>) pasien;
  }

  public Dokter getDokter() {
    return (Dokter) this.dokter;
  }

  public void setDokter(Dokter dokter) {
    this.dokter = (Set<Dokter>) dokter;
  }

  public String getKategori() {
    return this.kategori;
  }

  public void setKategori(String kategori) {
    this.kategori = kategori;
  }

  public String getDeskripsi() {
    return this.deskripsi;
  }

  public void setDeskripsi(String deskripsi) {
    this.deskripsi = deskripsi;
  }

  public LocalDateTime getTanggal() {
    return this.tanggal;
  }

  public void setTanggal(LocalDateTime tanggal) {
    this.tanggal = tanggal;
  }

}
