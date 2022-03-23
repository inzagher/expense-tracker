package inzagher.expense.tracker.server.repository;

import inzagher.expense.tracker.server.model.entity.BackupMetadataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BackupMetadataRepository extends JpaRepository<BackupMetadataEntity, Integer> {
}
