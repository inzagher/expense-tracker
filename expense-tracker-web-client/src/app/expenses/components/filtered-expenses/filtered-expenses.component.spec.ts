import { HttpClientTestingModule } from "@angular/common/http/testing";
import { ComponentFixture, TestBed } from "@angular/core/testing";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { NoopAnimationsModule } from "@angular/platform-browser/animations";
import { RouterTestingModule } from "@angular/router/testing";
import { MaterialModule } from "@material/material.module";
import { FilteredExpensesComponent } from "@expenses/components";

describe('FilteredExpensesComponent', () => {
    let component: FilteredExpensesComponent;
    let fixture: ComponentFixture<FilteredExpensesComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [FilteredExpensesComponent],
            imports: [
                HttpClientTestingModule,
                RouterTestingModule,
                FormsModule,
                ReactiveFormsModule,
                MaterialModule,
                NoopAnimationsModule
            ]
        }).compileComponents();
        fixture = TestBed.createComponent(FilteredExpensesComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('Creation', () => {
        expect(component).toBeTruthy();
    });
});
