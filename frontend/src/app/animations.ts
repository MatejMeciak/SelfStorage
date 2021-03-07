import { trigger, transition, style, animate, state, query, group } from '@angular/animations';

export const load = trigger('load', [
    transition(':enter', [style({ opacity : 0}), animate(600)])
]);