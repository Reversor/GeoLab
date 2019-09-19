package io.github.reversor.geolab;

import io.swagger.v3.jaxrs2.integration.resources.AcceptHeaderOpenApiResource;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import java.net.URI;
import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

class ServerInitializer {

    private static final Class[] RESOURCE_CLASSES = {
            OpenApiResource.class,
            AcceptHeaderOpenApiResource.class
    };
    private static final String[] RESOURCE_PACKAGES = {
            "io.swagger.jax-rs2.integration",
            "io.github.reversor"
    };

    static Server create(URI uri) {
        ResourceConfig resourceConfig = new ResourceConfig()
                .packages(true, RESOURCE_PACKAGES)
                .registerClasses(RESOURCE_CLASSES)
                .property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);

        return JettyHttpContainerFactory.createServer(uri, resourceConfig);
    }

}
