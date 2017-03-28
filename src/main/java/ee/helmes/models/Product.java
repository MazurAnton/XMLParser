package ee.helmes.models;

import ee.helmes.parsers.ParseConstants;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name="products")
@XmlAccessorType(XmlAccessType.FIELD)
public class Product implements ParseConstants{

    @XmlElement(name = CATEGORY_TAG)
    List<Category> categories;

    @Override
    public String toString() {
        return "categories=" + categories +
                '}';
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
