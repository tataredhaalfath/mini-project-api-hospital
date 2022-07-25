package org.acme.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;

import org.acme.Model.User;
import org.acme.Utils.GenerateToken;

import io.vertx.core.json.JsonObject;

@ApplicationScoped
public class UserService {

  public Response login(JsonObject req) {
    String username = req.getString("username");
    String password = req.getString("password");

    // User user = User.find("username = ?1", username).singleResult();
    List<User> users = User.findAll().list();
    Optional<User> getUser = users.stream().filter(u -> u.getUsername().equalsIgnoreCase(username))
        .findFirst();

    if (getUser.isEmpty()) {
      JsonObject result = new JsonObject();
      result.put("status", "error");
      result.put("message", "username not found!");
      return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
    }
    User user = getUser.get();

    if (!user.getPassword()
        .equalsIgnoreCase(Base64.getEncoder().encodeToString(password.getBytes(StandardCharsets.UTF_8)))) {
      JsonObject result = new JsonObject();
      result.put("status", "error");
      result.put("message", "wrong password!");
      return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
    }

    JsonObject data = new JsonObject();
    data.put("name", user.getName());
    data.put("username", user.getUsername());
    data.put("email", user.getEmail());
    data.put("phone_number", user.getPhoneNumber());
    data.put("user_type", user.getUserType());
    data.put("token", GenerateToken.generate(user));

    JsonObject result = new JsonObject();
    result.put("status", "success");
    result.put("data", data);

    return Response.ok().entity(result).build();
  }

  @Transactional
  public Response register(JsonObject req) {
    String name = req.getString("name");
    String username = req.getString("username");
    String password = req.getString("password");
    String email = req.getString("email");
    String phoneNumber = req.getString("phoneNumber");
    String userType = req.getString("userType");
    Set<String> permissions = new HashSet<>(req.getJsonArray("permissions").getList());

    // validation
    List<User> users = User.findAll().list();

    Optional<User> userNameExist = users.stream().filter(u -> u.getUsername().equalsIgnoreCase(username))
        .findFirst();

    Optional<User> emailExist = users.stream().filter(u -> u.getEmail().equalsIgnoreCase(email))
        .findFirst();

    if (!userNameExist.isEmpty()) {
      JsonObject result = new JsonObject();
      result.put("status", "error");
      result.put("message", "Username already exist!");
      return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
    }

    if (!emailExist.isEmpty()) {
      JsonObject result = new JsonObject();
      result.put("status", "error");
      result.put("message", "Email already exist!");
      return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
    }

    User user = new User();
    user.setName(name);
    user.setUsername(username);
    user.setPassword(Base64.getEncoder().encodeToString(password.getBytes(StandardCharsets.UTF_8)));
    user.setEmail(email);
    user.setPhoneNumber(phoneNumber);
    user.setUserType(userType);
    user.setPermissionName(permissions);
    user.persist();

    JsonObject data = new JsonObject();
    data.put("name", user.getName());
    data.put("username", user.getUsername());
    data.put("email", user.getEmail());
    data.put("phone_number", user.getPhoneNumber());
    data.put("user_type", user.getUserType());

    JsonObject result = new JsonObject();
    result.put("status", "success");
    result.put("data", data);

    return Response.ok().entity(result).build();
  }

}
