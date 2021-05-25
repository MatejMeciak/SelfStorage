import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {FileService} from '../../../services/file.service';
import { File} from '../../../models/file';

@Component({
  selector: 'app-folder',
  templateUrl: './folder.component.html',
  styleUrls: ['./folder.component.css']
})
export class FolderComponent implements OnInit {

  files: File[];
  constructor(private route: ActivatedRoute, private fileService: FileService) {}

  ngOnInit(): void {
    this.getFiles();
  }
  getFiles(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.fileService.getFolderFiles(id).subscribe(files => this.files = files);
  }
}
