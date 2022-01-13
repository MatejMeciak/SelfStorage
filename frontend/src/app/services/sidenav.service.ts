import { Injectable } from '@angular/core';
import {MatDrawer, MatSidenav} from "@angular/material/sidenav";

@Injectable({
  providedIn: 'root'
})
export class SidenavService {
  private mainSidenav: MatSidenav;

  public setMainSidenav(sidenav: MatSidenav) {
    this.mainSidenav = sidenav;
  }

  public open(sidenav:MatSidenav, MatDrawer) {
    return sidenav.open();
  }

  public close(sidenav:MatSidenav, MatDrawer) {
    return sidenav.close();
  }

  public toggle(sidenav:MatSidenav, MatDrawer): void {
    sidenav.toggle();
  }
}
