import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import {AuthService} from "../../services/auth.service";
import {User} from "../../models/user";
import {TokenStorageService} from "../../services/token-storage.service";
import {Router} from "@angular/router";
import {SidenavService} from "../../services/sidenav.service";
import { UserService } from "../../services/user.service";
import { DomSanitizer } from "@angular/platform-browser";
import { Observable } from "rxjs";

@Component({
  selector: 'app-navigation-bar',
  templateUrl: './navigation-bar.component.html',
  styleUrls: ['./navigation-bar.component.scss']
})
export class NavigationBarComponent implements OnInit {

  isLoggedIn = false;
  username: string;
  username$: string;
  user: User;
  user$: Observable<User>;
  userProfilePicture: any;

  constructor(
    private tokenStorageService: TokenStorageService,
    private authService: AuthService,
    private userService: UserService,
    private sidenavService: SidenavService,
    private sanitizer: DomSanitizer
  ) { }

  ngOnInit(): void {
    this.isLoggedIn = !!this.tokenStorageService.getToken();
    if (this.isLoggedIn) {
      this.user = this.tokenStorageService.getUser();
      this.user$ = this.userService.getCurrentUser();
      this.username = this.user.username;
      this.userService.getCurrentUser().subscribe((u) => {
        this.username$ = u.username
      });
      this.userService.getProfilePicture().subscribe(blob => {
        let objectURL = URL.createObjectURL(blob);
        this.userProfilePicture = this.sanitizer.bypassSecurityTrustUrl(objectURL);
      });
    }
  }

  async toggleSideNav(): Promise<void> {
    await this.sidenavService.toggleMainSidenav();
  }
}
