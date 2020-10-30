package inzagher.expense.tracker.server.xml;

import java.time.LocalDateTime;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public final class LocalDateTimeXmlAdapter extends XmlAdapter<String, LocalDateTime> {
    @Override
    public LocalDateTime unmarshal(String v) throws Exception {
        return v == null || v.isEmpty() ? null : LocalDateTime.parse(v);
    }

    @Override
    public String marshal(LocalDateTime v) throws Exception {
        return v == null ? null : v.toString();
    }
}
