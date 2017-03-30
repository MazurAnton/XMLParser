package ee.helmes.models;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

import static ee.helmes.parsers.ParserConstantes.CATEGORY_NAME_TAG;
import static ee.helmes.parsers.ParserConstantes.SUBCATEGORY_TAG;

@XmlAccessorType(XmlAccessType.FIELD)
public class Category {

    @XmlAttribute(name = CATEGORY_NAME_TAG)
    private String name;

    @XmlElement(name = SUBCATEGORY_TAG)
    List<SubCategory> subcategories = new ArrayList<>();

    @Override
    public String toString() {
        return "\n" + "Category{" +
                "name='" + name + '\'' +
                "," + "\n" + " subcategories=" + subcategories +
                '}' + "\n";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SubCategory> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(List<SubCategory> subcategories) {
        this.subcategories = subcategories;
    }
}
