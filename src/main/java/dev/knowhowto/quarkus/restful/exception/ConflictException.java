package dev.knowhowto.quarkus.restful.exception;

import jakarta.ws.rs.ClientErrorException;
import jakarta.ws.rs.core.Response;

public class ConflictException extends ClientErrorException {

  public ConflictException(final String msg) {
    super(msg, Response.Status.CONFLICT);
  }
}
