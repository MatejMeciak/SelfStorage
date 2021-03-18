import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-file-list',
  templateUrl: './file-list.component.html',
  styleUrls: ['./file-list.component.css']
})
export class FileListComponent implements OnInit {

  func

  files = [{ image: '../assets/images/bcg.png', filename: 'image', uploadDate: '22.2.2022'},
  { image: '../assets/images/bcg.png', filename: 'image', uploadDate: '22.2.2022'},
  { image: '../assets/images/bcg.png', filename: 'image', uploadDate: '22.2.2022'},
  { image: '../assets/images/bcg.png', filename: 'image', uploadDate: '22.2.2022'},
  { image: '../assets/images/bcg.png', filename: 'image', uploadDate: '22.2.2022'},
  { image: '../assets/images/bcg.png', filename: 'image', uploadDate: '22.2.2022'}]

  constructor() { }

  ngOnInit(): void {
  }

}
