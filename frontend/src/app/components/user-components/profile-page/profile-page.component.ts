import { Component, OnInit } from '@angular/core';
import {AuthService} from '../../../services/auth.service';
import {User} from '../../../models/user';
import {FileService} from '../../../services/file.service';
import {File} from '../../../models/file';

@Component({
  selector: 'app-profile-page',
  templateUrl: './profile-page.component.html',
  styleUrls: ['./profile-page.component.scss']
})
export class ProfilePageComponent implements OnInit {

  constructor(private authService: AuthService, private fileService: FileService) { }
  user;
  sharedFiles: File[];
  ngOnInit(): void {
    this.authService.getCurrentUser().subscribe(user => {
      this.user = user;
    });
    this.getSharedFiles();
  }
  getSharedFiles(): void {
    this.fileService.getSharedFiles().subscribe(files => this.sharedFiles = files);
  }

}
