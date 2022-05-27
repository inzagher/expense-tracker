package inzagher.expense.tracker.server.repository;

import inzagher.expense.tracker.server.model.entity.CategoryEntity;
import lombok.NonNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    @NonNull
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "STATIC_DATA")
    List<CategoryEntity> findAll();
}
