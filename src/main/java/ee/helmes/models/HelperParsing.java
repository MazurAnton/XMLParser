package ee.helmes.models;

import java.util.ArrayList;
import java.util.List;

import static ee.helmes.parsers.ParserConstantes.*;

public class HelperParsing {

    private List<Category> categories;
    private List<SubCategory> subCategories;
    private List<Item> items;

    private Category category;
    private SubCategory subCategory;
    private Item item;

    public void startTag() {

    }

    public void endTag(String endElementName) {

        if (endElementName.equals(CATEGORY_TAG)) {
            if (getCategories() == null) {
                setCategories(new ArrayList<>());
            }
            getCategory().setSubcategories(getSubCategories());
            getCategories().add(getCategory());
            setSubCategories(new ArrayList<>());
        } else if (endElementName.equals(SUBCATEGORY_TAG)) {
            if (getSubCategories() == null) {
                setSubCategories(new ArrayList<>());
            }
            getSubCategory().setItems(getItems());
            getSubCategories().add(getSubCategory());
            setItems(new ArrayList<>());
        } else if (endElementName.equals(ITEM_TAG)) {
            if (getItems() == null) {
                setItems(new ArrayList<>());
            }
            getItem().setInStock();
            getItems().add(getItem());
        }
    }

    public void setItem(String startElementName, String parameter) {
        if (startElementName.equals(ITEM_PRODUCER_TAG)) {
            getItem().setProducer(parameter);
        } else if (startElementName.equals(ITEM_MODEL_TAG)) {
            getItem().setModel(parameter);
        } else if (startElementName.equals(ITEM_ISSUE_DTAE_TAG)) {
            getItem().setItemDate(parameter, ITEM_DATE_FORMAT);
        } else if (startElementName.equals(ITEM_COLOR_TAG)) {
            getItem().setColor(parameter);
        } else if (startElementName.equals(ITEM_PRICE_TAG)) {
            getItem().setPriceString(parameter);
        }
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<SubCategory> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<SubCategory> subCategories) {
        this.subCategories = subCategories;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public SubCategory getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
