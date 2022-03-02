import { Component, OnInit } from '@angular/core';
import { map, mergeMap, Observable, tap } from "rxjs";
import { File } from "../../models/file";
import { FileService } from "../../services/file.service";
import { FolderService } from "../../services/folder.service";
import { Folder } from "../../models/folder";
import { ActivatedRoute } from "@angular/router";

@Component({
  selector: 'app-shared-page',
  templateUrl: './shared-page.component.html',
  styleUrls: ['./shared-page.component.scss']
})
export class SharedPageComponent implements OnInit {

  files$: Observable<File[]>;
  folders$: Observable<Folder[]>;
  constructor(private fileService: FileService,
              private folderService: FolderService,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.files$ = this.route.queryParamMap.pipe(
      map(queryMap => queryMap.get('email')),
      mergeMap(email => !!email
        ? this.fileService.getSharedFilesWith(email)
        : this.fileService.getSharedFiles()
      )
    );
    this.folders$ = this.route.queryParamMap.pipe(
      map(queryMap => queryMap.get('email')),
      mergeMap(email => !!email
        ? this.folderService.getFoldersFromFriends(email)
        : this.folderService.getSharedFolders()
      )
    );
  }

}
