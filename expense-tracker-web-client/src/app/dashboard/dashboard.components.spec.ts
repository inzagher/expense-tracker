import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { DashboardComponent } from '@dashboard/dashboard.component';

describe('DashboardComponent', () => {
    let component: DashboardComponent;
    let fixture: ComponentFixture<DashboardComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [DashboardComponent],
            imports: [HttpClientTestingModule]
        }).compileComponents();
        fixture = TestBed.createComponent(DashboardComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('Creation', () => {
        expect(component).toBeTruthy();
    });
});
