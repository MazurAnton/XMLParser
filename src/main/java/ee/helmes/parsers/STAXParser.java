package ee.helmes.parsers;

import ee.helmes.models.Category;
import ee.helmes.models.HelperParsing;
import ee.helmes.models.Item;
import ee.helmes.models.SubCategory;
import ee.helmes.parsetype.Type;
import org.apache.log4j.Logger;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.util.ArrayList;

public class STAXParser implements Type, ParseConstants {

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
            e.printStackTrace();
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
                } else setItem();

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

                if (startElementName.equals(ITEM_PRODUCER_TAG)) {
                    event = eventReader.nextEvent();
                    helper.getItem().setProducer(event.asCharacters().getData());
                } else if (startElementName.equals(ITEM_MODEL_TAG)) {
                    event = eventReader.nextEvent();
                    helper.getItem().setModel(event.asCharacters().getData());
                } else if (startElementName.equals(ITEM_ISSUE_DTAE_TAG)) {
                    event = eventReader.nextEvent();
                    helper.getItem().setItemDate(event.asCharacters().getData(), ITEM_DATE_FORMAT);
                } else if (startElementName.equals(ITEM_COLOR_TAG)) {
                    event = eventReader.nextEvent();
                    helper.getItem().setColor(event.asCharacters().getData());
                } else if (startElementName.equals(ITEM_PRICE_TAG)) {
                    event = eventReader.nextEvent();
                    helper.getItem().setPriceString(event.asCharacters().getData());
                }
            }
        } catch (XMLStreamException e) {
            logger.error(e);
        }
    }

    private void checkEndElement() {

        try {

            if (event.isEndElement()) {
                EndElement endElement = event.asEndElement();
                String endElementName = endElement.getName().getLocalPart();
                if (endElementName.equals(CATEGORY_TAG)) {
                    helper.getCategory().setSubcategories(helper.getSubCategories());
                    helper.getCategories().add(helper.getCategory());
                    helper.setSubCategories(new ArrayList<>());
                } else if (endElementName.equals(SUBCATEGORY_TAG)) {
                    helper.getSubCategory().setItems(helper.getItems());
                    helper.getSubCategories().add(helper.getSubCategory());
                    helper.setItems(new ArrayList<>());
                } else if (endElementName.equals(ITEM_TAG)) {
                    helper.getItems().add(helper.getItem());
                } else if (endElementName.equals(ITEM_IN_STOCK_TAG)) {
                    event = eventReader.nextEvent();
                    String inStockString = event.asCharacters().getData();
                    Boolean inStock = Boolean.parseBoolean(inStockString);
                    helper.getItem().setInStock(inStock);
                }
            }
        } catch (XMLStreamException e) {
            logger.error(e);
        }
    }
}
