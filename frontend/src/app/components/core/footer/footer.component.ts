import { Component } from '@angular/core';
import { socialMedia, footerLinks, FooterInterface, LinksInterface } from 'src/constants';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css']
})
export class FooterComponent {
  socialMediaList: LinksInterface[] = socialMedia;
  footerLinksList: FooterInterface[] = footerLinks;
}
