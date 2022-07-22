import { HttpClientTestingModule } from "@angular/common/http/testing";
import { ComponentFixture, TestBed } from "@angular/core/testing";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { NoopAnimationsModule } from "@angular/platform-browser/animations";
import { RouterTestingModule } from "@angular/router/testing";
import { MaterialModule } from "@material/material.module";
import { MonthlyExpensesComponent } from '@expenses/components';


describe('MonthlyExpensesComponent', () => {
    let component: MonthlyExpensesComponent;
    let fixture: ComponentFixture<MonthlyExpensesComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [MonthlyExpensesComponent],
            imports: [
                HttpClientTestingModule,
                RouterTestingModule,
                FormsModule,
                ReactiveFormsModule,
                MaterialModule,
                NoopAnimationsModule
            ]
        }).compileComponents();
        fixture = TestBed.createComponent(MonthlyExpensesComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('Creation', () => {
        expect(component).toBeTruthy();
    });
});
