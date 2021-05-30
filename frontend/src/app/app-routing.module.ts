import { LoginFormComponent } from './components/auth-forms/login-form/login-form.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { WelcomePageComponent } from './components/welcome-page/welcome-page.component';
import { RegistrationFormComponent } from './components/auth-forms/registration-form/registration-form.component';
import { FilesComponent } from './components/file-component/files/files.component';
import { SearchPageComponent } from './components/search-page/search-page.component';
import { AuthGuardService } from './auth/guard/auth-guard.service';
import {FolderComponent} from './components/file-component/folder/folder.component';
import {ProfilePageComponent} from './components/profile-page/profile-page.component';

const routes: Routes = [
  { path: 'home', component: WelcomePageComponent },
  { path: 'login', component: LoginFormComponent },
  { path: 'registration', component: RegistrationFormComponent },
  { path: 'files', component: FilesComponent, canActivate: [AuthGuardService] },
  { path: 'search', component: SearchPageComponent },
  { path: 'profile', component: ProfilePageComponent },
  { path: 'folder/:id', component: FolderComponent, canActivate: [AuthGuardService] },
  { path: '**', redirectTo: 'home' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { relativeLinkResolution: 'legacy' })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
