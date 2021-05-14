import { Component, OnInit } from '@angular/core';
import { load } from '../../../material/animations';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css'],
  animations: [ load ]
})
export class LoginFormComponent implements OnInit {

  loginGroup = new FormGroup({
    username: new FormControl('', Validators.required),
    password: new FormControl('', Validators.required)
  });
  constructor(
    private readonly authService: AuthService,
    private readonly router: Router
  ) { }


  ngOnInit(): void { }
  login(): void {
    if (this.loginGroup.valid) {
      const username = this.loginGroup.value.username;
      const password = this.loginGroup.value.password;
      this.authService.login(username, password)
        .subscribe(() => this.router.navigateByUrl('/home'));
    }
  }

}
