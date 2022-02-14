import { AfterContentInit, AfterViewInit, Component, Input, OnDestroy, OnInit } from '@angular/core';
import { map, mergeMap, Subject, takeUntil, tap } from "rxjs";
import { FileService } from "../../../services/file.service";
import { DialogService } from "../../../services/dialog.service";
import { FolderService } from "../../../services/folder.service";
import { CategoryService } from "../../../services/category.service";
import { Category } from "../../../models/category";
import { Folder } from "../../../models/folder";
import { ActivatedRoute } from "@angular/router";

@Component({
  selector: 'app-content-header',
  templateUrl: './content-header.component.html',
  styleUrls: ['./content-header.component.scss']
})
export class ContentHeaderComponent implements OnInit, AfterContentInit ,OnDestroy {
  @Input() title: string;
  @Input() actions: boolean = true;
  @Input() component: string;

  id: number | null;
  unsubscribe$ = new Subject();
  folder: Folder;
  constructor(private fileService: FileService,
              private dialogService: DialogService,
              private folderService: FolderService,
              private categoryService: CategoryService,
              private route: ActivatedRoute) { }

  ngOnInit(): void { }
  ngAfterContentInit(): void {
    this.route.paramMap.pipe(
      map(param => Number(param.get('id'))),
      tap(id => this.id = id),
    );
  }

  onFileInput(files: FileList): void {
    for (let i = 0; i < files.length; i++) {
      this.fileService.uploadFile(files.item(i)).pipe(
        takeUntil(this.unsubscribe$)
      ).subscribe(() => {
        location.reload();
      });
    }
  }
  onFileInputToFolder(files: FileList): void {
    for (let i = 0; i < files.length; i++) {
      this.fileService.uploadFile(files.item(i)).pipe(
        takeUntil(this.unsubscribe$)
      ).subscribe((file) => {
        this.folderService.addFileToFolder(this.id, file.id).pipe(
          takeUntil(this.unsubscribe$)
        ).subscribe();
        location.reload();
      });

    }
  }
  editFolder(): void {
    this.dialogService.editContentDialog(this.folder).subscribe((result) => {
      if (result){
        this.fileService.updateFile(result).subscribe(() => location.reload());
      }
    });
  }
  createFolder(): void {
    this.dialogService.createFolderOrCategoryDialog({ name: '' } as Folder, 'folder').subscribe(result => {
      if (result) {
        this.folderService.createFolder(result).subscribe(() => location.reload());
      }
    });
  }
  deleteFolder(): void {
    this.dialogService.confirmDialog(this.folder, 'delete').pipe(
      takeUntil(this.unsubscribe$)
    ).subscribe((result) => {
      if (result) {
        this.folderService.deleteFolder(this.folder.id).pipe(
          takeUntil(this.unsubscribe$)
        ).subscribe(() => location.reload());
      }
    });
  }
  createCategory(): void {
    this.dialogService.createFolderOrCategoryDialog({ name: '' } as Category, 'category').subscribe(result => {
      if (result) {
        this.categoryService.createCategory(result).subscribe(() => location.reload());
      }
    });
  }
  addCategory(): void {
    this.dialogService.selectContentDialog({},'category').subscribe((result) => {
      if (result) {
        this.categoryService.addContentToCategory(result.id, this.folder.id).subscribe();
      }
    });
  }
  ngOnDestroy(): void {
    this.unsubscribe$?.next(true);
    this.unsubscribe$?.complete();
  }
}