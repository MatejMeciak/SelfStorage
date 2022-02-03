import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NavigationBarComponent } from './components/navigation-bar/navigation-bar.component';
import { MaterialModule } from './material/material.module';
import { LoginFormComponent } from './components/auth-forms/login-form/login-form.component';
import { LandingPageComponent } from './components/landing-page/landing-page.component';
import { FileService } from './services/file.service';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { ContentCardComponent } from './components/file-components/content-card/content-card.component';
import { RegistrationFormComponent } from './components/auth-forms/registration-form/registration-form.component';
import { ContentComponent } from './components/file-components/content/content.component';
import { FileDetailComponent } from './components/file-components/file-detail/file-detail.component';
import { SearchPageComponent } from './components/search-page/search-page.component';
import { AuthService } from './services/auth.service';
import { AuthInterceptor } from './auth/interceptor/auth.interceptor';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { EditFileDialogComponent } from './components/dialogs/edit-file-dialog/edit-file-dialog.component';
import { UploadFileDialogComponent } from './components/dialogs/upload-file-dialog/upload-file-dialog.component';
import { CreateFolderDialogComponent } from './components/dialogs/create-folder-dialog/create-folder-dialog.component';
import { MoveToFolderDialogComponent } from './components/dialogs/move-to-folder-dialog/move-to-folder-dialog.component';
import { FolderComponent } from './components/file-components/folder/folder.component';
import { ProfilePageComponent } from './components/user-components/profile-page/profile-page.component';
import { MainSidenavComponent } from './components/main-sidenav/main-sidenav.component';
import { NavbarProfileMenuComponent } from './components/user-components/navbar-profile-menu/navbar-profile-menu.component';
import { FolderService } from "./services/folder.service";
import { CategoryService } from "./services/category.service";
import { DialogService } from "./services/dialog.service";
import { AdminComponent } from './components/admin/admin.component';
import { ReportService } from "./services/report.service";
import { FoldersComponent } from './components/file-components/folders/folders.component';
import { FilesComponent } from './components/file-components/files/files.component';
import { ImageService } from "./services/image.service";
import { SidenavService } from "./services/sidenav.service";
import { TokenStorageService } from "./services/token-storage.service";

@NgModule({
  declarations: [
    AppComponent,
    NavigationBarComponent,
    LoginFormComponent,
    LandingPageComponent,
    ContentCardComponent,
    RegistrationFormComponent,
    ContentComponent,
    FileDetailComponent,
    SearchPageComponent,
    EditFileDialogComponent,
    UploadFileDialogComponent,
    CreateFolderDialogComponent,
    MoveToFolderDialogComponent,
    FolderComponent,
    ProfilePageComponent,
    MainSidenavComponent,
    NavbarProfileMenuComponent,
    AdminComponent,
    FoldersComponent,
    FilesComponent,
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
  providers: [
    FileService, FolderService,
    CategoryService, DialogService,
    AuthService, ReportService,
    ImageService, SidenavService,
    TokenStorageService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
