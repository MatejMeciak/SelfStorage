import { ActivatedRoute, Router } from '@angular/router';
import { animate, group, keyframes, query, stagger, state, style, transition, trigger } from '@angular/animations';
import { Component, OnInit } from '@angular/core';
import { load } from '../animations';

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
          query('mat-icon', animate('300ms ease', style({ transform: 'translateX(86px)',borderLeftWidth:'1px' }))),
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

  animate: boolean = false;

  links: string[] = ['home', 'profile', 'files', 'search'];

  constructor(private router: Router) { 
  }

  ngOnInit(): void {
  }

  toggle(): void {
    this.animate = true;
    setTimeout(() => { this.router.navigate(['/login']); this.animate = false; }, 300)
  }


}
