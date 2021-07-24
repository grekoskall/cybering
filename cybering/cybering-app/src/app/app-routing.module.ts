import { NgModule } from '@angular/core';
import { Routes, RouterModule} from '@angular/router';
import { BrowserModule } from '@angular/platform-browser';
import { RegisterComponent } from './welcome_page/register/register.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { WelcomePageComponent } from './welcome_page/welcome-page.component';
import { FullSignInComponent } from './welcome_page/full-sign-in/full-sign-in.component';
import { ForgotPasswordComponent } from './welcome_page/forgot-password/forgot-password.component';


@NgModule({
  imports: [
  BrowserModule,
  RouterModule.forRoot([
    { path: '',  component: WelcomePageComponent },
    { path: 'forgotpassword', component: ForgotPasswordComponent },
    { path: 'fullsignin', component: FullSignInComponent },
    { path: 'register', component: RegisterComponent },
    { path: '**', component: PageNotFoundComponent }
  ]),
],
  exports: [RouterModule]
})
export class AppRoutingModule { }