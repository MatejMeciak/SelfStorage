import { Component, OnInit } from '@angular/core';
import { load } from '../../material/animations';
import {FileService} from '../../services/file.service';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css'],
  animations: [ load ]
})
export class HomePageComponent implements OnInit {

  file;
  constructor(private fileService: FileService) {
  }

  ngOnInit(): void {
  }

  onFileInput(files: FileList): void {
    for (let i = 0; i < files.length; i++) {
      this.fileService.uploadFile(files.item(i)).subscribe();
    }
  }

}
