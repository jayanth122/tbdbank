import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { DataService } from '../data.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss']
})
export class RegistrationComponent implements OnInit {
  constructor(private dataService: DataService) { }
  genders = ["Male", "Female", "Others"]
  provinces = ["AB", "BC", "MB", "NB", "NL", "NT", "NS", "NU", "ON", "PE", "QC", "SK", "YT"]
  ngOnInit(): void {

  }
  registration(regForm: NgForm) {
    this.dataService.sendRegistrationDetails(regForm.value).subscribe(data => {
      if (data.success) {
        alert("Registration Successfull")
      } else {
        alert("Registration UnSuccessfull")
      }
    })

  }
}
