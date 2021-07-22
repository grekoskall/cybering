import { NgModule } from '@angular/core';
import { Routes, RouterModule} from '@angular/router';
import { BrowserModule } from '@angular/platform-browser';
import { LoginComponent } from './welcome_page/login/login.component';
import { RegisterComponent } from './welcome_page/register/register.component';


@NgModule({
  imports: [
  BrowserModule,
  RouterModule.forRoot([
    { path: 'login', component: LoginComponent},
    { path: 'register', component: RegisterComponent }
  ]),
],
  exports: [RouterModule]
})
export class AppRoutingModule { }