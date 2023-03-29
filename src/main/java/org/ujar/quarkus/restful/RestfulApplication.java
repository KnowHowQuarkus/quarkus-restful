package org.ujar.quarkus.restful;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.eclipse.microprofile.auth.LoginConfig;

/**
 * Top level entry point for the REST services.
 */
@LoginConfig(authMethod = "MP-JWT", realmName = "ujar")
@ApplicationPath("/")
public class RestfulApplication extends Application {
}
