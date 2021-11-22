import { Directive, ElementRef, HostListener } from '@angular/core';

@Directive({
  selector: '[appHighlight]'
})
export class HighlightDirective {
  defaultColor = 'rgba(0,0,0,0.05)';

  constructor(private el: ElementRef) {
    this.el.nativeElement.style.borderRadius = '4px';
  }

  @HostListener('mouseenter') onMouseEnter(): void {
    this.highlight(this.defaultColor);
  }
  @HostListener('mouseleave') onMouseLeave(): void {
    this.highlight('');
  }

  private highlight(color: string): void {
    this.el.nativeElement.style.backgroundColor = color;
  }
}
