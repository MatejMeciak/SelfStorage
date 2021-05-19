import { Component, OnInit } from '@angular/core';
import { File } from '../../../models/file';
import { FileService } from '../../../services/file.service';
import { Folder } from '../../../models/folder';

@Component({
  selector: 'app-files',
  templateUrl: './files.component.html',
  styleUrls: ['./files.component.css']
})
export class FilesComponent implements OnInit {

  selectedFile: File;
  files: File[];

  folders: Folder[];
  constructor(private fileService: FileService) { }

  ngOnInit(): void {
    this.fileService.getUserFiles().subscribe(files => this.files = files);
  }
}
