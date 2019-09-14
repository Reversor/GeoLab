package io.github.reversor;

import io.netty.channel.Channel;
import java.net.URI;
import org.glassfish.jersey.netty.httpserver.NettyHttpContainerProvider;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

public class Main {

    private static final Class[] RESOURCE_CLASSES = {};
    private static final String[] RESOURCE_PACKAGES = {
            "io.github.reversor"
    };
    private static final URI BASE_URI = URI.create("http://localhost:4040");

    public static void main(String... args) {
        try {
            ResourceConfig resourceConfig = new ResourceConfig()
                    .packages(true, RESOURCE_PACKAGES)
                    .registerClasses(RESOURCE_CLASSES)
                    .property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);

            final Channel server = NettyHttpContainerProvider
                    .createHttp2Server(BASE_URI, resourceConfig, null);

            Runtime.getRuntime().addShutdownHook(new Thread(server::close));

            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
