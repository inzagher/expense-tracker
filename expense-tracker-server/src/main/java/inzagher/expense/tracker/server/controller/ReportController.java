package inzagher.expense.tracker.server.controller;

import inzagher.expense.tracker.server.model.dto.report.CategoryReportItemDTO;
import inzagher.expense.tracker.server.model.dto.report.YearlyReportItemDTO;
import inzagher.expense.tracker.server.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reports")
@Tag(name = "Report operations")
public class ReportController {
    private final ReportService service;

    @GetMapping("/category/{year}/{month}")
    @Operation(summary = "Create category report")
    public List<CategoryReportItemDTO> getCategoryReport(
            @PathVariable Integer year, @PathVariable Integer month) {
        return service.createCategoryReport(year, month);
    }

    @GetMapping("/yearly/{year}")
    @Operation(summary = "Create yearly report")
    public List<YearlyReportItemDTO> getYearlyReport(@PathVariable Integer year) {
        return service.createYearlyReport(year);
    }
}
