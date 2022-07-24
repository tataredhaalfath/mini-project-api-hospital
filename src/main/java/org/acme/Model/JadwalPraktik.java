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
@Table(name = "jadwal_praktik")
public class JadwalPraktik extends AuditModel implements Serializable {
  @Id
  @SequenceGenerator(name = "jadwalPraktikSeq", sequenceName = "jadwal_praktek_sequence", initialValue = 1, allocationSize = 1)
  @GeneratedValue(generator = "jadwalPraktikSeq", strategy = GenerationType.SEQUENCE)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "hari", nullable = false, length = 15, columnDefinition = "varchar(15)")
  private String hari;

  @Column(name = "start_time", nullable = false, columnDefinition = "timestamp")
  private LocalDateTime startTime;

  @Column(name = "end_time", nullable = false, columnDefinition = "timestamp")
  private LocalDateTime endTime;

  @Column(name = "deskripsi", nullable = false, length = 255, columnDefinition = "varchar(255)")
  private String deskripsi;

  @ManyToOne(targetEntity = Dokter.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "dokter", nullable = false)
  private Dokter dokter;

  public Long getId() {
    return this.id;
  }

  public String getHari() {
    return this.hari;
  }

  public void setHari(String hari) {
    this.hari = hari;
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

  public String getDeskripsi() {
    return this.deskripsi;
  }

  public void setDeskripsi(String deskripsi) {
    this.deskripsi = deskripsi;
  }

  public Dokter getDokter() {
    return this.dokter;
  }

  public void setDokter(Dokter dokter) {
    this.dokter = dokter;
  }

}
