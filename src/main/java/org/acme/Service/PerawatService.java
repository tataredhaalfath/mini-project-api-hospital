package org.acme.Service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;

import org.acme.Model.Perawat;

import io.vertx.core.json.JsonObject;

@ApplicationScoped
public class PerawatService {

  @Inject
  EntityManager em;

  @Transactional
  public Response add(JsonObject req) {
    String nama = req.getString("nama_lengkap");
    String gender = req.getString("gender");
    Long gaji = req.getLong("gaji");
    String email = req.getString("email");
    String phoneNumber = req.getString("phone_number");
    String status = req.getString("status");

    Perawat perawat = new Perawat();

    perawat.setNamaLengkap(nama);
    perawat.setGender(gender);
    perawat.setGaji(gaji);
    perawat.setEmail(email);
    perawat.setPhoneNumber(phoneNumber);
    perawat.setStatus(status);
    perawat.persist();

    JsonObject result = new JsonObject();
    result.put("status", "success");
    result.put("data", perawat);

    return Response.ok().entity(result).build();
  }

  public Response getAll(String nama_lengkap, String email, String phone_number, Integer page) {

    StringBuilder sb = new StringBuilder();
    sb.append("SELECT * ");
    sb.append(" FROM miniproject.perawat p ");
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

    Query query = em.createNativeQuery(sb.toString(), Perawat.class);

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
    List<Perawat> total = query.getResultList();
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
    List<Perawat> perawats = query.getResultList();

    JsonObject result = new JsonObject();
    result.put("status", "success");
    result.put("data", perawats);
    result.put("totalData", totalData);
    result.put("totalPage", tPage);
    result.put("maxDataPerPage", max);
    result.put("activePage", pageNow);

    return Response.ok().entity(result).build();
  }

  @Transactional
  public Response updateGaji(Long id, JsonObject req) {
    Perawat perawat = Perawat.findById(id);

    if (perawat == null) {
      JsonObject result = new JsonObject();
      result.put("status", "error");
      result.put("message", "Perawat not found!!");

      return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
    }

    perawat.setGaji(req.getLong("gaji"));
    perawat.persist();

    JsonObject result = new JsonObject();
    result.put("status", "success");
    result.put("message", "Gaji Perawat has been updated");
    result.put("data", perawat);

    return Response.ok().entity(result).build();
  }
}
