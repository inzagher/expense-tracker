package inzagher.expense.tracker.server.mapper;

import inzagher.expense.tracker.server.dto.BackupMetadataDTO;
import inzagher.expense.tracker.server.model.BackupMetadata;
import org.mapstruct.Mapper;

@Mapper
public interface BackupMetadataMapper {
    BackupMetadataDTO toDTO(BackupMetadata model);
}
