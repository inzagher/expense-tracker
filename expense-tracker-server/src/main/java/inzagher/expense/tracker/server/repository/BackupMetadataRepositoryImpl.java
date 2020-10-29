package inzagher.expense.tracker.server.repository;

import inzagher.expense.tracker.server.model.BackupMetadata;
import inzagher.expense.tracker.server.model.Expense;
import java.util.ArrayList;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

@Repository
public class BackupMetadataRepositoryImpl implements BackupMetadataRepositoryExtension {
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public Optional<BackupMetadata> last() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<BackupMetadata> cq = cb.createQuery(BackupMetadata.class);
        Root<BackupMetadata> root = cq.from(BackupMetadata.class);
        
        cq.select(root);
        cq.orderBy(cb.desc(root.get("time")));
        
        TypedQuery<BackupMetadata> query = entityManager.createQuery(cq).setMaxResults(1);
        BackupMetadata metadata = query.getSingleResult();
        return metadata == null ? Optional.empty() : Optional.of(metadata);
    }
}
