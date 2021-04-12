import { Component, OnInit } from '@angular/core';
import {File} from '../../models/file';
import {FileService} from '../../services/file.service';

@Component({
  selector: 'app-files',
  templateUrl: './files.component.html',
  styleUrls: ['./files.component.css']
})
export class FilesComponent implements OnInit {

  files: File[];
  constructor(private fileService: FileService) {
    this.fileService.getFiles().subscribe(files => this.files = files);
  }

  ngOnInit(): void {
  }

}
