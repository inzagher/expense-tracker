package inzagher.expense.tracker.server.service;

import inzagher.expense.tracker.server.model.dto.report.CategoryReportItemDTO;
import inzagher.expense.tracker.server.model.dto.report.YearlyReportItemDTO;
import inzagher.expense.tracker.server.model.exception.ExpenseTrackerException;
import inzagher.expense.tracker.server.model.mapper.CategoryMapper;
import inzagher.expense.tracker.server.repository.CategoryRepository;
import inzagher.expense.tracker.server.repository.ExpenseRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportService {
    private final CategoryRepository categoryRepository;
    private final ExpenseRepository expenseRepository;
    private final CategoryMapper categoryMapper;

    @Transactional
    public List<CategoryReportItemDTO> createCategoryReport(int year, int month) {
        var report = new ArrayList<CategoryReportItemDTO>();
        var periodFrom = LocalDate.of(year, month, 1);
        var periodTo = periodFrom.plusMonths(1);
        var categories = categoryRepository.findAll();
        var aggregates = toMap(expenseRepository.getCategoryReport(periodFrom, periodTo));
        for (var category : categories) {
            var item = new CategoryReportItemDTO();
            item.setCategory(categoryMapper.toDTO(category));
            item.setTotal(aggregates.getOrDefault(category.getId(), BigDecimal.ZERO));
            report.add(item);
        }
        return report;
    }

    @Transactional
    public List<YearlyReportItemDTO> createYearlyReport(int year) {
        var report = new ArrayList<YearlyReportItemDTO>();
        var yearStart =  LocalDate.of(year, 1, 1);
        var yearEnd = yearStart.plusYears(1);
        var aggregates = toMap(expenseRepository.getYearlyReport(yearStart, yearEnd));
        for (int month = 1; month <= 12; ++month) {
            var total = aggregates.getOrDefault((long) month, BigDecimal.ZERO);
            report.add(new YearlyReportItemDTO(month, total));
        }
        return report;
    }

    private Map<Long, BigDecimal> toMap(List<Object[]> table) {
        Map<Long, BigDecimal> result = new HashMap<>();
        for (Object[] row: table) {
            KeyValuePair<Long, BigDecimal> pair = new KeyValuePair<>();
            for (Object field : row) {
                if (field instanceof Long id) { pair.setKey(id); }
                else if (field instanceof Integer id) { pair.setKey(Long.valueOf(id)); }
                else if (field instanceof BigDecimal sum) { pair.setValue(sum); }
                else throw new ExpenseTrackerException("Invalid report item type");
            }
            result.put(pair.getKey(), pair.getValue());
        }
        return result;
    }

    @Getter
    @Setter
    private static class KeyValuePair<K, V> {
        private K key;
        private V value;
    }
}
