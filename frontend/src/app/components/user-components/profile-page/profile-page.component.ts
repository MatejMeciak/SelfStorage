import { Component, OnInit, ViewChild } from '@angular/core';
import { AuthService } from '../../../services/auth.service';
import { User } from '../../../models/user';

import { MatPaginator } from "@angular/material/paginator";
import { MatTableDataSource } from "@angular/material/table";
import { FormBuilder, FormControl, FormGroup, Validators } from "@angular/forms";
import { CustomErrorStateMatcher } from "../../../CustomErrorStateMatcher";
import { UserService } from "../../../services/user.service";
import { fromEvent, map, mergeMap, Observable, of, shareReplay, take, takeUntil, tap } from "rxjs";
import { ImageService } from "../../../services/image.service";
import { DomSanitizer, SafeUrl } from "@angular/platform-browser";


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
  userProfilePicture: any;
  passwordFormGroup: FormGroup;
  loginFormGroup = new FormGroup({
    oldPassword: new FormControl('', Validators.required)
  });
  usernameFormGroup = new FormGroup({
    username: new FormControl('', [Validators.required, Validators.maxLength(25)])
  });
  storage: any;
  errorMessage: string;
  matcher = new CustomErrorStateMatcher();

  constructor(private authService: AuthService,
              private userService: UserService,
              private imageService: ImageService,
              private formBuilder: FormBuilder,
              private sanitizer: DomSanitizer) {
    this.passwordFormGroup = this.formBuilder.group({
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['']
    }, { validator: this.checkPasswords });
  }
  ngOnInit(): void {
    this.userService.getCurrentUser().subscribe(user => {
      this.user = user;
      this.userService.getProfilePicture().subscribe(blob => {
        let objectURL = URL.createObjectURL(blob);
        this.userProfilePicture = this.sanitizer.bypassSecurityTrustUrl(objectURL);
      });
      this.userService.getUserFriends().subscribe(friendList => {
        this.friends = new MatTableDataSource<any>(friendList);
      });
      this.friends.paginator = this.paginator;
      this.userService.getUserSpace().subscribe(storage => this.storage = storage);
    });
  }
  setProfilePicture(file: File): void {
      this.userService.setProfilePicture(file).subscribe(() => {
        location.reload();});
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
          this.userService.changePassword(oldPassword, newPassword).subscribe(() => {
            location.reload();
          })
        },
        err => {
          this.errorMessage = err.error.message;
        }
      );
    }
  }
  changeUsername(): void {
    if (this.usernameFormGroup.valid) {
      this.userService.changeUsername(this.usernameFormGroup.value.username).subscribe(() => {
        location.reload();
      });
    }
  }
}

