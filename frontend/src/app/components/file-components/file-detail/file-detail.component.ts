import { Component, OnDestroy, OnInit } from '@angular/core';
import { FileService } from "../../../services/file.service";
import { filter, map, mergeMap, Observable, of, Subject, takeUntil, tap } from "rxjs";
import { File } from "../../../models/file";
import { ImageService } from "../../../services/image.service";
import { SidenavService } from "../../../services/sidenav.service";
import * as fileSaver from 'file-saver';
import { DialogService } from "../../../services/dialog.service";
import { FolderService } from "../../../services/folder.service";
import { CategoryService } from "../../../services/category.service";
import { User } from "../../../models/user";
import { AuthService } from "../../../services/auth.service";
import { ActivatedRoute, NavigationEnd, Router } from "@angular/router";
import { TokenStorageService } from "../../../services/token-storage.service";
import { ReportService } from "../../../services/report.service";
import { UserService } from "../../../services/user.service";

@Component({
  selector: 'app-file-detail',
  templateUrl: './file-detail.component.html',
  styleUrls: ['./file-detail.component.scss']
})
export class FileDetailComponent implements OnInit, OnDestroy {
  file: File;
  user?: User;

  unsubscribe$ = new Subject();
  constructor(private fileService: FileService,
              private tokenStorageService: TokenStorageService,
              private imageService: ImageService,
              private folderService: FolderService,
              private reportService: ReportService,
              private categoryService: CategoryService,
              private sidenavService: SidenavService,
              private dialogService: DialogService,
              private authService: AuthService,
              private userService: UserService,
              private route: ActivatedRoute,
              private router: Router) { }

  ngOnInit(): void {
    if (!!this.tokenStorageService.getToken()) {
      this.userService.getCurrentUser().pipe(
        takeUntil(this.unsubscribe$)
      ).subscribe(user => this.user = user);
    }
    this.fileService.getSelectedFile().subscribe(file => this.file = file);
  }
  isOwner(): boolean {
    return this.file.ownerId == this.user.id;
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
        ).subscribe(() => {
          location.reload();
        });
      }
    });
  }
  shareWithUser(): void {
    this.dialogService.inputDialog('Enter User Email').subscribe((result) => {
      if (result) {
        this.fileService.shareFileWithUser(result, this.file).subscribe()
      }
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
    this.dialogService.selectContentDialog({},'folder').subscribe( (result) => {
      if (result === 'storage') {
        this.folderService.deleteFileFromFolder(Number(this.route.snapshot.paramMap.get('id')), this.file.id).subscribe(() => location.reload())
      }
      else if (result) {
        this.folderService.addFileToFolder(result.id, this.file.id).subscribe(() => location.reload())
      }
    });
  }
  addCategory(): void {
    this.dialogService.selectContentDialog({}, 'category').subscribe((result) => {
      if (result) {
        this.categoryService.addContentToCategory(result.id, this.file.id).subscribe(() => location.reload());
      }
    });
  }
  removeCategory(): void {
    this.dialogService.selectContentDialog(this.file,'remove',).pipe(
      takeUntil(this.unsubscribe$),
      filter(result => !!result),
      mergeMap(result => this.categoryService.deleteContentFromCategory(result.id, this.file.id)),
    ).subscribe();
  }
  reportFile(): void {
    this.dialogService.inputDialog('Enter Report Reason').subscribe((result) => {
      if (result) {
        this.reportService.createReport(this.file.id, result).subscribe();
      }
    });
  }

  ngOnDestroy(): void {
    this.unsubscribe$?.next(true);
    this.unsubscribe$?.complete();
  }
}
