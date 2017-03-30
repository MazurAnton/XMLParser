package ee.helmes.parsers;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.SimpleDateFormat;
import java.util.Date;

import static ee.helmes.parsers.ParserConstantes.ITEM_DATE_FORMAT;

public class DateAdapter extends XmlAdapter<String, Date> {

    private SimpleDateFormat dateFormat = new SimpleDateFormat(ITEM_DATE_FORMAT);
    @Override
    public Date unmarshal(String v) throws Exception {
        return dateFormat.parse(v);
    }

    @Override
    public String marshal(Date v) throws Exception {
        return dateFormat.format(v);
    }
}
