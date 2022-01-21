import { Injectable } from '@angular/core';
import {MatDrawer, MatSidenav} from "@angular/material/sidenav";

@Injectable({
  providedIn: 'root'
})
export class SidenavService {
  private mainSidenav: MatSidenav;
  private detailSidenav: MatDrawer;

  public setMainSidenav(sidenav: MatSidenav) {
    this.mainSidenav = sidenav;
  }
  public setDetailSidenav(sidenav: MatDrawer) {
    this.detailSidenav = sidenav;
  }


  public openMainSidenav() {
    return this.mainSidenav.open();
  }
  public closeMainSidenav() {
    return this.mainSidenav.close();
  }
  public toggleMainSidenav() {
    return this.mainSidenav.toggle();
  }

  public openDetailSidenav() {
    return this.detailSidenav.open();
  }
  public closeDetailSidenav() {
    return this.detailSidenav.close();
  }
  public toggleDetailSidenav() {
    return this.detailSidenav.toggle();
  }
}
