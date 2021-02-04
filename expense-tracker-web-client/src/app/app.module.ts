import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { MaterialModule } from './material.module';
import { HttpClientModule } from '@angular/common/http';

import { BackupService, HttpBackupService, StubBackupService } from './service/backup.service';
import { CategoryService, HttpCategoryService, StubCategoryService } from './service/category.service';
import { ExpenseService, HttpExpenseService, StubExpenseService } from './service/expense.service';
import { HttpPersonDataAccessService, PersonDataAccessService, StubPersonDataAccessService } from './service/person.service';

import { AppComponent } from './app.component';
import { SettingsPageComponent } from './view/settings-page/settings-page.component';
import { ExpenseEditorDialogComponent } from './view/expense-editor-dialog/expense-editor-dialog.component';
import { CategoryEditorDialogComponent } from './view/category-editor-dialog/category-editor-dialog.component';
import { PersonEditorDialogComponent } from './view/person-editor-dialog/person-editor-dialog.component';
import { environment } from 'src/environments/environment';

@NgModule({
    declarations: [
        AppComponent,
        SettingsPageComponent,
        ExpenseEditorDialogComponent,
        CategoryEditorDialogComponent,
        PersonEditorDialogComponent
    ],
    imports: [
        AppRoutingModule,
        BrowserModule,
        BrowserAnimationsModule,
        HttpClientModule,
        MaterialModule
    ],
    providers: [
        { provide: BackupService, useClass: environment.production ? HttpBackupService : StubBackupService },
        { provide: PersonDataAccessService, useClass: environment.production ? HttpPersonDataAccessService : StubPersonDataAccessService },
        { provide: CategoryService, useClass: environment.production ? HttpCategoryService : StubCategoryService },
        { provide: ExpenseService, useClass: environment.production ? HttpExpenseService : StubExpenseService }
    ],
    bootstrap: [AppComponent]
})
export class AppModule { }
