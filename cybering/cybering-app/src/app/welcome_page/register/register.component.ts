import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';



@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  registerForm = new FormGroup({
    emailControl: new FormControl(''),
    passwordControl: new FormControl('')
  });

  constructor() { }

  ngOnInit(): void {
  }

  registerSubmit() {
    
  }

}
