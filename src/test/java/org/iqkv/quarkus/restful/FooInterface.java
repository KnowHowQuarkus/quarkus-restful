package org.iqkv.quarkus.restful;

import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;

interface FooInterface {
  void doSomething(
      @QueryParam("page") @DefaultValue("1") Integer page,
      @QueryParam("size") @DefaultValue("50") Integer size);
}
