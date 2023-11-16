package dev.knowhowto.quarkus.restful.logger;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.core.UriInfo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class InboundHttpRequestLoggerTest {

  private static final InboundHttpRequestLogger CUT = new InboundHttpRequestLogger();

  @Mock
  ContainerRequestContext context;

  @Mock
  UriInfo uriInfo;

  @Mock
  SecurityContext securityContext;

  @Test
  void test() {
    Mockito.doReturn(uriInfo).when(context).getUriInfo();
    Mockito.doReturn(securityContext).when(context).getSecurityContext();
    CUT.preMatchingFilter(context);
  }
}
