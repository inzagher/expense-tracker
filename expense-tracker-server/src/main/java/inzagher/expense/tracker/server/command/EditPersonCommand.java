package inzagher.expense.tracker.server.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class EditPersonCommand {
    private final Integer id;
    private final String name;
}
