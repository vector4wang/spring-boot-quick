package com.quick.tika;

import org.apache.commons.io.IOUtils;
import org.apache.tika.exception.TikaException;
import org.apache.tika.extractor.DocumentSelector;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.AbstractParser;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.html.BoilerpipeContentHandler;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.ContentHandlerDecorator;
import org.apache.tika.sax.TeeContentHandler;
import org.apache.tika.sax.XHTMLContentHandler;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TikaUtil {


    /**
     * Parsing context.
     */
    private static ParseContext context;

    /**
     * Configured parser instance.
     */
    private static Parser parser;

    /**
     * Captures requested embedded images
     */
    private static ImageSavingParser imageParser;

    /**
     * init method after server complete
     */
    static {
        context = new ParseContext();
        parser = new AutoDetectParser();
        imageParser = new ImageSavingParser(parser);
        context.set(DocumentSelector.class, new ImageDocumentSelector());
        context.set(Parser.class, imageParser);
    }

    public static String handleStreamContent(byte[] file)
            throws Exception {
        Metadata md = new Metadata();
        TikaInputStream input = TikaInputStream.get(file, md);
        StringWriter textBuffer = new StringWriter();
        StringBuilder metadataBuffer = new StringBuilder();

        ContentHandler handler = new TeeContentHandler(
                getTextContentHandler(textBuffer)
        );
        parser.parse(input, handler, md, context);
        return textBuffer.toString();
    }

    public static Map<String, String> handleStreamMetaDate(byte[] file)
            throws Exception {
        Map<String, String> meta = new HashMap<>();
        Metadata md = new Metadata();
        TikaInputStream input = TikaInputStream.get(file, md);
        StringWriter textBuffer = new StringWriter();

        ContentHandler handler = new TeeContentHandler(
                getTextContentHandler(textBuffer)
        );
        parser.parse(input, handler, md, context);

        String[] names = md.names();
        Arrays.sort(names);
        for (String name : names) {
            meta.put(name, md.get(name));
        }
        return meta;
    }

    private static ContentHandler getHtmlHandler(Writer writer)
            throws TransformerConfigurationException {
        SAXTransformerFactory factory = (SAXTransformerFactory)
                SAXTransformerFactory.newInstance();
        TransformerHandler handler = factory.newTransformerHandler();
        handler.getTransformer().setOutputProperty(OutputKeys.METHOD, "html");
        handler.setResult(new StreamResult(writer));
        return new ContentHandlerDecorator(handler) {
            @Override
            public void startElement(
                    String uri, String localName, String name, Attributes atts)
                    throws SAXException {
                if (XHTMLContentHandler.XHTML.equals(uri)) {
                    uri = null;
                }
                if (!"head".equals(localName)) {
                    if ("img".equals(localName)) {
                        AttributesImpl newAttrs;
                        if (atts instanceof AttributesImpl) {
                            newAttrs = (AttributesImpl) atts;
                        } else {
                            newAttrs = new AttributesImpl(atts);
                        }

                        for (int i = 0; i < newAttrs.getLength(); i++) {
                            if ("src".equals(newAttrs.getLocalName(i))) {
                                String src = newAttrs.getValue(i);
                                if (src.startsWith("embedded:")) {
                                    String filename = src.substring(src.indexOf(':') + 1);
                                    try {
                                        File img = imageParser.requestSave(filename);
                                        String newSrc = img.toURI().toString();
                                        newAttrs.setValue(i, newSrc);
                                    } catch (IOException e) {
                                        System.err.println("Error creating temp image file " + filename);
                                        // The html viewer will show a broken image too to alert them
                                    }
                                }
                            }
                        }
                        super.startElement(uri, localName, name, newAttrs);
                    } else {
                        super.startElement(uri, localName, name, atts);
                    }
                }
            }

            @Override
            public void endElement(String uri, String localName, String name)
                    throws SAXException {
                if (XHTMLContentHandler.XHTML.equals(uri)) {
                    uri = null;
                }
                if (!"head".equals(localName)) {
                    super.endElement(uri, localName, name);
                }
            }

            @Override
            public void startPrefixMapping(String prefix, String uri) {
            }

            @Override
            public void endPrefixMapping(String prefix) {
            }
        };
    }

    private static ContentHandler getTextContentHandler(Writer writer) {
        return new BodyContentHandler(writer);
    }

    private static ContentHandler getTextMainContentHandler(Writer writer) {
        return new BoilerpipeContentHandler(writer);
    }

    private static ContentHandler getXmlContentHandler(Writer writer)
            throws TransformerConfigurationException {
        SAXTransformerFactory factory = (SAXTransformerFactory)
                SAXTransformerFactory.newInstance();
        TransformerHandler handler = factory.newTransformerHandler();
        handler.getTransformer().setOutputProperty(OutputKeys.METHOD, "xml");
        handler.setResult(new StreamResult(writer));
        return handler;
    }

    /**
     * A {@link DocumentSelector} that accepts only images.
     */
    private static class ImageDocumentSelector implements DocumentSelector {
        public boolean select(Metadata metadata) {
            String type = metadata.get(Metadata.CONTENT_TYPE);
            return type != null && type.startsWith("image/");
        }
    }

    /**
     * A recursive parser that saves certain images into the temporary
     * directory, and delegates everything else to another downstream
     * parser.
     */
    private static class ImageSavingParser extends AbstractParser {
        private Map<String, File> wanted = new HashMap<String, File>();
        private Parser downstreamParser;
        private File tmpDir;

        private ImageSavingParser(Parser downstreamParser) {
            this.downstreamParser = downstreamParser;

            try {
                File t = File.createTempFile("tika", ".test");
                tmpDir = t.getParentFile();
            } catch (IOException e) {
            }
        }

        public File requestSave(String embeddedName) throws IOException {
            String suffix = ".tika";

            int splitAt = embeddedName.lastIndexOf('.');
            if (splitAt > 0) {
                embeddedName.substring(splitAt);
            }

            File tmp = File.createTempFile("tika-embedded-", suffix);
            wanted.put(embeddedName, tmp);
            return tmp;
        }

        public Set<MediaType> getSupportedTypes(ParseContext context) {
            // Never used in an auto setup
            return null;
        }

        public void parse(InputStream stream, ContentHandler handler,
                          Metadata metadata, ParseContext context) throws IOException,
                SAXException, TikaException {
            String name = metadata.get(Metadata.RESOURCE_NAME_KEY);
            if (name != null && wanted.containsKey(name)) {
                FileOutputStream out = new FileOutputStream(wanted.get(name));
                IOUtils.copy(stream, out);
                out.close();
            } else {
                if (downstreamParser != null) {
                    downstreamParser.parse(stream, handler, metadata, context);
                }
            }
        }

    }

}
