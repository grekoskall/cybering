import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { ProfessionalsListComponent } from './professionals-list/professionals-list.component';
import { ProfessionalsFormComponent } from './professionals-form/professionals-form.component';
import { ProfessionalService } from '../service/professional-service.service';

@NgModule({
  declarations: [
    AppComponent,
    ProfessionalsListComponent,
    ProfessionalsFormComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [ProfessionalService],
  bootstrap: [AppComponent]
})
export class AppModule { }