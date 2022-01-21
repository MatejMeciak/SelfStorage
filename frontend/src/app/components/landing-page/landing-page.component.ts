import { Component, OnInit } from '@angular/core';
import {File} from "../../models/file";
import {FileService} from "../../services/file.service";
import {MatDialog} from "@angular/material/dialog";
import {UploadFileDialogComponent} from "../dialogs/upload-file-dialog/upload-file-dialog.component";

@Component({
  selector: 'app-welcome-page',
  templateUrl: './landing-page.component.html',
  styleUrls: ['./landing-page.component.scss']
})
export class LandingPageComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {

  }
}
