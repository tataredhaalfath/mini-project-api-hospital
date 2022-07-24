package org.acme.Service;

import java.time.LocalDateTime;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;

import org.acme.Model.DaftarRawatInap;
import org.acme.Model.Dokter;
import org.acme.Model.Pasien;
import org.acme.Model.Perawat;
import org.acme.Model.RuangInap;

import io.vertx.core.json.JsonObject;

@ApplicationScoped
public class DaftarRawatInapService {

  @Inject
  EntityManager em;

  @Transactional
  public Response add(JsonObject req) {
    DaftarRawatInap daftarRawatInap = new DaftarRawatInap();

    Pasien pasien = Pasien.findById(req.getLong("pasien_id"));
    RuangInap ruangInap = RuangInap.findById(req.getLong("ruang_inap_id"));
    Dokter dokter = Dokter.findById(req.getLong("dokter_id"));
    Perawat perawatSatu = Perawat.findById(req.getLong("perawat_satu_id"));
    Perawat perawatDua = Perawat.findById(req.getLong("perawat_dua_id"));

    if (pasien == null) {
      JsonObject result = new JsonObject();
      result.put("status", "error");
      result.put("message", "Pasien not found!!");

      return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
    }
    if (ruangInap == null) {
      JsonObject result = new JsonObject();
      result.put("status", "error");
      result.put("message", "Ruang Inap not found!!");

      return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
    } else {
      if (ruangInap.getIsKosong() == false) {
        JsonObject result = new JsonObject();
        result.put("status", "error");
        result.put("message", "Ruang Inap is Used!!");

        return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
      }
    }
    if (dokter == null) {
      JsonObject result = new JsonObject();
      result.put("status", "error");
      result.put("message", "Dokter not found!!");

      return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
    }
    if (perawatSatu == null) {
      JsonObject result = new JsonObject();
      result.put("status", "error");
      result.put("message", "Perawat satu not found!!");

      return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
    }
    if (perawatDua == null) {
      JsonObject result = new JsonObject();
      result.put("status", "error");
      result.put("message", "Perawat dua not found!!");

      return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
    }

    daftarRawatInap.setPasien(pasien);
    daftarRawatInap.setRuangInap(ruangInap);
    daftarRawatInap.setDokter(dokter);
    daftarRawatInap.setPerawatSatu(perawatSatu);
    daftarRawatInap.setPerawatDua(perawatDua);
    daftarRawatInap.setStartDateTime(LocalDateTime.now());
    daftarRawatInap.setIsCheckout(false);
    daftarRawatInap.persist();

    // ruang inap is used
    ruangInap.setIsKosong(false);
    ruangInap.persist();

    JsonObject result = new JsonObject();
    result.put("status", "success");
    result.put("data", daftarRawatInap);

    return Response.ok().entity(result).build();
  }

  @Transactional
  public Response checkout(Long id) {
    DaftarRawatInap daftarRawatInap = DaftarRawatInap.findById(id);

    if (daftarRawatInap == null) {
      JsonObject result = new JsonObject();
      result.put("status", "error");
      result.put("message", "Daftar Rawat Inap not found!!");

      return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
    }

    RuangInap ruangInap = RuangInap.findById(daftarRawatInap.getRuangInap().getId());

    daftarRawatInap.setIsCheckout(true);
    daftarRawatInap.setEndDateTime(LocalDateTime.now());
    daftarRawatInap.persist();

    // ruang inap is not used
    ruangInap.setIsKosong(true);
    ruangInap.persist();

    JsonObject result = new JsonObject();
    result.put("status", "success");
    result.put("message", "Process Checkout success");
    result.put("data", daftarRawatInap);

    return Response.ok().entity(daftarRawatInap).build();
  }

  public Response getAll(String nama_pasien, String ruang_inap, Integer page) {

    StringBuilder sb = new StringBuilder();
    sb.append("SELECT * ");
    sb.append(" FROM miniproject.daftar_rawat_inap d ");
    sb.append(" WHERE TRUE ");

    Query query = em.createNativeQuery(sb.toString(), DaftarRawatInap.class);

    // get total data
    List<DaftarRawatInap> total = query.getResultList();
    //

    // konfigurasi pagination
    int first = 0;
    int max = 5;

    int totalData = total.size();
    double pageCount = Math.ceil((double) totalData / max);
    int tPage = (int) pageCount;

    int pageNow = 1; // halaman aktif

    if (page > 1) {
      pageNow = page;
      first = (max * pageNow) - max;
    }

    query.setFirstResult(first);
    query.setMaxResults(max);
    List<DaftarRawatInap> daftarRawatInaps = query.getResultList();

    JsonObject result = new JsonObject();
    result.put("status", "success");
    result.put("data", daftarRawatInaps);
    result.put("totalData", totalData);
    result.put("totalPage", tPage);
    result.put("maxDataPerPage", max);
    result.put("activePage", pageNow);

    return Response.ok().entity(result).build();
  }

}
