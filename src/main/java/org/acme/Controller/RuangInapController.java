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

import org.acme.Service.RuangInapService;

import io.vertx.core.json.JsonObject;

@Path("rumahsakit/ruang_inap")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RuangInapController {
  @Inject
  RuangInapService ruangInapService;

  @GET
  @RolesAllowed({ "user" })
  @Path("/kategori")
  public Response kategori() {
    return ruangInapService.getKategori();
  }

  @POST
  @RolesAllowed({ "user" })
  public Response create(JsonObject req) {
    return ruangInapService.add(req);
  }

  @GET
  @RolesAllowed({ "user" })
  public Response getAll(
      @QueryParam("prefix") String prefix,
      @QueryParam("nomor") String nomor,
      @QueryParam("page") int page) {
    return ruangInapService.getAll(prefix, nomor, page);
  }

  @PUT
  @RolesAllowed({ "user" })
  @Path("/{id}")
  public Response update(
      @PathParam("id") Long id,
      JsonObject req) {
    return ruangInapService.update(id, req);
  }

  @DELETE
  @RolesAllowed({ "user" })
  @Path("/{id}")
  public Response destroy(@PathParam("id") Long id) {
    return ruangInapService.drop(id);
  }

}
