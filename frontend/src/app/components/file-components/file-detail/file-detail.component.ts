import { Component, OnDestroy, OnInit } from '@angular/core';
import { FileService } from "../../../services/file.service";
import { filter, mergeMap, Observable, of, Subject, takeUntil } from "rxjs";
import { File } from "../../../models/file";
import { ImageService } from "../../../services/image.service";
import { SidenavService } from "../../../services/sidenav.service";
import * as fileSaver from 'file-saver';
import { DialogService } from "../../../services/dialog.service";
import { FolderService } from "../../../services/folder.service";
import { CategoryService } from "../../../services/category.service";

@Component({
  selector: 'app-file-detail',
  templateUrl: './file-detail.component.html',
  styleUrls: ['./file-detail.component.scss']
})
export class FileDetailComponent implements OnInit, OnDestroy {
  file: File;

  unsubscribe$ = new Subject();
  constructor(private fileService: FileService,
              private imageService: ImageService,
              private folderService: FolderService,
              private categoryService: CategoryService,
              private sidenavService: SidenavService,
              private dialogService: DialogService) { }

  ngOnInit(): void {
    this.fileService.getSelectedFile().pipe(
      takeUntil(this.unsubscribe$)
    ).subscribe(file => this.file = file);
  }

  getImage(file: File): Observable<string> {
    return file.mimeType.includes('image') ?
      this.imageService.getImageForFile(file.id) :
      of('assets/images/file_icon.png');
  }
  fileType(file: File) {
    return file.mimeType.split('/')[1];
  }

  closeDetail(): void {
    this.sidenavService.closeDetailSidenav();
  }

  // file functions
  viewFile(): void {
    this.dialogService.openViewContentDialog(
      { file: this.file, image: this.getImage(this.file) }
    );
  }
  editFile(): void {
    this.dialogService.editContentDialog(this.file).subscribe((result) => {
      if (result){
        this.fileService.updateFile(result).subscribe(() => location.reload());
      }
    });
  }
  downloadFile(): void {
    this.fileService.getFileBlob(this.file.id).pipe(
      takeUntil(this.unsubscribe$)
    ).subscribe(blob => {
      fileSaver.saveAs(blob, this.file.name);
    });
  }
  deleteFile(): void {
    this.dialogService.confirmDialog(this.file, 'delete').pipe(
      takeUntil(this.unsubscribe$)
    ).subscribe((result) => {
      if (result) {
        this.fileService.deleteFile(this.file.id).pipe(
          takeUntil(this.unsubscribe$)
        ).subscribe(() => location.reload());
      }
    });
  }
  shareWithUser(): void {
    this.dialogService.shareWithUserDialog().subscribe((result) => {
      console.log(result);
    });
  }
  publishFile(state: boolean): void {
    this.dialogService.confirmDialog(this.file, 'Publish').subscribe((result) => {
      if (result) {
        this.fileService.updateFile({ ...this.file, access: state }
        ).subscribe(() => location.reload())
      }
    });
  }
  moveFileToFolder(): void {
    this.dialogService.selectContentDialog('folder').subscribe( (result) => {
      if (result) {
        this.folderService.updateFolderWithFile(result.id, this.file.id).subscribe();
      }
    });
  }
  addCategory(): void {
    this.dialogService.selectContentDialog('category').subscribe((result) => {
      if (result) {
        this.categoryService.addContentToCategory(result.id, this.file.id).subscribe();
      }
    });
  }
  removeCategory(): void {
    this.dialogService.selectContentDialog('remove').pipe(
      takeUntil(this.unsubscribe$),
      filter(result => !!result),
      mergeMap(result => this.categoryService.deleteContentFromCategory(result)),
    ).subscribe();

    this.dialogService.selectContentDialog('remove').pipe(
      takeUntil(this.unsubscribe$),
      filter(result => !!result),
      mergeMap(result => this.categoryService.deleteContentFromCategory(result)),
    ).subscribe();
  }

  ngOnDestroy(): void {
    this.unsubscribe$?.next(true);
    this.unsubscribe$?.complete();
  }
}
