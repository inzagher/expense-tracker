import { HttpClientTestingModule } from "@angular/common/http/testing";
import { ComponentFixture, TestBed } from "@angular/core/testing";
import { NoopAnimationsModule } from "@angular/platform-browser/animations";
import { RouterTestingModule } from "@angular/router/testing";
import { MaterialModule } from "@material/material.module";
import { BackupsComponent } from "@backups/backups.component";

describe('BackupsComponent', () => {
    let component: BackupsComponent;
    let fixture: ComponentFixture<BackupsComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [BackupsComponent],
            imports: [
                HttpClientTestingModule,
                RouterTestingModule,
                MaterialModule,
                NoopAnimationsModule
            ]
        }).compileComponents();
        fixture = TestBed.createComponent(BackupsComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('Creation', () => {
        expect(component).toBeTruthy();
    });
});
