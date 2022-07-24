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

import org.acme.Service.DaftarRawatInapService;

import io.vertx.core.json.JsonObject;

@Path("rumahsakit/daftar_rawat_inap")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DaftarRawatInapController {
  @Inject
  DaftarRawatInapService daftarRawatInapService;

  @POST
  public Response create(JsonObject req) {
    return daftarRawatInapService.add(req);
  }

  @PUT
  @Path("/checkout/{id}")
  public Response checkout(@PathParam("id") Long id) {
    return daftarRawatInapService.checkout(id);
  }

  @GET
  public Response getAll(
      @QueryParam("nama") String nama,
      @QueryParam("ruang") String ruang,
      @QueryParam("page") int page) {
    return daftarRawatInapService.getAll(nama, ruang, page);
  }
}
