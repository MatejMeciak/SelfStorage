import { Router } from '@angular/router';
import { animate, group, keyframes, query, style, transition, trigger } from '@angular/animations';
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
    this.authService.token.subscribe(() => this.isAuthed = this.authService.isLoggedIn());
  }

  toggle(): void {
    // TODO redone animation setTimeout
    this.animate = true;
    this.isAuthed = this.authService.isLoggedIn();
    if (this.isAuthed) { this.authService.logout(); this.router.navigateByUrl('/'); }
    else {
      setTimeout(() => { this.router.navigateByUrl('/login'); this.animate = false; }, 300);
    }
  }


}
