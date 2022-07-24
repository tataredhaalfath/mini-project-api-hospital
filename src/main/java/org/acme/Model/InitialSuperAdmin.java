package org.acme.Model;

import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;

@ApplicationScoped
public class InitialSuperAdmin {
  private static final Logger LOGGER = Logger.getLogger("ListenerBean");

  @Transactional
  void onStart(@Observes StartupEvent ev) {
    LOGGER.info("Application is starting...");

    List<User> users = User.findAll().list();

    Optional<User> getUser = users.stream().filter(u -> u.getEmail().equalsIgnoreCase("superAdmin@gmail.com"))
        .findFirst();
    if (getUser.isEmpty()) {
      String pass = "superAdmin";
      Set<String> permission = new HashSet<>();
      permission.add("superAdmin");

      User user = new User();
      user.setName("superAdmin");
      user.setUsername("superAdmin");
      user.setEmail("superAdmin@gmail.com");
      user.setUserType("superAdmin");
      user.setPassword(java.util.Base64.getEncoder().encodeToString(pass.getBytes(StandardCharsets.UTF_8)));
      user.setPhoneNumber("085325224829");
      user.setPermissionName(permission);
      user.persist();
    }

    Response.ok().build();
  }

  void onStop(@Observes ShutdownEvent ev) {
    LOGGER.info("The application is stopping...");
  }
}
