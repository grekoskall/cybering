import { NgModule } from '@angular/core';
import { Routes, RouterModule} from '@angular/router';
import { BrowserModule } from '@angular/platform-browser';
import { RegisterComponent } from './welcome_page/register/register.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { WelcomePageComponent } from './welcome_page/welcome-page.component';
import { FullSignInComponent } from './welcome_page/full-sign-in/full-sign-in.component';
import { ForgotPasswordComponent } from './welcome_page/forgot-password/forgot-password.component';
import { HomePageComponent } from './home-page/home-page.component';
import { AuthGuard } from './guards/auth.guard';
import { AddPhoneComponent } from './welcome_page/register/add-phone/add-phone/add-phone.component';


@NgModule({
  imports: [
  BrowserModule,
  RouterModule.forRoot([
    { path: '',  component: WelcomePageComponent },
    { path: 'home-page', component: HomePageComponent, canActivate: [AuthGuard] },
    { path: 'add-phone', component: AddPhoneComponent, canActivate: [AuthGuard] },
    { path: 'forgotpassword', component: ForgotPasswordComponent },
    { path: 'fullsignin', component: FullSignInComponent },
    { path: 'register', component: RegisterComponent },
    { path: '**', component: PageNotFoundComponent }
  ]),
],
  exports: [RouterModule],
  providers: [AuthGuard]
})
export class AppRoutingModule { }