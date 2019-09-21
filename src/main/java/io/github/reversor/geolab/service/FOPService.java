package io.github.reversor.geolab.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.inject.Singleton;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
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
            throws FOPException, TransformerException, IOException, ParserConfigurationException {
        convertToPDF(new ByteArrayInputStream(xml.getBytes()),
                new ByteArrayInputStream(xslt.getBytes()),
                out);
    }

    public void convertToPDF(InputStream xml, InputStream xslt, OutputStream out)
            throws IOException, FOPException, TransformerException, ParserConfigurationException {
        try (xml; xslt) {
            StreamSource xmlSource = new StreamSource(xml);
            StreamSource xsltSource = new StreamSource(xslt);

            FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
            FOUserAgent foUserAgent = fopFactory.newFOUserAgent();

            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);

            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory
                    .newTransformer(xsltSource);

            Result res = new SAXResult(fop.getDefaultHandler());

            DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            transformer.transform(xmlSource, res);
        }
    }

}
