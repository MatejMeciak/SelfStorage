import {Component, HostListener} from '@angular/core';
import { load } from './material/animations';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  animations: [ load ]
})
export class AppComponent {
  title = 'cloud-service';
}
