package ee.helmes.models;

import ee.helmes.parsers.ParseConstants;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class SubCategory implements ParseConstants {

    @XmlAttribute(name = SUBCATEGORY_NAME_TAG)
    private String name;


    @XmlElement(name = ITEM_TAG)
    List<Item> items = new ArrayList<>();

    @Override
    public String toString() {
        return "\n" + "  SubCategory{" +
                "name='" + name + '\'' +
                "," + "items=" + items +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
