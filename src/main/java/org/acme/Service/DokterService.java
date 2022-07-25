package org.acme.Service;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;

import org.acme.Model.Dokter;

import io.vertx.core.json.JsonObject;

@ApplicationScoped
public class DokterService {

  @Inject
  EntityManager em;

  @Transactional
  public Response add(JsonObject req) {
    String namaLengkap = req.getString("nama_lengkap");
    Boolean isSpesialis = req.getBoolean("is_spesialis");
    String spesialisNama = req.getString("spesialis_nama");
    String email = req.getString("email");
    String phoneNumber = req.getString("phone_number");
    String status = req.getString("status");
    Long gaji = req.getLong("gaji");

    Dokter dokter = new Dokter();

    List<Dokter> dokters = Dokter.findAll().list();
    Optional<Dokter> getDokter = dokters.stream().filter(u -> u.getEmail().equalsIgnoreCase(email))
        .findFirst();

    if (!getDokter.isEmpty()) {
      JsonObject result = new JsonObject();
      result.put("status", "error");
      result.put("message", "Email already exist!");
      return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
    }

    dokter.setNamaLengkap(namaLengkap);
    dokter.setIsSpesialis(isSpesialis);
    dokter.setSpesialisNama(spesialisNama);
    dokter.setEmail(email);
    dokter.setPhoneNumber(phoneNumber);
    dokter.setStatus(status);
    dokter.setGaji(gaji);
    dokter.persist();

    JsonObject result = new JsonObject();
    result.put("status", "success");
    result.put("data", dokter);

    return Response.ok(result).build();
  }

  public Response getAll(String nama_lengkap, String email, String phone_number, String spesialis_nama, Integer page) {

    StringBuilder sb = new StringBuilder();
    sb.append("SELECT * ");
    sb.append(" FROM miniproject.dokter d ");
    sb.append(" WHERE TRUE ");

    if (nama_lengkap != null) {
      sb.append(" AND d.nama_lengkap = :nama_lengkap ");
    }
    if (email != null) {
      sb.append(" AND d.email = :email ");
    }
    if (phone_number != null) {
      sb.append(" AND d.phone_number = :phone_number");
    }
    if (spesialis_nama != null) {
      sb.append(" AND d.spesialis_nama = :spesialis_nama");
    }

    Query query = em.createNativeQuery(sb.toString(), Dokter.class);

    if (nama_lengkap != null) {
      query.setParameter("nama_lengkap", nama_lengkap);
    }
    if (email != null) {
      query.setParameter("email", email);
    }
    if (phone_number != null) {
      query.setParameter("phone_number", phone_number);
    }
    if (spesialis_nama != null) {
      query.setParameter("spesialis_nama", spesialis_nama);
    }
    // get total data
    List<Dokter> total = query.getResultList();
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
    List<Dokter> dokters = query.getResultList();

    JsonObject result = new JsonObject();
    result.put("status", "success");
    result.put("data", dokters);
    result.put("totalData", totalData);
    result.put("totalPage", tPage);
    result.put("maxDataPerPage", max);
    result.put("activePage", pageNow);

    return Response.ok().entity(result).build();

  }

  @Transactional
  public Response updateGaji(Long id, JsonObject req) {
    Dokter dokter = Dokter.findById(id);

    if (dokter == null) {
      JsonObject result = new JsonObject();
      result.put("status", "error");
      result.put("message", "Dokter not found!!");

      return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
    }

    dokter.setGaji(req.getLong("gaji"));
    dokter.persist();

    JsonObject result = new JsonObject();
    result.put("status", "success");
    result.put("message", "Gaji Dokter has been updated");
    result.put("data", dokter);

    return Response.ok().entity(result).build();
  }
}
