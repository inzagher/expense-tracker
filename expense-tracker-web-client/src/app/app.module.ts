import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { MaterialModule } from './material.module';
import { HttpClientModule } from '@angular/common/http';

import { BackupService, HttpBackupService } from './service/backup.service';
import { CategoryService, HttpCategoryService } from './service/category.service';
import { ExpenseService, HttpExpenseService } from './service/expense.service';
import { HttpPersonDataAccessService, PersonDataAccessService } from './service/person.service';

import { AppComponent } from './app.component';
import { SettingsPageComponent } from './view/settings-page/settings-page.component';

@NgModule({
    declarations: [
        AppComponent,
        SettingsPageComponent
    ],
    imports: [
        AppRoutingModule,
        BrowserModule,
        BrowserAnimationsModule,
        HttpClientModule,
        MaterialModule
    ],
    providers: [
        { provide: BackupService, useClass: HttpBackupService },
        { provide: PersonDataAccessService, useClass: HttpPersonDataAccessService },
        { provide: CategoryService, useClass: HttpCategoryService },
        { provide: ExpenseService, useClass: HttpExpenseService }
    ],
    bootstrap: [AppComponent]
})
export class AppModule { }
