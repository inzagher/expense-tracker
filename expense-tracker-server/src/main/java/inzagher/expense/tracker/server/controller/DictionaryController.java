package inzagher.expense.tracker.server.controller;

import inzagher.expense.tracker.server.service.DictionaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dictionaries")
@Tag(name = "Dictionary operations")
public class DictionaryController {
    private final DictionaryService service;

    @GetMapping("/descriptions")
    @Operation(summary = "Expense description autocomplete")
    public List<String> findExpenseDescriptions(
            @RequestParam String pattern,
            @RequestParam Integer minCount) {
        return service.findExpenseDescriptions(pattern, minCount);
    }
}
