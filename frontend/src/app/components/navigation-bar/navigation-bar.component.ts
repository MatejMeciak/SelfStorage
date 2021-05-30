import { NavigationEnd, Router} from '@angular/router';
import { animate, group, keyframes, query, style, transition, trigger } from '@angular/animations';
import { Component, OnInit } from '@angular/core';
import { load } from '../../material/animations';
import {AuthService} from '../../services/auth.service';
import {filter} from 'rxjs/operators';

@Component({
  selector: 'app-navigation-bar',
  templateUrl: './navigation-bar.component.html',
  styleUrls: ['./navigation-bar.component.css'],
  animations: [
    load,
    trigger('slider', [
      transition('* => slide', [
        group([
          query('.text', style({ opacity: 0 })),
          query('mat-icon', animate('300ms ease',
            style({ transform: 'translateX(86px)', borderLeftWidth: '1px' }))),
          animate(300, keyframes([
            style({ backgroundColor: 'white', offset: 0.3}),
            style({ backgroundColor: 'orange', offset: 1})
          ])),
        ]),
      ]),
    ])
  ]
})
export class NavigationBarComponent implements OnInit {

  isLoggedIn: boolean;
  animate = false;
  url: string;

  get action(): string {
    return  this.url === '/login' ? 'REGISTER' : !this.isLoggedIn ? 'LOGIN' : 'LOGOUT';
  }
  links: string[] = ['home', 'profile', 'files', 'search'];

  constructor(private router: Router, private authService: AuthService) { }

  ngOnInit(): void {
    this.authService.token.subscribe(() => this.isLoggedIn = this.authService.isLoggedIn());
    this.router.events.pipe(filter(event => event instanceof NavigationEnd))
      .subscribe((routerUrl: NavigationEnd) => this.url = routerUrl.urlAfterRedirects);
  }

  toggle(): void {
    this.animate = true;
    this.isLoggedIn = this.authService.isLoggedIn();
    const url = this.url === '/login' ? '/registration' : !this.isLoggedIn ? '/login' : '/';
    setTimeout(() => {
      if (this.isLoggedIn){
        this.authService.logout();
      }
      this.router.navigateByUrl(url);
      this.animate = false;
      }, 300);
  }
}
