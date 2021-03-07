import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { load } from './animations';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  animations: [ load ]
})
export class AppComponent {
  title = 'cloud-service';
}
