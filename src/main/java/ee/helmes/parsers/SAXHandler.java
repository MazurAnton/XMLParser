package ee.helmes.parsers;

import ee.helmes.models.Category;
import ee.helmes.models.HelperParsing;
import ee.helmes.models.Item;
import ee.helmes.models.SubCategory;
import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

public class SAXHandler extends DefaultHandler implements ParseConstants {

    private static final Logger logger = Logger.getLogger(SAXHandler.class);

    private HelperParsing helper = new HelperParsing();

    private boolean category = false;
    private boolean subCategory = false;
    private boolean item = false;
    private boolean itemProducer = false;
    private boolean itemModel = false;
    private boolean itemIssueDate = false;
    private boolean itemColor = false;
    private boolean itemPrice = false;
    private boolean itemInStock = false;

    public void startElement(String uri, String localName, String elementName, Attributes attributes)
            throws SAXException {
        if (elementName.equalsIgnoreCase(CATEGORY_TAG) && !category) {
            helper.getCategory().setName(attributes.getValue(CATEGORY_NAME_TAG));
            category = true;
        }

        if (elementName.equalsIgnoreCase(SUBCATEGORY_TAG) && !subCategory) {
            helper.getSubCategory().setName(attributes.getValue(SUBCATEGORY_NAME_TAG));
            subCategory = true;
        }

        if (elementName.equalsIgnoreCase(ITEM_TAG) && !item) {
            item = true;
        }

        if (elementName.equalsIgnoreCase(ITEM_PRODUCER_TAG)) {
            itemProducer = true;
        }

        if (elementName.equalsIgnoreCase(ITEM_MODEL_TAG)) {
            itemModel = true;
        }

        if (elementName.equalsIgnoreCase(ITEM_COLOR_TAG)) {
            itemColor = true;
        }

        if (elementName.equalsIgnoreCase(ITEM_ISSUE_DTAE_TAG)) {
            itemIssueDate = true;
        }

        if (elementName.equalsIgnoreCase(ITEM_PRICE_TAG)) {
            itemPrice = true;
        }
        if (elementName.equalsIgnoreCase(ITEM_IN_STOCK_TAG)) {
            itemInStock = true;
        }
    }

    public void characters(char ch[], int start, int length) {

        String parameter = new String(ch, start, length).trim();

        if (itemProducer) {
            itemProducer = false;
            helper.getItem().setProducer(parameter);
        }

        if (itemModel) {
            itemModel = false;
            helper.getItem().setModel(parameter);
        }

        if (itemIssueDate) {
            itemIssueDate = false;
            helper.getItem().setItemDate(parameter, ITEM_DATE_FORMAT);
        }

        if (itemColor) {
            itemColor = false;
            helper.getItem().setColor(parameter);
        }

        if (itemPrice) {
            itemPrice = false;
            helper.getItem().setPriceString(parameter);
        }

        if (itemInStock) {
            itemInStock = false;
            helper.getItem().setInStock(Boolean.parseBoolean(parameter));
        }

    }

    public void endElement(String uri, String localName, String elementName) {

        if (elementName.equalsIgnoreCase(SUBCATEGORY_TAG)) {
            subCategory = false;
            helper.getSubCategory().setItems(helper.getItems());
            helper.getSubCategories().add(helper.getSubCategory());
            helper.setItems(new ArrayList<>());
            helper.setSubCategory(new SubCategory());
        }

        if (elementName.equalsIgnoreCase(CATEGORY_TAG)) {
            category = false;
            helper.getCategory().setSubcategories(helper.getSubCategories());
            helper.getCategories().add(helper.getCategory());
            helper.setSubCategories(new ArrayList<>());
            helper.setCategory(new Category());
        }

        if (elementName.equalsIgnoreCase(ITEM_TAG)) {
            item = false;
            helper.getItems().add(helper.getItem());
            helper.setItem(new Item());
        }
    }

    public HelperParsing getHelper() {
        return helper;
    }
}
