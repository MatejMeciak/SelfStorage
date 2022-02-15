import { LoginFormComponent } from './components/auth-forms/login-form/login-form.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LandingPageComponent } from './components/landing-page/landing-page.component';
import { RegistrationFormComponent } from './components/auth-forms/registration-form/registration-form.component';
import { ContentPageComponent } from './components/file-components/content-page/content-page.component';
import { SearchPageComponent } from './components/search-page/search-page.component';
import { AuthGuardService } from './auth/guard/auth-guard.service';
import { FolderComponent } from './components/file-components/folder/folder.component';
import { ProfilePageComponent } from './components/user-components/profile-page/profile-page.component';
import { OnlyFilesComponent } from "./components/file-components/only-files/only-files.component";
import { OnlyFoldersComponent } from "./components/file-components/only-folders/only-folders.component";

const routes: Routes = [
  { path: '', component: LandingPageComponent },
  { path: 'storage', component: ContentPageComponent, canActivate: [AuthGuardService] },
  { path: 'allFiles', component: OnlyFilesComponent, canActivate: [AuthGuardService] },
  { path: 'folders', component: OnlyFoldersComponent, canActivate: [AuthGuardService] },
  { path: 'folder/:id', component: FolderComponent, canActivate: [AuthGuardService] },
  { path: 'categories', component: ContentPageComponent, canActivate: [AuthGuardService] },
  { path: 'public', component: SearchPageComponent },
  { path: 'login', component: LoginFormComponent },
  { path: 'registration', component: RegistrationFormComponent },
  { path: 'profile', component: ProfilePageComponent, canActivate: [AuthGuardService] },
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { relativeLinkResolution: 'legacy' })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
