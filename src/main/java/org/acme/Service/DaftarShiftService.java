package org.acme.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;

import org.acme.Model.DaftarShift;
import org.acme.Model.Perawat;
import org.acme.Model.Staff;

import io.vertx.core.json.JsonObject;

@ApplicationScoped
public class DaftarShiftService {

  @Inject
  EntityManager em;

  @Transactional
  public Response add(JsonObject req) {
    DaftarShift daftarShift = new DaftarShift();

    switch (req.getString("kategori").toLowerCase()) {
      case "perawat":
        Perawat perawat = Perawat.findById(req.getLong("foreign_id"));
        if (perawat == null) {
          JsonObject result = new JsonObject();
          result.put("status", "error");
          result.put("message", "Perawat not found!!");

          return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
        }
        break;

      case "staff":
        Staff staff = Staff.findById(req.getLong("foreign_id"));
        if (staff == null) {
          JsonObject result = new JsonObject();
          result.put("status", "error");
          result.put("message", "Staff not found!!");

          return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
        }
        break;

      default:
        JsonObject result = new JsonObject();
        result.put("status", "error");
        result.put("message", "Kategori not available!!");

        return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
    }

    Set<String> hari = new HashSet<>(req.getJsonArray("hari").getList());

    LocalDateTime actulTime = LocalDateTime.now();

    daftarShift.setKategori(req.getString("kategori"));
    daftarShift.setForeignId(req.getLong("foreign_id"));
    daftarShift.setStartDateTime(actulTime);
    daftarShift.setEndDateTime(actulTime.plusHours(8));
    daftarShift.setHari(hari);
    daftarShift.persist();

    JsonObject result = new JsonObject();
    result.put("status", "success");
    result.put("data", daftarShift);

    return Response.ok().entity(result).build();

  }

  public Response getAll(String kategori, Integer page) {

    StringBuilder sb = new StringBuilder();
    sb.append("SELECT * ");
    sb.append(" FROM miniproject.daftar_shift d ");
    sb.append(" WHERE TRUE ");

    if (kategori != null) {
      sb.append(" AND d.kategori = :kategori ");
    }

    Query query = em.createNativeQuery(sb.toString(), DaftarShift.class);

    if (kategori != null) {
      query.setParameter("kategori", kategori);
    }

    // get total data
    List<DaftarShiftService> total = query.getResultList();
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
    List<DaftarShiftService> daftarShifts = query.getResultList();
    // List<DaftarShift> daftarShifts = DaftarShift.findAll().list();

    JsonObject result = new JsonObject();
    result.put("status", "success");
    result.put("data", daftarShifts);
    result.put("totalData", totalData);
    result.put("totalPage", tPage);
    result.put("maxDataPerPage", max);
    result.put("activePage", pageNow);

    return Response.ok().entity(result).build();
  }

  @Transactional
  public Response update(Long id, JsonObject req) {
    DaftarShift daftarShift = DaftarShift.findById(id);
    
    if (daftarShift == null) {
      JsonObject result = new JsonObject();
      result.put("status", "error");
      result.put("message", "Daftar Shift not found!!");

      return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
    }

    switch (req.getString("kategori").toLowerCase()) {
      case "perawat":
        Perawat perawat = Perawat.findById(req.getLong("foreign_id"));
        if (perawat == null) {
          JsonObject result = new JsonObject();
          result.put("status", "error");
          result.put("message", "Perawat not found!!");

          return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
        }
        break;

      case "staff":
        Staff staff = Staff.findById(req.getLong("foreign_id"));
        if (staff == null) {
          JsonObject result = new JsonObject();
          result.put("status", "error");
          result.put("message", "Staff not found!!");

          return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
        }
        break;

      default:
        JsonObject result = new JsonObject();
        result.put("status", "error");
        result.put("message", "Kategori not available!!");

        return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
    }

    Set<String> hari = new HashSet<>(req.getJsonArray("hari").getList());

    LocalDateTime actulTime = LocalDateTime.now();

    daftarShift.setKategori(req.getString("kategori"));
    daftarShift.setForeignId(req.getLong("foreign_id"));
    daftarShift.setStartDateTime(actulTime);
    daftarShift.setEndDateTime(actulTime.plusHours(8));
    daftarShift.setHari(hari);

    daftarShift.persist();

    JsonObject result = new JsonObject();
    result.put("status", "success");
    result.put("data", daftarShift);

    return Response.ok().entity(result).build();
  }
}
