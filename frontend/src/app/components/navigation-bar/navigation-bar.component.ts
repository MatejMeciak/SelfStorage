import { Router } from '@angular/router';
import { animate, group, keyframes, query, stagger, state, style, transition, trigger } from '@angular/animations';
import { Component, OnInit } from '@angular/core';
import { load } from '../../material/animations';
import {AuthService} from '../../services/auth.service';

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
          query('mat-icon', animate('300ms ease', style({ transform: 'translateX(86px)', borderLeftWidth: '1px' }))),
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

  isAuthed: boolean;
  animate = false;

  links: string[] = ['home', 'profile', 'files', 'search'];

  constructor(private router: Router, private authService: AuthService) { }

  ngOnInit(): void {
    // redone with Observable
    this.isAuthed = this.authService.isLoggedIn();
  }

  toggle(): void {
    this.animate = true;
    this.isAuthed = this.authService.isLoggedIn();
    if (this.isAuthed) { this.authService.logout(); }
    else {
      setTimeout(() => { this.router.navigate(['login']); this.animate = false; }, 300);
    }
  }


}
