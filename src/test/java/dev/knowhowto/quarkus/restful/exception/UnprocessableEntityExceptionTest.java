package dev.knowhowto.quarkus.restful.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class UnprocessableEntityExceptionTest {

  @Test
  void test() {
    final UnprocessableEntityException e = new UnprocessableEntityException("message");
    assertEquals(422, e.getResponse().getStatus());
  }
}
