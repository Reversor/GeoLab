package io.github.reversor.geolab.service;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
import javax.inject.Singleton;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.FopFactoryBuilder;
import org.apache.fop.apps.MimeConstants;

@Singleton
public class FOPService {

    private static final FopFactory fopFactory = new FopFactoryBuilder(URI.create("."))
            .setStrictFOValidation(false)
            .setPageWidth("210mm")
            .setPageHeight("297mm")
            .build();


    public void convertToPDF(String xml, String xslt, OutputStream out)
            throws FOPException, TransformerException, ParserConfigurationException, IOException {
        convertToPDF(new StringReader(xml), new StringReader(xslt), out);
    }

    public void convertToPDF(Reader xml, Reader xslt, OutputStream out)
            throws FOPException, TransformerException, ParserConfigurationException, IOException {
        try (xml; xslt) {
            FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);

            TransformerFactory factory = TransformerFactory.newInstance();
            //TODO add templates caching
            Templates templates = factory.newTemplates(new StreamSource(xslt));
            Transformer transformer = templates.newTransformer();

            Result res = new SAXResult(fop.getDefaultHandler());

            DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            transformer.transform(new StreamSource(xml), res);
        }
    }

}
