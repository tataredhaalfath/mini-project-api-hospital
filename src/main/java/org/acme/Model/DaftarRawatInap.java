package org.acme.Model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "daftar_rawat_inap")
public class DaftarRawatInap extends AuditModel implements Serializable {
  @Id
  @SequenceGenerator(name = "daftarRawatInapSeq", sequenceName = "daftar_rawat_inap_sequence", initialValue = 1, allocationSize = 1)
  @GeneratedValue(generator = "daftarRawatInapSeq", strategy = GenerationType.SEQUENCE)
  @Column(name = "id", nullable = false)
  private Long id;

  @ManyToOne(targetEntity = Pasien.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "pasien_id", nullable = false)
  private Pasien pasien;

  @ManyToOne(targetEntity = RuangInap.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "ruang_inap_id")
  private RuangInap ruangInap;

  @ManyToOne(targetEntity = Dokter.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "dokter_id", nullable = false)
  private Dokter dokter;

  @ManyToOne(targetEntity = Perawat.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "perawat_satu_id", nullable = false)
  private Perawat perawatSatu;

  @ManyToOne(targetEntity = Perawat.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "perawat_dua_id", nullable = false)
  private Perawat perawatDua;

  @Column(name = "start_datetime", nullable = false, columnDefinition = "timestamp")
  private LocalDateTime startDateTime;

  @Column(name = "end_datetime", nullable = true, columnDefinition = "timestamp")
  private LocalDateTime endDateTime;

  @Column(name = "is_checkout", nullable = false, columnDefinition = "boolean")
  private boolean isCheckout;

  public Long getId() {
    return this.id;
  }

  public Pasien getPasien() {
    return this.pasien;
  }

  public void setPasien(Pasien pasien) {
    this.pasien = pasien;
  }

  public RuangInap getRuangInap() {
    return this.ruangInap;
  }

  public void setRuangInap(RuangInap ruangInap) {
    this.ruangInap = ruangInap;
  }

  public Dokter getDokter() {
    return this.dokter;
  }

  public void setDokter(Dokter dokter) {
    this.dokter = dokter;
  }

  public Perawat getPerawatSatu() {
    return this.perawatSatu;
  }

  public void setPerawatSatu(Perawat perawatSatu) {
    this.perawatSatu = perawatSatu;
  }

  public Perawat getPerawatDua() {
    return this.perawatDua;
  }

  public void setPerawatDua(Perawat perawatDua) {
    this.perawatDua = perawatDua;
  }

  public LocalDateTime getStartDateTime() {
    return this.startDateTime;
  }

  public void setStartDateTime(LocalDateTime startDateTime) {
    this.startDateTime = startDateTime;
  }

  public LocalDateTime getEndDateTime() {
    return this.endDateTime;
  }

  public void setEndDateTime(LocalDateTime endDateTime) {
    this.endDateTime = endDateTime;
  }

  public boolean isIsCheckout() {
    return this.isCheckout;
  }

  public boolean getIsCheckout() {
    return this.isCheckout;
  }

  public void setIsCheckout(boolean isCheckout) {
    this.isCheckout = isCheckout;
  }

}
