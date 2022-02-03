import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import {AuthService} from "../../services/auth.service";
import {User} from "../../models/user";
import {TokenStorageService} from "../../services/token-storage.service";
import {Router} from "@angular/router";
import {SidenavService} from "../../services/sidenav.service";

@Component({
  selector: 'app-navigation-bar',
  templateUrl: './navigation-bar.component.html',
  styleUrls: ['./navigation-bar.component.scss']
})
export class NavigationBarComponent implements OnInit {

  isLoggedIn = false;
  username: string;
  user: User;

  constructor(
    private tokenStorageService: TokenStorageService,
    private authService: AuthService,
    private sidenavService: SidenavService,
  ) { }

  ngOnInit(): void {
    this.isLoggedIn = !!this.tokenStorageService.getToken();
    console.log(this.tokenStorageService.getToken());
    if (this.isLoggedIn) {
      this.user = this.tokenStorageService.getUser();
      this.username = this.user.username;
    }
  }

  async toggleSideNav(): Promise<void> {
    await this.sidenavService.toggleMainSidenav();
  }
}
