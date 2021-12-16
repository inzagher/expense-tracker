package inzagher.expense.tracker.server.service;

import inzagher.expense.tracker.server.dto.CategoryReportItemDTO;
import inzagher.expense.tracker.server.dto.YearlyReportItemDTO;
import inzagher.expense.tracker.server.mapper.CategoryMapper;
import inzagher.expense.tracker.server.query.ExpenseQueryFilter;
import inzagher.expense.tracker.server.repository.CategoryRepository;
import inzagher.expense.tracker.server.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
        var categories = categoryRepository.findAll();
        for (var category: categories) {
            var filter = new ExpenseQueryFilter();
            filter.setDateFrom(LocalDate.of(year, month, 1));
            filter.setDateTo(filter.getDateFrom().plusMonths(1).minusDays(1));
            filter.getCategoryIdentifiers().add(category.getId());
            report.add(new CategoryReportItemDTO(categoryMapper.toDTO(category), expenseRepository.sum(filter)));
        }
        return report;
    }

    @Transactional
    public List<YearlyReportItemDTO> createYearlyReport(int year) {
        var report = new ArrayList<YearlyReportItemDTO>();
        for (int month = 1; month <= 12; ++month) {
            var filter = new ExpenseQueryFilter();
            filter.setDateFrom(LocalDate.of(year, month, 1));
            filter.setDateTo(filter.getDateFrom().plusMonths(1).minusDays(1));
            report.add(new YearlyReportItemDTO(month, expenseRepository.sum(filter)));
        }
        return report;
    }
}
