import { Component, OnInit, ViewChild } from '@angular/core';
import { AuthService } from '../../../services/auth.service';
import { User } from '../../../models/user';

import { MatPaginator } from "@angular/material/paginator";
import { MatTableDataSource } from "@angular/material/table";
import { FormControl, Validators } from "@angular/forms";

@Component({
  selector: 'app-profile-page',
  templateUrl: './profile-page.component.html',
  styleUrls: ['./profile-page.component.scss']
})
export class ProfilePageComponent implements OnInit {

  password = new FormControl('',
    [Validators.required, Validators.minLength(6)]
  );
  displayedColumns: string[] = ['user', 'username', 'email', 'action'];
  friends = new MatTableDataSource<any>();
  @ViewChild(MatPaginator) paginator: MatPaginator;
  user: User;

  constructor(private authService: AuthService) { }
  ngOnInit(): void {
    this.authService.getCurrentUser().subscribe(user => {
      this.user = user;
      this.friends = new MatTableDataSource<any>();
      this.friends.paginator = this.paginator;
    });
  }
  changePassword(): void {
    this.authService.login({ email: this.user.email, password: this.user.password })
  }
  getErrorMessage() {
    if (this.password.hasError('required')) {
      return 'You must enter a value';
    }

    return this.password.hasError('email') ? 'Not a valid email' : '';
  }
}
