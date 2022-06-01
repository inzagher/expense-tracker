import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '@shared/shared.module';
import { BackupsRoutingModule } from '@backups/backups-routing.module';
import { BackupsComponent } from '@backups/backups.component';

@NgModule({
    declarations: [
        BackupsComponent
    ],
    imports: [
        CommonModule,
        SharedModule,
        BackupsRoutingModule
    ]
})
export class BackupsModule { }
