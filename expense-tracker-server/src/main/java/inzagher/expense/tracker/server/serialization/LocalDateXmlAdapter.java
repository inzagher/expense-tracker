package inzagher.expense.tracker.server.serialization;

import java.time.LocalDate;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public final class LocalDateXmlAdapter extends XmlAdapter<String, LocalDate> {
    @Override
    public LocalDate unmarshal(String v) throws Exception {
        return v == null || v.isEmpty() ? null : LocalDate.parse(v);
    }

    @Override
    public String marshal(LocalDate v) throws Exception {
        return v == null ? null : v.toString();
    }
}
