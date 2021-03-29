import { Component, OnInit } from '@angular/core';
import { load } from '../../material/animations';
import { FileService } from '../../services/file.service';
import { File } from '../../models/file';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css'],
  animations: [ load ]
})
export class HomePageComponent implements OnInit {
  file: File;
  files: File[];
  constructor(private fileService: FileService) {
    this.fileService.getFiles().subscribe(files => this.files = files);
  }

  ngOnInit(): void {
  }

  onFileInput(files: FileList): void {
    for (let i = 0; i < files.length; i++) {
      this.fileService.uploadFile(files.item(i)).subscribe(file => this.files.push(file));
    }
  }

}
