import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { RegisterComponent } from './welcome_page/register/register.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { WelcomePageComponent } from './welcome_page/welcome-page.component';
import { ReactiveFormsModule } from '@angular/forms';
import { FullSignInComponent } from './welcome_page/full-sign-in/full-sign-in.component';
import { ForgotPasswordComponent } from './welcome_page/forgot-password/forgot-password.component';
import { SignInService } from 'src/service/sign-in-service/sign-in.service';
import { CookieService } from 'ngx-cookie-service';
import { HomePageComponent } from './cybering/home-page/home-page.component';
import { SamePasswordDirective } from './validators/same-password.directive';
import { AddPhoneComponent } from './welcome_page/register/add-phone/add-phone.component';
import { BasicInfoComponent } from './welcome_page/register/basic-info/basic-info.component';
import { RegisterService } from 'src/service/register-service/register.service';
import { CyberingComponent } from './cybering/cybering.component';
import { NetworkComponent } from './cybering/network/network.component';
import { AdvertisementsComponent } from './cybering/advertisements/advertisements.component';
import { DiscussionsComponent } from './cybering/discussions/discussions.component';
import { NotificationsComponent } from './cybering/notifications/notifications.component';
import { PersonalinfoComponent } from './cybering/personalinfo/personalinfo.component';
import { SettingsComponent } from './cybering/settings/settings.component';
import { NetworkListComponent } from './cybering/home-page/network-list/network-list.component';
import { TimelineComponent } from './cybering/home-page/timeline/timeline.component';
import { TimelinePostComponent } from './cybering/home-page/timeline/timeline-post/timeline-post.component';
import { PersonalinfoListComponent } from './cybering/home-page/personalinfo-list/personalinfo-list.component';
import { ProfileInfoService } from 'src/service/profile-info-service/profile-info.service';
import { NetworkService } from 'src/service/network-list-service/network.service';
import { ArticleService } from 'src/service/articles-service/article.service';
import { AdminComponent } from './admin/admin.component';
import { ManageComponent } from './admin/manage/manage.component';
import { ExportComponent } from './admin/export/export.component';
import { AdvertisementPostComponent } from './cybering/advertisements/advertisement-post/advertisement-post.component';
import { AdvertisementsApplicationsComponent } from './cybering/advertisements/advertisements-applications/advertisements-applications.component';
import { AdvertisementsHomeComponent } from './cybering/advertisements/advertisements-home/advertisements-home.component';
import { AdvertService } from 'src/service/advert-service/advert.service';
import { ProfileComponent } from './cybering/profile/profile.component';

@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent,
    PageNotFoundComponent,
    WelcomePageComponent,
    FullSignInComponent,
    ForgotPasswordComponent,
    HomePageComponent,
    SamePasswordDirective,
    AddPhoneComponent,
    BasicInfoComponent,
    CyberingComponent,
    NetworkComponent,
    AdvertisementsComponent,
    DiscussionsComponent,
    NotificationsComponent,
    PersonalinfoComponent,
    SettingsComponent,
    NetworkListComponent,
    TimelineComponent,
    TimelinePostComponent,
    PersonalinfoListComponent,
    AdminComponent,
    ManageComponent,
    ExportComponent,
    AdvertisementPostComponent,
    AdvertisementsApplicationsComponent,
    AdvertisementsHomeComponent,
    ProfileComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [ArticleService, SignInService, CookieService, RegisterService, ProfileInfoService, NetworkService, AdvertService],
  bootstrap: [AppComponent]
})
export class AppModule { }