package org.acme.Controller;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.acme.Service.UserService;
import org.eclipse.microprofile.jwt.JsonWebToken;

import io.vertx.core.json.JsonObject;

@Path("rumahsakit/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserController {
  @Inject
  UserService userService;

  @Inject
  JsonWebToken jwt;

  @POST
  @Path("/login")
  public Response login(JsonObject req) {
    return userService.login(req);
  }

  @POST
  @RolesAllowed({ "superAdmin" })
  @Path("/register")
  public Response register(JsonObject req) {
    return userService.register(req);
  }

}
