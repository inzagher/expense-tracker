import { Pipe, PipeTransform } from '@angular/core';
import { Color } from '../model/color';

@Pipe({ name: 'color', pure: true })
export class ColorPipe implements PipeTransform {
    transform(color: Color): any {
        let result = "#";
        result += color.red.toString(16);
        result += color.green.toString(16);
        result += color.blue.toString(16);
        return result;
    }
}
