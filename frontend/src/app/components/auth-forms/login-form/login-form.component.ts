import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../../services/auth.service';
import { ActivatedRoute, Router } from '@angular/router';
import { environment } from "../../../../environments/environment";
import { TokenStorageService } from "../../../services/token-storage.service";

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.scss']
})
export class LoginFormComponent implements OnInit {
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  currentUser: any;
  form: any;
  googleURL = environment.googleAuthUrl;
  facebookURL = environment.facebookAuthUrl;

  loginGroup = new FormGroup({
    email: new FormControl('', Validators.required),
    password: new FormControl('', Validators.required)
  });
  constructor(
    private tokenStorage: TokenStorageService,
    private route: ActivatedRoute,
    private readonly authService: AuthService,
    private readonly router: Router
  ) { }


  ngOnInit(): void {
    const token: string = this.route.snapshot.queryParamMap.get('token');
    const error: string = this.route.snapshot.queryParamMap.get('error');
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.currentUser = this.tokenStorage.getUser();
    }
    else if(token){
      this.tokenStorage.saveToken(token);
      this.authService.getCurrentUser().subscribe(
        data => {
          this.login(data);
        },
        err => {
          this.errorMessage = err.error.message;
          this.isLoginFailed = true;
        }
      );
    }
    else if(error){
      this.errorMessage = error;
      this.isLoginFailed = true;
    }
  }
  onSubmit(): void {
    if (this.loginGroup.valid) {
      const email = this.loginGroup.value.email;
      const password = this.loginGroup.value.password;
      this.form = { email, password };
      this.authService.login(this.form).subscribe(
        data => {
          this.tokenStorage.saveToken(data.accessToken);
          this.login(data.user);
        },
        err => {
          this.errorMessage = err.error.message;
          this.isLoginFailed = true;
        }
      );
    }
  }

  login(user): void {
    this.tokenStorage.saveUser(user);
    this.isLoginFailed = false;
    this.isLoggedIn = true;
    this.currentUser = this.tokenStorage.getUser();
    window.location.reload();
    this.router.navigate(['/']);
  }

}
