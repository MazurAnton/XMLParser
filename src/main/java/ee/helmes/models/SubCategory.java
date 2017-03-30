package ee.helmes.models;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

import static ee.helmes.parsers.ParserConstantes.ITEM_TAG;
import static ee.helmes.parsers.ParserConstantes.SUBCATEGORY_NAME_TAG;

@XmlAccessorType(XmlAccessType.FIELD)
public class SubCategory {

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
