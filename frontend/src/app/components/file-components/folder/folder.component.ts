import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {FileService} from '../../../services/file.service';
import { File} from '../../../models/file';
import {FileDetailComponent} from '../file-detail/file-detail.component';
import {CreateFolderOrCategoryDialogComponent} from '../../dialogs/create-folder-dialog/create-folder-or-category-dialog.component';
import {Folder} from '../../../models/folder';
import {MatDialog} from '@angular/material/dialog';
import {FolderService} from "../../../services/folder.service";

@Component({
  selector: 'app-folder',
  templateUrl: './folder.component.html',
  styleUrls: ['./folder.component.scss']
})
export class FolderComponent implements OnInit {
  folder: Folder;
  files: File[];
  constructor(private folderService: FolderService, private fileService: FileService,
              private route: ActivatedRoute, private dialog: MatDialog) { }

  ngOnInit(): void {
    this.getFiles();
    this.getFolder();
  }
  getFiles(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.folderService.getFolderContent(id).subscribe(files => this.files = files);
  }
  getFolder(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.folderService.getFolder(id).subscribe(folder => this.folder = folder);
  }

  onFileInput(files: FileList): void {
    for (let i = 0; i < files.length; i++) {
      this.fileService.uploadFile(files.item(i)).subscribe(file => this.files.push(file));
    }
  }
  openDetailDialogOf(file: File): void {
    this.dialog.open(FileDetailComponent, { data: file, panelClass: 'custom-dialog' });
  }
  openFolderDialog(): void {
    const dialogRef = this.dialog.open(CreateFolderOrCategoryDialogComponent, { data: {name: '', access: false } as Folder });
    dialogRef.afterClosed().subscribe(folder => {
      this.folderService.createFolder(folder).subscribe();
    });
  }

}
