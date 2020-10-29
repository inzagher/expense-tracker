package inzagher.expense.tracker.server.repository;

import inzagher.expense.tracker.server.model.BackupMetadata;
import java.util.Optional;

public interface BackupMetadataRepositoryExtension {
    Optional<BackupMetadata> last();
}
