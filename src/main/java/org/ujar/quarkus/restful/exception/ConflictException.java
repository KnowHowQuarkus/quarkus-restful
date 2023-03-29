package org.ujar.quarkus.restful.exception;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;

public class ConflictException extends ClientErrorException {

  public ConflictException(final String msg) {
    super(msg, Response.Status.CONFLICT);
  }
}
