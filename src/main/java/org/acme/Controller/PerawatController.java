package org.acme.Controller;

import javax.annotation.security.RolesAllowed;
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

import org.acme.Service.PerawatService;

import io.vertx.core.json.JsonObject;

@Path("rumahsakit/perawat")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PerawatController {
  @Inject
  PerawatService perawatService;

  @POST
  @RolesAllowed({ "user", "superAdmin" })
  public Response create(JsonObject req) {
    return perawatService.add(req);
  }

  @GET
  public Response getAll(
      @QueryParam("nama") String nama,
      @QueryParam("email") String email,
      @QueryParam("phoneNumber") String phoneNumber,
      @QueryParam("page") int page) {
    return perawatService.getAll(nama, email, phoneNumber, page);
  }

  @PUT
  @Path("/{id}")
  public Response updateGaji(
      @PathParam("id") Long id,
      JsonObject req) {
    return perawatService.updateGaji(id, req);
  }
}
