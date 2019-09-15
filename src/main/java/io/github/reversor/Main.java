package io.github.reversor;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.glassfish.jersey.uri.internal.JerseyUriBuilder;

import java.net.URI;
import java.util.Optional;

@OpenAPIDefinition(info = @Info(description = "Geo lab"))
public class Main {

    private static final URI BASE_URI = new JerseyUriBuilder().scheme("http").host("localhost").port(4040).build();

    public static void main(String... args) throws Exception {
        CommandLine parse = new DefaultParser()
                .parse(new Options().addOption(Option.builder("h").argName("Host").build()), args);

        URI uri = Optional.ofNullable(parse.getOptionValue('h')).map(URI::create).orElse(BASE_URI);

        ServerInitializer.create(uri).start();
    }

}
