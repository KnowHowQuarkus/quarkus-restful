package org.ujar.quarkus.restful.model;

import lombok.Builder;
import lombok.Getter;

@Builder
public class ClientError {

  @Getter
  private final String message;
}
