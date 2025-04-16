import {AuthService} from 'src/app/service/auth/auth.service';
import {Component, OnDestroy, OnInit} from '@angular/core';
import {NavigationEnd, Router} from '@angular/router';
import {Subscription} from 'rxjs';
import {filter} from 'rxjs/operators';
import {Link} from 'src/types/types';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html'
})
export class NavbarComponent implements OnInit, OnDestroy {
  private authSubscription!: Subscription;
  isLoggedIn = false;
  links: Link[] = [
    {
        label: 'Home',
        path: '/',
        icon: 'pi-home',
        active: false,
        disabled: false,
        target: '_self',
        hidden: false,
      },
      {
        label: 'Login',
        path: '/login',
        icon: 'pi-user',
        active: false,
        disabled: false,
        target: '_self',
        hidden: this.isLoggedIn,
      },
      {
        label: 'Rides',
        path: '/rides',
        icon: 'pi-map-marker',
        active: false,
        disabled: false,
        target: '_self',
        hidden: !this.isLoggedIn,
      },
      {
        label: 'Users',
        path: '/users',
        icon: 'pi-users',
        active: false,
        disabled: false,
        target: '_self',
        hidden: !this.isLoggedIn,
      },
      {
        label: 'Drivers',
        path: '/drivers',
        icon: 'pi-id-card',
        active: false,
        disabled: false,
        target: '_self',
        hidden: !this.isLoggedIn,
      },
      {
        label: 'Vehicles',
        path: '/vehicles',
        icon: 'pi-car',
        active: false,
        disabled: false,
        target: '_self',
        hidden: !this.isLoggedIn,
      },
      {
        label: 'Profile',
        path: '/profile',
        icon: 'pi-user',
        active: false,
        disabled: false,
        target: '_self',
        hidden: !this.isLoggedIn,
      },

  ];

  constructor(private router: Router, private authService: AuthService) {
    this.router.events
      .pipe(filter((event) => event instanceof NavigationEnd))
      .subscribe(() => {
        this.setActiveLink();
      });
  }

  ngOnInit() {
    this.authSubscription = this.authService.loggedIn$.subscribe(
      (isLoggedIn) => {
        this.isLoggedIn = isLoggedIn;
        this.updateLinksVisibility();
      }
    );
    this.setActiveLink();
  }

  ngOnDestroy() {
    if (this.authSubscription) {
      this.authSubscription.unsubscribe();
    }
  }

  setActiveLink() {
    const currentPath = this.router.url;

    this.links.forEach((link) => {
      if (link.path === "/" && currentPath === "/") {
        link.active = true;
      }
      else {
        link.active = currentPath === link.path ||
                     (link.path !== "/" && currentPath.startsWith(link.path + "/"));
      }
    });
  }

  updateLinksVisibility() {
    this.links = this.links.map((link) => {
      if (link.label === 'Login') {
        return { ...link, hidden: this.isLoggedIn };
      } else if (link.label !== 'Home') {
        return { ...link, hidden: !this.isLoggedIn };
      }
      return link;
    });
  }
}
