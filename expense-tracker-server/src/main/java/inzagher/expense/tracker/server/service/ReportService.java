package inzagher.expense.tracker.server.service;

import inzagher.expense.tracker.server.dto.MonthlyReportItemDTO;
import inzagher.expense.tracker.server.dto.YearlyReportItemDTO;
import inzagher.expense.tracker.server.model.MonthlyReportItem;
import inzagher.expense.tracker.server.model.YearlyReportItem;
import inzagher.expense.tracker.server.repository.ExpenseRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportService {
    private final ExpenseRepository expenseRepository;
    
    @Autowired
    public ReportService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }
    
    public List<MonthlyReportItemDTO> createMonthlyReport(int year, int month) {
        return expenseRepository.createMonthlyReport(year, month)
                .stream()
                .map(MonthlyReportItem::toDTO)
                .collect(Collectors.toList());
    }
    
    public List<YearlyReportItemDTO> createYearlyReport(int year) {
        return expenseRepository.createYearlyReport(year)
                .stream()
                .map(YearlyReportItem::toDTO)
                .collect(Collectors.toList());
    }
}
