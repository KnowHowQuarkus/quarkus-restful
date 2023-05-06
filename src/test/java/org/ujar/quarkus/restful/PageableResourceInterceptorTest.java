package org.ujar.quarkus.restful;

import jakarta.interceptor.InvocationContext;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.InternalServerErrorException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.JRE;
import org.mockito.Mockito;

@EnabledOnJre({ JRE.JAVA_18 })
class PageableResourceInterceptorTest {
  private InvocationContext context;

  static final PageableResourceInterceptor CUT = new PageableResourceInterceptor();

  @BeforeEach
  void setUp() {
    this.context = Mockito.mock(InvocationContext.class);
  }

  @Test
  void validate() throws Exception {
    Mockito.doReturn(Foo.class.getMethods()[0]).when(context).getMethod();
    Mockito.doReturn(new Object[] {0, 9}).when(context).getParameters();
    CUT.validateQueryParams(context);
  }

  @Test
  void validate_InternalServerError_NoInterface() {
    Mockito.doReturn(FooNoInterface.class.getMethods()[0]).when(context).getMethod();
    Assertions.assertThrows(InternalServerErrorException.class, () -> CUT.validateQueryParams(context));
  }

  @Test
  void validate_InternalServerError_MissingAnnotations() {
    Mockito.doReturn(FooMissingAnnotations.class.getMethods()[0]).when(context).getMethod();
    Assertions.assertThrows(InternalServerErrorException.class, () -> CUT.validateQueryParams(context));
  }

  @Test
  void validate_InternalServerError_NoDefaultValues() {
    Mockito.doReturn(Foo.class.getMethods()[0]).when(context).getMethod();
    Mockito.doReturn(new Object[] {null, null}).when(context).getParameters();
    Assertions.assertThrows(InternalServerErrorException.class, () -> CUT.validateQueryParams(context));
  }

  @Test
  void validate_BadRequest_PageSize() {
    Mockito.doReturn(Foo.class.getMethods()[0]).when(context).getMethod();
    Mockito.doReturn(new Object[] {1, 150}).when(context).getParameters();
    Assertions.assertThrows(BadRequestException.class, () -> CUT.validateQueryParams(context));
  }

  @Test
  void validate_BadRequest_NegativePageNumber() {
    Mockito.doReturn(Foo.class.getMethods()[0]).when(context).getMethod();
    Mockito.doReturn(new Object[] {-1, 1}).when(context).getParameters();
    Assertions.assertThrows(BadRequestException.class, () -> CUT.validateQueryParams(context));
  }

  @Test
  void validate_BadRequest_NegativePageSize() {
    Mockito.doReturn(Foo.class.getMethods()[0]).when(context).getMethod();
    Mockito.doReturn(new Object[] {1, -1}).when(context).getParameters();
    Assertions.assertThrows(BadRequestException.class, () -> CUT.validateQueryParams(context));
  }
}
