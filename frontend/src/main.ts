import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';

import { AppModule } from './app/app.module';

// https://www.figma.com/community/file/1009711598420640093/EduPrix-Online-Education-Website-Template--Crafted-by-ThemeWagon
// https://www.figma.com/file/HtwEBKgg5pmz3f8vQgs87H/Educational-Dashboard-(Teachers)-(Community)?type=design&node-id=1-1530&mode=design&t=lMW6hnNv09oeAsPB-0


platformBrowserDynamic().bootstrapModule(AppModule)
  .catch(err => console.error(err));
