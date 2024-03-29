package io.github.reversor.geolab;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import java.net.URI;
import java.util.Map;
import java.util.Optional;
import javax.inject.Named;
import javax.sql.DataSource;
import javax.ws.rs.core.GenericType;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.inject.cdi.se.CdiSeInjectionManagerFactory;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.uri.internal.JerseyUriBuilder;
import org.postgresql.ds.PGSimpleDataSource;

@OpenAPIDefinition(info = @Info(description = "Geo lab"))
public class Main {

    private static final URI BASE_URI = new JerseyUriBuilder().scheme("http").host("localhost")
            .port(4040).build();

    public static void main(String... args) throws ParseException {
        CommandLine parse = new DefaultParser()
                .parse(new Options().addOption(Option.builder("h").argName("Host").build()), args);

        URI uri = Optional.ofNullable(parse.getOptionValue('h')).map(URI::create).orElse(BASE_URI);

        try {
            Server server = ServerInitializer.create(uri);
            server.start();

            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
