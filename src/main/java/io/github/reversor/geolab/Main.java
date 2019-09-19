package io.github.reversor.geolab;

import io.github.reversor.geolab.producer.LiquibaseProducer;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import java.lang.reflect.Type;
import javax.sql.DataSource;
import org.apache.commons.cli.*;
import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.uri.internal.JerseyUriBuilder;
import org.jboss.weld.bean.ContextualInstance;
import org.jboss.weld.contexts.CreationalContextImpl;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

import java.net.URI;
import java.util.Optional;
import org.postgresql.ds.PGSimpleDataSource;

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

            PGSimpleDataSource pgSimpleDataSource = new PGSimpleDataSource();

            Server server = ServerInitializer.create(uri);
            server.start();

            Runtime.getRuntime().addShutdownHook(new Thread(weld::shutdown));

            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
