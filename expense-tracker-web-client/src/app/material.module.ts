import { NgModule } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatTableModule } from '@angular/material/table';

@NgModule({
    exports: [
        MatButtonModule,
        MatMenuModule,
        MatIconModule,
        MatTableModule,
        MatDialogModule
    ]
})
export class MaterialModule {}
