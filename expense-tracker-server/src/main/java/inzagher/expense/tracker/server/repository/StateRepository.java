package inzagher.expense.tracker.server.repository;

import inzagher.expense.tracker.server.model.State;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends JpaRepository<State, UUID> {
}
