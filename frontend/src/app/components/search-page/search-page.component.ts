import { Component, OnInit } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { FileService } from '../../services/file.service';
import { debounceTime, distinctUntilChanged, mergeMap } from 'rxjs/operators';
import { File } from '../../models/file';

@Component({
  selector: 'app-search-page',
  templateUrl: './search-page.component.html',
  styleUrls: ['./search-page.component.css']
})
export class SearchPageComponent implements OnInit {

  selectedFile: File;
  files: Observable<File[]>;
  private searchKeyword = new Subject<string>();
  constructor(private fileService: FileService) { }

  ngOnInit(): void {
    this.files = this.searchKeyword.pipe(
      debounceTime(250),
      distinctUntilChanged(),
      mergeMap((keyword: string) => this.fileService.searchFile(keyword)),
    );
  }

  inputChange(keyword: string): void {
    this.searchKeyword.next(keyword);
  }

  search(): void {

  }
}
