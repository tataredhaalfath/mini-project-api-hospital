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

import org.acme.Service.StaffService;

import io.vertx.core.json.JsonObject;

@Path("rumahsakit/staff")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StaffController {

  @Inject
  StaffService staffService;

  @POST
  @RolesAllowed({ "user" })
  public Response create(JsonObject req) {
    return staffService.add(req);
  }

  @GET
  @RolesAllowed({ "user" })
  public Response getAll(
      @QueryParam("nama") String nama,
      @QueryParam("email") String email,
      @QueryParam("phoneNumber") String phoneNumber,
      @QueryParam("page") int page) {
    return staffService.getAll(nama, email, phoneNumber, page);
  }

  @GET
  @RolesAllowed({ "user" })
  @Path("/posisi")
  public Response posisiList() {
    return staffService.listPosisi();
  }

  @DELETE
  @RolesAllowed({ "user" })
  @Path("/{id}")
  public Response destroy(@PathParam("id") Long id) {
    return staffService.drop(id);
  }

  @PUT
  @RolesAllowed({ "user" })
  @Path("/{id}")
  public Response updateGaji(
      @PathParam("id") Long id,
      JsonObject req) {
    return staffService.updateGaji(id, req);
  }

}
