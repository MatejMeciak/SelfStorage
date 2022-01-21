import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../../services/auth.service';
import { Router } from '@angular/router';
import { environment } from "../../../../environments/environment";

@Component({
  selector: 'app-registration-form',
  templateUrl: './registration-form.component.html',
  styleUrls: ['./registration-form.component.scss']
})
export class RegistrationFormComponent implements OnInit {
  isSuccessful = false;
  isSignUpFailed = false;
  errorMessage = '';
  form:any;
  googleURL = environment.googleAuthUrl;
  facebookURL = environment.facebookAuthUrl;


  registerGroup = new FormGroup({
    username: new FormControl('', Validators.required),
    password: new FormControl('', Validators.required),
    matchingPassword: new FormControl('', Validators.required),
    email: new FormControl('', Validators.required),
  });
  constructor(
    private readonly authService: AuthService,
    private readonly router: Router
  ) { }

  ngOnInit(): void { }
  onSubmit(): void {
    if (this.registerGroup.valid) {
      const username = this.registerGroup.value.username;
      const password = this.registerGroup.value.password;
      const matchingPassword = this.registerGroup.value.matchingPassword;
      const email = this.registerGroup.value.email;
      this.form = { username, password, email };
      this.authService.register(this.form).subscribe(
        data => {
          this.isSuccessful = true;
          this.isSignUpFailed = false;
        },
        err => {
          this.errorMessage = err.error.message;
          this.isSignUpFailed = true;
        }
      );
    }

  }
}
