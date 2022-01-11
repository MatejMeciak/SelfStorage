import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import {AuthService} from "../../services/auth.service";
import {User} from "../../models/user";
import {File} from "../../models/file";
import {TokenStorageService} from "../../services/token-storage.service";

@Component({
  selector: 'app-navigation-bar',
  templateUrl: './navigation-bar.component.html',
  styleUrls: ['./navigation-bar.component.scss']
})
export class NavigationBarComponent implements OnInit {

  isLoggedIn = false;
  // private roles: string[];
  // showAdminBoard = false;
  // showModeratorBoard = false;
  username: string;

  @Output() toggleSideNavEvent = new EventEmitter();

  constructor(
    private tokenStorageService: TokenStorageService,
    private authService: AuthService
  ) { }
  user: User;

  ngOnInit(): void {
    this.getUser();
    this.isLoggedIn = !!this.tokenStorageService.getToken();

    if (this.isLoggedIn) {
      this.user = this.tokenStorageService.getUser();
      // this.roles = user.roles;
      //
      // this.showAdminBoard = this.roles.includes('ROLE_ADMIN');
      // this.showModeratorBoard = this.roles.includes('ROLE_MODERATOR');

      this.username = this.user.username;
    }
  }
  getUser(): void {
    //this.authService.getUser().subscribe(user => this.user = user);
  }

  toggleSideBar(): void {
    this.toggleSideNavEvent.emit();
  }
  // logout() {
  //   this.authService.logout();
  // }
  logout(): void {
    this.tokenStorageService.signOut();
    window.location.reload();
  }
}
