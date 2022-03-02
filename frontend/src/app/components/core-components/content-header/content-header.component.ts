import { AfterContentInit, AfterViewInit, Component, Input, OnDestroy, OnInit } from '@angular/core';
import { map, mergeMap, of, Subject, takeUntil, tap } from "rxjs";
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
export class ContentHeaderComponent implements OnInit, OnDestroy {
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

  ngOnInit(): void {
    this.route.paramMap.pipe(
      map(param => Number(param.get('id'))),
      tap(id => this.id = id),
      map( id => id!! ? this.folderService.getFolder(id).pipe(
          tap(folder => this.folder = folder)
        ).subscribe() : null
      )
    ).subscribe();
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
        this.folderService.editFolder(result).subscribe(() => location.reload());
      }
    });
  }
  createFolder(): void {
    this.dialogService.createFolderOrCategoryDialog({ name: '' } as Folder, 'folder').subscribe(result => {
      if (result) {
        this.folderService.createFolder(result.name).subscribe(() => location.reload());
      }
    });
  }
  deleteFolder(): void {
    this.dialogService.confirmDialog(this.folder, 'delete').pipe(
      takeUntil(this.unsubscribe$)
    ).subscribe((result) => {
      if (result) {
        this.folderService.getFolderContent(this.folder.id).subscribe(files => {
          files.forEach( file => this.fileService.updateFile({ ...file, folderId: null }).subscribe())
          this.folderService.deleteFolder(this.folder.id).pipe(
            takeUntil(this.unsubscribe$)
          ).subscribe(() => location.replace('/'));
        });
      }
    });
  }
  createCategory(): void {
    this.dialogService.createFolderOrCategoryDialog({ name: '' } as Category, 'category').subscribe(result => {
      if (result) {
        this.categoryService.createCategory(result.name).subscribe(() => location.reload());
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
  shareWithUser(): void {
    this.dialogService.inputDialog('Enter User Email').subscribe((result) => {
      if (result) {
        this.folderService.shareFolderWithFriends(this.folder.id, result).subscribe()
      }
    });
  }
  publishFile(state: boolean): void {
    this.dialogService.confirmDialog(this.folder, 'Publish').subscribe((result) => {
      if (result) {
        this.folderService.editFolder({ ...this.folder, access: state }
        ).subscribe(() => location.reload())
      }
    });
  }
  ngOnDestroy(): void {
    this.unsubscribe$?.next(true);
    this.unsubscribe$?.complete();
  }
}
