import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { File } from '../../../models/file';
import { environment } from '../../../../environments/environment';
import { FileService } from '../../../services/file.service';
import * as fileSaver from 'file-saver';

@Component({
  selector: 'app-file-detail',
  templateUrl: './file-detail.component.html',
  styleUrls: ['./file-detail.component.css']
})
export class FileDetailComponent implements OnInit {

  @Input() file: File;
  @Output() closeEvent = new EventEmitter<File>();
  constructor( private fileService: FileService) { }

  ngOnInit(): void { }
  closeFile(): void {
    this.closeEvent.emit(null);
  }
  getFileUrl(id: number): string {
    return `${environment.apiUrl}/file/${id}`;
  }
  downloadFile(): void {
    this.fileService.downloadFile(this.file).subscribe(blob => {
      fileSaver.saveAs(blob, this.file.fileName);
    });
  }
}
