package ee.helmes;

import ee.helmes.parsers.DOMParser;
import ee.helmes.parsers.JAXBParser;
import ee.helmes.parsers.SAXParser;
import ee.helmes.parsers.STAXParser;
import ee.helmes.parsetype.ParsingType;

public class MainApplication {

    public static void main(String[] args){
        ParsingType parsingType = new ParsingType(new SAXParser());
        parsingType.setType(new DOMParser());
        parsingType.setType(new JAXBParser());
        parsingType.parse();
        parsingType.setType(new STAXParser());
    }
}
