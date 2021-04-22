import { Component, Input, OnInit } from '@angular/core';
import { File } from '../../../models/file';
import { environment } from '../../../../environments/environment';

@Component({
  selector: 'app-file-card',
  templateUrl: './file-card.component.html',
  styleUrls: ['./file-card.component.css']
})
export class FileCardComponent implements OnInit {
  @Input() file: File;
  constructor() { }

  ngOnInit(): void {
  }
  getFileUrl(id: number): string {
    return `${environment.apiUrl}/file/${id}`;
  }
}
