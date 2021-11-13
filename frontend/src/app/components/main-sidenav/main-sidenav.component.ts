import {Component, OnInit, ViewChild} from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';

@Component({
  selector: 'app-main-sidenav',
  templateUrl: './main-sidenav.component.html',
  styleUrls: ['./main-sidenav.component.css']
})
export class MainSidenavComponent implements OnInit {

  @ViewChild('sidenav') sideNavRef: MatSidenav;

  constructor() { }
  ngOnInit(): void { }

  async toggleSideNav(): Promise<void> {
    await this.sideNavRef.toggle();
  }
}
