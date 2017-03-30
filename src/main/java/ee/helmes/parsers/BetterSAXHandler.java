package ee.helmes.parsers;

import ee.helmes.models.Category;
import ee.helmes.models.HelperParsing;
import ee.helmes.models.Item;
import ee.helmes.models.SubCategory;
import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.Stack;

import static ee.helmes.parsers.ParserConstantes.*;

public class BetterSAXHandler extends DefaultHandler {

    private static final Logger logger = Logger.getLogger(BetterSAXHandler.class);

    private Stack<String> elementStack = new Stack<String>();
    private Stack<Object> objectStack = new Stack<Object>();
    private HelperParsing helper = new HelperParsing();

    public void startElement(String uri, String localName, String elementName, Attributes attributes)
            throws SAXException {

        elementStack.push(elementName);

        if (elementName.equals(CATEGORY_TAG)) {
            helper.setCategory(new Category());
            helper.getCategory().setName(attributes.getValue(CATEGORY_NAME_TAG));
        } else if (elementName.equals(SUBCATEGORY_TAG)) {
            helper.setSubCategory(new SubCategory());
            helper.getSubCategory().setName(attributes.getValue(SUBCATEGORY_NAME_TAG));
        } else if (elementName.equals(ITEM_TAG)) {
            helper.setItem(new Item());
        }
    }

    public void characters(char ch[], int start, int length) {

        String parameter = new String(ch, start, length).trim();

        helper.setItem(currentElement(), parameter);
    }

    public void endElement(String uri, String localName, String elementName) {

        elementStack.pop();
        helper.endTag(elementName);
    }

    private String currentElement() {
        return elementStack.peek();
    }

    public HelperParsing getHelper() {
        return helper;
    }
}
