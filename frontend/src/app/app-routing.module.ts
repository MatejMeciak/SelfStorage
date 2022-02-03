import { LoginFormComponent } from './components/auth-forms/login-form/login-form.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LandingPageComponent } from './components/landing-page/landing-page.component';
import { RegistrationFormComponent } from './components/auth-forms/registration-form/registration-form.component';
import { ContentComponent } from './components/file-components/content/content.component';
import { SearchPageComponent } from './components/search-page/search-page.component';
import { AuthGuardService } from './auth/guard/auth-guard.service';
import { FolderComponent } from './components/file-components/folder/folder.component';
import { ProfilePageComponent } from './components/user-components/profile-page/profile-page.component';

const routes: Routes = [
  { path: '', component: LandingPageComponent },
  { path: 'files', component: ContentComponent, canActivate: [AuthGuardService] },
  { path: 'folder/:id', component: FolderComponent, canActivate: [AuthGuardService] },
  { path: 'search', component: SearchPageComponent },
  { path: 'login', component: LoginFormComponent },
  { path: 'registration', component: RegistrationFormComponent },
  { path: 'profile', component: ProfilePageComponent, canActivate: [AuthGuardService] },
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { relativeLinkResolution: 'legacy' })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
