package inzagher.expense.tracker.server.repository;

import inzagher.expense.tracker.server.model.BackupMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BackupMetadataRepository extends JpaRepository<BackupMetadata, Integer> {
}
