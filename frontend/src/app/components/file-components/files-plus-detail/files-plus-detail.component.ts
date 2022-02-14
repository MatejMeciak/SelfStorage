import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatDrawer } from "@angular/material/sidenav";

import { SidenavService } from "../../../services/sidenav.service";
import { Observable } from "rxjs";
import { File } from "../../../models/file";

@Component({
  selector: 'app-files-plus-detail',
  templateUrl: './files-plus-detail.component.html',
  styleUrls: ['./files-plus-detail.component.scss']
})
export class FilesPlusDetailComponent implements OnInit {
  @ViewChild('detailSidenav', { static: true }) public detailSidenav: MatDrawer;

  @Input() content$: Observable<File[]>;

  constructor(private sidenavService: SidenavService) { }

  ngOnInit(): void {
    this.sidenavService.setDetailSidenav(this.detailSidenav);
  }
}
