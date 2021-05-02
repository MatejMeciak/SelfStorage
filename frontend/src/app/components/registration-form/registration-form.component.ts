import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-registration-form',
  templateUrl: './registration-form.component.html',
  styleUrls: ['./registration-form.component.css']
})
export class RegistrationFormComponent implements OnInit {

  registerGroup = new FormGroup({
    username: new FormControl('', Validators.required),
    password: new FormControl('', Validators.required),
    firstName: new FormControl('', Validators.required),
    lastName: new FormControl('', Validators.required),
  });
  constructor(
    private readonly authService: AuthService, private readonly router: Router
  ) { }

  ngOnInit(): void {
  }
  register(): void {
    if (this.registerGroup.valid) {
      const username = this.registerGroup.value.username;
      const password = this.registerGroup.value.password;
      const firstName = this.registerGroup.value.firstName;
      const lastName = this.registerGroup.value.lastName;
      this.authService.register(username, password, firstName, lastName)
        .subscribe(() => this.router.navigateByUrl('/home'));
    }
  }

}
