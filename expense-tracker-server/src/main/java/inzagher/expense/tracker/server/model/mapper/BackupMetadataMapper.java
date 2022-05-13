package inzagher.expense.tracker.server.model.mapper;

import inzagher.expense.tracker.server.model.dto.BackupMetadataDTO;
import inzagher.expense.tracker.server.model.entity.BackupMetadataEntity;
import org.mapstruct.Mapper;

@Mapper
public interface BackupMetadataMapper {
    BackupMetadataDTO toDTO(BackupMetadataEntity entity);
}
