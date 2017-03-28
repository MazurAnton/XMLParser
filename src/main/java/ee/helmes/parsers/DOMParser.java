package ee.helmes.parsers;

import ee.helmes.models.Category;
import ee.helmes.models.Item;
import ee.helmes.models.SubCategory;
import ee.helmes.parsetype.Type;
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

    private static final int FIRST_ELEMENT = 0;

    @Override
    public void parse() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document document = null;
        List<Category> categories;

        try {
            builder = factory.newDocumentBuilder();
            document = builder.parse(ClassLoader.getSystemResourceAsStream(PARSE_FILE_NAME));
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        categories = findCategories(document);
        System.out.print(categories);
    }

    private static List<Category> findCategories(Document document) {

        List<Category> categories = new ArrayList<>();

        NodeList categoriesAndTextNodes = document.getDocumentElement().getChildNodes();
        for (int i = 0; i < categoriesAndTextNodes.getLength(); i++) {
            Node categoryNode = categoriesAndTextNodes.item(i);
            if (categoryNode instanceof Element) {
                Category category = new Category();
                String categoryName = categoryNode.getAttributes().getNamedItem(CATEGORY_NAME_TAG).getNodeValue();

                category.setName(categoryName);
                category.setSubcategories(findSubCategories(categoryNode));
                categories.add(category);
            }
        }
        return categories;
    }

    private static List<SubCategory> findSubCategories(Node categoryNode) {

        List<SubCategory> subCategories = new ArrayList<>();

        NodeList subCategoriesAndTextNodes = categoryNode.getChildNodes();
        for (int j = 0; j < subCategoriesAndTextNodes.getLength(); j++) {
            Node subCategoryNode = subCategoriesAndTextNodes.item(j);

            if (subCategoryNode instanceof Element) {
                SubCategory subCategory = new SubCategory();
                String subCategoryName = subCategoryNode.getAttributes()
                        .getNamedItem(SUBCATEGORY_NAME_TAG).getNodeValue();

                subCategory.setName(subCategoryName);
                subCategory.setItems(findItems(subCategoryNode));
                subCategories.add(subCategory);
            }
        }
        return subCategories;
    }

    private static List<Item> findItems(Node subCategoryNode) {

        List<Item> items = new ArrayList<>();

        NodeList itemsAndTextNodes = subCategoryNode.getChildNodes();
        for (int k = 0; k < itemsAndTextNodes.getLength(); k++) {
            Node itemNode = itemsAndTextNodes.item(k);
            if (itemNode instanceof Element) {
                Item item = new Item();

                setItem(item, itemNode);
                items.add(item);
            }
        }
        return items;
    }

    private static void setItem(Item item, Node itemNode) {
        Element element = (Element) itemNode;
        String producer = element.getElementsByTagName(ITEM_PRODUCER_TAG).item(FIRST_ELEMENT).getTextContent();
        String model = element.getElementsByTagName(ITEM_MODEL_TAG).item(FIRST_ELEMENT).getTextContent();
        String issueDate = element.getElementsByTagName(ITEM_ISSUE_DTAE_TAG).item(FIRST_ELEMENT).getTextContent();
        String color = element.getElementsByTagName(ITEM_COLOR_TAG).item(FIRST_ELEMENT).getTextContent();
        String price = null;
        String inStock = null;
        if (element.getElementsByTagName(ITEM_PRICE_TAG).item(FIRST_ELEMENT) != null) {
            price = element.getElementsByTagName(ITEM_PRICE_TAG).item(FIRST_ELEMENT).getTextContent();
        }
        if (element.getElementsByTagName(ITEM_IN_STOCK_TAG).item(FIRST_ELEMENT) != null) {
            inStock = element.getElementsByTagName(ITEM_IN_STOCK_TAG).item(FIRST_ELEMENT).getTextContent();
        }

        item.setProducer(producer);
        item.setModel(model);
        item.setItemDate(issueDate, ITEM_DATE_FORMAT);
        item.setColor(color);
        item.setPriceString(price);
        item.setInStock(Boolean.parseBoolean(inStock));
    }
}