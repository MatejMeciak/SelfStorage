import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { load } from '../../material/animations';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css'],
  animations: [ load ]
})
export class HomePageComponent implements OnInit {

  constructor(private route: ActivatedRoute) {
    const data = this.route.snapshot.data;
    console.log(data);
  }

  ngOnInit(): void {
  }

}
