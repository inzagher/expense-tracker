package inzagher.expense.tracker.server.model.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class EditCategoryCommand {
    private final Integer id;
    private final String name;
    private final Integer colorRed;
    private final Integer colorGreen;
    private final Integer colorBlue;
    private final String description;
    private final Boolean obsolete;
}
