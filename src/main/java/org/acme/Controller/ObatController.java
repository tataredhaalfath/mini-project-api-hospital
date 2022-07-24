package org.acme.Controller;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.acme.Service.ObatService;

import io.vertx.core.json.JsonObject;

@Path("rumahsakit/obat")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ObatController {

  @Inject
  ObatService obatService;

  @POST
  public Response create(JsonObject req) {
    return obatService.add(req);
  }

  @GET
  @Path("/kategori")
  public Response kategori() {
    return obatService.kategori();
  }

  @GET
  public Response getAll(
      @QueryParam("nama") String nama,
      @QueryParam("produksi") String produksi,
      @QueryParam("kategori") String kategori,
      @QueryParam("page") int page) {
    return obatService.getAll(nama, produksi, kategori, page);
  }

  @PUT
  @Path("/{id}")
  public Response update(@PathParam("id") Long id, JsonObject req) {
    return obatService.update(id, req);
  }

  @DELETE
  @Path("/{id}")
  public Response destroy(@PathParam("id") Long id) {
    return obatService.drop(id);
  }

}
