import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatTableModule } from '@angular/material/table';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatDividerModule } from '@angular/material/divider';
import { MatToolbarModule } from '@angular/material/toolbar';

@NgModule({
    exports: [
        FormsModule,
        MatButtonModule,
        MatDividerModule,
        MatInputModule,
        MatFormFieldModule,
        MatMenuModule,
        MatIconModule,
        MatTableModule,
        MatDialogModule,
        MatToolbarModule
    ]
})
export class MaterialModule {}
