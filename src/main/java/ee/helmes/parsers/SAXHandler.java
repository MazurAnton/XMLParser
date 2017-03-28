package ee.helmes.parsers;

import ee.helmes.models.Category;
import ee.helmes.models.Item;
import ee.helmes.models.SubCategory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class SAXHandler extends DefaultHandler implements ParseConstants {

    private List<Category> categories;
    private List<SubCategory> subCategories = new ArrayList<>();
    private List<Item> items = new ArrayList<>();
    private Item oItem = new Item();
    private SubCategory oSubCategory = new SubCategory();
    private Category oCategory = new Category();

    private boolean category = false;
    private boolean subCategory = false;
    private boolean item = false;
    private boolean itemProducer = false;
    private boolean itemModel = false;
    private boolean itemIssueDate = false;
    private boolean itemColor = false;
    private boolean itemPrice = false;
    private boolean itemInStock = false;

    public SAXHandler(List<Category> categories) {
        this.categories = categories;
    }

    public void startElement(String uri, String localName, String elementName, Attributes attributes)
            throws SAXException {
        if (elementName.equalsIgnoreCase(CATEGORY_TAG) && !category) {
            oCategory.setName(attributes.getValue(CATEGORY_NAME_TAG));
            category = true;
        }

        if (elementName.equalsIgnoreCase(SUBCATEGORY_TAG) && !subCategory) {
            oSubCategory.setName(attributes.getValue(SUBCATEGORY_NAME_TAG));
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
            oItem.setProducer(parameter);
        }

        if (itemModel) {
            itemModel = false;
            oItem.setModel(parameter);
        }

        if (itemIssueDate) {
            itemIssueDate = false;
            oItem.setItemDate(parameter, ITEM_DATE_FORMAT);
        }

        if (itemColor) {
            itemColor = false;
            oItem.setColor(parameter);
        }

        if (itemPrice) {
            itemPrice = false;
        }

        if (itemInStock) {
            itemInStock = false;
            oItem.setInStock(Boolean.parseBoolean(parameter));
        }

    }

    public void endElement(String uri, String localName, String elementName) {

        if (elementName.equalsIgnoreCase(SUBCATEGORY_TAG)) {
            subCategory = false;
            oSubCategory.setItems(items);
            subCategories.add(oSubCategory);
            items = new ArrayList<>();
            oSubCategory = new SubCategory();
        }

        if (elementName.equalsIgnoreCase(CATEGORY_TAG) ){
            category = false;
            oCategory.setSubcategories(subCategories);
            categories.add(oCategory);
            subCategories = new ArrayList<>();
            oCategory = new Category();
        }

        if (elementName.equalsIgnoreCase(ITEM_TAG)) {
            item = false;
            items.add(oItem);
            oItem = new Item();
        }
    }
}
