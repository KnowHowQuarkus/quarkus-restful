package dev.knowhowto.quarkus.restful.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ConflictExceptionTest {

  @Test
  void test() {
    final ConflictException e = new ConflictException("message");
    assertEquals(409, e.getResponse().getStatus());
  }
}
