package inzagher.expense.tracker.server.controller;

import inzagher.expense.tracker.server.dto.CategoryReportItemDTO;
import inzagher.expense.tracker.server.dto.YearlyReportItemDTO;
import inzagher.expense.tracker.server.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReportApiController {
    private final ReportService service;

    @GetMapping(path = "/api/reports/category/{year}/{month}")
    public List<CategoryReportItemDTO> getCategoryReport(
            @PathVariable Integer year, @PathVariable Integer month) {
        return service.createCategoryReport(year, month);
    }

    @GetMapping(path = "/api/reports/yearly/{year}")
    public List<YearlyReportItemDTO> getYearlyReport(@PathVariable Integer year) {
        return service.createYearlyReport(year);
    }
}
