import { Component, OnDestroy, OnInit } from '@angular/core';
import { map, Observable, Subscription } from "rxjs";
import { File } from "../../models/file";
import { Folder } from "../../models/folder";
import { CategoryService } from "../../services/category.service";
import { ActivatedRoute } from "@angular/router";
import { tap } from "rxjs/operators";
import { FileService } from "../../services/file.service";
import { FolderService } from "../../services/folder.service";

@Component({
  selector: 'app-categories',
  templateUrl: './categories.component.html',
  styleUrls: ['./categories.component.scss']
})
export class CategoriesComponent implements OnInit, OnDestroy {

  files$: Observable<File[]>
  category: string;
  private subscription?: Subscription;
  constructor(private categoryService: CategoryService,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
     this.subscription = this.route.queryParamMap.pipe(
       map(queryMap => +queryMap.get('category')),
       tap((categoryId) => this.categoryService.getCategory(categoryId).pipe(
         tap(category => this.category = category.name)
       ).subscribe()),
       map(categoryId => this.files$ = this.categoryService.getFilesInCategory(categoryId)),
     ).subscribe();
  }

  ngOnDestroy(): void {
    this.subscription?.unsubscribe();
  }

}
