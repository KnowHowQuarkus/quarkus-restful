package org.ujar.quarkus.restful.exception;

import javax.ws.rs.ClientErrorException;

public class UnprocessableEntityException extends ClientErrorException {

  public UnprocessableEntityException(final String msg) {
    // See https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/422
    super(msg, 422);
  }
}
