package io.github.reversor.geolab.rest;

import io.github.reversor.geolab.entity.FOPWrapper;
import io.github.reversor.geolab.service.FOPService;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import javax.xml.transform.TransformerException;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.MimeConstants;

@Path("fop")
public class FOPEndpoint {

    private FOPService fopService;

    @Inject
    public FOPEndpoint(FOPService fopService) {
        this.fopService = fopService;
    }

    @POST
    @Path("pdf")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MimeConstants.MIME_PDF)
    public Response generatePDF(FOPWrapper fopWrapper) throws Exception {
        StreamingOutput streamingOutput = output -> {
            try {
                fopService.convertToPDF(fopWrapper.getXml(), fopWrapper.getXlst(), output);
            } catch (FOPException | TransformerException e) {
                throw new InternalServerErrorException(e);
            }
        };

        return Response.ok(streamingOutput, MimeConstants.MIME_PDF).build();
    }
}
