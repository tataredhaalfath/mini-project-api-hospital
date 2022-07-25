package org.acme.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;

import org.acme.Model.KategoriRuangInap;
import org.acme.Model.RuangInap;
import org.acme.Model.KategoriRuangInap.KategoriRuangan;

import io.vertx.core.json.JsonObject;

@ApplicationScoped
public class RuangInapService {
  @Inject
  EntityManager em;

  public Response getKategori() {
    List<String> kategori = Stream.of(KategoriRuangan.values()).map(e -> e.name()).collect(Collectors.toList());

    JsonObject result = new JsonObject();
    result.put("status", "success");
    result.put("data", kategori);

    return Response.ok().entity(result).build();
  }

  public Response getAll(String prefix_ruangan, String nomor_ruangan, Integer page) {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT * ");
    sb.append(" FROM miniproject.ruang_inap r ");
    sb.append(" WHERE TRUE ");

    if (prefix_ruangan != null) {
      sb.append(" AND r.prefix_ruangan = :prefix_ruangan ");
    }
    if (nomor_ruangan != null) {
      sb.append(" AND r.nomor_ruangan = :nomor_ruangan ");
    }

    Query query = em.createNativeQuery(sb.toString(), RuangInap.class);

    if (prefix_ruangan != null) {
      query.setParameter("prefix_ruangan", prefix_ruangan);
    }
    if (nomor_ruangan != null) {
      query.setParameter("nomor_ruangan", nomor_ruangan);
    }

    // get total data
    List<RuangInap> total = query.getResultList();
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
    List<RuangInap> ruangInaps = query.getResultList();

    JsonObject result = new JsonObject();
    result.put("status", "success");
    result.put("data", ruangInaps);
    result.put("totalData", totalData);
    result.put("totalPage", tPage);
    result.put("maxDataPerPage", max);
    result.put("activePage", pageNow);

    return Response.ok().entity(result).build();
  }

  @Transactional
  public Response add(JsonObject req) {
    RuangInap ruangInap = new RuangInap();

    String kategori = req.getString("kategori_ruangan");

    switch (kategori.toLowerCase()) {
      case "standard":
        ruangInap.setKategori_ruangan(KategoriRuangan.Standard);
        break;
      case "vip":
        ruangInap.setKategori_ruangan(KategoriRuangan.VIP);
        break;
      case "vvip":
        ruangInap.setKategori_ruangan(KategoriRuangan.VVIP);
        break;
      default:
        JsonObject result = new JsonObject();
        result.put("status", "error");
        result.put("message", "Unknown Kategori Ruangan!!");
        return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
    }
    ruangInap.setPrefixRuangan(req.getString("prefix_ruangan"));
    ruangInap.setNomorRuangan(req.getString("nomor_ruangan"));
    ruangInap.setIsKosong(req.getBoolean("is_kosong"));
    ruangInap.persist();

    JsonObject result = new JsonObject();
    result.put("status", "success");
    result.put("data", ruangInap);

    return Response.ok().entity(result).build();
  }

  @Transactional
  public Response update(Long id, JsonObject req) {
    RuangInap ruangInap = RuangInap.findById(id);

    if (ruangInap == null) {
      JsonObject result = new JsonObject();
      result.put("status", "error");
      result.put("message", "Ruang Inap not found!!");

      return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
    }

    if (ruangInap.getIsKosong() == false) {
      JsonObject result = new JsonObject();
      result.put("status", "error");
      result.put("message", "Can't update, Ruang Inap is used!!");

      return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
    }

    String kategori = req.getString("kategori_ruangan");

    switch (kategori.toLowerCase()) {
      case "standard":
        ruangInap.setKategori_ruangan(KategoriRuangan.Standard);
        break;
      case "vip":
        ruangInap.setKategori_ruangan(KategoriRuangan.VIP);
        break;
      case "vvip":
        ruangInap.setKategori_ruangan(KategoriRuangan.VVIP);
        break;
      default:
        JsonObject result = new JsonObject();
        result.put("status", "error");
        result.put("message", "Unknown Kategori Ruangan!!");
        return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
    }

    ruangInap.setPrefixRuangan(req.getString("prefix_ruangan"));
    ruangInap.setNomorRuangan(req.getString("nomor_ruangan"));
    ruangInap.setIsKosong(req.getBoolean("is_kosong"));
    ruangInap.persist();

    JsonObject result = new JsonObject();
    result.put("status", "success");
    result.put("data", ruangInap);

    return Response.ok().entity(result).build();
  }

  @Transactional
  public Response drop(Long id) {
    RuangInap ruangInap = RuangInap.findById(id);
    if (ruangInap == null) {
      JsonObject result = new JsonObject();
      result.put("status", "error");
      result.put("message", "Ruang Inap not found!!");

      return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
    }

    if (ruangInap.getIsKosong() == false) {
      JsonObject result = new JsonObject();
      result.put("status", "error");
      result.put("message", "Can't delete, Ruang Inap is used!!");

      return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
    }

    ruangInap.delete();

    JsonObject result = new JsonObject();
    result.put("status", "success");
    result.put("message", "Ruang Inap has been deleted");

    return Response.ok().entity(result).build();
  }
}
