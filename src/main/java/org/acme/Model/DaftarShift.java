package org.acme.Model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "daftar_shift")
public class DaftarShift extends AuditModel implements Serializable {
  @Id
  @SequenceGenerator(name = "daftarShiftSeq", sequenceName = "daftar_shift_sequence", initialValue = 1, allocationSize = 1)
  @GeneratedValue(generator = "daftarShiftSeq", strategy = GenerationType.SEQUENCE)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "kategori", length = 50, nullable = false, columnDefinition = "varchar(50)")
  private String kategori;

  @Column(name = "foreign_id", nullable = false, columnDefinition = "bigint")
  private Long foreignId;

  @Column(name = "start_datetime", nullable = false, columnDefinition = "timestamp")
  private LocalDateTime startDateTime;

  @Column(name = "end_datetime", nullable = false, columnDefinition = "timestamp")
  private LocalDateTime endDateTime;

  public Long getId() {
    return this.id;
  }

  public String getKategori() {
    return this.kategori;
  }

  public void setKategori(String kategori) {
    this.kategori = kategori;
  }

  public Long getForeignId() {
    return this.foreignId;
  }

  public void setForeignId(Long foreignId) {
    this.foreignId = foreignId;
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

}
