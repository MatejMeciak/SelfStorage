import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NavigationBarComponent } from './components/navigation-bar/navigation-bar.component';
import { HomePageComponent } from './components/home-page/home-page.component';
import { MaterialModule } from './material/material.module';
import { LoginFormComponent } from './components/auth-forms/login-form/login-form.component';
import { WelcomePageComponent } from './components/welcome-page/welcome-page.component';
import { FileService } from './services/file.service';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FileCardComponent } from './components/file-component/file-card/file-card.component';
import { RegistrationFormComponent } from './components/auth-forms/registration-form/registration-form.component';
import { FilesComponent } from './components/file-component/files/files.component';
import { FileDetailComponent } from './components/file-component/file-detail/file-detail.component';
import { SearchPageComponent } from './components/search-page/search-page.component';
import { AuthService } from './services/auth.service';
import { AuthInterceptor } from './auth/interceptor/auth.interceptor';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { EditFileDialogComponent } from './components/file-component/dialogs/edit-file-dialog/edit-file-dialog.component';
import { UploadFileDialogComponent } from './components/file-component/dialogs/upload-file-dialog/upload-file-dialog.component';
import { CreateFolderDialogComponent } from './components/file-component/dialogs/create-folder-dialog/create-folder-dialog.component';
import { MoveToFolderDialogComponent } from './components/file-component/dialogs/move-to-folder-dialog/move-to-folder-dialog.component';
import { FolderComponent } from './components/file-component/folder/folder.component';

@NgModule({
  declarations: [
    AppComponent,
    NavigationBarComponent,
    HomePageComponent,
    LoginFormComponent,
    WelcomePageComponent,
    FileCardComponent,
    RegistrationFormComponent,
    FilesComponent,
    FileDetailComponent,
    SearchPageComponent,
    EditFileDialogComponent,
    UploadFileDialogComponent,
    CreateFolderDialogComponent,
    MoveToFolderDialogComponent,
    FolderComponent,
  ],
  imports: [
    MaterialModule,
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
  ],
  providers: [ FileService, AuthService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
