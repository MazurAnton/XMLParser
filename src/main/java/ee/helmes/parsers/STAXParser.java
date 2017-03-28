package ee.helmes.parsers;

import ee.helmes.models.Category;
import ee.helmes.models.Item;
import ee.helmes.models.SubCategory;
import ee.helmes.parsetype.Type;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.util.ArrayList;
import java.util.List;

public class STAXParser implements Type, ParseConstants {

    private XMLEventReader eventReader;

    List<Category> categories = new ArrayList<>();
    List<SubCategory> subCategories = new ArrayList<>();
    List<Item> items = new ArrayList<>();

    Category category = new Category();
    SubCategory subCategory = new SubCategory();
    Item item = new Item();

    @Override
    public void parse() {
        try {
            XMLInputFactory factory = XMLInputFactory.newFactory().newInstance();
            XMLEventReader eventReader = factory.createXMLEventReader(ClassLoader.getSystemResourceAsStream(PARSE_FILE_NAME));

            this.eventReader = eventReader;

            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();

                checkStartElement();
                checkEndElement();
            }
            System.out.print(categories);
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }

    }

    private void checkStartElement() {

        try {
            XMLEvent event = eventReader.nextEvent();

            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                String startElementName = startElement.getName().getLocalPart();
                if (startElementName.equals(CATEGORY_TAG)) {
                    category = new Category();
                    Attribute attribute = startElement.getAttributeByName(new QName(CATEGORY_NAME_TAG));
                    category.setName(attribute.getValue());
                } else if (startElementName.equals(SUBCATEGORY_TAG)) {
                    subCategory = new SubCategory();
                    event = eventReader.nextEvent();
                    Attribute attribute = startElement.getAttributeByName(new QName(SUBCATEGORY_NAME_TAG));
                    subCategory.setName(attribute.getValue());
                } else if (startElementName.equals(ITEM_TAG)) {
                    item = new Item();
                    event = eventReader.nextEvent();
                } else setItem();

            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    private void setItem() {

        try {
            XMLEvent event = eventReader.nextEvent();

            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                String startElementName = startElement.getName().getLocalPart();

                if (startElementName.equals(ITEM_PRODUCER_TAG)) {
                    event = eventReader.nextEvent();
                    item.setProducer(event.asCharacters().getData());
                } else if (startElementName.equals(ITEM_MODEL_TAG)) {
                    event = eventReader.nextEvent();
                    item.setModel(event.asCharacters().getData());
                } else if (startElementName.equals(ITEM_ISSUE_DTAE_TAG)) {
                    event = eventReader.nextEvent();
                    item.setItemDate(event.asCharacters().getData(), ITEM_DATE_FORMAT);
                } else if (startElementName.equals(ITEM_COLOR_TAG)) {
                    event = eventReader.nextEvent();
                    item.setColor(event.asCharacters().getData());
                } else if (startElementName.equals(ITEM_PRICE_TAG)) {
                    event = eventReader.nextEvent();
                    item.setPriceString(event.asCharacters().getData());
                }
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    private void checkEndElement() {

        try {
            XMLEvent event = eventReader.nextEvent();

            if (event.isEndElement()) {
                EndElement endElement = event.asEndElement();
                String endElementName = endElement.getName().getLocalPart();
                if (endElementName.equals(CATEGORY_TAG)) {
                    category.setSubcategories(subCategories);
                    categories.add(category);
                    subCategories = new ArrayList<>();
                } else if (endElementName.equals(SUBCATEGORY_TAG)) {
                    subCategory.setItems(items);
                    subCategories.add(subCategory);
                    items = new ArrayList<>();
                } else if (endElementName.equals(ITEM_TAG)) {
                    items.add(item);
                } else if (endElementName.equals(ITEM_IN_STOCK_TAG)) {
                    event = eventReader.nextEvent();
                    String inStockString = event.asCharacters().getData();
                    Boolean inStock = Boolean.parseBoolean(inStockString);
                    item.setInStock(inStock);
                }
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }
}
