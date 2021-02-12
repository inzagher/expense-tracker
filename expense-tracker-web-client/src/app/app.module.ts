import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { MaterialModule } from './material.module';
import { HttpClientModule } from '@angular/common/http';

import { BackupService, HttpBackupService, StubBackupService } from './service/backup.service';
import { CategoryService, HttpCategoryService, StubCategoryService } from './service/category.service';
import { ExpenseService, HttpExpenseService, StubExpenseService } from './service/expense.service';
import { HttpPersonDataAccessService, PersonService, StubPersonDataAccessService } from './service/person.service';

import { AppComponent } from './app.component';
import { HomePageComponent } from './view/page/home-page/home-page.component';
import { SettingsPageComponent } from './view/page/settings-page/settings-page.component';
import { ExpenseEditorDialogComponent } from './view/dialog/expense-editor-dialog/expense-editor-dialog.component';
import { CategoryEditorDialogComponent } from './view/dialog/category-editor-dialog/category-editor-dialog.component';
import { PersonEditorDialogComponent } from './view/dialog/person-editor-dialog/person-editor-dialog.component';
import { ConfirmationDialogComponent } from './view/dialog/confirmation-dialog/confirmation-dialog.component';

import { ColorPipe } from './pipe/color.pipe';
import { environment } from 'src/environments/environment';

@NgModule({
    declarations: [
        AppComponent,
        SettingsPageComponent,
        ConfirmationDialogComponent,
        ExpenseEditorDialogComponent,
        CategoryEditorDialogComponent,
        PersonEditorDialogComponent,
        HomePageComponent,
        ColorPipe
    ],
    imports: [
        AppRoutingModule,
        BrowserModule,
        BrowserAnimationsModule,
        HttpClientModule,
        MaterialModule
    ],
    entryComponents: [
        ConfirmationDialogComponent,
        PersonEditorDialogComponent,
        CategoryEditorDialogComponent,
        ExpenseEditorDialogComponent
    ],
    providers: [
        { provide: BackupService, useClass: environment.backend ? HttpBackupService : StubBackupService },
        { provide: PersonService, useClass: environment.backend ? HttpPersonDataAccessService : StubPersonDataAccessService },
        { provide: CategoryService, useClass: environment.backend ? HttpCategoryService : StubCategoryService },
        { provide: ExpenseService, useClass: environment.backend ? HttpExpenseService : StubExpenseService }
    ],
    bootstrap: [AppComponent]
})
export class AppModule { }
