import { Component, OnInit, ViewChild } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { Router } from '@angular/router';

@Component({
  selector: 'app-main-sidenav',
  templateUrl: './main-sidenav.component.html',
  styleUrls: ['./main-sidenav.component.scss']
})
export class MainSidenavComponent implements OnInit {

  @ViewChild('sidenav') sideNavRef: MatSidenav;

  routerLinkList = [
    { title: 'All files', link: 'files', matIcon: 'file_copy' },
    { title: 'Folders', link: 'folders', matIcon: 'folder' },
    { title: 'Category', link: 'files', matIcon: 'category',
      sub: [
        { title: 'Favourite', link: 'favourite', matIcon: 'favorite'},
        { title: 'Cars', link: 'cars', matIcon: 'star' }
      ]
    },
    { title: 'Public files', link: 'search', matIcon: 'public' },
    { title: 'Shared with', link: 'search', matIcon: 'folder_shared' },
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
