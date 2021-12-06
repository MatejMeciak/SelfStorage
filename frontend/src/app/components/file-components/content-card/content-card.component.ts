import { Component, Input, OnInit } from '@angular/core';
import { File } from '../../../models/file';
//import { getFileUrl } from '../../../utils/utils';
import {Folder} from '../../../models/folder';
import {environment} from "../../../../environments/environment";

@Component({
  selector: 'app-file-card',
  templateUrl: './content-card.component.html',
  styleUrls: ['./content-card.component.scss']
})
export class ContentCardComponent implements OnInit {
  @Input() file: File;
  @Input() folder: Folder;

  get fileUrl(): string {
    return `${environment.apiUrl}/file/${this.file.id}`;
  }
  constructor() { }
  ngOnInit(): void { }
}
