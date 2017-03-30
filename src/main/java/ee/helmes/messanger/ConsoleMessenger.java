package ee.helmes.messanger;

import ee.helmes.parsers.DOMParser;
import ee.helmes.parsers.JAXBParser;
import ee.helmes.parsers.SAXParser;
import ee.helmes.parsers.STAXParser;
import ee.helmes.parsers.Type;

import java.util.Scanner;

public class ConsoleMessenger {

    private static final int SAX_CHOOSING_NUMBER = 1;
    private static final int STAX_CHOOSING_NUMBER = 2;
    private static final int DOM_CHOOSING_NUMBER = 3;
    private static final int JAXB_CHOOSING_NUMBER = 4;
    private static final String SAX_PARSER_NAME = "SAX";
    private static final String STAX_PARSER_NAME = "STAX";
    private static final String DOM_PARSER_NAME = "DOM";
    private static final String JAXB_PARSER_NAME = "JAXB";
    private static final String INVITE_TO_CHOOSE_TYPE_MESSAGE =
            "To choose parse type, input the next number:" +
                    " " + SAX_PARSER_NAME + "(" + SAX_CHOOSING_NUMBER + ")," +
                    " " + STAX_PARSER_NAME + "(" + STAX_CHOOSING_NUMBER + ")," +
                    " " + DOM_PARSER_NAME + "(" + DOM_CHOOSING_NUMBER + ")," +
                    " " + JAXB_PARSER_NAME + "(" + JAXB_CHOOSING_NUMBER + ")";
    private static final String YOU_CHOOSED_MESSAGE = "You choosed parser ";
    private static final String INVITE_WRITE_NUMBER = "Please, write the number ";
    private static final String TABULET = "\n";

    private Type parserType;

    public void print() {
        Scanner in;

        while (true) {
            System.out.println(INVITE_TO_CHOOSE_TYPE_MESSAGE);
            in = new Scanner(System.in);
            if (in.hasNextInt()) {
                printParsingResult(in);
            } else {
                System.out.println(INVITE_WRITE_NUMBER);
            }
        }
    }

    private void printParsingResult(Scanner in) {
        int numberParsingType = in.nextInt();
        setAction(numberParsingType);
        execute();
        System.out.print(TABULET + TABULET);
    }

    private void setAction(int numberParsingType) {
        switch (numberParsingType) {
            case SAX_CHOOSING_NUMBER:
                parserType = new SAXParser();
                System.out.println(YOU_CHOOSED_MESSAGE + SAX_PARSER_NAME);
                break;
            case STAX_CHOOSING_NUMBER:
                parserType = new STAXParser();
                System.out.println(YOU_CHOOSED_MESSAGE + STAX_PARSER_NAME);
                break;
            case DOM_CHOOSING_NUMBER:
                parserType = new DOMParser();
                System.out.println(YOU_CHOOSED_MESSAGE + DOM_PARSER_NAME);
                break;
            case JAXB_CHOOSING_NUMBER:
                parserType = new JAXBParser();
                System.out.println(YOU_CHOOSED_MESSAGE + JAXB_PARSER_NAME);
                break;
        }
    }

    private void execute() {
        if (parserType != null) {
            parserType.parse();
            parserType = null;
        }
    }
}
