import { AfterViewInit, Component, OnInit } from '@angular/core';
import { Observable, of, Subject } from 'rxjs';
import { FileService } from '../../services/file.service';
import {debounceTime, distinctUntilChanged, mergeMap} from 'rxjs/operators';
import { File } from '../../models/file';
import { SidenavService } from "../../services/sidenav.service";
import { ImageService } from "../../services/image.service";

@Component({
  selector: 'app-search-page',
  templateUrl: './search-page.component.html',
  styleUrls: ['./search-page.component.scss']
})
export class SearchPageComponent implements OnInit, AfterViewInit {

  files$: Observable<File[]>;
  private searchKeyword = new Subject<string>();
  constructor(private fileService: FileService,
              private sidenavService: SidenavService,
              private imageService: ImageService,) { }

  ngOnInit(): void {
    this.files$ = this.searchKeyword.pipe(
      debounceTime(250),
      distinctUntilChanged(),
      mergeMap((keyword: string) => !!keyword ?
        this.fileService.getSearchedPublicFiles(keyword) :
        this.fileService.getPublicFiles()
      )
    );
  }
  ngAfterViewInit() {
    this.searchKeyword.next(undefined);
  }

  inputChange(keyword: string): void {
    this.searchKeyword.next(keyword);
  }
  getImage(file: File): Observable<string> {
    return file.mimeType.includes('image') ?
      this.imageService.getImageForFile(file.id) :
      of('assets/images/file_icon.png');
  }
  openFileDetail(file): void {
    this.fileService.setSelectedFile(file);
    this.sidenavService.openDetailSidenav();
  }
}
