import { AuthService } from '../../services/auth.service';
import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate {

  constructor(
    private readonly authService: AuthService,
    private readonly router: Router) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Promise<boolean> | boolean {
    return !this.authService.isLoggedIn() ? this.router.navigate(['/']) :
      route.url[0].path.includes('login') ? this.router.navigate(['/']) : true;
  }
}
