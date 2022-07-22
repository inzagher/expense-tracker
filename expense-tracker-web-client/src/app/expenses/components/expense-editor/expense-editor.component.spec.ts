import { HttpClientTestingModule } from "@angular/common/http/testing";
import { ComponentFixture, TestBed } from "@angular/core/testing";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { NoopAnimationsModule } from "@angular/platform-browser/animations";
import { RouterTestingModule } from "@angular/router/testing";
import { MaterialModule } from "@material/material.module";
import { ExpenseEditorComponent } from "@expenses/components";

describe('ExpenseEditorComponent', () => {
    let component: ExpenseEditorComponent;
    let fixture: ComponentFixture<ExpenseEditorComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [ExpenseEditorComponent],
            imports: [
                HttpClientTestingModule,
                RouterTestingModule,
                FormsModule,
                ReactiveFormsModule,
                MaterialModule,
                NoopAnimationsModule
            ]
        }).compileComponents();
        fixture = TestBed.createComponent(ExpenseEditorComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('Creation', () => {
        expect(component).toBeTruthy();
    });
});
