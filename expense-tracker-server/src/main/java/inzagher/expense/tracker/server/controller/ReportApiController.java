package inzagher.expense.tracker.server.controller;

import inzagher.expense.tracker.server.dto.CategoryReportItemDTO;
import inzagher.expense.tracker.server.dto.YearlyReportItemDTO;
import inzagher.expense.tracker.server.service.ReportService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportApiController {
    private final ReportService reportService;

    @Autowired
    public ReportApiController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping (path = "/api/reports/monthly-category-report/{year}/{month}",
                 produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CategoryReportItemDTO> monthlyCategoryReport(
            @PathVariable Integer year, @PathVariable Integer month
    ) {
        return reportService.createMonthlyCategoryReport(year, month);
    }

    @GetMapping (path = "/api/reports/yearly-report/{year}",
                 produces = MediaType.APPLICATION_JSON_VALUE)
    public List<YearlyReportItemDTO> yearlyReport(@PathVariable Integer year) {
        return reportService.createYearlyReport(year);
    }
}
