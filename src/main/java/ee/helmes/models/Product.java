package ee.helmes.models;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

import static ee.helmes.parsers.ParserConstantes.CATEGORY_TAG;

@XmlRootElement(name="products")
@XmlAccessorType(XmlAccessType.FIELD)
public class Product {

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
