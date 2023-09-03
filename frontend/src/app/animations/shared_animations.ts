import { AnimationTriggerMetadata, trigger, state, style, transition, animate } from "@angular/animations";

export const fadeAnimation: AnimationTriggerMetadata = trigger('fade', [
    state('in', style({
        opacity: 1,
        width: '*',
    })),
    state('out', style({
        opacity: 0,
        width: 0,
    })),
    transition('in <=> out', [
        animate('0.1s'),
    ])
]);

export const hoverAnimation: AnimationTriggerMetadata = trigger('hoverOnOff', [
    state('on', style({
      backgroundColor: '#fff',
    })),
    state('off', style({
    })),
    transition('on => off', [
      animate('0.05s')
    ]),
    transition('off => on', [
      animate('0.1s')
    ]),
  ]);

export const hoverChildAnimation: AnimationTriggerMetadata = trigger('hoverOnOffChild', [
    state('on', style({
      color: 'black',
    })),
    state('off', style({
    })),
    transition('on => off', [
      animate('0.05s')
    ]),
    transition('off => on', [
      animate('0.1s')
    ]),
  ]);