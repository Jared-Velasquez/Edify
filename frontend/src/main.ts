import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';

import { AppModule } from './app/app.module';

// https://www.figma.com/community/file/1009711598420640093/EduPrix-Online-Education-Website-Template--Crafted-by-ThemeWagon
// https://dribbble.com/shots/18418516-edtech-platform-product-design-dashboard


platformBrowserDynamic().bootstrapModule(AppModule)
  .catch(err => console.error(err));
