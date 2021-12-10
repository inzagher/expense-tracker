package inzagher.expense.tracker.server.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class CreateCategoryCommand {
    private final String name;
    private final Integer colorRed;
    private final Integer colorGreen;
    private final Integer colorBlue;
    private final String description;
}
