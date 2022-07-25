package org.acme.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;

import org.acme.Model.Staff;
import org.acme.Model.PosisiStaff.Posisi;

import io.vertx.core.json.JsonObject;

@ApplicationScoped
public class StaffService {
  @Inject
  EntityManager em;

  public Response listPosisi() {
    List<String> posisi = Stream.of(Posisi.values()).map(e -> e.name()).collect(Collectors.toList());

    JsonObject result = new JsonObject();
    result.put("status", "success");
    result.put("data", posisi);

    return Response.ok().entity(result).build();
  }

  @Transactional
  public Response add(JsonObject req) {
    Staff staff = new Staff();
    String email = req.getString("email");

    List<Staff> staffs = Staff.findAll().list();
    Optional<Staff> getStaff = staffs.stream().filter(u -> u.getEmail().equalsIgnoreCase(email))
        .findFirst();

    if (!getStaff.isEmpty()) {
      JsonObject result = new JsonObject();
      result.put("status", "error");
      result.put("message", "Email already exist!");
      return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
    }

    String posisi = req.getString("posisi");
    switch (posisi.toLowerCase()) {
      case "security":
        staff.setPosisi(Posisi.Engineer);
        break;
      case "janitor":
        staff.setPosisi(Posisi.Janitor);
        break;
      case "receipt":
        staff.setPosisi(Posisi.Receipt);
        break;
      case "engineer":
        staff.setPosisi(Posisi.Engineer);
        break;
      default:
        JsonObject result = new JsonObject();
        result.put("status", "error");
        result.put("message", "Unknown Posisi!!");
        return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
    }

    LocalDateTime actulTime = LocalDateTime.now();

    staff.setNamaLengkap(req.getString("nama_lengkap"));
    staff.setGender(req.getString("gender"));
    staff.setStartTime(LocalDateTime.now());
    staff.setEndTime(actulTime.plusHours(8));
    staff.setGaji(req.getLong("gaji"));
    staff.setEmail(req.getString("email"));
    staff.setPhoneNumber(req.getString("phone_number"));
    staff.setStatus(req.getString("status"));
    // staff.setKategori(Posisi.Engineer);
    staff.persist();

    JsonObject result = new JsonObject();
    result.put("status", "success");
    result.put("data", staff);

    return Response.ok().entity(result).build();
  }

  public Response getAll(String nama_lengkap, String email, String phone_number, int page) {

    StringBuilder sb = new StringBuilder();
    sb.append("SELECT * ");
    sb.append("FROM miniproject.staff s ");
    sb.append("WHERE TRUE ");

    if (nama_lengkap != null) {
      sb.append(" AND s.nama_lengkap = :nama_lengkap ");
    }
    if (email != null) {
      sb.append(" AND s.email = :email ");
    }
    if (phone_number != null) {
      sb.append(" AND s.phone_number = :phone_number");
    }

    Query query = em.createNativeQuery(sb.toString(), Staff.class);

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
    List<Staff> total = query.getResultList();
    //

    // konfigurasi pagination
    int first = 0;
    int max = 5;

    int totalData = total.size();
    double pageCont = Math.ceil((double) totalData / max);
    int tPage = (int) pageCont;

    int pageNow = 1; // halaman aktif

    if (page > 1) {
      pageNow = page;
      first = (max * pageNow) - max;
    }

    query.setFirstResult(first);
    query.setMaxResults(max);
    List<Staff> staffs = query.getResultList();

    JsonObject result = new JsonObject();
    result.put("status", "success");
    result.put("data", staffs);
    result.put("totalData", totalData);
    result.put("totalPage", tPage);
    result.put("maxDataPerPage", max);
    result.put("activePage", pageNow);

    return Response.ok().entity(result).build();
  }

  @Transactional
  public Response drop(Long id) {
    Staff staff = Staff.findById(id);
    if (staff == null) {
      JsonObject result = new JsonObject();
      result.put("status", "error");
      result.put("message", "Staff not found!!");

      return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
    }

    staff.delete();

    JsonObject result = new JsonObject();
    result.put("status", "success");
    result.put("message", "Staff has been deleted");

    return Response.ok().entity(result).build();

  }

  @Transactional
  public Response updateGaji(Long id, JsonObject req) {
    Staff staff = Staff.findById(id);

    if (staff == null) {
      JsonObject result = new JsonObject();
      result.put("status", "error");
      result.put("message", "Staff not found!!");

      return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
    }

    staff.setGaji(req.getLong("gaji"));
    staff.persist();

    JsonObject result = new JsonObject();
    result.put("status", "success");
    result.put("message", "Gaji Staff has been updated");
    result.put("data", staff);

    return Response.ok().entity(result).build();
  }
}
