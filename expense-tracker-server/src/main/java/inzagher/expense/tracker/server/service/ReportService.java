package inzagher.expense.tracker.server.service;

import inzagher.expense.tracker.server.model.dto.CategoryReportItemDTO;
import inzagher.expense.tracker.server.model.dto.YearlyReportItemDTO;
import inzagher.expense.tracker.server.model.mapper.CategoryMapper;
import inzagher.expense.tracker.server.repository.CategoryRepository;
import inzagher.expense.tracker.server.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
        for (var category : categories) {
            var periodFrom = LocalDate.of(year, month, 1);
            var periodTo = periodFrom.plusMonths(1).minusDays(1);
            var totalAmount = expenseRepository.getTotalAmountForCategory(
                    category.getId(), periodFrom, periodTo);
            var item = new CategoryReportItemDTO(categoryMapper.toDTO(category),
                    totalAmount.orElse(BigDecimal.ZERO));
            report.add(item);
        }
        return report;
    }

    @Transactional
    public List<YearlyReportItemDTO> createYearlyReport(int year) {
        var report = new ArrayList<YearlyReportItemDTO>();
        for (int month = 1; month <= 12; ++month) {
            var periodFrom =  LocalDate.of(year, month, 1);
            var periodTo = periodFrom.plusMonths(1).minusDays(1);
            var totalAmount = expenseRepository.getTotalAmount(periodFrom, periodTo);
            report.add(new YearlyReportItemDTO(month, totalAmount.orElse(BigDecimal.ZERO)));
        }
        return report;
    }
}
