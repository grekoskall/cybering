import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ProfessionalsListComponent } from './professionals-list/professionals-list.component';
import { ProfessionalsFormComponent } from './professionals-form/professionals-form.component';

const routes: Routes = [
  { path: 'professionals', component: ProfessionalsListComponent },
  { path: 'addprofessional', component: ProfessionalsFormComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }