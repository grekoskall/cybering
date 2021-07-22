import { NgModule } from '@angular/core';
import { Routes, RouterModule} from '@angular/router';
import { BrowserModule } from '@angular/platform-browser';
import { LoginComponent } from './welcome_page/login/login.component';
import { RegisterComponent } from './welcome_page/register/register.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { WelcomePageComponent } from './welcome_page/welcome-page.component';


@NgModule({
  imports: [
  BrowserModule,
  RouterModule.forRoot([
    { path: '',  component: WelcomePageComponent },
    { path: 'login', component: LoginComponent },
    { path: 'register', component: RegisterComponent },
    { path: '**', component: PageNotFoundComponent }
  ]),
],
  exports: [RouterModule]
})
export class AppRoutingModule { }