package io.github.reversor.rest;

import io.github.reversor.service.HelloService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("hello")
public class HelloEndpoint {

    private HelloService helloService;

    @Inject
    public HelloEndpoint(HelloService helloService) {
        this.helloService = helloService;
    }

    @GET
    public Response world() {
        return Response.ok(helloService.world()).build();
    }

}
