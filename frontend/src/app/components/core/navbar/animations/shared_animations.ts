import { AnimationTriggerMetadata, trigger, state, style, transition, animate } from "@angular/animations";

export const fadeAnimation: AnimationTriggerMetadata = trigger('fade', [
    state('in', style({
        opacity: 1,
    })),
    state('out', style({
        opacity: 0,
    })),
    transition('in <=> out', [
        animate('0.1s'),
    ])
]);