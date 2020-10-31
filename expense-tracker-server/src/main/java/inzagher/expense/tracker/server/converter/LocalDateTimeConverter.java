package inzagher.expense.tracker.server.converter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Date> {
    private final ZoneId zone = ZoneId.systemDefault();
    @Override
    public Date convertToDatabaseColumn(LocalDateTime entityValue) {
        return entityValue == null ? null : Date.from(entityValue.atZone(zone).toInstant());
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Date databaseValue) {
        return databaseValue == null ? null : Instant.ofEpochMilli(databaseValue.getTime()).atZone(zone).toLocalDateTime();
    }
}
