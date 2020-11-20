import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BackupDataAccessService, HttpBackupDataAccessService } from './service/backup-data-access.service';
import { CategoryDataAccessService, HttpCategoryDataAccessService } from './service/category-data-access.service';
import { ExpenseDataAccessService, HttpExpenseDataAccessService } from './service/expense-data-access.service';
import { HttpPersonDataAccessService, PersonDataAccessService } from './service/person-data-access.service';

import { SettingsPageComponent } from './view/settings-page/settings-page.component';

@NgModule({
  declarations: [
    AppComponent,
    SettingsPageComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [
      { provide: BackupDataAccessService, useClass: HttpBackupDataAccessService },
      { provide: PersonDataAccessService, useClass: HttpPersonDataAccessService },
      { provide: CategoryDataAccessService, useClass: HttpCategoryDataAccessService },
      { provide: ExpenseDataAccessService, useClass: HttpExpenseDataAccessService }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
