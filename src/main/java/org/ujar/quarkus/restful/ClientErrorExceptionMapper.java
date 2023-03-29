package org.ujar.quarkus.restful;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.ujar.quarkus.restful.model.ClientError;

/**
 * If the app has thrown a ClientErrorException with an exception message then include
 * the message in the body of the HTTP error response. This helps clients to understand
 * what's gone wrong.
 */
@Provider
@Slf4j
public class ClientErrorExceptionMapper implements ExceptionMapper<ClientErrorException> {

  private static final ObjectMapper MAPPER = new ObjectMapper();

  @Override
  @Produces(MediaType.APPLICATION_JSON)
  public Response toResponse(final ClientErrorException e) {
    Response.ResponseBuilder rb = Response.status(e.getResponse().getStatus());
    if (e.getMessage() != null) {
      final var body = ClientError.builder().message(e.getMessage()).build();
      try {
        // If we don't manually generate the string ourselves then in some
        // cases (notably when the client doesn't request application/json)
        // the framework just does a .toString() on the body object.
        rb = rb.entity(MAPPER.writeValueAsString(body));
      } catch (final JsonProcessingException e2) {
        log.info("Error generating String", e2);
      }
    }
    return rb.build();
  }
}
