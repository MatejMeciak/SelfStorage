import { trigger, transition, style, animate, state } from '@angular/animations';

export let load = trigger('load', [
    transition(':enter', [
        style({ opacity: 0 }),
        animate(650)
    ])
]);