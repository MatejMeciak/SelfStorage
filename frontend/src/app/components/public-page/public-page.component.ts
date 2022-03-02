import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { filter, Observable, of, Subject, takeUntil } from 'rxjs';
import { FileService } from '../../services/file.service';
import {debounceTime, distinctUntilChanged, mergeMap} from 'rxjs/operators';
import { File } from '../../models/file';
import { SidenavService } from "../../services/sidenav.service";
import { ImageService } from "../../services/image.service";
import { MatDrawer } from "@angular/material/sidenav";
import { TokenStorageService } from "../../services/token-storage.service";
import { FolderService } from "../../services/folder.service";
import { ReportService } from "../../services/report.service";
import { CategoryService } from "../../services/category.service";
import { DialogService } from "../../services/dialog.service";
import { AuthService } from "../../services/auth.service";
import { ActivatedRoute, Router } from "@angular/router";
import { User } from "../../models/user";
import * as fileSaver from 'file-saver';

@Component({
  selector: 'app-search-page',
  templateUrl: './public-page.component.html',
  styleUrls: ['./public-page.component.scss']
})
export class PublicPageComponent implements OnInit, AfterViewInit {
  @ViewChild('detailSidenav', { static: true }) public detailSidenav: MatDrawer;
  files$: Observable<File[]>;
  file: File;
  user?: User;
  private searchKeyword = new Subject<string>();
  constructor(private fileService: FileService,
              private tokenStorageService: TokenStorageService,
              private imageService: ImageService,
              private folderService: FolderService,
              private reportService: ReportService,
              private categoryService: CategoryService,
              private sidenavService: SidenavService,
              private dialogService: DialogService,
              private authService: AuthService,
              private route: ActivatedRoute,
              private router: Router) { }

  ngOnInit(): void {
    if (!!this.tokenStorageService.getToken()) {
      this.authService.getCurrentUser().subscribe(user => this.user = user);
    }
    this.sidenavService.setDetailSidenav(this.detailSidenav);
    this.files$ = this.searchKeyword.pipe(
      debounceTime(250),
      distinctUntilChanged(),
      mergeMap((keyword: string) => !!keyword ?
        this.fileService.getSearchedPublicFiles(keyword) :
        this.fileService.getPublicFiles()
      )
    );
    this.fileService.getSelectedFile().subscribe(file => this.file = file);
  }

  getImage(file: File): Observable<string> {
    return file.mimeType.includes('image') ?
      of(`http://localhost:8080/api/file/public/${file.id}`) :
      of('assets/images/file_icon.png');
  }
  ngAfterViewInit() {
    this.searchKeyword.next(undefined);
  }

  inputChange(keyword: string): void {
    this.searchKeyword.next(keyword);
  }
  openFileDetail(file): void {
    this.fileService.setSelectedFile(file);
    this.sidenavService.openDetailSidenav();
  }
  isOwner(): boolean {
    return this.file.ownerId == this.user.id;
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
    this.fileService.getPublicFileBlob(this.file.id).subscribe(blob => {
      fileSaver.saveAs(blob, this.file.name);
    });
  }
  deleteFile(): void {
    this.dialogService.confirmDialog(this.file, 'delete').subscribe((result) => {
      if (result) {
        this.fileService.deleteFile(this.file.id).subscribe(() => {
          this.router.navigate(['storage']);
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
      if (result) {
        this.folderService.addFileToFolder(result.id, this.file.id).subscribe(() => location.reload())
      }
    });
  }
  addCategory(): void {
    this.dialogService.selectContentDialog({}, 'category').subscribe((result) => {
      if (result) {
        this.categoryService.addContentToCategory(result.id, this.file.id).subscribe();
      }
    });
  }
  removeCategory(): void {
    this.dialogService.selectContentDialog(this.file,'remove',).pipe(
      filter(result => !!result),
      mergeMap(result => this.categoryService.deleteContentFromCategory(this.file.id, result.id)),
    ).subscribe();
  }
  reportFile(): void {
    this.dialogService.inputDialog('Enter Report Reason').subscribe((result) => {
      if (result) {
        this.reportService.createReport(this.file.id, result).subscribe();
      }
    });
  }
}
