package ee.helmes.models;

import java.util.ArrayList;
import java.util.List;

public class HelperParsing {

    private List<Category> categories = new ArrayList<>();
    private List<SubCategory> subCategories = new ArrayList<>();
    private List<Item> items = new ArrayList<>();

    private Category category = new Category();
    private SubCategory subCategory = new SubCategory();
    private Item item = new Item();

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
