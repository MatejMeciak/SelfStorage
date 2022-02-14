import { Component, OnInit, ViewChild } from '@angular/core';
import { AuthService } from '../../../services/auth.service';
import { User } from '../../../models/user';

import { MatPaginator } from "@angular/material/paginator";
import { MatTableDataSource } from "@angular/material/table";
import { FormBuilder, FormControl, FormGroup, Validators } from "@angular/forms";
import { CustomErrorStateMatcher } from "../../../CustomErrorStateMatcher";


@Component({
  selector: 'app-profile-page',
  templateUrl: './profile-page.component.html',
  styleUrls: ['./profile-page.component.scss']
})
export class ProfilePageComponent implements OnInit {

  displayedColumns: string[] = ['user', 'username', 'email', 'action'];
  friends = new MatTableDataSource<any>();
  @ViewChild(MatPaginator) paginator: MatPaginator;
  user: User;
  passwordFormGroup: FormGroup;
  loginFormGroup = new FormGroup({
    oldPassword: new FormControl('', Validators.required)
  });

  errorMessage: string;
  matcher = new CustomErrorStateMatcher();

  constructor(private authService: AuthService,
              private formBuilder: FormBuilder) {
    this.passwordFormGroup = this.formBuilder.group({
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['']
    }, { validator: this.checkPasswords });
  }
  ngOnInit(): void {
    this.authService.getCurrentUser().subscribe(user => {
      this.user = user;
      this.friends = new MatTableDataSource<any>();
      this.friends.paginator = this.paginator;
    });
  }
  checkPasswords(group: FormGroup) {
    let pass = group.controls['password'].value;
    let confirmPass = group.controls['confirmPassword'].value;

    return pass === confirmPass ? null : { notSame: true }
  }
  changePassword(): void {
    if (this.passwordFormGroup.valid) {
      const oldPassword = this.loginFormGroup.value.oldPassword;
      const newPassword = this.passwordFormGroup.value.password;

      this.authService.login({
        email: this.user.email, password: oldPassword
      }).subscribe(
        data => {
          this.authService.changePassword(oldPassword, newPassword).subscribe(() => {
            location.reload();
          })
        },
        err => {
          this.errorMessage = err.error.message;
        }
      );
    }
  }
}
