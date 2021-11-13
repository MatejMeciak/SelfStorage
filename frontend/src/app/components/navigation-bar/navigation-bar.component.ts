import { Component, EventEmitter, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-navigation-bar',
  templateUrl: './navigation-bar.component.html',
  styleUrls: ['./navigation-bar.component.css']
})
export class NavigationBarComponent implements OnInit {

  @Output() toggleSideNavEvent = new EventEmitter();

  constructor() { }

  ngOnInit(): void { }

  toggleSideBar(): void {
    this.toggleSideNavEvent.emit();
  }
}
