package ee.helmes.parsers;
import ee.helmes.models.Product;
import org.apache.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import static ee.helmes.parsers.ParserConstantes.PARSE_FILE_NAME;

public class JAXBParser implements Type {

    private static final Logger logger = Logger.getLogger(JAXBParser.class);

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
            logger.error(e);
        }
        System.out.print(product);
    }
}
