package ee.helmes.models;

import ee.helmes.parsers.ParseConstants;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Category implements ParseConstants {

    @XmlAttribute(name = CATEGORY_NAME_TAG)
    private String name;

    @XmlElement(name = SUBCATEGORY_TAG)
    List<SubCategory> subcategories = new ArrayList<>();

    @Override
    public String toString() {
        return "\n" + "Category{" +
                "name='" + name + '\'' +
                "," + "\n" + "subcategories=" + subcategories +
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
