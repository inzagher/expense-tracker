import { NgModule } from "@angular/core";
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

@NgModule({
    imports: [
        MatToolbarModule,
        MatButtonModule,
        MatIconModule
    ],
    exports: [
        MatToolbarModule,
        MatButtonModule,
        MatIconModule
    ]
})
export class MaterialModule { }
