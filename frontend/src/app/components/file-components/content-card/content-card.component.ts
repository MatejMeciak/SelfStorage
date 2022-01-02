import { Component, Input, OnInit } from '@angular/core';
import { Folder } from '../../../models/folder';
import { File } from '../../../models/file';
import { getFileUrl } from '../../../utils/utils';

@Component({
  selector: 'app-content-card',
  templateUrl: './content-card.component.html',
  styleUrls: ['./content-card.component.scss']
})
export class ContentCardComponent implements OnInit {
  @Input() content: File | Folder;

  get fileUrl(): string {
    return getFileUrl(this.content);
  }
  constructor() { }
  ngOnInit(): void { }
}
