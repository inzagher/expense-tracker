import { Pipe, PipeTransform } from '@angular/core';
import { Color } from '../model/color';

@Pipe({ name: 'color', pure: true })
export class ColorPipe implements PipeTransform {
    transform(color: Color): any {
        let result = "#";
        result += this.toHex(color.red);
        result += this.toHex(color.green);
        result += this.toHex(color.blue);
        return result;
    }

    private toHex(n: number) {
        var hex = n.toString(16);
        return hex.length == 1 ? "0" + hex : hex;
    }
}
