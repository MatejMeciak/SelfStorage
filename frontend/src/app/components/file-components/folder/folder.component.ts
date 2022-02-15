import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FileService } from '../../../services/file.service';
import { File} from '../../../models/file';
import { Folder } from '../../../models/folder';
import { MatDialog } from '@angular/material/dialog';
import { FolderService } from "../../../services/folder.service";
import { map, mergeMap, Observable, tap } from "rxjs";

@Component({
  selector: 'app-folder',
  templateUrl: './folder.component.html',
  styleUrls: ['./folder.component.scss']
})
export class FolderComponent implements OnInit {
  folder$: Observable<Folder>;
  files$: Observable<File[]>;
  constructor(private folderService: FolderService, private fileService: FileService,
              private route: ActivatedRoute, private dialog: MatDialog) { }

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.folder$ = this.folderService.getFolder(id);
    this.files$ = this.folderService.getFolderContent(id);
    }
    // this.folder$ = this.route.paramMap.pipe(
    //   map(paramMap => Number(paramMap.get('id'))),
    //   tap(id => this.files$ = this.folderService.getFolderContent(id)),
    //   mergeMap(id => this.folderService.getFolder(id)
    //   )
    // );
}
