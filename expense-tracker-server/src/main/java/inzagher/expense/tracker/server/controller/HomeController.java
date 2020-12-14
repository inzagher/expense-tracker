package inzagher.expense.tracker.server.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @RequestMapping("/**/{path:[^\\.]+}")
    public String get() {
        return "forward:/";
    }
}
