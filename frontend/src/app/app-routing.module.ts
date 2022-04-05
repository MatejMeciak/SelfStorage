import { LoginFormComponent } from './components/auth-forms/login-form/login-form.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LandingPageComponent } from './components/landing-page/landing-page.component';
import { RegistrationFormComponent } from './components/auth-forms/registration-form/registration-form.component';
import { ContentPageComponent } from './components/file-components/content-page/content-page.component';
import { PublicPageComponent } from './components/public-page/public-page.component';
import { AuthGuardService } from './auth/guard/auth-guard.service';
import { FolderComponent } from './components/file-components/folder/folder.component';
import { ProfilePageComponent } from './components/user-components/profile-page/profile-page.component';
import { OnlyFilesComponent } from "./components/file-components/only-files/only-files.component";
import { OnlyFoldersComponent } from "./components/file-components/only-folders/only-folders.component";
import { SharedFromPageComponent } from "./components/shared-from-page/shared-from-page.component";
import { CategoriesComponent } from "./components/categories/categories.component";
import { LoginGuard } from "./auth/guard/login.guard";
import { SharedToPageComponent } from "./components/shared-to-page/shared-to-page.component";

const routes: Routes = [
  { path: '', component: LandingPageComponent },
  { path: 'public', component: PublicPageComponent },
  { path: 'storage', component: ContentPageComponent, canActivate: [AuthGuardService] },
  { path: 'allFiles', component: OnlyFilesComponent, canActivate: [AuthGuardService] },
  { path: 'folders', component: OnlyFoldersComponent, canActivate: [AuthGuardService] },
  { path: 'folder/:id', component: FolderComponent, canActivate: [AuthGuardService] },
  { path: 'categories', component: CategoriesComponent, canActivate: [AuthGuardService] },
  { path: 'sharedFrom', component: SharedFromPageComponent, canActivate: [AuthGuardService] },
  { path: 'sharedTo', component: SharedToPageComponent, canActivate: [AuthGuardService] },
  { path: 'profile', component: ProfilePageComponent, canActivate: [AuthGuardService] },
  { path: 'login', component: LoginFormComponent, canActivate: [LoginGuard] },
  { path: 'registration', component: RegistrationFormComponent, canActivate: [LoginGuard] },
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { relativeLinkResolution: 'legacy' })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
