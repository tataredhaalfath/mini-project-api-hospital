package org.acme.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;

import org.acme.Model.Obat;
import org.acme.Model.KategoriObat.ObatKategori;

import io.vertx.core.json.JsonObject;

@ApplicationScoped
public class ObatService {

  @Inject
  EntityManager em;

  public Response kategori() {
    List<String> obat_kategori = Stream.of(ObatKategori.values()).map(e -> e.name()).collect(Collectors.toList());

    JsonObject result = new JsonObject();
    result.put("status", "success");
    result.put("data", obat_kategori);

    return Response.ok().entity(result).build();
  }

  @Transactional
  public Response add(JsonObject req) {
    Obat obat = new Obat();

    String kategori = req.getString("obat_kategori");

    switch (kategori.toLowerCase()) {
      case "syrup":
        obat.setObat_kategori(ObatKategori.Syrup);
        break;
      case "pil":
        obat.setObat_kategori(ObatKategori.Pil);
        break;
      case "tablet":
        obat.setObat_kategori(ObatKategori.Tablet);
        break;
      case "cair":
        obat.setObat_kategori(ObatKategori.Cair);
        break;
      case "other":
        obat.setObat_kategori(ObatKategori.Other);
        break;
      default:
        JsonObject result = new JsonObject();
        result.put("status", "error");
        result.put("message", "Unknown Kategori Obat!!");
        return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
    }

    obat.setNamaObat(req.getString("nama_obat"));
    obat.setProduksi(req.getString("produksi"));
    obat.setDeskripsi(req.getString("deskripsi"));
    obat.persist();

    JsonObject result = new JsonObject();
    result.put("status", "success");
    result.put("data", obat);

    return Response.ok().entity(result).build();
  }

  public Response getAll(String nama_obat, String produksi, String obat_kategori, int page) {

    StringBuilder sb = new StringBuilder();
    sb.append("SELECT * ");
    sb.append("FROM miniproject.obat o ");
    sb.append("WHERE TRUE ");

    if (nama_obat != null) {
      sb.append("AND o.nama_obat = :nama_obat ");
    }
    if (produksi != null) {
      sb.append("AND o.produksi = :produksi ");
    }
    if (obat_kategori != null) {
      sb.append("AND o.obat_kategori = :obat_kategori ");
    }

    Query query = em.createNativeQuery(sb.toString(), Obat.class);

    if (nama_obat != null) {
      query.setParameter("nama_obat", nama_obat);
    }
    if (produksi != null) {
      query.setParameter("produksi", produksi);
    }
    if (obat_kategori != null) {
      query.setParameter("obat_kategori", obat_kategori);
    }

    // get total data
    List<Obat> total = query.getResultList();
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
    List<Obat> obats = query.getResultList();

    JsonObject result = new JsonObject();
    result.put("status", "success");
    result.put("data", obats);
    result.put("totalData", totalData);
    result.put("totalPage", tPage);
    result.put("maxDataPerPage", max);
    result.put("activePage", pageNow);

    return Response.ok().entity(result).build();

  }

  @Transactional
  public Response update(Long id, JsonObject req) {
    Obat obat = Obat.findById(id);

    if (obat == null) {
      JsonObject result = new JsonObject();
      result.put("status", "error");
      result.put("message", "Obat not found!!");

      return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
    }

    String kategori = req.getString("obat_kategori");

    switch (kategori.toLowerCase()) {
      case "syrup":
        obat.setObat_kategori(ObatKategori.Syrup);
        break;
      case "pil":
        obat.setObat_kategori(ObatKategori.Pil);
        break;
      case "tablet":
        obat.setObat_kategori(ObatKategori.Tablet);
        break;
      case "cair":
        obat.setObat_kategori(ObatKategori.Cair);
        break;
      case "other":
        obat.setObat_kategori(ObatKategori.Other);
        break;
      default:
        JsonObject result = new JsonObject();
        result.put("status", "error");
        result.put("message", "Unknown Kategori Obat!!");
        return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
    }

    obat.setNamaObat(req.getString("nama_obat"));
    obat.setProduksi(req.getString("produksi"));
    obat.setDeskripsi(req.getString("deskripsi"));
    obat.persist();

    JsonObject result = new JsonObject();
    result.put("status", "success");
    result.put("message", "Obat has been updated");
    result.put("data", obat);

    return Response.ok().entity(result).build();
  }

  @Transactional
  public Response drop(Long id) {
    Obat obat = Obat.findById(id);

    if (obat == null) {
      JsonObject result = new JsonObject();
      result.put("status", "error");
      result.put("message", "Obat not found!!");

      return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
    }

    obat.delete();

    JsonObject result = new JsonObject();
    result.put("status", "success");
    result.put("message", "Obat has been deleted");

    return Response.ok().entity(result).build();
  }
}
