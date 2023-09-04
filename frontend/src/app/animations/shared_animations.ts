import { AnimationTriggerMetadata, trigger, state, style, transition, animate, query, stagger, keyframes } from "@angular/animations";

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

export const fadeDelayedAnimation: AnimationTriggerMetadata = trigger('fade', [
  state('in', style({
      opacity: 1,
      width: '*',
  })),
  state('out', style({
      opacity: 0,
      width: 0,
  })),
  transition('in <=> out', [
      animate('0.5s'),
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

  export const listAnimation: AnimationTriggerMetadata = trigger('listAnimation', [
    transition('* => *', [
      query(':enter', style({ opacity: 0 }), { optional: true }),
      query(':enter', stagger('500ms', [
        animate('750ms ease-in', keyframes([
          style({ opacity: 0, transform: 'translateY(100px)', offset: 0 }),
          style({ opacity: .25, transform: 'translateY(50px)', offset: 0.25 }),
          style({ opacity: .5, transform: 'translateY(25px)', offset: 0.5 }),
          style({ opacity: .75, transform: 'translateY(10px)', offset: 0.75 }),
          style({ opacity: 1, transform: 'translateY(0)', offset: 1 }),
        ]))
      ]), { optional: true })
    ])
  ])