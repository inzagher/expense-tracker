import { BusCommand } from "@core/commands";

export class ChangeTitleCommand extends BusCommand {
    constructor(public title: string) {
        super();
    }
}
