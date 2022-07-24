package org.acme.Model;

import java.io.Serializable;

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
@Table(name = "resep_obat")
public class ResetObat extends AuditModel implements Serializable {
  @Id
  @SequenceGenerator(name = "resepObatSeq", sequenceName = "resep_obat_sequence", initialValue = 1, allocationSize = 1)
  @GeneratedValue(generator = "resepObatSeq", strategy = GenerationType.SEQUENCE)
  @Column(name = "id", nullable = false)
  private Long id;

  @ManyToOne(targetEntity = DaftarPertemuan.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "pertemuan_id", nullable = false)
  private DaftarPertemuan pertemuan;

  @Column(name = "obat_id", nullable = false, columnDefinition = "bigint")
  private Long obatId;

  @Column(name = "dosis", nullable = false, length = 50, columnDefinition = "varchar(50)")
  private String dosis;

  @Column(name = "deskripsi", nullable = false, length = 255, columnDefinition = "text")
  private String deskripsi;

  public Long getId() {
    return this.id;
  }

  public DaftarPertemuan getPertemuan() {
    return this.pertemuan;
  }

  public void setPertemuan(DaftarPertemuan pertemuan) {
    this.pertemuan = pertemuan;
  }

  public Long getObatId() {
    return this.obatId;
  }

  public void setObatId(Long obatId) {
    this.obatId = obatId;
  }

  public String getDosis() {
    return this.dosis;
  }

  public void setDosis(String dosis) {
    this.dosis = dosis;
  }

  public String getDeskripsi() {
    return this.deskripsi;
  }

  public void setDeskripsi(String deskripsi) {
    this.deskripsi = deskripsi;
  }

}
