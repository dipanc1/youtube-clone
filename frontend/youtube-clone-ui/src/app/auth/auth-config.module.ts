import { NgModule } from '@angular/core';
import { AuthModule } from 'angular-auth-oidc-client';
import { environment } from 'src/environments/environment';

@NgModule({
  imports: [AuthModule.forRoot({
    config: {
      authority: environment.authOAuthority,
      redirectUrl: window.location.origin,
      clientId: environment.authOClientId,
      scope: 'openid profile offline_access email',
      responseType: 'code',
      silentRenew: true,
      useRefreshToken: true,
      secureRoutes: ['http://localhost:8080/'],
      customParamsAuthRequest: {
        audience: 'http://localhost:8080'
      }
    }
  })],
  exports: [AuthModule],
})
export class AuthConfigModule { }
