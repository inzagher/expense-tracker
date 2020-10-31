package inzagher.expense.tracker.server.converter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LocalDateConverter implements AttributeConverter<LocalDate, Date> {
    private final ZoneId zone = ZoneId.systemDefault();
    @Override
    public Date convertToDatabaseColumn(LocalDate entityValue) {
        return entityValue == null ? null : Date.from(entityValue.atStartOfDay(zone).toInstant());
    }

    @Override
    public LocalDate convertToEntityAttribute(Date databaseValue) {
        return databaseValue == null ? null : Instant.ofEpochMilli(databaseValue.getTime()).atZone(zone).toLocalDate();
    }
}
