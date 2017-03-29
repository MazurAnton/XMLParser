package ee.helmes.parsers;

import ee.helmes.models.Category;
import ee.helmes.models.HelperParsing;
import ee.helmes.models.Item;
import ee.helmes.models.SubCategory;
import ee.helmes.parsetype.Type;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class DOMParser implements Type, ParseConstants {

    private static final Logger logger = Logger.getLogger(DOMParser.class);
    private static final int FIRST_ELEMENT = 0;
    private HelperParsing helper = new HelperParsing();

    @Override
    public void parse() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document document = null;
        List<Category> categories;

        try {
            builder = factory.newDocumentBuilder();
            document = builder.parse(ClassLoader.getSystemResourceAsStream(PARSE_FILE_NAME));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            logger.error(e);
        }

        categories = findCategories(document);
        System.out.print(categories);
    }

    private List<Category> findCategories(Document document) {

        helper.setCategories(new ArrayList<>());

        NodeList categoriesAndTextNodes = document.getDocumentElement().getChildNodes();
        for (int i = 0; i < categoriesAndTextNodes.getLength(); i++) {
            Node categoryNode = categoriesAndTextNodes.item(i);
            if (categoryNode instanceof Element) {
                helper.setCategory(new Category());
                String categoryName = categoryNode.getAttributes().getNamedItem(CATEGORY_NAME_TAG).getNodeValue();

                helper.getCategory().setName(categoryName);
                helper.getCategory().setSubcategories(findSubCategories(categoryNode));
                helper.getCategories().add(helper.getCategory());
            }
        }
        return helper.getCategories();
    }

    private List<SubCategory> findSubCategories(Node categoryNode) {

        helper.setSubCategories(new ArrayList<>());

        NodeList subCategoriesAndTextNodes = categoryNode.getChildNodes();
        for (int j = 0; j < subCategoriesAndTextNodes.getLength(); j++) {
            Node subCategoryNode = subCategoriesAndTextNodes.item(j);

            if (subCategoryNode instanceof Element) {
                helper.setSubCategory(new SubCategory());
                String subCategoryName = subCategoryNode.getAttributes()
                        .getNamedItem(SUBCATEGORY_NAME_TAG).getNodeValue();

                helper.getSubCategory().setName(subCategoryName);
                helper.getSubCategory().setItems(findItems(subCategoryNode));
                helper.getSubCategories().add(helper.getSubCategory());
            }
        }
        return helper.getSubCategories();
    }

    private List<Item> findItems(Node subCategoryNode) {

        helper.setItems(new ArrayList<>());

        NodeList itemsAndTextNodes = subCategoryNode.getChildNodes();
        for (int k = 0; k < itemsAndTextNodes.getLength(); k++) {
            Node itemNode = itemsAndTextNodes.item(k);
            if (itemNode instanceof Element) {
                helper.setItem(new Item());

                setItem(itemNode);
                helper.getItems().add(helper.getItem());
            }
        }
        return helper.getItems();
    }

    private void setItem( Node itemNode) {
        Element element = (Element) itemNode;
        String producer = element.getElementsByTagName(ITEM_PRODUCER_TAG).item(FIRST_ELEMENT).getTextContent();
        String model = element.getElementsByTagName(ITEM_MODEL_TAG).item(FIRST_ELEMENT).getTextContent();
        String issueDate = element.getElementsByTagName(ITEM_ISSUE_DTAE_TAG).item(FIRST_ELEMENT).getTextContent();
        String color = element.getElementsByTagName(ITEM_COLOR_TAG).item(FIRST_ELEMENT).getTextContent();

        if (element.getElementsByTagName(ITEM_PRICE_TAG).item(FIRST_ELEMENT) != null) {
            String price = element.getElementsByTagName(ITEM_PRICE_TAG).item(FIRST_ELEMENT).getTextContent();
            helper.getItem().setPriceString(price);
        }
        if (element.getElementsByTagName(ITEM_IN_STOCK_TAG).item(FIRST_ELEMENT) != null) {
            String inStock = element.getElementsByTagName(ITEM_IN_STOCK_TAG).item(FIRST_ELEMENT).getTextContent();
            helper.getItem().setInStock(Boolean.parseBoolean(inStock));
        }

        helper.getItem().setProducer(producer);
        helper.getItem().setModel(model);
        helper.getItem().setItemDate(issueDate, ITEM_DATE_FORMAT);
        helper.getItem().setColor(color);
    }
}