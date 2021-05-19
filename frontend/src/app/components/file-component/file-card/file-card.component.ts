import { Component, Input, OnInit } from '@angular/core';
import { File } from '../../../models/file';
import { getFileUrl } from '../../../utils/utils';

@Component({
  selector: 'app-file-card',
  templateUrl: './file-card.component.html',
  styleUrls: ['./file-card.component.css']
})
export class FileCardComponent implements OnInit {
  @Input() file: File;
  constructor() {
  }

  get fileUrl(): string {
    return getFileUrl(this.file);
  }
  ngOnInit(): void { }
}
