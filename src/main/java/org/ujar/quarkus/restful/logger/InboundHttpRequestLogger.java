package org.ujar.quarkus.restful.logger;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.MultivaluedMap;
import java.security.Principal;

import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.reactive.server.ServerRequestFilter;

@Slf4j
public class InboundHttpRequestLogger {

  /**
   * Logs all rest calls
   */
  @ServerRequestFilter
  public void preMatchingFilter(final ContainerRequestContext context) {
    final String method = context.getMethod();
    final String path = context.getUriInfo().getPath();
    final String address = context.getHeaderString("X-Forwarded-For");
    final Principal userPrincipal = context.getSecurityContext().getUserPrincipal();
    final String principal = userPrincipal == null ? "Unknown" : userPrincipal.getName();
    final String restCall = method + " " + path;
    final MultivaluedMap<String, String> queryParams = context.getUriInfo().getQueryParameters();

    log.info("[{}] hit [{}] from IP: {} Params: {}", principal, restCall, address, queryParams);
  }
}
