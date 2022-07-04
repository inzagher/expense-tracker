import { Pipe, PipeTransform } from '@angular/core';

interface Color {
    red: number | null;
    green: number | null;
    blue: number | null;
}

@Pipe({ name: 'color', pure: true })
export class ColorPipe implements PipeTransform {
    transform(color: Color | null | undefined): string | null {
        if (color === undefined || color === null) {
            return null;
        }

        let result = "#";
        result += this.toHex(color.red ?? 0);
        result += this.toHex(color.green ?? 0);
        result += this.toHex(color.blue ?? 0);
        return result;
    }

    private toHex(n: number) {
        const hex = n.toString(16);
        return hex.length == 1 ? "0" + hex : hex;
    }
}
