import { Component, OnInit } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';
import { KeycloakProfile } from 'keycloak-js';
import { BackendService } from './data/service/backend.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  public isLoggedIn = false;
  public userProfile: KeycloakProfile | null = null;
  userRoles: string [];
  userContent: string;
  adminContent: string;

  constructor(private readonly keycloak: KeycloakService, private backendService: BackendService) { }

  public async ngOnInit() {
    this.isLoggedIn = await this.keycloak.isLoggedIn();

    if (this.isLoggedIn) {
      this.userProfile = await this.keycloak.loadUserProfile();
      this.userRoles = this.keycloak.getUserRoles(true);

      this.backendService.getUserData().subscribe(response => {
        this.userContent = response.data;
      }, error => {
        this.userContent = error.error.error;
      });

      this.backendService.getAdminData().subscribe(response => {
        this.adminContent = response.data;
      }, error => {
        this.adminContent = error.error.error;
      });
    }
  }

  public login() {
    this.keycloak.login();
  }

  public logout() {
    this.keycloak.logout();
  }
}
