package inzagher.expense.tracker.server.service;

import inzagher.expense.tracker.server.dto.CategoryReportItemDTO;
import inzagher.expense.tracker.server.dto.YearlyReportItemDTO;
import inzagher.expense.tracker.server.model.CategorySummaryItem;
import inzagher.expense.tracker.server.repository.CategoryRepository;
import inzagher.expense.tracker.server.repository.ExpenseRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ReportService {
    private final CategoryRepository categoryRepository;
    private final ExpenseRepository expenseRepository;
    
    @Autowired
    public ReportService(ExpenseRepository expenseRepository,
            CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
        this.expenseRepository = expenseRepository;
    }
    
    public List<CategoryReportItemDTO> createMonthlyCategoryReport(int year, int month) {
        List<CategorySummaryItem> result = categoryRepository.findAll().stream()
                .map(c -> new CategorySummaryItem(c, Double.valueOf(0)))
                .collect(Collectors.toList());
        expenseRepository.getCategorySummary(year, month).forEach((repoItem) -> {
            result.stream()
                .filter((resultItem) -> categoryReportItemsEquals(resultItem, repoItem))
                .forEachOrdered((resultItem) -> resultItem.setAmount(repoItem.getAmount()));
        });
        return result.stream()
                .map(CategorySummaryItem::toDTO)
                .collect(Collectors.toList());
    }
    
    public List<YearlyReportItemDTO> createYearlyReport(int year) {
        List<YearlyReportItemDTO> report = new ArrayList<>();
        for (int month = 1; month <= 12; ++month) {
            Float amount = expenseRepository.getTotalMonthAmount(year, month);
            report.add(new YearlyReportItemDTO(month, amount == null ? 0 : amount));
        }
        return report;
    }
    
    private Boolean categoryReportItemsEquals(CategorySummaryItem left,
            CategorySummaryItem right) {
        return left.getCategory().getId().equals(right.getCategory().getId());
    }
}
