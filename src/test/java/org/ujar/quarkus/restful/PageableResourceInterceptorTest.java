package org.ujar.quarkus.restful;

import javax.interceptor.InvocationContext;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.QueryParam;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PageableResourceInterceptorTest {

  @Mock
  InvocationContext context;

  private interface FooInterface {
    void doSomething(
        @QueryParam("page") @DefaultValue("1") Integer page,
        @QueryParam("size") @DefaultValue("50") Integer size);
  }

  private static class Foo implements FooInterface {
    public void doSomething(Integer page, Integer size) {
    }
  }

  private static class FooNoInterface {
    public void doSomething(Integer start, Integer end) {
    }
  }

  private interface FooInterfaceMissingAnnotations {
    void doSomething(Integer start, Integer end);
  }

  private static class FooMissingAnnotations implements FooInterfaceMissingAnnotations {
    public void doSomething(Integer start, Integer end) {
    }
  }

  private static final PageableResourceInterceptor CUT = new PageableResourceInterceptor();

  @Test
  void validate() throws Exception {
    Mockito.doReturn(Foo.class.getMethods()[0]).when(context).getMethod();
    Mockito.doReturn(new Object[] {0, 9}).when(context).getParameters();
    CUT.validateQueryParams(context);
  }

  @Test
  void validate_InternalServerError_NoInterface() throws Exception {
    Mockito.doReturn(FooNoInterface.class.getMethods()[0]).when(context).getMethod();
    Assertions.assertThrows(InternalServerErrorException.class, () -> CUT.validateQueryParams(context));
  }

  @Test
  void validate_InternalServerError_MissingAnnotations() throws Exception {
    Mockito.doReturn(FooMissingAnnotations.class.getMethods()[0]).when(context).getMethod();
    Assertions.assertThrows(InternalServerErrorException.class, () -> CUT.validateQueryParams(context));
  }

  @Test
  void validate_InternalServerError_NoDefaultValues() throws Exception {
    Mockito.doReturn(Foo.class.getMethods()[0]).when(context).getMethod();
    Mockito.doReturn(new Object[] {null, null}).when(context).getParameters();
    Assertions.assertThrows(InternalServerErrorException.class, () -> CUT.validateQueryParams(context));
  }

  @Test
  void validate_BadRequest_PageSize() throws Exception {
    Mockito.doReturn(Foo.class.getMethods()[0]).when(context).getMethod();
    Mockito.doReturn(new Object[] {1, 150}).when(context).getParameters();
    Assertions.assertThrows(BadRequestException.class, () -> CUT.validateQueryParams(context));
  }

  @Test
  void validate_BadRequest_NegativePageNumber() throws Exception {
    Mockito.doReturn(Foo.class.getMethods()[0]).when(context).getMethod();
    Mockito.doReturn(new Object[] {-1, 1}).when(context).getParameters();
    Assertions.assertThrows(BadRequestException.class, () -> CUT.validateQueryParams(context));
  }

  @Test
  void validate_BadRequest_NegativePageSize() throws Exception {
    Mockito.doReturn(Foo.class.getMethods()[0]).when(context).getMethod();
    Mockito.doReturn(new Object[] {1, -1}).when(context).getParameters();
    Assertions.assertThrows(BadRequestException.class, () -> CUT.validateQueryParams(context));
  }
}
