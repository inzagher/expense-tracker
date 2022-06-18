import { Component, OnDestroy, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { ChangeTitleCommand } from "@core/commands";
import { CategoryDTO } from "@core/dto";
import { Bus, CategoryService } from "@core/services";
import { DialogService } from "@core/services/dialog.service";
import { Observable, BehaviorSubject, switchMap } from "rxjs";

@Component({
    selector: 'categories',
    templateUrl: './categories.component.html',
    styleUrls: ['./categories.component.scss']
})
export class CategoriesComponent implements OnInit, OnDestroy {
    public categories$: Observable<CategoryDTO[]> | null = null;
    private update: BehaviorSubject<void> = new BehaviorSubject<void>(void 0);

    constructor(private bus: Bus,
                private router: Router,
                private dialogService: DialogService,
                private categoryService: CategoryService) { }

    ngOnInit(): void {
        this.categories$ = this.update.pipe(switchMap(() => this.categoryService.findAll()));
        this.bus.publish(new ChangeTitleCommand("Категории"));
    }

    ngOnDestroy(): void {
        this.update.complete();
    }

    refresh(): void {
        this.update.next();
    }

    add(): void {
        this.router.navigate(['categories/editor'])
    }

    edit(category: CategoryDTO): void {
        this.router.navigate(['categories/editor/' + category.id]);
    }

    delete(category: CategoryDTO): void {
        let caption = 'Внимание!';
        let question = 'Вы действительно хотите удалить категорию?';
        let request$ = this.categoryService.deleteById(category.id as number);
        this.dialogService.confirmAndExecute(caption, question, request$).subscribe({
            next: () => { this.refresh(); },
            error: (error) => { console.log(error); }
        });
    }
}
