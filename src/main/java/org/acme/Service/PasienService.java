package org.acme.Service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;

import org.acme.Model.Pasien;

import io.vertx.core.json.JsonObject;

@ApplicationScoped
public class PasienService {

  @Inject
  EntityManager em;

  @Transactional
  public Response add(JsonObject req) {
    Pasien pasien = new Pasien();

    pasien.setNamaLengkap(req.getString("nama_lengkap"));
    pasien.setGender(req.getString("gender"));
    pasien.setStatus(req.getString("status"));
    pasien.setAddress(req.getString("address"));
    pasien.setEmail(req.getString("email"));
    pasien.setPhoneNumber(req.getString("phone_number"));
    pasien.setIsCoverBpjs(req.getBoolean("is_cover_bpjs"));
    pasien.persist();

    JsonObject result = new JsonObject();
    result.put("status", "success");
    result.put("data", pasien);

    return Response.ok().entity(result).build();
  }

  public Response getAll(String nama_lengkap, String email, String phone_number, Integer page) {

    StringBuilder sb = new StringBuilder();
    sb.append("SELECT * ");
    sb.append(" FROM miniproject.pasien p ");
    sb.append(" WHERE TRUE ");

    if (nama_lengkap != null) {
      sb.append(" AND p.nama_lengkap = :nama_lengkap ");
    }
    if (email != null) {
      sb.append(" AND p.email = :email ");
    }
    if (phone_number != null) {
      sb.append(" AND p.phone_number = :phone_number");
    }

    Query query = em.createNativeQuery(sb.toString(), Pasien.class);

    if (nama_lengkap != null) {
      query.setParameter("nama_lengkap", nama_lengkap);
    }
    if (email != null) {
      query.setParameter("email", email);
    }
    if (phone_number != null) {
      query.setParameter("phone_number", phone_number);
    }

    // get total data
    List<Pasien> total = query.getResultList();
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
    List<Pasien> pasiens = query.getResultList();

    JsonObject result = new JsonObject();
    result.put("status", "success");
    result.put("data", pasiens);
    result.put("totalData", totalData);
    result.put("totalPage", tPage);
    result.put("maxDataPerPage", max);
    result.put("activePage", pageNow);

    return Response.ok().entity(result).build();
  }

  @Transactional
  public Response update(Long id, JsonObject req) {
    Pasien pasien = Pasien.findById(id);

    if (pasien == null) {
      JsonObject result = new JsonObject();
      result.put("status", "error");
      result.put("message", "Pasien not found!!");

      return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
    }

    pasien.setNamaLengkap(req.getString("nama_lengkap"));
    pasien.setGender(req.getString("gender"));
    pasien.setStatus(req.getString("status"));
    pasien.setAddress(req.getString("address"));
    pasien.setEmail(req.getString("email"));
    pasien.setPhoneNumber(req.getString("phone_number"));
    pasien.setIsCoverBpjs(req.getBoolean("is_cover_bpjs"));
    pasien.persist();

    JsonObject result = new JsonObject();
    result.put("status", "success");
    result.put("message", "Pasien has been updated");
    result.put("data", pasien);

    return Response.ok().entity(result).build();
  }

  @Transactional
  public Response drop(Long id) {
    Pasien pasien = Pasien.findById(id);

    if (pasien == null) {
      JsonObject result = new JsonObject();
      result.put("status", "error");
      result.put("message", "Pasien not found!!");

      return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
    }

    pasien.delete();
    JsonObject result = new JsonObject();
    result.put("status", "success");
    result.put("message", "Pasien has been deleted");

    return Response.ok().entity(result).build();
  }

}
