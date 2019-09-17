package io.github.reversor;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.apache.commons.cli.*;
import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.uri.internal.JerseyUriBuilder;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

import java.net.URI;
import java.util.Optional;

@OpenAPIDefinition(info = @Info(description = "Geo lab"))
public class Main {

    private static final URI BASE_URI = new JerseyUriBuilder().scheme("http").host("localhost").port(4040).build();

    public static void main(String... args) throws ParseException {
        CommandLine parse = new DefaultParser()
                .parse(new Options().addOption(Option.builder("h").argName("Host").build()), args);

        URI uri = Optional.ofNullable(parse.getOptionValue('h')).map(URI::create).orElse(BASE_URI);

        try {
            Weld weld = new Weld();
            WeldContainer weldContainer = weld.initialize();
            
            Server server = ServerInitializer.create(uri);
            server.start();

            Runtime.getRuntime().addShutdownHook(new Thread(weld::shutdown));

            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
