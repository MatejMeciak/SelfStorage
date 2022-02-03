import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Folder } from "../../../models/folder";
import { File } from "../../../models/file";

@Component({
  selector: 'app-content-card',
  templateUrl: './content-card.component.html',
  styleUrls: ['./content-card.component.scss']
})
export class ContentCardComponent implements OnInit {
  @Input() content: File | Folder;
  @Input() image: string;
  @Input() category: string;
  @Output() clickEvent = new EventEmitter<File | Folder>()

  constructor() { }
  ngOnInit(): void { }

  onClick(content: File | Folder) {
    this.clickEvent.emit(content);
  }
}
