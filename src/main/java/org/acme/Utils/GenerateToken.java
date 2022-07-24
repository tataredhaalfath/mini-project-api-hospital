package org.acme.Utils;

import org.acme.Model.User;

import io.smallrye.jwt.build.Jwt;

public class GenerateToken {
  public static String generate(User user) {
    return Jwt.issuer("http://kawahedukasi/issuer")
        .expiresIn(600L)
        .upn(user.getUsername())
        .groups(user.getPermissionName())
        .claim("email", "redha@email.com")
        .sign();
  }
}
