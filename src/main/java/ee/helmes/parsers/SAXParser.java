package ee.helmes.parsers;

import ee.helmes.models.Category;
import ee.helmes.parsetype.Type;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SAXParser implements Type, ParseConstants {

    private static final Logger logger = Logger.getLogger(SAXParser.class);

    @Override
    public void parse() {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        javax.xml.parsers.SAXParser saxParser = null;
        try {
            saxParser = factory.newSAXParser();
            SAXHandler handler = new SAXHandler();
            saxParser.parse(ClassLoader.getSystemResourceAsStream(PARSE_FILE_NAME), handler);
            System.out.print(handler.getHelper().getCategories());
        } catch (ParserConfigurationException | SAXException | IOException e) {
            logger.error(e);
        }
    }
}


