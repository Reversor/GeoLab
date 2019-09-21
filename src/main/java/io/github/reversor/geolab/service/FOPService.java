package io.github.reversor.geolab.service;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import javax.inject.Singleton;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;

@Singleton
public class FOPService {

    public void convertToPDF(String xml, String xslt, OutputStream out)
            throws FOPException, TransformerException, IOException {
        try (ByteArrayInputStream xmlStream = new ByteArrayInputStream(xml.getBytes());
                ByteArrayInputStream xlstStream = new ByteArrayInputStream(xslt.getBytes())) {
            StreamSource xmlSource = new StreamSource(xmlStream);
            StreamSource xsltSource = new StreamSource(xlstStream);

            FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
            FOUserAgent foUserAgent = fopFactory.newFOUserAgent();

            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);

            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory
                    .newTransformer(xsltSource);

            Result res = new SAXResult(fop.getDefaultHandler());

            transformer.transform(xmlSource, res);
        }
    }

}
