import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NavigationBarComponent } from './components/navigation-bar/navigation-bar.component';
import { HomePageComponent } from './components/home-page/home-page.component';
import { MaterialModule } from './material/material.module';
import { LoginFormComponent } from './components/login-form/login-form.component';
import { UserService } from './services/user.service';
import { WelcomePageComponent } from './components/welcome-page/welcome-page.component';
import { FileService } from './services/file.service';
import { HttpClientModule } from '@angular/common/http';
import { FileCardComponent } from './components/file-card/file-card.component';

@NgModule({
  declarations: [
    AppComponent,
    NavigationBarComponent,
    HomePageComponent,
    LoginFormComponent,
    WelcomePageComponent,
    FileCardComponent,
  ],
  imports: [
    MaterialModule,
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule
  ],
  providers: [UserService, FileService],
  bootstrap: [AppComponent]
})
export class AppModule { }
