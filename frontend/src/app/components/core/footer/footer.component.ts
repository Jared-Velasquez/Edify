import { Component } from '@angular/core';
import styles from 'src/styles';
import { socialMedia } from 'src/constants';
import { footerLinks } from 'src/constants';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css']
})
export class FooterComponent {
  styles = styles;
  socialMediaList = socialMedia;
  footerLinksList = footerLinks;
}
