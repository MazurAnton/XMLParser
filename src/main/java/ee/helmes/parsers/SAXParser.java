package ee.helmes.parsers;

import ee.helmes.models.Category;
import ee.helmes.parsetype.Type;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SAXParser implements Type, ParseConstants {

    @Override
    public void parse() {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        javax.xml.parsers.SAXParser saxParser = null;
        List<Category> categories = new ArrayList<>();
        try {
            saxParser = factory.newSAXParser();
            SAXHandler handler = new SAXHandler(categories);
            saxParser.parse(ClassLoader.getSystemResourceAsStream(PARSE_FILE_NAME), handler);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        System.out.print(categories);
    }
}


