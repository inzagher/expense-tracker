package inzagher.expense.tracker.server.repository;

import inzagher.expense.tracker.server.model.entity.NoteEntity;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<NoteEntity, Long> {

    @NonNull
    @Override
    @EntityGraph(attributePaths = { "person" })
    Optional<NoteEntity> findById(@NonNull Long id);

    @NonNull
    @Override
    @EntityGraph(attributePaths = { "person" })
    Page<NoteEntity> findAll(@NonNull Pageable pageable);
}
