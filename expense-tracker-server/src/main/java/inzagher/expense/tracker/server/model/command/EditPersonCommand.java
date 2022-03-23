package inzagher.expense.tracker.server.model.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class EditPersonCommand {
    private final Integer id;
    private final String name;
}
