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
import { ContentPageComponent } from './components/file-components/content-page/content-page.component';
import { FileDetailComponent } from './components/file-components/file-detail/file-detail.component';
import { PublicPageComponent } from './components/public-page/public-page.component';
import { AuthService } from './services/auth.service';
import { AuthInterceptor } from './auth/interceptor/auth.interceptor';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { EditFileDialogComponent } from './components/dialogs/edit-file-dialog/edit-file-dialog.component';
import { UploadFileDialogComponent } from './components/dialogs/upload-file-dialog/upload-file-dialog.component';
import { CreateFolderOrCategoryDialogComponent } from './components/dialogs/create-folder-dialog/create-folder-or-category-dialog.component';
import { SelectContentDialogComponent } from './components/dialogs/select-content-dialog/select-content-dialog.component';
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
import { OpenContentDialogComponent } from './components/dialogs/open-content-dialog/open-content-dialog.component';
import { ConfirmDialogComponent } from './components/dialogs/confirm-dialog/confirm-dialog.component';
import { InputDialogComponent } from './components/dialogs/input-dialog/input-dialog.component';
import { ContentHeaderComponent } from './components/core-components/content-header/content-header.component';
import { FilesPlusDetailComponent } from './components/file-components/files-plus-detail/files-plus-detail.component';
import { OnlyFilesComponent } from './components/file-components/only-files/only-files.component';
import { OnlyFoldersComponent } from './components/file-components/only-folders/only-folders.component';
import { SharedPageComponent } from './components/shared-page/shared-page.component';

@NgModule({
  declarations: [
    AppComponent,
    NavigationBarComponent,
    LoginFormComponent,
    LandingPageComponent,
    ContentCardComponent,
    RegistrationFormComponent,
    ContentPageComponent,
    FileDetailComponent,
    PublicPageComponent,
    EditFileDialogComponent,
    UploadFileDialogComponent,
    CreateFolderOrCategoryDialogComponent,
    SelectContentDialogComponent,
    FolderComponent,
    ProfilePageComponent,
    MainSidenavComponent,
    NavbarProfileMenuComponent,
    AdminComponent,
    FoldersComponent,
    FilesComponent,
    OpenContentDialogComponent,
    ConfirmDialogComponent,
    InputDialogComponent,
    ContentHeaderComponent,
    FilesPlusDetailComponent,
    OnlyFilesComponent,
    OnlyFoldersComponent,
    SharedPageComponent,
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
