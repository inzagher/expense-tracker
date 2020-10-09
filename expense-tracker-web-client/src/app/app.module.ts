import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SettingsComponent } from './view/pages/settings/settings.component';
import { ExpenseEditorDialogComponent } from './view/dialogs/expense-editor-dialog/expense-editor-dialog.component';
import { CategoryEditorDialogComponent } from './view/dialogs/category-editor-dialog/category-editor-dialog.component';
import { PersonEditorDialogComponent } from './view/dialogs/person-editor-dialog/person-editor-dialog.component';

@NgModule({
  declarations: [
    AppComponent,
    SettingsComponent,
    ExpenseEditorDialogComponent,
    CategoryEditorDialogComponent,
    PersonEditorDialogComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
