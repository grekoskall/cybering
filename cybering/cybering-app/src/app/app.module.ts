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
import { HomePageComponent } from './home-page/home-page.component';
import { SamePasswordDirective } from './validators/same-password.directive';
import { AddPhoneComponent } from './welcome_page/register/add-phone/add-phone/add-phone.component';

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
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [SignInService, CookieService],
  bootstrap: [AppComponent]
})
export class AppModule { }