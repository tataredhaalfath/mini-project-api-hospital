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
@Table(name = "daftar_shift_hari")
public class DaftarShiftHari extends AuditModel implements Serializable {
  @Id
  @SequenceGenerator(name = "daftarShiftHariSeq", sequenceName = "daftar_shift_hari_sequence", initialValue = 1, allocationSize = 1)
  @GeneratedValue(generator = "daftarShiftHariSeq", strategy = GenerationType.SEQUENCE)
  @Column(name = "id", nullable = false)
  private Long id;

  @ManyToOne(targetEntity = DaftarShift.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "daftar_shift_id", nullable = false)
  private DaftarShift daftarShift;

  @Column(name = "hari", length = 15, nullable = false, columnDefinition = "varchar(15)")
  private String hari;

  public Long getId() {
    return this.id;
  }

  public DaftarShift getDaftarShift() {
    return this.daftarShift;
  }

  public void setDaftarShift(DaftarShift daftarShift) {
    this.daftarShift = daftarShift;
  }

  public String getHari() {
    return this.hari;
  }

  public void setHari(String hari) {
    this.hari = hari;
  }

}
