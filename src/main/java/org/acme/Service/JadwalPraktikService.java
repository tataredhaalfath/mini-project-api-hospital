package org.acme.Service;

import java.time.LocalDateTime;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;

import org.acme.Model.Dokter;
import org.acme.Model.JadwalPraktik;

import io.vertx.core.json.JsonObject;

@ApplicationScoped
public class JadwalPraktikService {
  @Inject
  EntityManager em;

  @Transactional
  public Response add(JsonObject req) {
    JadwalPraktik jadwalPraktik = new JadwalPraktik();

    Dokter dokter = Dokter.findById(req.getLong("dokter_id"));
    if (dokter == null) {
      JsonObject result = new JsonObject();
      result.put("status", "error");
      result.put("message", "Dokter not found!!");

      return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
    }

    LocalDateTime actulTime = LocalDateTime.now();

    jadwalPraktik.setHari(req.getString("hari"));
    jadwalPraktik.setStartTime(actulTime);
    jadwalPraktik.setEndTime(actulTime.plusHours(8));
    jadwalPraktik.setDeskripsi(req.getString("deskripsi"));
    jadwalPraktik.setDokter(dokter);
    jadwalPraktik.persist();

    JsonObject result = new JsonObject();
    result.put("status", "success");
    result.put("data", jadwalPraktik);

    return Response.ok(result).build();
  }

  public Response getAll(String hari, Integer page) {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT * ");
    sb.append(" FROM miniproject.jadwal_praktik j ");
    sb.append(" WHERE TRUE ");

    if (hari != null) {
      sb.append(" AND j.hari = :hari ");
    }

    Query query = em.createNativeQuery(sb.toString(), JadwalPraktik.class);

    if (hari != null) {
      query.setParameter("hari", hari);
    }

    // get total data
    List<JadwalPraktik> total = query.getResultList();
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
    List<JadwalPraktik> jadwalPraktiks = query.getResultList();

    JsonObject result = new JsonObject();
    result.put("status", "success");
    result.put("data", jadwalPraktiks);
    result.put("totalData", totalData);
    result.put("totalPage", tPage);
    result.put("maxDataPerPage", max);
    result.put("activePage", pageNow);

    return Response.ok().entity(result).build();
  }

  @Transactional
  public Response update(Long id, JsonObject req) {
    JadwalPraktik jadwalPraktik = JadwalPraktik.findById(id);

    if (jadwalPraktik == null) {
      JsonObject result = new JsonObject();
      result.put("status", "error");
      result.put("message", "Jadwal Praktik not found!!");

      return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
    }

    Dokter dokter = Dokter.findById(req.getLong("dokter_id"));
    if (dokter == null) {
      JsonObject result = new JsonObject();
      result.put("status", "error");
      result.put("message", "Dokter not found!!");

      return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
    }

    LocalDateTime actulTime = LocalDateTime.now();

    jadwalPraktik.setHari(req.getString("hari"));
    jadwalPraktik.setStartTime(actulTime);
    jadwalPraktik.setEndTime(actulTime.plusHours(8));
    jadwalPraktik.setDeskripsi(req.getString("deskripsi"));
    jadwalPraktik.setDokter(dokter);
    jadwalPraktik.persist();

    JsonObject result = new JsonObject();
    result.put("status", "success");
    result.put("data", jadwalPraktik);

    return Response.ok(result).build();

  }
}
