package io.github.reversor.geolab.rest;

import io.github.reversor.geolab.entity.FOPWrapper;
import io.github.reversor.geolab.service.FOPService;
import java.io.BufferedReader;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.MimeConstants;

@Path("fop")
public class FOPEndpoint {

    private static final String EMPTY_XML = "<?xml version=\"1.0\" encoding=\"utf-8\"?><metadata></metadata>";
    private FOPService fopService;

    @Inject
    public FOPEndpoint(FOPService fopService) {
        this.fopService = fopService;
    }

    @POST
    @Path("pdf")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MimeConstants.MIME_PDF)
    public Response generatePDF(FOPWrapper fopWrapper) {
        StreamingOutput streamingOutput = output -> {
            try {
                fopService.convertToPDF(fopWrapper.getXml(), fopWrapper.getXlst(), output);
            } catch (FOPException | TransformerException | ParserConfigurationException e) {
                throw new InternalServerErrorException(e);
            }
        };

        return Response.ok(streamingOutput, MimeConstants.MIME_PDF).build();
    }

    @GET
    @Path("example")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MimeConstants.MIME_PDF)
    public Response examplePDF() {
        StreamingOutput streamingOutput = output -> {
            try {
                BufferedReader xslt = Files.newBufferedReader(Paths.get("fop/PurchaseOrder.xsl"));
                fopService.convertToPDF(new StringReader(EMPTY_XML), xslt, output);
            } catch (Exception e) {
                throw new InternalServerErrorException(e);
            }
        };

        return Response.ok(streamingOutput, MimeConstants.MIME_PDF).build();
    }
}
