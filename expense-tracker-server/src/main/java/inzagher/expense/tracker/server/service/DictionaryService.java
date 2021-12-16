package inzagher.expense.tracker.server.service;

import inzagher.expense.tracker.server.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DictionaryService {
    private final ExpenseRepository expenseRepository;

    public List<String> findExpenseDescriptions(String pattern, int minCount) {
        log.debug("Find expense descriptions. Pattern: '{}'. Min count: {}", pattern, minCount);
        return expenseRepository.findDescriptionsByPattern(pattern, minCount);
    }
}
