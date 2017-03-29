package ee.helmes.models;

import ee.helmes.parsers.DateAdapter;
import ee.helmes.parsers.ParseConstants;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@XmlAccessorType(XmlAccessType.FIELD)
public class Item implements ParseConstants {

    @XmlElement(name = ITEM_PRODUCER_TAG)
    private String producer;

    @XmlElement(name = ITEM_MODEL_TAG)
    private String model;


    @XmlElement(name = ITEM_ISSUE_DTAE_TAG)
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date issueDate;

    @XmlElement(name = ITEM_COLOR_TAG)
    private String color;

    @XmlElement(name = ITEM_PRICE_TAG)
    private double price;

    @XmlElement(name = ITEM_IN_STOCK_TAG, defaultValue = "true")
    private boolean inStock;

    @Override
    public String toString() {
        return "\n" + "    Item{" +
                "producer='" + producer + '\'' +
                ", model='" + model + '\'' +
                ", issueDate=" + issueDate +
                ", color='" + color + '\'' +
                ", price=" + price +
                ", inStock=" + inStock +
                '}';
    }

    public void setItemDate(String issueDate, String dateFromat) {

        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern(dateFromat);
        try {
            setIssueDate(format.parse(issueDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setPriceString(String price) {
        setPrice(Double.parseDouble(price));
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        if (price < 0.00001) {
            this.inStock = true;
        } else {
            this.inStock = inStock;
        }
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
