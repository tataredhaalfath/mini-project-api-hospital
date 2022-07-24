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

import org.acme.Service.DokterService;
import io.vertx.core.json.JsonObject;

@Path("rumahsakit/dokter")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DokterController {
  @Inject
  DokterService dokterService;

  @POST
  @RolesAllowed({ "user", "superAdmin" })
  public Response create(JsonObject req) {
    return dokterService.add(req);
  }

  @GET
  public Response getAll(
      @QueryParam("nama") String nama,
      @QueryParam("email") String email,
      @QueryParam("phoneNumber") String phoneNumber,
      @QueryParam("spesialis") String spesialis,
      @QueryParam("page") int page) {
    return dokterService.getAll(nama, email, phoneNumber, spesialis, page);
  }


  @PUT
  @Path("/{id}")
  public Response updateGaji(
      @PathParam("id") Long id,
      JsonObject req) {
    return dokterService.updateGaji(id, req);
  }

}
