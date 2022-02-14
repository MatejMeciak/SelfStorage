import { Component, OnInit } from '@angular/core';
import { Observable } from "rxjs";
import { File } from "../../../models/file";
import { FileService } from "../../../services/file.service";

@Component({
  selector: 'app-only-files',
  templateUrl: './only-files.component.html',
  styleUrls: ['./only-files.component.scss']
})
export class OnlyFilesComponent implements OnInit {

  files$: Observable<File[]>;
  constructor(private fileService: FileService) { }

  ngOnInit(): void {
    this.files$ = this.fileService.getAllFiles();
  }

}
