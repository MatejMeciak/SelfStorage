import { Component, OnInit } from '@angular/core';
import { File } from '../../../models/file';
import { FileService } from '../../../services/file.service';

@Component({
  selector: 'app-files',
  templateUrl: './files.component.html',
  styleUrls: ['./files.component.css']
})
export class FilesComponent implements OnInit {

  selectedFile: File;
  files: File[];
  constructor(private fileService: FileService) { }

  ngOnInit(): void {
    this.fileService.getFiles().subscribe(files => this.files = files);
  }
}
