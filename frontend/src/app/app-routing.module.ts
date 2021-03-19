import { LoginFormComponent } from './components/login-form/login-form.component';
import { HomePageComponent } from './components/home-page/home-page.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { WelcomePageComponent } from './components/welcome-page/welcome-page.component';

const routes: Routes = [
  { path: '', redirectTo: '/welcome', pathMatch: 'full' },
  { path: 'home', component: HomePageComponent },
  { path: 'welcome', component: WelcomePageComponent },
  { path: 'login', component: LoginFormComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
