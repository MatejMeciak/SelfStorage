import { Component, OnInit } from '@angular/core';
import { FileService } from "../../../services/file.service";
import { CategoryService } from "../../../services/category.service";
import { ActivatedRoute } from "@angular/router";
import { map, mergeMap, Observable, of, tap } from "rxjs";
import { File } from "../../../models/file";
import { ImageService } from "../../../services/image.service";
import { SidenavService } from "../../../services/sidenav.service";

@Component({
  selector: 'app-files',
  templateUrl: './files.component.html',
  styleUrls: ['./files.component.scss']
})
export class FilesComponent implements OnInit {
  files$: Observable<File[]>;
  category: string | null;

  constructor(private fileService: FileService,
              private categoryService: CategoryService,
              private imageService: ImageService,
              private sidenavService: SidenavService,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.files$ = this.route.queryParamMap.pipe(
      map(queryMap => queryMap.get('category')),
      tap(category => this.category = category),
      mergeMap(category => !!category
        ? this.categoryService.getCategoryContent(category)
        : this.fileService.getFiles()
      )
    );
  }

  getImage(file: File): Observable<string> {
    return file.mimeType.includes('image') ?
      this.imageService.getImageForFile(file.id) :
      of('assets/images/file_icon.png');
  }
  openFileDetail(file): void {
    this.fileService.setSelectedFile(file);
    this.sidenavService.openDetailSidenav();
  }
}
