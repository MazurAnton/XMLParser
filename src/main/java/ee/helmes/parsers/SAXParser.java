package ee.helmes.parsers;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

public class SAXParser implements Type {

    private static final Logger logger = Logger.getLogger(SAXParser.class);

    @Override
    public void parse() {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        javax.xml.parsers.SAXParser saxParser = null;
        try {
            saxParser = factory.newSAXParser();
            // SAXHandler handler = new SAXHandler();
            BetterSAXHandler handler = new BetterSAXHandler();
            saxParser.parse(ClassLoader.getSystemResourceAsStream(ParserConstantes.PARSE_FILE_NAME), handler);
            System.out.print(handler.getHelper().getCategories());
        } catch (ParserConfigurationException | SAXException | IOException e) {
            logger.error(e);
        }
    }
}


