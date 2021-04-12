import {Component, Input, OnInit, Output, EventEmitter} from '@angular/core';
import {File} from '../../models/file';

@Component({
  selector: 'app-file-detail',
  templateUrl: './file-detail.component.html',
  styleUrls: ['./file-detail.component.css']
})
export class FileDetailComponent implements OnInit {

  @Input() file: File;
  @Output() closeEvent = new EventEmitter<File>();
  constructor() { }

  ngOnInit(): void {
  }
  closeFile(): void {
    this.closeEvent.emit(null);
  }

}
