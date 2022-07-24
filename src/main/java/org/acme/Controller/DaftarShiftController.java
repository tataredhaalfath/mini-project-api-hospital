package org.acme.Controller;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.acme.Service.DaftarShiftService;

import io.vertx.core.json.JsonObject;

@Path("rumahsakit/daftar_shift")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DaftarShiftController {

  @Inject
  DaftarShiftService shiftService;

  @POST
  public Response create(JsonObject req) {
    return shiftService.add(req);
  }

  @GET
  public Response getAll(
      @QueryParam("kategori") String kategori,
      @QueryParam("page") int page) {
    return shiftService.getAll(kategori, page);
  }

  @PUT
  @Path("/{id}")
  public Response update(
      @PathParam("id") Long id,
      JsonObject req) {
    return shiftService.update(id, req);
  }

}
