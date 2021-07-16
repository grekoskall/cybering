import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ProfessionalService } from '../../service/professional-service.service';
import { Professional } from '../../model/professional';

@Component({
  selector: 'app-professionals-form',
  templateUrl: './professionals-form.component.html',
  styleUrls: ['./professionals-form.component.css']
})
export class ProfessionalsFormComponent {

  professional: Professional;

  constructor(
    private route: ActivatedRoute, 
      private router: Router, 
        private professionalService: ProfessionalService) {
    this.professional = new Professional();
  }

  onSubmit() {
    this.professionalService.save(this.professional).subscribe(result => this.gotoUserList());
  }

  gotoUserList() {
    this.router.navigate(['/professionals']);
  }
}