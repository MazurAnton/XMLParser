package ee.helmes.parsers;

import ee.helmes.models.Product;
import ee.helmes.parsetype.Type;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class JAXBParser implements Type, ParseConstants {

    @Override
    public void parse() {

        Product product = null;
        try {
            JAXBContext jc = JAXBContext.newInstance(Product.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();

            product = (Product) unmarshaller.unmarshal((ClassLoader.getSystemResourceAsStream(PARSE_FILE_NAME)));

            // build xml
            //Marshaller marshaller = jc.createMarshaller();
            //marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
           // marshaller.marshal(product, System.out);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        System.out.print(product);
    }
}
