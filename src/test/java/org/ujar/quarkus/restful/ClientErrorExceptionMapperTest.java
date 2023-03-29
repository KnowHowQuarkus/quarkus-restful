package org.ujar.quarkus.restful;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;

import org.junit.jupiter.api.Test;

class ClientErrorExceptionMapperTest {

  private static final ClientErrorExceptionMapper CUT = new ClientErrorExceptionMapper();

  @Test
  void test() {
    final BadRequestException e = new BadRequestException("you did something wrong!");
    final Response response = CUT.toResponse(e);
    assertEquals(400, response.getStatus());
    final String responseBody = (String) response.getEntity();
    assertEquals("{\"message\":\"you did something wrong!\"}", responseBody);
  }

  @Test
  void test_NoMessage() {
    final ClientErrorException e = new ClientErrorException(null, 400);
    final Response response = CUT.toResponse(e);
    assertEquals(400, response.getStatus());
    assertEquals(-1, response.getLength());
  }
}
