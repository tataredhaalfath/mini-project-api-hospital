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

import org.acme.Model.KategoriObat.ObatKategori;

@Entity
@Table(name = "obat")
public class Obat extends AuditModel implements Serializable {
  @Id
  @SequenceGenerator(name = "obatSeq", sequenceName = "obat_sequence", initialValue = 1, allocationSize = 1)
  @GeneratedValue(generator = "obatSeq", strategy = GenerationType.SEQUENCE)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "nama_obat", nullable = false, length = 50, columnDefinition = "varchar(50)")
  private String namaObat;

  @Column(name = "produksi", nullable = false, length = 50, columnDefinition = "varchar(50)")
  private String produksi;

  // @Column(name = "obat_kategori", nullable = false, length = 25,
  // columnDefinition = "varchar(25)")
  // private String obatKategori;

  @Enumerated
  private ObatKategori obat_kategori;

  public void setId(Long id) {
    this.id = id;
  }

  @Column(name = "deskripsi", nullable = false, length = 255, columnDefinition = "varchar(255)")
  private String deskripsi;

  public Long getId() {
    return this.id;
  }

  public String getNamaObat() {
    return this.namaObat;
  }

  public void setNamaObat(String namaObat) {
    this.namaObat = namaObat;
  }

  public String getProduksi() {
    return this.produksi;
  }

  public void setProduksi(String produksi) {
    this.produksi = produksi;
  }

  // public String getObatKategori() {
  // return this.obatKategori;
  // }

  // public void setObatKategori(String obatKategori) {
  // this.obatKategori = obatKategori;
  // }

  public ObatKategori getObat_kategori() {
    return this.obat_kategori;
  }

  public void setObat_kategori(ObatKategori obat_kategori) {
    this.obat_kategori = obat_kategori;
  }

  public String getDeskripsi() {
    return this.deskripsi;
  }

  public void setDeskripsi(String deskripsi) {
    this.deskripsi = deskripsi;
  }

}
