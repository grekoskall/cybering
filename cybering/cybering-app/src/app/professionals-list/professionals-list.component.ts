import { Component, OnInit } from '@angular/core';
import { Professional } from '../../model/professional';
import { ProfessionalService } from '../../service/professional-service.service';

@Component({
  selector: 'app-professionals-list',
  templateUrl: './professionals-list.component.html',
  styleUrls: ['./professionals-list.component.css']
})
export class ProfessionalsListComponent implements OnInit {

  professionals: Professional[] | undefined;

  constructor(private professionalService: ProfessionalService) {
  }

  ngOnInit() {
    this.professionalService.findAll().subscribe(data => {
      this.professionals = data;
    });
  }
}