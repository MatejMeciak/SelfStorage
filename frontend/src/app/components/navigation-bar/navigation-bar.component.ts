import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import {AuthService} from "../../services/auth.service";
import {User} from "../../models/user";
import {File} from "../../models/file";

@Component({
  selector: 'app-navigation-bar',
  templateUrl: './navigation-bar.component.html',
  styleUrls: ['./navigation-bar.component.scss']
})
export class NavigationBarComponent implements OnInit {

  @Output() toggleSideNavEvent = new EventEmitter();

  constructor(private authService: AuthService) { }
  user: User;

  ngOnInit(): void {
    this.getUser();
  }
  getUser(): void {
    this.authService.getUser().subscribe(user => this.user = user);
  }

  toggleSideBar(): void {
    this.toggleSideNavEvent.emit();
  }
}
