import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { load } from '../animations';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css'],
  animations: [ load ]
})
export class LoginFormComponent implements OnInit {


  constructor() { }

  ngOnInit(): void {

  }

}
