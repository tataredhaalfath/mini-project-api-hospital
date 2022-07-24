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

import org.acme.Service.JadwalPraktikService;

import io.vertx.core.json.JsonObject;

@Path("rumahsakit/jadwal_praktik")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class JadwalPraktik {
  @Inject
  JadwalPraktikService jadwalPraktikService;

  @POST
  public Response create(JsonObject req) {
    return jadwalPraktikService.add(req);
  }

  @GET
  public Response getAll(
      @QueryParam("hari") String hari,
      @QueryParam("page") int page) {
    return jadwalPraktikService.getAll(hari, page);
  }

  @PUT
  @Path("/{id}")
  public Response update(
      @PathParam("id") Long id,
      JsonObject req) {
    return jadwalPraktikService.update(id, req);
  }

}
