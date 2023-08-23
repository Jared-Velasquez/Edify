import { Component } from '@angular/core';
import styles from 'src/styles';
import { socialMedia, footerLinks, Footer, Links } from 'src/constants';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css']
})
export class FooterComponent {
  styles = styles;
  socialMediaList: Links[] = socialMedia;
  footerLinksList: Footer[] = footerLinks;
}
