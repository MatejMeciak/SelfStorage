import {Component, OnDestroy, OnInit} from '@angular/core';
import {TokenStorageService} from "./services/token-storage.service";
import {AuthService} from "./services/auth.service";
import { Subscription } from 'rxjs';
import { SidenavService } from "./services/sidenav.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit, OnDestroy {
  isLoggedIn = false;
  showAdminBoard = false;
  subscription: Subscription;
  constructor(private tokenStorageService: TokenStorageService,
              private sidenavService: SidenavService,
              private authService: AuthService) { }

  ngOnInit() {
    this.isLoggedIn = !!this.tokenStorageService.getToken();

    if (this.isLoggedIn) {
      this.subscription = this.authService.getCurrentUser().subscribe(user => {
        this.showAdminBoard = user.roles.includes('ROLE_ADMIN');
      });
    }
  }
  ngOnDestroy() {
    this.subscription.unsubscribe();
  }
}
