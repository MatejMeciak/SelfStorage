import { Component, OnInit, ViewChild } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { Router } from '@angular/router';

@Component({
  selector: 'app-main-sidenav',
  templateUrl: './main-sidenav.component.html',
  styleUrls: ['./main-sidenav.component.css']
})
export class MainSidenavComponent implements OnInit {

  @ViewChild('sidenav') sideNavRef: MatSidenav;

  routerLinkList = [
    {
      title: 'All files',
      link: 'files',
      matIcon: 'file_copy'
    },
    {
      title: 'Folders',
      link: '',
      matIcon: 'folder'
    },
    {
      title: 'Category',
      link: '',
      matIcon: 'category',
      sub: [
        {
          title: 'Favourite',
          link: '',
          matIcon: 'favorite',
        },
        {
          title: 'Cars',
          link: '',
          matIcon: 'directions_car_filled',
        }
      ]
    },
    {
      title: 'Public files',
      link: 'search',
      matIcon: 'public'
    },
  ];
  constructor(private router: Router) {
    router.events.subscribe(() => {
      this.sideNavRef.close();
    });
  }
  ngOnInit(): void { }

  async toggleSideNav(): Promise<void> {
    await this.sideNavRef.toggle();
  }
}
