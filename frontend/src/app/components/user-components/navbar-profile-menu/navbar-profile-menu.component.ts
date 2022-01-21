import { Component, OnInit } from '@angular/core';
import {TokenStorageService} from "../../../services/token-storage.service";
import {Router} from "@angular/router";
import { User } from "../../../models/user";

@Component({
  selector: 'app-navbar-profile-menu',
  templateUrl: './navbar-profile-menu.component.html',
  styleUrls: ['./navbar-profile-menu.component.scss']
})
export class NavbarProfileMenuComponent implements OnInit {
  isLoggedIn = false;
  user: User;

  constructor(private tokenStorageService: TokenStorageService,
              private router: Router) { }

  ngOnInit(): void {
    this.isLoggedIn = !!this.tokenStorageService.getToken();
    this.user = this.tokenStorageService.getUser();
  }

  logout(): void {
    this.tokenStorageService.signOut();
    window.location.reload();
    this.router.navigate(['login']);
  }
}
