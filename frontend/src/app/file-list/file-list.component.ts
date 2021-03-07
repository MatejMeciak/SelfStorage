import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-file-list',
  templateUrl: './file-list.component.html',
  styleUrls: ['./file-list.component.css']
})
export class FileListComponent implements OnInit {

  file = { image: '../assets/images/bcg.png', filename: 'image'}

  constructor() { }

  ngOnInit(): void {
  }

}
