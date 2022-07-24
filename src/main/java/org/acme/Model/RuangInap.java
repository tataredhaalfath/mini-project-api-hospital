package org.acme.Model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.acme.Model.KategoriRuangInap.KategoriRuangan;

@Entity
@Table(name = "ruang_inap")
public class RuangInap extends AuditModel implements Serializable {
  @Id
  @SequenceGenerator(name = "ruangInapSeq", sequenceName = "ruang_inap_sequence", initialValue = 1, allocationSize = 1)
  @GeneratedValue(generator = "ruangInapSeq", strategy = GenerationType.SEQUENCE)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "prefix_ruangan", nullable = false, length = 15, columnDefinition = "varchar(15)")
  private String prefixRuangan;

  @Column(name = "nomor_ruangan", nullable = false, length = 15, columnDefinition = "varchar(15)")
  private String nomorRuangan;

  // @Column(name = "kategori_ruangan", nullable = false, length = 15,
  // columnDefinition = "varchar(15)")
  // private String kategoriRuang;

  @Enumerated
  private KategoriRuangan kategori_ruangan;

  public void setId(Long id) {
    this.id = id;
  }

  public boolean isIsKosong() {
    return this.isKosong;
  }

  @Column(name = "is_kosong", nullable = false, columnDefinition = "boolean")
  private boolean isKosong;

  public Long getId() {
    return id;
  }

  public String getPrefixRuangan() {
    return prefixRuangan;
  }

  public void setPrefixRuangan(String prefixRuangan) {
    this.prefixRuangan = prefixRuangan;
  }

  public String getNomorRuangan() {
    return nomorRuangan;
  }

  public void setNomorRuangan(String nomorRuangan) {
    this.nomorRuangan = nomorRuangan;
  }

  // public String getKategoriRuangan() {
  // return kategoriRuang;
  // }

  // public void setKategoriRuangan(String kategoriRuangan) {
  // this.kategoriRuang = kategoriRuangan;
  // }

  public KategoriRuangan getKategori_ruangan() {
    return this.kategori_ruangan;
  }

  public void setKategori_ruangan(KategoriRuangan kategori_ruangan) {
    this.kategori_ruangan = kategori_ruangan;
  }

  public boolean getIsKosong() {
    return isKosong;
  }

  public void setIsKosong(boolean isKosong) {
    this.isKosong = isKosong;
  }
}
