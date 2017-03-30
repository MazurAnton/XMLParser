package ee.helmes.parsers;

import ee.helmes.models.Category;
import ee.helmes.models.HelperParsing;
import ee.helmes.models.Item;
import ee.helmes.models.SubCategory;
import org.apache.log4j.Logger;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import static ee.helmes.parsers.ParserConstantes.*;

public class STAXParser implements Type {

    private static final Logger logger = Logger.getLogger(STAXParser.class);

    private XMLEventReader eventReader;
    private XMLEvent event;
    private HelperParsing helper = new HelperParsing();

    @Override
    public void parse() {
        try {
            XMLInputFactory factory = XMLInputFactory.newFactory().newInstance();
            XMLEventReader eventReader = factory.createXMLEventReader(ClassLoader.getSystemResourceAsStream(PARSE_FILE_NAME));

            this.eventReader = eventReader;

            while (eventReader.hasNext()) {
                event = eventReader.nextEvent();
                checkStartElement();
                checkEndElement();
            }
        } catch (XMLStreamException e) {
            logger.error(e);
        }
        System.out.print(helper.getCategories());
    }

    private void checkStartElement() {

        try {

            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                String startElementName = startElement.getName().getLocalPart();

                if (startElementName.equals(CATEGORY_TAG)) {
                    helper.setCategory(new Category());
                    Attribute attribute = startElement.getAttributeByName(new QName(CATEGORY_NAME_TAG));
                    helper.getCategory().setName(attribute.getValue());
                } else if (startElementName.equals(SUBCATEGORY_TAG)) {
                    helper.setSubCategory(new SubCategory());
                    event = eventReader.nextEvent();
                    Attribute attribute = startElement.getAttributeByName(new QName(SUBCATEGORY_NAME_TAG));
                    helper.getSubCategory().setName(attribute.getValue());
                } else if (startElementName.equals(ITEM_TAG)) {
                    helper.setItem(new Item());
                    event = eventReader.nextEvent();
                } else {
                    setItem();
                }
            }
        } catch (XMLStreamException e) {
            logger.error(e);
        }
    }

    private void setItem() {

        try {

            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                String startElementName = startElement.getName().getLocalPart();

                event = eventReader.nextEvent();
                String parameter = event.asCharacters().getData();

                helper.setItem(startElementName, parameter);
            }
        } catch (XMLStreamException e) {
            logger.error(e);
        }
    }

    private void checkEndElement() {

        if (event.isEndElement()) {
            EndElement endElement = event.asEndElement();
            String endElementName = endElement.getName().getLocalPart();
            helper.endTag(endElementName);
        }
    }
}
