package org.acme.Controller;

import javax.annotation.security.RolesAllowed;
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

import org.acme.Service.PasienService;

import io.vertx.core.json.JsonObject;

@Path("rumahsakit/pasien")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PasienController {

  @Inject
  PasienService pasienService;

  @POST
  @RolesAllowed({ "user" })
  public Response create(JsonObject req) {
    return pasienService.add(req);
  }

  @GET
  @RolesAllowed("user")
  public Response getAll(
      @QueryParam("nama") String nama,
      @QueryParam("email") String email,
      @QueryParam("phoneNumber") String phoneNumber,
      @QueryParam("page") int page) {
    return pasienService.getAll(nama, email, phoneNumber, page);
  }

  @PUT
  @RolesAllowed({ "user" })
  @Path("/{id}")
  public Response update(
      @PathParam("id") Long id,
      JsonObject req) {
    return pasienService.update(id, req);
  }

  @DELETE
  @RolesAllowed({ "user" })
  @Path("/{id}")
  public Response destroy(@PathParam("id") Long id) {
    return pasienService.drop(id);
  }

}
