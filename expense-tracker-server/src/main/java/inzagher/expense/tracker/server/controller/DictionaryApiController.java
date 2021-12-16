package inzagher.expense.tracker.server.controller;

import inzagher.expense.tracker.server.service.DictionaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DictionaryApiController {
    private final DictionaryService service;

    @GetMapping("/api/expenses/descriptions")
    public List<String> findExpenseDescriptions(
            @RequestParam String pattern,
            @RequestParam Integer minCount) {
        return service.findExpenseDescriptions(pattern, minCount);
    }
}
