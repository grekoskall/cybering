import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { BrowserModule } from '@angular/platform-browser';
import { RegisterComponent } from './welcome_page/register/register.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { WelcomePageComponent } from './welcome_page/welcome-page.component';
import { FullSignInComponent } from './welcome_page/full-sign-in/full-sign-in.component';
import { ForgotPasswordComponent } from './welcome_page/forgot-password/forgot-password.component';
import { HomePageComponent } from './cybering/home-page/home-page.component';
import { AuthGuard } from './guards/auth.guard';
import { AddPhoneComponent } from './welcome_page/register/add-phone/add-phone.component';
import { BasicInfoComponent } from './welcome_page/register/basic-info/basic-info.component';
import { CyberingComponent } from './cybering/cybering.component';
import { NetworkComponent } from './cybering/network/network.component';
import { AdvertisementsComponent } from './cybering/advertisements/advertisements.component';
import { DiscussionsComponent } from './cybering/discussions/discussions.component';
import { NotificationsComponent } from './cybering/notifications/notifications.component';
import { PersonalinfoComponent } from './cybering/personalinfo/personalinfo.component';
import { SettingsComponent } from './cybering/settings/settings.component';
import { AdminComponent } from './admin/admin.component';
import { ManageComponent } from './admin/manage/manage.component';
import { ExportComponent } from './admin/export/export.component';
import { AdvertisementPostComponent } from './cybering/advertisements/advertisement-post/advertisement-post.component';
import { AdvertisementsApplicationsComponent } from './cybering/advertisements/advertisements-applications/advertisements-applications.component';
import { AdvertisementsHomeComponent } from './cybering/advertisements/advertisements-home/advertisements-home.component';


@NgModule({
  imports: [
    BrowserModule,
    RouterModule.forRoot([
      { path: '', component: WelcomePageComponent },
      { path: 'admin/cybering', component: AdminComponent, canActivate: [AuthGuard], children: [
        { path: '', component: ManageComponent, canActivate: [AuthGuard] },
        { path: 'manage', component: ManageComponent, canActivate: [AuthGuard] },
        { path: 'export', component: ExportComponent, canActivate: [AuthGuard] },
      ]},
      {
        path: 'cybering', component: CyberingComponent, canActivate: [AuthGuard], children: [
          { path: '', component: HomePageComponent, canActivate: [AuthGuard] },
          { path: 'home-page', component: HomePageComponent, canActivate: [AuthGuard] },
          { path: 'network', component: NetworkComponent, canActivate: [AuthGuard] },
          { path: 'advertisements', component: AdvertisementsComponent, canActivate: [AuthGuard], children: [
            { path: 'home', component: AdvertisementsHomeComponent, canActivate: [AuthGuard] },
            { path: 'post', component: AdvertisementPostComponent, canActivate: [AuthGuard]},
            { path: 'applications', component: AdvertisementsApplicationsComponent, canActivate: [AuthGuard]}
          ] },
          { path: 'discussions', component: DiscussionsComponent, canActivate: [AuthGuard] },
          { path: 'notifications', component: NotificationsComponent, canActivate: [AuthGuard] },
          { path: 'personalinfo', component: PersonalinfoComponent, canActivate: [AuthGuard] },
          { path: 'settings', component: SettingsComponent, canActivate: [AuthGuard] }
        ]
      },
      { path: 'forgotpassword', component: ForgotPasswordComponent },
      { path: 'fullsignin', component: FullSignInComponent },
      {
        path: 'register', component: RegisterComponent, children: [
          { path: '', component: BasicInfoComponent },
          { path: 'add-phone', component: AddPhoneComponent, canActivate: [AuthGuard] },
        ]
      },
      { path: '**', component: PageNotFoundComponent }
    ]),
  ],
  exports: [RouterModule],
  providers: [AuthGuard]
})
export class AppRoutingModule { }