import { Color } from './color';

export class Category {
    public id: string | null = null;
    public name: string = '';
    public description: string = '';
    public color: Color = new Color();
    public obsolete: boolean = false;
}
