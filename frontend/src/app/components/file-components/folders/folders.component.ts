import { Component, Input, OnInit } from '@angular/core';
import { map, mergeMap, Observable, tap } from "rxjs";
import { Folder } from "../../../models/folder";
import { FolderService } from "../../../services/folder.service";
import { ActivatedRoute, Router } from "@angular/router";
import { CategoryService } from "../../../services/category.service";

@Component({
  selector: 'app-folders',
  templateUrl: './folders.component.html',
  styleUrls: ['./folders.component.scss']
})
export class FoldersComponent implements OnInit {
  @Input() content$: Observable<Folder[]>
  folders$:  Observable<Folder[]>;
  image = './assets/images/folder_icon.png';
  category: number | null;

  constructor(private folderService: FolderService,
              private route: ActivatedRoute,
              private router: Router,
              private categoryService: CategoryService) { }

  ngOnInit(): void {
    this.folders$ = this.content$ ? this.content$ : this.route.queryParamMap.pipe(
      map(queryMap => +queryMap.get('category')),
      tap(category => this.category = category),
      mergeMap(category => !!category
        ? this.categoryService.getFoldersInCategory(category)
        : this.folderService.getFolders()
      )
    );
  }
  openFolder(folder: Folder): void {
    this.router.navigate([`folder/${folder.id}`]);
  }
}
