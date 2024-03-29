package inzagher.expense.tracker.server.controller;

import inzagher.expense.tracker.server.service.DictionaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Dictionary operations")
public class DictionaryApiController {
    private final DictionaryService service;

    @GetMapping("/api/dictionaries/descriptions")
    @Operation(summary = "Expense description autocomplete")
    public List<String> findExpenseDescriptions(
            @RequestParam String pattern,
            @RequestParam Integer minCount) {
        return service.findExpenseDescriptions(pattern, minCount);
    }
}
